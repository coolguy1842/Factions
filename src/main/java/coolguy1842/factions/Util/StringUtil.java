package coolguy1842.factions.Util;

import java.util.ArrayList;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class StringUtil {
    public static String join(String delim, String... strArr) {
        String out = "";

        if(strArr.length <= 0) return "";

        for(int i = 0; i < strArr.length - 1; i++) {
            if(strArr[i].length() <= 0) continue;

            out += strArr[i] + delim;
        }

        out += strArr[strArr.length - 1];

        return out;
    }
    
    public static String join(String delim, ArrayList<String> strArr) {
        String out = "";
        if(strArr.size() <= 0) return "";

        for(int i = 0; i < strArr.size() - 1; i++) {
            if(strArr.get(i).length() <= 0) continue;

            out += strArr.get(i) + delim;
        }

        out += strArr.get(strArr.size() - 1);

        return out;
    }

    public static String componentsToString(Component... components) {
        String out = "";
            
        for(Component component : components) {
            out += PlainTextComponentSerializer.plainText().serialize(component);
        }

        return out;
    }
}
