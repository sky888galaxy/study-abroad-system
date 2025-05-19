package dao;

import java.sql.*;

public class MajorDAO {
    private static final String URL = "jdbc:sqlite:database/study_abroad.db";

    public static boolean tryOccupySlot(String university, String major) {
        String query = "SELECT quota_total, quota_used FROM majors WHERE university_name = ? AND major_name = ?";
        String update = "UPDATE majors SET quota_used = quota_used + 1 WHERE university_name = ? AND major_name = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, university);
            stmt.setString(2, major);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("quota_total");
                int used = rs.getInt("quota_used");
                if (used < total) {
                    try (PreparedStatement updateStmt = conn.prepareStatement(update)) {
                        updateStmt.setString(1, university);
                        updateStmt.setString(2, major);
                        updateStmt.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}