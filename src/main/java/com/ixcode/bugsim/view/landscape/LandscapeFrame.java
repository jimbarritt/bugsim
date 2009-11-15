/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.explodingpixels.macwidgets.*;
import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.swing.*;

import javax.swing.*;
import static javax.swing.BorderFactory.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeFrame extends JFrameExtension {

    public static final String TITLE = "Landscape Viewer";

    private LandscapeView landscapeView;
    private AgentTypeChoiceCombo agentTypeChoiceCombo;
    private OpenSimulationAction openSimulationAction;
    private LandscapeToolbar landscapeToolbar;

    public LandscapeFrame(Landscape landscape) throws HeadlessException {
        this(landscape, new OpenSimulationAction());
    }

    public LandscapeFrame(OpenSimulationAction openSimAction) throws HeadlessException {
        super(TITLE, new JPanel(new BorderLayout()));

        initSizeAndLocation(landscapeToolbar.getHeight(), 300);

        openSimulationAction = openSimAction;

        super.setBorderBackground(landscapeView.getBackground());

        super.setSystemExitOnClose(false);
    }

    private void initSizeAndLocation(int paddingTop, int paddingRight) {
        Dimension screenSize = super.getScreenSize();
        int extent = screenSize.height;
        super.setSize(new Dimension(extent + (paddingRight - paddingTop), extent));
        super.setLocation(0, 0);
    }

    public LandscapeFrame(Landscape landscape, OpenSimulationAction openSimAction) throws HeadlessException {
        super(TITLE, new JPanel(new BorderLayout()));

        // For some versions of Mac OS X, Java will handle painting the Unified Tool Bar.
        // Calling this method ensures that this painting is turned on if necessary.


        openSimulationAction = openSimAction;

        JPanel container = (JPanel) super.getContent();

        agentTypeChoiceCombo = new AgentTypeChoiceCombo(landscape.getSimulation().getAgentRegistry());
        landscapeView = new LandscapeView(landscape, agentTypeChoiceCombo, getStatusBar());
        landscapeToolbar = new LandscapeToolbar(landscapeView, agentTypeChoiceCombo, openSimulationAction);

        JPanel landscapeViewContainer = new JPanel(new BorderLayout());
        landscapeViewContainer.setBorder(createEmptyBorder(20, 20, 20, 20));
        landscapeViewContainer.setBackground(Color.DARK_GRAY);

        landscapeViewContainer.add(landscapeView, BorderLayout.CENTER);

        JPanel controls = new JPanel(new BorderLayout());
        controls.add(Box.createHorizontalStrut(300), BorderLayout.NORTH);
        controls.add(new JLabel("Viewing Controls"), BorderLayout.CENTER);
        container.add(controls, BorderLayout.EAST);

        MacUtils.makeWindowLeopardStyle(getRootPane());
        UnifiedToolBar toolBar = new UnifiedToolBar();
        JButton button = new JButton("My Button");
        button.putClientProperty("JButton.buttonType", "textured");
        toolBar.addComponentToLeft(button);
        container.add(toolBar.getComponent(), BorderLayout.NORTH);

        initSizeAndLocation(50, 300);


        container.add(landscapeViewContainer, BorderLayout.CENTER);

        BottomBar bottomBar = new BottomBar(BottomBarSize.SMALL);
        bottomBar.addComponentToLeft(MacWidgetFactory.createEmphasizedLabel(" Status"));

        super.replaceStatusBar(bottomBar.getComponent());

        super.setSystemExitOnClose(false);
    }

    private static class TestPanel extends JComponent {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Rectangle shape = g.getClipBounds();
            g.setColor(Color.white);
            g.fillRect(shape.x, shape.y, shape.width, shape.height);

            Rectangle test = new Rectangle(0, 0, shape.width, shape.height);
            g.setColor(Color.blue);
            int strokeWidth = 4;
            int halfStrokeWidth = strokeWidth / 2;
            g2d.setStroke(new BasicStroke(strokeWidth));
            g.drawRect(test.x + halfStrokeWidth, test.y + halfStrokeWidth, test.width - strokeWidth, test.height - strokeWidth);
        }
    }

    public void open() {
        setVisible(true);
    }

    public LandscapeView getLandscapeView() {
        return landscapeView;
    }

    public AgentTypeChoiceCombo getAgentChoiceCombo() {
        return agentTypeChoiceCombo;
    }

    public void setLandscape(Landscape landscape) {

        landscapeView.setLandscape(landscape);
        landscape.fireAgentsChangedEvent();


//        _view.addPropertyChangeListener(new LandscapeStatusBarUpdater(super.getStatusBar()));

    }

    public void addActionToToolbar(Action action) {
        getToolbar().add(action);
    }

    private static JComponent createContent(Landscape landscape) {
        JPanel container = new JPanel(new BorderLayout());
        return container;
    }

    public LandscapeToolbar getToolbar() {
        return landscapeToolbar;
    }


}
