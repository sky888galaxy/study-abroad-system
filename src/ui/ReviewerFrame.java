package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.ApplicationDAO;
import model.Student;
import dao.StudentDAO;

public class ReviewerFrame extends JFrame {
    private JTextArea studentInfoArea;
    private JButton approveButton, rejectButton;

    public ReviewerFrame(int applicationId) {
        setTitle("审查员审核界面");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 学生信息区域
        studentInfoArea = new JTextArea(10, 40);
        studentInfoArea.setEditable(false);
        panel.add(new JScrollPane(studentInfoArea), BorderLayout.CENTER);

        // 审核按钮
        JPanel buttonPanel = new JPanel();
        approveButton = new JButton("通过");
        rejectButton = new JButton("不通过");

        buttonPanel.add(approveButton);
        buttonPanel.add(rejectButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 按钮点击事件
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationDAO.updateApplicationStatus(applicationId, "审核通过");
                JOptionPane.showMessageDialog(null, "申请通过！");

                // 关闭当前界面
                dispose();
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ApplicationDAO.updateApplicationStatus(applicationId, "审核不通过");
                JOptionPane.showMessageDialog(null, "申请不通过！");

                // 关闭当前界面
                dispose();
            }
        });

        // 加载申请信息
        loadStudentInfo(applicationId);

        // 设置布局并显示
        add(panel);
        setVisible(true);
    }

    // 根据申请 ID 加载学生信息
    private void loadStudentInfo(int applicationId) {
        // 获取申请信息
        Student student = ApplicationDAO.getStudentByApplicationId(applicationId);

        // 显示学生信息
        if (student != null) {
            studentInfoArea.setText("姓名: " + student.getName() + "\n"
                    + "学校: " + student.getSchool() + "\n"
                    + "邮箱: " + student.getEmail());
        }
    }

    public static void main(String[] args) {
        new ReviewerFrame(1); // 这里传入一个申请 ID，实际使用中可以从数据库中获取
    }
}
