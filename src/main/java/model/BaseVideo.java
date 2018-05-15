package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by gaochen on 2018/4/22.
 * 视频信息基类
 */
@AllArgsConstructor
@ToString
@Data
public class BaseVideo {

    private String name;

    private String url;

    private String imageUrl;

    private String description;

    private String from;

    private List<Episode> episodes;
}