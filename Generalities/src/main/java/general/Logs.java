package general;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class Logs {

    private static Logger logger = null;
    private static FileHandler fh;
    private static SimpleFormatter formatter = new SimpleFormatter();
    private static boolean check = true;


    public static void writeLog(Class c, String method, String message, Level level, boolean isClient) {
        if(check){
            File f = new File("C:\\Logs\\ClientLogs.log");
            File f1 = new File("C:\\Logs\\ServerLogs.log");
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
                f1.getParentFile().mkdirs();
                f1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            check=false;
        }

        try {
            logger = Logger.getLogger("name1");
            if (isClient) {
                fh = new FileHandler("C:\\Logs\\ClientLogs.log");
                logger.addHandler(fh);
                fh.setFormatter(formatter);
            } else {
                fh = new FileHandler("C:\\Logs\\ServerLogs.log");
                logger.addHandler(fh);
                fh.setFormatter(formatter);
            }


            logger.logp(level, c.getName(), method, message);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}