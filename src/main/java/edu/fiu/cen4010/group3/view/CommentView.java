package edu.fiu.cen4010.group3.view;

import edu.fiu.cen4010.group3.data.CommentsDao;
import edu.fiu.cen4010.group3.model.Comments;
import io.javalin.http.Context;

public class CommentView {

    private CommentsDao commentsdao;

    public CommentView(){
         commentsdao = new CommentsDao();
    }

    /**
     * get Information from context body and pass it to DOA to save
     * @param ctx
     * @Return NONE
     */
    public void postComments(Context ctx){
        Comments commentsObj =  ctx.bodyAsClass(Comments.class);
        commentsdao.saveComments(commentsObj.getComment(),commentsObj.getUserID(),commentsObj.getBookID());
    }

    /**
     * get bookID from the query parameter and pass it to DOA to fetch a list of the comment for the bookID
     * @param ctx
     * @Return a JSON list of comments
     */
    public void getAllComment(Context ctx){
        ctx.json(commentsdao.getComments(ctx.queryParam("bookID")));
    }
}
