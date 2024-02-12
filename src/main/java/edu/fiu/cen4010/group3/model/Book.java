package edu.fiu.cen4010.group3.model;

public class Book {
    private String isbn;
    private String name;
    private String description;
    private String author;
    private String genre;
    private String publisher;
    private int yearPublished;
    private int copiesSold;
    private double price;

    public Book() {
    }

    public Book(String isbn, String name, String description, String author, String genre, String publisher,
            int yearPublished, double price, int copiesSold) {
        this.isbn = isbn;
        this.name = name;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.yearPublished = yearPublished;
        this.price = price;
        this.copiesSold = copiesSold;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public int getCopiesSold() {
        return copiesSold;
    }

    public double getPrice() {
        return price;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void sellCopy() {
        this.copiesSold++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Book))
            return false;

        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
