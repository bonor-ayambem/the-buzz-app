package edu.lehigh.cse216.alb323.backend;

/**
 * LikesRow holds a row of information.  A row of information consists of
 * an identifier, strings for a "title" and "content", and a creation date.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public.  That being the case, we will
 * not bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class LikesData {

    /**
     * The content for this row of data
     */
    public int mMessageID;

    /**
     * The content for this row of data
     */
    public int mLikes;


    LikesData(int message_id, int likes) {
        mMessageID = message_id;
        mLikes = likes;
    }

    /**
     * Copy constructor to create one LikesRow from another
     */
    LikesData(LikesData data) {
        mMessageID = data.mMessageID;
        mLikes = data.mLikes;
    }
}