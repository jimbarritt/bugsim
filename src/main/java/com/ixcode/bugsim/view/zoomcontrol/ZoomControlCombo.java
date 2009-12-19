/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.zoomcontrol;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.ixcode.bugsim.view.landscape.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ZoomControlCombo extends JComboBox implements ItemListener {
    public ZoomControlCombo(LandscapeView view) {
        super.addItem("Fit To Screen");
        for (int i=0;i< ZOOM_VALUES.length;++i) {
            int zoom = ZOOM_VALUES[i];
            super.addItem(new Integer(zoom));

        }
        super.setPreferredSize(new Dimension(150, 10));
        super.setMaximumSize(new Dimension(150, 40));
        _view = view;
        super.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e) {
        if (log.isDebugEnabled()) {
            log.debug("Item State Changed! "  + e);
        }
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (super.getSelectedItem().equals("Fit To Screen")) {
                _view.setFitToScreen(true);
            } else {
                _view.setFitToScreen(false);
                int zoom = ((Integer)super.getSelectedItem()).intValue();
                _view.setZoomPercent((double)zoom / 100);
            }
            _view.repaint();
        }
    }



    private static final int[] ZOOM_VALUES = new int[] {3000, 2000, 1000, 700, 600, 500, 400, 300, 200, 150, 100, 75, 50, 25, 20, 15, 10, 5,  1};

    private LandscapeView _view;
    private static final Logger log = Logger.getLogger(ZoomControlCombo.class);
}
