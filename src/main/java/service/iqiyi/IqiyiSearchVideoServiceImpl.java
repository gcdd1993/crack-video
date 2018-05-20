package service.iqiyi;

import model.BaseVideo;
import model.Episode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import service.ISearchVideoService;
import utils.URLUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaochen on 2018/5/20.
 */
public class IqiyiSearchVideoServiceImpl implements ISearchVideoService {

    private static final String BASE_URL = "http://so.iqiyi.com/so/q_%s";

    @Override
    public List<BaseVideo> search(String key) {
        try {
            Document document = Jsoup.connect(String.format(BASE_URL, key)).get();
            System.out.println(document.html());
            Elements infos = document.select("body > div.page-search > div.container.clearfix > div.search_result_main > div > div.mod_search_result > div.mod_result > ul.mod_result_list > li.list_item");
            return infos.stream().map(info -> {
                String url = URLUtils.complementUrl(info.select("a").attr("href"),false);
                String name = info.attr("data-widget-searchlist-tvname");
                String description = info.select("div.info_item span.result_info_txt").text();
                String imageUrl = URLUtils.complementUrl(info.select("a > img").attr("src"),false);
                //剧集
                Elements elements = info.select("div > div.info_item.mt15 > div > div > ul > li > a");
                List<Episode> episodes = elements.stream().map(element ->
                        new Episode(element.attr("title"), URLUtils.complementUrl(element.attr("href"),false)))
                        .filter(episode -> !episode.getUrl().contains("javascript"))
                        .distinct()
                        .collect(Collectors.toList());
                if(episodes.isEmpty()) {
                    episodes.add(new Episode(name,url));
                }
                return new BaseVideo(name, url, imageUrl, description,"爱奇艺", episodes);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("ten service error " + e.getMessage());
            return null;
        }
    }
}
