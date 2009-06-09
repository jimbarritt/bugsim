/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.config;

import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridBeanInfo;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.swing.property.JavaBeanPropertyGroupPanel;
import com.ixcode.framework.swing.property.PropertySheetBase;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GridPropertySheet_General extends PropertySheetBase {


    public GridPropertySheet_General(JavaBeanModelAdapter modelAdapter) {
        super("General", modelAdapter);



        JavaBeanPropertyGroupPanel group1Panel = new JavaBeanPropertyGroupPanel(modelAdapter, "Grid", Grid.class, GridBeanInfo.GRID_GROUP_PROPERTIES);
        JavaBeanPropertyGroupPanel group2Panel  = new JavaBeanPropertyGroupPanel(modelAdapter, "Landscape", Grid.class, GridBeanInfo.LANDSCAPE_GROUP_PROPERTIES);

        super.add(group1Panel);
        super.add(group2Panel);
    }
}
