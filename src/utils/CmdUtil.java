package utils;

/**
 * Created by mcalancea on 2015-08-04.
 */
public class CmdUtil {
    public static String getValue(String userValue, String defaultValue){
        String result = defaultValue;
        if(userValue != null && userValue.length() > 0){
            result = userValue;
        }
        return result;
    }

}
