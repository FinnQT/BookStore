package com.example.do_an_2;

import java.io.Serializable;

public class Book_demo1 implements Serializable {
    int amount;
    String author;
    boolean check;
    String id;
    String image;
    String name;
    float price;
    String type;


    public Book_demo1() {
    }

    public Book_demo1(int amount, String author, boolean check, String id, String image, String name, float price, String type) {
        this.amount = amount;
        this.author = author;
        this.check = check;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Book_demo1{" +
                "amount=" + amount +
                ", author='" + author + '\'' +
                ", check=" + check +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
