package ui;
// RoleSelectionFrame.java (角色选择界面)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoleSelectionFrame extends JFrame {
    public RoleSelectionFrame() {
        setTitle("Select Role");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton studentButton = new JButton("Student");
        JButton agentButton = new JButton("Agent");
        JButton reviewerButton = new JButton("Reviewer");
        JButton adminButton = new JButton("Admin");

        panel.add(studentButton);
        panel.add(agentButton);
        panel.add(reviewerButton);
        panel.add(adminButton);

        add(panel);

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentFrame().setVisible(true);
                dispose();
            }
        });
        agentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AgentFrame().setVisible(true);
                dispose();
            }
        });
        reviewerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReviewerFrame().setVisible(true);
                dispose();
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminFrame().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RoleSelectionFrame().setVisible(true);
            }
        });
    }
}
