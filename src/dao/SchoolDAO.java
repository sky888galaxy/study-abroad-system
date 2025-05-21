package dao;
// src/dao/SchoolDAO.java
import util.DatabaseConnection;
import java.sql.*;


public class SchoolDAO {
    private Connection conn;

    public SchoolDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();
    }

    // 添加学校
    public boolean addSchool(String schoolName, String country) throws SQLException {
        String query = "INSERT INTO schools (school_name, country) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, schoolName);
            stmt.setString(2, country);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // 插入成功
        }
    }

    // 删除学校
    public boolean deleteSchool(int schoolId) throws SQLException {
        String query = "DELETE FROM schools WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, schoolId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // 获取所有学校
    public ResultSet getAllSchools() throws SQLException {
        String query = "SELECT * FROM schools";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
}
