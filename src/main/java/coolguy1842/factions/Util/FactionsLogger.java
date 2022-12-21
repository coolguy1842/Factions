package coolguy1842.factions.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactionsLogger {
    private static final Logger parent = LoggerFactory.getLogger("Factions");

    public static void info(String message) {
        parent.info(message);
    }

    public static void warning(String message) {
        parent.warn(message);
    }

    public static void severe(String message) {
        parent.error(message);
    }
}