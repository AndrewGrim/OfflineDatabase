import javafx.collections.ObservableList;
import java.sql.*;


public class DatabaseOperations {

    private Connection connect() {
        // SQLite connection string, connects to this specific database
        String url = "jdbc:sqlite:MonsterHunter.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewMonstersTable() {
        // SQL statement for creating a new monsters table
        String sql = "CREATE TABLE IF NOT EXISTS monsters (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	species text NOT NULL,\n"
                + "	generation text NOT NULL,\n"
                + " size text NOT NULL\n"
                + ");";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createNewTitleIconsTable() {
        // SQL statement for creating a new title icons table
        String sql = "CREATE TABLE IF NOT EXISTS titleIcons (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + " path text NOT NULL,\n"
                + " icon integer NOT NULL\n"
                + ");";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printAllMonsters() {
        // used for printing all monsters rows to the console
        String sql = "SELECT id, name, species, generation, size FROM monsters";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("species") + "\t" +
                        rs.getString("generation") + "\t" +
                        rs.getString("size"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertMonster(String name, String species, String generation, String size) {
        // insert a single monster with the specified data into the monsters table
        String sql = "INSERT INTO monsters(name,species,generation,size) VALUES(?,?,?,?)";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, species);
            pstmt.setString(3, generation);
            pstmt.setString(4, size);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String table, int id) {
        // delete the specified id from the specified table
        String sql = "DELETE FROM " + table + " WHERE id = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setTableView(ObservableList<Monster> oblist) {
        // populates the tableview with all the monster data using the monster class, loops untill all db rows added
        String sql = "SELECT id, name, species, generation, size FROM monsters";

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                oblist.add(new Monster(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("generation"),
                        rs.getString("size")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateMonster(String name, String species, String generation, String size, int id) {
        // updates the database with the new details of the specified monster 
        String sql = "UPDATE monsters SET name = ? , "
        + "species = ? , "
        + "generation = ? , "
        + "size = ? "
        + "WHERE id = ?"; // commmas hello??

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, species);
            pstmt.setString(3, generation);
            pstmt.setString(4, size);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getMonsterName(int id) {
        // returns the monsters name by looking up its id
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

    public String getDefaultIconPath() {
        // checks the titleIcons table for a defualt icon and returns its path
        int currentIcon = 1;
        String sql = "SELECT path FROM titleIcons WHERE icon = ?";
        String iconPath = null;

        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, currentIcon);
            ResultSet rs = pstmt.executeQuery();
            iconPath = rs.getString("path");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return iconPath;
    }

    public String getDefaultIconName() {
        // checks the titleIcons table for a defualt icon and returns its name
        String currentIconName = null;
        int defaultName = 1;
        String sql = "SELECT name FROM titleIcons WHERE icon = ?";

        try (Connection conn = this.connect()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, defaultName);
            ResultSet rs = pstmt.executeQuery();
            currentIconName = rs.getString("name");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return currentIconName;
    }

    public void updateDefaultIcon(String defaultName) {
        // changes from the current default icon to a new one, swaps to the other one whenever called
        String sql = "UPDATE titleIcons SET icon = ? WHERE name = ?";
        int defaultIcon = 1;
        int tempIcon = 2;
        int switchIcon = 0;

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tempIcon);
            pstmt.setString(2, defaultName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "UPDATE titleIcons SET icon = ? WHERE icon = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, defaultIcon);
            pstmt.setInt(2, switchIcon);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        sql = "UPDATE titleIcons SET icon = ? WHERE icon = ?";

        try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, switchIcon);
            pstmt.setInt(2, tempIcon);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
