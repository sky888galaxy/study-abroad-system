package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.StudentDAO;

public class AgentFrame extends JFrame {
    private JTextField nameField, schoolField, emailField;
    private JComboBox<String> universityComboBox, majorComboBox;
    private JButton submitButton;

    public AgentFrame() {
        setTitle("学生申请界面");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 设置背景色和布局
        getContentPane().setBackground(new Color(240, 240, 240)); // 背景颜色
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));

        JLabel nameLabel = new JLabel("姓名:");
        JLabel schoolLabel = new JLabel("在读学校:");
        JLabel emailLabel = new JLabel("邮箱:");
        JLabel universityLabel = new JLabel("选择学校:");
        JLabel majorLabel = new JLabel("选择专业:");

        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        schoolLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        nameField = new JTextField(20);
        schoolField = new JTextField(20);
        emailField = new JTextField(20);

        universityComboBox = new JComboBox<>(new String[] {"澳洲国立大学", "悉尼大学", "新南威尔士大学"});
        majorComboBox = new JComboBox<>(new String[] {"计算机科学", "电子工程", "生物医学"});

        submitButton = new JButton("提交申请");
        submitButton.setBackground(new Color(0, 123, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setPreferredSize(new Dimension(120, 40));

        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(0, 102, 204));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBackground(new Color(0, 123, 255));
            }
        });

        // GridBagLayout 布局
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(schoolLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(schoolField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(universityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(universityComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(majorLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(majorComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new AgentFrame(); // 启动学生申请界面
    }
}
