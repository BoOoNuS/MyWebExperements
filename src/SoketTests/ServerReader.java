package SoketTests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Стас on 28.06.2016.
 */
public class ServerReader {

    public static final String ENTER = "\n";

    public String makeLine(String fullPath) {
        String answer = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String str;
            while ((str = reader.readLine()) != null) {
                answer += str + ENTER;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
}
