/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment.properties;

import com.ixcode.bugsim.controller.experiment.IExperiment;
import com.ixcode.framework.model.ModelBase;

import java.util.List;

/**
 * @deprecated 
 */
public class ExperimentProperties extends ModelBase {



    public ExperimentProperties(List propertySheetNames) {
        _propertySheetNames = propertySheetNames;

    }

    public void setExperiment(IExperiment parent) {
        _parent = parent;
    }


    public List getPropertySheetClassNames() {
           return _propertySheetNames;
       }





    public long getLongPropertyValue(String name) {
        return ((Long)getPropertyValue(name)).longValue();
    }

    public int getIntPropertyValue(String name) {
        return ((Integer)getPropertyValue(name)).intValue();
    }

    public IExperiment getExperiment() {
        return _parent;
    }


    private List _propertySheetNames;
    private IExperiment _parent;
}
