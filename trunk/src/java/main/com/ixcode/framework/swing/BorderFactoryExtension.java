/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import javax.swing.*;
import javax.swing.border.Border;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BorderFactoryExtension {

    public static Border createEmptyBorder(int size) {
        return BorderFactory.createEmptyBorder(size, size, size, size);
    }


}
