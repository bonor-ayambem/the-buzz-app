public class LikesTable extends Table {
    public void create(){
        try {
            super.db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblLikes (row_id SERIAL PRIMARY KEY, user_id INTEGER REFERENCES tblUsers, message_id INTEGER REFERENCES tblMessages, content INTEGER)").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}