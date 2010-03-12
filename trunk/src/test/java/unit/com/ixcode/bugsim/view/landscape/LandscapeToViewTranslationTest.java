package com.ixcode.bugsim.view.landscape;

import static com.ixcode.framework.math.scale.DistanceUnitRegistry.*;
import static com.ixcode.framework.math.scale.ScaledDistance.*;
import com.ixcode.framework.simulation.model.landscape.*;
import static org.junit.Assert.*;
import org.junit.*;

import java.awt.*;

/**
 * Remember that the screen in 2D graphics has origin at the top left (0,0) We want the origin of the landscape to be
 * bottom right, which would be (0, viewHeight)
 */
public class LandscapeToViewTranslationTest {
    private Landscape landscape;
    private LandscapeToViewTranslation translation;

    private static final int SCREEN_EXTENT = 810;
    private static final double EXPECTED_PRECISION = 0.01d;

    @Before
    public void setup() {
        landscape = new Landscape(scaledDistanceOf(10, metres()));
        landscape.setScale(scaledDistanceOf(1, centimetres()));

        translation = new LandscapeToViewTranslation(landscape);
        translation.scaleToFitInView(new Rectangle(new Dimension(SCREEN_EXTENT, SCREEN_EXTENT)));
    }

    @Test
    public void bottomLeftCorner() {
        Location landscapeLocation = translation.landscapeLocationFromViewPoint(new Point(0, SCREEN_EXTENT));

        assertEquals(0d, landscapeLocation.getDoubleX(), EXPECTED_PRECISION);
        assertEquals(0d, landscapeLocation.getDoubleY(), EXPECTED_PRECISION);
    }

    @Test
    public void bottomRightCorner() {
        Location landscapeLocation = translation.landscapeLocationFromViewPoint(new Point(SCREEN_EXTENT, SCREEN_EXTENT));

        assertEquals(1000d, landscapeLocation.getDoubleX(), EXPECTED_PRECISION);
        assertEquals(0d, landscapeLocation.getDoubleY(), EXPECTED_PRECISION);
    }

    @Test
    public void topRightCorner() {
        Location landscapeLocation = translation.landscapeLocationFromViewPoint(new Point(SCREEN_EXTENT, 0));

        assertEquals(1000d, landscapeLocation.getDoubleX(), EXPECTED_PRECISION);
        assertEquals(1000d, landscapeLocation.getDoubleY(), EXPECTED_PRECISION);
    }

    @Test
    public void topLeftCorner() {
        Location landscapeLocation = translation.landscapeLocationFromViewPoint(new Point(0, 0));

        assertEquals(0d, landscapeLocation.getDoubleX(), EXPECTED_PRECISION);
        assertEquals(1000d, landscapeLocation.getDoubleY(), EXPECTED_PRECISION);
    }

}
