import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class DatabaseOperations {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:MonsterHunter.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS monsters (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + " gender text NOT NULL, \n"
                + "	species text NOT NULL\n"
                + "	generation text NOT NULL\n"
                + "	icon text NOT NULL\n"
                + ");";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement()){
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printAll(){
        String sql = "SELECT id, name, gender, species, generation, icon FROM monsters";

        try (Connection conn = this.connect(); Statement stmt  = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("gender") + "\t" +
                        rs.getString("species") + "\t" +
                        rs.getString("icon"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String name, String gender, String species, String generation, String icon) {
        String sql = "INSERT INTO monsters(name,gender,species,generation,icon) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setString(3, species);
            pstmt.setString(4, generation);
            pstmt.setString(5, icon);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM monsters WHERE id = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setTableView(ObservableList<Monster> oblist){
        String sql = "SELECT id, name, gender, species, generation, icon FROM monsters";
        // "select * from data" instead of sql??

        try (Connection conn = this.connect(); Statement stmt  = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                oblist.add(new Monster(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("species"),
                        rs.getString("generation"),
                        rs.getString("icon")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}