/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import com.ixcode.framework.parameter.model.*;
import junit.framework.*;

import java.text.*;

/**
 * @FunctionalTest for class : Experiment
 */
public class ExperimentControllerTestCase extends TestCase {


    /**
     * Launches a simple gui through which you can test the control of the experiment.
     */
    public void disabledTestControl() throws InterruptedException {
        FakeExperiment experiment = new FakeExperiment();
        ExperimentController experimentController = new ExperimentController(experiment, MultipleProcessController.SINGLE_PROCESS);
        experimentController.setOutputResults(true);
        experimentController.reset();

        final TestExperimentControllerFrame f = new TestExperimentControllerFrame(experimentController);

        Thread t = new Thread(new Runnable() {
            public void run() {
                f.pack();
                f.show();
            }
        }, "Test Experiment Thread");


        t.start();

        Thread.currentThread().sleep(1000);
        while (f.isVisible()) {
            Thread.currentThread().sleep(1000);
        }

    }

    public void testFormatTime() {
        System.out.println(TIME_FORMAT.format(new Long(0)));
        System.out.println(TIME_FORMAT.format(new Long(1)));
        System.out.println(TIME_FORMAT.format(new Long(TWELVE_H + 1000)));
        System.out.println(TIME_FORMAT.format(new Long(10000)));
        System.out.println(TIME_FORMAT.format(new Long(43200000)));
    }


    public void testAlgortihmParamToString() {
        StrategyDefinitionParameter algortihm = new StrategyDefinitionParameter("algorithm", "some.class");
        Parameter p = new Parameter ("someAlgortihm", algortihm);

        System.out.println(p.toString());

        System.out.println("fqName: " + algortihm.getFullyQualifiedName());
        System.out.println("fqName: " + ((Parameter)algortihm).getFullyQualifiedName());
    }


      private static Format TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static long TWELVE_H = 43200000;
}
