package coolguy1842.factions.Util;

import java.util.ArrayList;

public class StringUtil {
    public static String join(String delim, String... strArr) {
        String out = "";

        for(int i = 0; i < strArr.length - 1; i++) {
            if(strArr[i].length() <= 0) continue;

            out += strArr[i] + delim;
        }

        out += strArr[strArr.length - 1];

        return out;
    }
    
    public static String join(String delim, ArrayList<String> strArr) {
        String out = "";

        for(int i = 0; i < strArr.size() - 1; i++) {
            if(strArr.get(i).length() <= 0) continue;

            out += strArr.get(i) + delim;
        }

        out += strArr.get(strArr.size() - 1);

        return out;
    }
}
