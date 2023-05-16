package edu.lehigh.cse216.alb323.admin;

/**
 * LikesRow holds a row of information. A row of information consists of an
 * identifier, strings for a "title" and "content", and a creation date.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public. That being the case, we will not
 * bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class LikesRow {
    /**
     * The unique identifier associated with this element. It's final, because we
     * never want to change it.
     */
    public int mLikeID;

    /**
     * The title for this row of data
     */
    public int mUserID;

    /**
     * The content for this row of data
     */
    public int mMessageID;

    /**
     * The content for this row of data
     */
    public int mFlag;

    /**
     * Create a new LikesRow with the provided id and title/content, and a creation
     * date based on the system clock at the time the constructor was called
     * 
     * @param user_id    The user id of the user who liked the message
     * @param message_id The message id of the message that was liked
     * @param flag       The value of the like (either -1 or 1)
     */
    LikesRow(int user_id, int message_id, int flag) {
        mUserID = user_id;
        mMessageID = message_id;
        mFlag = flag;
    }

    /**
     * Copy constructor to create one LikesRow from another
     */
    LikesRow(LikesRow data) {
        mUserID = data.mUserID;
        mMessageID = data.mMessageID;
        mFlag = data.mFlag;
    }
}