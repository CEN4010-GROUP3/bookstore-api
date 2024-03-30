package edu.fiu.cen4010.group3.model;

public class Comments {
    private String userID;
    private String bookID;
    private String comment;

    public Comments() {
    }
    public Comments(String userID, String bookID, String comment) {
        this.userID = userID;
        this.bookID = bookID;
        this.comment = comment;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getBookID() {
        return bookID;
    }
    public void setBookId(String bookId) {
        this.bookID = bookId;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "userID='" + userID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
