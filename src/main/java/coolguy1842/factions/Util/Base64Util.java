package coolguy1842.factions.Util;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    public static String strToBase64(String str) {
        return new String(Base64.encodeBase64(str.getBytes()));
    }
    
    public static String base64ToStr(String base64) {
        return new String(Base64.decodeBase64(base64.getBytes()));
    }
}
