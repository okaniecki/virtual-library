package com.example;

import java.util.Comparator;

public class Book{
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


    public static class OrderByName implements Comparator<Book> {

        @Override
        public int compare(Book o1, Book o2) {
            return o1.name.compareTo(o2.name);
        }
    }

}
