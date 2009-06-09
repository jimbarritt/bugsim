/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.swing.layout;

import java.awt.*;

/**
 *  Description : Stretches Components horizontally but aligns them vertically in the centre, NORTH or SOUTH
 *  Created     : Jan 26, 2007 @ 4:10:10 PM by jim
 */
public class HorizontalStretchLayout implements LayoutManager2 {


    public static final String NORTH = "north";
    public static final String SOUTH = "south";
    public static final String CENTRE = "centre";

    public HorizontalStretchLayout() {
        this(CENTRE);
    }

    public HorizontalStretchLayout(String componentPosition) {
        _componentPosition = componentPosition;
    }

    public void addLayoutComponent(Component comp, Object constraints) {
        _component = comp;
        if (constraints != null) {
            _componentPosition = (String)constraints;
        }
    }

    public Dimension maximumLayoutSize(Container target) {
        return target.getSize();
    }

    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    public void invalidateLayout(Container target) {

    }

    public void addLayoutComponent(String name, Component comp) {
        addLayoutComponent(comp, CENTRE);
    }

    public void removeLayoutComponent(Component comp) {
        _component = null;
    }

    public Dimension preferredLayoutSize(Container parent) {
        if (_component != null) {
            return _component.getPreferredSize();
        }
        return parent.getPreferredSize();
    }

    public Dimension minimumLayoutSize(Container parent) {
        if (_component != null) {
            return _component.getMinimumSize();
        }
        return parent.getMinimumSize();
    }

    public void layoutContainer(Container parent) {
        if (_component != null) {
            Rectangle outerBounds= parent.getBounds();
            Rectangle innerBounds = _component.getBounds();

            int innerH = (innerBounds.height == 0) ? _component.getPreferredSize().height : innerBounds.height;




            int x, y, w, h;

            x = 0;
            y =  (outerBounds.height / 2)-(innerH/ 2);
            w = outerBounds.width;
            h = innerH;

            Rectangle newBounds = new Rectangle(x, y, w, h);
            _component.setBounds(newBounds);
        }
    }

    private Component _component;
    private String _componentPosition;


}
