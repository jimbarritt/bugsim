/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.viewmode;

import com.ixcode.framework.swing.Point2DFormatter;
import com.ixcode.framework.swing.StatusBar;
import com.ixcode.bugsim.view.landscape.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeStatusBarUpdater implements PropertyChangeListener {

    public LandscapeStatusBarUpdater(StatusBar statusBar) {
        _statusBar = statusBar;
    }

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        LandscapeView viewer = (LandscapeView)propertyChangeEvent.getSource();
        StringBuffer sb = new StringBuffer();

        sb.append("Vw (" + F2.format(viewer.getDisplayWidth()) + ", " + F2.format(viewer.getDisplayHeight()) + ")");
        sb.append(", Sc (" + F2.format(viewer.getScaleX()) + ", " + F2.format(viewer.getScaleY()) + ")");
        sb.append(", Zc" + Point2DFormatter.Double.format(viewer.getZoomCenter()));
        sb.append(", Tr(x=" + F2.format(viewer.getTranslateX()) + ", y=" + F2.format(viewer.getTranslateY()) + ")");

        sb.append(", Cl (" + F2.format(viewer.getLandscapeClipSizeX()) + ", " + F2.format(viewer.getLandscapeClipSizeY()) + ")");
        sb.append(", Ls " + Point2DFormatter.Double.format(viewer.getLandscapeOrigin()));
        _statusBar.setText(sb.toString());
    }


    private static final NumberFormat F5 = new DecimalFormat("#,##0.00000");
    private static final     NumberFormat F2 = new DecimalFormat("#,##0.00");
    private StatusBar _statusBar;
}
