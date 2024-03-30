package edu.fiu.cen4010.group3.view;

import edu.fiu.cen4010.group3.data.RatingDao;
import edu.fiu.cen4010.group3.model.Rating;
import io.javalin.http.Context;

public class RatingView {

    private RatingDao ratingDao;

    public RatingView (){
        ratingDao  = new RatingDao();
    }

    /**
     * get Information from context body and pass it to DOA  to save
     * @param ctx
     * @return NONE
     */
    public void postRating(Context ctx){
        Rating ratingObj =  ctx.bodyAsClass(Rating.class);
        System.out.println(ratingObj.toString());
        ratingDao.saveRating(ratingObj.getBookID(),ratingObj.getRating(),ratingObj.getUserID());
    }

    /**
     * get bookID from the query parameter and pass it to DOA to fetch the average of Rating for bookID
     * @param ctx
     * @return JSON of Double number average of rating
     */
    public void getAVGRating(Context ctx){
        ctx.json(ratingDao.averageRating((ctx.queryParam("bookID"))));
    }

}
