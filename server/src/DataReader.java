import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class DataReader extends Thread {
    private final Socket socket;

    public DataReader(Socket socket) {
        this.socket = socket;
    }
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            String fileName = objectInputStream.readUTF();
            String path = Main.WRITE_DIRECTORY_PATH + File.separator + fileName;

            LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) objectInputStream.readObject();
            FileWriter writer = new FileWriter(path);
            map.forEach((k, v) -> {
                try {
                    writer.write(k + "=" + v + System.lineSeparator());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();

            Logger.log("File saved successfully." + path);
            objectOutputStream.writeObject("OK");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
