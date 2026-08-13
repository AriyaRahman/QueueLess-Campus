package view.admin;

import controller.AdminController;
import util.Constants;
import util.ThemeUtil;
import view.LoginFrame;

import javax.swing.*;
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
        tabs.setBackground(ThemeUtil.BACKGROUND);
        tabs.setForeground(ThemeUtil.TEXT_PRIMARY);
        tabs.setBorder(BorderFactory.createEmptyBorder(4, 6, 0, 6));
        tabs.addTab("Queue Monitor", ThemeUtil.icon("queue", ThemeUtil.PRIMARY), queueMonitorPanel);
        tabs.addTab("Manage Services", ThemeUtil.icon("service", ThemeUtil.PRIMARY), serviceManagementPanel);
        tabs.addTab("Reports", ThemeUtil.icon("report", ThemeUtil.PRIMARY), reportPanel);
        tabs.addTab("Announcements", ThemeUtil.icon("announcement", ThemeUtil.PRIMARY), announcementAdminPanel);
        tabs.setBackgroundAt(0, ThemeUtil.PRIMARY_LIGHT);
        tabs.setBackgroundAt(1, ThemeUtil.SKY_LIGHT);
        tabs.setBackgroundAt(2, ThemeUtil.PURPLE_LIGHT);
        tabs.setBackgroundAt(3, ThemeUtil.WARNING_LIGHT);

        tabs.addChangeListener(e -> {
            queueMonitorPanel.reloadServices();
            reportPanel.refresh();
        });

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel header = ThemeUtil.createHeaderPanel("Staff Portal - " + controller.getStaff().getName());

        JButton logoutButton = ThemeUtil.createDangerButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        header.add(logoutButton, BorderLayout.EAST);

        return header;
    }
}
