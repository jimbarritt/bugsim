/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.util;

import java.text.DecimalFormat;
import java.text.Format;

/**
 *  Description : Helper class to make reporting of memory usage easier
 */
public class MemoryStats {

    public MemoryStats(Runtime runtime) {
        _runtime = runtime;
    }

    public double getFreeMb() {
        return convertByteToMb(_runtime.freeMemory());
    }

    public double getMaxMb() {
        return convertByteToMb(_runtime.maxMemory());
    }

    public double getJvmMb() {
        return convertByteToMb(_runtime.totalMemory());
    }

    public double convertByteToMb(long l) {
        return (double)l / (double)(1024 * 1024);
    }

    public String getSummary() {
        return "Free: " + format(getFreeMb()) + "M of " + format(getJvmMb()) + "M (Max " + format(getMaxMb()) + "M)";
    }

    public String format(double value) {
        return DP2.format(new Double(value));
    }

    private Format DP2 = new DecimalFormat("0.00");
    private Runtime _runtime;

}
