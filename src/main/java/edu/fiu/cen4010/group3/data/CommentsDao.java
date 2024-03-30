package edu.fiu.cen4010.group3.data;

import edu.fiu.cen4010.group3.utils.DBUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CommentsDao {

    /**
     * Save comment,user ID and Book ID in Database
     * @param Comment
     * @param userID
     * @param bookID
     * @Return NONE
     */
    public static void saveComments(String Comment,String userID,String bookID){
        Connection connect = null;
        PreparedStatement stmt = null;
        String SQL = "INSERT INTO ratings (bookcomment, userid,bookid,datestamped) VALUES (?,?,?,?);";
        try{
            connect = DBUtils.Connect();

            stmt = connect.prepareStatement(SQL);
            stmt.setString(1,Comment);
            stmt.setString(2,userID);
            stmt.setString(3, bookID);
            stmt.setDate(4, Date.valueOf(LocalDate.now()));

            stmt.execute();
            stmt.close();
            connect.close();

        }catch (SQLException e){
            e.printStackTrace();
            System.out.printf("Issue with getting Data for SaveRating in DOA class");
        }
    }

    /**
     * Retrieve all comments of a bookID in DataBase
     * @param bookID
     * @return an Array String list of Comments
     */
    public static ArrayList<String> getComments(String bookID){
        Connection connect= null;
        Statement stmt = null;
        ResultSet rs =  null;

        String sql = "SELECT bookid, bookcomment FROM ratings WHERE bookid = '" + bookID + "' ;";
        ArrayList<String>commentList = new ArrayList<>();
        try{
            connect = DBUtils.Connect();
            stmt = connect.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()){
                commentList.add(rs.getString("bookcomment"));
            }
            connect.close();
            stmt.close();
            rs.close();
        }catch (Exception s) {
            s.printStackTrace();
        }
        return commentList;
    }
}
