// src/ui/AdminFrame.java
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import util.DatabaseConnection;

public class AdminFrame extends JFrame {

    private JPanel schoolPanel, majorPanel, agentPanel;

    public AdminFrame() {
        setTitle("管理员 - 学校、专业和专员管理");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 创建面板，显示学校和专业
        schoolPanel = new JPanel();
        schoolPanel.setLayout(new BoxLayout(schoolPanel, BoxLayout.Y_AXIS));
        JScrollPane schoolScrollPane = new JScrollPane(schoolPanel);
        add(schoolScrollPane, BorderLayout.WEST);

        majorPanel = new JPanel();
        majorPanel.setLayout(new BoxLayout(majorPanel, BoxLayout.Y_AXIS));
        JScrollPane majorScrollPane = new JScrollPane(majorPanel);
        add(majorScrollPane, BorderLayout.CENTER);

        agentPanel = new JPanel();
        agentPanel.setLayout(new BoxLayout(agentPanel, BoxLayout.Y_AXIS));
        JScrollPane agentScrollPane = new JScrollPane(agentPanel);
        add(agentScrollPane, BorderLayout.EAST);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // 使用 GridBagLayout 布局

        // 按钮
        JButton addSchoolButton = new JButton("增加学校");
        JButton deleteSchoolButton = new JButton("删除学校");
        JButton addMajorButton = new JButton("增加专业");
        JButton deleteMajorButton = new JButton("删除专业");
        JButton manageAgentButton = new JButton("专员管理");

        // 设置按钮的 GridBagConstraints（布局控制）
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // 添加间距

        // 按钮按顺序放置，设置位置为居中
        gbc.gridx = 0; gbc.gridy = 0;
        buttonPanel.add(addSchoolButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        buttonPanel.add(deleteSchoolButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        buttonPanel.add(addMajorButton, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        buttonPanel.add(deleteMajorButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        buttonPanel.add(manageAgentButton, gbc);

        // 按钮事件处理
        addSchoolButton.addActionListener(e -> openAddSchoolDialog());
        deleteSchoolButton.addActionListener(e -> openDeleteSchoolDialog());
        addMajorButton.addActionListener(e -> openAddMajorDialog());
        deleteMajorButton.addActionListener(e -> openDeleteMajorDialog());
        manageAgentButton.addActionListener(e -> openManageAgentDialog());

        // 将按钮面板添加到正中央
        add(buttonPanel, BorderLayout.CENTER);

        // 加载数据
        loadSchools();
        loadMajors();
        loadAgents();

        setVisible(true);
    }

    // 加载学校数据
    private void loadSchools() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM schools";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String schoolName = rs.getString("school_name");
                    schoolPanel.add(new JLabel(schoolName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 加载专业数据
    private void loadMajors() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT m.major_name, s.school_name FROM majors m JOIN schools s ON m.school_id = s.id";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String majorName = rs.getString("major_name");
                    String schoolName = rs.getString("school_name");
                    majorPanel.add(new JLabel(schoolName + " - " + majorName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 加载专员数据
    private void loadAgents() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT username, role FROM users WHERE role IN ('Agent', 'Reviewer', 'SchoolApprover')";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String role = rs.getString("role");
                    agentPanel.add(new JLabel("专员： " + username + " - 角色：" + role));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加学校对话框
    private void openAddSchoolDialog() {
        JDialog dialog = new JDialog(this, "增加学校", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new FlowLayout());

        JTextField schoolNameField = new JTextField(20);
        JTextField countryField = new JTextField(20);
        JButton addButton = new JButton("添加");

        addButton.addActionListener(e -> {
            String schoolName = schoolNameField.getText();
            String country = countryField.getText();
            addSchool(schoolName, country);
            dialog.dispose();
        });

        dialog.add(new JLabel("学校名称："));
        dialog.add(schoolNameField);
        dialog.add(new JLabel("国家："));
        dialog.add(countryField);
        dialog.add(addButton);

        dialog.setVisible(true);
    }

    // 添加学校到数据库
    private void addSchool(String schoolName, String country) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO schools (school_name, country) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, schoolName);
                stmt.setString(2, country);
                stmt.executeUpdate();
                // 刷新界面
                schoolPanel.removeAll();
                loadSchools();
                schoolPanel.revalidate();
                schoolPanel.repaint();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除学校对话框
    private void openDeleteSchoolDialog() {
        JDialog dialog = new JDialog(this, "删除学校", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new FlowLayout());

        JTextField schoolIdField = new JTextField(20);
        JButton deleteButton = new JButton("删除");

        deleteButton.addActionListener(e -> {
            int schoolId = Integer.parseInt(schoolIdField.getText());
            deleteSchool(schoolId);
            dialog.dispose();
        });

        dialog.add(new JLabel("学校ID："));
        dialog.add(schoolIdField);
        dialog.add(deleteButton);

        dialog.setVisible(true);
    }

    // 删除学校从数据库
    private void deleteSchool(int schoolId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM schools WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, schoolId);
                stmt.executeUpdate();
                // 刷新界面
                schoolPanel.removeAll();
                loadSchools();
                schoolPanel.revalidate();
                schoolPanel.repaint();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 添加专业对话框
    private void openAddMajorDialog() {
        JDialog dialog = new JDialog(this, "增加专业", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new FlowLayout());

        JTextField majorNameField = new JTextField(20);
        JTextField schoolIdField = new JTextField(20);
        JButton addButton = new JButton("添加");

        addButton.addActionListener(e -> {
            String majorName = majorNameField.getText();
            int schoolId = Integer.parseInt(schoolIdField.getText());
            addMajor(majorName, schoolId);
            dialog.dispose();
        });

        dialog.add(new JLabel("专业名称："));
        dialog.add(majorNameField);
        dialog.add(new JLabel("学校ID："));
        dialog.add(schoolIdField);
        dialog.add(addButton);

        dialog.setVisible(true);
    }

    // 添加专业到数据库
    private void addMajor(String majorName, int schoolId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO majors (major_name, school_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, majorName);
                stmt.setInt(2, schoolId);
                stmt.executeUpdate();
                // 刷新界面
                majorPanel.removeAll();
                loadMajors();
                majorPanel.revalidate();
                majorPanel.repaint();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除专业对话框
    private void openDeleteMajorDialog() {
        JDialog dialog = new JDialog(this, "删除专业", true);
        dialog.setSize(300, 200);
        dialog.setLayout(new FlowLayout());

        JTextField majorIdField = new JTextField(20);
        JButton deleteButton = new JButton("删除");

        deleteButton.addActionListener(e -> {
            int majorId = Integer.parseInt(majorIdField.getText());
            deleteMajor(majorId);
            dialog.dispose();
        });

        dialog.add(new JLabel("专业ID："));
        dialog.add(majorIdField);
        dialog.add(deleteButton);

        dialog.setVisible(true);
    }

    // 删除专业从数据库
    private void deleteMajor(int majorId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM majors WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, majorId);
                stmt.executeUpdate();
                // 刷新界面
                majorPanel.removeAll();
                loadMajors();
                majorPanel.revalidate();
                majorPanel.repaint();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 专员管理
    private void openManageAgentDialog() {
        // 专员管理对话框内容
        JOptionPane.showMessageDialog(this, "专员管理功能");
    }

    public static void main(String[] args) {
        new AdminFrame();
    }
}
