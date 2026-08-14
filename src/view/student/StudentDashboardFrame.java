package view.student;

import controller.StudentController;
import util.Constants;
import util.ThemeUtil;
import view.LoginFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Main window for a logged-in student, hosting the queue-related panels in tabs.
 */
public class StudentDashboardFrame extends JFrame {

    private final StudentController controller;
    private final JoinQueuePanel joinQueuePanel;
    private final MyTokensPanel myTokensPanel;

    public StudentDashboardFrame(StudentController controller) {
        this.controller = controller;
        setTitle(Constants.APP_TITLE + " - Student Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 560);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(ThemeUtil.BACKGROUND);

        add(buildHeader(), BorderLayout.NORTH);

        joinQueuePanel = new JoinQueuePanel(controller);
        myTokensPanel = new MyTokensPanel(controller);
        AnnouncementsPanel announcementsPanel = new AnnouncementsPanel(controller);
        FeedbackPanel feedbackPanel = new FeedbackPanel(controller);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(ThemeUtil.FONT_SUBTITLE.deriveFont(13f));
        tabs.setBackground(ThemeUtil.BACKGROUND);
        tabs.setForeground(ThemeUtil.TEXT_PRIMARY);
        tabs.setBorder(BorderFactory.createEmptyBorder(4, 6, 0, 6));
        tabs.addTab("Join Queue", ThemeUtil.icon("queue", ThemeUtil.PRIMARY), joinQueuePanel);
        tabs.addTab("My Tokens", ThemeUtil.icon("token", ThemeUtil.PRIMARY), myTokensPanel);
        tabs.addTab("Announcements", ThemeUtil.icon("announcement", ThemeUtil.PRIMARY), announcementsPanel);
        tabs.addTab("Feedback", ThemeUtil.icon("feedback", ThemeUtil.PRIMARY), feedbackPanel);
        tabs.setBackgroundAt(0, ThemeUtil.PRIMARY_LIGHT);
        tabs.setBackgroundAt(1, ThemeUtil.SKY_LIGHT);
        tabs.setBackgroundAt(2, ThemeUtil.PURPLE_LIGHT);
        tabs.setBackgroundAt(3, ThemeUtil.SUCCESS_LIGHT);

        tabs.addChangeListener(e -> {
            joinQueuePanel.refresh();
            myTokensPanel.refresh();
        });

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel header = ThemeUtil.createHeaderPanel("Welcome, " + controller.getStudent().getName());

        JButton logoutButton = ThemeUtil.createDangerButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        header.add(logoutButton, BorderLayout.EAST);

        return header;
    }
}
