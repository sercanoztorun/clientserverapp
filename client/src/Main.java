import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static String MONITOR_DIRECTORY_PATH;
    public static String KEY_FILTERING_PATTERN;
    public static String SERVER_ADDRESS;
    public static Integer SERVER_PORT;

    public static void main(String[] args) {
        try {
            String configFilePath = args[0];
            FileInputStream configInput = new FileInputStream(configFilePath);
            Properties configProperties = new Properties();
            configProperties.load(configInput);

            MONITOR_DIRECTORY_PATH = configProperties.getProperty("MONITOR_DIRECTORY_PATH");
            if (MONITOR_DIRECTORY_PATH == null || MONITOR_DIRECTORY_PATH.isEmpty()) {
                Logger.log("MONITOR_DIRECTORY_PATH can not be null or empty");
                System.exit(0);
            }
            KEY_FILTERING_PATTERN = configProperties.getProperty("KEY_FILTERING_PATTERN");
            if (KEY_FILTERING_PATTERN == null || KEY_FILTERING_PATTERN.isEmpty()) {
                Logger.log("KEY_FILTERING_PATTERN can not be null or empty");
                System.exit(0);
            }
            SERVER_ADDRESS = configProperties.getProperty("SERVER_ADDRESS");
            if (SERVER_ADDRESS == null || SERVER_ADDRESS.isEmpty()) {
                Logger.log("SERVER_ADDRESS can not be null or empty");
                System.exit(0);
            }
            try{
                SERVER_PORT = Integer.valueOf(configProperties.getProperty("SERVER_PORT"));
            }catch (NumberFormatException e) {
                Logger.log("SERVER_PORT should be an Integer value");
                System.exit(0);
            }

            FileScanner fileScanner = new FileScanner();
            fileScanner.scanFiles();

        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

}