/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import java.awt.*;

/**
 * Description : used to render landscape specific properties like backgrounds or overlays (maybe for showing gradients
 */
public interface LandscapeLayer {

        void render(LandscapeView view, Graphics2D graphics2D);

    boolean isVisible();


    void setVisible(boolean isVisible);
}
