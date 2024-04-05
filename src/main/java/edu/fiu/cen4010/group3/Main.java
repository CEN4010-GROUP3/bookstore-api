package edu.fiu.cen4010.group3;

import edu.fiu.cen4010.group3.view.*;
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

        //route to post comments and get a list of comments
        CommentView commentview =  new CommentView();
        app.post("book/create/comments", ctx -> commentview.postComments(ctx));
        app.get("book/comments/{bookID}", ctx -> commentview.getAllComment(ctx));

        // Wishlist-related requests
        app.post("/wishlist/create", ctx -> WishlistView.createWishlist(ctx));
        app.get("/user/{userid}/wishlist", ctx -> WishlistView.getUserWishlists(ctx));
        app.get("/wishlist/{id}", ctx -> WishlistView.getWishlistById(ctx));
        app.post("/wishlist/{id}/{isbn}", ctx -> WishlistView.addToWishlist(ctx));
        app.delete("wishlist/{id}/{isbn}", ctx -> WishlistView.deleteFromWishlist(ctx));
        app.start(7070);

        // User Profile related requests
        app.post("/user", ctx -> UserProfileView.createUser(ctx));
        app.get("/user/{username}", ctx -> UserProfileView.retrieveUser(ctx));
        app.put("/user/{username}", ctx -> UserProfileView.updateUser(ctx));
        app.post("/user/{username}/{credit-card}", ctx ->  UserProfileView.creditCard(ctx));

    }
}
