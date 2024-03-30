package edu.fiu.cen4010.group3.model;

public class Rating {
    private String bookID;
    private Double rating;
    private String userID;

    public Rating() {
    }
    public Rating(String bookID, Double rating, String userID) {
        this.bookID = bookID;
        this.rating = rating;
        this.userID = userID;
    }
    public String getBookID() {
        return bookID;
    }
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "userID='" + userID + '\'' +
                ", bookID='" + bookID + '\'' +
                ", rating=" + rating +
                '}';
    }
}
