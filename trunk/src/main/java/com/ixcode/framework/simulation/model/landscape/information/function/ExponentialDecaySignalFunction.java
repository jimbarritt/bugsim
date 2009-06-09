/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.information.function;

import com.ixcode.framework.math.function.FunctionBase;
import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSource;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExponentialDecaySignalFunction extends FunctionBase implements ISignalFunction {

    public static final String NAME = "Exponential Decay";
    public static final String PROPERTY_MAX_FORCE = "maxForce";
    public static final String PROPERTY_GRAVITATIONAL_CONSTANT = "gravitationalConstant";
    public static final String PROPERTY_SCALE = "scale";


    public ExponentialDecaySignalFunction() {
        this(0, 0, 0);

    }

    public ExponentialDecaySignalFunction(double gravitationalConstant, double scale) {
        this(gravitationalConstant, scale, Double.MAX_VALUE);
    }

    public ExponentialDecaySignalFunction(double gravitationalConstant, double scale, double maxForce) {
        super(NAME);
        super.addProperty(PROPERTY_MAX_FORCE, Double.class);
        super.addProperty(PROPERTY_GRAVITATIONAL_CONSTANT, Double.class);
        super.addProperty(PROPERTY_SCALE, Double.class);

        setGravitationalConstant(gravitationalConstant);
        setScale(scale);
        setMaxForce(maxForce);
    }

    /**
     * Not sure how mass should affect it - if we simply multiply scale by mass its too big an effect
     *
     * @param signalSource
     * @param distanceToAttractor
     * @return result
     */
    public double calculateSensoryInformationValue(ISignalSource signalSource, double distanceToAttractor) {
        double result = (1 / Math.pow(distanceToAttractor, getGravitationalConstant())) * getScale();

        if (result > getMaxForce()) {
            result = getMaxForce();
        }
        return result;
    }

    public double getGravitationalConstant() {
        return super.getPropertyDoubleValue(PROPERTY_GRAVITATIONAL_CONSTANT);
    }

    public void setGravitationalConstant(double gravitationalConstant) {
        super.setPropertyDouble(PROPERTY_GRAVITATIONAL_CONSTANT, gravitationalConstant);
    }

    public double getScale() {
        return super.getPropertyDoubleValue(PROPERTY_SCALE);
    }

    public void setScale(double scale) {
        super.setPropertyDouble(PROPERTY_SCALE, scale);
    }

    public double getMaxForce() {
        return super.getPropertyDoubleValue(PROPERTY_MAX_FORCE);
    }

    public void setMaxForce(double maxForce) {
        super.setPropertyDouble(PROPERTY_MAX_FORCE, maxForce);
    }


}
