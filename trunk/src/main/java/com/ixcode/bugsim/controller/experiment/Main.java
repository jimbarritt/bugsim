/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.OpenSimulationAction;
import com.ixcode.bugsim.view.landscape.zoomcontrol.ZoomFrame;
import com.ixcode.bugsim.view.simulation.GravityMachineControlFrame;
import com.ixcode.bugsim.view.simulation.GravityMachineControlPanel;
import com.ixcode.bugsim.view.simulation.player.ExperimentPlayerFrame;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.information.GravitationalCalculator;
import com.ixcode.framework.simulation.model.landscape.information.function.ExponentialDecaySignalFunction;
import com.ixcode.framework.swing.property.IPropertyInspectorListener;
import com.ixcode.framework.swing.property.PropertyInspector;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * @deprecated
 */
public class Main {


    public static void main(String[] args) {
        // Does something to the window decorations...
        JFrame.setDefaultLookAndFeelDecorated(true);

        GravitationalCalculator gravitationalCalculator = new GravitationalCalculator(new ExponentialDecaySignalFunction(2, 1, .004), 0.000001);
        Simulation simulation = new Simulation(gravitationalCalculator);

        OpenSimulationAction openSim = new OpenSimulationAction();

//        IExperiment experiment = new RandomWalkEdgeEffectExperiment(simulation, openSim, 1000);
         IExperiment experiment = new TestLineIntersectionExperiment(simulation, openSim);
//        simulation.setExperiment(experiment);
        LandscapeFrame landscapeZoom = experiment.createLandscapeFrame();
//        landscapeZoom.getGlassPane().setVisible(true);


        ZoomFrame landscapeOverview = new ZoomFrame(landscapeZoom.getLandscapeView());

//        landscapeOverview.setSize(160, 170);
//        landscapeOverview.setLocation(340, 100);
//        landscapeZoom.setLocation(550, 0);




        List loops = createExperimentLoops();

        ExperimentPlayerFrame player = new ExperimentPlayerFrame(experiment, loops);
        player.setSize(520, 340);
        player.setLocation(10, 440);
        landscapeZoom.setLocation(550, 0);
        player.selectLoop((IExperimentLoop)loops.get(0));


         openSim.init(landscapeZoom.getLandscapeView(), landscapeZoom, landscapeOverview);



//        RandomWalkEdgeEffectProperties parameters = (RandomWalkEdgeEffectProperties)experiment.getProperties();
//        try {
//            PropertySheetFactory.INSTANCE.registerPropertySheets(parameters.getClass(), parameters.getPropertySheetClassNames());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            System.exit(-1);
//        }
        PropertyInspector inspector = new PropertyInspector(null, new JavaBeanModelAdapter(), false);
//        landscapeOverview.show();
        inspector.setLocation(10, 0);
        inspector.setSize(520, 400);

        attatchExperimentListener(experiment, inspector);
        attatchPropertyInspectorlistener(inspector, player);

        landscapeZoom.show();

//        landscape.getSimulation().addAgent(new CabbageAgent(99999, new Location(0, 0), 10));

        GravityMachineControlFrame gravityMachineControl = new GravityMachineControlFrame(new GravityMachineControlPanel());
        player.show();
        inspector.setReadonly(false);
        inspector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        inspector.inspect(experiment.getName(), parameters);


    }

    private static List createExperimentLoops() {
        List loops = new ArrayList();
        IExperimentLoop singleLoop = new SingleExperimentLoop();
        IExperimentLoop moveLengthLoop = new RandomWalkEdgeEffectPropertiesLoop();
        moveLengthLoop.addPropertyChanger(RandomWalkEdgeEffectProperties.PARAM_BUTTERFLY_MOVE_LENGTH, new Integer(1), new Integer(30), new Integer(2));
        loops.add(moveLengthLoop);
        loops.add(singleLoop);
        return loops;
    }

    private static void attatchPropertyInspectorlistener(PropertyInspector inspector, final ExperimentPlayerFrame player) {
        IPropertyInspectorListener listener = new IPropertyInspectorListener(){
            public void appliedChanges(Object instance) {
                player.reset();
            }
        };
        inspector.addPropertyInspectorListener(listener);
    }

    private static void attatchExperimentListener(IExperiment experiment,final PropertyInspector experimentProperties) {
        IExperimentListener experimentListener = new ExperimentListenerBase() {

            public void experimentStarted(IExperiment experiment) {
                experimentProperties.setReadonly(true);
            }


            public void experimentComplete(IExperiment experiment) {
                experimentProperties.setReadonly(false);
            }

            public void experimentStopped(IExperiment experiment) {
//                experimentProperties.setReadonly(false);
            }

            public void experimentReset(IExperiment experiment) {
                experimentProperties.setReadonly(false);
            }


        };
        experiment.addExperimentListener(experimentListener);

    }

}



