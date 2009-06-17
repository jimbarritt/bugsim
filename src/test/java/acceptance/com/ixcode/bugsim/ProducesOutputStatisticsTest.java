package com.ixcode.bugsim;

import com.ixcode.bugsim.fixture.*;
import static org.junit.Assert.*;
import org.junit.*;

import java.io.*;
import java.net.*;

@AcceptanceTest
public class ProducesOutputStatisticsTest {

    @Test
    public void producesSomeCsvFiles() {
        BugsimApplication bugsimApplication = new BugsimApplication();

        URL experimentPlanUrl = this.getClass().getResource("simpleExperimentPlan.xml");
        bugsimApplication.executeExperimentPlan(experimentPlanUrl);

        File[] outputFiles = bugsimApplication.listOutputFiles();

        assertNotNull(outputFiles);        
    }




}
