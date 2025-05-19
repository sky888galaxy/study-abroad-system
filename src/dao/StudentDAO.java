package dao;

import java.sql.*;
import model.Student;

public class StudentDAO {
    // 保存学生信息
    public static void saveStudent(Student student, String university, String major) {
        String sql = "INSERT INTO students (name, school, email) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database/study_abroad.db");
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 插入学生信息
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getSchool());
            stmt.setString(3, student.getEmail());
            stmt.executeUpdate();

            // 获取生成的学生 ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int studentId = rs.getInt(1);

                // 插入申请信息
                String applicationSql = "INSERT INTO applications (student_id, university_code, major_code, status) VALUES (?, ?, ?, ?)";
                try (PreparedStatement appStmt = conn.prepareStatement(applicationSql)) {
                    appStmt.setInt(1, studentId);
                    appStmt.setString(2, university);
                    appStmt.setString(3, major);
                    appStmt.setString(4, "待审核");
                    appStmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
