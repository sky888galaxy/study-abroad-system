package ui;

import dao.UserDAO;
import dao.ApplicationDAO;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel registerLink;
    private JCheckBox rememberMeCheckbox;

    public LoginFrame() {
        setTitle("留学申请系统 - 登录");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(35, 38, 47));
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(35, 38, 47));

        JLabel usernameLabel = new JLabel("用户名:");
        JLabel passwordLabel = new JLabel("密码:");
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("登录");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(120, 40));

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 102, 204));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0, 123, 255));
            }
        });

        // 七天免登录
        rememberMeCheckbox = new JCheckBox("七天免登录");
        rememberMeCheckbox.setBackground(new Color(35, 38, 47));
        rememberMeCheckbox.setForeground(Color.WHITE);

        // 注册链接
        registerLink = new JLabel("<html><u>立即注册</u></html>");
        registerLink.setForeground(Color.WHITE);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegisterFrame();
                dispose();
            }
        });

        // 登录按钮事件
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            String role = UserDAO.getRole(username, password);

            if (role != null) {
                JOptionPane.showMessageDialog(null, "登录成功！");
                dispose();

                switch (role) {
                    case "学生":
                        JOptionPane.showMessageDialog(null, "学生登录成功（暂不开放界面）");
                        break;
                    case "代理":
                        new AgentFrame();
                        break;
                    case "审查员":
                        Integer appId = ApplicationDAO.getFirstApplicationIdByStatus("待审核");
                        if (appId != null) {
                            new ReviewerFrame(appId);
                        } else {
                            JOptionPane.showMessageDialog(null, "当前没有待审核的申请！");
                        }
                        break;

                    case "批复专员":
                        new SchoolFrame();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "未知角色：" + role);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1; panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(rememberMeCheckbox, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(loginButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(registerLink, gbc);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
