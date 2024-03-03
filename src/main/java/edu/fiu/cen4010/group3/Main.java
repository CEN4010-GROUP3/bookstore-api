package edu.fiu.cen4010.group3;

import edu.fiu.cen4010.group3.view.BookView;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        app.get("/books", ctx -> BookView.displayBooks(ctx));
        app.get("/books/{isbn}", ctx -> BookView.getBookByIsbn(ctx));
        app.post("/books", ctx -> BookView.createBook(ctx));
        app.put("/books/{isbn}", ctx -> BookView.updateBook(ctx));
        app.delete("/books/{isbn}", ctx -> BookView.deleteBook(ctx));

        //route to Book genre view feature
        app.get("/books/genre/{genre}", ctx -> BookView.getBooksByGenre(ctx));

        //route to display the top 10 selling books
        app.get("/books/top-sellers", ctx -> BookView.getTopSellingBooks(ctx));

        app.start(7071);

    }
}