package view.admin;

import controller.AdminController;
import service.ReportManager;
import util.ThemeUtil;
import view.shared.TableUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;

/**
 * Displays per-service usage statistics and lets admin export them as CSV.
 */
public class ReportPanel extends JPanel {

    private final AdminController controller;
    private final DefaultTableModel tableModel;

    public ReportPanel(AdminController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(ThemeUtil.BACKGROUND);

        add(ThemeUtil.createSubtitleLabel("Service Reports"), BorderLayout.NORTH);

        tableModel = TableUtil.createModel(new String[]{"Service", "Issued", "Completed", "Cancelled", "Avg. Wait (min)"});
        JTable table = TableUtil.createStyledTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(ThemeUtil.BACKGROUND);
        JButton refreshButton = ThemeUtil.createSecondaryButton("Refresh");
        JButton exportButton = ThemeUtil.createPrimaryButton("Export CSV");
        refreshButton.addActionListener(e -> refresh());
        exportButton.addActionListener(e -> handleExport());
        bottom.add(refreshButton);
        bottom.add(exportButton);
        add(bottom, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (ReportManager.ServiceReportRow row : controller.generateReport()) {
            tableModel.addRow(new Object[]{row.serviceName, row.issued, row.completed, row.cancelled, row.avgWaitMinutes});
        }
    }

    private void handleExport() {
        String fileName = "data" + File.separator + "report_" + LocalDate.now() + ".csv";
        controller.exportReport(fileName);
        JOptionPane.showMessageDialog(this, "Report exported to " + fileName);
    }
}
