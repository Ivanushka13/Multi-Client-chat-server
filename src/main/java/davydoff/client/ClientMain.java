package davydoff.client;

import java.io.*;
import java.net.*;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClientMain extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);

    private static final int PORT = 5001;

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", PORT);

             BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            String userInput;

            String clientName = "";

            ClientThread clientThread = new ClientThread(socket);

            clientThread.start();

            do {
                if (clientName.equals("")) {

                    System.out.print("Enter your name: ");

                    userInput = consoleIn.readLine();
                    clientName = userInput;

                    output.println("Client " + clientName + " has connected.");

                } else {

                    String message = "(" + clientName + "): ";

                    userInput = consoleIn.readLine();

                    output.println(message + " " + userInput);

                    if (Objects.equals(userInput, "/exit")) {

                        output.println("Client " + clientName + " has disconnected.");
                    }
                }

            } while (!userInput.equals("/exit"));

        } catch (IOException ex) {
            LOGGER.error("Occured error in ClientMain: " + ex);
        }
    }

}
