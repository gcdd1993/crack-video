package service.tencent;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BaseVideo;
import model.Episode;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.ISearchVideoService;
import utils.URLUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by gaochen on 2018/4/22.
 * 腾讯视频接口
 */
public class TenSearchVideoServiceImpl implements ISearchVideoService {

    private static final String BASE_URL = "https://v.qq.com/x/search/?q=%s";

    private static final String EPISODE_URL = "https://s.video.qq.com/get_playsource?id=%s&plat=2&type=4&range=1-100000&data_type=2&video_type=2&plname=qq&otype=json";

    @Override
    public List<BaseVideo> search(String key) {
        try {
            Document document = Jsoup.connect(String.format(BASE_URL, key)).get();
            Elements infos = document.select("body > div.search_container > div.wrapper > div.wrapper_main > div[data-index]");
            return infos.stream().map(info -> {
                String dataId = info.attr("data-id");
                Elements infoList = info.select("div._infos");
                if(infoList == null || infoList.isEmpty()) {
                    return null;
                }
                Element detail = infoList.get(0);
                String url = detail.select("h2 > a").attr("href");
                String name = StringUtils.join(detail.select("h2 > a > em").text(),
                        detail.select("h2 > a > span.sub").text(),
                        detail.select("h2 > a > span.type").text());
                String description = detail.select("div > div.info_item.info_item_desc > span.desc_text").text();
                String imageUrl = URLUtils.complementUrl(detail.select("a > img").attr("src"),false);
                //剧集
                Elements elements = info.select("div._playlist > div > div > div.result_episode_list.cf > div > a");
                //body > div.search_container > div.wrapper > div:nth-child(1) > div:nth-child(2) > div._playlist > div > div > div > div:nth-child(15) > a
                //body > div.search_container > div.wrapper > div:nth-child(1) > div:nth-child(2) > div._playlist > div > div > div > div:nth-child(12) > a
                List<Episode> episodes = new ArrayList<>();
                System.out.println("dataId : " + dataId);
                try {
                    String json = Jsoup.connect(String.format(EPISODE_URL, dataId))
                            .ignoreContentType(true)
                            .header("Accept", "application/json")
                            .header("Content-Type", "application/json; charset=UTF-8")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                            .timeout(10000)
                            .get().text().replace("QZOutputJson=","");
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                    JsonNode rootNode = mapper.readTree(json);
                    Optional<JsonNode> playList = Optional.ofNullable(rootNode.get("PlaylistItem").get("videoPlayList"));
                    playList.ifPresent(p -> {
                      p.elements().forEachRemaining(jsonNode -> {
                            episodes.add(new Episode(jsonNode.get("title").asText(), jsonNode.get("playUrl").asText()));
                        });
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(episodes.isEmpty()) {
                    episodes.add(new Episode(name,url));
                }
                return new BaseVideo(name, url, imageUrl, description,"腾讯视频", episodes);
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("ten service error " + e.getMessage());
            return null;
        }
    }

}
