/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.bugsim.model.experiment.BugsimExtensionJavaBeanValueFormats;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterMapDebug;
import com.ixcode.framework.parameter.model.ParameterMapFactory;
import com.ixcode.framework.math.geometry.*;
import junit.framework.TestCase;

import java.net.URL;

/**
 * @FunctionalTest for class : ExperimentPlanXML
 *
 * @todo this needs to get split up and moved into Bugsim cos we are using the CabbageInitialisationParameter classes
 */
public class ExperimentPlanXMLTestCase extends TestCase {


    protected void setUp() throws Exception {
        FakePlanTemplate.registerTemplate();
        BugsimExtensionJavaBeanValueFormats.registerBugsimExtensionFormats();
    }

    public void testLoad() throws Exception {
//        ExperimentPlan template = new ExperimentPlan();


        URL testFileURL = Thread.currentThread().getContextClassLoader().getResource("test/test-plan-import.xml");
        if (testFileURL == null) {
            throw new IllegalStateException("Could not find resource 'test/test-plan-import.xml'");
        }

        ExperimentPlan actual = ExperimentPlanXML.INSTANCE.importPlan(testFileURL);
        assertNotNull("Should have loaded a plan!", actual);

        ParameterMap copy = new ParameterMapFactory().createFromTemplate(actual.getParameterTemplate());
        ParameterMapDebug.debugParams(copy);

//        assertEquals("name", "testPlan", actual.getName());
//        assertEquals("trialName", "testTrial", actual.getTrialName());
//        assertEquals("templateNaPme", "test-template", actual.getTemplate().getTemplateName());
//        assertEquals("templateName", "test-template", actual.getTemplateName());
    }

    public void testNestedStrategies() throws Exception {
        URL testFileURL = Thread.currentThread().getContextClassLoader().getResource("test/test-nested-strategies.xml");
        if (testFileURL == null) {
            throw new IllegalStateException("Could not find resource 'test/test-nested-strategies.xml'");
        }
        ExperimentPlan actual = ExperimentPlanXML.INSTANCE.importPlan(testFileURL);
        assertNotNull("Should have loaded a plan!", actual);

    }
}
