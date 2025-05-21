// src/ui/ChangePasswordFrame.java
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UserDAO;

public class ChangePasswordFrame extends JFrame {
    private JTextField oldPasswordField;
    private JTextField newPasswordField;
    private JTextField confirmNewPasswordField;

    public ChangePasswordFrame() {
        setTitle("修改密码");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        // 背景颜色
        getContentPane().setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 设置组件间距

        // 设置中文字体（宋体或黑体）
        Font chineseFont = new Font("宋体", Font.PLAIN, 14);  // 你可以换成 "黑体" 或 "微软雅黑" 试试

        // 标题
        JLabel titleLabel = new JLabel("修改密码");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 123, 255));  // 设置标题颜色
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        // 旧密码标签和输入框
        JLabel oldPasswordLabel = new JLabel("旧密码:");
        oldPasswordLabel.setFont(chineseFont);
        oldPasswordField = new JPasswordField(20);
        styleTextField(oldPasswordField, chineseFont); // 美化输入框
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(oldPasswordLabel, gbc);
        gbc.gridx = 1;
        add(oldPasswordField, gbc);

        // 新密码标签和输入框
        JLabel newPasswordLabel = new JLabel("新密码:");
        newPasswordLabel.setFont(chineseFont);
        newPasswordField = new JPasswordField(20);
        styleTextField(newPasswordField, chineseFont); // 美化输入框
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(newPasswordLabel, gbc);
        gbc.gridx = 1;
        add(newPasswordField, gbc);

        // 确认新密码标签和输入框
        JLabel confirmNewPasswordLabel = new JLabel("确认新密码:");
        confirmNewPasswordLabel.setFont(chineseFont);
        confirmNewPasswordField = new JPasswordField(20);
        styleTextField(confirmNewPasswordField, chineseFont); // 美化输入框
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(confirmNewPasswordLabel, gbc);
        gbc.gridx = 1;
        add(confirmNewPasswordField, gbc);

        // 提交按钮
        JButton submitButton = new JButton("提交");
        submitButton.setBackground(new Color(0, 123, 255));  // 按钮颜色
        submitButton.setForeground(Color.WHITE);  // 按钮文字颜色
        submitButton.setFont(new Font("宋体", Font.PLAIN, 14));
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitButton.setPreferredSize(new Dimension(120, 40));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPassword = oldPasswordField.getText();
                String newPassword = newPasswordField.getText();
                String confirmNewPassword = confirmNewPasswordField.getText();

                // 校验密码
                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(ChangePasswordFrame.this, "请填写所有字段");
                    return;
                }

                if (!newPassword.equals(confirmNewPassword)) {
                    JOptionPane.showMessageDialog(ChangePasswordFrame.this, "新密码与确认密码不一致");
                    return;
                }

                // 验证旧密码是否正确
                String username = "example";  // 这里你可以根据当前登录用户传入用户名
                try {
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.validateUser(username, oldPassword)) {
                        // 修改密码
                        if (userDAO.updatePassword(username, newPassword)) {
                            JOptionPane.showMessageDialog(ChangePasswordFrame.this, "密码修改成功");
                            dispose();  // 关闭窗口
                        } else {
                            JOptionPane.showMessageDialog(ChangePasswordFrame.this, "密码修改失败");
                        }
                    } else {
                        JOptionPane.showMessageDialog(ChangePasswordFrame.this, "旧密码错误");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ChangePasswordFrame.this, "操作失败：" + ex.getMessage());
                }
            }
        });

        // 按钮居中
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitButton, gbc);

        setVisible(true);
    }

    // 美化文本框并设置字体
    private void styleTextField(JTextField textField, Font font) {
        textField.setBackground(new Color(240, 240, 240)); // 设置输入框背景色
        textField.setFont(font);
        textField.setPreferredSize(new Dimension(200, 30));  // 设置输入框的大小（更宽一些）
    }
}
