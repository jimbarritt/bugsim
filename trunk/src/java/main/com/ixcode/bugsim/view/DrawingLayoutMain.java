/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view;

import com.ixcode.bugsim.model.agent.cabbage.AttractionFieldAgent;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgentFilter;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.bugsim.view.landscape.agent.AgentRendererRegistry;
import com.ixcode.bugsim.view.landscape.agent.IAgentRenderer;
import com.ixcode.bugsim.view.landscape.agent.RadiusOfAttractionRenderer;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.swing.JFrameExtension;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Iterator;
import java.util.List;


/**
 * Description : Used For Doing drawings on the landscape viewer for docs.
 */
public class DrawingLayoutMain {


    public static void main(String[] args) {

        Landscape l = new Landscape(new Simulation());
        l.setExtentX(100);
        l.setExtentY(100);

        DistanceRelativeToSeparationRenderer r = new DistanceRelativeToSeparationRenderer(
                l.getLogicalBounds().getCentre(), 0, 5);


        final LandscapeFrame f = new LandscapeFrame(l, null);
        final LandscapeView view = f.getLandscapeView();
        view.setLogicalGridResolution(10);
        view.setLogicalGridThickness(1);

        view.addBackgroundRenderer(r, 0);


        f.show();

//        drawExpandingRadiuses();


    }

    private static void drawExpandingRadiuses() {
        Landscape l = setupDrawing(0);

        final LandscapeFrame f = new LandscapeFrame(l, null);
        final LandscapeView view = f.getLandscapeView();
        view.setLogicalGridResolution(10);
        view.setLogicalGridThickness(1);


        f.show();


        JFrameExtension f2 = new JFrameExtension("Distance Control");
        JPanel p = new JPanel();
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 500, 0);

        slider.setMajorTickSpacing(100);
        slider.setPaintLabels(true);
        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setBackground(p.getBackground());
        slider.setBackground(Color.white);

        slider.setExtent(10);

        p.add(slider);
        f2.getContentPane().add(slider);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider)e.getSource();

                int value = s.getValue();
                Landscape l = setupDrawing(value);
                f.setLandscape(l);


            }
        });
        f2.setLocation(0, 0);
        f2.setSize(200, 100);
        f2.show();
    }


    private static Landscape setupDrawing(double distance) {

        double stepLength = 10d;
        CartesianBounds patchBounds = new CartesianBounds(0, 0, 80, 80);
        double borderRadius = patchBounds.getRadiusOfEnclosingCircle() + distance;

        Simulation s = new Simulation(null);
        Landscape l = new Landscape(s);
        s.setLandscape(l);
        double dim = borderRadius * 2 + 20;
        CartesianBounds landscapeB = new CartesianBounds(0, 0, dim, dim);
        l.setLogicalBounds(landscapeB);

        double centreX = landscapeB.getDoubleCentreX();
        double centreY = landscapeB.getDoubleCentreY();


        AttractionFieldAgent border = new AttractionFieldAgent(new Location(centreX, centreY), borderRadius);
        border.setColor(Color.red);
        border.setTransparency(0.1f);
        s.addAgent(border);


        AttractionFieldAgent stepLengthB = new AttractionFieldAgent(new Location(centreX, centreY), borderRadius - stepLength);
        stepLengthB.setColor(Color.red);
        stepLengthB.setTransparency(0.1f);
        s.addAgent(stepLengthB);

//        CabbageAgent[][] cabbages = CalculatedLayoutCabbageFactory.createCabbagePatch(CalculatedCabbageLayoutType.CORNER_CENTRE, 5d, 10d, centreX,
//                centreY, s, CabbageParameters.ISOLATION_CATEGORY, "grid", 0, patchBounds);

        //@todo need to create an instance of the CalculatedCabbageFactory (maybe have a default static factory method on it) 
        CabbageAgent[][] cabbages = new CabbageAgent[][]{};

        List cabbagesS = s.getAllAgents(CabbageAgentFilter.INSTANCE);
        RectangularCoordinate topLeft = new RectangularCoordinate(centreX - patchBounds.getDoubleWidth() / 2, centreY + patchBounds.getDoubleHeight() / 2);
        RectangularCoordinate topLeftCabbage = cabbages[0][3].getLocation().getCoordinate();

        double radius = topLeftCabbage.calculateDistanceTo(topLeft) + distance;

        for (Iterator itr = cabbagesS.iterator(); itr.hasNext();) {
            CabbageAgent agent = (CabbageAgent)itr.next();

            AttractionFieldAgent a = new AttractionFieldAgent(agent.getLocation(), radius);

            s.addAgent(a);

        }


        IAgentRenderer attractionRenderer = new RadiusOfAttractionRenderer();
        AgentRendererRegistry.INSTANCE.registerAgentRenderer(AttractionFieldAgent.AGENT_CLASS_ID, attractionRenderer);
        return l;
    }


}
