/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.framework.swing.JFrameExtension;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class OverlayingGlassPane extends JComponent {


    public OverlayingGlassPane(JFrameExtension parent) {

        _parent = parent;

    }

    public void paintComponent(Graphics graphics) {
        Point location = _parent.getContent().getLocation();
        Dimension size = _parent.getContent().getSize();
        double x = size.width / 2 + location.getX();
        double y = (size.height / 2) + location.getY();

        Graphics2D g = (Graphics2D)graphics;


        Ellipse2D centrePoint = new Ellipse2D.Double(x - 15, y - 15, 30, 30);
        g.setPaint(Color.GREEN);
        g.draw(centrePoint);

    }




    private JFrameExtension _parent;
}
