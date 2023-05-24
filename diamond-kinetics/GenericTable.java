import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenericTable<T> {
    private String name;
    private Database db;

    public Table(String tName, Database db){
        name = tName;
        this.db = db;
    }

    public String getName(){
        return name;
    }

    public void create(String createStatement) throws SQLException {
        try {
            db.mConnection.prepareStatement(createStatement).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void drop() {
        String statement = "DROP TABLE IF EXISTS " + name + " CASCADE;";
        try {
            db.mConnection.prepareStatement(statement).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insert(T item, String insertStatement) throws SQLException {
        try {
            PreparedStatement preparedStatement = db.mConnection.prepareStatement(insertStatement);
            // Set the values in the prepared statement based on the item's properties
            // preparedStatement.setXXX(index, value);
            // Example: preparedStatement.setString(1, item.getName());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(T item, String updateStatement) throws SQLException {
        try {
            PreparedStatement preparedStatement = db.mConnection.prepareStatement(updateStatement);
            // Set the values in the prepared statement based on the item's properties
            // preparedStatement.setXXX(index, value);
            // For example: preparedStatement.setString(1, item.getName());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(int id, String deleteStatement) throws SQLException {
        try {
            PreparedStatement preparedStatement = db.mConnection.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<T> selectAll(String selectAllStatement) throws SQLException {
        ArrayList<T> res = new ArrayList<>();
        try {
            ResultSet rs = db.mConnection.prepareStatement(selectAllStatement).executeQuery();
            while (rs.next()) {
                // create an instance of the specific table class and populate its properties from the result set
                T item = createInstance(rs);
                res.add(item);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public T selectOne(int id, String selectOneStatement) throws SQLException {
        T res = null;
        try {
            PreparedStatement preparedStatement = db.mConnection.prepareStatement(selectOneStatement);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                // Create an instance of the specific table class and populate its properties from the result set
                res = createInstance(rs);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    private T createInstance(ResultSet rs) {
        // Create an instance of the specific table class and populate its properties from the result set
        // for example: return new messageTable(rs.getInt("id"), rs.getString("name"), rs.getInt("value"));
        return null;
    }
}
