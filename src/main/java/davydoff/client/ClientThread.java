package davydoff.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientThread.class);

    private final Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while (true) {

                if (!socket.isClosed()) {

                    String response = input.readLine();
                    LOGGER.info(response);

                } else {
                    break;
                }

            }

        } catch (IOException ex) {
            LOGGER.error("Occured error in ClientThread: " + ex);

        }

    }
}
