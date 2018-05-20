package service;

import constant.VideoTypeEnum;
import constant.VipResolverTypeEnum;
import model.BaseVideo;
import service.iqiyi.IqiyiSearchVideoServiceImpl;
import service.mgtv.MGTVSearchVideoServiceImpl;
import service.tencent.TenSearchVideoServiceImpl;
import service.youku.YouKuSearchVideoServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by gaochen on 2018/5/15.
 */
public class SearchServiceHandler {

    private static SearchServiceHandler searchServiceHandler = new SearchServiceHandler();

    private static ConcurrentHashMap<String,ISearchVideoService> searchVideoServiceMap = new ConcurrentHashMap<>();

    static {
        //todo
        searchVideoServiceMap.put(VipResolverTypeEnum.TENCENT.getDescription(),new TenSearchVideoServiceImpl());
        searchVideoServiceMap.put(VipResolverTypeEnum.YOUKU.getDescription(),new YouKuSearchVideoServiceImpl());
        searchVideoServiceMap.put(VipResolverTypeEnum.IQIYI.getDescription(),new IqiyiSearchVideoServiceImpl());
        searchVideoServiceMap.put(VipResolverTypeEnum.MGTV.getDescription(),new MGTVSearchVideoServiceImpl());
    }

    public static SearchServiceHandler getInstance() {
        return searchServiceHandler;
    }

    public List<BaseVideo> search(List<String> videoWebs,String key) {
        List<BaseVideo> result = new ArrayList<>();
        searchVideoServiceMap.forEach((k,v) -> {
            if(videoWebs.contains(k)) {
                result.addAll(v.search(key));
            }
        });
        return filter(result);
    }

    private List<BaseVideo> filter(List<BaseVideo> videos) {
        return videos.stream().filter(video -> !VideoTypeEnum.NONE.equals(video.getType())).collect(Collectors.toList());
    }

}
