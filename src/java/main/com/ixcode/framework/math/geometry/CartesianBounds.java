/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import com.ixcode.framework.math.BigDecimalMath;
import com.ixcode.framework.math.DoubleMath;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Description : Describes a bounded shape - it is always in terms of rectangular coordinates but the shape it contains may not be rectangular
 * @todo consider implementing a CircularCartesianBounds which extends this. Maybe rectangular aswell. either that or have some specific SHAPE objects
 * @todo change it all back to doubles for all calculations.
 */
public class CartesianBounds {

    public CartesianBounds(RectangularCoordinate location, CartesianDimensions dimensions) {
        double x, y, w,  h;
        x = location.getDoubleX();
        y = location.getDoubleY();
        w = dimensions.getDoubleWidth();
        h = dimensions.getDoubleHeight();

        _doubleX = x;
        _doubleY = y;
        _doubleW = w;
        _doubleH = h;
        _x = BigDecimalMath.accurateOut(x);
        _y = BigDecimalMath.accurateOut(y);
        _w = BigDecimalMath.accurateOut(w);
        _h = BigDecimalMath.accurateOut(h);

        _doublePerimeterLength = 2 * (_doubleW + _doubleH);
        _perimeterLength = _x.add(_y).multiply(BigDecimalMath.accurateIn(2));

        _location = location;
        _dimensions = dimensions;
    }

    public CartesianBounds(double x, double y, double w, double h) {
        _doubleX = x;
        _doubleY = y;
        _doubleW = w;
        _doubleH = h;
        _x = BigDecimalMath.accurateOut(x);
        _y = BigDecimalMath.accurateOut(y);
        _w = BigDecimalMath.accurateOut(w);
        _h = BigDecimalMath.accurateOut(h);

        _doublePerimeterLength = 2 * (_doubleW + _doubleH);
        _perimeterLength = _x.add(_y).multiply(BigDecimalMath.accurateIn(2));

        _location = new RectangularCoordinate(_doubleX, _doubleY);
        _dimensions = new CartesianDimensions(_doubleW, _doubleH);
    }

    public CartesianBounds(BigDecimal x, BigDecimal y, BigDecimal w, BigDecimal h) {

        _x = BigDecimalMath.accurateOut(x);
        _y = BigDecimalMath.accurateOut(y);
        _w = BigDecimalMath.accurateOut(w);
        _h = BigDecimalMath.accurateOut(h);

        _doubleX = _x.doubleValue();
        _doubleY = _y.doubleValue();
        _doubleW = _w.doubleValue();
        _doubleH = _h.doubleValue();


        _doublePerimeterLength = 2 * (_doubleW + _doubleH);
        _perimeterLength = _x.add(_y).multiply(BigDecimalMath.accurateIn(2));

        _location = new RectangularCoordinate(_doubleX, _doubleY);
        _dimensions = new CartesianDimensions(_doubleW, _doubleH);


    }

    /**
     * @return
     * 
     */
    public double getDoubleX() {
        return _doubleX;
    }

    /**
     * @return
     *
     */
    public double getDoubleY() {
        return _doubleY;
    }

    /**
     * @return     
     */
    public double getDoubleWidth() {
        return _doubleW;
    }

    /**
     * @return
     *
     */
    public double getDoubleHeight() {
        return _doubleH;
    }

    /**
     * @return
     *
     */
    public double getDoublePerimeterLength() {
        return _doublePerimeterLength;
    }

    public boolean isInside(RectangularCoordinate coordinate) {
        return Geometry.isPointInRectangle(coordinate.getDoubleX(), coordinate.getDoubleY(), _doubleX, _doubleY, _doubleW, _doubleH);
    }

    public boolean isOutside(RectangularCoordinate coordinate) {
        return !Geometry.isPointInRectangle(coordinate.getDoubleX(), coordinate.getDoubleY(), _doubleX, _doubleY, _doubleW, _doubleH);
    }

    public double getRadiusOfEnclosingCircle() {
        return Geometry.radiusOfEnclosingCircleDouble(getDoubleCentreX(), getDoubleCentreY(), _doubleX, _doubleY);
    }

