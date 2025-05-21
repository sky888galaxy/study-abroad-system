package dao;
// src/dao/UserDAO.java
import model.User;
import util.DatabaseConnection;  // 导入 DatabaseConnection 类
import java.sql.*;


public class UserDAO {
    private Connection conn;

    public UserDAO() throws SQLException {
        this.conn = DatabaseConnection.getConnection();  // 使用 DatabaseConnection 获取连接
    }

    // 根据用户名验证用户是否存在
    public boolean isUsernameExist(String username) throws SQLException {
        String query = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // 如果找到该用户名，返回 true
        }
    }

    // 根据用户名和密码验证用户身份
    public boolean validateUser(String username, String password) throws SQLException {
        String query = "SELECT 1 FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // 如果找到了匹配的用户名和密码，返回 true
        }
    }

    // 获取用户角色
    public String getUserRole(String username) throws SQLException {
        String query = "SELECT role FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");  // 返回角色
            }
            return null;  // 如果找不到该用户名
        }
    }


    // 修改密码
    public boolean updatePassword(String username, String newPassword) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);  // 新密码
            stmt.setString(2, username);  // 用户名
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // 如果更新成功，返回 true
        }
    }




}
