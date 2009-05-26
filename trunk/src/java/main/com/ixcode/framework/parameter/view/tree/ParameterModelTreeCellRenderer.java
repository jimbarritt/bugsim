/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.view.tree;

import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.parameter.model.Parameter;
import com.sun.javadoc.*;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 *  Description : renders our model
 */
public class ParameterModelTreeCellRenderer extends DefaultTreeCellRenderer {


    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label =  (JLabel)super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        IParameterModel parameterModel = (IParameterModel)value;
        Object pvalue = null;
        if (parameterModel instanceof Parameter) {
            pvalue = ((Parameter)parameterModel).getValue();
        }

        String psummary = (pvalue == null) ? "" : " (" + pvalue + ")";
        label.setText(parameterModel.getName() + psummary);

        setIcon(label, value);
        return label;
    }


    public void setIcon(JLabel label, Object value) {

        if (value instanceof ParameterMap) {
            label.setIcon(I_PARAMETER_MAP);
        } else if (value instanceof Category) {
            label.setIcon(I_CATEGORY);
        } else if (value instanceof StrategyDefinitionParameter) {
            label.setIcon(I_STRATEGY);
        } else if (value instanceof Parameter) {
            Parameter p = (Parameter)value;
            if (p.containsStrategy()) {
                label.setIcon(I_STRATEGY_CONTAINER);
            } else {
                label.setIcon(I_PARAMETER);
            }
        }

    }
     public static ImageIcon createImageIcon(String iconResourcePath) {
        URL url = ImageIcon.class.getResource(iconResourcePath);
        if (url == null) {
            throw new RuntimeException("Could not load icon from classpath: " + iconResourcePath);
        }

        return new ImageIcon(url);
    }
    private static final Logger log = Logger.getLogger(ParameterModelTreeCellRenderer.class);
    private static final Icon I_PARAMETER_MAP = createImageIcon("/icons/parameterMap.png");
    private static final Icon I_CATEGORY = createImageIcon("/icons/category.gif");
    private static final Icon I_PARAMETER = createImageIcon("/icons/parameter.png");
    private static final Icon I_STRATEGY = createImageIcon("/icons/category.gif");
    private static final Icon I_STRATEGY_CONTAINER = createImageIcon("/icons/category.gif");

}
