import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.*;
import java.util.LinkedHashMap;
import java.util.List;

public class DataSender extends Thread{
    private final String fileName;
    public DataSender(String fileName){
        this.fileName = fileName;
    }

    public void run()
    {
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        Path filePath =  Paths.get(Main.MONITOR_DIRECTORY_PATH + File.separator + this.fileName);
        //prepare map
        try{
            List<String> allLines = Files.readAllLines(filePath);
            for (String line : allLines) {
                String[] keyValueArr = line.split("=", 2);
                if (keyValueArr.length != 2 || !Util.checkValidKey(keyValueArr[0])) {
                    continue;
                }
                map.put(keyValueArr[0], keyValueArr[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // send data & delete file
        try{
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(Main.SERVER_ADDRESS, Main.SERVER_PORT));
            Logger.log("Socket Connection Successful");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            objectOutputStream.writeUTF(fileName);
            objectOutputStream.writeObject(map);

            //Receive the server's response to delete file
            String serverMessage = (String) objectInputStream.readObject();
            if(serverMessage.equals("OK")){
                Util.deleteFiles(filePath);
            }

        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
