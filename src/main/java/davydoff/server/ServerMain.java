package davydoff.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ServerMain extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    private static final int PORT = 5001;

    public static void main(String[] args) {

        List<ServerThread> serverThreads = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                Socket socket = serverSocket.accept();

                ServerThread serverThread = new ServerThread(socket, serverThreads);

                serverThreads.add(serverThread);

                serverThread.start();
            }
        } catch (IOException ex) {
            LOGGER.error("Occured error in ServerMain: " + ex);
        }
    }

}
