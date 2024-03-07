package edu.fiu.cen4010.group3.data;

import edu.fiu.cen4010.group3.model.Book;
import edu.fiu.cen4010.group3.model.Wishlist;
import edu.fiu.cen4010.group3.utils.DBUtils;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/*
DONE:
Create wishlist
Only three wishlist per user
Get user wishlists
TODO:
Get list of books in user Id
Add book to wishlist
Remove book from wishlist (and add to cart)
 */
public class WishlistDao {

    // Creates a blank wishlist under a userId in the database.
    public void unchecked_create(String name, int userId) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "INSERT INTO wishlist (name, user_id) VALUES (?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (PSQLException s) {
            System.err.println(s.getClass().getName() + ": " + s.getMessage());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    // Check if the user already has three wishlists.
    public boolean check(int userId) {
        int wishlistAmount = 0;
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "SELECT COUNT(*) FROM wishlist WHERE user_id = ?");
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            stmt.close();
            c.close();

            if (resultSet.next()) {
                wishlistAmount = resultSet.getInt(1);
            }
        } catch (PSQLException s) {
            System.err.println(s.getClass().getName() + ": " + s.getMessage());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        // Return true if less than three wishlists.
        return (wishlistAmount < 3);
    }

    // Checks before creating wishlist.
    public void create(String name, int userId) {
        if (check(userId)) {
            unchecked_create(name, userId);
        }
    }

    // Get all of the wishlists under a userID
    public ArrayList<Wishlist> getWishlists(int userId) {

        HashMap<Integer, Wishlist> wishlists = new HashMap<>();

        Connection c = null;
        Statement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT name, id FROM wishlist WHERE user_id = ?");
            while (rs.next()) {
                Wishlist wishlist = new Wishlist(rs.getString("name"), userId, rs.getInt("id"));
                wishlists.put(wishlist.getWishlistId(), wishlist);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return new ArrayList<>(wishlists.values());
    }
/*
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
*/
}
