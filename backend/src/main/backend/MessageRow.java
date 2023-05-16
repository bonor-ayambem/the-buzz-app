package edu.lehigh.cse216.alb323.backend;

/**
 * MessageRow holds a row of information.  A row of information consists of
 * an identifier, strings for a "title" and "content", and a creation date.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public.  That being the case, we will
 * not bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class MessageRow {

    public int mMessageID;
    /**
     * The title for this row of data
     */
    public final String mTitle;

    /**
     * The content for this row of data
     */
    public final String mContent;

    /**
     * The creator of this row of data
     */
    public final int mUser;


    /**
     * The creation date for this row of data.  Once it is set, it cannot be 
     * changed
     */

    /**
     * Create a new MessageRow with the provided id and title/content, and a 
     * creation date based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param title The title string for this row of data
     * 
     * @param content The content string for this row of data
     * 
     */
    MessageRow(String title, String content, int user) {
        mTitle = title;
        mContent = content;
        mUser = user;
    }

    MessageRow(int message_id, String title, String content, int user) {
        mMessageID = message_id;
        mTitle = title;
        mContent = content;
        mUser = user;
    }

    /**
     * Copy constructor to create one MessageRow from another
     */
    MessageRow(MessageRow data) {
        // NB: Strings and Dates are immutable, so copy-by-reference is safe
        mTitle = data.mTitle;
        mContent = data.mContent;
        mUser = data.mUser;
    }
}