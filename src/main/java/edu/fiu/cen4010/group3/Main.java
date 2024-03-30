package edu.fiu.cen4010.group3;

import edu.fiu.cen4010.group3.view.BookView;
import edu.fiu.cen4010.group3.view.AuthorView;
import edu.fiu.cen4010.group3.view.RatingView;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        app.get("/books", ctx -> BookView.displayBooks(ctx));

        //route to display the top 10 selling books
        app.get("/books/top-sellers", ctx -> BookView.getTopSellingBooks(ctx));

        // Route to retrieve books by rating
        app.get("/books/rating/{rating}", ctx -> BookView.getBooksByRating(ctx));

        app.get("/books/{isbn}", ctx -> BookView.getBookByIsbn(ctx));
        app.get("/authors/books", ctx -> BookView.getBooksByAuthor(ctx));
        app.post("/books", ctx -> BookView.createBook(ctx));
        app.put("/books/{isbn}", ctx -> BookView.updateBook(ctx));
        app.delete("/books/{isbn}", ctx -> BookView.deleteBook(ctx));

        //route to Book genre view feature
        app.get("/books/genre/{genre}", ctx -> BookView.getBooksByGenre(ctx));
        
        app.get("/authors", ctx -> AuthorView.getAuthors(ctx));
        app.post("/authors", ctx -> AuthorView.createAuthor(ctx));
        app.put("/authors", ctx -> AuthorView.updateAuthor(ctx));
        app.delete("/authors", ctx -> AuthorView.deleteAuthor(ctx));

        //route to post rating and get average rating
        RatingView ratingView = new RatingView();
        app.post("book/create/rating", ctx -> ratingView.postRating(ctx));
        app.get("book/ratings/{bookID}", ctx -> ratingView.getAVGRating(ctx));

        app.start(7070);

    }
}