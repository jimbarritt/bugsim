/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.grid;

import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeCategory;

import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class GridRenderOptions {
    public Color getDarkColor(int gridLevel, Grid grid) {
        if (grid.getName().equals(Landscape.ZERO_BOUNDARY_GRID) || grid.getName().equals(Landscape.RELEASE_GRID) ) {
            return Color.blue;
        }
        return new Color(30, 22, 0);

    }

    public Color getLightColor(Grid grid) {
        if (grid.getName().equals(Landscape.ZERO_BOUNDARY_GRID) || grid.getName().equals(Landscape.RELEASE_GRID) ) {
            return Color.blue;
        }
        return new Color(69, 157, 93);
    }

    public float getOpacity(Grid grid) {
        if (grid.getName().equals("Experimental Area")) {
            return .1f;
        } else if (grid.getName().equals("searchGrid")) {
            return .1f;
        }
        return 0.1f;
    }

    public float getStrokeThickness(int gridLevel) {
        float s;
        switch (gridLevel){
            case 0: {
                s = 1;
                break;
            }
            case 1: {
                s = 1;
                break;
            }
            case 2: {
                s = 1;
                break;
            }
            default :
                throw new IllegalStateException("We dont define this number of gridlevels! : " + gridLevel);
        }

        return s;
    }
}
