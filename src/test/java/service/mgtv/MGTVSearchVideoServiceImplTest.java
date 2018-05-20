package service.mgtv;

import model.BaseVideo;
import org.junit.Test;

import java.util.List;

/**
 * Created by gaochen on 2018/5/20.
 */
public class MGTVSearchVideoServiceImplTest {

    @Test
    public void search() {
        MGTVSearchVideoServiceImpl iqiyiSearchVideoService = new MGTVSearchVideoServiceImpl();
        List<BaseVideo> videos = iqiyiSearchVideoService.search("三生三世");
        System.out.println(videos);
    }
}