package service;

import model.BaseVideo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by gaochen on 2018/5/15.
 */
public class SearchServiceHandler implements ISearchVideoService {

    private static SearchServiceHandler searchServiceHandler = new SearchServiceHandler();

    private static List<ISearchVideoService> searchVideoServiceList = new ArrayList<>();

    static {
        //todo
        searchVideoServiceList.add(new TenSearchVideoImplService());
    }

    public static SearchServiceHandler getInstance() {
        return searchServiceHandler;
    }

    @Override
    public List<BaseVideo> search(String key) {
        return searchVideoServiceList.stream()
                .map(service -> service.search(key))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
