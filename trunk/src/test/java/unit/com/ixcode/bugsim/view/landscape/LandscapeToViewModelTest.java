package com.ixcode.bugsim.view.landscape;

import static com.ixcode.framework.math.scale.DistanceUnitRegistry.*;
import static com.ixcode.framework.math.scale.ScaledDistance.*;
import com.ixcode.framework.simulation.model.landscape.*;
import org.junit.*;

public class LandscapeToViewModelTest {

    @Test
    public void convertsScreenToLogicalCoordinates() {
        Landscape landscape = new Landscape(scaledDistanceOf(10, metres()));
        landscape.setScale(scaledDistanceOf(1, centimetres()));

        LandscapeToViewModel model = new LandscapeToViewModel(landscape);

    }
}
