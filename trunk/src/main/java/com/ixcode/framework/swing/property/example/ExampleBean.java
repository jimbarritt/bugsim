/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property.example;

/**
 *  Description : Used to test property functionality.
 */
public class ExampleBean {

    public ExampleBean() {

    }

    public ExampleBean(int intValue, double primitiveDoubleValue, long primitiveLongValue, Integer integerValue, Double doubleValue, Long longValue, String stringValue, ExampleTypeSafeEnum enumValue) {
        _intValue = intValue;
        _primitiveDoubleValue = primitiveDoubleValue;
        _primitiveLongValue = primitiveLongValue;
        _integerValue = integerValue;
        _doubleValue = doubleValue;
        _longValue = longValue;
        _stringValue = stringValue;
        _enumValue = enumValue;
    }

    public int getIntValue() {
        return _intValue;
    }

    public void setIntValue(int intValue) {
        _intValue = intValue;
    }

    public double getPrimitiveDoubleValue() {
        return _primitiveDoubleValue;
    }

    public void setPrimitiveDoubleValue(double primitiveDubleValue) {
        _primitiveDoubleValue = primitiveDubleValue;
    }

    public long getPrimitiveLongValue() {
        return _primitiveLongValue;
    }

    public void setPrimitiveLongValue(long primitiveLongValue) {
        _primitiveLongValue = primitiveLongValue;
    }

    public Integer getIntegerValue() {
        return _integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
        this._integerValue = integerValue;
    }

    public Double getDoubleValue() {
        return _doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        _doubleValue = doubleValue;
    }

    public Long getLongValue() {
        return _longValue;
    }

    public void setLongValue(Long longValue) {
        _longValue = longValue;
    }

    public String getStringValue() {
        return _stringValue;
    }

    public void setStringValue(String stringValue) {
        _stringValue = stringValue;
    }

    public ExampleTypeSafeEnum getEnumValue() {
        return _enumValue;
    }

    public void setEnumValue(ExampleTypeSafeEnum enumValue) {
        _enumValue = enumValue;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("intValue    = " + _intValue).append("\n");
        sb.append("primDouble  = " + _primitiveDoubleValue).append("\n");
        sb.append("integer = " + _integerValue).append("\n");
        sb.append("double = " + _doubleValue).append("\n");
        sb.append("long = " + _longValue).append("\n");
        sb.append("string = " + _stringValue).append("\n");
        sb.append("enum = " + _enumValue).append("\n");
        return sb.toString();
    }

    private int _intValue;
    private double _primitiveDoubleValue;
    private long _primitiveLongValue;
    private Integer _integerValue;
    private Double _doubleValue;
    private Long _longValue;
    private String _stringValue;
    private ExampleTypeSafeEnum _enumValue;
}
