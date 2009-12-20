/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor.landscape;

import com.ixcode.bugsim.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.ParameterGroupPanel;
import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.extent.ExtentStrategyPanel;
import com.ixcode.bugsim.view.experiment.editor.strategy.StrategyParameterBinding;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.ExtentStrategyFactory;
import com.ixcode.framework.swing.BorderFactoryExtension;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;


/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeCategoryPanel extends JPanel  implements IParameterMapLookup  {

    public LandscapeCategoryPanel(JavaBeanModelAdapter modelAdapter) {
        super(new BorderLayout());
        _modelAdapter = modelAdapter;
        initUI();
    }

    public void initUI() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));


        JPanel scaleContainer = new JPanel(new BorderLayout());
        _landscapeScale = new ParameterGroupPanel(_modelAdapter, "Scale");

        scaleContainer.setBorder(BorderFactoryExtension.createEmptyBorder(5));
        scaleContainer.add(_landscapeScale, BorderLayout.NORTH);

        _landscapeScaleEditor = _landscapeScale.addScaledDistanceParameterEditor(LandscapeCategory.class.getName(), LandscapeCategory.P_SCALE, "1 Logical unit =", 100, false, DistanceUnitRegistry.centimetres());
        _landscapeScaleEditor.setReadonly(true); // @todo need to work out what happens if we re-scale everything - kind of need to go through all figures and update them ....
        container.add(scaleContainer);

        JTabbedPane tabbedPane = new JTabbedPane();

        _extentStrategyPanel = new ExtentStrategyPanel(_modelAdapter,this, 100);

        _extentStrategyBinding = new StrategyParameterBinding("LandscapeC.Extent", _extentStrategyPanel, DefaultExtentStrategyFactory.INSTANCE);

        tabbedPane.addTab("Extent", _extentStrategyPanel);

        _releaseBoundaryPanel = new ReleaseBoundaryStrategyPanel(_modelAdapter, this, 100);

        tabbedPane.addTab("Release Boundary", _releaseBoundaryPanel);

        container.add(tabbedPane);


        super.add(container, BorderLayout.NORTH);
    }

    private static class DefaultExtentStrategyFactory implements IStrategyDefinitionFactory {
        public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
             return ExtentStrategyFactory.createExtentStrategy(className, parameterMapLookup.getParameterMap());
        }

        public static IStrategyDefinitionFactory INSTANCE = new DefaultExtentStrategyFactory();
    }

    public ParameterMap getParameterMap() {
        return _landscapeCategory != null ? _landscapeCategory.getParameterMap() : null;
    }

    public void setBugsimParameters(BugsimParameterMap bugsimParameters) {
        LandscapeCategory landscapeCategory = bugsimParameters.getLandscapeCategory();

        _extentStrategyBinding.unbind();

        _landscapeCategory = landscapeCategory;
        _landscapeScaleEditor.setValue(_modelAdapter.getPropertyValueAsString(_landscapeCategory, LandscapeCategory.P_SCALE, Locale.UK));

        _releaseBoundaryPanel.setLandscapeCategory(landscapeCategory);

        _extentStrategyBinding.bind(landscapeCategory, LandscapeCategory.P_EXTENT);    }


    private static final Logger log = Logger.getLogger(LandscapeCategoryPanel.class);
    private JavaBeanModelAdapter _modelAdapter;
    private ExtentStrategyPanel _extentStrategyPanel;
    private ParameterGroupPanel _landscapeScale;
    private LandscapeCategory _landscapeCategory;
    private IPropertyValueEditor _landscapeScaleEditor;

    private StrategyParameterBinding _extentStrategyBinding;
    private ReleaseBoundaryStrategyPanel _releaseBoundaryPanel;
}
