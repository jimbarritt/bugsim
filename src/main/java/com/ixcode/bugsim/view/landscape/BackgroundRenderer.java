package com.ixcode.bugsim.view.landscape;

import java.awt.*;

public class BackgroundRenderer {

    private void paintBackground(Graphics2D graphics2D, Color backgroundColor) {
        graphics2D.setPaint(backgroundColor);
        graphics2D.fill(graphics2D.getClipBounds());
    }

    public void render(Graphics2D graphics2D, Color backgroundColor) {
        paintBackground(graphics2D, backgroundColor);
    }
}
