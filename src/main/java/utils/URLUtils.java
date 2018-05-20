package utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by gaochen on 2018/5/19.
 */
public class URLUtils {

    private static final String HTTP= "http://";
    private static final String HTTPS =  "https://";

    public static String complementUrl(String url,boolean isHttps) {
        return StringUtils.join(isHttps ? HTTPS : HTTP,clearUrl(url));
    }

    //清除URL里多余的符号
    private static String clearUrl(String url) {
        if (url.contains(HTTP)) {
            url = url.substring(url.lastIndexOf(HTTP) + HTTP.length());
            for (int i = 0; i < url.length(); i++) {
                if (url.charAt(i) == '/' || url.charAt(i) == '.'
                        || url.charAt(i) == '\\') {
                } else {
                    url = url.substring(i);
                    return url;
                }
            }
        } else if (url.contains(HTTPS)) {
            url = url.substring(url.lastIndexOf(HTTPS) + HTTPS.length());
            for (int i = 0; i < url.length(); i++) {
                if (url.charAt(i) == '/' || url.charAt(i) == '.'
                        || url.charAt(i) == '\\') {
                } else {
                    url = url.substring(i);
                    return url;
                }
            }
        }else{
            for (int i = 0; i < url.length(); i++) {
                if (url.charAt(i) == '/' || url.charAt(i) == '.'
                        || url.charAt(i) == '\\') {
                } else {
                    url = url.substring(i);
                    return url;
                }
            }
        }
        return url;
    }
}
