package com.example.do_an_2;

public class Book {
    private String name;
    private String type;
    private String image;
    private float price;

    public Book() {
    }

    public Book(String image, String name, float price, String type) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}
