package service.letv;

import model.BaseVideo;
import org.junit.Test;

import java.util.List;

/**
 * Created by gaochen on 2018/5/20.
 */
public class LETVSearchVideoServiceImplTest {

    @Test
    public void search() {
        LETVSearchVideoServiceImpl iqiyiSearchVideoService = new LETVSearchVideoServiceImpl();
        List<BaseVideo> videos = iqiyiSearchVideoService.search("三生三世");
        System.out.println(videos);
    }
}