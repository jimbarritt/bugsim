/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor;

import com.ixcode.framework.experiment.model.ExperimentPlanFile;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentPlanTableModel extends DefaultTableModel {

    public ExperimentPlanTableModel(boolean includeDirectory) {
        super();

        super.addColumn(COL_NAME);
        super.addColumn(COL_DESCRIPTION);

        if (includeDirectory) {
            super.addColumn(COL_DIRECTORY);
        }
    }

    public void setPlanFiles(List planFiles) {
        _planFiles = planFiles;
        super.fireTableDataChanged();
    }

    public Object getValueAt(int row, int column) {
        ExperimentPlanFile file = (ExperimentPlanFile)_planFiles.get(row);

        Object value = null;
        if (column == 0) {
            value = file.getName();
        } else if (column == 1){
            value = file.getDescription();
        } else if (column == 2) {
            value = file.getParentFile().getName();
        }
        return value;
    }

    public int getRowCount() {
        if (_planFiles != null) {
            return _planFiles.size();
        } else {
            return 0;
        }
    }

    public static final String COL_NAME = "Name";

    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_DIRECTORY = "Directory";
    private List _planFiles = new ArrayList();
}
