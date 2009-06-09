/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.strategy;

import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.framework.parameter.model.IStrategyDefinitionContainer;
import com.ixcode.framework.parameter.model.IStrategyDefinitionContainerListener;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 1, 2007 @ 3:53:42 PM by jim
 */
public class StrategyParameterBinding implements IStrategyParameterEditor, IStrategyDefinitionContainerListener, PropertyChangeListener , IParameterMapLookup {


    public StrategyParameterBinding(String name, StrategyPanel strategyPanel, IStrategyDefinitionFactory factory) {
        _strategyPanel = strategyPanel;
        _name = name;
        _strategyPanel.addPropertyChangeListener(StrategyPanel.P_STRATEGY_CLASS_NAME, this);
        _factory = factory;
    }


    public void propertyChange(PropertyChangeEvent evt) {
        if (log.isDebugEnabled()) {
            log.debug("[" + _name + "] [propertyChange: " + evt.getPropertyName() + "] isLoading=" + _isLoading);
        }
        if (!_isLoading) {
            if (evt.getPropertyName().equals(StrategyPanel.P_STRATEGY_CLASS_NAME)) {
                updateContainerFromPanel();
            } else if (evt.getPropertyName().equals(_parameterName)) {
                updatePanelFromContainer();                
            }
        }
    }


    public void strategyDefinitionReplaced(IStrategyDefinitionContainer source, String parameterName, StrategyDefinition oldStrategy, StrategyDefinition newStrategy) {
        if (_container != null) {
            if (parameterName.equals(_parameterName)) {
                updatePanelFromContainer();
            }
        }
    }

    public void bind(IStrategyDefinitionContainer strategyContainer, String parameterName) {
        if (log.isDebugEnabled()) {
            log.debug("[bind] " + _name + "->(" + parameterName + ") in: " + strategyContainer);
        }
        _container = strategyContainer;
        _parameterName = parameterName;
        _container.addStrategyDefinitionContainerListener(this);
        _parameterMap = strategyContainer.getParameterMap();

        _container.addPropertyChangeListener(parameterName, this);

        updatePanelFromContainer();
    }

    private void updatePanelFromContainer() {

        StrategyDefinition strategy = _container.getStrategyDefinition(_parameterName);
        if (log.isDebugEnabled()) {
            log.debug("[updateUI] newStrategy=" + strategy);
        }

        _isLoading = true;
        _strategyPanel.setStrategyDefinition(strategy);
        _isLoading = false;
    }

    private void updateContainerFromPanel() {
        String className = _strategyPanel.getStrategyClassName();
        if (log.isDebugEnabled()) {
            log.info("[updateModel] strategy=" + className);
        }
        StrategyDefinition updatedStrategy = null;
        if (_strategyPanel.hasStrategy(className)) {
            updatedStrategy = _strategyPanel.getStrategyDefinition(className);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("[createNewDefaultStrategy] className=" + className);
            }
            updatedStrategy = _factory.createDefaultStrategyDefinition(className, this);
        }
        _container.replaceStrategyDefinition(_parameterName, updatedStrategy);
    }


    public void unbind() {
        if (_container != null) {
            if (log.isDebugEnabled()) {
                log.debug("[unbind] container=" + _container);
            }
            _container.removeStrategyDefinitionContainerListener(this);
            _container.removePropertyChangeListener(this);
        }
    }

    public IStrategyDefinitionContainer getStrategyContainer() {
        return _container;
    }

    public StrategyPanel getStrategyPanel() {
        return _strategyPanel;
    }


    public String getParameterName() {
        return _parameterName;
    }

    public String getName() {
        return _name;
    }

    public ParameterMap getParameterMap() {
        return _parameterMap;
    }

    private static final Logger log = Logger.getLogger(StrategyParameterBinding.class);
    private IStrategyDefinitionContainer _container;

    private StrategyPanel _strategyPanel;
    private String _parameterName;
    private String _name;
    private boolean _isLoading;
    private IStrategyDefinitionFactory _factory;
    private ParameterMap _parameterMap;
}
