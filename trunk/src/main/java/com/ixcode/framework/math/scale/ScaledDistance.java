/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.scale;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.StringTokenizer;

/**
 * Description : Provides functionality to transform between logical and real world distances.
 */
public class ScaledDistance {

    public static ScaledDistance scaledDistanceOf(double distance, IDistanceUnit units) {
        return new ScaledDistance(distance, units);
    }

    public ScaledDistance(double distance, IDistanceUnit units) {
        _units = units;
        _distance = distance;
    }


    public IDistanceUnit getUnits() {
        return _units;
    }

    public double getDistance() {
        return _distance;
    }

    public Distance convertLogicalToScaledDistance(double value) {
        return new Distance(value * _distance, _units);
    }

    public double convertScaledToLogicalDistance(Distance value) {
        double convertedForUnits = _units.convertValue(value.getUnits(), value.getValue());
        return convertedForUnits / _distance;
    }

    public RectangularCoordinate convertScaledToLogicalCoordinate(ScaledCartesianCoordinate scaledCoordinate) {
        double logicalX = scaleCoordToLogical(scaledCoordinate.getUnits(), scaledCoordinate.getDoubleX(), _units, _distance);
        double logicalY = scaleCoordToLogical(scaledCoordinate.getUnits(), scaledCoordinate.getDoubleY(), _units, _distance);
        return new RectangularCoordinate(logicalX, logicalY);
    }

    private static double scaleCoordToLogical(IDistanceUnit valueUnits, double value, IDistanceUnit scaleUnits, double scale) {
        return scaleUnits.convertValue(valueUnits, value) * scale;
    }

    public ScaledCartesianCoordinate convertLogicalToScaledCoordinate(RectangularCoordinate coordinate) {
        double scaledX = coordinate.getDoubleX() / _distance;
        double scaledY = coordinate.getDoubleY() / _distance;
        return new ScaledCartesianCoordinate(scaledX, scaledY, _units);
    }

    public static ScaledDistance parse(String value) {
        StringTokenizer st = new StringTokenizer(value, " ");
        if (st.countTokens() != 2) {
            throw new IllegalArgumentException("Invalid value passed to ScaledValueField: '" + value);

        }
       return parse(st.nextToken(), st.nextToken());
    }

    public static ScaledDistance parse(String distanceString, String unitSymbol) {
        double distance = Double.parseDouble(distanceString);
        IDistanceUnit units = DistanceUnitRegistry.INSTANCE.resolveUnitFromSymbol(unitSymbol);
        return new ScaledDistance(distance, units);
    }

    public String toString() {
        return getDistanceAsString() + " " + getUnitsAsString();
    }

    public String getDistanceAsString() {
        return F4.format(_distance);
    }

    public String getUnitsAsString() {
        return _units.getSymbol();
    }

    public double convertToLogicalDistance(ScaledDistance scale) {
        return scale.convertScaledToLogicalDistance(new Distance(_distance, _units));
    }

    public double scaleTo(ScaledDistance scale) {
        return scale.getUnits().convertValue(_units, _distance);
    }


    private IDistanceUnit _units;

    /**
     * How many logical coordinates to 1 scaled coordinate.
     * so if scale is 100 - 100 logical coordinates is 1 unit in size.
     */
    private double _distance;

    private static final     NumberFormat F4 = new DecimalFormat("###0.00");

}
