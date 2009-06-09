/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.view.tree;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.IParameterModel;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.swing.JFrameExtension;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 4, 2007 @ 3:50:46 PM by jim
 */
public class ParameterSelectionDialog extends JDialog {

     public ParameterSelectionDialog(JFrame owner, ParameterMap parameterMap) throws HeadlessException {
        super(owner);
        super.setModal(true);
        initUI(parameterMap);
        super.setSize(400, 500);
        JFrameExtension.centreWindowOnScreen(this);
         _parameterMap = parameterMap;
    }
    public ParameterSelectionDialog(JDialog owner, ParameterMap parameterMap) throws HeadlessException {
        super(owner);
        super.setModal(true);
        initUI(parameterMap);
        super.setSize(400, 500);
        JFrameExtension.centreWindowOnScreen(this);
        _parameterMap = parameterMap;
    }

    private void initUI(ParameterMap parameterMap) {
        super.getContentPane().setLayout(new BorderLayout());

        Container cp = super.getContentPane();

        ParameterMapTreePanel pp = new ParameterMapTreePanel(parameterMap);
        cp.add(pp, BorderLayout.CENTER);

        _btnSelect = new JButton("Select");
        _btnCancel = new JButton("Cancel");
        _btnSelect.setEnabled(false);

        pp.getTree().getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                IParameterModel model = (IParameterModel)e.getPath().getLastPathComponent();
                if ((model instanceof Parameter) && !(model instanceof StrategyDefinitionParameter)) {
                    _selectedParameter = (Parameter)model;
                    _btnSelect.setEnabled(true);
                } else {
                     _btnSelect.setEnabled(false);
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        buttonPanel.add(_btnSelect);    
        buttonPanel.add(_btnCancel);

        _btnSelect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                select();
            }
        });

        _btnCancel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        JPanel spacer = new JPanel(new BorderLayout());
        spacer.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
        spacer.add(buttonPanel, BorderLayout.NORTH);
        cp.add(spacer, BorderLayout.SOUTH);

    }


    public void selectParameter() {
        _cancelled = true;
        super.setVisible(true);
    }

    public void select() {
        _cancelled = false;
        setVisible(false);

    }

    public void cancel() {
        dispose();
        setVisible(false);
    }


    public boolean isCancelled() {
        return _cancelled;
    }

    public Parameter getSelectedParameter() {
        return _selectedParameter;
    }

    /**
     * Refresh ourselves but if nothings changed try to recreate the same tree collapsed state...
     * @param parameterMap
     */
    public void refresh(ParameterMap parameterMap) {

    }

    public void setSelectVisible(boolean b) {
        _btnSelect.setVisible(b);
    }

    public ParameterMap getParameterMap() {
        return _parameterMap;
    }

    private boolean _cancelled = true;

    private Parameter _selectedParameter;
    private JButton _btnSelect;
    private JButton _btnCancel;
    private ParameterMap _parameterMap;
}
