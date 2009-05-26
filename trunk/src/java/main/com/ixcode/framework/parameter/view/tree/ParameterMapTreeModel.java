/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.view.tree;

import com.ixcode.framework.parameter.model.*;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeModel;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 4, 2007 @ 2:44:48 PM by jim
 */
public class ParameterMapTreeModel implements TreeModel {



    public ParameterMapTreeModel(ParameterMap root) {
        _root = root;
    }

    public Object getRoot() {
        return _root;
    }

    public Object getChild(Object parent, int index) {
        Object child = null;
        if (parent instanceof ParameterMap) {
            ParameterMap rootNodeModel = (ParameterMap)parent;
            child = rootNodeModel.getCategories().get(index);
        } else if (parent instanceof Category) {
            Category groupModel = (Category)parent;
            if (index < groupModel.getSubCategories().size()) {
                child = groupModel.getSubCategories().get(index);
            } else {
                child = groupModel.getParameters().get(index - groupModel.getSubCategories().size());
            }
        } else if (parent instanceof StrategyDefinitionParameter) {
            StrategyDefinitionParameter parameter = (StrategyDefinitionParameter)parent;
            child = parameter.getParameters().get(index);
        } else if (parent instanceof Parameter) {
            child = ((Parameter)parent).getStrategyDefinitionValue();
        }

        return child;
    }

    public int getChildCount(Object parent) {
        int childCount = 0;
        if (parent instanceof ParameterMap) {
            ParameterMap rootNodeModel = (ParameterMap)parent;
            childCount = rootNodeModel.getCategories().size();
        } else if (parent instanceof Category) {
            Category groupModel = (Category)parent;
            childCount = groupModel.getSubCategories().size();
            childCount += groupModel.getParameters().size();
        } else if (parent instanceof StrategyDefinitionParameter) {
            StrategyDefinitionParameter parameter = (StrategyDefinitionParameter)parent;
            childCount = parameter.getParameters().size();
        } else if (parent instanceof Parameter) {
            childCount = 1;
        }
        return childCount;
    }

    public boolean isLeaf(Object node) {
        boolean isLeaf;
        if (node instanceof StrategyDefinitionParameter) {
            isLeaf = false;
        } else if  (node instanceof Parameter) {
            Parameter p = (Parameter)node;
            isLeaf = !p.containsStrategy();
        } else {
            isLeaf = false;
        }

        return isLeaf;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("valueForPathChanged: " + path + "new Value");
    }

    public int getIndexOfChild(Object parent, Object child) {
        int indexOfChild = -1;
        if (parent instanceof ParameterMap) {
            ParameterMap rootNodeModel = (ParameterMap)parent;
            indexOfChild = rootNodeModel.getCategories().indexOf(child);
        } else if (parent instanceof Category) {
            Category groupModel = (Category)parent;
            indexOfChild = groupModel.getSubCategories().indexOf(child);
            if (indexOfChild == -1) {
                indexOfChild = groupModel.getParameters().indexOf(child) + groupModel.getParameters().size();
            }
        } else if (parent instanceof StrategyDefinitionParameter) {
            StrategyDefinitionParameter strategyP = (StrategyDefinitionParameter)parent;
            indexOfChild = strategyP.getParameters().indexOf(child);

        } else if (parent instanceof  Parameter) {
            indexOfChild = 0;
        }
        return indexOfChild;
    }

    public void addTreeModelListener(TreeModelListener l) {
        _modelListeners.add(l);
    }

    public void removeTreeModelListener(TreeModelListener l) {
        _modelListeners.remove(l);

    }

    public void fireTreeNodesChangedEvent(IParameterModel model) {
        TreeModelEvent e = new TreeModelEvent(this, createTreePath(model));
        for (Iterator itr = _modelListeners.iterator(); itr.hasNext();) {
            TreeModelListener listener = (TreeModelListener)itr.next();
            listener.treeNodesChanged(e);
        }
    }

    private Object[] createTreePath(IParameterModel model) {
        List treePath = new ArrayList();
        IParameterModel current = model;

        addPathItem(treePath, current);

        Object[] path = treePath.toArray(new Object[]{});
//        System.out.println("path: " + treePath);
        return path;

    }

    private void addPathItem(List treePath, IParameterModel current) {
        treePath.add(0, current);
//        System.out.println("Adding: " + current);
        if (current.getParent() != null) {
            addPathItem(treePath, current.getParent());
        }
    }

    public void fireTreeNodesInserted(IParameterModel parent, IParameterModel newModel, int newModelIndex) {
        int[] childIndices = new int[] {newModelIndex};
        Object[] children = new Object[]{newModel};
        TreeModelEvent e = new TreeModelEvent(this, createTreePath(parent), childIndices, children);
        for (Iterator itr = _modelListeners.iterator(); itr.hasNext();) {
            TreeModelListener listener = (TreeModelListener)itr.next();
            listener.treeNodesInserted(e);
        }
    }

    public void fireTreeNodesRemoved(IParameterModel parent, IParameterModel removedModel, int removedModelIndex) {
         int[] childIndices = new int[] {removedModelIndex};
        Object[] children = new Object[]{removedModel};
        TreeModelEvent e = new TreeModelEvent(this, createTreePath(parent), childIndices, children);
        for (Iterator itr = _modelListeners.iterator(); itr.hasNext();) {
            TreeModelListener listener = (TreeModelListener)itr.next();
            listener.treeNodesRemoved(e);
        }
        }


    public void fireTreeStructureChanged() {
        TreeModelEvent e = new TreeModelEvent(this, createTreePath(_root));
        for (Iterator itr = _modelListeners.iterator(); itr.hasNext();) {
            TreeModelListener listener = (TreeModelListener)itr.next();
            listener.treeStructureChanged(e);
        }
    }

                       private static final Logger log = Logger.getLogger(ParameterMapTreeModel.class);
    private ParameterMap _root;
    private List _modelListeners = new ArrayList();

}
