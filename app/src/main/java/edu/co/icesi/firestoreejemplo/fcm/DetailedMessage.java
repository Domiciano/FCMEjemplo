package edu.co.icesi.firestoreejemplo.fcm;

import edu.co.icesi.firestoreejemplo.model.Message;
import edu.co.icesi.firestoreejemplo.model.User;

public class DetailedMessage {
    private Message message;
    private User fromUser;
    private User toUser;

    public DetailedMessage(Message message, User fromUser, User toUser) {
        this.message = message;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public DetailedMessage() {
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }
}
