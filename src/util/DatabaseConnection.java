package util;
// src/DatabaseConnection.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:database/study_abroad.db";  // SQLite路径
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL);
                System.out.println("连接到 SQLite 数据库成功");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException("数据库连接失败");
            }
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("关闭 SQLite 数据库连接");
        }
    }
}
