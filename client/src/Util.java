import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static boolean checkValidKey(String key){
        Pattern pattern = Pattern.compile(Main.KEY_FILTERING_PATTERN);
        Matcher matcher = pattern.matcher(key);
        return matcher.matches();
    }
    public static void deleteFiles(Path filePath){
        try {
            Files.deleteIfExists(filePath);
        }
        catch (NoSuchFileException e) {
            Logger.log("Deletion failed because no such file/directory exists. " + filePath);
        }
        catch (DirectoryNotEmptyException e) {
            Logger.log("Deletion failed because directory is not empty. " + filePath);
        }
        catch (IOException e) {
            Logger.log("Deletion failed because of invalid permissions. " + filePath);
        }
        Logger.log("Deletion successful. " + filePath);
    }
}
