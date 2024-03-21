package edu.fiu.cen4010.group3;

import edu.fiu.cen4010.group3.view.BookView;
import edu.fiu.cen4010.group3.view.WishlistView;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        app.get("/books", ctx -> BookView.displayBooks(ctx));
        app.get("/books/{isbn}", ctx -> BookView.getBookByIsbn(ctx));
        app.post("/books", ctx -> BookView.createBook(ctx));
        app.put("/books/{isbn}", ctx -> BookView.updateBook(ctx));
        app.delete("/books/{isbn}", ctx -> BookView.deleteBook(ctx));

        app.post("/wishlist/create", ctx -> WishlistView.createWishlist(ctx));
        app.get("/user/{userid}/wishlist", ctx -> WishlistView.getUserWishlists(ctx));
        app.get("/wishlist/{id}", ctx -> WishlistView.getWishlistById(ctx));
        app.post("/wishlist/{id}/{isbn}", ctx -> WishlistView.addToWishlist(ctx));
        app.start(7070);

    }
}
