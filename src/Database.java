import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:tictactoe.db";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
        return connection;
    }

    public static void initialize() {
        String sql = "CREATE TABLE IF NOT EXISTS scores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "player_name TEXT NOT NULL UNIQUE, " +
                "wins INTEGER NOT NULL DEFAULT 0, " +
                "losses INTEGER NOT NULL DEFAULT 0, " +
                "draws INTEGER NOT NULL DEFAULT 0" +
                ");";

        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
        }
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Failed to close database: " + e.getMessage());
        }
    }
}