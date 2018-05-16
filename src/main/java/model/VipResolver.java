package model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by gaochen on 2018/5/16.
 */
@Data
@AllArgsConstructor
public class VipResolver {
    private String name;

    private String url;

    private boolean checked;

}
