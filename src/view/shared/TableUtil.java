package view.shared;

import util.ThemeUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;


public final class TableUtil {

    private TableUtil() { }

    public static DefaultTableModel createModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    public static JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(ThemeUtil.FONT_BODY);
        table.setSelectionBackground(ThemeUtil.PRIMARY_LIGHT);
        table.setSelectionForeground(ThemeUtil.TEXT_PRIMARY);
        table.setGridColor(ThemeUtil.BORDER);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(ThemeUtil.FONT_SUBTITLE.deriveFont(13f));
        header.setOpaque(true);
        header.setBackground(ThemeUtil.PRIMARY);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ThemeUtil.ACCENT));

        // header colour doesn't always apply directly, so set it via renderer
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        headerRenderer.setFont(ThemeUtil.FONT_SUBTITLE.deriveFont(13f));
        headerRenderer.setForeground(Color.WHITE);
        headerRenderer.setBackground(ThemeUtil.PRIMARY);
        headerRenderer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        header.setDefaultRenderer(headerRenderer);

        // alternate row colours for readability
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                                                             boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : ThemeUtil.BACKGROUND);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return c;
            }
        });

        return table;
    }
}
