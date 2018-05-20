package service.youku;

import constant.VIPResolverTypeEnum;
import model.BaseVideo;
import model.Episode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.ISearchVideoService;
import utils.URLUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by gaochen on 2018/5/19.
 */
public class YouKuSearchVideoServiceImpl implements ISearchVideoService {

    private static final String BASE_URL = "http://www.soku.com/search_video/q_%s";

    @Override
    public List<BaseVideo> search(String key) {
        try {
            Document document = Jsoup.connect(String.format(BASE_URL, key)).get();
            //body > div.sk_container > div.sk-express > div > div:nth-child(1) > div
            Elements infos = document.select("body > div.sk_container > div.sk-express > div.DIR > div.s_dir > div");
            //div.s_info > div > div > div > p > span
            return infos.stream().map(info -> {
                Elements infoList = info.select("div.s_inform.s_madeform > div.s_detail");
                if(infoList == null || infoList.isEmpty()) {
                    return null;
                }
                Element detail = infoList.get(0);
                String url = URLUtils.complementUrl(detail.select("div.s_base > h2 > a:nth-child(1)").attr("href"),false);
                String name = detail.select("div.s_base > h2 > a:nth-child(1)").attr("_log_title");
                String description = detail.select("div.s_info > div > div > div > p > span").text();
                String imageUrl = URLUtils.complementUrl(info.select("div.s_poster > div.s_thumb > div.s_target > img").attr("src"),false);
                //剧集
                Elements elements = info.select("div.s_inform.s_madeform > div.s_detail > div.s_items.site14.lang0 > ul.clearfix > li > a");
                List<Episode> episodes = elements.stream().map(element ->
                        new Episode(element.text(), URLUtils.complementUrl(element.attr("href"),false)))
                        .filter(episode -> !episode.getUrl().contains("javascript")).collect(Collectors.toList());
                if(episodes.isEmpty()) {
                    episodes.add(new Episode(name,url));
                }
                return new BaseVideo(name, url, imageUrl, description,VIPResolverTypeEnum.YOUKU.getDescription(), episodes);
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("ten service error " + e.getMessage());
            return null;
        }
    }
}
