import model.Message;
import util.MessageConverter;
import util.MessageSorter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server side application.
 *
 * @version 1.0
 * @author Sherzod Mamadaliev
 */
public class ServerApplication {

    private static ArrayList<Message> messages = new ArrayList<>();

    /**
     * Ports of this server.
     * Default port for sending messages is 45777.
     * Default port for sending files if 45778.
     */
    private final static int MESSAGE_PORT = 45777;
    private final static int UPLOADS_PORT = 45778;

    /**
     * Runs the client application.
     *
     * @param args Arguments from Command Line Interface.
     */
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(MESSAGE_PORT);

        while (true) {
            Socket accept = socket.accept();
            //System.out.println("LOG: " + socket.getInetAddress() + ": connected");

            new Thread(() -> {
                try (Socket a = accept) {
                    serve(a);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private static void serve(Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        BufferedReader fromClient = new BufferedReader(new InputStreamReader(is));
        PrintWriter toClient = new PrintWriter(os, true);

        /* get command from clients */
        String command = (String) fromClient.readLine();
        System.out.println(command);

        if (command.matches("/.*")) {
            if (command.matches("/messages/[\\w]+")) {
                String login = command.split("/")[2];
                toClient.println(MessageSorter.getMessagesByLogin(messages, login).toString());
                //System.out.println(MessageSorter.getMessagesByLogin(messages, login).toString());
            }
        } else if (messages.add(MessageConverter.convertToMessage(command))) {
            toClient.println("sended");
        } else {
            toClient.println("Your message has not been sent, try again.");
        }
        System.out.println(messages);
    }
}
