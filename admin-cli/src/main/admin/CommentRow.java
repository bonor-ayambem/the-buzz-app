package edu.lehigh.cse216.alb323.admin;

public class CommentRow {

    /**
     * The unique identifier associated with the corisponding message
     */
    public int mCommentID;

    /**
     * The unique identifier associated with the corisponding message
     */
    public final int mMessageID;

    /**
     * The unique identifier associated with the user who made the comment
     */
    public final int mUserID;

    /**
     * The contents of the comment
     */
    public final String mContent;

    /**
     * Create a new CommentRow
     * 
     * @param id        The unique identifier associated with this element.
     * @param messageId he unique identifier associated with the corisponding
     *                  message
     * @param user      The unique identifier associated with the user who made the
     *                  comment
     * @param content   The contents of the comment
     */
    CommentRow(int message_id, int user_id, String content) {
        mMessageID = message_id;
        mUserID = user_id;
        mContent = content;
    }

    CommentRow(int comment_id, int messageId, int user_id, String content) {
        mCommentID = comment_id;
        mMessageID = messageId;
        mUserID = user_id;
        mContent = content;
    }

    /**
     * Copy constructor to create one CommentRow from another
     * 
     * @param data CommentRow that is being coppied
     */
    CommentRow(CommentRow data) {
        mMessageID = data.mMessageID;
        mUserID = data.mUserID;
        mContent = data.mContent;
    }
}
