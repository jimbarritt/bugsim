package com.ixcode.framework.swing;

import java.awt.*;

public interface ViewMode {
    ViewModeName getViewModeName();

    void begin(Component parent);

    void end(Component parent);

    boolean is(ViewModeName modeName);
}
