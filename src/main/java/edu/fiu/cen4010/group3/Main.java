package edu.fiu.cen4010.group3;

import edu.fiu.cen4010.group3.view.BookView;
import edu.fiu.cen4010.group3.view.AuthorView;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        app.get("/books", ctx -> BookView.displayBooks(ctx));
        app.get("/books/{isbn}", ctx -> BookView.getBookByIsbn(ctx));
        app.get("/authors/books/{author}", ctx -> BookView.getBooksByAuthor(ctx));
        app.post("/books", ctx -> BookView.createBook(ctx));
        app.put("/books/{isbn}", ctx -> BookView.updateBook(ctx));
        app.delete("/books/{isbn}", ctx -> BookView.deleteBook(ctx));

        app.get("/authors", ctx -> AuthorView.getAuthors(ctx));
        app.post("/authors", ctx -> AuthorView.createAuthor(ctx));
        app.put("/authors", ctx -> AuthorView.updateAuthor(ctx));
        app.delete("/authors", ctx -> AuthorView.deleteAuthor(ctx));

        app.start(7070);

    }
}
