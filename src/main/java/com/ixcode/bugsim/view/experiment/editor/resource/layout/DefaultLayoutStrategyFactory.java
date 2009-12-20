/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout;

import com.ixcode.bugsim.view.experiment.editor.boundary.IStrategyDefinitionFactory;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;
import com.ixcode.bugsim.view.experiment.editor.strategy.DefaultAzimuthStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceLayoutStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.framework.parameter.model.StrategyDefinition;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 4:20:23 PM by jim
 */
public class DefaultLayoutStrategyFactory implements IStrategyDefinitionFactory {
    public StrategyDefinition createDefaultStrategyDefinition(String className, IParameterMapLookup parameterMapLookup) {
       ResourceLayoutStrategyBase s = (ResourceLayoutStrategyBase)ResourceLayoutStrategyFactory.createDefaultLayoutS(className, parameterMapLookup.getParameterMap());
        LandscapeCategory landscapeCategory = LandscapeCategory.findInParameterMap(parameterMapLookup.getParameterMap());
        ResourceLayoutStrategyBase.centreOnLandscape(s, landscapeCategory);
        return s;
    }

    public static final IStrategyDefinitionFactory INSTANCE = new DefaultLayoutStrategyFactory();
}
