/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model;

import java.io.File;
import java.net.URI;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlanFile extends File {
    public ExperimentPlanFile() {
        super("");
    }



    public ExperimentPlanFile(String pathname, String description) {
        super(pathname);
        _description = description;
    }

    public ExperimentPlanFile(String parent, String child, String description) {
        super(parent, child);
        _description = description;
    }

    public ExperimentPlanFile(File parent, String child, String description) {
        super(parent, child);
        _description = description;
    }

    public ExperimentPlanFile(URI uri, String description) {
        super(uri);
        _description = description;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
    }

    private String _description;


}
