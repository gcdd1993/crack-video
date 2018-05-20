package constant;

/**
 * Created by gaochen on 2018/5/20.
 */
public enum VideoTypeEnum {

    MOVIE("电影"),
    TV("电视剧"),
//    VARIETY("综艺"),
    COMIC("动漫"),
    NONE("未知类型");

    private String description;

    private VideoTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static VideoTypeEnum typeOf(String name) {
        if(name.contains("电视剧")) {
            return TV;
        }else if(name.contains("电影")) {
            return MOVIE;
//        }else if(name.contains("综艺")) {
//            return VARIETY;
        }else if(name.contains("动漫")) {
            return COMIC;
        }else {
            return NONE;
        }
    }

    public static VideoTypeEnum valueOf(int i) {
        switch (i) {
            case 0:return VideoTypeEnum.MOVIE;
            case 1:return VideoTypeEnum.TV;
//            case 2:return VideoTypeEnum.VARIETY;
            case 2:return VideoTypeEnum.COMIC;
            default:return VideoTypeEnum.NONE;
        }
    }
}
