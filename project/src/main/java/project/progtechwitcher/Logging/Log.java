package project.progtechwitcher.Logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

//Azért egy külön osztály, hogy ne kelljen mindenhova importálni a felső 2 osztályt + létrehozni egy loggert.
//https://www.youtube.com/watch?v=uco-a7c6U8w&t=47s
public class Log {
    private static Logger log = null;
//    private static final ConsoleAppender appender = new ConsoleAppender();
//    private static final FileAppender fileAppender = new FileAppender();
    private static String filePath = ".\\src\\main\\java\\project\\progtechwitcher\\Logging\\LogFiles";
    private static String fileName = "log.log";

    public static void CreateLogFile(){
        boolean folder=false, file=false;
        try {
            File f = new File(filePath);
            folder = f.mkdir();

            f = new File(filePath + "\\" + fileName);
            file = f.createNewFile();
        }
        catch(Exception e)
        {
            if(!folder && !file)
            {
                Info(Log.class,"Folders and files are already created");
            }
            else
            {
                Error(Log.class,"Creating folders and files failed");
            }
        }
    }

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
