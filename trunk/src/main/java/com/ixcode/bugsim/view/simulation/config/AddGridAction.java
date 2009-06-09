/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.config;

import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquareLocation;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.swing.action.ActionBase;
import com.ixcode.framework.swing.property.PropertyInspector;
import com.ixcode.framework.swing.property.PropertySheetFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class AddGridAction extends ActionBase {
    static {
        PropertySheetFactory.INSTANCE.registerPropertySheet(Grid.class,  GridPropertySheet_General.class);

    }

    public AddGridAction(JFrame owner, GridTree gridTree, List grids) {
        super("Add Grid", "/icons/add.gif");
        _gridTree = gridTree;
        _gridTreeModel = (GridTreeModel)_gridTree.getModel();
        _grids = grids;
        _owner = owner;
    }

    /**
     * @param e
     * @todo Should fire the tree changed event by firing and catching events from the underlying model
     */
    public void actionPerformed(ActionEvent e) {
        Object selected = _gridTree.getSelectionPath().getLastPathComponent();
        if (selected instanceof GridTreeRootNode) {
            addGrid((GridTreeRootNode)selected);
        }

    }


    private void addGrid(GridTreeRootNode root) {
        Grid newGrid = createGrid(null);

        if (newGrid != null) {
            root.addGrid(newGrid);
            System.out.println("Added grid to root " + root.getGrids().size());
            _gridTreeModel.fireTreeNodesInserted(root, newGrid, root.getGrids().indexOf(newGrid));
            if (root.getGrids().size() == 1) {
                _gridTreeModel.fireTreeStructureChanged(root);
            }
            _gridTree.setSelectionRow(0);
        }
    }

    private Grid createGrid(GridSquare parentGridSquare) {
        PropertyInspector inspector = new PropertyInspector(_owner, new JavaBeanModelAdapter(), true);

        GridSquareLocation location = (parentGridSquare == null) ? null : new GridSquareLocation(parentGridSquare.getParent(), parentGridSquare.getxIndex(), parentGridSquare.getyIndex());
        Grid g = new Grid(location);
        inspector.inspect("New Grid", g);
        if (!inspector.isCancelled()) {
            return g;
        }else {
            return null;
        }

    }

    private GridTree _gridTree;
    private GridTreeModel _gridTreeModel;
    private List _grids;
    private JFrame _owner;
}
