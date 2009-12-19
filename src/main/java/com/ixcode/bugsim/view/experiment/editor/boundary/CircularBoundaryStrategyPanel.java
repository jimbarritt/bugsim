/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.boundary;

import com.ixcode.bugsim.view.geometry.RectangularCoordinatePropertyEditor;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CircularBoundaryStrategy;
import com.ixcode.framework.swing.property.IPropertyValueEditor;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
class CircularBoundaryStrategyPanel extends BoundaryStrategyPanelBase {

    public CircularBoundaryStrategyPanel(IModelAdapter modelAdapter) {
        super(modelAdapter, CircularBoundaryStrategy.class);
        initUI();
    }

    private void initUI() {
        IPropertyValueEditor boundaryBoundsField = addCartesianBoundsParameterEditor(BoundaryStrategyBase.class.getName(), BoundaryStrategyBase.P_BOUNDS, "Bounds", 100, 2);
        boundaryBoundsField.setReadonly(true);

        _locationEditor = addRectangularCoordinateParameterEditor(CircularBoundaryStrategy.class.getName(),  CircularBoundaryStrategy.P_LOCATION, null, 100, 2);
        _radiusEditor = addParameterEditor(CircularBoundaryStrategy.class.getName(),  CircularBoundaryStrategy.P_RADIUS, 100);

         super.addPropertyEditorBinding(boundaryBoundsField);
        super.addPropertyEditorBinding(_locationEditor);
        super.addPropertyEditorBinding(_radiusEditor);
    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);

         updateDerivedStatus();

    }

    public void updateDerivedStatus() {
        CircularBoundaryStrategy circularBoundaryStrategy = (CircularBoundaryStrategy)super.getStrategyDefinition();

        if (circularBoundaryStrategy != null) {
            if (circularBoundaryStrategy.isLocationDerived()) {
                _locationEditor.setReadonly(true);
            } else {
                _locationEditor.setReadonly(false);
            }


            if (circularBoundaryStrategy.isRadiusDerived()) {
                _radiusEditor.setReadonly(true);
            } else {
                _radiusEditor.setReadonly(false);
            }
        }
    }


    private RectangularCoordinatePropertyEditor _locationEditor;
    private IPropertyValueEditor _radiusEditor;
}
