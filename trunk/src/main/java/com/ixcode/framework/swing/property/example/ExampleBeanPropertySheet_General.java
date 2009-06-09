/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property.example;

import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.swing.property.JavaBeanPropertyGroupPanel;
import com.ixcode.framework.swing.property.PropertySheetBase;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExampleBeanPropertySheet_General extends PropertySheetBase {


    public ExampleBeanPropertySheet_General(JavaBeanModelAdapter modelAdapter) {
        super("General", modelAdapter);



        JavaBeanPropertyGroupPanel group1Panel = new JavaBeanPropertyGroupPanel(modelAdapter, "Group 1", ExampleBean.class, ExampleBeanInfo.GROUP_1_PROPERTIES);
        JavaBeanPropertyGroupPanel group2Panel  = new JavaBeanPropertyGroupPanel(modelAdapter, "Group 2", ExampleBean.class, ExampleBeanInfo.GROUP_2_PROPERTIES);

        super.add(group1Panel);
        super.add(group2Panel);
    }
}
