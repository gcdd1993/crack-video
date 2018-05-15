package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Created by gaochen on 2018/4/22.
 * 剧集
 */
@AllArgsConstructor
@ToString
@Data
public class Episode {
    private String name;

    private String url;
}