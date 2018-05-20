package constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaochen on 2018/5/20.
 */
public enum VIPResolverTypeEnum {

    ALL("万能接口"),
    TENCENT("腾讯视频"),
    YOUKU("优酷"),
    IQIYI("爱奇艺"),
    MGTV("芒果TV"),
    LETV("乐视"),
    SOUHU("搜狐");

    private String description;

    public String getDescription() {
        return description;
    }

    private VIPResolverTypeEnum(String description) {
        this.description = description;
    }

    @JsonValue
    public int toInt() {
        return ordinal();
    }

    public static VIPResolverTypeEnum typeOf(String description) {
        switch (description) {
            case "万能接口": return ALL;
            case "腾讯视频": return TENCENT;
            case "优酷": return YOUKU;
            case "爱奇艺": return IQIYI;
            case "芒果TV": return MGTV;
            case "乐视": return LETV;
            case "搜狐": return SOUHU;
            default: return ALL;
        }
    }

    public static List<String> list() {
        return Arrays.stream(VIPResolverTypeEnum.values())
                .map(VIPResolverTypeEnum::getDescription)
                .collect(Collectors.toList());
    }

}
