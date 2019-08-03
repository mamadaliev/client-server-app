package util;

import model.Message;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert Json string to Message object and vice versa.
 */
public class MessageConverter {

    /**
     * Encode Message object to Json string.
     *
     * @param message Message object.
     *                For example,
     *                <code>new Message("user", "message", 1500000000000)</code>
     *                <code>new Message("user", "message")</code>
     *
     * @return A Json string containing a message.
     */
    public static String contvertToJson(Message message) {
        return message.toString();
    }

    /**
     * Encode Message objects to Json string.
     *
     * @param messages Message objects.
     * @return A Json string containing messages.
     */
    public static String convertAllToJson(ArrayList<Message> messages) {
        return messages.toString();
    }

    /**
     * Decode the Json string to Message object.
     *
     * @param json A Json string containing a message.
     *             For example, <code>{login: "user", text: "message", timestamp: 1500000000000}</code>
     *
     * @return Message object.
     */
    public static Message convertToMessage(String json) {
        String login = "";
        String text = "";
        long date = 0;
        Matcher match = Pattern.compile("\\w+:\\s[\"]?[\\w\\d\\s:]*[\"]?").matcher(json);

        while (match.find()) {
            String row = json.substring(match.start(), match.end());
            Matcher name = Pattern.compile("\\w+:").matcher(row);
            Matcher value = Pattern.compile(": [\"]?.*").matcher(row);

            if (name.find() && value.find()) {
                String n = row.substring(name.start(), name.end() - 1);
                String v = row.substring(value.start(), value.end());

                if (v.endsWith("\"")) {
                    if (row.substring(value.start() + 3, value.end()).length() > 0) {
                        v = row.substring(value.start() + 3, value.end() - 1);
                    } else {
                        v = row.substring(value.start() + 3, value.end());
                    }
                } else {
                    v = row.substring(value.start() + 2, value.end());
                }

                switch (n) {
                    case "login":
                        login = v;
                        break;
                    case "text":
                        text = v;
                        break;
                    case "timestamp":
                        date = Long.parseLong(v);
                        break;
                    default:
                        throw new RuntimeException();
                }
            }
        }
        return new Message(login, text, date);
    }

    /**
     * Decode the Json array to ArrayList with Message objects.
     *
     * @param json A Json array containing a messages.
     * @return An ArrayList object containing Message objects.
     */
    public static ArrayList<Message> convertAllToMessages(String json) {
        ArrayList<Message> messages = new ArrayList<>();
        Matcher match = Pattern.compile("\\{[\\w\\d:\",\\s]+}").matcher(json);

        while (match.find()) {
            String row = json.substring(match.start(), match.end());
            messages.add(convertToMessage(row));
        }
        return messages;
    }
}
