package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

/**
 * Created by gaochen on 2018/4/22.
 * 剧集
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Episode {

    private Integer id;

    private String name;

    private String url;

    private Timestamp updateTime;
}