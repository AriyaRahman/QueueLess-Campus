package view.admin;

import controller.AdminController;
import model.Service;
import model.Token;
import util.ThemeUtil;
import view.shared.TableUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Lets staff pick a service, see who is waiting, and call/complete
 * students one at a time.
 */
public class QueueMonitorPanel extends JPanel {

    private final AdminController controller;
    private final JComboBox<String> serviceCombo = new JComboBox<>();
    private final JLabel servingLabel = new JLabel("No one is currently being served.");
    private final DefaultTableModel tableModel;
    private final JTable table;
    private List<Service> currentServices;

    public QueueMonitorPanel(AdminController controller) {
        this.controller = controller;
        ThemeUtil.styleInput(serviceCombo);
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ThemeUtil.BACKGROUND);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.setBackground(ThemeUtil.BACKGROUND);
        top.add(ThemeUtil.createSubtitleLabel("Queue Monitor"), BorderLayout.NORTH);

        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectPanel.setBackground(ThemeUtil.BACKGROUND);
        selectPanel.add(new JLabel("Service:"));
        selectPanel.add(serviceCombo);
        serviceCombo.addActionListener(e -> refreshQueue());
        top.add(selectPanel, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        tableModel = TableUtil.createModel(new String[]{"Token #", "Student ID", "Status", "Issued At"});
        table = TableUtil.createStyledTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(ThemeUtil.BACKGROUND);
        servingLabel.setFont(ThemeUtil.FONT_SUBTITLE.deriveFont(13f));
        bottom.add(servingLabel, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setBackground(ThemeUtil.BACKGROUND);
        JButton refreshButton = ThemeUtil.createSecondaryButton("Refresh");
        JButton callNextButton = ThemeUtil.createPrimaryButton("Call Next");
        JButton completeButton = ThemeUtil.createSuccessButton("Complete Current");
        refreshButton.addActionListener(e -> refreshQueue());
        callNextButton.addActionListener(e -> handleCallNext());
        completeButton.addActionListener(e -> handleComplete());
        actions.add(refreshButton);
        actions.add(callNextButton);
        actions.add(completeButton);
        bottom.add(actions, BorderLayout.EAST);

        add(bottom, BorderLayout.SOUTH);

        reloadServices();
    }

    public void reloadServices() {
        currentServices = controller.getAllServices();
        String previous = (String) serviceCombo.getSelectedItem();
        serviceCombo.removeAllItems();
        for (Service s : currentServices) {
            if (s.isActive()) {
                serviceCombo.addItem(s.getName());
            }
        }
        if (previous != null) {
            serviceCombo.setSelectedItem(previous);
        }
        refreshQueue();
    }

    private Optional<Service> getSelectedService() {
        int index = serviceCombo.getSelectedIndex();
        if (index < 0) return Optional.empty();
        String name = (String) serviceCombo.getSelectedItem();
        return currentServices.stream().filter(s -> s.getName().equals(name)).findFirst();
    }

    private void refreshQueue() {
        tableModel.setRowCount(0);
        Optional<Service> service = getSelectedService();
        if (service.isEmpty()) {
            servingLabel.setText("No service selected.");
            return;
        }
        String serviceId = service.get().getServiceId();
        for (Token t : controller.getQueueSnapshot(serviceId)) {
            tableModel.addRow(new Object[]{t.getTokenNumber(), t.getStudentId(), t.getStatus(), t.getIssueTime()});
        }
        Optional<Token> serving = controller.getCurrentlyServing(serviceId);
        servingLabel.setText(serving.map(t -> "Currently serving token #" + t.getTokenNumber())
                .orElse("No one is currently being served."));
    }

    private void handleCallNext() {
        Optional<Service> service = getSelectedService();
        if (service.isEmpty()) return;
        Optional<Token> next = controller.callNext(service.get().getServiceId());
        if (next.isPresent()) {
            JOptionPane.showMessageDialog(this, "Now serving token #" + next.get().getTokenNumber());
        } else {
            JOptionPane.showMessageDialog(this, "The queue is empty.", "Nothing to call",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        refreshQueue();
    }

    private void handleComplete() {
        Optional<Service> service = getSelectedService();
        if (service.isEmpty()) return;
        Optional<Token> completed = controller.completeCurrent(service.get().getServiceId());
        if (completed.isPresent()) {
            JOptionPane.showMessageDialog(this, "Token #" + completed.get().getTokenNumber() + " marked as completed.");
        } else {
            JOptionPane.showMessageDialog(this, "No one is currently being served.",
                    "Nothing to complete", JOptionPane.INFORMATION_MESSAGE);
        }
        refreshQueue();
    }
}
