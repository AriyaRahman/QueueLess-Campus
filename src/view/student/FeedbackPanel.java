package view.student;

import controller.StudentController;
import model.Service;
import util.ThemeUtil;
import util.ValidationUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Lets the student submit a 1-5 star rating and comment for a service they used.
 */
public class FeedbackPanel extends JPanel {

    private final StudentController controller;
    private final JComboBox<String> serviceCombo = new JComboBox<>();
    private final JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
    private final JTextArea commentArea = new JTextArea(6, 30);
    private List<Service> currentServices;

    public FeedbackPanel(StudentController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ThemeUtil.BACKGROUND);

        add(ThemeUtil.createSubtitleLabel("Leave Feedback"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(ThemeUtil.BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Service:"), gbc);
        gbc.gridx = 1;
        form.add(serviceCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Rating (1-5):"), gbc);
        gbc.gridx = 1;
        form.add(ratingSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        form.add(new JLabel("Comment:"), gbc);
        gbc.gridx = 1;
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        form.add(new JScrollPane(commentArea), gbc);

        add(form, BorderLayout.CENTER);

        JButton submitButton = ThemeUtil.createPrimaryButton("Submit Feedback");
        submitButton.addActionListener(e -> handleSubmit());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(ThemeUtil.BACKGROUND);
        bottom.add(submitButton);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        currentServices = controller.getActiveServices();
        serviceCombo.removeAllItems();
        for (Service s : currentServices) {
            serviceCombo.addItem(s.getName());
        }
    }

    private void handleSubmit() {
        int index = serviceCombo.getSelectedIndex();
        if (index < 0 || currentServices == null || currentServices.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No service available to review.",
                    "Cannot submit", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String comment = commentArea.getText().trim();
        if (!ValidationUtil.isNotEmpty(comment)) {
            JOptionPane.showMessageDialog(this, "Please write a short comment.",
                    "Comment required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Service selected = currentServices.get(index);
        int rating = (Integer) ratingSpinner.getValue();
        controller.submitFeedback(selected.getServiceId(), rating, comment);
        commentArea.setText("");
        JOptionPane.showMessageDialog(this, "Thank you for your feedback!");
    }
}
