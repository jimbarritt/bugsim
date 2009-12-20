/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.landscape;

import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CircularBoundaryStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;
import com.ixcode.framework.parameter.model.DerivedParameter;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;

import com.ixcode.bugsim.view.experiment.editor.boundary.BoundaryPanel;
import com.ixcode.bugsim.view.experiment.editor.boundary.DefaultBoundaryStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;

import javax.swing.*;
import java.awt.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 6, 2007 @ 11:18:12 PM by jim
 */
public class ReleaseBoundaryStrategyPanel extends JPanel {
    public ReleaseBoundaryStrategyPanel(JavaBeanModelAdapter modelAdapter, IParameterMapLookup lookup, int minWidth) {
        super(new BorderLayout());
        _modelAdapter = modelAdapter;
        _lookup = lookup;
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        _general = new ParameterGroupPanel(_modelAdapter);

        IPropertyValueEditor releaseDistanceE = _general.addParameterEditor(LandscapeCategory.class.getName(), LandscapeCategory.P_RELEASE_DISTANCE, minWidth);
        _general.addPropertyEditorBinding(releaseDistanceE);


        JTabbedPane tabbedPane = new JTabbedPane();


        _boundaryPanel = new BoundaryPanel("ReleaseBoundaryPanel", _modelAdapter, _lookup);

        _derivedLocationE = new ReadOnlyPropertyEditor("DerivedLocationE", "Location from", minWidth);
        _derivedDimensionE = new ReadOnlyPropertyEditor("DerivedDimensionE", "Dimension from", minWidth);



        JPanel derivedPanel= new JPanel();
        derivedPanel.setLayout(new BoxLayout(derivedPanel, BoxLayout.PAGE_AXIS));
        derivedPanel.add(_derivedDimensionE);
        derivedPanel.add(_derivedLocationE);
        
        _boundaryPanel.getLayoutContainer().add(derivedPanel);

        _boundaryPanelBinding = new StrategyParameterBinding("ReleaseBoundary", _boundaryPanel, DefaultBoundaryStrategyDefinitionFactory.INSTANCE);
        tabbedPane.addTab("Outer Boundary", _boundaryPanel);

        super.add(_general, BorderLayout.NORTH);
        super.add(tabbedPane, BorderLayout.CENTER);
    }

    public void setLandscapeCategory(LandscapeCategory landscapeC) {
        _general.setModel(landscapeC);
        _boundaryPanelBinding.bind(landscapeC, LandscapeCategory.P_RELEASE_BOUNDARY);
        _derivedDimensionE.setVisible(false);
        if (landscapeC.getReleaseBoundary() instanceof CircularBoundaryStrategy) {
            CircularBoundaryStrategy circ = (CircularBoundaryStrategy)landscapeC.getReleaseBoundary();
            if (circ.isRadiusDerived()) {
                _derivedDimensionE.setLabel("Radius From");
                _derivedDimensionE.setVisible(true);
                _derivedDimensionE.setValue(((DerivedParameter)circ.getRadiusP()).getSourceParameters().getFirstParameter().getFullyQualifiedName());
            }
        } else if (landscapeC.getReleaseBoundary() instanceof RectangularBoundaryStrategy) {
            RectangularBoundaryStrategy rect = (RectangularBoundaryStrategy)landscapeC.getReleaseBoundary();
            if (rect.getDimensionsP().isDerived()) {
                _derivedDimensionE.setLabel("Dimension From");
                _derivedDimensionE.setVisible(true);
                _derivedDimensionE.setValue(((DerivedParameter)rect.getDimensionsP()).getSourceParameters().getFirstParameter().getFullyQualifiedName());
            }
        }

        if (landscapeC.getReleaseBoundary().getLocationP().isDerived()) {
            String name = ((DerivedParameter)landscapeC.getReleaseBoundary().getLocationP()).getSourceParameters().getFirstParameter().getFullyQualifiedName();
            _derivedLocationE.setVisible(true);
            _derivedLocationE.setValue(name);
        } else {
            _derivedLocationE.setVisible(false);
        }
    }

    private JavaBeanModelAdapter _modelAdapter;
    private IParameterMapLookup _lookup;
    private ParameterGroupPanel _general;
    private BoundaryPanel _boundaryPanel;
    private StrategyParameterBinding _boundaryPanelBinding;
    private ReadOnlyPropertyEditor _derivedDimensionE;
    private ReadOnlyPropertyEditor _derivedLocationE;
}
