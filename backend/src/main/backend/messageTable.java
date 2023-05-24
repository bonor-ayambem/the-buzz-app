public class messageTable extends Table<MessageRow> {
    public void create() throws SQLException{
        try {
            super.db.mConnection.prepareStatement("CREATE TABLE IF NOT EXISTS tblMessages (row_id SERIAL PRIMARY KEY, subject VARCHAR(50) NOT NULL, content VARCHAR(500) NOT NULL, user_id INTEGER REFERENCES tblUsers)").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<MessageRow> selectAll(int id) throws SQLException{
        ArrayList<MessageRow> res = new ArrayList<MessageRow>();
        try {
            ResultSet rs = db.mConnection.prepareStatement("SELECT message_id, subject, content, user_id FROM tblMessages").executeQuery();
            while (rs.next()) {
                res.add(new MessageRow(rs.getInt("message_id"), rs.getString("subject"), rs.getString("content"), rs.getInt("user_id")));
            }
            rs.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MessageRow selectOne(int id) throws SQLException {
        MessageRow res = null;
        try {
            db.mConnection.prepareStatement("SELECT * from tblMessages WHERE message_id = ?").setInt(1, id);
            ResultSet rs = db.mConnection.prepareStatement("SELECT * from tblMessages WHERE message_id = ?").executeQuery();
            if (rs.next()) {
                res = new MessageRow(rs.getInt("message_id"), rs.getString("subject"),  rs.getString("content"), rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}