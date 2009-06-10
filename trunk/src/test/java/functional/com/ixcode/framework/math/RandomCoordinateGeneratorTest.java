package com.ixcode.framework.math;

import com.ixcode.framework.math.geometry.*;
import org.junit.*;import static org.junit.Assert.fail;

import java.util.*;

public class RandomCoordinateGeneratorTest {

    private static final Random RANDOM = new Random();

    @Test
    public void generatesRandomCoordOnPerimeter() {
        CartesianBounds b = new CartesianBounds(0, 0, 10000, 10000);
        boolean isCircular = true;
        final int MAX_COORDS = 100;
        List coords = new ArrayList();

        for (int iCoord = 0; iCoord < MAX_COORDS; ++iCoord) {
            RectangularCoordinate coord = Geometry.generateRandomCoordOnPerimeter(RANDOM, b, isCircular);
            coords.add(coord);
            if (!Geometry.isPointInCircleDouble(coord.getDoubleX(), coord.getDoubleY(), b.getDoubleCentreX(), b.getDoubleCentreY(), b.getRadiusOfInnerCircle())) {
                fail("Coordinate: " + coord + " is NOT in circle!");
            }

            System.out.println("Coord: " + coord);

        }


    }

}
