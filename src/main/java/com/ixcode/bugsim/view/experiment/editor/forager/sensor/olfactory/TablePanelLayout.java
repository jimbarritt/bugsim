/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.forager.sensor.olfactory;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 6, 2007 @ 4:55:36 PM by jim
 */
public class TablePanelLayout implements LayoutManager2 {

    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints == null ) {
            throw new IllegalArgumentException("Must specify constraints - same as border layout except only North Centre and South supported.");
        }
           _map.put(comp, constraints);
    }

    public Dimension maximumLayoutSize(Container target) {
        return null;
    }

    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    public void invalidateLayout(Container target) {

    }

    public void addLayoutComponent(String name, Component comp) {
        throw new IllegalStateException("Not implemented");
    }

    public void removeLayoutComponent(Component comp) {

    }

    public Dimension preferredLayoutSize(Container parent) {
        return parent.getPreferredSize();
    }

    public Dimension minimumLayoutSize(Container parent) {
        return parent.getMinimumSize();
    }

    public void layoutContainer(Container parent) {
        JComponent northC = (JComponent)_map.get(NORTH);
        JComponent centreC = (JComponent)_map.get(CENTER);
        JComponent southC = (JComponent)_map.get(SOUTH);

        if (log.isInfoEnabled()) {
            log.info("Layout container...");
        }
        northC.setLocation(0, 0);

        int width= (int)parent.getWidth();
        int northHeight = (northC != null) ? (int)northC.getPreferredSize().getHeight() : 0;
        if (northC != null) {
            northC.setSize(width, northHeight);
        }

        int southHeight = (int)southC.getPreferredSize().getHeight();
        int centreHeight =parent.getHeight() - (northHeight + southHeight);

        centreC.setLocation(0, northHeight);
        centreC.setSize(width, centreHeight);

        southC.setLocation(0, northHeight+centreHeight);

        southC.setSize(width, southHeight);


    }
    private static final Logger log = Logger.getLogger(TablePanelLayout.class);
    public static final String NORTH = "north";
    public static final String CENTER = "center";
    public static final String SOUTH = "south";

    private Map _map = new HashMap();
}
