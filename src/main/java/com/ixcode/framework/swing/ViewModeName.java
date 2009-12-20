package com.ixcode.framework.swing;

import com.ixcode.framework.datatype.*;

public class ViewModeName extends TypeSafeEnum {
    public static ViewModeName DISPLAY = new ViewModeName("display");
    
    protected ViewModeName(String name) {
        super(name);
    }

}
