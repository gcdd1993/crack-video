package utils;

/**
 * Created by gaochen on 2018/5/19.
 */
public class StringUtil {
    public static String autoNewLine(String str,int maxByte) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for(int i=0;i<str.length();i++) {
            if(index % maxByte == 0 && index > 0) {
                sb.append("\n");
            }
            sb.append(str.charAt(i));
            index ++;
        }
        return sb.toString();
    }
}
