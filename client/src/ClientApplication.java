import model.Message;
import util.MessageConverter;
import util.MessageSorter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientApplication {

    private final static String HOSTNAME  = "127.0.0.1";
    private final static int MESSAGE_PORT = 45777;
    private final static int UPLOADS_PORT = 45778;

    private static Scanner in = new Scanner(System.in);
    private static String login = "";

    // main
    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter login:");
            login = in.nextLine();

            if (login.matches("[a-z]{1,4}")) {
                menu();
            } else if (login.equals("\\q")) {
                System.out.println("Exiting the program...");
                break;
            } else {
                System.err.println("Error: login length must be from 1 to 4, " +
                        "only latin lowercase letters are allowed.");
            }
        }
    }

    // menu
    private static void menu() {
        System.out.println(
                "----------[ " + login + " ]----------\n" +
                        "1. Enter a new message\n" +
                        "2. Show a list of my messages\n" +
                        "3. Delete your message (by id)\n" +
                        "4. Exit the session");

        while (true) {
            String command = in.nextLine();
            try {
                int number = Integer.valueOf(command);

                if (number == 4) {
                    System.out.println("Exiting the session...");
                    break;
                }

                switch (number) {
                    case 1:
                        System.out.println("Enter your message:");
                        try {
                            writeMessage();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 2:
                        try {
                            showMessages();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    case 3:
                        System.out.println(3);
                        break;
                    default:
                        System.err.println("There is no such command, try again.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("There is no such command, try again.");
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeMessage() throws IOException {
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();

        try (Socket socket = new Socket(HOSTNAME, MESSAGE_PORT)) {
            try (PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // send to server
                toServer.println(new Message(login, message));

                // response from server
                String line = fromServer.readLine();
                System.out.println(line);
            }
        }
    }

    // MessageSorter.getMessagesByLogin(messages, login);
    private static void showMessages() throws IOException {
        try (Socket socket = new Socket(HOSTNAME, MESSAGE_PORT)) {
            try (PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // send to server
                toServer.println("/messages:" + login);

                // response from server
                String line = fromServer.readLine();

//                System.out.println(line);

                // All messages
                ArrayList<Message> messages = MessageSorter.getMessagesByLogin(
                        MessageConverter.convertAllToMessages(line), login);

//                System.out.println(messages.toString());

                System.out.println("----------[ 2. My messages ]----------");
                for (int i = 0; i < messages.size(); i++) {
                    System.out.println(i + ". " + messages.get(i).getLogin() + ": " + messages.get(i).getText());
                }
            }
        }
    }
}
