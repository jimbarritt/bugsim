/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.javabean.TextAlignment;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.ModelBase;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class PropertyBindingRunner extends JFrame {


    public PropertyBindingRunner() throws HeadlessException, JavaBeanException {
        super("Test Property Binding");

        _model = new TestModel();

        JPanel content = new JPanel(new BorderLayout());

        IModelAdapter modelAdapter = new JavaBeanModelAdapter();
        addProperties(content, modelAdapter);

        addReadOnlyValidation(modelAdapter, content);

        addButtons(content);
        super.getContentPane().add(content);
    }

    private void addReadOnlyValidation(IModelAdapter modelAdapter, JPanel content) throws JavaBeanException {
        PropertyGroupPanel state = new PropertyGroupPanel();
        ReadOnlyPropertyEditor booleanProperty = new ReadOnlyPropertyEditor(TestModel.PROPERTY_TEST_BOOLEAN_PROPERTY, "Val Of Test Boolean", 30);
        state.addPropertyEditor(booleanProperty);
        PropertyBinding binding = new PropertyBinding(booleanProperty, modelAdapter);
        binding.bind(_model);

        ReadOnlyPropertyEditor name = new ReadOnlyPropertyEditor(TestModel.PROPERTY_NAME, "Val Of Name ", 30);
        state.addPropertyEditor(name);
        PropertyBinding binding2 = new PropertyBinding(name, modelAdapter);
        binding2.bind(_model);

        ReadOnlyPropertyEditor path = new ReadOnlyPropertyEditor(TestModel.PROPERTY_PATH, "Val Of Path ", 40);
        state.addPropertyEditor(path);
        PropertyBinding.bindEditor(path, modelAdapter, _model);

        content.add(state);
    }

    private void addProperties(JPanel content, IModelAdapter modelAdapter) throws JavaBeanException {
        PropertyGroupPanel p = new PropertyGroupPanel("Properties");
        CheckBoxPropertyEditor editor1 = new CheckBoxPropertyEditor(TestModel.PROPERTY_TEST_BOOLEAN_PROPERTY, "Check Mah!");
        p.addPropertyEditor(editor1);
        PropertyBinding binding = new PropertyBinding(editor1, modelAdapter);
        binding.bind(_model);

        TextFieldPropertyEditor editor2 = new TextFieldPropertyEditor(TestModel.PROPERTY_NAME, "Name", "not set", 20, TextAlignment.LEFT, 40);
        p.addPropertyEditor(editor2);
        PropertyBinding binding2 = new PropertyBinding(editor2, modelAdapter);
        binding2.bind(_model);

        FileChooserPropertyEditor editor3 = new FileChooserPropertyEditor(TestModel.class,  TestModel.PROPERTY_PATH, "Some Path", 40);
        p.addPropertyEditor(editor3);
        PropertyBinding.bindEditor(editor3, modelAdapter, _model);


        content.add(p, BorderLayout.NORTH);
    }

    private void addButtons(JPanel content) {
        JPanel pBtn = new JPanel();

        JButton clicker = new JButton("Click MAh!");
        clicker.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                _model.setTestBooleanProperty(!_model.isTestBooleanProperty());
                _model.setName("This is MAH! name!");
                _model.setPath("/Users/jim/Documents");
            }
        });
        pBtn.add(clicker);
        content.add(pBtn, BorderLayout.SOUTH);
    }


    public static class TestModel extends ModelBase {

        public static final String PROPERTY_TEST_BOOLEAN_PROPERTY = "testBooleanProperty";
        public static final String PROPERTY_NAME = "name";
        public static final String PROPERTY_PATH = "path";

        public boolean isTestBooleanProperty() {
//            if (log.isInfoEnabled()) {
//                log.info("isTestBooleanProperty: " + _testBooleanProperty);
//            }
            return _testBooleanProperty;
        }

        public void setTestBooleanProperty(boolean testBooleanProperty) {
            Object oldVal = new Boolean(_testBooleanProperty);
            _testBooleanProperty = testBooleanProperty;
//            if (log.isInfoEnabled()) {
//                log.info("setTestBooleanProperty: " + _testBooleanProperty);
//            }
            super.firePropertyChangeEvent(PROPERTY_TEST_BOOLEAN_PROPERTY, oldVal, new Boolean(_testBooleanProperty));
        }

        public String getName() {
            return _name;
        }

        public void setName(String name) {
            String oldVal = _name;
            _name = name;

            super.firePropertyChangeEvent(PROPERTY_NAME, oldVal, _name);
        }

        public String getPath() {
            return _path;
        }

        public void setPath(String path) {
            String oldVal = _path;
            _path = path;
            super.firePropertyChangeEvent(PROPERTY_PATH, oldVal, _path);

        }

        private boolean _testBooleanProperty;
        private String _name;
        private String _path;
        private static final Logger log = Logger.getLogger(TestModel.class);


    }

    private TestModel _model;
}
