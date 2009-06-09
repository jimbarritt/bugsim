/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.util;

import junit.framework.TestCase;

/**
 * TestCase for class : MemoryStats
 */
public class MemoryStatsTestCase extends TestCase {


    public void testMemoryStats() {
        MemoryStats stats = new MemoryStats(Runtime.getRuntime());

        System.out.println("From Runtime:");
        Runtime r = Runtime.getRuntime();
        System.out.println("free: " + r.freeMemory());
        System.out.println("jvm: " + r.totalMemory());
        System.out.println("max: " + r.maxMemory());

        System.out.println("From stats:");
        System.out.println("free: " + stats.getFreeMb() + "MB");
        System.out.println("jvm: " + stats.getJvmMb() + "MB");
        System.out.println("max: " + stats.getMaxMb() +"MB");
    }
}
