package view.shared;

import util.ThemeUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        table.setRowHeight(26);
        table.setFont(ThemeUtil.FONT_BODY);
        table.setSelectionBackground(ThemeUtil.PRIMARY.brighter());
        table.setGridColor(new Color(224, 224, 224));
        table.getTableHeader().setFont(ThemeUtil.FONT_SUBTITLE.deriveFont(13f));
        table.getTableHeader().setBackground(ThemeUtil.PRIMARY);
        table.getTableHeader().setForeground(Color.BLACK);
        table.setFillsViewportHeight(true);
        return table;
    }
}
