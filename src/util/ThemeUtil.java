package util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Centralised colour palette, fonts and small Swing component factories
 */
public final class ThemeUtil {

    private ThemeUtil() { }

    public static final Color PRIMARY = new Color(25, 70, 120);
    public static final Color PRIMARY_DARK = new Color(19, 58, 105);
    public static final Color ACCENT = new Color(0, 150, 136);
    public static final Color BACKGROUND = Color.WHITE;
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final Color SUCCESS = new Color(46, 125, 50);
    public static final Color WARNING = new Color(230, 126, 34);
    public static final Color DANGER = new Color(198, 40, 40);
    public static final Color TEXT_PRIMARY = Color.BLACK;
    public static final Color TEXT_SECONDARY = new Color(100, 100, 100);

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 15);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 13);

    public static void applySystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // fall back to default cross-platform look and feel
        }
    }

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(PRIMARY);
        button.setForeground(Color.BLACK);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(8, 18, 8, 18));
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BACKGROUND);
        button.setForeground(PRIMARY_DARK);
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(PRIMARY, 1));
        return button;
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_TITLE);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_SUBTITLE);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }
}
