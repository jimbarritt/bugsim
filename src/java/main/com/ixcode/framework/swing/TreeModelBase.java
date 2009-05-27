/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : This gives you useful functionality for implementing a tree model based on a custom object model
 */
public abstract class TreeModelBase implements TreeModel {

    public TreeModelBase(Object root) {
        _root = root;
    }

    public Object getRoot() {
        return _root;
    }


    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("valueForPathChanged: " + path + "new Value");
    }

    public void addTreeModelListener(TreeModelListener l) {
        _modelListeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        _modelListeners.remove(l);

    }

    public void fireTreeNodesChangedEvent(Object model) {
        TreeModelEvent e = new TreeModelEvent(this, createTreePath(model));
        for (Iterator itr = _modelListeners.iterator(); itr.hasNext();) {
            TreeModelListener listener = (TreeModelListener)itr.next();
            listener.treeNodesChanged(e);
        }
    }

    private Object[] createTreePath(Object model) {
        List treePath = new ArrayList();
        Object current = model;

        addPathItem(treePath, current);

        Object[] path = treePath.toArray(new Object[]{});

        return path;

    }

    private void addPathItem(List treePath, Object current) {
        treePath.add(0, current);
        if (getParent(current) != null) {
            addPathItem(treePath, getParent(current));
        }
    }

    protected abstract Object getParent(Object child);

    public void fireTreeNodesInserted(Object parent, Object newModel, int newModelIndex) {
        int[] childIndices = new int[]{newModelIndex};
        Object[] children = new Object[]{newModel};
        TreeModelEvent e = new TreeModelEvent(this, createTreePath(parent), childIndices, children);
        for (Iterator itr = _modelListeners.iterator(); itr.hasNext();) {
            TreeModelListener listener = (TreeModelListener)itr.next();
            listener.treeNodesInserted(e);
        }
    }

    public void fireTreeNodesRemoved(Object parent, Object removedModel, int removedModelIndex) {
        int[] childIndices = new int[]{removedModelIndex};
        Object[] children = new Object[]{removedModel};
        TreeModelEvent e = new TreeModelEvent(this, createTreePath(parent), childIndices, children);
        for (Iterator itr = _modelListeners.iterator(); itr.hasNext();) {
            TreeModelListener listener = (TreeModelListener)itr.next();
            listener.treeNodesRemoved(e);
        }
    }


    public void fireTreeStructureChanged(Object node) {
        TreeModelEvent e = new TreeModelEvent(this, createTreePath(node));
        for (Iterator itr = _modelListeners.iterator(); itr.hasNext();) {
            TreeModelListener listener = (TreeModelListener)itr.next();
            listener.treeStructureChanged(e);
        }
    }


    private Object _root;
    private List _modelListeners = new ArrayList();

}
