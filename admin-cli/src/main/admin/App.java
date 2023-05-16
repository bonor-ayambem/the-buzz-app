package edu.lehigh.cse216.alb323.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;
//iven
/**
 * App is our basic admin app. For now, it is a demonstration of the six key
 * operations on a database: connect, insert, update, query, delete, disconnect
 */
public class App {

    /**
     * Print the menu for our program
     */
    static void menu() {
        System.out.println("Main Menu");
        System.out.println("  [T] Create a table");
        System.out.println("  [D] Drop a table");
        System.out.println("  [1] Query for a specific row");
        System.out.println("  [*] Query for all rows");
        System.out.println("  [-] Delete a row");
        System.out.println("  [+] Insert a new row");
        System.out.println("  [~] Update a table row");
        System.out.println("  [L] Update Likes");
        System.out.println("  [q] Quit Program");
        System.out.println("  [?] Help (this message)");
    }

    // ADDED IN PHASE 2
    /**
     * Print the table select screen for our program
     */
    static void tableMenu() {
        System.out.println("Table Menu");
        System.out.println("  [M] For tblMessages");
        System.out.println("  [C] For tblComments");
        System.out.println("  [L] For tblLikes");
        System.out.println("  [U] For tblUsers");
        System.out.println("  [A] for tblMFiles");
        System.out.println("  [B] for tblCFiles");
        System.out.println("  [q] Go Back to Main Menu");

    }

    // ADDED IN PHASE 2
    /**
     * Print the like select screen for our program
     */
    static void likeMenu() {
        System.out.println("Like Menu");
        System.out.println("Enter one of the following characters:");
        System.out.println(" L  to update to Like");
        System.out.println(" D  to update to Like");
        System.out.print(":> ");
        System.out.println("  [q] Go Back to Main Menu");

    }

