package org.servicetwo.entity;

public class Book {
    int id;
    String bookName;
    String description;

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

    public Book() {
    }

    public Book(int id, String bookName, String description) {
        this.id = id;
        this.bookName = bookName;
        this.description = description;
    }
}
