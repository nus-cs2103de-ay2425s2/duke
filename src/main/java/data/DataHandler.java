package data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DataHandler {
    public static void writeFile(Path filePath, List<String> payLoad, boolean isAppend) throws IOException {
        // file does not exist
        if (!Files.exists(filePath)) {
            Files.writeString(filePath, String.join("\n", payLoad), StandardOpenOption.CREATE_NEW);
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
            throw new FileNotFoundException("File does not exists");
        }
        return Files.readAllLines(filePath);
    }
}
