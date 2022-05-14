package project.progtechwitcher.Logging;
import org.apache.log4j.*;

import java.io.File;

//Azért egy külön osztály, hogy ne kelljen mindenhova importálni a felső 2 osztályt + létrehozni egy loggert.
//https://www.youtube.com/watch?v=uco-a7c6U8w&t=47s
public class Log {
    private static final Logger log = LogManager.getRootLogger();
    private static final ConsoleAppender appender = new ConsoleAppender();
    private static final FileAppender fileAppender = new FileAppender();
    private static String filePath = ".\\src\\main\\java\\project\\progtechwitcher\\Logging\\LogFiles";
    private static String fileName = "log.txt";

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
                Info("Folders and files are already created");
            }
            else
            {
                Error("Creating folders and files failed");
            }
        }
    }

    private static void SetAppender()
    {
        appender.setLayout(new PatternLayout("%d [%p] [%C{1}]: %m%n"));
        fileAppender.setLayout(new PatternLayout("%d [%p] [%C{1}]: %m%n"));
        appender.setThreshold(Level.ALL);
        fileAppender.setThreshold(Level.ALL);
        appender.activateOptions();
        fileAppender.setFile("log.txt");
        fileAppender.setImmediateFlush(true);
        fileAppender.activateOptions();
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().addAppender(fileAppender);
    }
    public static void Warning(String message)
    {
        SetAppender();
        log.warn(message);
    }
    public static void Info(String message)
    {
        SetAppender();
        log.info(message);
    }
    public static void Error(String message)
    {
        SetAppender();
        log.error(message);
    }
    public static void Debug(String message)
    {
        SetAppender();
        log.debug(message);
    }
    public static void Fatal(String message)
    {
        SetAppender();
        log.fatal(message);
    }

}
