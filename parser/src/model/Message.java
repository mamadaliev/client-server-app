package model;

import java.io.Serializable;
import java.time.Instant;

public class Message implements Serializable {

    private static long serialVersionUID = -7829019828079546536L;

    private long id;
    private String login;
    private String text;
    private int length;
    private long timestamp;

    public Message(String login, String text) {
        this.login = login;
        this.text = text;
        this.length = text.length();
        this.timestamp = Instant.now().toEpochMilli();
    }

    public Message(String login, String text, long timestamp) {
        this.login = login;
        this.text = text;
        this.length = text.length();
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getLength() {
        return this.length;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{id: " + this.id + ", " +
                "login: \"" + this.login + "\", " +
                "text: \"" + this.text + "\", " +
                "length: " + this.length + ", " +
                "timestamp: " + this.timestamp + "}";
    }
}
