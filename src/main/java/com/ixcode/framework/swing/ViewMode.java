/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import com.ixcode.framework.datatype.TypeSafeEnum;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public  class ViewMode extends TypeSafeEnum {
    public static final ViewMode DISPLAY = new ViewMode("display");

   protected ViewMode(String name) {
          super(name);
    }



}
