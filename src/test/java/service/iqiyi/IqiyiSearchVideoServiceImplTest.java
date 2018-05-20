package service.iqiyi;

import model.BaseVideo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by gaochen on 2018/5/20.
 */
public class IqiyiSearchVideoServiceImplTest {

    @Test
    public void search() {
        IqiyiSearchVideoServiceImpl iqiyiSearchVideoService = new IqiyiSearchVideoServiceImpl();
        List<BaseVideo> videos = iqiyiSearchVideoService.search("三生三世");
        System.out.println(videos);
    }
}