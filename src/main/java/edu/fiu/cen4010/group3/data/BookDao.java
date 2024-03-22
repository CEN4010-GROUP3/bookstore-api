package edu.fiu.cen4010.group3.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import edu.fiu.cen4010.group3.model.Book;
import edu.fiu.cen4010.group3.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class BookDao {

    public void save(String isbn, String name, String description, String author, String genre, String publisher,
            int yearPublished, double price) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "INSERT INTO books (isbn, name, description, author, genre, publisher, year_published, price, copies_sold) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, isbn);
            stmt.setString(2, name);
            stmt.setString(3, description);
            stmt.setString(4, author);
            stmt.setString(5, genre);
            stmt.setString(6, publisher);
            stmt.setInt(7, yearPublished);
            stmt.setDouble(8, price);
            stmt.setInt(9, 0);
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (PSQLException s) {
            System.err.println(s.getClass().getName() + ": " + s.getMessage());

            if (s.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new IllegalArgumentException("error:ISBN already exists");
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public List<Book> getBooks() {

        HashMap<String, Book> books = new HashMap<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT isbn, name, description, author, genre, publisher, year_published, price, copies_sold FROM books;");

            while (rs.next()) {
                Book b = new Book(rs.getString("isbn"), rs.getString("name"), rs.getString("description"),
                        rs.getString("author"), rs.getString("genre"), rs.getString("publisher"),
                        rs.getInt("year_published"), rs.getDouble("price"), rs.getInt("copies_sold"));
                books.put(b.getIsbn(), b);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return new ArrayList<>(books.values());
    }

    public void delete(String isbn) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement("DELETE FROM books WHERE isbn = ?");
            stmt.setString(1, isbn);
            stmt.executeUpdate();

            if (stmt.getUpdateCount() > 0)
                System.out.println(" - SUCCESS");
            else
                System.out.println(" - NOT FOUND");

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }  
    }

    public Book findByIsbn(String isbn) {
        Book b = null;
        Connection c = null;
        PreparedStatement stmt = null;

        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "SELECT isbn, name, description, author, genre, publisher, year_published, price, copies_sold FROM books WHERE isbn = ?");
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                b = new Book(rs.getString("isbn"), rs.getString("name"), rs.getString("description"), rs.getString("author"),
                        rs.getString("genre"), rs.getString("publisher"), rs.getInt("year_published"), rs.getDouble("price"),
                        rs.getInt("copies_sold"));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return b;
    }

    public void update(String isbn, String name, String description, String author, String genre, String publisher,
            int yearPublished, double price) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "UPDATE books SET name = ?, description = ?, author = ?, genre = ?, publisher = ?, year_published = ?, price = ? WHERE isbn = ?");
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, author);
            stmt.setString(4, genre);
            stmt.setString(5, publisher);
            stmt.setInt(6, yearPublished);
            stmt.setDouble(7, price);
            stmt.setString(8, isbn);
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
