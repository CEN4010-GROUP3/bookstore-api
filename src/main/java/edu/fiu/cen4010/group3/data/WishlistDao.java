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
Get list of books in user Id
TODO:
Add book to wishlist
Remove book from wishlist (and add to cart)
 */

public class WishlistDao {

    // Creates a blank wishlist under a userId in the database.
    private void unchecked_create(String name, int userId) {
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
    private boolean check(int userId) {
        int wishlistAmount = 0;
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "SELECT COUNT(*) FROM wishlist WHERE user_id = ?");
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                wishlistAmount = resultSet.getInt(1);
            }

            stmt.close();
            c.close();
        } catch (PSQLException s) {
            System.err.println(s.getClass().getName() + ": " + s.getMessage());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        // Return true if less than three wishlists.
        // System.out.println("UserId " + userId + " has " + wishlistAmount + " therefore, creation: " + (wishlistAmount < 3));
        return (wishlistAmount < 3);
    }

    // Checks before creating wishlist.
    public void create(String name, int userId) {
        if (check(userId)) {
            unchecked_create(name, userId);
        }
    }

    // Get all of the wishlists under a userID
    public ArrayList<Wishlist> getUserWishlists(int userId) {

        HashMap<Integer, Wishlist> wishlists = new HashMap<>();

        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement("SELECT name, id FROM wishlist WHERE user_id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
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

    // Get a list of books in a wishlist
    public Wishlist getWishlistById(int wishlistId) {

        HashMap<String, Book> books = new HashMap<>();

        Connection c = null;
        PreparedStatement stmt = null;
        Wishlist wishlist = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement("SELECT isbn, name, description, author, genre, publisher, year_published, price, copies_sold " +
                    "FROM books JOIN wishlist_contents ON books.isbn = wishlist_contents.book_isbn WHERE wishlist_id = ?");
            stmt.setInt(1, wishlistId);
            // Query statement is modified, but other than that, this is basically getBooks
            ResultSet rs = stmt.executeQuery();
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
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement("SELECT name, user_id FROM wishlist WHERE id = ?");
            stmt.setInt(1, wishlistId);
            // Query statement is modified, but other than that, this is basically getBooks
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                wishlist = new Wishlist(rs.getString("name"), rs.getInt("user_id"), wishlistId);
                wishlist.setContents(new ArrayList<>(books.values()));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return wishlist;
    }

    public void addToWishlist(String isbn, int wishlistId) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "INSERT INTO wishlist_contents (book_isbn, wishlist_id) VALUES (?, ?)");
            stmt.setString(1, isbn);
            stmt.setInt(2, wishlistId);
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (PSQLException s) {
            System.err.println(s.getClass().getName() + ": " + s.getMessage());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void deleteFromWishlist(String isbn, int wishlistId) {
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement("DELETE FROM wishlist_contents WHERE book_isbn = ? AND wishlist_id = ?");
            stmt.setString(1, isbn);
            stmt.setInt(2, wishlistId);
            stmt.executeUpdate();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
