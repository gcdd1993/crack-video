package service;

import model.BaseVideo;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by gaochen on 2018/5/16.
 */
public class TenSearchVideoImplServiceTest {

    @Test
    public void search() {
        TenSearchVideoImplService service = new TenSearchVideoImplService();
        List<BaseVideo> videos = service.search("斗罗大陆");
        System.out.println(videos);
    }
}