package ru.kolpakovkuleshov.helpfulClasses;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Logs {
    private static Logger logger = null;
    public static void writeLog(Class c, String message, Level level){
        logger = Logger.getLogger(c.getName());
        logger.log(level, message);
    }
}
