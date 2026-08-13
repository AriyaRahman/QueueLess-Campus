package view;

import controller.LoginController;
import model.Staff;
import model.Student;
import util.Constants;
import util.ThemeUtil;
import util.ValidationUtil;
import view.admin.AdminDashboardFrame;
import view.student.StudentDashboardFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Optional;

public class LoginFrame extends JFrame {

    private final LoginController loginController;

    private final JRadioButton studentRadio = new JRadioButton("Student", true);
    private final JRadioButton staffRadio = new JRadioButton("Staff / Admin");
    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JLabel statusLabel = new JLabel(" ");

    public LoginFrame() {
        this.loginController = new LoginController();
        setTitle(Constants.APP_TITLE + " - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(ThemeUtil.BACKGROUND);
        ThemeUtil.styleInput(emailField);
        ThemeUtil.styleInput(passwordField);
        setLayout(new GridBagLayout());
        add(buildCard());
    }

    private JPanel buildCard() {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ThemeUtil.CARD_BACKGROUND);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(ThemeUtil.BORDER, 1, true),
                new EmptyBorder(28, 42, 28, 42)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridwidth = 2;

        int row = 0;

        // Small brand badge
        JLabel badge = new JLabel("  CAMPUS QUEUE MANAGEMENT  ",
                ThemeUtil.icon("queue", ThemeUtil.ACCENT), SwingConstants.CENTER);
        badge.setOpaque(true);
        badge.setBackground(ThemeUtil.PRIMARY_LIGHT);
        badge.setForeground(ThemeUtil.PRIMARY_DARK);
        badge.setFont(ThemeUtil.FONT_SMALL.deriveFont(Font.BOLD));
        badge.setBorder(new EmptyBorder(7, 12, 7, 12));
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 6, 14, 6);
        card.add(badge, gbc);

        JLabel title = ThemeUtil.createTitleLabel(Constants.APP_TITLE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setIconTextGap(10);
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 6, 5, 6);
        card.add(title, gbc);

