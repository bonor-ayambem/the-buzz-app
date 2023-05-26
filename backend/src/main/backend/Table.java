import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Table<T> {
    private String name;
    private Database db;

    private String deleteStatement;
    private String updateStatement;

    public Table(String tName, Database db){
        name = tName;
        this.db = db;
        deleteStatement = "DELETE FROM " + name + " WHERE row_id = ?";
        updateStatement = "UPDATE " + name + " SET content = ? WHERE row_id = ?";
    }

    public String getName(){
        return name;
    }

    public abstract void create() throws SQLException;

    public void drop() {
        String statement = "DROP TABLE IF EXISTS " + name + " CASCADE;";
        try {
            db.mConnection.prepareStatement(statement).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insertRow(ArrayList<String> content) {
        StringJoiner statement = new StringJoiner(", ", "INSERT INTO " + name + " VALUES (default,", ")");

        for (int i = 0; i < content.size(); i++) {
            statement.add(" ?");
        }

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

    public int updateRow(int row_id, String content) {
        try {
            db.mUpdateRow = db.mConnection.prepareStatement(updateStatement);
            db.mUpdateRow.setString(1, content);
            db.mUpdateRow.setInt(2, row_id);
            return db.mUpdateRow.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteRow(int row_id) {
        try {
            db.mDeleteRow = db.mConnection.prepareStatement(deleteStatement);
            db.mDeleteRow.setInt(1, row_id);
            return db.mDeleteRow.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public abstract ArrayList<T> selectAll(int id) throws SQLException;
    public abstract T selectOne(int id) throws SQLException;
}
