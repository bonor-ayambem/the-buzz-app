package edu.lehigh.cse216.alb323.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Database {    
    /**
     * The Database constructor is private: we only create Database objects 
     * through the getDatabase() method.
     */
    public Database() { //made it public as part of PHASE1 Admin app

    }

    static Database getDatabase(){
        Database db = new Database();
        return db;
    }

    /**
     * Phase1: Below is the new code for getDatabase. Above is the old code for getDatabase
     * Get a fully-configured connection to the database
     * 
     * @param db_url   The URL address of the Heroku database
     * @return A Database object, or null if we cannot connect properly
     */
    
    static Database getDatabase(String db_url) {
        // Create an un-configured Database object
        Database db = new Database();

        // Give the Database object a connection, fail if we cannot get one
        try {
            Class.forName("org.postgresql.Driver");
            URI dbUri = new URI(db_url);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            if (conn == null) {
                System.err.println("Error: DriverManager.getConnection() returned a null object");
                return null;
            }
            db.mConnection = conn;
        } catch (SQLException e) {
            System.err.println("Error: DriverManager.getConnection() threw a SQLException");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Unable to find postgresql driver");
            return null;
        } catch (URISyntaxException s) {
            System.out.println("URI Syntax Error");
            return null;
        }

    //     // Attempt to create all of our prepared statements.  If any of these 
    //     // fail, the whole getDatabase() call should fail
    //     try {
    //         // NB: we can easily get ourselves in trouble here by typing the
    //         //     SQL incorrectly.  We really should have things like "tblMessages"
    //         //     as constants, and then build the strings for the statements
    //         //     from those constants.

    //         // MessageTable
    //         db.mSelectAllMessage = db.mConnection.prepareStatement("SELECT message_id, subject, content, user_id FROM tblMessages"); //WORKING
    //         db.mSelectOneMessage = db.mConnection.prepareStatement("SELECT * from tblMessages WHERE message_id = ?"); //WORKING

    //         //Comment Table
    //         db.mSelectAllComments = db.mConnection.prepareStatement("SELECT * from tblComment WHERE message_id = ?"); //WORKING
    //         db.mSelectOneComment = db.mConnection.prepareStatement("SELECT * from tblComment WHERE comment_id = ?"); //WROKING

    //         //User Table
    //         db.mSelectAllUsers = db.mConnection.prepareStatement("SELECT user_name, subject FROM tblUsers"); //NOT NEEDED
    //         db.mSelectOneUser = db.mConnection.prepareStatement("SELECT * from tblUsers WHERE user_id = ?"); //WORKING

    //         //Like Table
    //         db.mSelectAllLikes = db.mConnection.prepareStatement("SELECT message_id, sum(flag) as sum_like from tblLikes GROUP BY message_id"); //NOT NEEDED
    //         db.mSelectOneLike = db.mConnection.prepareStatement("SELECT SUM(flag) as sum_like from tblLikes where message_id = ?"); //WORKING

    //         //File Table
    //         db.mSelectOneCommentFile = db.mConnection.prepareStatement("SELECT * from tblCommentFile WHERE message_id = ?");
    //         db.mSelectOneMessageFile = db.mConnection.prepareStatement("SELECT * from tblMessageFile WHERE message_id = ?");

    //     } catch (SQLException e) {
    //         System.err.println("Error creating prepared statement");
    //         e.printStackTrace();
    //         db.disconnect();
    //         return null;
    //     }
        return db;
    // }

    /**
     * Close the current connection to the database, if one exists.
     * 
     * NB: The connection will always be null after this call, even if an 
     *     error occurred during the closing operation.
     * 
     * @return True if the connection was cleanly closed, false otherwise
     */
    boolean disconnect() {
        if (mConnection == null) {
            System.err.println("Unable to close connection: Connection was null");
            return false;
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println("Error: Connection.close() threw a SQLException");
            e.printStackTrace();
            mConnection = null;
            return false;
        }
        mConnection = null;
        return true;
    }

    boolean isUser(int user_id) {
        try {
            mSelectOneUser.setInt(1, user_id);
            mSelectOneUser.executeQuery();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    int insertRow(Table table, ArrayList<String> row_content) {
        int count = 0;
        try {
            count += table.insertRow(db, row_content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int updateRow(Table table, int id, String content) {
        int res = -1;
        try {
            res = table.updateRow(db, id, content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int deleteRow(Table table, int id) {
        int res = -1;
        try {
            res = table.deleteRow(db, id); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    void createTable(Table table) {
        try {
            table.create(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void dropTable(Table table){
         try {
            table.drop(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ArrayList<MessageRow> selectAllMessages() {
    //     ArrayList<MessageRow> res = new ArrayList<MessageRow>();
    //     try {
    //         ResultSet rs = mSelectAllMessage.executeQuery();
    //         while (rs.next()) {
    //             res.add(new MessageRow(rs.getInt("message_id"), rs.getString("subject"), rs.getString("content"), rs.getInt("user_id"))); //come back here later. should likes here be -1?
    //         }
    //         rs.close();
    //         return res;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // ArrayList<LikesRow> selectAllLikes(int id) {
    //     ArrayList<LikesRow> res = new ArrayList<LikesRow>();
    //     try {
    //         mSelectAllLikes.setInt(1, id);
    //         ResultSet rs = mSelectAllLikes.executeQuery();
    //         if (rs.next()) {
    //             res.add(new LikesRow(rs.getInt("user_id"), rs.getInt("message_id"), rs.getInt("flag")));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }


    // ArrayList<UserRow> selectAllUsers() {
    //     ArrayList<UserRow> res = new ArrayList<UserRow>();
    //     try {
    //         ResultSet rs = mSelectAllUsers.executeQuery();
    //         if (rs.next()) {
    //             res.add(new UserRow(rs.getString("user"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email")));
    //         }
    //         rs.close();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }

    // ArrayList<CommentRow> selectAllComments(int messageID) {
    //     ArrayList<CommentRow> res = new ArrayList<CommentRow>();
    //     try {
    //         mSelectAllComments.setInt(1, messageID);
    //         ResultSet rs = mSelectAllComments.executeQuery();
    //         while (rs.next()) {
    //             res.add(new CommentRow(rs.getInt("comment_id"), rs.getInt("message_id"), rs.getInt("user_id"), rs.getString("content"))); //should this be comment_id or message_id or both
    //         }
    //         rs.close();
    //         return res;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // LikesData selectOneLike(int message_id) {
    //     LikesData res = null;
    //     try {
    //         mSelectOneLike.setInt(1, message_id);
    //         ResultSet rs = mSelectOneLike.executeQuery();
    //         if (rs.next()) {
    //             res = new LikesData(rs.getInt("sum_like"), message_id);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }

    // MessageRow selectOneMessage(int id) {
    //     MessageRow res = null;
    //     try {
    //         mSelectOneMessage.setInt(1, id);
    //         ResultSet rs = mSelectOneMessage.executeQuery();
    //         if (rs.next()) {
    //             res = new MessageRow(rs.getInt("message_id"), rs.getString("subject"),  rs.getString("content"), rs.getInt("user_id"));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }

    // CommentRow selectOneComment(int id) {
    //     CommentRow res = null;
    //     try {
    //         mSelectOneComment.setInt(1, id);
    //         ResultSet rs = mSelectOneComment.executeQuery();
    //         if (rs.next()) {
    //             res = new CommentRow(rs.getInt("message_id"), rs.getInt("user_id"), rs.getString("content")); //should this be comment_id or message_id or both
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }

    // UserRow selectOneUser(int user_id) {
    //     UserRow res = null;
    //     try {
    //         mSelectOneUser.setInt(1, user_id);
    //         ResultSet rs = mSelectOneUser.executeQuery();
    //         if (rs.next()) {
    //             res = new UserRow(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }

    // FileRow selectOneMessageFileRow(int message_id) {
    //     FileRow res = null;
    //     try {
    //         mSelectOneMessageFile.setInt(1, message_id);
    //         ResultSet rs = mSelectOneUser.executeQuery();
    //         if (rs.next()) {
    //             res = new FileRow(rs.getInt("file_id"), rs.getInt("message_id"), rs.getString("file_type"), rs.getString("file_name"), rs.getString("file_hash"));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }

    // FileRow selectOneCommentFileRow(int message_id) {
    //     FileRow res = null;
    //     try {
    //         mSelectOneCommentFile.setInt(1, message_id);
    //         ResultSet rs = mSelectOneUser.executeQuery();
    //         if (rs.next()) {
    //             res = new FileRow(rs.getInt("file_id"), rs.getInt("message_id"), rs.getString("file_type"), rs.getString("file_name"), rs.getString("file_hash"));
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return res;
    // }
}       
