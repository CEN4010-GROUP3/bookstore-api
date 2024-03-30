package edu.fiu.cen4010.group3.data;

import edu.fiu.cen4010.group3.utils.DBUtils;

import java.sql.*;
import java.time.LocalDate;

public class RatingDao {
    /**
     * Save rating,user ID and Book ID in Database
     * @param bookID
     * @param Rating
     * @param userID
     * @Return NONE
     */
    public void saveRating(String bookID, Double Rating, String userID){
        Connection connect = null;
        PreparedStatement stmt = null;
        String SQL =  "INSERT INTO  ratings (rating, userid, bookid, createdDateTime) VALUES (?,?,?,?);";
        try{
            connect = DBUtils.Connect();
            stmt = connect.prepareStatement(SQL);
            stmt.setDouble(1,Rating);
            stmt.setString(2,userID);
            stmt.setString(3,bookID);
            stmt.setDate(4, Date.valueOf(LocalDate.now()));
            stmt.execute();
            stmt.close();
            connect.close();
        }catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Issue with getting Data for SaveRating in DOA class");
        }
    }

    /**
     * Retrieve average rating of a bookID in DataBase
     * @param bookID
     * @return Double Data Type of the average rating
     */
    public Double averageRating(String bookID){
        Connection connect= null;
        Statement stmt = null;
        ResultSet rs =  null;

        Double average = 0.00;
        String sql = "SELECT AVG(rating) ::NUMERIC(10,2) FROM ratings WHERE bookid ='"+bookID +"';";
        try{
            connect = DBUtils.Connect();
            stmt = connect.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                average = rs.getDouble("AVG");
            }
            connect.close();
            stmt.close();
            rs.close();
        }catch (Exception s) {
            s.printStackTrace();
        }
        return average;
    }


}
