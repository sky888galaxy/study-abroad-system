package ui;
// StudentFrame.java (学生界面)
import javax.swing.*;
import java.awt.*;

public class StudentFrame extends JFrame {
    public StudentFrame() {
        setTitle("Student Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel schoolLabel = new JLabel("School:");
        JTextField schoolField = new JTextField();
        JLabel majorLabel = new JLabel("Major:");
        JTextField majorField = new JTextField();
        JButton submitButton = new JButton("Submit Application");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(schoolLabel);
        panel.add(schoolField);
        panel.add(majorLabel);
        panel.add(majorField);
        panel.add(new JLabel());  // Empty placeholder
        panel.add(submitButton);

        add(panel);

        submitButton.addActionListener(e -> {
            // 提交申请逻辑
            JOptionPane.showMessageDialog(StudentFrame.this, "Application Submitted");
        });
    }
}
