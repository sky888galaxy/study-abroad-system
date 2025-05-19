package dao;

import model.Application;
import model.Student;

import java.sql.*;
import java.util.*;

public class ApplicationDAO {

    private static final String URL = "jdbc:sqlite:database/study_abroad.db"; // 确保数据库路径正确

    // 获取状态为指定状态的申请列表
    public static List<Application> getApplicationsByStatus(String status) {
        List<Application> list = new ArrayList<>();
        String sql = "SELECT a.id, s.name, a.university_code, a.major_code, a.status " +
                "FROM applications a JOIN students s ON a.student_id = s.id WHERE a.status = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            // 遍历结果集，创建 Application 对象并添加到列表
            while (rs.next()) {
                list.add(new Application(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("university_code"),
                        rs.getString("major_code"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 更新申请状态
    public static void updateApplicationStatus(int applicationId, String status) {
        String sql = "UPDATE applications SET status = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, applicationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 获取申请的学生信息
    public static Student getStudentByApplicationId(int applicationId) {
        String sql = "SELECT students.name, students.school, students.email FROM students " +
                "JOIN applications ON students.id = applications.student_id " +
                "WHERE applications.id = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, applicationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getString("name"),
                        rs.getString("school"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getFirstApplicationIdByStatus(String status) {
        String sql = "SELECT id FROM applications WHERE status = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/study_abroad.db");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}
