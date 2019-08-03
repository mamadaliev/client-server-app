package model;

import java.io.Serializable;
import java.time.Instant;

public class Message implements Serializable {

    private static long serialVersionUID = -7829019828079546536L;

    private String login;
    private String text;
    private long timestamp;

    public Message(String login, String text) {
        this.login = login;
        this.text = text;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public Message(String login, String text, long timestamp) {
        this.login = login;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{login: \"" + login + "\", " +
                "text: \"" + text + "\", " +
                "timestamp: " + timestamp + "}";
    }
}
