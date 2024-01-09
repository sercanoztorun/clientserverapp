import java.io.IOException;
import java.nio.file.*;

public class FileScanner {
    public void scanFiles(){
        try{
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(Main.MONITOR_DIRECTORY_PATH);

            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE);
            Logger.log("Program started and monitor properties files in " + Main.MONITOR_DIRECTORY_PATH);
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    if(event.context().toString().endsWith("properties")){
                        Thread sender = new DataSender(event.context().toString());
                        sender.start();
                    }
                }
                key.reset();
            }
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
