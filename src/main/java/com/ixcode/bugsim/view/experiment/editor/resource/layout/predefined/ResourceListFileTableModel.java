/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.resource.layout.predefined;

import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceListFile;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ResourceListFileTableModel extends DefaultTableModel {

    public ResourceListFileTableModel(boolean includeDirectory) {
        super();

        super.addColumn(COL_NAME);
        super.addColumn(COL_PREVIEW);

        if (includeDirectory) {
            super.addColumn(COL_DIRECTORY);
        }
    }

    public void setResourceListFiles(List resourceListFiles) {
        _resourceListFiles = resourceListFiles;
    }

    public Object getValueAt(int row, int column) {
        ResourceListFile file = (ResourceListFile)_resourceListFiles.get(row);

        Object value = null;
        if (column == 0) {
            value = file.getName();
        } else if (column == 1){
            value = file.getPreview();
        } else if (column == 2) {
            value = file.getParentFile().getName();
        }
        return value;
    }

    public int getRowCount() {
        if (_resourceListFiles != null) {
            return _resourceListFiles.size();
        } else {
            return 0;
        }
    }

    public static final String COL_NAME = "Name";

    public static final String COL_PREVIEW = "Preview";
    public static final String COL_DIRECTORY = "Directory";
    private List _resourceListFiles = new ArrayList();
}
