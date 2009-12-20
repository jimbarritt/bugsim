package com.ixcode.bugsim.view.landscape;

import com.explodingpixels.macwidgets.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;

import static javax.swing.BorderFactory.*;
import javax.swing.*;
import java.awt.*;
import static java.awt.Toolkit.*;

public class LandscapeFrame extends JFrameExtension {

    private LandscapeView landscapeView;

    public LandscapeFrame(Landscape landscape) throws HeadlessException {
        super("Bugsim - [Landscape View]", new JPanel(new BorderLayout()));

        initialiseStatusBar();
        landscapeView = new LandscapeView(landscape, getStatusBar());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = (JPanel) super.getContent();
        makeWindowLookLikeOsx(container);

        container.add(createLandscapeViewContainer(), BorderLayout.CENTER);
        container.add(createControlPanel(), BorderLayout.EAST);

        initSizeAndLocation(50, 300);        
    }

    public void open() {
        setVisible(true);
    }

    private JPanel createLandscapeViewContainer() {
        JPanel landscapeViewContainer = new JPanel(new BorderLayout());
        landscapeViewContainer.setBorder(createEmptyBorder(20, 20, 20, 20));
        landscapeViewContainer.setBackground(Color.DARK_GRAY);

        landscapeViewContainer.add(landscapeView, BorderLayout.CENTER);
        return landscapeViewContainer;
    }

    private JPanel createControlPanel() {
        JPanel controls = new JPanel(new BorderLayout());
        controls.add(Box.createHorizontalStrut(300), BorderLayout.NORTH);
        controls.add(new JLabel("Viewing Controls"), BorderLayout.CENTER);
        return controls;
    }

    private void initialiseStatusBar() {
        FancyStatusBar bottomBar = new FancyStatusBar();
        super.replaceStatusBarComponent(bottomBar.getComponent());
        super.replaceStatusBar(bottomBar);
    }

    /**
     * For some versions of Mac OS X, Java will handle painting the Unified Tool Bar.
     * Calling this method ensures that this painting is turned on if necessary.
     */
    private void makeWindowLookLikeOsx(JPanel container) {
        MacUtils.makeWindowLeopardStyle(getRootPane());
        UnifiedToolBar toolBar = new UnifiedToolBar();
        JButton button = new JButton("My Button");
        button.putClientProperty("JButton.buttonType", "textured");
        toolBar.addComponentToLeft(button);
        container.add(toolBar.getComponent(), BorderLayout.NORTH);
    }

    private void initSizeAndLocation(int paddingTop, int paddingRight) {
        Dimension screenSize = getDefaultToolkit().getScreenSize();
        int extent = screenSize.height;
        super.setSize(new Dimension(extent + (paddingRight - paddingTop), extent));
        super.setLocation(0, 0);
    }

}
