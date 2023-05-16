package edu.lehigh.cse216.alb323.backend;

public class UserRow {

    public int mUserID;
    /**
     * he unique identifier associated with this element.  It's final, because
     * we never want to change it. 
     */
    public final String mUser;

    /**
     * First name of user
     */
    public final String mFirstName;

    /**
     * Last Name of user
     */
    public final String mLastName;

    /**
     * Email of user
     */
    public final String mEmail;


    /**
     * Create a new UserRow with the provided
     * @param user The user associated with this row
     * @param firstName The First name of the user
     * @param lastName The Last name of the user
     * @param email The Email of the user
     */
    UserRow(String user, String firstName, String lastName, String email) {
        mUser = user;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
    }

    UserRow(int user_id, String user, String firstName, String lastName, String email) {
        mUserID = user_id;
        mUser = user;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
    }

    /**
     * Copy constructor to create one UserRow from another
     * @param data UserRow that is being coppied
     */
    UserRow(UserRow data) {
        mUser = data.mUser;
        mFirstName = data.mFirstName;
        mLastName = data.mLastName;
        mEmail = data.mEmail;       
    }
}
