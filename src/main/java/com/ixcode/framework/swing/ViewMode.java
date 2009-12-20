/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import java.awt.*;

public interface ViewMode {
    ViewModeName getName();

    void begin(Component parent);

    void end(Component parent);
}
