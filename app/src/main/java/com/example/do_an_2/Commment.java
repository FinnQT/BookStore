package com.example.do_an_2;

public class Commment {
    String uid;
    String text;

    public Commment() {
    }

    public Commment(String text,String uid ) {
        this.uid = uid;
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Commment{" +
                "uid='" + uid + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
