/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import org.apache.log4j.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import static java.awt.Toolkit.*;
import java.awt.event.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class JFrameExtension extends JFrame {

    private static final Logger log = Logger.getLogger(JFrameExtension.class);
    private JComponent content;
    private StatusBar statusBar = new StatusBar();
    private Color borderBackround;
    JPanel borderPanel;
    private boolean systemExitOnClose = true;


    public JFrameExtension(String title) throws HeadlessException {
        this(title, null);
    }

    public JFrameExtension(String title, JComponent content) throws HeadlessException {
        this(title, content, Color.GRAY, BorderLayout.CENTER, false);

    }

    public JFrameExtension(String title, JComponent content, boolean border) throws HeadlessException {
        this(title, content, Color.GRAY, BorderLayout.CENTER, border);

    }

    public JFrameExtension(String title, JComponent content, Color backgroundColor, String contentPosition, boolean border) throws HeadlessException {
        super();
        this.content = content;
        setTitle(title);
        setSize(400, 400);
        setLocation(200, 200);

        final int BORDER_WIDTH = 100;
        Border b = BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH);
        borderPanel = new JPanel();

        if (border) {
            borderPanel.setBorder(b);
        }

        borderPanel.setBackground(backgroundColor);
        borderPanel.setLayout(new GridLayout(1, 1));
        if (this.content != null) {
            borderPanel.add(this.content);
        }

        super.getContentPane().setLayout(new BorderLayout());
        super.getContentPane().add(borderPanel, contentPosition);
        super.getContentPane().add(statusBar, BorderLayout.SOUTH);
    }

    public Container getContentPane() {
        return borderPanel;
    }

    protected void replaceStatusBarComponent(JComponent newStatusBar) {
        super.getContentPane().remove(statusBar);
        super.getContentPane().add(newStatusBar, BorderLayout.SOUTH);
    }


    public void setStatusBar(boolean show) {
        statusBar.setVisible(show);
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public JComponent getContent() {
        return content;
    }

    public void setBorderBackground(Color color) {
        borderPanel.setBackground(color);

    }

    public Dimension getScreenSize() {
        return getDefaultToolkit().getScreenSize();
    }

    public static void centreWindowOnScreen(Window window) {
        Dimension size = window.getSize();
        Toolkit toolkit = getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int x = (int) ((screenSize.getWidth() / 2) - (size.getWidth() / 2));
        int y = (int) ((screenSize.getHeight() / 2) - (size.getHeight() / 2));

        window.setLocation(x, y);

    }

    public static void setSystemLookAndFeel() {
        try {
            String sysLook = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(sysLook);
            if (log.isInfoEnabled()) {
                log.info("Set look and feel to " + sysLook);
            }
        } catch (Throwable t) {
            log.error("Could not set system look and feel", t);
        }
    }

    public boolean isSystemExitOnClose() {
        return systemExitOnClose;
    }

    public void setSystemExitOnClose(boolean systemExitOnClose) {
        this.systemExitOnClose = systemExitOnClose;
    }


    protected void setCrosshairCursor() {
        setCursor(java.awt.Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void replaceStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }
}


