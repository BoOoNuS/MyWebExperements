package SoketTests;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Стас on 28.06.2016.
 */
public class ServerWriter {

    public static final String WRITE_KEY = "WRITE: ";

    public boolean writeFile(String text, String fullPath) {
        try (FileWriter writer = new FileWriter(fullPath)) {
            writer.write(text.split(WRITE_KEY)[1].trim());
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
