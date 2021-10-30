package edu.co.icesi.firestoreejemplo.services;

public class FMCMessage<T> {

    private String to;
    private T data;

    public FMCMessage(String to, T data) {
        this.to = to;
        this.data = data;
    }

    public FMCMessage() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
