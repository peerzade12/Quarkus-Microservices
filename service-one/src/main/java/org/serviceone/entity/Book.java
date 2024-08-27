package org.serviceone.entity;

public class Book {
    private int id;
    private String bookName;
    private String description;
    private String author;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Book(int id, String bookName, String description, String author, double price) {
        this.id = id;
        this.bookName = bookName;
        this.description = description;
        this.author = author;
        this.price = price;
    }

    public Book() {
    }
}
