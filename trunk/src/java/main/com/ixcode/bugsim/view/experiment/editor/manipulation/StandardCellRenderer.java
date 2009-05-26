/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import org.apache.log4j.Logger;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 9, 2007 @ 3:34:58 PM by jim
 */
public class StandardCellRenderer extends DefaultTableCellRenderer {

    public StandardCellRenderer() {
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel("" + value);
        if (log.isInfoEnabled()) {
            log.info("getCellRenderer: isSelected=" + isSelected + " : vale=" + value);
        }
//        if (isSelected) {
//            label.setForeground(Color.white);
//            label.setBackground(Color.blue);
//        } else {
//            label.setForeground(Color.black);
//            label.setBackground(Color.white);
//        }
        return label;
    }

    private static final Logger log = Logger.getLogger(StandardCellRenderer.class);
}