    public BigDecimal getCentreY() {
        return _y.add(BigDecimalMath.divide(_h, 2));
    }

    public BigDecimal getCentreX() {
        return _x.add(BigDecimalMath.divide(_w, 2));
    }




    public String toString() {

            return _format.format(this);
        
    }

    /**
     * Creates a bounds centered inside this one with a border of a certain distance...
     * @param distance
     * @return
     */
    public CartesianBounds calculateInnerBounds(double distance) {
        double x = _doubleX + distance;
        double y = _doubleY + distance;
        double w = _doubleW - (distance*2);
        double h = _doubleH - (distance * 2);
        return new CartesianBounds(x, y, w, h);
    }

    public boolean containsPoint(RectangularCoordinate coordinate) {
        return  Geometry.isPointInRectangle(coordinate.getDoubleX(), coordinate.getDoubleY(), _doubleX, _doubleY, _doubleW, _doubleH);

    }

    private CartesianBoundsFormat _format = new CartesianBoundsFormat();

    public CartesianBounds centre(CartesianBounds enclosing) {
        BigDecimal x = enclosing.getCentreX().subtract(getHalfWidth());
        BigDecimal y = enclosing.getCentreY().subtract(getHalfHeight());


        return new CartesianBounds(x, y, getWidth(), getHeight());
    }

    public RectangularCoordinate getCentre() {
        return new RectangularCoordinate(getDoubleCentreX(), getDoubleCentreY());
    }

    public BigDecimal getX() {
        return _x;
    }

    public BigDecimal getY() {
        return _y;
    }

    public BigDecimal getWidth() {
        return _w;
    }

    public BigDecimal getHeight() {
        return _h;
    }

    public BigDecimal getPerimeterLength() {
        return _perimeterLength;
    }

    public BigDecimal getHalfWidth() {
        return BigDecimalMath.divide(_w, 2);
    }

    public BigDecimal getHalfHeight() {
        return BigDecimalMath.divide(_h, 2);
    }

    public RectangularCoordinate getOrigin() {
        return new RectangularCoordinate(_doubleX, _doubleY);
    }

    /**
     * Return the coordinate relative to the global coordinate system
     * @param coordinate
     * @return
     */
    public RectangularCoordinate convertLocalToGlobalCoord(RectangularCoordinate coordinate) {
        return new RectangularCoordinate(coordinate.getDoubleX()+ _doubleX, coordinate.getDoubleY()+_doubleY);
    }

    /**
     * Return the coordinate relative to inside this bounds
     * @param coordinate
     * @return
     */
    public RectangularCoordinate convertGlobalToLocalCoord(RectangularCoordinate coordinate) {
        return new RectangularCoordinate(coordinate.getDoubleX()-_doubleX, coordinate.getDoubleY()- _doubleY);
    }

    public double getDoubleCentreX() {
        return _doubleX + (_doubleW / 2);
    }

    public double getDoubleCentreY() {
        return _doubleY + (_doubleH / 2);
    }

    public double getRadiusOfInnerCircle() {
        return _doubleH / 2;
    }

    public boolean isOnEdge(RectangularCoordinate coord) {
        return DoubleMath.precisionEquals(coord.getDoubleX(), _doubleX, DoubleMath.DOUBLE_PRECISION_DELTA) || DoubleMath.precisionEquals(coord.getDoubleY(), _doubleY, DoubleMath.DOUBLE_PRECISION_DELTA);
    }

    public CartesianDimensions getDimensions() {
        return _dimensions;
    }


    public RectangularCoordinate getLocation() {
        return _location;
    }

    public double getDoubleHalfWidth() {
        return _doubleW/2;
    }



    private double _doubleX;
    private double _doubleY;
    private double _doubleW;
    private double _doubleH;

    private double _doublePerimeterLength;
    private static final DecimalFormat D2 = DoubleMath.DECIMAL_FORMAT;
    private BigDecimal _x;
    private BigDecimal _y;
    private BigDecimal _w;
    private BigDecimal _h;
    private BigDecimal _perimeterLength;

    CartesianDimensions _dimensions;
    RectangularCoordinate _location;

    
}
