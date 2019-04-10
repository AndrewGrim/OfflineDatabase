import javafx.collections.ObservableList;
import java.sql.*;


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
                + " size text NOT NULL\n"
                + ");";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printAll() {
        String sql = "SELECT id, name, gender, species, generation, size FROM monsters";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("gender") + "\t" +
                        rs.getString("species") + "\t" +
                        rs.getString("generation") + "\t" +
                        rs.getString("size"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(String name, String gender, String species, String generation, String size) {
        String sql = "INSERT INTO monsters(name,gender,species,generation,size) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setString(3, species);
            pstmt.setString(4, generation);
            pstmt.setString(5, size);
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

    public void setTableView(ObservableList<Monster> oblist) {
        String sql = "SELECT id, name, gender, species, generation, size FROM monsters";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                oblist.add(new Monster(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("species"),
                        rs.getString("generation"),
                        rs.getString("size")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void update(String name, String gender, String species, String generation, String size, int id) {
        String sql = "UPDATE monsters SET name = ? , "
        + "gender = ? , "
        + "species = ? , "
        + "generation = ? , "
        + "size = ? "
        + "WHERE id = ?"; // commmas hello??

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setString(3, species);
            pstmt.setString(4, generation);
            pstmt.setString(5, size);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getMonsterName(int id) {
        String sql = "SELECT name FROM monsters WHERE id = ?";
        String monsterName = null;

        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            monsterName = rs.getString("name");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return monsterName;
    }
}
