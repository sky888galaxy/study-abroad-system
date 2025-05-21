// src/ui/AgentFrame.java
package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.sql.*;
import util.DatabaseConnection;  // 导入数据库连接工具类
import java.util.HashMap;
import java.util.Map;

public class AgentFrame extends JFrame {
    private JPanel applicationListPanel;
    private int applicationCount = 0;

    public AgentFrame() {
        setTitle("代理专员 - 留学申请管理");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        applicationListPanel = new JPanel();
        applicationListPanel.setLayout(new BoxLayout(applicationListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(applicationListPanel);
        add(scrollPane, BorderLayout.CENTER);

        String[] headers = {
                "序号", "申请日期", "学生姓名", "在读学校", "Email",
                "申请学校及代号", "专业及代号",
                "自荐材料", "成绩单",
                "状态", "资质审核", "是否占位", "是否录取"
        };
        JPanel headerPanel = new JPanel(new GridLayout(1, headers.length));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        headerPanel.setBackground(new Color(220, 220, 220));
        for (String h : headers) {
            JLabel label = new JLabel(h, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            headerPanel.add(label);
        }
        applicationListPanel.add(headerPanel);

        JButton addButton = new JButton("新增申请");
        addButton.addActionListener(e -> openApplicationDialog());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(addButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void openApplicationDialog() {
        JDialog dialog = new JDialog(this, "新增申请", true);
        dialog.setSize(550, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(20);
        JTextField schoolField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        String[] universities = {"澳洲国立大学#01", "悉尼大学#02", "新南威尔士大学#03"};
        Map<String, String[]> majorMap = new HashMap<>();
        majorMap.put("01", new String[]{"专业1 #01A", "专业2 #01B", "专业3 #01C"});
        majorMap.put("02", new String[]{"专业4 #02A", "专业5 #02B", "专业6 #02C"});
        majorMap.put("03", new String[]{"专业7 #03A", "专业8 #03B", "专业9 #03C"});

        JComboBox<String> schoolBox = new JComboBox<>();
        JComboBox<String> majorBox = new JComboBox<>();
        majorBox.setEnabled(false);

        for (String uni : universities) schoolBox.addItem(uni);
        schoolBox.insertItemAt("", 0);
        schoolBox.setSelectedIndex(0);

        schoolBox.addActionListener(e -> {
            majorBox.removeAllItems();
            String selected = (String) schoolBox.getSelectedItem();
            if (selected != null && selected.contains("#")) {
                String code = selected.split("#")[1];
                String[] majors = majorMap.getOrDefault(code, new String[0]);
                for (String m : majors) majorBox.addItem(m);
                majorBox.setEnabled(true);
                majorBox.setSelectedIndex(-1);
            } else {
                majorBox.setEnabled(false);
            }
        });

        JLabel introPathLabel = new JLabel("未选择");
        JButton selectIntroBtn = new JButton("选择自荐材料");
        selectIntroBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION)
                introPathLabel.setText(fc.getSelectedFile().getName());
        });

        JLabel transcriptPathLabel = new JLabel("未选择");
        JButton selectTranscriptBtn = new JButton("选择成绩单");
        selectTranscriptBtn.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION)
                transcriptPathLabel.setText(fc.getSelectedFile().getName());
        });

        int y = 0;
        dialog.add(new JLabel("学生姓名："), gbcAt(0, y)); dialog.add(nameField, gbcAt(1, y++));
        dialog.add(new JLabel("在读学校："), gbcAt(0, y)); dialog.add(schoolField, gbcAt(1, y++));
        dialog.add(new JLabel("Email："), gbcAt(0, y)); dialog.add(emailField, gbcAt(1, y++));
        dialog.add(new JLabel("申请学校："), gbcAt(0, y)); dialog.add(schoolBox, gbcAt(1, y++));
        dialog.add(new JLabel("申请专业："), gbcAt(0, y)); dialog.add(majorBox, gbcAt(1, y++));

        dialog.add(new JLabel("自荐材料："), gbcAt(0, y)); dialog.add(selectIntroBtn, gbcAt(1, y++));
        dialog.add(new JLabel("已选文件："), gbcAt(0, y)); dialog.add(introPathLabel, gbcAt(1, y++));
        dialog.add(new JLabel("成绩单："), gbcAt(0, y)); dialog.add(selectTranscriptBtn, gbcAt(1, y++));
        dialog.add(new JLabel("已选文件："), gbcAt(0, y)); dialog.add(transcriptPathLabel, gbcAt(1, y++));

        JButton submitButton = new JButton("提交");
        gbc = gbcAt(1, ++y);
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String currentSchool = schoolField.getText().trim();
            String email = emailField.getText().trim();
            String selectedSchool = (String) schoolBox.getSelectedItem();
            String selectedMajor = (String) majorBox.getSelectedItem();
            String introPath = introPathLabel.getText().trim();
            String transcriptPath = transcriptPathLabel.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "请填写学生姓名");
                return;
            }
            if (currentSchool.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "请填写在读学校");
                return;
            }
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "请填写 Email");
                return;
            }
            if (selectedSchool == null || selectedSchool.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "请选择申请学校");
                return;
            }
            if (selectedMajor == null || selectedMajor.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "请选择申请专业");
                return;
            }
            if (introPath.equals("未选择")) {
                JOptionPane.showMessageDialog(dialog, "请上传自荐材料");
                return;
            }
            if (transcriptPath.equals("未选择")) {
                JOptionPane.showMessageDialog(dialog, "请上传成绩单");
                return;
            }

            applicationCount++;
            String date = LocalDate.now().toString();
            // 存储数据到数据库
            storeApplicationToDatabase(name, currentSchool, email, selectedSchool, selectedMajor, introPath, transcriptPath, date);

            applicationListPanel.add(new ApplicationRow(applicationCount + "", date, name,
                    currentSchool, email, selectedSchool, selectedMajor,
                    introPath, transcriptPath,
                    0, 0, 0));
            applicationListPanel.revalidate();
            dialog.dispose();
        });

        dialog.setVisible(true);
    }

    // 存储申请信息到数据库
    private void storeApplicationToDatabase(String name, String currentSchool, String email,
                                            String selectedSchool, String selectedMajor,
                                            String introPath, String transcriptPath, String date) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("数据库连接成功，准备执行 SQL 插入操作...");

            // 插入数据的 SQL 语句
            String query = "INSERT INTO applications (application_date, student_name, current_school, email, university, major, intro_material_path, transcript_path) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, date);
                stmt.setString(2, name);
                stmt.setString(3, currentSchool);
                stmt.setString(4, email);
                stmt.setString(5, selectedSchool);
                stmt.setString(6, selectedMajor);
                stmt.setString(7, introPath);
                stmt.setString(8, transcriptPath);

                // 调试输出，检查参数是否正确设置
                System.out.println("执行 SQL 插入操作...");
                int rowsAffected = stmt.executeUpdate();  // 执行插入操作

                // 检查插入是否成功
                if (rowsAffected > 0) {
                    System.out.println("插入成功！数据已插入到数据库。");
                } else {
                    System.out.println("插入失败！没有记录被插入。");
                }
            } catch (SQLException e) {
                System.out.println("SQL 执行时出错：" + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("数据库连接错误：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static GridBagConstraints gbcAt(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgentFrame::new);
    }

    // src/ui/AgentFrame.java

    private static class ApplicationRow extends JPanel {
        public ApplicationRow(String index, String date, String name, String school,
                              String email, String uniCode, String majorCode,
                              String introPath, String transcriptPath,
                              int qualification, int occupation, int admission) {
            setLayout(new GridLayout(1, 13));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

            addCell(index);
            addCell(date);
            addCell(name);
            addCell(school);
            addCell(email);
            addCell(uniCode);
            addCell(majorCode);
            addCell(introPath);
            addCell(transcriptPath);

            addCell(resolveStatus(qualification, occupation, admission));
            addCell(getQualText(qualification));
            addCell(getOccText(occupation));
            addCell(getAdmText(admission));
        }

        private void addCell(String text) {
            JLabel label = new JLabel(text, SwingConstants.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            add(label);
        }

        private String resolveStatus(int qualification, int occupation, int admission) {
            if (qualification == 0) return "待审核";
            if (qualification == -1) return "资质审核未通过";
            if (qualification == 1) {
                return switch (occupation) {
                    case 0 -> "待占位";
                    case 1 -> "待录取";
                    case 2 -> "待修改申请专业";
                    case 3, 4 -> "待修改申请学校";
                    default -> "未知状态";
                };
            }
            return "未知状态";
        }

        private String getQualText(int q) {
            return switch (q) {
                case 1 -> "合格";
                case -1 -> "不合格";
                default -> "";
            };
        }

        private String getOccText(int o) {
            return switch (o) {
                case 1 -> "已占位";
                case 2 -> "专业已满";
                case 3 -> "学校已满";
                case 4 -> "条件不符";
                default -> "";
            };
        }

        private String getAdmText(int a) {
            return a == 1 ? "已录取" : "";
        }
    }






}
