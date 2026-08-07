package view.student;

import controller.StudentController;
import model.Service;
import model.Token;
import model.TokenStatus;
import util.ThemeUtil;
import view.shared.TableUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Shows every token the student has ever taken, its live status and queue
 * position, and lets the student cancel a token that is still waiting.
 */
public class MyTokensPanel extends JPanel {

    private final StudentController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private List<Token> currentTokens;

    public MyTokensPanel(StudentController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ThemeUtil.BACKGROUND);

        add(ThemeUtil.createSubtitleLabel("My Tokens"), BorderLayout.NORTH);

        tableModel = TableUtil.createModel(new String[]{"Token #", "Service", "Status", "Position in Queue"});
        table = TableUtil.createStyledTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(ThemeUtil.BACKGROUND);
        JButton refreshButton = ThemeUtil.createSecondaryButton("Refresh");
        JButton cancelButton = ThemeUtil.createPrimaryButton("Cancel Token");
        refreshButton.addActionListener(e -> refresh());
        cancelButton.addActionListener(e -> handleCancel());
        bottom.add(refreshButton);
        bottom.add(cancelButton);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        currentTokens = controller.getMyTokens();
        tableModel.setRowCount(0);
        for (Token t : currentTokens) {
            String serviceName = controller.findService(t.getServiceId())
                    .map(Service::getName).orElse(t.getServiceId());
            String position = t.getStatus() == TokenStatus.WAITING
                    ? String.valueOf(controller.getQueuePosition(t.getTokenId(), t.getServiceId()))
                    : "-";
            tableModel.addRow(new Object[]{t.getTokenNumber(), serviceName, t.getStatus(), position});
        }
    }

    private void handleCancel() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a token first.",
                    "No token selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Token selected = currentTokens.get(row);
        if (selected.getStatus() != TokenStatus.WAITING) {
            JOptionPane.showMessageDialog(this, "Only waiting tokens can be cancelled.",
                    "Cannot cancel", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean cancelled = controller.cancelToken(selected.getTokenId());
        if (cancelled) {
            JOptionPane.showMessageDialog(this, "Token cancelled.");
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "Could not cancel this token.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
