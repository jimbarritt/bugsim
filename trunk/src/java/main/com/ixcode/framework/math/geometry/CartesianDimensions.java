/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.math.DoubleMath;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @todo make big decimalised and make Cartesian Bounds extend or aggregate this...
 */
public class CartesianDimensions {

     public CartesianDimensions(double squareDimension) {
        _doubleW = squareDimension;
        _doubleH = squareDimension;
        _isSquare = true;
    }
    public CartesianDimensions(double doubleW, double doubleH) {
        _doubleW = doubleW;
        _doubleH = doubleH;

        if (DoubleMath.precisionEquals(_doubleW, _doubleW, DoubleMath.DOUBLE_PRECISION_DELTA)) {
            _isSquare = true;
        }
    }

    public double getDoubleWidth() {
        return _doubleW;
    }

    public double getDoubleHeight() {
        return _doubleH;
    }
    public String toString() {
        return _dimensionsFormat.format(this);
    }

    public double getInnerRadius() {
        return (_isSquare) ? _doubleW / 2 : Math.sqrt((_doubleH * _doubleH) + (_doubleW * _doubleW));
    }

    private double _doubleW;
    private double _doubleH;
   CartesianDimensionsFormat _dimensionsFormat = new CartesianDimensionsFormat();

    private boolean _isSquare = false;
}
