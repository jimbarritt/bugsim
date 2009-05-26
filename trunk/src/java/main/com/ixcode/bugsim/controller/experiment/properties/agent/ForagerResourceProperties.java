/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment.properties.agent;

import com.ixcode.bugsim.controller.experiment.properties.ExperimentProperties;
import com.ixcode.bugsim.controller.experiment.properties.ForagingAgentProperties;

import java.util.ArrayList;

/**
 *  Description : For foragers which look for resources.
 * @deprecated
 */
public class ForagerResourceProperties extends ExperimentProperties {


    public ForagerResourceProperties() {
        super(new ArrayList());
    }

    ForagingAgentProperties getForaginAgentProperties() {
         return null;
    }


}
