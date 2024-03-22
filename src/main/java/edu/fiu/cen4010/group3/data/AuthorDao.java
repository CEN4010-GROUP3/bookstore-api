package edu.fiu.cen4010.group3.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import edu.fiu.cen4010.group3.model.Author;
import edu.fiu.cen4010.group3.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;


public class AuthorDao {

    public void save(Author author) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "INSERT INTO authors (first_name, last_name, publisher, biography) VALUES (?, ?, ?, ?)");
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());    
            stmt.setString(3, author.getPublisher());
            stmt.setString(4, author.getBiography());
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (PSQLException s) {
            System.err.println(s.getClass().getName() + ": " + s.getMessage());

            if (s.getMessage().contains("duplicate key value")) {
                throw new IllegalArgumentException("error:Author already exists");
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName()
                    + ": " + e.getMessage());
        }
    }

    public void updateAuthor(Author author) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "UPDATE authors SET first_name = ?, last_name = ?, publisher = ?, biography = ? WHERE first_name = ? AND last_name = ?");
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setString(3, author.getPublisher());
            stmt.setString(4, author.getBiography());
            stmt.setString(5, author.getFirstName());
            stmt.setString(6, author.getLastName());
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName()
                    + ": " + e.getMessage());
        }
    }

    public void deleteAuthor(Author author) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "DELETE FROM authors WHERE first_name = ? AND last_name = ?");
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName()
                    + ": " + e.getMessage());
        }
    }

    public List<Author> getAuthors() {

        HashMap<String, Author> authors = new HashMap<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM authors");
            while (rs.next()) {
                Author author = new Author();
                author.setFirstName(rs.getString("first_name"));
                author.setLastName(rs.getString("last_name"));
                author.setPublisher(rs.getString("publisher"));
                author.setBiography(rs.getString("biography"));
                authors.put(author.getFullName(), author);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName()
                    + ": " + e.getMessage());
        }
        return new ArrayList<>(authors.values());
    }

}
