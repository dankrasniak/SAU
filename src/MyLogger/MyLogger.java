package MyLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MyLogger {

    public static void Log(String loggerName, String message) {
        if (instance.loggers.containsKey(loggerName)) {
//            instance.loggers.get(loggerName).info(message);
            Logger.getLogger(loggerName).info(message);
        }
        else {
            // Prepare Logger
            instance.loggers.put(loggerName,Logger.getLogger(loggerName));
            try {
                FileHandler fh = new FileHandler(loggerName + ".log");
                instance.instance.loggers.get(loggerName).addHandler(fh);
                fh.setFormatter(new Formatter() {
                    @Override
                    public String format(LogRecord record) {
                        return record.getMessage() + "\n";
                    }
                });
                instance.instance.loggers.get(loggerName).setUseParentHandlers(false);
            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static MyLogger instance = new MyLogger();
    private Map<String, Logger> loggers = new HashMap<>();
}
