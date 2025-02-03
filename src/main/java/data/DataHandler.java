package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DataHandler {
    public static String saveDelimeter = "|";

    public static void writeFile(Path filePath, List<String> payLoad, boolean isAppend) throws IOException {
        // file does not exist
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            Files.writeString(filePath, String.join("\n", payLoad), StandardOpenOption.APPEND);
            return;
        }

        //file exists
        if (isAppend) {
            Files.writeString(filePath, String.join("\n", payLoad), StandardOpenOption.APPEND);
        }
        else {
            Files.writeString(filePath, String.join("\n", payLoad), StandardOpenOption.TRUNCATE_EXISTING);
        }
    };

    public static List<String> readFile(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Files.writeString(filePath, "", StandardOpenOption.CREATE);
        }
        return Files.readAllLines(filePath);
    }
}
