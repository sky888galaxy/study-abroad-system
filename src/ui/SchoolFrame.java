package ui;

import dao.ApplicationDAO;
import dao.MajorDAO;
import model.Application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SchoolFrame extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public SchoolFrame() {
        setTitle("批复专员界面 - 审核申请");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new Object[]{"申请ID", "学生名", "学校", "专业", "状态", "操作"}, 0);
        table = new JTable(model);
        table.setRowHeight(30);

        loadData();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0); // 清空原数据
        List<Application> applications = ApplicationDAO.getApplicationsByStatus("审核通过");

        for (Application app : applications) {
            JButton approveBtn = new JButton("占位");
            JButton rejectBtn = new JButton("建议修改");

            // 添加监听器
            approveBtn.addActionListener(e -> {
                boolean ok = MajorDAO.tryOccupySlot(app.getUniversityCode(), app.getMajorCode());
                if (ok) {
                    ApplicationDAO.updateApplicationStatus(app.getId(), "已占位");
                    JOptionPane.showMessageDialog(this, "占位成功！");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "该专业已无名额！");
                }
            });

            rejectBtn.addActionListener(e -> {
                ApplicationDAO.updateApplicationStatus(app.getId(), "待修改");
                JOptionPane.showMessageDialog(this, "已标记为待修改！");
                loadData();
            });

            // 按钮面板
            JPanel actionPanel = new JPanel();
            actionPanel.add(approveBtn);
            actionPanel.add(rejectBtn);

            model.addRow(new Object[]{
                    app.getId(),
                    app.getStudentName(),
                    app.getUniversityCode(),
                    app.getMajorCode(),
                    app.getStatus(),
                    actionPanel
            });
        }
    }

    public static void main(String[] args) {
        new SchoolFrame();
    }
}
