package com.example.do_an_2;

public class ReviewBook {
    private String id;
    private String text;

    public ReviewBook() {
    }

    public ReviewBook(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReviewBook{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
