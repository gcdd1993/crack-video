package service;

import model.BaseVideo;
import org.junit.Test;
import service.youku.YouKuSearchVideoServiceImpl;

import java.util.List;

/**
 * Created by gaochen on 2018/5/19.
 */
public class YouKuSearchVideoServiceImplTest {

    @Test
    public void search() {
        YouKuSearchVideoServiceImpl service = new YouKuSearchVideoServiceImpl();
        List<BaseVideo> videos = service.search("斗罗大陆");
        System.out.println(videos);
    }
}