/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.boundary;

import com.ixcode.bugsim.model.experiment.BugsimExtensionJavaBeanValueFormats;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.swing.BorderFactoryExtension;
import com.ixcode.framework.swing.JFrameExtension;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TestCase for class : BoundaryPanel
 * Created     : Jan 28, 2007 @ 2:25:00 PM by jim
 */
public class BoundaryPanelMain {

    public static void main(String[] args) {
        testPanel();
    }

    public static void testPanel() {
        BugsimExtensionJavaBeanValueFormats.registerBugsimExtensionFormats();
        JavaBeanModelAdapter modelAdapter = new JavaBeanModelAdapter();
        ParameterMapXML.initFormatter(modelAdapter.getFormatter());
        JFrameExtension f = new JFrameExtension("Test BoundaryPanel");
        ParameterMap params = new ParameterMap();
        final BoundaryStrategyBase boundaryStrategy = BoundaryStrategyFactory.createDefaultBoundaryStrategy(RectangularBoundaryStrategy.class.getName(), params, true);


        final PropertyChangeListener listener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (log.isInfoEnabled()) {
                    log.info("[strategy] PropertyChanged: " + evt.getPropertyName() + " : " + evt.getNewValue());
                }
            }
        };
        boundaryStrategy.addPropertyChangeListener(listener);

        IParameterMapLookup lookup = new IParameterMapLookup() {
            public ParameterMap getParameterMap() {
                return _parameterMap;
            }

            private ParameterMap _parameterMap = new ParameterMap();
        };
        final BoundaryPanel boundaryPanel = new BoundaryPanel("TestPanel", modelAdapter, lookup);
        boundaryPanel.setStrategyDefinition(boundaryStrategy);

//        boundaryPanel.addPropertyChangeListener(BoundaryPanel.P_STRATEGY, new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                if (log.isInfoEnabled()) {
//                    log.info("[panel] PropertyChanged: " + evt.getPropertyName() + " : " + evt.getNewValue());
//                }
//
//                BoundaryStrategyBase s = (BoundaryStrategyBase)evt.getOldValue();
//                s.removePropertyChangeListener(listener);
//                s = (BoundaryStrategyBase)evt.getNewValue();
//                s.addPropertyChangeListener(listener);
//            }
//        });


//        StrategyParameterBindingOld binding = new StrategyParameterBindingOld(boundaryPanel, "TEST_PROP");
//        binding.bind(new TestParameterContainer());


        JPanel container = new JPanel(new BorderLayout());
        container.add(boundaryPanel, BorderLayout.CENTER);
        container.setBorder(BorderFactoryExtension.createEmptyBorder(30));
        f.getContentPane().add(container);
        f.pack();
        JFrameExtension.centreWindowOnScreen(f);
        f.show();


    }

    private static class TestParameterContainer implements IParameterContainer {
        public TestParameterContainer() {
            
        }

        public void removeParameterContainerListener(IParameterContainerListener listener) {

        }

        public Parameter getParameter(String parameterName) {
            return null;
        }

        public void addPropertyChangeListener(String parameterName, PropertyChangeListener listener) {

        }

        public void fireParameterChangeEvent(String parameterName, Object oldValue, Object newValue) {

        }

        public Object getParameterValue(String parameterName) {
            return null;
        }

        public void addParameterContainerListener(IParameterContainerListener listener) {

        }

        public void fireParameterReplacedEvent(Parameter oldP, Parameter newP) {

        }

        public void setParameterValue(String parameterName, Object value) {
            if (log.isInfoEnabled()) {
                log.info("[parentStrategy] : setParameterValue(" + parameterName + ", " + value + ")");
            }
        }

        private String _name;
    }

    private static final Logger log = Logger.getLogger(BoundaryPanelMain.class);

}
