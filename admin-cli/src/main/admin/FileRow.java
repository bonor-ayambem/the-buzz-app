package edu.lehigh.cse216.alb323.admin;

/**
 * MessageRow holds a row of information. A row of information consists of an
 * identifier, strings for a "title" and "content", and a creation date.
 * 
 * Because we will ultimately be converting instances of this object into JSON
 * directly, we need to make the fields public. That being the case, we will not
 * bother with having getters and setters... instead, we will allow code to
 * interact with the fields directly.
 */
public class FileRow {

    /**
     * The id of the file on this row
     */
    public final int mFileID;

    /**
     * The id of the message/comment associated with the file on this row
     */
    public int mMessageID;

    /**
     * The content for this row of data
     */
    public String mFileType;

    /**
     * The creator of this row of data
     */
    public String mFileName;

    /**
     * The creator of this row of data
     */
    public String mUserName;

    /**
     * The creator of this row of data
     */
    public String mFileHash;

    /**
     * Create a new FileRow with the provided id and title/content, and a creation
     * date based on the system clock at the time the constructor was called
     * 
     * @param id      The id to associate with this row. Assumed to be unique
     *                throughout the whole program.
     * 
     * @param title   The title string for this row of data
     * 
     * @param content The content string for this row of data
     * 
     * @param hash    The hash string for this row of data
     */
    FileRow(int file_id, int message_id, String file_type, String file_name, String user_name, String file_hash) {
        mFileID = file_id;
        mMessageID = message_id;
        mFileType = file_type;
        mFileName = file_name;
        mUserName = user_name;
        mFileHash = file_hash;

    }

    /**
     * Copy constructor to create one MessageRow from another
     */
    FileRow(FileRow data) {
        mFileID = data.mFileID;
        mMessageID = data.mMessageID;
        mFileType = data.mFileType;
        mFileName = data.mFileName;
        mUserName = data.mUserName;
        mFileHash = data.mFileHash;
    }
}