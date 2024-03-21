package edu.fiu.cen4010.group3.view;

import edu.fiu.cen4010.group3.data.BookDao;
import edu.fiu.cen4010.group3.data.WishlistDao;
import edu.fiu.cen4010.group3.model.Book;
import edu.fiu.cen4010.group3.model.Wishlist;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class WishlistView {

    public static void createWishlist(Context ctx) {
        Wishlist newWishlist = ctx.bodyAsClass(Wishlist.class);

        WishlistDao wishlistDao = new WishlistDao();
        try {
            wishlistDao.create(newWishlist.getName(), newWishlist.getUserId());
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }
        ctx.status(HttpStatus.CREATED);
    }

    public static void getUserWishlists(Context ctx) {
        WishlistDao wishlistDao = new WishlistDao();
        ctx.json(wishlistDao.getUserWishlists(
                Integer.parseInt(
                        ctx.pathParam("userid")
                )));
        ctx.status(HttpStatus.OK);
    }

    public static void getWishlistById(Context ctx) {
        WishlistDao wishlistDao = new WishlistDao();
        Wishlist w = wishlistDao.getWishlistById(
                Integer.parseInt(
                        ctx.pathParam("id")
        ));
        if (w != null) {
            ctx.json(w);
            ctx.status(HttpStatus.OK);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
        }
    }

    public static void addToWishlist(Context ctx) {
        WishlistDao wishlistDao = new WishlistDao();
        try {
            wishlistDao.addToWishlist(ctx.pathParam("isbn"), Integer.parseInt(ctx.pathParam("id")));
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }
        ctx.status(HttpStatus.CREATED);
    }

