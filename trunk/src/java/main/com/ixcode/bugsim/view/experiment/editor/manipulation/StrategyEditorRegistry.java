/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.bugsim.model.agent.cabbage.layout.PredefinedResourceLayout;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.framework.model.IModelAdapter;

import java.util.Map;
import java.util.HashMap;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 14, 2007 @ 9:30:28 PM by jim
 */
public class StrategyEditorRegistry {

    public StrategyEditorRegistry(IModelAdapter modelAdapter, IParameterMapLookup lookup) {
        registerEditor(PredefinedResourceLayout.class,  new ResourceLayoutStrategyEditor(modelAdapter, lookup));
    }

    public void registerEditor(Class implementingClass, IStrategyEditor editor) {
        _editors.put(implementingClass.getName(), editor);
    }

    public IStrategyEditor getEditor(String implementingClassName) {
        return (IStrategyEditor)_editors.get(implementingClassName);
    }

    private Map _editors = new HashMap();

}
