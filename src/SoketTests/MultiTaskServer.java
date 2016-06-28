package SoketTests;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

/**
 * Created by Стас on 28.06.2016.
 */
public class MultiTaskServer implements Runnable {

    public static final String LOCALHOST = "localhost";
    public static final String SERVER_STARTED_MESSAGE = "Server started!";

    private Socket socket;

    public MultiTaskServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ServerHelper helper = new ServerHelper(socket);
        byte[] buf = new byte[64 * 1024];
        helper.makeDirectory();

        try (OutputStream os = socket.getOutputStream()) {
            InputStream is = socket.getInputStream();
            is.read(buf);
            os.write(helper.getOutputString(buf).getBytes());
            is.close();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888, 0, InetAddress.getByName(LOCALHOST))) {
            System.out.println(SERVER_STARTED_MESSAGE);
            while (true) {
                new Thread(new MultiTaskServer(serverSocket.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
