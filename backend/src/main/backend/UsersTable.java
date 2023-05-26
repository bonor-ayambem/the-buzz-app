public class UsersTable extends Table {
    public void create(){
        try {
            super.db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblUsers (row_id SERIAL PRIMARY KEY, user_name VARCHAR(500) NOT NULL, first_name VARCHAR(500) NOT NULL, last_name VARCHAR(500) NOT NULL, email VARCHAR(500) NOT NULL)").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}