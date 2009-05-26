/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import com.ixcode.framework.simulation.model.landscape.Location;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * TestCase for class : VisualSignalProcessor
 */
public class VisualSignalProcessorTestCase extends TestCase {



    public void testInputs() {
        IPhysicalAgent agent1 = new MockAgent(new Location(10, 10));
        IPhysicalAgent agent2 = new MockAgent(new Location(10, 15));
        IPhysicalAgent agent3 = new MockAgent(new Location(5, 5));

        List agentsInView = new ArrayList();
        agentsInView.add(agent1);
        agentsInView.add(agent2);
        agentsInView.add(agent3);

        MockViewer  thisAgent = new MockViewer(new Location(10, 5));
        VisualSignalProcessor calc = new VisualSignalProcessor(true);

//        DiscreetValueMap inputs = calc.processVisualSignalInputs(thisAgent, agentsInView, thisAgent);

//        for (int i=0;i<inputs.getKeys().length;++i) {
//            System.out.println("[" + i + "] - " + inputs.getKeys()[i] + " = " + inputs.getValues()[i]);
//        }

    }



}
