package view.student;

import controller.StudentController;
import model.Service;
import model.Token;
import util.ThemeUtil;
import view.shared.TableUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Lets the student see all active campus services and join a queue with one click.
 */
public class JoinQueuePanel extends JPanel {

    private final StudentController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private List<Service> currentServices;

    public JoinQueuePanel(StudentController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ThemeUtil.BACKGROUND);

        add(ThemeUtil.createSubtitleLabel("Available Services"), BorderLayout.NORTH);

        tableModel = TableUtil.createModel(new String[]{"Service", "Department", "Avg. Time (min)"});
        table = TableUtil.createStyledTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(ThemeUtil.BACKGROUND);
        JButton refreshButton = ThemeUtil.createSecondaryButton("Refresh");
        JButton joinButton = ThemeUtil.createPrimaryButton("Join Queue");
        refreshButton.addActionListener(e -> refresh());
        joinButton.addActionListener(e -> handleJoin());
        bottom.add(refreshButton);
        bottom.add(joinButton);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        currentServices = controller.getActiveServices();
        tableModel.setRowCount(0);
        for (Service s : currentServices) {
            tableModel.addRow(new Object[]{s.getName(), s.getDepartment(), s.getAvgServiceTimeMinutes()});
        }
    }

    private void handleJoin() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a service first.",
                    "No service selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Service selected = currentServices.get(row);
        Token token = controller.joinQueue(selected.getServiceId());
        JOptionPane.showMessageDialog(this,
                "You joined the queue for " + selected.getName() + ".\nYour token number is #" + token.getTokenNumber() + ".",
                "Queue joined", JOptionPane.INFORMATION_MESSAGE);
    }
}
