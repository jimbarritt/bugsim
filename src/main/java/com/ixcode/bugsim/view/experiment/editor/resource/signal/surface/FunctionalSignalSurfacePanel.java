/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.signal.surface;

import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.FunctionalSignalSurfaceStrategy;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.resource.signal.surface.function.SignalFunctionPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyDetailsPanel;
import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.TextFieldPropertyEditor;

import javax.swing.*;
import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 6, 2007 @ 6:29:39 PM by jim
 */
public class FunctionalSignalSurfacePanel extends StrategyDetailsPanel {

    public FunctionalSignalSurfacePanel(IModelAdapter modelAdapter, int minWidth) {
        super(modelAdapter, FunctionalSignalSurfaceStrategy.class);
        initUI(minWidth);
    }

    private void initUI(int minWidth) {
        JPanel container = new JPanel(new BorderLayout());

        ParameterGroupPanel general = new ParameterGroupPanel(super.getModelAdapter());

        IPropertyValueEditor surfaceNameE = new TextFieldPropertyEditor(FunctionalSignalSurfaceStrategy.P_SURFACE_NAME, "Surface Name", "", 30, TextAlignment.LEFT,  minWidth);
        IPropertyValueEditor includeSignalSurveyE = general.addParameterEditor(super.getStrategyClassName(), FunctionalSignalSurfaceStrategy.P_INCLUDE_SURVEY, minWidth);
        IPropertyValueEditor surveyResolutionE = general.addParameterEditor(super.getStrategyClassName(), FunctionalSignalSurfaceStrategy.P_SURVEY_RESOLUTION, minWidth);

        general.addPropertyEditor(surfaceNameE);
        general.addPropertyEditor(includeSignalSurveyE);
        general.addPropertyEditor(surveyResolutionE);

        super.addPropertyEditorBinding(surfaceNameE);
        super.addPropertyEditorBinding(includeSignalSurveyE);
        super.addPropertyEditorBinding(surveyResolutionE);


        JTabbedPane tabbedPane = new JTabbedPane();

        _signalFunctionPanel = new SignalFunctionPanel(super.getModelAdapter(), this, 130);

        container.add(general, BorderLayout.NORTH);
        tabbedPane.add("Function", _signalFunctionPanel);
        container.add(tabbedPane, BorderLayout.CENTER);

        

        super.add(container, BorderLayout.CENTER);

    }

    public void setStrategyDefinition(StrategyDefinition strategyDefinition) {
        super.setStrategyDefinition(strategyDefinition);
        FunctionalSignalSurfaceStrategy fss = (FunctionalSignalSurfaceStrategy)strategyDefinition;
        _signalFunctionPanel.setStrategyDefinition(fss.getSignalFunction());
    }

    private SignalFunctionPanel _signalFunctionPanel;
}
