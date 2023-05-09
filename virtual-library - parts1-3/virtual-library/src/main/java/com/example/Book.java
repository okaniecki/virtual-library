package com.example;

public class Book {
    private int bookID;
    private String name;

    public Book(int bookID, String name) {
        this.bookID = bookID;
        this.name = name;
    }

    //get/set methods for book object
    public int getBookID() {
        return this.bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return this.name;
    }

    public void setBookName(String name) {
        this.name = name;
    }

    public String toString() {
        return name + " " + bookID + "\n";
    }
}
