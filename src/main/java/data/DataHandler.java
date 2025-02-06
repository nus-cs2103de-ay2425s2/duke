package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataHandler {
    public static String saveDelimiter = "|";
    public static Path programRoot = Paths.get("").toAbsolutePath();
    public static DateTimeFormatter dateSaveFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static DateTimeFormatter dateTimeSaveFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Method to write data to a file
     * Precondition: File already exists in the system
     *
     * @param filePath Path object of the file
     * @param payLoad  List of strings to write to the file, each item will be separated by \n
     * @param isAppend Flag to append data instead of rewriting the file
     * @throws IOException IO fails
     */
    public static void writeFile(Path filePath, List<String> payLoad, boolean isAppend) throws IOException {
        //file exists
        if (isAppend) {
            Files.writeString(filePath, String.join("\n", payLoad), StandardOpenOption.APPEND);
        } else {
            Files.writeString(filePath, String.join("\n", payLoad), StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    public static List<String> readFile(Path filePath) throws IOException {
        if (!Files.exists(programRoot.resolve("data"))) {
            Files.createDirectory(programRoot.resolve("data"));
        }

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        return Files.readAllLines(filePath);
    }
}
