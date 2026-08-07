package view.student;

import controller.StudentController;
import model.Announcement;
import util.ThemeUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * Read-only feed of campus-wide announcements, newest first.
 */
public class AnnouncementsPanel extends JPanel {

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    private final StudentController controller;
    private final JTextArea textArea = new JTextArea();

    public AnnouncementsPanel(StudentController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ThemeUtil.BACKGROUND);

        add(ThemeUtil.createSubtitleLabel("Announcements"), BorderLayout.NORTH);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(ThemeUtil.FONT_BODY);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton refreshButton = ThemeUtil.createSecondaryButton("Refresh");
        refreshButton.addActionListener(e -> refresh());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(ThemeUtil.BACKGROUND);
        bottom.add(refreshButton);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        StringBuilder builder = new StringBuilder();
        for (Announcement a : controller.getAnnouncements()) {
            builder.append(a.getTitle()).append("  (").append(a.getDate().format(DISPLAY_FORMAT))
                    .append(" - ").append(a.getPostedBy()).append(")\n");
            builder.append(a.getMessage()).append("\n");
            builder.append("--------------------------------------------\n");
        }
        if (builder.length() == 0) {
            builder.append("No announcements yet.");
        }
        textArea.setText(builder.toString());
        textArea.setCaretPosition(0);
    }
}
