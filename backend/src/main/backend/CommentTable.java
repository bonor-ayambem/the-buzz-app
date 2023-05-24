public class CommentTable extends Table {
    public void create(){
        try {
            super.db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblComment (row_id SERIAL PRIMARY KEY, message_id INTEGER REFERENCES tblMessages, user_id INTEGER REFERENCES tblUsers, content VARCHAR(500) NOT NULL)").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}