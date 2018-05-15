package service;

import model.BaseVideo;

import java.util.List;

/**
 * Created by gaochen on 2018/4/22.
 * 视频搜索接口
 */
public interface ISearchVideoService {
    /**
     * 搜索视频
     * @param key 关键字
     * @return
     */
    List<BaseVideo> search(String key);
}