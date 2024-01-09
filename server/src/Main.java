import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Main {
    public static String WRITE_DIRECTORY_PATH;
    public static Integer SERVER_PORT;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            String configFilePath = args[0];
            FileInputStream configInput = new FileInputStream(configFilePath);
            Properties configProperties = new Properties();
            configProperties.load(configInput);

            WRITE_DIRECTORY_PATH = configProperties.getProperty("WRITE_DIRECTORY_PATH");
            if (WRITE_DIRECTORY_PATH == null || WRITE_DIRECTORY_PATH.isEmpty()) {
                Logger.log("WRITE_DIRECTORY_PATH can not be null or empty");
                System.exit(0);
            }
            try {
                SERVER_PORT = Integer.valueOf(configProperties.getProperty("SERVER_PORT"));
            } catch (NumberFormatException e) {
                Logger.log("SERVER_PORT should be an Integer value");
                System.exit(0);
            }

            serverSocket = new ServerSocket(SERVER_PORT);
            Logger.log("Server Listening on port " + SERVER_PORT);

            while (true) {
                Socket connectionSocket = serverSocket.accept();
                Logger.log("Server socket accepted " + connectionSocket.getInetAddress());
                Thread reader = new DataReader(connectionSocket);
                reader.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}