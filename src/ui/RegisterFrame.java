package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import dao.UserDAO;

public class RegisterFrame extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;

    public RegisterFrame() {
        setTitle("留学申请系统 - 注册");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(35, 38, 47));
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(35, 38, 47));

        JLabel usernameLabel = new JLabel("用户名:");
        JLabel passwordLabel = new JLabel("密码:");
        JLabel emailLabel = new JLabel("邮箱:");
        JLabel roleLabel = new JLabel("角色:");

        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        emailLabel.setForeground(Color.WHITE);
        roleLabel.setForeground(Color.WHITE);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        roleComboBox = new JComboBox<>(new String[]{"学生", "代理", "审查员", "批复专员"});

        registerButton = new JButton("注册");
        registerButton.setBackground(new Color(0, 123, 255));
        registerButton.setForeground(Color.WHITE);
        registerButton.setPreferredSize(new Dimension(120, 40));

        // hover 效果略

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(usernameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(roleLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(roleComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        add(panel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String role = (String) roleComboBox.getSelectedItem();

                // 调用 UserDAO 的注册方法
                if (UserDAO.registerUser(username, password, email, role)) {
                    JOptionPane.showMessageDialog(null, "注册成功！");
                    dispose(); // 关闭注册界面
                    new LoginFrame(); // 打开登录界面
                } else {
                    JOptionPane.showMessageDialog(null, "用户名已存在，请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterFrame();
    }
}
