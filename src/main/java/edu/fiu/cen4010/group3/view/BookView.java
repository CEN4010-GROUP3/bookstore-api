package edu.fiu.cen4010.group3.view;

import java.util.List;

import edu.fiu.cen4010.group3.data.BookDao;
import edu.fiu.cen4010.group3.model.Book;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class BookView {

    public static void displayBooks(Context ctx) {
        BookDao bookDao = new BookDao();
        ctx.json(bookDao.getBooks());
        ctx.status(HttpStatus.OK);
    }

    public static void getBookByIsbn(Context ctx) {
        BookDao bookDao = new BookDao();
        Book b = bookDao.findByIsbn(ctx.pathParam("isbn"));
        if (b != null) {
            ctx.json(b);
            ctx.status(HttpStatus.OK);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
        }
    }

    public static void createBook(Context ctx) {

        Book user = ctx.bodyAsClass(Book.class);

        BookDao bookDao = new BookDao();
        try {
            bookDao.save(user.getIsbn(), user.getName(), user.getDescription(), user.getAuthor(), user.getGenre(),
                    user.getPublisher(), user.getYearPublished(), user.getPrice());
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }

        ctx.status(HttpStatus.CREATED);

    }

    public static void updateBook(Context ctx) {
        Book user = ctx.bodyAsClass(Book.class);
        BookDao bookDao = new BookDao();
        try {
            bookDao.update(user.getIsbn(), user.getName(), user.getDescription(), user.getAuthor(), user.getGenre(),
                    user.getPublisher(), user.getYearPublished(), user.getPrice());
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }
        ctx.status(HttpStatus.NO_CONTENT);
    }

    public static void deleteBook(Context ctx) {
        BookDao bookDao = new BookDao();

        bookDao.delete(ctx.pathParam("isbn"));
        ctx.status(HttpStatus.NO_CONTENT);
    }


 /**
 * Handles an HTTP request to retrieve books by a specific genre. This method reads the
 * genre parameter from the request's query parameters. If the genre parameter is not
 * provided or is empty, the method responds with a BAD REQUEST status. Otherwise,
 * it queries the database for books matching the specified genre using a {@link BookDao}
 * instance and sends the list of books as a JSON response with an OK status.
 * 
 * @param ctx The {@link Context} object provided by the web framework, representing the
 *            current HTTP request and response context. It is used to access request
 *            parameters, modify the response status, and send the response.
 */
    public static void getBooksByGenre(Context ctx) {
        String genre = ctx.pathParam("genre");
        if (genre == null || genre.trim().isEmpty()) {
            ctx.status(HttpStatus.BAD_REQUEST).result("Genre parameter is required");
            return;
        }
        BookDao bookDao = new BookDao();
        List<Book> books = bookDao.getBooksByGenre(genre);
        ctx.json(books).status(HttpStatus.OK);
    }


/**
 * Handles an HTTP request to display the top 10 selling books.
 * This method utilizes {@link BookDao#getTopSellingBooks()} to fetch the top-selling books
 * and sends them back to the client as a JSON response. The HTTP status is set to OK if the operation succeeds.
 *
 * @param ctx The {@link Context} object provided by Javalin, representing the current request and response context.
 *            It is used to set the response status and send the list of top-selling books as JSON.
 */
    public static void getTopSellingBooks(Context ctx) {
        try {
            BookDao bookDao = new BookDao();
            List<Book> topSellingBooks = bookDao.getTopSellingBooks();
            if (topSellingBooks.isEmpty()) {
                ctx.status(HttpStatus.NOT_FOUND).result("No top-selling books found.");
            } else {
                ctx.json(topSellingBooks).status(HttpStatus.OK);
            }
        } catch (Exception e) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR).result("An error occurred: " + e.getMessage());
        }
    }


/**
 * Handles an HTTP GET request to retrieve books with a rating equal to or higher than a specified value. This method parses the rating value from the request's path parameter. If the rating parameter is not a valid double or if it's missing, the method responds with a BAD REQUEST status and an error message. Otherwise, it queries the database for books that meet the rating criteria using a {@link BookDao} instance and sends the list of books as a JSON response with an OK status.
 * 
 * @param ctx The {@link Context} object provided by Javalin, representing the current HTTP request and response context. It is used to access path parameters, modify the response status, and send the response body.
 * @throws NumberFormatException if the rating path parameter cannot be parsed into a double. This exception is caught internally, and an appropriate error response is set.
 */

public static void getBooksByRating(Context ctx) {
    double rating;
    try {
        rating = Double.parseDouble(ctx.pathParam("rating"));
    } catch (NumberFormatException e) {
        ctx.status(HttpStatus.BAD_REQUEST).result("Invalid rating format");
        return;
    }

    BookDao bookDao = new BookDao();
    List<Book> books = bookDao.getBooksByRating(rating);
    ctx.json(books).status(HttpStatus.OK);
}

}
