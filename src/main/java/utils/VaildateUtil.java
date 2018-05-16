package utils;

import java.util.regex.Pattern;

/**
 * Created by gaochen on 2018/5/16.
 */
public class VaildateUtil {

    private static final Pattern URL_PATTERN = Pattern.compile("[a-zA-z]+://[^\\s]*");

    public static boolean validateUrl(String url) {
        return URL_PATTERN.matcher(url).matches();
    }
}
