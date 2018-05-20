package model;

import constant.VideoTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by gaochen on 2018/4/22.
 * 视频信息基类
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class BaseVideo {

    private String name;

    private String url;

    private String imageUrl;

    private String description;

    private String from;

    private VideoTypeEnum type;

    private List<Episode> episodes;
}