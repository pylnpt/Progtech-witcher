package project.progtechwitcher.Logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Azért egy külön osztály, hogy ne kelljen mindenhova importálni a felső 2 osztályt + létrehozni egy loggert.
//https://www.youtube.com/watch?v=uco-a7c6U8w&t=47s
public class Log {
    private static Logger log = null;

    public static void Warning(String className, String message)
    {
        log = LogManager.getLogger(className);
        log.warn(message);
    }
    public static void Info(Class className, String message)
    {
        log = LogManager.getLogger(className);
        log.info(message);
    }
    public static void Error(Class className, String message)
    {
        log = LogManager.getLogger(className);
        log.error(message);
    }
    public static void Debug(Class className, String message)
    {
        log = LogManager.getLogger(className);
        log.debug(message);
    }
    public static void Fatal(Class className, String message)
    {
        log = LogManager.getLogger(className);
        log.fatal(message);
    }

}