        JLabel subtitle = new JLabel("Skip the line. Join the queue virtually.", SwingConstants.CENTER);
        subtitle.setFont(ThemeUtil.FONT_BODY);
        subtitle.setForeground(ThemeUtil.TEXT_SECONDARY);
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 6, 18, 6);
        card.add(subtitle, gbc);

        JLabel roleTitle = new JLabel("Choose your account type", SwingConstants.CENTER);
        roleTitle.setFont(ThemeUtil.FONT_BODY.deriveFont(Font.BOLD));
        roleTitle.setForeground(ThemeUtil.TEXT_PRIMARY);
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 6, 8, 6);
        card.add(roleTitle, gbc);

        // centered role selector (Student / Staff/Admin)
        ButtonGroup group = new ButtonGroup();
        group.add(studentRadio);
        group.add(staffRadio);
        studentRadio.setFont(ThemeUtil.FONT_BODY.deriveFont(Font.BOLD));
        staffRadio.setFont(ThemeUtil.FONT_BODY.deriveFont(Font.BOLD));
        studentRadio.setForeground(ThemeUtil.PRIMARY_DARK);
        staffRadio.setForeground(ThemeUtil.TEXT_PRIMARY);
        studentRadio.setOpaque(false);
        staffRadio.setOpaque(false);
        studentRadio.setIcon(ThemeUtil.icon("student", ThemeUtil.PRIMARY));
        studentRadio.setSelectedIcon(ThemeUtil.icon("student", ThemeUtil.ACCENT));
        staffRadio.setIcon(ThemeUtil.icon("admin", ThemeUtil.PRIMARY));
        staffRadio.setSelectedIcon(ThemeUtil.icon("admin", ThemeUtil.ACCENT));
        studentRadio.setIconTextGap(8);
        staffRadio.setIconTextGap(8);

        JPanel rolePanel = new JPanel(new GridLayout(1, 2, 12, 0));
        rolePanel.setOpaque(false);
        JPanel studentChoice = createRoleChoice(studentRadio, true);
        JPanel staffChoice = createRoleChoice(staffRadio, false);
        rolePanel.add(studentChoice);
        rolePanel.add(staffChoice);
        gbc.gridy = row++;
        gbc.insets = new Insets(0, 0, 18, 0);
        card.add(rolePanel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(6, 6, 6, 8);
        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel emailLabel = new JLabel("Email:");
        ThemeUtil.styleLabel(emailLabel, "email");
        card.add(emailLabel, gbc);
        gbc.gridx = 1;
        card.add(emailField, gbc);
        row++;

        gbc.gridy = row;
        gbc.gridx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        ThemeUtil.styleLabel(passwordLabel, "password");
        card.add(passwordLabel, gbc);
        gbc.gridx = 1;
        card.add(passwordField, gbc);
        row++;

        JButton loginButton = ThemeUtil.createPrimaryButton("Login");
        loginButton.setPreferredSize(new Dimension(300, 44));
        loginButton.addActionListener(e -> handleLogin());
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = row++;
        gbc.insets = new Insets(16, 6, 8, 6);
        card.add(loginButton, gbc);

        JButton registerButton = ThemeUtil.createSecondaryButton("New student?  Register here");
        registerButton.setPreferredSize(new Dimension(300, 42));
        registerButton.addActionListener(e -> handleRegister());
        gbc.gridy = row++;
        gbc.insets = new Insets(4, 6, 6, 6);
        card.add(registerButton, gbc);

        statusLabel.setForeground(ThemeUtil.DANGER);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(ThemeUtil.FONT_SMALL.deriveFont(Font.BOLD));
        gbc.gridy = row;
        gbc.insets = new Insets(4, 6, 0, 6);
        card.add(statusLabel, gbc);

        return card;
    }

    private JPanel createRoleChoice(JRadioButton radio, boolean selected) {
        JPanel panel = new JPanel(new GridBagLayout());
        // show which one is selected with the radio + border
        panel.setBackground(ThemeUtil.PRIMARY_LIGHT);
        panel.setBorder(new javax.swing.border.LineBorder(
                ThemeUtil.PRIMARY, selected ? 2 : 1, true));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(radio);

        Runnable refresh = () -> {
            boolean active = radio.isSelected();
            panel.setBackground(ThemeUtil.PRIMARY_LIGHT);
            panel.setBorder(new javax.swing.border.LineBorder(
                    ThemeUtil.PRIMARY, active ? 2 : 1, true));
            radio.setForeground(active ? ThemeUtil.PRIMARY_DARK : ThemeUtil.TEXT_PRIMARY);
            panel.repaint();
        };
        radio.addActionListener(e -> refresh.run());
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                radio.setSelected(true);
                refresh.run();
            }
        });
        return panel;
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (!ValidationUtil.isValidEmail(email) || !ValidationUtil.isNotEmpty(password)) {
            statusLabel.setText("Please enter a valid email and password.");
            return;
        }

        if (studentRadio.isSelected()) {
            Optional<Student> student = loginController.loginStudent(email, password);
            if (student.isPresent()) {
                openStudentDashboard(student.get());
            } else {
                statusLabel.setText("Invalid student credentials.");
            }
        } else {
            Optional<Staff> staff = loginController.loginStaff(email, password);
            if (staff.isPresent()) {
                openAdminDashboard(staff.get());
            } else {
                statusLabel.setText("Invalid staff credentials.");
            }
        }
    }

    private void handleRegister() {
        JTextField nameField = new JTextField();
        JTextField emailRegField = new JTextField();
        JPasswordField passRegField = new JPasswordField();
        JTextField deptField = new JTextField();

        Object[] fields = {
                "Full name:", nameField,
                "Email:", emailRegField,
                "Password:", passRegField,
                "Department:", deptField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Register as Student",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) return;

        String name = nameField.getText().trim();
        String email = emailRegField.getText().trim();
        String password = new String(passRegField.getPassword());
        String department = deptField.getText().trim();

        if (!ValidationUtil.isNotEmpty(name) || !ValidationUtil.isValidEmail(email)
                || !ValidationUtil.isValidPassword(password) || !ValidationUtil.isNotEmpty(department)) {
            JOptionPane.showMessageDialog(this, "Please fill all fields correctly (password min 4 chars).",
                    "Invalid input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Optional<Student> registered = loginController.registerStudent(name, email, password, department);
        if (registered.isPresent()) {
            JOptionPane.showMessageDialog(this, "Registration successful. You can now log in.");
        } else {
            JOptionPane.showMessageDialog(this, "That email is already registered.",
                    "Registration failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openStudentDashboard(Student student) {
        dispose();
        StudentDashboardFrame frame = new StudentDashboardFrame(loginController.buildStudentController(student));
        frame.setVisible(true);
    }

    private void openAdminDashboard(Staff staff) {
        dispose();
        AdminDashboardFrame frame = new AdminDashboardFrame(loginController.buildAdminController(staff));
        frame.setVisible(true);
    }
}
