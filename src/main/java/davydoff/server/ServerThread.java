package davydoff.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerThread.class);

    private final Socket socket;

    private final List<ServerThread> serverThreads;

    PrintWriter output;

    public ServerThread(Socket socket, List<ServerThread> serverThreads) {
        this.socket = socket;
        this.serverThreads = serverThreads;
    }

    @Override
    public void run() {

        try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            output = new PrintWriter(socket.getOutputStream(), true);

            while (true) {

                String outputString = input.readLine();

                if (outputString == null || outputString.equals("/exit")) {
                    break;
                }

                printStringToAllClients(outputString);

                LOGGER.info("Server received: {}", outputString);
            }

        } catch (IOException ex) {

            LOGGER.error("Occured error in ServerThread: " + ex);

        }
    }

    void printStringToAllClients(String string) {
        for (ServerThread sh : serverThreads) {
            sh.output.println(string);
        }
    }

}
