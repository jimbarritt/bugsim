/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.experiment;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class EggCountPanel extends JPanel {

    public EggCountPanel() {
        _coords.setForeground(Color.DARK_GRAY);
        _eggCount.setForeground(Color.BLUE);
        super.add(_coords);
        super.add(_eggCount);


    }

    public void setData(long x, long y, long eggCount) {
      _coords.setText(createLabelText(x, y));
        _eggCount.setText(FORMAT.format(eggCount));
    }

    private String createLabelText(long x, long y) {
        return "(" + x + "," + y + ") : ";
    }

    private JLabel _coords = new JLabel();
    private JLabel _eggCount = new JLabel();
    private NumberFormat FORMAT = new DecimalFormat("0000");
}
