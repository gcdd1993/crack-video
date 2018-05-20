package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by gaochen on 2018/5/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipResolver {
    private Integer id;

    private String name;

    private String url;

    private Boolean checked;

    private Integer type;

}
