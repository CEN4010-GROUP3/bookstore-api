package edu.fiu.cen4010.group3.view;

import edu.fiu.cen4010.group3.data.AuthorDao;
import edu.fiu.cen4010.group3.model.Author;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class AuthorView {
    
    public static void createAuthor(Context ctx) {

        Author author = ctx.bodyAsClass(Author.class);
        System.out.println("[VIEW] Creating author: " + author.getFullName() );

        AuthorDao authorDao = new AuthorDao();
        try {
            authorDao.save(author);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }

        ctx.status(HttpStatus.CREATED);

    }

    public static void getAuthors(Context ctx) {
        System.out.println("[VIEW] Getting all authors");
        AuthorDao authorDao = new AuthorDao();
        ctx.json(authorDao.getAuthors());
        ctx.status(HttpStatus.OK);
    }

    public static void updateAuthor(Context ctx) {
        Author author = ctx.bodyAsClass(Author.class);
        System.out.println("[VIEW] Updating author: " + author.getFullName() );

        AuthorDao authorDao = new AuthorDao();
        try {
            authorDao.updateAuthor(author);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }
        ctx.status(HttpStatus.NO_CONTENT);
    }

    public static void deleteAuthor(Context ctx) {
        Author author = ctx.bodyAsClass(Author.class);
        System.out.println("[VIEW] Deleting author: " + author.getFullName() );

        AuthorDao authorDao = new AuthorDao();
        try {
            authorDao.deleteAuthor(author);
        } catch (IllegalArgumentException e) {
            ctx.status(HttpStatus.BAD_REQUEST);
            ctx.result(e.getMessage());
            return;
        }
        ctx.status(HttpStatus.NO_CONTENT);
    }
    
}
