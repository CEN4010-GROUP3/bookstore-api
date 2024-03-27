package edu.fiu.cen4010.group3.view;

import edu.fiu.cen4010.group3.data.BookDao;
import edu.fiu.cen4010.group3.model.Book;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class BookView {

    public static void displayBooks(Context ctx) {
        System.out.println("[VIEW] Displaying all books");
        BookDao bookDao = new BookDao();
        ctx.json(bookDao.getBooks());
        ctx.status(HttpStatus.OK);
    }

    public static void getBookByIsbn(Context ctx) {
        System.out.println("[VIEW] Getting book with isbn: " + ctx.pathParam("isbn"));
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

        System.out.println("[VIEW] Creating book with isbn: " + ctx.pathParam("isbn"));
        Book book = ctx.bodyAsClass(Book.class);

        BookDao bookDao = new BookDao();
        try {
            bookDao.save(book.getIsbn(), book.getName(), book.getDescription(), book.getAuthor(), book.getGenre(),
                    book.getPublisher(), book.getYearPublished(), book.getPrice());
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }

        ctx.status(HttpStatus.CREATED);

    }

    public static void updateBook(Context ctx) {

        System.out.println("[VIEW] Updating book with isbn: " + ctx.pathParam("isbn"));
        Book book = ctx.bodyAsClass(Book.class);
        BookDao bookDao = new BookDao();
        try {
            bookDao.update(book.getIsbn(), book.getName(), book.getDescription(), book.getAuthor(), book.getGenre(),
            book.getPublisher(), book.getYearPublished(), book.getPrice());
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }
        ctx.status(HttpStatus.NO_CONTENT);
    }

    public static void deleteBook(Context ctx) {
        System.out.print("[VIEW] Deleting book with isbn: " + ctx.pathParam("isbn"));
        BookDao bookDao = new BookDao();
        
        bookDao.delete(ctx.pathParam("isbn"));
        ctx.status(HttpStatus.NO_CONTENT);
    }

    public static void getBooksByAuthor(Context ctx) {
        System.out.println("[VIEW] Getting books by author: " + ctx.pathParam("author"));
        BookDao bookDao = new BookDao();
        ctx.json(bookDao.findByAuthor(ctx.pathParam("author")));
        ctx.status(HttpStatus.OK);
    }
}
