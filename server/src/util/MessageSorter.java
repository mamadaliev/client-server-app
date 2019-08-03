package util;

import model.Message;

import java.util.ArrayList;

public class MessageSorter {

    public static ArrayList<Message> getMessagesByLogin(ArrayList<Message> messages, String login) {
        ArrayList<Message> result = new ArrayList<>();

        for (Message message : messages) {
            if (message.getLogin().equals(login)) {
                result.add(message);
            }
        }
        return result;
    }
}
