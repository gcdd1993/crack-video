package service;

import model.BaseVideo;
import org.junit.Test;
import service.tencent.TenSearchVideoServiceImpl;

import java.util.List;

/**
 * Created by gaochen on 2018/5/16.
 */
public class TenSearchVideoServiceImplTest {

    @Test
    public void search() {
        TenSearchVideoServiceImpl service = new TenSearchVideoServiceImpl();
        List<BaseVideo> videos = service.search("三生三世");
        System.out.println(videos);
    }

}