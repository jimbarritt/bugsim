/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.util.Iterator;
import java.util.List;

/**
 *  Description : Given a list of Information sources, it will calculate the current absolute value and
 */
public class FunctionalSignalSurfaceCalculator implements ISignalSurfaceCalculator {

    public FunctionalSignalSurfaceCalculator(ISignalFunction function) {
        _function = function;
    }

    public double calculateSurfaceHeight(List informationSources, RectangularCoordinate location) {
        double surfaceHieght = 0;
        for (Iterator itr = informationSources.iterator(); itr.hasNext();) {
            ISignalSource source = (ISignalSource)itr.next();
            double distance = source.getLocation().getCoordinate().calculateDistanceTo(location);
            surfaceHieght += (_function.calculateSensoryInformationValue(source, distance));
        }
        return surfaceHieght;
    }

    public double calculateSurfaceGradient(List informationSources, RectangularCoordinate location) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ISignalFunction getSignalFunction() {
        return _function;
    }

    private ISignalFunction _function;
}
