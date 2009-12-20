/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report.cabbage;

import junit.framework.*;

/**
 * TestCase for class : CabbageStatisticEntry
 */
public class CabbageStatisticEntryTestCase extends TestCase {


    public void testAverageNumberOfEggs() {
        CabbageStatisticEntry entry = new CabbageStatisticEntry(null);
        entry.addEggs(10);
        entry.addEggs(10);
        entry.addEggs(10);

        assertEquals("Average", 10d, entry.getEggsPerPlant(), 0.0000);

    }
    public void testAverageNumberOfEggs_2() {
        CabbageStatisticEntry entry = new CabbageStatisticEntry(null);
        entry.addEggs(6);
        entry.addEggs(7);
        entry.addEggs(92);
        entry.addEggs(93);

        assertEquals("Average", 49.5d, entry.getEggsPerPlant(), 0.0000);

    }
}
