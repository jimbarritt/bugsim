/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import java.awt.*;

/**
 * Description : used to render landscape specific properties like backgrounds or overlays (maybe for showing gradients
 */
public interface ILandscapeRenderer {

        void render(Graphics2D g, LandscapeView view);

    boolean isVisible();


    void setVisible(boolean isVisible);
}
