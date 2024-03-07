package edu.fiu.cen4010.group3.model;

import java.util.ArrayList;

public class Wishlist {
    private String name;
    private int wishlistId;
    private int userId;
    private ArrayList<Book> contents;

    public Wishlist() {
    }

    // Constructor without contents.
    public Wishlist(String name, int userId, int wishlistId) {
        this.name = name;
        this.userId = userId;
        this.wishlistId = wishlistId;
        this.contents = new ArrayList<Book>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<Book> getContents() {
        return contents;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContents(ArrayList<Book> contents) {
        this.contents = contents;
    }
}