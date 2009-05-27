/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property.example;

import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.swing.property.JavaBeanPropertyGroupPanel;
import com.ixcode.framework.swing.property.PropertySheetBase;

import javax.swing.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExampleBeanPropertySheet_Specific extends PropertySheetBase {

    public ExampleBeanPropertySheet_Specific(JavaBeanModelAdapter modelAdapter) {
        super("Specific", modelAdapter);


        super.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JavaBeanPropertyGroupPanel group3Panel = new JavaBeanPropertyGroupPanel(modelAdapter,  ExampleBean.class, ExampleBeanInfo.GROUP_3_PROPERTIES);




        super.add(group3Panel);

    }
}
