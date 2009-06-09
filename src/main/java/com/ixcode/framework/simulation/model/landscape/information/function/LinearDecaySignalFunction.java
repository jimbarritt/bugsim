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
public class LinearDecaySignalFunction extends FunctionBase implements ISignalFunction {

    public static final String NAME = "Linear Decay";
    public static final String PROPERTY_MAX = "max";
    public static final String PROPERTY_INTERCEPTOR = "intercept";
    public static final String PROPERTY_MULTIPLIER = "multiplier";

    public LinearDecaySignalFunction() {
        this(0, 0);

    }
    public LinearDecaySignalFunction(double interceptor, double multiplier) {
        super(NAME);
        super.addProperty(PROPERTY_MAX, Double.class);
        super.addProperty(PROPERTY_INTERCEPTOR, Double.class);
        super.addProperty(PROPERTY_MULTIPLIER, Double.class);
        super.setPropertyDouble(PROPERTY_MAX, 100);
        super.setPropertyDouble(PROPERTY_INTERCEPTOR, interceptor);
        super.setPropertyDouble(PROPERTY_MULTIPLIER, multiplier);
    }

    public double calculateSensoryInformationValue(ISignalSource signalSource, double distanceToAttractor) {
        return getMax() - (distanceToAttractor * getMultiplier() + getInterceptor());
    }

    public double getInterceptor() {
        return super.getPropertyDoubleValue(PROPERTY_INTERCEPTOR);
    }

    public void setInterceptor(double interceptor) {
        super.setPropertyDouble(PROPERTY_INTERCEPTOR, interceptor);
    }

    public double getMultiplier() {
        return super.getPropertyDoubleValue(PROPERTY_MULTIPLIER);
    }

    public void setMultiplier(double multiplier) {
        super.setPropertyDouble(PROPERTY_MULTIPLIER, multiplier);
    }

    public double getMax() {
        return super.getPropertyDoubleValue(PROPERTY_MAX);
    }

    public void setMax(double max) {
        super.setPropertyDouble(PROPERTY_MAX, max);
    }


}
