package service.letv;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.VipResolverTypeEnum;
import model.BaseVideo;
import model.Episode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import service.ISearchVideoService;
import utils.URLUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by gaochen on 2018/5/20.
 */
public class LETVSearchVideoServiceImpl implements ISearchVideoService {

    private static final String BASE_URL = "http://so.le.com/s?wd=%s";

    private static final String EPISODE_URL = "http://open.api.letv.com/detail/cbase/q/website?sk=newleso_a_%s";

    @Override
    public List<BaseVideo> search(String key) {
        try {
            Document document = Jsoup.connect(String.format(BASE_URL, key)).get();
            Elements infos = document.select("body > div[data-statectn] > div.so-cont");
            return infos.stream().map(info -> {
                //body > div:nth-child(10) > div > div.con > ul > li.selected > a
                String dataId = info.select("div.con > ul.supplier-tab.j-tui-tab > li.selected > a").attr("k-data");
                String url = URLUtils.complementUrl(info.select("div.con > div.info-tit > h1 > a").attr("href"),false);
                String name = info.select("div.con > div.info-tit > h1 > a").attr("title");
                String description = info.select("div.con > div.info-cnt").text();
                String imageUrl = URLUtils.complementUrl(info.select("div.left > div > a > img").attr("src"),false);
                //剧集
                List<Episode> episodes = new ArrayList<>();
                if(!dataId.isEmpty()) {
                    try {
                        String json = Jsoup.connect(String.format(EPISODE_URL, dataId.replace(",","_")))
                                .ignoreContentType(true)
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json; charset=UTF-8")
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                                .timeout(10000)
                                .get().text();
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                        JsonNode rootNode = mapper.readTree(json);
                        Optional<JsonNode> episodeNode = StreamSupport.stream(Spliterators.spliteratorUnknownSize(rootNode.elements(), Spliterator.ORDERED),
                                false).filter(node -> node != null && "letv".equals(node.get("site").asText()))
                                .findFirst();
                        episodeNode.ifPresent(node -> {
                            node.get("videoList").elements().forEachRemaining(e -> {
                                episodes.add(new Episode(null,e.get("aorder").asText(),e.get("url").asText(),null));
                            });
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(episodes.isEmpty()) {
                    episodes.add(new Episode(null,name,url,null));
                }
                return new BaseVideo(name, url, imageUrl, description,VipResolverTypeEnum.MGTV.getDescription(), null,episodes);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("ten service error " + e.getMessage());
            return null;
        }
    }
}
