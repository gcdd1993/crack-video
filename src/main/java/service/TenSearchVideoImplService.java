package service;

import model.BaseVideo;
import model.Episode;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by gaochen on 2018/4/22.
 * 腾讯视频接口
 */
public class TenSearchVideoImplService implements ISearchVideoService {

    private static final String BASE_URL = "https://v.qq.com/x/search/?q=%s";

    @Override
    public List<BaseVideo> search(String key) {
        try {
            Document document = Jsoup.connect(String.format(BASE_URL, key)).get();
            Elements infos = document.select("body > div.search_container > div.wrapper > div.wrapper_main > div");
            List<BaseVideo> baseVideoList = infos.stream().map(info -> {
                Elements infoList = info.select("div._infos");
                if(infoList == null || infoList.isEmpty()) {
                    return null;
                }
                Element detail = infoList.get(0);
//                log.debug("detail : {}", detail);
                String url = detail.select("h2 > a").attr("href");
                String name = StringUtils.join(detail.select("h2 > a > em").text(),
                        detail.select("h2 > a > span.sub").text(),
                        detail.select("h2 > a > span.type").text());
                String description = detail.select("div > div.info_item.info_item_desc > span.desc_text").text();
                String imageUrl = StringUtils.join("http:",detail.select("a > img").attr("src"));
                //剧集
                Elements elements = document.select("div._playlist > div > div > div > div a");
                List<Episode> episodes = elements.stream().map(element ->
                        new Episode(element.text(), element.attr("href")))
                        .filter(episode -> !episode.getUrl().contains("javascript")).collect(Collectors.toList());
                BaseVideo baseVideo = new BaseVideo(name, url, imageUrl, description,"腾讯视频", episodes);
//                log.info("video : {}", baseVideo);
                return baseVideo;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            return baseVideoList;
        } catch (IOException e) {
//            log.error("tencent search error ",e);
            return null;
        }
    }
}
