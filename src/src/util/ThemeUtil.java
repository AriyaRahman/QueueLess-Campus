package util;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Central visual theme for QueueLess Campus.
 * Uses only Swing/AWT drawing, so no external icon libraries are required.
 */
public final class ThemeUtil {
    private ThemeUtil() { }

    /* Modern, colourful but formal palette. */
    public static final Color PRIMARY = new Color(72, 86, 190);
    public static final Color PRIMARY_DARK = new Color(49, 61, 150);
    public static final Color PRIMARY_LIGHT = new Color(224, 228, 255);
    public static final Color ACCENT = new Color(0, 156, 166);
    public static final Color ACCENT_DARK = new Color(0, 117, 126);
    public static final Color SUCCESS = new Color(38, 153, 92);
    public static final Color SUCCESS_LIGHT = new Color(220, 246, 231);
    public static final Color WARNING = new Color(232, 145, 33);
    public static final Color WARNING_LIGHT = new Color(255, 241, 214);
    public static final Color DANGER = new Color(213, 64, 78);
    public static final Color DANGER_LIGHT = new Color(255, 225, 229);
    public static final Color PURPLE = new Color(126, 87, 194);
    public static final Color PURPLE_LIGHT = new Color(238, 231, 252);
    public static final Color SKY = new Color(57, 133, 196);
    public static final Color SKY_LIGHT = new Color(229, 241, 252);
    public static final Color BACKGROUND = new Color(244, 247, 252);
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final Color HEADER_TEXT = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(31, 39, 58);
    public static final Color TEXT_SECONDARY = new Color(99, 108, 128);
    public static final Color BORDER = new Color(218, 224, 237);
    public static final Color INPUT_BACKGROUND = new Color(250, 251, 255);

    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN, 12);

    private static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_MIN_WIDTH = 145;

    public enum ButtonStyle { PRIMARY, SECONDARY, ACCENT, DANGER, SUCCESS }

    public static void applySystemLookAndFeel() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) { }
    }

    public static JButton createPrimaryButton(String text) { return createButton(text, ButtonStyle.PRIMARY); }
    public static JButton createSecondaryButton(String text) { return createButton(text, ButtonStyle.SECONDARY); }
    public static JButton createAccentButton(String text) { return createButton(text, ButtonStyle.ACCENT); }
    public static JButton createDangerButton(String text) { return createButton(text, ButtonStyle.DANGER); }
    public static JButton createSuccessButton(String text) { return createButton(text, ButtonStyle.SUCCESS); }

    public static JButton createButton(String text, ButtonStyle style) {
        Color base, hover, foreground;
        boolean filled = true;
        switch (style) {
            case SECONDARY:
                base = Color.WHITE; hover = PRIMARY_LIGHT; foreground = PRIMARY_DARK; filled = false; break;
            case ACCENT:
                base = ACCENT; hover = ACCENT_DARK; foreground = Color.WHITE; break;
            case DANGER:
                base = DANGER; hover = DANGER.darker(); foreground = Color.WHITE; break;
            case SUCCESS:
                base = SUCCESS; hover = SUCCESS.darker(); foreground = Color.WHITE; break;
            case PRIMARY:
            default:
                base = PRIMARY; hover = PRIMARY_DARK; foreground = Color.WHITE; break;
        }
        RoundedButton button = new RoundedButton(text, base, hover, foreground, filled);
        button.setFont(FONT_BUTTON);
        FontMetrics fm = button.getFontMetrics(FONT_BUTTON);
        int width = Math.max(BUTTON_MIN_WIDTH, fm.stringWidth(text) + 72);
        Dimension size = new Dimension(width, BUTTON_HEIGHT);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setIcon(iconFor(text, foreground));
        button.setIconTextGap(8);
        return button;
    }

    /** Public vector icon factory used by dashboards, tabs and labels. */
    public static Icon icon(String key, Color color) {
        if (key == null) return null;
        String k = key.toLowerCase();
        ActionIcon.Type type;
        if (k.contains("queue")) type = ActionIcon.Type.QUEUE;
        else if (k.contains("token")) type = ActionIcon.Type.TICKET;
        else if (k.contains("announcement")) type = ActionIcon.Type.BELL;
        else if (k.contains("feedback")) type = ActionIcon.Type.FEEDBACK;
        else if (k.contains("report")) type = ActionIcon.Type.REPORT;
        else if (k.contains("service")) type = ActionIcon.Type.SERVICE;
        else if (k.contains("student") || k.contains("user")) type = ActionIcon.Type.USER;
        else if (k.contains("staff") || k.contains("admin")) type = ActionIcon.Type.ADMIN;
        else if (k.contains("email")) type = ActionIcon.Type.EMAIL;
        else if (k.contains("password") || k.contains("lock")) type = ActionIcon.Type.LOCK;
        else if (k.contains("rating") || k.contains("star")) type = ActionIcon.Type.STAR;
        else if (k.contains("comment") || k.contains("message")) type = ActionIcon.Type.MESSAGE;
        else if (k.contains("time") || k.contains("clock")) type = ActionIcon.Type.CLOCK;
        else if (k.contains("login")) type = ActionIcon.Type.LOGIN;
        else if (k.contains("logout")) type = ActionIcon.Type.LOGOUT;
        else if (k.contains("refresh")) type = ActionIcon.Type.REFRESH;
        else if (k.contains("cancel")) type = ActionIcon.Type.CANCEL;
        else if (k.contains("submit") || k.contains("post")) type = ActionIcon.Type.SEND;
        else if (k.contains("export")) type = ActionIcon.Type.EXPORT;
        else if (k.contains("add") || k.contains("register")) type = ActionIcon.Type.ADD;
        else if (k.contains("toggle")) type = ActionIcon.Type.TOGGLE;
        else if (k.contains("call next") || k.contains("next")) type = ActionIcon.Type.NEXT;
        else if (k.contains("complete") || k.contains("check")) type = ActionIcon.Type.CHECK;
        else type = ActionIcon.Type.DOT;
        return new ActionIcon(type, color == null ? PRIMARY : color);
    }

    private static Icon iconFor(String text, Color color) { return icon(text, color); }

    private static final class ActionIcon implements Icon {
        enum Type { LOGOUT, LOGIN, REFRESH, TICKET, CANCEL, SEND, EXPORT, ADD, TOGGLE, NEXT, CHECK,
            QUEUE, BELL, FEEDBACK, REPORT, SERVICE, USER, ADMIN, EMAIL, LOCK, STAR, MESSAGE, CLOCK, DOT }
        private final Type type; private final Color color;
        ActionIcon(Type type, Color color) { this.type = type; this.color = color; }
        public int getIconWidth() { return 18; }
        public int getIconHeight() { return 18; }

        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color); g2.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int cx = x + 9, cy = y + 9;
            switch (type) {
                case LOGOUT: line(g2,x+2,y+3,x+2,y+15); line(g2,x+2,y+3,x+7,y+3); line(g2,x+2,y+15,x+7,y+15); line(g2,x+7,y+9,x+16,y+9); line(g2,x+12,y+5,x+16,y+9); line(g2,x+12,y+13,x+16,y+9); break;
                case LOGIN: line(g2,x+16,y+3,x+16,y+15); line(g2,x+16,y+3,x+11,y+3); line(g2,x+16,y+15,x+11,y+15); line(g2,x+2,y+9,x+12,y+9); line(g2,x+8,y+5,x+12,y+9); line(g2,x+8,y+13,x+12,y+9); break;
                case REFRESH: g2.drawArc(x+2,y+2,14,14,45,270); line(g2,x+13,y+2,x+16,y+2); line(g2,x+16,y+2,x+15,y+6); break;
                case TICKET: g2.drawRoundRect(x+2,y+4,14,10,2,2); line(g2,x+8,y+4,x+8,y+14); break;
                case CANCEL: line(g2,x+4,y+4,x+14,y+14); line(g2,x+14,y+4,x+4,y+14); break;
                case SEND: Polygon p=new Polygon(new int[]{x+2,x+16,x+5},new int[]{y+9,y+2,y+16},3); g2.drawPolygon(p); line(g2,x+5,y+9,x+13,y+6); break;
                case EXPORT: g2.drawRect(x+3,y+2,12,13); line(g2,x+9,y+5,x+9,y+13); line(g2,x+6,y+10,x+9,y+13); line(g2,x+12,y+10,x+9,y+13); break;
                case ADD: g2.drawRoundRect(x+3,y+3,12,12,2,2); line(g2,cx,y+6,cx,y+12); line(g2,x+6,cy,x+12,cy); break;
                case TOGGLE: g2.drawRoundRect(x+2,y+5,14,8,4,4); g2.fillOval(x+9,y+6,6,6); break;
                case NEXT: line(g2,x+3,y+9,x+12,y+9); line(g2,x+9,y+5,x+13,y+9); line(g2,x+9,y+13,x+13,y+9); break;
                case CHECK: line(g2,x+3,y+9,x+7,y+13); line(g2,x+7,y+13,x+15,y+4); break;
                case QUEUE: g2.drawRoundRect(x+2,y+3,14,12,3,3); line(g2,x+5,y+7,x+13,y+7); line(g2,x+5,y+10,x+11,y+10); break;
                case BELL: g2.drawArc(x+4,y+3,10,11,0,180); line(g2,x+4,y+8,x+4,y+13); line(g2,x+14,y+8,x+14,y+13); line(g2,x+3,y+13,x+15,y+13); g2.fillOval(x+7,y+14,4,2); break;
                case FEEDBACK: g2.drawRoundRect(x+2,y+3,14,10,3,3); Polygon q=new Polygon(new int[]{x+5,x+8,x+5},new int[]{y+13,y+16,y+13},3); g2.drawPolygon(q); break;
                case REPORT: g2.drawRect(x+3,y+3,12,13); line(g2,x+6,y+12,x+6,y+9); line(g2,x+9,y+12,x+9,y+6); line(g2,x+12,y+12,x+12,y+8); break;
                case SERVICE: g2.drawRoundRect(x+2,y+5,14,9,2,2); g2.drawArc(x+5,y+2,8,7,0,180); break;
                case USER: g2.drawOval(x+6,y+2,6,6); g2.drawArc(x+3,y+9,12,9,0,180); break;
                case ADMIN: g2.drawOval(x+6,y+2,6,6); g2.drawArc(x+3,y+9,12,9,0,180); line(g2,x+9,y+5,x+9,y+9); break;
                case EMAIL: g2.drawRoundRect(x+2,y+4,14,10,2,2); line(g2,x+3,y+5,x+9,y+10); line(g2,x+15,y+5,x+9,y+10); break;
                case LOCK: g2.drawRoundRect(x+4,y+8,10,8,2,2); g2.drawArc(x+5,y+2,8,10,0,180); break;
                case STAR: Polygon s=new Polygon(); for(int i=0;i<10;i++){double a=-Math.PI/2+i*Math.PI/5; int r=(i%2==0?7:3); s.addPoint(cx+(int)(Math.cos(a)*r),cy+(int)(Math.sin(a)*r));} g2.drawPolygon(s); break;
                case MESSAGE: g2.drawRoundRect(x+2,y+3,14,10,3,3); line(g2,x+5,y+13,x+5,y+16); line(g2,x+5,y+16,x+8,y+13); break;
                case CLOCK: g2.drawOval(x+2,y+2,14,14); line(g2,cx,cy,cx,y+5); line(g2,cx,cy,cx+4,cy+2); break;
                case DOT: g2.fillOval(x+6,y+6,6,6); break;
            }
            g2.dispose();
        }
        private static void line(Graphics2D g,int x1,int y1,int x2,int y2){g.drawLine(x1,y1,x2,y2);}
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, icon("queue", PRIMARY), SwingConstants.LEFT);
        label.setFont(FONT_TITLE); label.setForeground(TEXT_PRIMARY); label.setIconTextGap(10); return label;
    }

    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text, icon(iconKey(text), PRIMARY), SwingConstants.LEFT);
        label.setFont(FONT_SUBTITLE); label.setForeground(PRIMARY_DARK); label.setIconTextGap(8); return label;
    }

    private static String iconKey(String text) {
        String t = text == null ? "" : text.toLowerCase();
        if (t.contains("queue")) return "queue";
        if (t.contains("token")) return "token";
        if (t.contains("announcement")) return "announcement";
        if (t.contains("feedback")) return "feedback";
        if (t.contains("report")) return "report";
        if (t.contains("service")) return "service";
        if (t.contains("student")) return "student";
        return "dashboard";
    }

    public static void styleInput(JComponent component) {
        component.setFont(FONT_BODY);
        component.setForeground(TEXT_PRIMARY);
        component.setBackground(INPUT_BACKGROUND);
        component.setBorder(new CompoundBorder(new LineBorder(BORDER, 1, true), new EmptyBorder(7, 9, 7, 9)));
        if (component instanceof JTextArea) ((JTextArea) component).setLineWrap(true);
    }

    public static void styleLabel(JLabel label, String key) {
        label.setFont(FONT_BODY.deriveFont(Font.BOLD));
        label.setForeground(TEXT_PRIMARY);
        label.setIcon(icon(key, PRIMARY));
        label.setIconTextGap(6);
    }

    public static JPanel createHeaderPanel(String welcomeText) {
        JPanel header = new GradientPanel(PRIMARY_DARK, ACCENT);
        header.setLayout(new BorderLayout(12, 0));
        header.setBorder(new EmptyBorder(13, 22, 13, 22));
        JLabel welcome = new JLabel(welcomeText, icon("user", Color.WHITE), SwingConstants.LEFT);
        welcome.setFont(FONT_SUBTITLE); welcome.setForeground(HEADER_TEXT); welcome.setIconTextGap(9);
        header.add(welcome, BorderLayout.WEST);
        return header;
    }

    public static class GradientPanel extends JPanel {
        private final Color start, end;
        public GradientPanel(Color start, Color end) { this.start=start; this.end=end; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2=(Graphics2D)g.create(); g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(0,0,start,getWidth(),getHeight(),end)); g2.fillRect(0,0,getWidth(),getHeight()); g2.dispose(); super.paintComponent(g);
        }
    }

    private static class RoundedButton extends JButton {
        private final Color base, hover; private final boolean filled; private boolean hovering;
        RoundedButton(String text,Color base,Color hover,Color foreground,boolean filled){
            super(text); this.base=base;this.hover=hover;this.filled=filled;setForeground(foreground);
            setContentAreaFilled(false);setFocusPainted(false);setBorderPainted(false);setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter(){public void mouseEntered(MouseEvent e){hovering=true;repaint();} public void mouseExited(MouseEvent e){hovering=false;repaint();}});
        }
        @Override protected void paintComponent(Graphics g){
            Graphics2D g2=(Graphics2D)g.create();g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            RoundRectangle2D shape=new RoundRectangle2D.Float(0,0,getWidth()-1,getHeight()-1,12,12);
            if(filled){g2.setColor(hovering?hover:base);g2.fill(shape);}else{g2.setColor(Color.WHITE);g2.fill(shape);g2.setColor(hovering?hover:base);g2.setStroke(new BasicStroke(1.5f));g2.draw(shape);}g2.dispose();super.paintComponent(g);
        }
    }
}
