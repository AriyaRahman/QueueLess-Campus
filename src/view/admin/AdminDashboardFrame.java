package view.admin;

import controller.AdminController;
import util.Constants;
import util.ThemeUtil;
import view.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Main window for logged-in staff/admin, hosting queue monitoring, service
 * management, reporting and announcement panels in tabs.
 */
public class AdminDashboardFrame extends JFrame {

    private final AdminController controller;

    public AdminDashboardFrame(AdminController controller) {
        this.controller = controller;
        setTitle(Constants.APP_TITLE + " - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ThemeUtil.BACKGROUND);

        add(buildHeader(), BorderLayout.NORTH);

        QueueMonitorPanel queueMonitorPanel = new QueueMonitorPanel(controller);
        ReportPanel reportPanel = new ReportPanel(controller);
        ServiceManagementPanel serviceManagementPanel = new ServiceManagementPanel(controller,
                queueMonitorPanel::reloadServices);
        AnnouncementAdminPanel announcementAdminPanel = new AnnouncementAdminPanel(controller);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(ThemeUtil.FONT_SUBTITLE.deriveFont(13f));
        tabs.addTab("Queue Monitor", queueMonitorPanel);
        tabs.addTab("Manage Services", serviceManagementPanel);
        tabs.addTab("Reports", reportPanel);
        tabs.addTab("Announcements", announcementAdminPanel);

        tabs.addChangeListener(e -> {
            queueMonitorPanel.reloadServices();
            reportPanel.refresh();
        });

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeUtil.PRIMARY_DARK);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel welcome = new JLabel("Staff Portal - " + controller.getStaff().getName());
        welcome.setFont(ThemeUtil.FONT_SUBTITLE);
        welcome.setForeground(Color.WHITE);
        header.add(welcome, BorderLayout.WEST);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        header.add(logoutButton, BorderLayout.EAST);

        return header;
    }
}
