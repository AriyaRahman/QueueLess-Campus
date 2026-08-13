package view.admin;

import controller.AdminController;
import model.Service;
import util.ThemeUtil;
import util.ValidationUtil;
import view.shared.TableUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Lets admin staff create new campus services and activate/deactivate existing ones.
 */
public class ServiceManagementPanel extends JPanel {

    private final AdminController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private List<Service> currentServices;
    private final Runnable onServicesChanged;

    public ServiceManagementPanel(AdminController controller, Runnable onServicesChanged) {
        this.controller = controller;
        this.onServicesChanged = onServicesChanged;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ThemeUtil.BACKGROUND);

        add(ThemeUtil.createSubtitleLabel("Manage Services"), BorderLayout.NORTH);

        tableModel = TableUtil.createModel(new String[]{"Name", "Department", "Avg. Time (min)", "Active"});
        table = TableUtil.createStyledTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(ThemeUtil.BACKGROUND);
        JButton refreshButton = ThemeUtil.createSecondaryButton("Refresh");
        JButton toggleButton = ThemeUtil.createAccentButton("Toggle Active");
        JButton addButton = ThemeUtil.createPrimaryButton("Add Service");
        refreshButton.addActionListener(e -> refresh());
        toggleButton.addActionListener(e -> handleToggle());
        addButton.addActionListener(e -> handleAdd());
        bottom.add(refreshButton);
        bottom.add(toggleButton);
        bottom.add(addButton);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        currentServices = controller.getAllServices();
        tableModel.setRowCount(0);
        for (Service s : currentServices) {
            tableModel.addRow(new Object[]{s.getName(), s.getDepartment(), s.getAvgServiceTimeMinutes(),
                    s.isActive() ? "Yes" : "No"});
        }
    }

    private void handleToggle() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a service first.",
                    "No service selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Service selected = currentServices.get(row);
        controller.setServiceActive(selected.getServiceId(), !selected.isActive());
        refresh();
        onServicesChanged.run();
    }

    private void handleAdd() {
        JTextField nameField = new JTextField();
        JTextField deptField = new JTextField();
        JTextField timeField = new JTextField();
        ThemeUtil.styleInput(nameField);
        ThemeUtil.styleInput(deptField);
        ThemeUtil.styleInput(timeField);

        Object[] fields = {
                "Service name:", nameField,
                "Department:", deptField,
                "Avg. service time (minutes):", timeField
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Add New Service",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String name = nameField.getText().trim();
        String department = deptField.getText().trim();
        String time = timeField.getText().trim();

        if (!ValidationUtil.isNotEmpty(name) || !ValidationUtil.isNotEmpty(department)
                || !ValidationUtil.isPositiveInteger(time)) {
            JOptionPane.showMessageDialog(this, "Please fill all fields correctly.",
                    "Invalid input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        controller.addService(name, department, Integer.parseInt(time));
        refresh();
        onServicesChanged.run();
    }
}
