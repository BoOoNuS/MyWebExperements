import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Стас on 16.05.2016.
 */
public class Main {

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", 8888)) {
            socket.getOutputStream().write("READ: InputData\\127.0.0.1\\50619".getBytes());
            byte buf[] = new byte[64 * 1024];
            String data = new String(buf, 0, socket.getInputStream().read(buf));
            System.out.println(data);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
