/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.strategy;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyRegistry;
import com.ixcode.framework.parameter.model.StrategyRegistryEntry;
import com.ixcode.framework.swing.CardLayoutPanel;
import com.ixcode.framework.swing.property.EnumerationComboBox;
import com.ixcode.framework.swing.property.EnumerationPropertyEditor;
import com.ixcode.framework.swing.property.PropertyBinding;
import org.apache.log4j.Logger;
import org.w3c.dom.html.HTMLSelectElement;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

/**
 * Description : Used to display a strategy
 * You can provide various implementations of the strategy and these will be presented in a card layout with a combo box at the top to select them.
 * Created     : Jan 28, 2007 @ 2:29:02 PM by jim
 */
public abstract class StrategyPanel extends JPanel {



    public StrategyPanel(String name , IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, StrategyRegistry registry, String strategyTypeLabel, int minWidth) {
        this(name, modelAdapter, parameterMapLookup, registry,  strategyTypeLabel, minWidth, null);
    }
    public StrategyPanel(String name, IModelAdapter modelAdapter, IParameterMapLookup parameterMapLookup, StrategyRegistry registry, String strategyTypeLabel, int minWidth, JComponent extraComponentForCombo) {
        super(new BorderLayout());
        _modelAdapter = modelAdapter;
        _parameterMapLookup = parameterMapLookup;
        _defaultStrategyClassName = registry.getDefaultStrategyEntry().getStrategyClass().getName();

        _name = name;
        _minWidth= minWidth;
        initUI(registry, strategyTypeLabel, minWidth, extraComponentForCombo);

    }


    public StrategyDefinition getStrategyDefinition() {
        return _strategy;
    }

    /**
     *
     * @param strategy
     */
    public void setStrategyDefinition(StrategyDefinition strategy) {

        _strategy = strategy;

        if (_strategy != null) {
            String className = strategy.getClass().getName();
            setStrategyClassName(className);

            IStrategyDetailsPanel currentPanel = (IStrategyDetailsPanel)_strategyCardPanel.getCard(className);
            currentPanel.setStrategyDefinition(_strategy);
            _strategyCardPanel.showCard(className);
        }

    }

//        StrategyDetailsPanel currentPanel = (StrategyDetailsPanel)_strategyCardPanel.getCard(className);
//
//        if (!currentPanel.hasStrategy()) {
//            StrategyDefinition strategyS = _strategyFactory.createDefaultStrategyDefinition(className, _parameterMapLookup);
//            currentPanel.setStrategyDefinition(strategyS);
//        }
//
//        setStrategyDefinition(currentPanel.getStrategyDefinition());


//    public void setStrategyDefinition(String className, StrategyDefinitionParameter sParam) {
//        StrategyDetailsPanel currentPanel = (StrategyDetailsPanel)_strategyCardPanel.getCard(className);
//        if (!currentPanel.hasStrategy()) {
//            StrategyDefinition strategyS = _strategyFactory.createDefaultStrategyDefinition(className, _parameterMapLookup);
//                    currentPanel.setStrategyDefinition(strategyS);
//                }
//
//        currentPanel.getStrategyDefinition().setStrategyS(sParam);
//    }

    /**
     * This is our feedack from when the value of the combo box changes....
     * @param className
     */
    public void setStrategyClassName(String className) {
        Object oldVal = _strategyClassName;
        if (log.isDebugEnabled()) {
            log.debug("[" + _name + "] : setStrategyClassName('" + className + "')");
        }
        _strategyClassName = className;

//        _updateCommand.execute(_strategyClassName);

        firePropertyChange(P_STRATEGY_CLASS_NAME, oldVal, _strategyClassName);
    }
    public String getStrategyClassName() {
        return _strategyClassName;
    }




    public void addStrategyDetailPanel(IStrategyDetailsPanel detailsPanel) {
        _strategyCardPanel.addCard(detailsPanel.getDisplayComponent(), detailsPanel.getStrategyClassName());
    }


    protected void initUI(StrategyRegistry registry, String strategyTypeLabel, int minWidth, JComponent extraComponent) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        _strategyTypeEditor = createStrategyTypeEditor(registry, strategyTypeLabel, minWidth, extraComponent);
        _strategyCardPanel = createStrategyCardPanel();

        container.add(_strategyTypeEditor);
        container.add(_strategyCardPanel);

        super.add(container, BorderLayout.NORTH);
    }

    public  JPanel getLayoutContainer() {
        return this;
    }

    private EnumerationPropertyEditor createStrategyTypeEditor(StrategyRegistry registry, String strategyTypeLabel, int minWidth, JComponent extraComponent) {
        EnumerationComboBox strategyTypeCombo = new EnumerationComboBox();

        for (Iterator itr = registry.getStrategyRegistryEntries().iterator(); itr.hasNext();) {
            StrategyRegistryEntry strategyEntry = (StrategyRegistryEntry)itr.next();
            strategyTypeCombo.addValue(strategyEntry.getStrategyLabel(), strategyEntry.getStrategyClass().getName());
        }

        EnumerationPropertyEditor strategyTypeEditor = new EnumerationPropertyEditor(P_STRATEGY_CLASS_NAME, strategyTypeLabel, strategyTypeCombo, minWidth, extraComponent);

        PropertyBinding strategyTypeBinding = new PropertyBinding(strategyTypeEditor, _modelAdapter);
        try {
            strategyTypeBinding.bind(this);
        } catch (JavaBeanException e) {
            throw new RuntimeException(e);
        }

        return strategyTypeEditor;
    }

    private CardLayoutPanel createStrategyCardPanel() {
        return new CardLayoutPanel();
    }

    /**
     * Determines wether the user can edit the parameters for each boundary type.
     *
     * @param readonly
     */
    public void setParametersReadOnly(boolean readonly) {
        for (Iterator itr = _strategyCardPanel.getAllCards().iterator();itr.hasNext(); ) {
            StrategyDetailsPanel p = (StrategyDetailsPanel)itr.next();
            p.setReadOnly(readonly);
        }
    }




    public void setChangeTypeEnabled(boolean enabled) {
        _strategyTypeEditor.setReadonly(!enabled);
    }
    public void setChangeTypeVisible(boolean visible) {
        _strategyTypeEditor.setVisible(visible);
    }

    public void unbind() {

    }

    public boolean hasStrategy(String className) {
        return ((IStrategyDetailsPanel)_strategyCardPanel.getCard(className)).hasStrategy();
    }

    public StrategyDefinition getStrategyDefinition(String className) {
        return ((IStrategyDetailsPanel)_strategyCardPanel.getCard(className)).getStrategyDefinition();
    }

    public String getName() {
        return _name;
    }

    public IModelAdapter getModelAdapter() {
        return _modelAdapter;
    }

    public int getMinWidth() {
        return _minWidth;
    }



    private static final Logger log = Logger.getLogger(StrategyPanel.class);


    public static final String P_STRATEGY_CLASS_NAME = "strategyClassName";

    private IModelAdapter _modelAdapter;
    private CardLayoutPanel _strategyCardPanel;


    private EnumerationPropertyEditor _strategyTypeEditor;



    private StrategyDefinition _strategy;

    private String _defaultStrategyClassName;
    private IParameterMapLookup _parameterMapLookup;

    private String _strategyClassName;

    private boolean _isLoading;

    private String _name;
    private int _minWidth;
}
