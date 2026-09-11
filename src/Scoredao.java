import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Scoredao {

    private static void ensurePlayerExists(String name) {
        String sql = "INSERT OR IGNORE INTO scores (player_name, wins, losses, draws) VALUES (?, 0, 0, 0)";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to add player: " + e.getMessage());
        }
    }

    public static void recordWin(String winnerName, String loserName) {
        ensurePlayerExists(winnerName);
        incrementColumn(winnerName, "wins");

        if (loserName != null) {
            ensurePlayerExists(loserName);
            incrementColumn(loserName, "losses");
        }
    }

    public static void recordDraw(String playerXName, String playerOName) {
        ensurePlayerExists(playerXName);
        ensurePlayerExists(playerOName);
        incrementColumn(playerXName, "draws");
        incrementColumn(playerOName, "draws");
    }

    private static void incrementColumn(String name, String column) {
        // column is never user input, always one of "wins"/"losses"/"draws" from within this class
        String sql = "UPDATE scores SET " + column + " = " + column + " + 1 WHERE player_name = ?";
        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to update score: " + e.getMessage());
        }
    }

    public static List<Playerscore> getLeaderboard() {
        List<Playerscore> leaderboard = new ArrayList<>();
        String sql = "SELECT player_name, wins, losses, draws FROM scores ORDER BY wins DESC, draws DESC";

        try (Statement stmt = Database.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                leaderboard.add(new Playerscore(
                        rs.getString("player_name"),
                        rs.getInt("wins"),
                        rs.getInt("losses"),
                        rs.getInt("draws")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Failed to load leaderboard: " + e.getMessage());
        }

        return leaderboard;
    }
}