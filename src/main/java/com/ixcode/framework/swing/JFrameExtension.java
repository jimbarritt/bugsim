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
        _content = content;
        setTitle(title);
        setSize(400, 400);
        setLocation(200, 200);

        addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent event) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void windowClosing(WindowEvent event) {
                if (_systemExitOnClose) {
                    if (log.isInfoEnabled()) {
                        log.info("Exiting Application!");
                    }
                    System.exit(0);
                }


            }

            public void windowClosed
                    (WindowEvent
                            event) {
            }

            public void windowIconified
                    (WindowEvent
                            event) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void windowDeiconified
                    (WindowEvent
                            event) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void windowActivated
                    (WindowEvent
                            event) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void windowDeactivated
                    (WindowEvent
                            event) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        }

        );

        final int BORDER_WIDTH = 100;
        Border b = BorderFactory.createEmptyBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH);
        _borderPanel = new JPanel();

        if (border) {
            _borderPanel.setBorder(b);
        }

        _borderPanel.setBackground(backgroundColor);
        _borderPanel.setLayout(new GridLayout(1, 1));
        if (_content != null) {
            _borderPanel.add(_content);
        }

        super.getContentPane().setLayout(new BorderLayout());
        super.getContentPane().add(_borderPanel, contentPosition);
        super.getContentPane().add(_statusBar, BorderLayout.SOUTH);
    }

    public Container getContentPane() {
        return _borderPanel;
    }

    protected void replaceStatusBar(JComponent newStatusBar) {
        super.getContentPane().remove(_statusBar);
        super.getContentPane().add(newStatusBar, BorderLayout.SOUTH);
    }


    public void setStatusBar(boolean show) {
        _statusBar.setVisible(show);
    }

    public StatusBar getStatusBar() {
        return _statusBar;
    }

    public JComponent getContent() {
        return _content;
    }

    public void setBorderBackground(Color color) {
        _borderPanel.setBackground(color);

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
        return _systemExitOnClose;
    }

    public void setSystemExitOnClose(boolean systemExitOnClose) {
        _systemExitOnClose = systemExitOnClose;
    }

    private static final Logger log = Logger.getLogger(JFrameExtension.class);
    private JComponent _content;
    private StatusBar _statusBar = new StatusBar();
    private Color _borderBackground;
    JPanel _borderPanel;
    private boolean _systemExitOnClose = true;


    protected void setCrosshairCursor() {
        setCursor(java.awt.Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
}


