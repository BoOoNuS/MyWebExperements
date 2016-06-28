package SoketTests;

import java.io.File;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * Created by Стас on 28.06.2016.
 */
public final class ServerHelper {

    public static final String DIRECTORY_SEPARATOR = "\\";
    public static final String ROOT_FILE = "InputData";
    public static final String WRITE_KEY = "WRITE: ";
    public static final String READ_KEY = "READ: ";
    public static final String FILES_ARE_LOCATED_AT_MESSAGE = "Files are located at - ";
    public static final String INCORRECT_USER_A_MESSAGE_EXCEPTION = "INCORRECT USER A MESSAGE";
    public static final String EMPTY_FILE_EXCEPTION = "EMPTY FILE";

    private Socket socket;

    public ServerHelper(Socket socket) {
        this.socket = socket;
    }

     String getOutputString(byte[] buf) throws ExecutionException, InterruptedException {
        String fullFilePatch = ROOT_FILE + DIRECTORY_SEPARATOR
                + socket.getInetAddress().getHostAddress()
                + DIRECTORY_SEPARATOR;

        if (isThatKey(buf, WRITE_KEY)) {
            if(!new ServerWriter().writeFile(readMessage(buf), fullFilePatch + socket.getPort())){
                throw new IllegalArgumentException(EMPTY_FILE_EXCEPTION);
            }
            return FILES_ARE_LOCATED_AT_MESSAGE + fullFilePatch + socket.getPort();
        }
        if (isThatKey(buf, READ_KEY)) {
            return new ServerReader().makeLine(readMessage(buf).split(READ_KEY)[1].trim());
        }
        throw new IllegalArgumentException(INCORRECT_USER_A_MESSAGE_EXCEPTION);
    }

    void makeDirectory() {
        String pathToFile = ROOT_FILE + DIRECTORY_SEPARATOR
                + socket.getInetAddress().getHostAddress();
        File patch = new File(pathToFile);
        if (!patch.exists()) {
            patch.mkdirs();
        }
    }

    private String readMessage(byte[] buf){
        String answer = "";
        for (byte b : buf) {
            answer += (char) b;
        }
        return answer;
    }

    private boolean isThatKey(byte[] buf, String sampleKey) {
        String key = readMessage(buf);
        if (key == null) {
            return false;
        }
        if (Pattern.compile(sampleKey).matcher(key).find()) {
            return true;
        }
        return false;
    }

}
