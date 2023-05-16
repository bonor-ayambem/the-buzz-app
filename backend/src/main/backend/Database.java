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
     * The connection to the database.  When there is no connection, it should
     * be null.  Otherwise, there is a valid open connection
     */
    private Connection mConnection;

    /**
     * A prepared statement for getting all data in the database
     */
    private PreparedStatement mSelectAllMessage;

    /**
     * A prepared statement for getting all comments in the specific database
     */
    private PreparedStatement mSelectAllComments;

        /**
     * A prepared statement for getting all users in the database
     */
    private PreparedStatement mSelectAllUsers;

    /**
     * A prepared statement for getting one row from the database
     */
    private PreparedStatement mSelectOneMessage;

    /**
     * A prepared statement for getting one row from the database
     */
    private PreparedStatement mSelectOneComment;

    
    /**
     * A prepared statement for getting one row from the database
     */
    private PreparedStatement mSelectOneUser;

    /**
     * A prepared statement for deleting a row from the database
     */
    private PreparedStatement mDeleteOneMessage;

    
    /**
     * A prepared statement for deleting a comment from a specific database
     */
    private PreparedStatement mDeleteOneComment;

    /**
     * A prepared statement for deleting a user from the database
     */
    private PreparedStatement mDeleteOneUser;

    /**
     * A prepared statement for inserting into the database
     */
    private PreparedStatement mInsertOneMessage;

    /**
     * A prepared statement for inserting a comment into the a specific database
     */
    private PreparedStatement mInsertOneComment;

    /**
     * A prepared statement for inserting a user into the database
     */
    private PreparedStatement mInsertOneUser;

    /**
     * A prepared statement for updating a single row in the database
     */
    private PreparedStatement mUpdateOneMessage;
    
    /**
     * A prepared statement for creating the table in our database
     */
    private PreparedStatement mCreateTableMessages;

    /**
     * A prepared statement for creating the comment table in our database
     */
    private PreparedStatement mCreateTableComment;

     /**
     * A prepared statement for creating the user table in our database
     */
    private PreparedStatement mCreateTableUser;

     /**
     * A prepared statement for creating the likes table in our database
     */
    private PreparedStatement mCreateTableLikes;

    /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTableMessages;

        /**
     * A prepared statement for dropping the  table in our database
     */
    private PreparedStatement mDropTableComment;

     /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTableUser;

     /**
     * A prepared statement for dropping the table in our database
     */
    private PreparedStatement mDropTableLikes;

    /**
     * A prepared statement for updating the content of a comment on the table in our database
     */
    private PreparedStatement mUpdateOneComment;

    /**
     * A prepared statment for getting all the likes for each message
     */
    private PreparedStatement mSelectAllLikes;
    /**
     * A prepared statement for getting the number of likes given a message id
     */
    private PreparedStatement mSelectOneLike;

    /**
     * A prepared statement for inserting a like row into the liek table
     */
    private PreparedStatement mInsertOneLike;

    /**
     * Udates one like to change the flag
     */

    private PreparedStatement mUpdateOneLike;

    /**
     * Deletes One Like Row
     */
    private PreparedStatement mDeleteOneLike;

    /**
     * A prepared statement for createing a message file table
     */
    private PreparedStatement mCreateTableMessageFile;

    /**
     * A prepared statement for createing a message file table
     */
    private PreparedStatement mCreateTableCommentFile;

    /**
     * A prepared statement for createing a comment file table
     */
    private PreparedStatement mDropTableMessageFile;

    /**
     * A prepared statement for dropping comment table
     */
    private PreparedStatement mDropTableCommentFile;

    /**
     * post a file to FileMessage
     */
    private PreparedStatement mInsertOneMessageFile;

    private PreparedStatement mInsertOneCommentFile;

    private PreparedStatement mSelectOneMessageFile;

    private PreparedStatement mSelectOneCommentFile;

    private PreparedStatement mDeleteOneCommentFile;
    
    private PreparedStatement mDeleteOneMessageFile;



    
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

        // Attempt to create all of our prepared statements.  If any of these 
        // fail, the whole getDatabase() call should fail
        try {
            // NB: we can easily get ourselves in trouble here by typing the
            //     SQL incorrectly.  We really should have things like "tblMessages"
            //     as constants, and then build the strings for the statements
            //     from those constants.

            // Create Table
            db.mCreateTableMessages = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblMessages (message_id SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL, content VARCHAR(500) NOT NULL, user_id INTEGER REFERENCES tblUsers)"); //WORKING
            db.mCreateTableComment = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblComment (comment_id SERIAL PRIMARY KEY, message_id INTEGER REFERENCES tblMessages, user_id INTEGER REFERENCES tblUsers, content VARCHAR(500) NOT NULL)"); //WORKING
            db.mCreateTableUser = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblUsers (user_id SERIAL PRIMARY KEY, user_name VARCHAR(500) NOT NULL, first_name VARCHAR(500) NOT NULL, last_name VARCHAR(500) NOT NULL, email VARCHAR(500) NOT NULL)");//WORKING
            db.mCreateTableLikes = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblLikes (like_id SERIAL PRIMARY KEY, user_id INTEGER REFERENCES tblUsers, message_id INTEGER REFERENCES tblMessages, flag INTEGER)");//WORKING
            db.mCreateTableMessageFile = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblMessageFile (file_id PRIMARY KEY, message_id INTEGER REFERENCES tblMessages, file_type VARCHAR(500) NOT NULL, file_name VARCHAR(500) NOT NULL, file_hash VARCHAR(500) NOT NULL)");
            db.mCreateTableCommentFile = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblCommentFile (file_id PRIMARY KEY, message_id INTEGER REFERENCES tblMessages, file_type VARCHAR(500) NOT NULL, file_name VARCHAR(500) NOT NULL, file_hash VARCHAR(500) NOT NULL)");

            //Drop Table
            db.mDropTableMessages = db.mConnection.prepareStatement("DROP TABLE IF EXISTS tblMessages CASCADE"); //WORKING
            db.mDropTableComment = db.mConnection.prepareStatement("DROP TABLE tblComment CASCADE"); //WORKING
            db.mDropTableUser = db.mConnection.prepareStatement("DROP TABLE IF EXISTS tblUsers CASCADE"); //WORKING
            db.mDropTableLikes = db.mConnection.prepareStatement("DROP TABLE IF EXISTS tblLikes CASCADE"); //WORKING
            db.mDropTableMessageFile = db.mConnection.prepareStatement("DROP TABLE IF EXISTS tblMessageFile CASCADE");
            db.mDropTableCommentFile = db.mConnection.prepareStatement("DROP TABLE IF EXISTS tblCommentFile CASCADE");

            // MessageTable
            db.mSelectAllMessage = db.mConnection.prepareStatement("SELECT message_id, subject, content, user_id FROM tblMessages"); //WORKING
            db.mSelectOneMessage = db.mConnection.prepareStatement("SELECT * from tblMessages WHERE message_id = ?"); //WORKING
            db.mInsertOneMessage = db.mConnection.prepareStatement("INSERT INTO tblMessages VALUES (default, ?, ?, ?)"); //WORKING
            db.mUpdateOneMessage = db.mConnection.prepareStatement("UPDATE tblMessages SET content = ? WHERE message_id = ?"); //WORKING
            db.mDeleteOneMessage = db.mConnection.prepareStatement("DELETE FROM tblMessages WHERE message_id = ?"); //WORKING

            //Comment Table
            db.mSelectAllComments = db.mConnection.prepareStatement("SELECT * from tblComment WHERE message_id = ?"); //WORKING
            db.mSelectOneComment = db.mConnection.prepareStatement("SELECT * from tblComment WHERE comment_id = ?"); //WROKING
            db.mInsertOneComment = db.mConnection.prepareStatement("INSERT INTO tblComment VALUES (default, ?, ?, ?)"); //WORKING
            db.mUpdateOneComment = db.mConnection.prepareStatement("UPDATE tblComment SET content = ? WHERE comment_id = ?"); //WORKING
            db.mDeleteOneComment = db.mConnection.prepareStatement("DELETE FROM tblComment WHERE comment_id = ?"); //WORKING

            //User Table
            db.mSelectAllUsers = db.mConnection.prepareStatement("SELECT user_name, subject FROM tblUsers"); //NOT NEEDED
            db.mSelectOneUser = db.mConnection.prepareStatement("SELECT * from tblUsers WHERE user_id = ?"); //WORKING
            db.mInsertOneUser = db.mConnection.prepareStatement("INSERT INTO tblUsers VALUES(DEFAULT, ?,?,?,?)"); //WORKING
            db.mDeleteOneUser = db.mConnection.prepareStatement("DELETE FROM tblUsers WHERE user_id = ?"); //WORKING

            //Like Table
            db.mSelectAllLikes = db.mConnection.prepareStatement("SELECT message_id, sum(flag) as sum_like from tblLikes GROUP BY message_id"); //NOT NEEDED
            db.mSelectOneLike = db.mConnection.prepareStatement("SELECT SUM(flag) as sum_like from tblLikes where message_id = ?"); //WORKING
            db.mInsertOneLike = db.mConnection.prepareStatement("INSERT INTO tblLikes VALUES(DEFAULT, ?, ?, ?)"); //WORKING
            db.mUpdateOneLike = db.mConnection.prepareStatement("UPDATE tblLikes SET flag = ? WHERE like_id = ?"); //WORKING
            db.mDeleteOneLike = db.mConnection.prepareStatement("DELETE FROM tblLikes WHERE like_id = ?"); //WORKING

            //File Table
            db.mInsertOneCommentFile = db.mConnection.prepareStatement("INSERT INTO tblCommentFile VALUES (default, ?,?,?,?)");
            db.mInsertOneMessageFile = db.mConnection.prepareStatement("INSERT INTO tblMessageFile VALUES (default, ?,?,?,?)");
            db.mSelectOneCommentFile = db.mConnection.prepareStatement("SELECT * from tblCommentFile WHERE message_id = ?");
            db.mSelectOneMessageFile = db.mConnection.prepareStatement("SELECT * from tblMessageFile WHERE message_id = ?");
            db.mDeleteOneCommentFile = db.mConnection.prepareStatement("DELETE FROM tblCommentFile WHERE file_id = ?");
            db.mDeleteOneMessageFile = db.mConnection.prepareStatement("DELETE FROM tblMessageFIle WHERE file_id = ?");
            


        } catch (SQLException e) {
            System.err.println("Error creating prepared statement");
            e.printStackTrace();
            db.disconnect();
            return null;
        }
        return db;
    }

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

    /**
     * 
     * Insert a row into the database
     * 
     * @param subject The subject for this new row
     * @param content The message body for this new row
     * 
     * @return The number of rows that were inserted
     */
    int insertRowMessage(String subject, String content, int user_id) {
        int count = 0;
        try {
            mInsertOneMessage.setString(1, subject);
            mInsertOneMessage.setString(2, content);
            mInsertOneMessage.setInt(3, user_id);
            count += mInsertOneMessage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertRowComment(int comment_id, int user_id, String content) {
        int count = 0;
        try {
            mInsertOneComment.setInt(1, comment_id);
            mInsertOneComment.setInt(2, user_id);
            mInsertOneComment.setString(3, content);
            count += mInsertOneComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertRowLikes(int id, String user, int flag) {
        int count = 0;
        try {
            mInsertOneMessage.setInt(1, id);
            mInsertOneMessage.setString(2, user);
            mInsertOneMessage.setInt(3, flag);
            count += mInsertOneMessage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

       /**
     * Insert a row into the database
     * 
     * @param email The email for this new user row
     * @param bio The bio for this new user row
     * 
     * @return The number of rows that were inserted
     */
    int insertRowUser(String user, String firstName, String lastName, String email) {
        int count = 0;
        try {
            mInsertOneUser.setString(1, user);
            mInsertOneUser.setString(2, firstName);
            mInsertOneUser.setString(3, lastName);
            mInsertOneUser.setString(4, email);
            count += mInsertOneUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * PHASE 2: For MessageRow
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<MessageRow> selectAllMessages() {
        ArrayList<MessageRow> res = new ArrayList<MessageRow>();
        try {
            ResultSet rs = mSelectAllMessage.executeQuery();
            while (rs.next()) {
                res.add(new MessageRow(rs.getInt("message_id"), rs.getString("subject"), rs.getString("content"), rs.getInt("user_id"))); //come back here later. should likes here be -1?
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Get all likes for a specific row in MessageTable, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    ArrayList<LikesRow> selectAllLikes(int id) {
        ArrayList<LikesRow> res = new ArrayList<LikesRow>();
        try {
            mSelectAllLikes.setInt(1, id);
            ResultSet rs = mSelectAllLikes.executeQuery();
            if (rs.next()) {
                res.add(new LikesRow(rs.getInt("user_id"), rs.getInt("message_id"), rs.getInt("flag")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    LikesData selectOneLike(int message_id) {
        LikesData res = null;
        try {
            mSelectOneLike.setInt(1, message_id);
            ResultSet rs = mSelectOneLike.executeQuery();
            if (rs.next()) {
                res = new LikesData(rs.getInt("sum_like"), message_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

        /**
     * Get all likes for a specific row in MessageTable, by ID
     * 
     *
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    ArrayList<UserRow> selectAllUsers() {
        ArrayList<UserRow> res = new ArrayList<UserRow>();
        try {
            ResultSet rs = mSelectAllUsers.executeQuery();
            if (rs.next()) {
                res.add(new UserRow(rs.getString("user"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    
    /**
     * Query the database for a list of all subjects and their IDs
     * 
     * @return All rows, as an ArrayList
     */
    ArrayList<CommentRow> selectAllComments(int messageID) {
        ArrayList<CommentRow> res = new ArrayList<CommentRow>();
        try {
            mSelectAllComments.setInt(1, messageID);
            ResultSet rs = mSelectAllComments.executeQuery();
            while (rs.next()) {
                res.add(new CommentRow(rs.getInt("comment_id"), rs.getInt("message_id"), rs.getInt("user_id"), rs.getString("content"))); //should this be comment_id or message_id or both
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * PHASE 2: For MessageRow
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    MessageRow selectOneMessage(int id) {
        MessageRow res = null;
        try {
            mSelectOneMessage.setInt(1, id);
            ResultSet rs = mSelectOneMessage.executeQuery();
            if (rs.next()) {
                res = new MessageRow(rs.getInt("message_id"), rs.getString("subject"),  rs.getString("content"), rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

        /**
     * Get all data for a specific row, by ID
     * 
     * @param id The id of the row being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    CommentRow selectOneComment(int id) {
        CommentRow res = null;
        try {
            mSelectOneComment.setInt(1, id);
            ResultSet rs = mSelectOneComment.executeQuery();
            if (rs.next()) {
                res = new CommentRow(rs.getInt("message_id"), rs.getInt("user_id"), rs.getString("content")); //should this be comment_id or message_id or both
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

        /**
     * Get all data for a specific row, by ID
     * 
     * @param email The email of the user being requested
     * 
     * @return The data for the requested row, or null if the ID was invalid
     */
    UserRow selectOneUser(int user_id) {
        UserRow res = null;
        try {
            mSelectOneUser.setInt(1, user_id);
            ResultSet rs = mSelectOneUser.executeQuery();
            if (rs.next()) {
                res = new UserRow(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
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

    /**
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteRowMessage(int id) {
        int res = -1;
        try {
            mDeleteOneMessage.setInt(1, id);
            res = mDeleteOneMessage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int deleteRowComment(int id) {
        int res = -1;
        try {
            mDeleteOneComment.setInt(1, id);
            res = mDeleteOneComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * PHASE 2: For MessageRow
     * Delete a row by ID
     * 
     * @param id The id of the row to delete
     * 
     * @return The number of rows that were deleted.  -1 indicates an error.
     */
    int deleteRowUser(int user_id) {
        int res = -1;
        try {
            mDeleteOneUser.setInt(1, user_id);
            res = mDeleteOneUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * PHASE 2: For MessageRow
     * Update the message for a row in the database
     * 
     * @param id The id of the row to update
     * @param content The new message contents
     * 
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updateOneMessage(int id, String content) {
        int res = -1;
        try {
            mUpdateOneMessage.setString(1, content);
            mUpdateOneMessage.setInt(2, id);
            res = mUpdateOneMessage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**************************************************************
     * Update the comment for a row in the database
     * 
     * @param id The id of the row to update
     * @param message The new message contents
     * @param likex
     * @param mMessage
     * 
     * @return The number of rows that were updated.  -1 indicates an error.
     */
    int updateOneComment(int id, String content) {
        int res = -1;
        try {
            mUpdateOneComment.setString(1, content);
            mUpdateOneComment.setInt(2, id);
            res = mUpdateOneComment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Adds a like to a message 
     * 
     * @param user_id The user id of the user who liked the message
     * @param message_id The message id of the message that that got a like
     * @param flag the value of the like either -1 or 1
     * @return
     */
    int insertRowLike(int user_id, int message_id, int flag) {
        int count = 0;
        try {
            mInsertOneLike.setInt(1, user_id);
            mInsertOneLike.setInt(2, message_id);
            mInsertOneLike.setInt(3, flag);
            count += mInsertOneLike.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int updateOneLike(int like_id, int flag) {
        int res = -1;
        try {
            mUpdateOneLike.setInt(1, flag);
            mUpdateOneLike.setInt(2, like_id);
            res = mUpdateOneLike.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int deleteRowLike(int like_id) {
        int res = -1;
        try {
            mDeleteOneLike.setInt(1, like_id);
            res = mDeleteOneUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int insertRowMessageFile(int message_id, String file_type, String file_name, String file_hash) {
        int count = 0;
        try {
            mInsertOneMessageFile.setInt(1, message_id);
            mInsertOneMessageFile.setString(2, file_type);
            mInsertOneMessageFile.setString(3, file_name);
            mInsertOneMessageFile.setString(4, file_hash);
            count += mInsertOneMessage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    int insertRowCommentFile(int message_id, String file_type, String file_name, String file_hash) {
        int count = 0;
        try {
            mInsertOneCommentFile.setInt(1, message_id);
            mInsertOneCommentFile.setString(2, file_type);
            mInsertOneCommentFile.setString(3, file_name);
            mInsertOneCommentFile.setString(4, file_hash);
            count += mInsertOneMessage.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    FileRow selectOneMessageFileRow(int message_id) {
        FileRow res = null;
        try {
            mSelectOneMessageFile.setInt(1, message_id);
            ResultSet rs = mSelectOneUser.executeQuery();
            if (rs.next()) {
                res = new FileRow(rs.getInt("file_id"), rs.getInt("message_id"), rs.getString("file_type"), rs.getString("file_name"), rs.getString("file_hash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    FileRow selectOneCommentFileRow(int message_id) {
        FileRow res = null;
        try {
            mSelectOneCommentFile.setInt(1, message_id);
            ResultSet rs = mSelectOneUser.executeQuery();
            if (rs.next()) {
                res = new FileRow(rs.getInt("file_id"), rs.getInt("message_id"), rs.getString("file_type"), rs.getString("file_name"), rs.getString("file_hash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int deleteRowMessageFile(int file_id) {
        int res = -1;
        try {
            mDeleteOneMessageFile.setInt(1, file_id);
            res = mDeleteOneUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    int deleteRowCommentFile(int file_id) {
        int res = -1;
        try {
            mDeleteOneCommentFile.setInt(1, file_id);
            res = mDeleteOneUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }




    /**
     * Create tblMessages.  If it already exists, this will print an error
     */
    void createTableMessages() {
        try {
            mCreateTableMessages.execute();
            System.out.println("TABLE CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblLikes.  If it already exists, this will print an error
     */
    void createTableLikes() {
        try {
            mCreateTableLikes.execute();
            System.out.println("TABLE CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove tblMessages from the database.  If it does not exist, this will print
     * an error.
     */
    void dropTableMessages() {
        try {
            mDropTableMessages.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblComment.  If it already exists, this will print an error
     */
    void createTableComment() {
        try {
            mCreateTableComment.execute();
            System.out.println("TABLE CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create tblUser.  If it already exists, this will print an error
     */
    void createTableUser() {
        try {
            mCreateTableUser.execute();
            System.out.println("TABLE CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createTableMessageFile() {
        try {
            mCreateTableMessageFile.execute();
            System.out.println("TABLE CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createTableCommentFile() {
        try {
            mCreateTableCommentFile.execute();
            System.out.println("TABLE CREATED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Remove tblLikes from the database.  If it does not exist, this will print
     * an error.
     */
    void dropTableLikes() {
        try {
            mDropTableLikes.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
        /**
     * Remove tblComment from the database.  If it does not exist, this will print
     * an error.
     */
    void dropTableComment() {
        try {
            mDropTableComment.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

            /**
     * Remove tblUsers from the database.  If it does not exist, this will print
     * an error.
     */
    void dropTableUser() {
        try {
            mDropTableUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void dropTableMessageFile() {
        try {
            mDropTableMessageFile.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void dropTableCommentFile() {
        try {
            mDropTableCommentFile.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}       