package constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gaochen on 2018/5/20.
 */
public enum VipResolverTypeEnum {

    ALL("万能接口"),
    TENCENT("腾讯视频"),
    YOUKU("优酷"),
    IQIYI("爱奇艺"),
    MGTV("芒果TV");
//    LETV("乐视"),
//    SOUHU("搜狐");

    private String description;

    public String getDescription() {
        return description;
    }

    private VipResolverTypeEnum(String description) {
        this.description = description;
    }

    @JsonValue
    public int toInt() {
        return ordinal();
    }

    public static VipResolverTypeEnum typeOf(String description) {
        switch (description) {
            case "万能接口": return ALL;
            case "腾讯视频": return TENCENT;
            case "优酷": return YOUKU;
            case "爱奇艺": return IQIYI;
            case "芒果TV": return MGTV;
//            case "乐视": return LETV;
//            case "搜狐": return SOUHU;
            default: return ALL;
        }
    }

    public static List<String> list() {
        return Arrays.stream(VipResolverTypeEnum.values())
                .map(VipResolverTypeEnum::getDescription)
                .collect(Collectors.toList());
    }

    public static VipResolverTypeEnum valueOf(int i) {
        switch (i) {
            case 0:return VipResolverTypeEnum.ALL;
            case 1:return VipResolverTypeEnum.TENCENT;
            case 2:return VipResolverTypeEnum.YOUKU;
            case 3:return VipResolverTypeEnum.IQIYI;
            case 4:return VipResolverTypeEnum.MGTV;
            default:return VipResolverTypeEnum.ALL;
        }
    }

}
