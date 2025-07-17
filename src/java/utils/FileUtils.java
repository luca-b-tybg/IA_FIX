package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {

    public static String loadFile(File file) throws IOException {

        List<String> lines = Files.readAllLines(file.toPath());
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(line);
        }


        return builder.toString();
    }

    public static void saveFile(File file, String content) throws IOException {

        FileWriter fw = new FileWriter(file);
        BufferedWriter bf = new BufferedWriter(fw);
        bf.write(content);
        bf.close();
        fw.close();
    }
    public static URL getResourceUrl(String resourceName) {
        return FileUtils.class.getResource("/" + resourceName);
    }


}
