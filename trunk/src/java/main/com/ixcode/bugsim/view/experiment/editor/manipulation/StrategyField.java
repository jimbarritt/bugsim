/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.view.experiment.editor.manipulation;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.swing.JFrameExtension;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.bugsim.view.experiment.editor.IParameterMapLookup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;
import org.jfree.date.SerialDate;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 14, 2007 @ 9:22:21 PM by jim
 */
public class StrategyField extends JPanel {
    public StrategyField(JTable parent, IModelAdapter modelAdapter, IParameterMapLookup lookup) {
        super(new BorderLayout());
        super.add(_label, BorderLayout.CENTER);
        _editorRegistry = new StrategyEditorRegistry(modelAdapter, lookup);

        _parent = parent;
        JButton b = new JButton("...");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        editValue();
                        if (_parent.isEditing()) {
                            _parent.getCellEditor().stopCellEditing();
                        }
                    }


                });


            }
        });
        super.add(b, BorderLayout.EAST);
    }

    private void editValue() {
        if (log.isInfoEnabled()) {
            log.info("Selected new Strategy");
        }
        IStrategyEditor editor = _editorRegistry.getEditor(_currentStrategy.getImplementingClassName());
        editor.setStrategyDefinitionParameter(_currentStrategy);

        JDialog d =  new JDialog((JFrame)this.getRootPane().getParent(), "Edit Strategy", true);
        d.getContentPane().setLayout(new BorderLayout());
        d.getContentPane().add(editor.getEditingComponent(), BorderLayout.CENTER);
//        d.setSize(200, 300);
        d.pack();
        JFrameExtension.centreWindowOnScreen(d);
        d.show();

        _currentStrategy = editor.getStrategyDefinitionParameter();
    }

    public Object getEditedValue() {
        return _currentStrategy;
    }

    public void setStrategy(StrategyDefinitionParameter sp) {
        _originalStrategy = sp;
        _currentStrategy = _originalStrategy;
        _label.setText(_originalStrategy.getName());
    }

    private static final Logger log = Logger.getLogger(StrategyField.class);
    private StrategyDefinitionParameter _originalStrategy;
    private JTable _parent;
    private JLabel _label= new JLabel();
    private StrategyDefinitionParameter _currentStrategy;
    private StrategyEditorRegistry _editorRegistry;
}