    /**
     * Ask the user to enter a menu option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char prompt(BufferedReader in) {
        // The valid actions:
        String actions = "TD1*-+~udq?";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.println("Enter one of the following characters:");
            System.out.println("T to CREATE table");
            System.out.println("D to DELETE table");
            System.out.println("1 to QUERY ONE row");
            System.out.println("* to QUERY ALL rows");
            System.out.println("- to DELETE a row");
            System.out.println("+ to INSERT a row");
            System.out.println("~ to UPDATE a table row");
            System.out.println("u to UPVOTE a message");
            System.out.println("d to DOWNVOTE a message");
            System.out.println("q to QUIT Program");
            System.out.println("? for HELP");
            System.out.print(":> ");

            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    // ADDED IN PHASE 2
    /**
     * Ask the user to enter a table option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen menu option
     */
    static char tablePrompt(BufferedReader in) {
        // The valid actions:
        String actions = "MCLUmcluqAB"; // Marvel Cinematic Linear Universe

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.println("Enter one of the following characters:");
            System.out.println(" M  For tblMessages");
            System.out.println(" C  For tblComments");
            System.out.println(" L  For tblLikes");
            System.out.println(" U  For tblUsers");
            System.out.println(" A  For tblMFiles"); // message files
            System.out.println(" B  For tblCFiles"); // comment files
            System.out.println(" U  For tblUsers");
            System.out.println(" q  To go Back to Main Menu");
            System.out.print(":> ");

            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    // ADDED IN PHASE 2
    /**
     * Ask the user to enter a like option; repeat until we get a valid option
     * 
     * @param in A BufferedReader, for reading from the keyboard
     * 
     * @return The character corresponding to the chosen like option
     */
    static char likePrompt(BufferedReader in) {
        // The valid actions:
        String actions = "UDud";

        // We repeat until a valid single-character option is selected
        while (true) {
            System.out.println("Enter one of the following characters:");
            System.out.println(" U  to update to Like");
            System.out.println(" D  to update to Downvote");
            System.out.print(":> ");

            String action;
            try {
                action = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            if (action.length() != 1)
                continue;
            if (actions.contains(action)) {
                return action.charAt(0);
            }
            System.out.println("Invalid Command");
        }
    }

    /**
     * Ask the user to enter a String message
     * 
     * @param in      A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The string that the user provided. May be "".
     */
    static String getString(BufferedReader in, String message) {
        String s;
        try {
            System.out.print(message + " :> ");
            s = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return s;
    }

    /**
     * Ask the user to enter an integer
     * 
     * @param in      A BufferedReader, for reading from the keyboard
     * @param message A message to display when asking for input
     * 
     * @return The integer that the user provided. On error, it will be -1
     */
    static int getInt(BufferedReader in, String message) {
        int i = -1;
        try {
            System.out.print(message + " :> ");
            i = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * The main routine runs a loop that gets a request from the user and processes
     * it
     * 
     * @param argv Command-line options. Ignored by this program.
     */
    public static void main(String[] argv) {
        // get the Postgres configuration from the environment
        Map<String, String> env = System.getenv();
        // String ip = env.get("POSTGRES_IP");
        // String port = env.get("POSTGRES_PORT");
        // String user = env.get("POSTGRES_USER");
        // String pass = env.get("POSTGRES_PASS");
        String db_url = env.get("DATABASE_URL");
        // Get a fully-configured connection to the database, or exit
        // immediately

        // Database db = Database.getDatabase(ip, port, user, pass);
        /*
         * Phase 1: I commented out the original dataStore variable and replaced it with
         * the dataStore variable below
         */

        Database db = Database.getDatabase(db_url);

        /*
         * char tableChoice = tablePrompt(choice); if(tableChoice == 'M'|| tableChoice
         * == 'm'){ db.createTableMessages(); } else if(tableChoice == 'C'|| tableChoice
         * == 'c'){ db.createTableComment(); } else if(tableChoice == 'L'|| tableChoice
         * == 'l'){ db.createTableLikes(); } else if(tableChoice == 'U'|| tableChoice ==
         * 'u'){ db.createTableUser(); }
         */

        if (db == null)
            return;

        // Start our basic command-line interpreter:
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        // Have another interpreter distinct for tables
        BufferedReader choice = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            // Get the user's request, and do it
            //
            // NB: for better testability, each action should be a separate
            // function call
            char action = prompt(in);
            if (action == '?') {
                menu();
            } else if (action == 'q') {
                break;
            } else if (action == 'T') {

                char tableChoice = tablePrompt(choice);
                if (tableChoice == 'M' || tableChoice == 'm')
                    db.createTableMessages();

                else if (tableChoice == 'C' || tableChoice == 'c')
                    db.createTableComment();

                else if (tableChoice == 'L' || tableChoice == 'l')
                    db.createTableLikes();

                else if (tableChoice == 'U' || tableChoice == 'u')
                    db.createTableUser();

                else if (tableChoice == 'A' || tableChoice == 'a')
                    db.createTableMessageFile();

                else if (tableChoice == 'B' || tableChoice == 'b')
                    db.createTableCommentFile();

                else if (action == 'q') {
                    break;
                }

            } else if (action == 'D') {

                char tableChoice = tablePrompt(choice);
                if (tableChoice == 'M' || tableChoice == 'm')
                    db.dropTableMessages();

                else if (tableChoice == 'C' || tableChoice == 'c')
                    db.dropTableComment();

                else if (tableChoice == 'L' || tableChoice == 'l')
                    db.dropTableLikes();

                else if (tableChoice == 'U' || tableChoice == 'u')
                    db.dropTableUser();

                else if (tableChoice == 'A' || tableChoice == 'a')
                    db.dropTableMessageFile();

                else if (tableChoice == 'B' || tableChoice == 'b')
                    db.dropTableCommentFile();

                else if (action == 'q') {
                    break;
                }

            } else if (action == '1') {

                char tableChoice = tablePrompt(choice);
                if (tableChoice == 'M' || tableChoice == 'm') {
                    int mid = getInt(in, "Enter the row ID");
                    if (mid == -1)
                        continue;
                    MessageRow mres = db.selectOneMessage(mid);
                    if (mres != null) {
                        System.out.println("  [" + mres.mMessageID + "] " + mres.mTitle);
                        System.out.println("  --> " + mres.mContent);
                        System.out.println("  --> " + mres.mUser);
                    }
                }

                else if (tableChoice == 'C' || tableChoice == 'c') {
                    int id = getInt(in, "Enter the message ID");
                    int cid = getInt(in, "Enter the comment ID");
                    if (cid == -1)
                        continue;
                    // CommentRow cres = db.selectOneComment(id, cid);
                    // if (cres != null) {
                    // System.out.println(" [" + cres.mMessageID + "] " + cres.mId);
                    // System.out.println(" --> " + cres.mContent);
                    // System.out.println(" --> " + cres.mUser);
                    // }
                }

                else if (tableChoice == 'L' || tableChoice == 'l') {

                    System.out.println("Error, not valid for this table");
                    /*
                     * Code that would work if selectOneLike was made, but not sure if we want this
                     * int lid = getInt(in, "Enter the row ID"); if (lid == -1) continue; LikesRow
                     * lres = db.selectOneLike(lid); if (lres != null) { System.out.println("  [" +
                     * lres.mMessageID + "] " + lres.mFlag); System.out.println("  --> " +
                     * lres.mUser); } }
                     */

                }

                else if (tableChoice == 'U' || tableChoice == 'u') {

                    String uid = getString(in, "Enter the the email in the same format you sign in with.");
                    if (uid == "")
                        continue;
                    UserRow ures = db.selectOneUser(uid);
                    if (ures != null) {
                        System.out.println("  --> " + ures.mUser);
                        System.out.println("  --> " + ures.mFirstName);
                        System.out.println("  --> " + ures.mLastName);
                        System.out.println("  --> " + ures.mEmail);
                    }
                } else if (action == 'q') {
                    break;
                }

            } else if (action == '*') {

                char tableChoice = tablePrompt(choice);
                if (tableChoice == 'M' || tableChoice == 'm') {
                    ArrayList<MessageRow> res = db.selectAllMessages();
                    if (res == null)
                        continue;
                    System.out.println("  Current Database Contents");
                    System.out.println("  -------------------------");
                    for (MessageRow rd : res) {
                        System.out.println("  [" + rd.mMessageID + "] " + rd.mTitle + " by: " + rd.mUser);
                    }
                } else if (tableChoice == 'C' || tableChoice == 'c') {
                    int cid = getInt(in, "Enter the ID of the Message you wish to retreive comments from");
                    ArrayList<CommentRow> res = db.selectAllComments(cid);
                    if (res == null)
                        continue;
                    System.out.println("  Current Comments on message " + cid);
                    System.out.println("  -------------------------");
                    for (CommentRow rd : res) {
                        System.out.println("  [" + rd.mMessageID + "] " + rd.mUserID + " said: ");
                        System.out.println(rd.mContent);
                    }
                } else if (tableChoice == 'L' || tableChoice == 'l') {
                    int cid = getInt(in, "Enter the ID of the Message you wish to retreive Likes from");
                    ArrayList<LikesRow> res = db.selectAllLikes(cid);
                    if (res == null)
                        continue;
                    System.out.println("  Current likes on message " + cid);
                    System.out.println("  -------------------------");
                    for (LikesRow rd : res) {
                        System.out.println("  [" + rd.mFlag + "] " + rd.mUserID);
                    }
                } else if (tableChoice == 'U' || tableChoice == 'u') {

                    ArrayList<UserRow> res = db.selectAllUsers();
                    if (res == null)
                        continue;
                    System.out.println("  Current users ");
                    System.out.println("  -------------------------");
                    for (UserRow rd : res) {
                        System.out.println("User: " + rd.mUser + "       Email : " + rd.mEmail);
                    }
                } else if (tableChoice == 'A' || tableChoice == 'a') {
                    ArrayList<FileRow> res = db.selectAllMessageFiles();
                    if (res == null)
                        continue;
                    System.out.println("  Current Message Files Contents");
                    System.out.println("  ------------------------------");
                    for (FileRow rd : res) {
                        System.out.println("  [" + rd.mFileID + "] " + rd.mFileName + " by: " + rd.mUserName);
                    }
                } else if (tableChoice == 'B' || tableChoice == 'b') {
                    ArrayList<FileRow> res = db.selectAllCommentFiles();
                    if (res == null)
                        continue;
                    System.out.println(" Current Comment Files Contents");
                    System.out.println("  -------------------------");
                    for (FileRow rd : res) {
                        System.out.println("  [" + rd.mFileID + "] " + rd.mFileName + " by: " + rd.mUserName);
                    }
                }

                else if (action == 'q') {
                    break;
                }

            } else if (action == '-') {

                char tableChoice = tablePrompt(choice);
                if (tableChoice == 'M' || tableChoice == 'm') {
                    int id = getInt(in, "Enter the row ID");
                    if (id == -1)
                        continue;
                    int res = db.deleteRowMessage(id);
                    if (res == -1)
                        continue;
                    System.out.println("  " + res + " rows deleted");
                } else if (tableChoice == 'C' || tableChoice == 'c') {
                    int id = getInt(in, "Enter the ID of the message the comment was left on");
                    if (id == -1)
                        continue;
                    int cid = getInt(in, "Enter the ID of the comment");
                    if (cid == -1)
                        continue;
                    // int res = db.deleteRowComment(id, cid);
                    // if (res == -1)
                    // continue;
                    // System.out.println(" " + res + " rows deleted");
                } else if (tableChoice == 'L' || tableChoice == 'l') {

                    System.out.println("Invalid operation for row");
                    /*
                     * int id = getInt(in, "Enter the ID of the message the like was left on"); if
                     * (id == -1) continue; int lid = getInt(in, "Enter the ID of the like"); if
                     * (lid == -1) continue; int res = db.deleteRowLike(id, lid); if (res == -1)
                     * continue; System.out.println("  " + res + " rows deleted"); }
                     */
                } else if (tableChoice == 'U' || tableChoice == 'u') {
                    String email = getString(in, "Enter the email of the user you wish to delete");
                    if (email == "")
                        continue;
                    int res = db.deleteRowUser(email);
                    if (res == -1)
                        continue;
                    System.out.println("  " + res + " rows deleted");
                } else if (action == 'q') {
                    break;
                }
            } else if (action == '+') {

                char tableChoice = tablePrompt(choice);
                if (tableChoice == 'M' || tableChoice == 'm') {
                    String subject = getString(in, "Enter the subject");
                    String message = getString(in, "Enter the message");
                    String author = getString(in, "Enter the user email");
                    // int likes = 0;
                    if (subject.equals("") || message.equals("") || author.equals(""))
                        continue;
                    int res = db.insertRowMessage(subject, message, author);
                    System.out.println(res + " rows added");
                } else if (tableChoice == 'C' || tableChoice == 'c') {
                    int id = getInt(in, "Enter the post of what you want to comment on");
                    String message = getString(in, "Enter the message");
                    String user = getString(in, "Enter the user email");
                    // int likes = 0;
                    if (user.equals("") || message.equals("") || id == (-1))
                        continue;
                    int res = db.insertRowComment(id, message, user);
                    System.out.println(res + " rows added");
                } else if (tableChoice == 'L' || tableChoice == 'l') {
                    System.out.println("Not Valid for this table");
                } else if (tableChoice == 'U' || tableChoice == 'u') {
                    String firstN = getString(in, "Enter your first name");
                    String lastN = getString(in, "Enter your last name");
                    String email = getString(in, "Enter your email");
                    // int likes = 0;
                    if (email.equals("") || firstN.equals("") || lastN.equals(""))
                        continue;
                    // int res = db.insertRowUser(firstN, lastN, email);
                    // System.out.println(res + " rows added");
                } else if (tableChoice == 'A' || tableChoice == 'a') {
                    int mFileID = getInt(in, "Enter File ID");
                    String mFileType = getString(in, "Enter File Type");
                    String mUserName = getString(in, "Enter User Name");
                    String mFileHash = getString(in, "Enter File Hash");

                    int res = db.insertRowCommentFile(mFileID, mFileType, mUserName, mFileHash);
                    System.out.println(res + " rows added");
                    // int res = db.insertRowUser(firstN, lastN, email);
                    // System.out.println(res + " rows added");
                } else if (tableChoice == 'B' || tableChoice == 'b') {
                    int mFileID = getInt(in, "Enter File ID");
                    String mFileType = getString(in, "Enter File Type");
                    String mUserName = getString(in, "Enter User Name");
                    String mFileHash = getString(in, "Enter File Hash");
                    int res = db.insertRowCommentFile(mFileID, mFileType, mUserName, mFileHash);
                    System.out.println(res + " rows added");
                } else if (action == 'q') {
                    break;
                }

            } /*
               * else if (action == 'u') { int id = getInt(in, "Enter the row ID"); if (id ==
               * -1) continue; int res = db.upvoteRow(id); System.out.println(res +
               * " rows updated"); } else if (action == 'd') { int id = getInt(in,
               * "Enter the row ID"); if (id == -1) continue; int res = db.downvoteRow(id);
               * System.out.println(res + " rows updated"); }
               */
            else if (action == '~') {
                char tableChoice = tablePrompt(choice);
                if (tableChoice == 'M' || tableChoice == 'm') {
                    int id = getInt(in, "Enter the row ID :> ");
                    if (id == -1)
                        continue;
                    String newMessage = getString(in, "Enter the new message");
                    int res = db.updateOneMessage(id, newMessage);
                    if (res == -1)
                        continue;
                    System.out.println("  " + res + " rows updated");
                } else if (tableChoice == 'C' || tableChoice == 'c') {
                    System.out.println("Invalid operation for row");
                } else if (tableChoice == 'L' || tableChoice == 'l') {

                    int idm = getInt(in, "Enter the ID of the message you wish to update a vote of");
                    String userm = getString(in, "Enter the email of the user who voted.");
                    char likeChoice = likePrompt(choice);
                    if (likeChoice == 'u') {
                        int id = getInt(in, "Enter the row ID");
                        if (id == -1)
                            continue;
                        // int res = db.upvoteRow(id);
                        // System.out.println(res + " rows updated");
                    } else if (likeChoice == 'd') {
                        int id = getInt(in, "Enter the row ID");
                        if (id == -1)
                            continue;
                        // int res = db.downvoteRow(id);
                        // System.out.println(res + " rows updated");
                    }

                } else if (tableChoice == 'U' || tableChoice == 'u') {
                    System.out.println("Invalid operation for row");
                } else if (action == 'q') {
                    break;
                }

            }
        }
        // Always remember to disconnect from the database when the program
        // exits
        db.disconnect();
    }
}