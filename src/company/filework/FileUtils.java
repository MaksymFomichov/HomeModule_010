package company.filework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {
    }

    public static void writeToFile(String json, String path) throws IOException {
        System.out.println("\nФайл сохранён: " + path);
        FileWriter writer = new FileWriter(path);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    public static String readFromFile(String path) throws IOException {
        System.out.println("\nФайл загружен: " + path);
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String json = reader.readLine();
        return json;
    }
}
