package view.student;

import controller.StudentController;
import util.Constants;
import util.ThemeUtil;
import view.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        tabs.addTab("Join Queue", joinQueuePanel);
        tabs.addTab("My Tokens", myTokensPanel);
        tabs.addTab("Announcements", announcementsPanel);
        tabs.addTab("Feedback", feedbackPanel);

        tabs.addChangeListener(e -> {
            joinQueuePanel.refresh();
            myTokensPanel.refresh();
        });

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeUtil.PRIMARY);
        header.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel welcome = new JLabel("Welcome, " + controller.getStudent().getName());
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
