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
        int length = 0;
        long timestamp = 0;

        /* login: "\w" */
        Matcher loginMatcher = Pattern.compile("login: \"[\\w]+\"").matcher(json);
        if (loginMatcher.find()) {
            if (json.substring(loginMatcher.start() + 8, loginMatcher.end() - 1).length() > 0)
                login = json.substring(loginMatcher.start() + 8,
                        loginMatcher.end() - 1);
        }

        /* length: \d */
        Matcher textLengthMatcher = Pattern.compile("length: \\d+").matcher(json);
        if (textLengthMatcher.find()) {
            if (json.substring(textLengthMatcher.start() + 8, textLengthMatcher.end()).length() > 0)
                length = Integer.parseInt(json.substring(textLengthMatcher.start() + 8,
                        textLengthMatcher.end()));
        }

        /* text: (.*){length} */
        Matcher textMatcher = Pattern.compile("text: \"(.){" + length + "}").matcher(json);
        if (textMatcher.find()) {
            if (json.substring(textMatcher.start() + 7, textMatcher.end()).length() > 0)
                text = json.substring(textMatcher.start() + 7,
                        textMatcher.end());
        }

        /* timestamp: \d */
        String afterText = json.substring(textMatcher.end());
        Matcher dateMatcher = Pattern.compile("timestamp: [\\d]+").matcher(afterText);
        if (dateMatcher.find()) {
            if (afterText.substring(dateMatcher.start() + 11, dateMatcher.end() - 1).length() > 0)
                timestamp = Long.parseLong(afterText.substring(dateMatcher.start() + 11,
                        dateMatcher.end()));
        }

        /*
        System.out.println("login: " + login);
        System.out.println("length: " + length);
        System.out.println("text: " + text);
        System.out.println("timestamp: " + timestamp);
        */

        return new Message(login, text, timestamp);
    }

    /**
     * Decode the Json array to ArrayList with Message objects.
     *
     * @param json A Json array containing a messages.
     * @return An ArrayList object containing Message objects.
     */
    public static ArrayList<Message> convertAllToMessages(String json) {
        ArrayList<Message> messages = new ArrayList<>();
        String[] rows = Pattern.compile("},").split(json);

        for (String row : rows) {
            row = row.trim();
            if (row.startsWith("["))
                row = row.substring(1);
            if (row.endsWith("]"))
                row = row.substring(0, row.length() - 1);
            //System.out.println(row);
            messages.add(convertToMessage(row));
        }
        return messages;
    }
}
