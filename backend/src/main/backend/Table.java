import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Table {
    private String name;

    private Connection mConnection;
    private PreparedStatement mCreateTable;
    private PreparedStatement mDropTable;
    private PreparedStatement mDeleteRow;
    private PreparedStatement mInsertRow;
    private PreparedStatement mUpdateRow;
    // Other prepared statements...

    public Table(String tName){
        name = tName;
    }

    public String getName(){
        return name;
    }

    public void create(Database db) {
        try {
            if(name.equals("tblMessages")) {
                db.mCreateTable = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblMessages (row_id SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL, content VARCHAR(500) NOT NULL, user_id INTEGER REFERENCES tblUsers)");
            }
            else if(name.equals("tblComment")) {
                db.mCreateTable =  db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblComment (row_id SERIAL PRIMARY KEY, message_id INTEGER REFERENCES tblMessages, user_id INTEGER REFERENCES tblUsers, content VARCHAR(500) NOT NULL)");
            }
            else if(name.equals("tblUsers")) {
                db.mCreateTable = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblUsers (row_id SERIAL PRIMARY KEY, user_name VARCHAR(500) NOT NULL, first_name VARCHAR(500) NOT NULL, last_name VARCHAR(500) NOT NULL, email VARCHAR(500) NOT NULL)");
            }
            else if(name.equals("tblLikes")) {
                db.mCreateTable = db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblLikes (row_id SERIAL PRIMARY KEY, user_id INTEGER REFERENCES tblUsers, message_id INTEGER REFERENCES tblMessages, content INTEGER)");    
            }

            db.mCreateTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop(Database db) {
        String statement = "DROP TABLE IF EXISTS " + name + " CASCADE;"; // Added space before CASCADE
        try {
            db.mDropTable = db.mConnection.prepareStatement(statement);
            db.mDropTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertRow(Database db, ArrayList<String> content) {
        StringBuilder statement = new StringBuilder("INSERT INTO " + name + " VALUES (default");

        for (int i = 0; i < content.size(); i++) {
            statement.append(", ?");
        }
        statement.append(")");

        try {
            db.mInsertRow = db.mConnection.prepareStatement(statement.toString());
            for (int i = 0; i < content.size(); i++) {
                db.mInsertRow.setString(i + 1, content.get(i));
            }
            return db.mInsertRow.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateRow(Database db, int row_id, String content) {
        String statement = "UPDATE tblMessages SET content = ? WHERE row_id = ?";
        try {
            db.mUpdateRow = db.mConnection.prepareStatement(statement);
            db.mUpdateRow.setString(1, content);
            db.mUpdateRow.setInt(2, row_id);
            return db.mUpdateRow.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteRow(Database db, int row_id) {
        String statement = "DELETE FROM " + name + " WHERE row_id = ?";
        try {
            db.mDeleteRow = db.mConnection.prepareStatement(statement);
            db.mDeleteRow.setInt(1, row_id);
            return db.mDeleteRow.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
