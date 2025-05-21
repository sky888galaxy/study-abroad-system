package ui;
// src/ui/LoginFrame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.UserDAO;
import java.awt.Desktop;
import java.net.URI;


public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

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

        JButton loginButton = new JButton("登录");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(120, 40));

        // 修改密码按钮
        JButton changePasswordButton = new JButton("修改密码");
        changePasswordButton.setBackground(new Color(0, 123, 255));
        changePasswordButton.setForeground(Color.WHITE);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setBorderPainted(false);
        changePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changePasswordButton.setPreferredSize(new Dimension(120, 40));

        // 这里添加一个按钮来跳转到外部网站
        JButton visitWebsiteButton = new JButton("了解更多学校和专业");
        visitWebsiteButton.setBackground(new Color(0, 123, 255));
        visitWebsiteButton.setForeground(Color.WHITE);
        visitWebsiteButton.setFocusPainted(false);
        visitWebsiteButton.setBorderPainted(false);
        visitWebsiteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        visitWebsiteButton.setPreferredSize(new Dimension(180, 40));

        visitWebsiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 打开指定的网站 URL
                    Desktop.getDesktop().browse(new URI("https://sites.google.com/view/bhtwyxchylzyljrzws/%E9%A6%96%E9%A1%B5"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 跳转到修改密码界面
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePasswordFrame().setVisible(true);  // 打开修改密码界面
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    UserDAO userDAO = new UserDAO();
                    boolean isUsernameExist = userDAO.isUsernameExist(username);  // 检查用户名是否存在
                    if (!isUsernameExist) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "用户名不存在");
                        return;  // 如果用户名不存在，直接返回
                    }

                    boolean isValidUser = userDAO.validateUser(username, password);  // 验证用户名和密码
                    if (isValidUser) {
                        String role = userDAO.getUserRole(username);  // 获取角色
                        JOptionPane.showMessageDialog(LoginFrame.this, "登录成功！");
                        dispose();  // 关闭当前窗口
                        // 根据角色跳转到对应的界面
                        switch (role) {
                            case "Agent":
                                new AgentFrame().setVisible(true);  // 代理专员界面
                                break;
                            case "Reviewer":
                                new ReviewerFrame().setVisible(true);  // 审查专员界面
                                break;
                            case "SchoolApprover":
                                new SchoolApproverFrame().setVisible(true);  // 批复专员界面
                                break;
                            case "Admin":
                                new AdminFrame().setVisible(true);  // 管理员界面
                                break;
                            default:
                                JOptionPane.showMessageDialog(LoginFrame.this, "未知角色");
                        }
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "密码错误");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "登录失败：" + ex.getMessage());
                }
            }
        });


        // 布局设置
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1; panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1; panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(changePasswordButton, gbc);  // 添加修改密码按钮

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(visitWebsiteButton, gbc);  // 添加了解更多学校和专业按钮

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}