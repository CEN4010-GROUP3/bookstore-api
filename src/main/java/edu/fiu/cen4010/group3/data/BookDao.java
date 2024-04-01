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

import java.sql.SQLException;

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

    public List<Book> findByAuthor(String author) {
        HashMap<String, Book> books = new HashMap<>();

        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            stmt = c.prepareStatement(
                    "SELECT isbn, name, description, author, genre, publisher, year_published, price, copies_sold FROM books WHERE upper(author) = upper(?)");
            stmt.setString(1, author);
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

        return new ArrayList<>(books.values());
    }

    
    /**
     * Retrieves a list of books from the database that match a specific genre.
     * This method establishes a connection to the database, prepares a SQL query
     * to select books based on the provided genre, executes the query, and then
     * processes the result set to create a list of {@link Book} objects.
     *
     * @param genre The genre of books to retrieve. This is used to filter books
     *              in the SQL query.
     * @return A list of {@link Book} objects that match the specified genre. If no
     *         books are found or in case of an error, this method returns an empty list.
     *         Note that this method suppresses any SQL or connection-related exceptions
     *         and prints an error message to the standard error stream instead of
     *         propagating exceptions further up the call stack.
     */
    public List<Book> getBooksByGenre(String genre) {
        List<Book> books = new ArrayList<>();

        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            String sql = "SELECT isbn, name, description, author, genre, publisher, year_published, price, copies_sold FROM books WHERE genre = ?";
            stmt = c.prepareStatement(sql);
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book b = new Book(rs.getString("isbn"), rs.getString("name"), rs.getString("description"),
                        rs.getString("author"), rs.getString("genre"), rs.getString("publisher"),
                        rs.getInt("year_published"), rs.getDouble("price"), rs.getInt("copies_sold"));
                books.add(b);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }

        return books;
    }


    /**
     * Retrieves the top 10 selling books from the database.
     * This method queries the database for books ordered by the number of copies sold in descending order,
     * limiting the results to the top 10. It constructs a list of {@link Book} objects representing these top-selling books.
     *
     * @return A List of {@link Book} objects representing the top 10 selling books. If there are no sales or an error occurs,
     *         an empty list is returned.
     */
    public List<Book> getTopSellingBooks() {
        List<Book> topSellingBooks = new ArrayList<>();

        Connection c = null;
        PreparedStatement stmt = null;
        try {
            c = DBUtils.Connect();
            String sql = "SELECT isbn, name, description, author, genre, publisher, year_published, price, copies_sold FROM books ORDER BY copies_sold DESC LIMIT 10";
            stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book b = new Book(rs.getString("isbn"), rs.getString("name"), rs.getString("description"),
                        rs.getString("author"), rs.getString("genre"), rs.getString("publisher"),
                        rs.getInt("year_published"), rs.getDouble("price"), rs.getInt("copies_sold"));
                topSellingBooks.add(b);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }

        return topSellingBooks;
    }
    
/**
 * Retrieves a list of books from the database that have a rating equal to or higher than the specified minimum rating. 
 * This method establishes a connection to the database, prepares a SQL query to select books based on the rating criterion, 
 * executes the query, and processes the result set to create a list of {@link Book} objects.
 *
 * @param rating The minimum rating threshold for books to be retrieved. This value is used to filter books in the SQL query.
 * @return A list of {@link Book} objects that match the rating criteria. Each {@link Book} object includes details such as 
 * ISBN, name, description, author, genre, publisher, year published, price, and copies sold. If no books meet the rating criteria 
 * or in case of an error, this method returns an empty list.
 * @throws SQLException if a database access error occurs or this method is called on a closed connection.
 * @throws Exception if an error occurs during the execution of the SQL query or processing the result set.
 */
public List<Book> getBooksByRating(double rating) {
    List<Book> books = new ArrayList<>();
    Connection c = null;
    PreparedStatement stmt = null;
    try {
        c = DBUtils.Connect();
        // Corrected SQL query to filter by rating instead of price
        String sql = "SELECT isbn, name, description, author, genre, publisher, year_published, price, copies_sold, rating FROM books WHERE rating >= ?";
        stmt = c.prepareStatement(sql);
        stmt.setDouble(1, rating);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            // Assuming a constructor that includes rating or setRating method is available
            Book b = new Book(rs.getString("isbn"), rs.getString("name"), rs.getString("description"),
                    rs.getString("author"), rs.getString("genre"), rs.getString("publisher"),
                    rs.getInt("year_published"), rs.getDouble("price"), rs.getInt("copies_sold"));
            // Uncomment the following line if the Book model includes a rating field
            // b.setRating(rs.getDouble("rating"));
            books.add(b);
        }
        rs.close();
    } catch (Exception e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (c != null) c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    return books;
}

/**
 * Applies a discount to all books from a specific publisher.
 * This method updates the price of each book by reducing it based on the given discount percentage.
 *
 * @param publisher The publisher whose books will be discounted.
 * @param discountPercent The percentage of discount to be applied to the book prices.
 */
public void discountBooksByPublisher(String publisher, double discountPercent) {
    Connection c = null;
    PreparedStatement stmt = null;
    try {
        c = DBUtils.Connect();
        String sql = "UPDATE books SET price = price - (price * ? / 100) WHERE publisher = ?";
        stmt = c.prepareStatement(sql);
        stmt.setDouble(1, discountPercent);
        stmt.setString(2, publisher);
        int rowsUpdated = stmt.executeUpdate();
        System.out.println(rowsUpdated + " rows updated.");
    } catch (SQLException e) {
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (c != null) c.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}


}
