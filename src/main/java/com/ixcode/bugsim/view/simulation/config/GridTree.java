/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.config;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.Enumeration;
import java.util.List;

/**
 *  Description : extends JTree to wrap up all the nastee ness so we can just pass it a list of grids.
 */
public class GridTree extends JTree {

    public GridTree(String rootName, List grids) {
        super(new GridTreeModel(rootName, grids));
        super.setCellRenderer(new GridTreeCellRenderer());
        super.setSelectionRow(0);
    }


    public void expandAll(boolean expand) {
        TreeNode root = (TreeNode)super.getModel().getRoot();


        expandAll(this, new TreePath(root), expand);
    }

    private void expandAll(JTree tree, TreePath parent, boolean expand) {

        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }


        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }






}
