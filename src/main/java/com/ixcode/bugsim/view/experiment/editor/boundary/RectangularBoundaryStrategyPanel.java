/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.boundary;

import com.ixcode.bugsim.model.experiment.BugsimExtensionJavaBeanValueFormats;
import com.ixcode.bugsim.view.landscape.geometry.CartesianDimensionsPropertyEditor;
import com.ixcode.bugsim.view.landscape.geometry.RectangularCoordinatePropertyEditor;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterMapXML;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.swing.BorderFactoryExtension;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
class RectangularBoundaryStrategyPanel extends BoundaryStrategyPanelBase {
    public RectangularBoundaryStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, RectangularBoundaryStrategy.class);
        initUI();
    }

    private void initUI() {
        IPropertyValueEditor boundaryBoundsField = addCartesianBoundsParameterEditor(BoundaryStrategyBase.class.getName(), BoundaryStrategyBase.P_BOUNDS, "Bounds", 100, 2);
        boundaryBoundsField.setReadonly(true);

        _locationEditor = super.addRectangularCoordinateParameterEditor(RectangularBoundaryStrategy.class.getName(), RectangularBoundaryStrategy.P_LOCATION, null, 100, 2);
        _sizeEditor = super.addCartesianDimensionsParameterEditor(RectangularBoundaryStrategy.class.getName(), RectangularBoundaryStrategy.P_DIMENSIONS, null, 100, 2);

        super.addPropertyEditorBinding(boundaryBoundsField);
        super.addPropertyEditorBinding(_locationEditor);
        super.addPropertyEditorBinding(_sizeEditor);


    }


    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);



         updateDerivedStatus();
    }

    public void updateDerivedStatus() {
        final RectangularBoundaryStrategy rbs = (RectangularBoundaryStrategy)super.getStrategyDefinition();
        if (rbs != null) {
            if (rbs.isDimensionsDerived()) {
                _sizeEditor.setReadonly(true);
            } else {
                _sizeEditor.setReadonly(false);
            }

    //        if (log.isInfoEnabled()) {
    //            log.info("Setting Strategy: " + strategyDefinition.getStrategyS().getFullyQualifiedName());
    //        }

            if (rbs.isLocationDerived()) {
                _locationEditor.setReadonly(true);
            } else {
                _locationEditor.setReadonly(false);
            }
        }
     }




    public static void main(String[] args) {
        BugsimExtensionJavaBeanValueFormats.registerBugsimJavaBeanExtensionFormats();
        JavaBeanModelAdapter modelAdapter = new JavaBeanModelAdapter();
        ParameterMapXML.initFormatter(modelAdapter.getFormatter());

        JFrameExtension f = new JFrameExtension("Test RectangularBoundaryPanel");
        ParameterMap params = new ParameterMap();
        BoundaryStrategyBase boundaryStrategy = BoundaryStrategyFactory.createDefaultBoundaryStrategy(RectangularBoundaryStrategy.class.getName(), params, true);


        RectangularBoundaryStrategyPanel boundaryPanel = new RectangularBoundaryStrategyPanel(modelAdapter);

        boundaryPanel.setStrategyDefinition(boundaryStrategy);

        JPanel container = new JPanel(new BorderLayout());
        container.add(boundaryPanel, BorderLayout.CENTER);
        container.setBorder(BorderFactoryExtension.createEmptyBorder(30));
        f.getContentPane().add(container);
        f.pack();
        JFrameExtension.centreWindowOnScreen(f);
        f.show();

    }


    private static final Logger log = Logger.getLogger(RectangularBoundaryStrategyPanel.class);
    private CartesianDimensionsPropertyEditor _sizeEditor;
    private RectangularCoordinatePropertyEditor _locationEditor;
}

