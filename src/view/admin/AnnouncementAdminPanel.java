package view.admin;

import controller.AdminController;
import model.Announcement;
import util.ThemeUtil;
import util.ValidationUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * Lets admin staff post new campus-wide announcements and see the history of past ones.
 */
public class AnnouncementAdminPanel extends JPanel {

    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    private final AdminController controller;
    private final JTextField titleField = new JTextField(25);
    private final JTextArea messageArea = new JTextArea(4, 25);
    private final JTextArea historyArea = new JTextArea();

    public AnnouncementAdminPanel(AdminController controller) {
        this.controller = controller;
        ThemeUtil.styleInput(titleField);
        ThemeUtil.styleInput(messageArea);
        ThemeUtil.styleInput(historyArea);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ThemeUtil.BACKGROUND);

        JPanel top = new JPanel(new BorderLayout(6, 6));
        top.setBackground(ThemeUtil.BACKGROUND);
        top.add(ThemeUtil.createSubtitleLabel("Post Announcement"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(ThemeUtil.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        form.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Message:"), gbc);
        gbc.gridx = 1;
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        form.add(new JScrollPane(messageArea), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JButton postButton = ThemeUtil.createPrimaryButton("Post Announcement");
        postButton.addActionListener(e -> handlePost());
        form.add(postButton, gbc);

        top.add(form, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyArea.setFont(ThemeUtil.FONT_BODY);
        add(new JScrollPane(historyArea), BorderLayout.CENTER);

        refresh();
    }

    private void handlePost() {
        String title = titleField.getText().trim();
        String message = messageArea.getText().trim();
        if (!ValidationUtil.isNotEmpty(title) || !ValidationUtil.isNotEmpty(message)) {
            JOptionPane.showMessageDialog(this, "Please fill in both title and message.",
                    "Invalid input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        controller.postAnnouncement(title, message);
        titleField.setText("");
        messageArea.setText("");
        refresh();
    }

    public void refresh() {
        StringBuilder builder = new StringBuilder();
        for (Announcement a : controller.getAnnouncements()) {
            builder.append(a.getTitle()).append("  (").append(a.getDate().format(DISPLAY_FORMAT)).append(")\n");
            builder.append(a.getMessage()).append("\n");
            builder.append("--------------------------------------------\n");
        }
        if (builder.length() == 0) {
            builder.append("No announcements posted yet.");
        }
        historyArea.setText(builder.toString());
        historyArea.setCaretPosition(0);
    }
}
