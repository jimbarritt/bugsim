/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GravitationalCalculator {


    public static final String PROPERTY_DISTANCE_CUTOFF = "distanceCutoff";

    public GravitationalCalculator(ISignalFunction signalFunction, double distanceCutoff) {
        _signalFunction = signalFunction;
        _distanceCutoff = distanceCutoff;

    }


    public RectangularCoordinate calculateNetDisplacement(List attractors, RectangularCoordinate affectedCoordinate) {
        double[][] vectorOfDisplacement = new double[attractors.size()][2];

        int i = 0;
        for (Iterator itr = attractors.iterator(); itr.hasNext();) {
            ISignalSource signalSource = (ISignalSource)itr.next();
            RectangularCoordinate displacement = calculateDisplacement(signalSource, affectedCoordinate);

            double distance = affectedCoordinate.calculateDistanceTo(displacement);
            if (distance > _distanceCutoff) {
                vectorOfDisplacement[i][0] = displacement.getChangeInX(affectedCoordinate);
                vectorOfDisplacement[i][1] = displacement.getChangeInY(affectedCoordinate);
            }


            i++;
        }

        double netChangeInX = sumVector(vectorOfDisplacement, 0);
        double netChangeInY = sumVector(vectorOfDisplacement, 1);

        double newX = affectedCoordinate.getDoubleX() + netChangeInX;
        double newY = affectedCoordinate.getDoubleY() + netChangeInY;

        return new RectangularCoordinate(newX, newY);

    }


    private double sumVector(double[][] vectorOfDisplacement, int parameterIndex) {
        double sum = 0;
        for (int i = 0; i < vectorOfDisplacement.length; ++i) {
            sum += vectorOfDisplacement[i][parameterIndex];
        }
        return sum;
    }

    /**
     * Phew! so this function is the guts of the machine - it first has to work out how far away and in what direction we are from
     * the signalSource - we can do this by calculating the displacement between the coordinates.
     * <p/>
     * Then we will have a value of "d" to put into our function of distance to gravitational force.
     * <p/>
     * Once we have the reuslt of this (happily it is in the same units as distance) we can then work out
     * where we would end up if we moved towards the signalSource by that much force.
     * <p/>
     * we could insert some function that translates between units of f and units of d but for clarity they are the same in this simulation.
     *
     * @param signalSource
     * @param location
     * @return
     */
    private RectangularCoordinate calculateDisplacement(ISignalSource signalSource, RectangularCoordinate location) {
        double potentialX = signalSource.getLocation().getDoubleX();
        double potentialY = signalSource.getLocation().getDoubleY();
        AzimuthCoordinate azimuthCoordinate = location.calculateAzimuthCoordinateTo(potentialX, potentialY);
        double f = _signalFunction.calculateSensoryInformationValue(signalSource, azimuthCoordinate.getDistance());

        return location.moveTo(new AzimuthCoordinate(azimuthCoordinate.getAzimuth(), f));


    }

    public ISignalFunction getForceFunction() {
        return _signalFunction;
    }

    public void setForceFunction(ISignalFunction signalFunction) {
        _signalFunction = signalFunction;
    }

    public double getDistanceCutoff() {
        return _distanceCutoff;
    }

    public void setDistanceCutoff(double distanceCutoff) {
        _distanceCutoff = distanceCutoff;
    }

    private ISignalFunction _signalFunction;
    private double _distanceCutoff;

}
