/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.math.geometry;

import java.math.BigDecimal;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BorderDefinition {

    public BorderDefinition(BigDecimal top, BigDecimal bottom, BigDecimal left, BigDecimal right) {
        _top = top;
        _bottom = bottom;
        _left = left;
        _right = right;
    }


    public BigDecimal getTop() {
        return _top;
    }

    public BigDecimal getBottom() {
        return _bottom;
    }

    public BigDecimal getLeft() {
        return _left;
    }

    public BigDecimal getRight() {
        return _right;
    }


    BigDecimal _top;
    BigDecimal _bottom;
    BigDecimal _left;
    BigDecimal _right;
}
