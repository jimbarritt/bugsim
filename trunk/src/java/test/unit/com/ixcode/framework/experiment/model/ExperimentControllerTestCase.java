/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import junit.framework.TestCase;

import java.text.Format;
import java.text.SimpleDateFormat;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.experiment.model.ExperimentController;

/**
 * TestCase for class : Experiment
 */
public class ExperimentControllerTestCase extends TestCase {


    /**
     * Launches a simple gui through which you can test the control of the experiment.
     */
    public void testControl() throws InterruptedException {
        TestExperiment experiment = new TestExperiment();
        ExperimentController experimentController = new ExperimentController(experiment, MultipleProcessController.SINGLE_PROCESS);
        experimentController.setOutputResults(true);
        experimentController.reset();

        final ExperimentTestFrame f = new ExperimentTestFrame(experimentController);

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
