package service.mgtv;

import constant.VideoTypeEnum;
import constant.VipResolverTypeEnum;
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
public class MGTVSearchVideoServiceImpl implements ISearchVideoService {

    private static final String BASE_URL = "https://so.mgtv.com/so/k-%s";

    @Override
    public List<BaseVideo> search(String key) {
        try {
            Document document = Jsoup.connect(String.format(BASE_URL, key)).get();
            Elements infos = document.select("body > div.so-container > div.search-content.clearfix > div > div.search-resultlist > div > div.result-content.clearfix");
            return infos.stream().map(info -> {
                String url = URLUtils.complementUrl(info.select("p > a").attr("href"),true);
                String name = info.attr("data-widget-searchlist-tvname");
                String description = info.select("div.result-box.result-box-imgo > div.desc_text > span:nth-child(2)").text();
                String imageUrl = URLUtils.complementUrl(info.select("p > a > img").attr("src"),true);
                VideoTypeEnum type = VideoTypeEnum.typeOf(info.select("div.result-box.result-box-imgo > p.text-1 > span > a").text());
                //剧集
                Elements elements = info.select("div.result-box.result-box-iqiyi > div > div.searchresult-foldup > p.so-result-alist.playSeriesList > a");
                List<Episode> episodes = elements.stream().map(element ->
                        new Episode(null,element.attr("title"), URLUtils.complementUrl(element.attr("href"),false),null))
                        .filter(episode -> !episode.getUrl().contains("javascript"))
                        .distinct()
                        .collect(Collectors.toList());
                if(episodes.isEmpty()) {
                    episodes.add(new Episode(null,name,url,null));
                }
                return new BaseVideo(name,
                        url,
                        imageUrl,
                        description,
                        VipResolverTypeEnum.MGTV.getDescription(),
                        type,
                        episodes);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("ten service error " + e.getMessage());
            return null;
        }
    }
}
