/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;


import com.ixcode.framework.model.ModelBase;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ChartSamplingParameters extends ModelBase {

    public static final String PROPERTY_RANGE_START = "rangeStart";
    public static final String PROPERTY_RANGE_END = "rangeEnd";
    public static final String PROPERTY_FREQUENCY = "frequency";

    public ChartSamplingParameters() {

    }
    public ChartSamplingParameters(double sampleRangeStart, double sampleRangeEnd, int sampleFrequency) {
        super.addProperty(PROPERTY_RANGE_START, Double.class);
        super.addProperty(PROPERTY_RANGE_END, Double.class);
        super.addProperty(PROPERTY_FREQUENCY, Integer.class);

        setSampleRangeStart(sampleRangeStart);
        setSampleRangeEnd(sampleRangeEnd);
        setSampleFrequency(sampleFrequency);
    }


    public double getSampleRangeStart() {
        return super.getPropertyDoubleValue(PROPERTY_RANGE_START);
    }



    public void setSampleRangeStart(double sampleRangeStart) {
        super.setPropertyDouble(PROPERTY_RANGE_START, sampleRangeStart);
    }

    public double getSampleRangeEnd() {
        return super.getPropertyDoubleValue(PROPERTY_RANGE_END);
    }

    public void setSampleRangeEnd(double sampleRangeEnd) {
        super.setPropertyDouble(PROPERTY_RANGE_END, sampleRangeEnd);
    }

    public int getSampleFrequency() {
        return super.getPropertyIntegerValue(PROPERTY_FREQUENCY);
    }

    public void setSampleFrequency(int sampleFrequency) {
        super.setPropertyInteger(PROPERTY_FREQUENCY, sampleFrequency);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("ChartSamplingParameters:[");
        sb.append("rangeStart=").append(getSampleRangeStart()).append(", ");
        sb.append("rangeEnd=").append(getSampleRangeEnd()).append(", ");
        sb.append("frequency=").append(getSampleFrequency()).append(", ");
        return sb.toString();
    }


}
