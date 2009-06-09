/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.javabean.format.TypeSafeEnumFormat;
import com.ixcode.framework.swing.property.example.ExampleBean;
import com.ixcode.framework.swing.property.example.ExampleBeanPropertySheet_General;
import com.ixcode.framework.swing.property.example.ExampleBeanPropertySheet_Specific;
import com.ixcode.framework.swing.property.example.ExampleTypeSafeEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class PropertyInspector extends JDialog implements PropertyChangeListener{



    public static final String MSG_NO_OBJECT_SELECTED ="No Object Selected";
    public PropertyInspector(JFrame owner, JavaBeanModelAdapter modelAdapter, boolean modal) throws HeadlessException {
        super(owner, MSG_NO_OBJECT_SELECTED);
        JPanel content = new JPanel(new BorderLayout());
        super.getContentPane().add(content);
        super.setModal(modal);
        _modelAdapter = modelAdapter;


        _tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        content.add(_tabbedPane);

        _buttonPanel = new PropertyInspectorButtons(this, modal);

        JPanel btnContainer = new JPanel(new BorderLayout());
        btnContainer.add(Box.createVerticalStrut(15), BorderLayout.SOUTH);
        btnContainer.add(_buttonPanel, BorderLayout.NORTH);
        content.add(btnContainer, BorderLayout.SOUTH);
        _factory = PropertySheetFactory.INSTANCE;
        _modal = modal;


    }

    public void addPropertyInspectorListener(IPropertyInspectorListener listener) {
        _listeners.add(listener);
    }

    public void removePropertyInspectorListener(IPropertyInspectorListener listener) {
        _listeners.remove(listener);
    }

    private void fireAppliedChangesEvent() {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IPropertyInspectorListener listener = (IPropertyInspectorListener)itr.next();
            listener.appliedChanges(_bean);
        }
    }


    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals(IPropertyValueEditor.EDITED_PROPERTY) && !_isLoading) {
            _buttonPanel.setState(ButtonPanelState.DIRTY);
        }

    }

    public void inspect(String instanceId, Object bean) {
        _isCancelled = true;
        removeBeanListener(_bean);
       super.setTitle("Properties for '" + instanceId + "'");
        initPropertySheets(bean);
        loadSheetsFromBean(bean);
        _bean = bean;
//        super.pack();
        super.setVisible(true);
        listenToBean(_bean);
    }

    private void removeBeanListener(Object instance) {

        }

        private void listenToBean(Object instance) {
            try {
                Method addPropertyListener =instance.getClass().getMethod("addPropertyChangeListener", new Class[]{PropertyChangeListener.class});

                PropertyChangeListener listener = new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent evt) {

                        if (!_savingBean) {
                            loadSheetsFromBean(_bean);
                        }
                    }
                };
                addPropertyListener.invoke(instance, new Object[] {listener});
            } catch (NoSuchMethodException e) {
                // Just dont add the listener
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }


    private void loadSheetsFromBean(Object bean) {
        for (Iterator itr = _sheets.iterator(); itr.hasNext();) {
            IPropertySheet sheet = (IPropertySheet)itr.next();
            _isLoading = true;
            sheet.loadFromBean(bean);
            _isLoading = false;
        }
    }


    private void saveSheetsToBean(Object bean) {
        _savingBean = true;
       for (Iterator itr = _sheets.iterator(); itr.hasNext();) {
            IPropertySheet sheet = (IPropertySheet)itr.next();
            sheet.saveToBean(bean);
        }
        _savingBean = false;
    }

    private void initPropertySheets(Object bean) {
        if (_sheets != null) {
            for (Iterator itr = _sheets.iterator(); itr.hasNext();) {
                IPropertySheet sheet = (IPropertySheet)itr.next();
                sheet.getDisplayComponent().removePropertyChangeListener(this);
            }
        }
        _sheets = (List)_factory.createPropertySheets(bean.getClass(), _modelAdapter);
        _tabbedPane.removeAll();

        for (Iterator itr = _sheets.iterator(); itr.hasNext();) {

            IPropertySheet sheet = (IPropertySheet)itr.next();
            _tabbedPane.add(sheet.getSheetName(), sheet.getDisplayComponent());
            sheet.addPropertyChangeListener(this);

                sheet.setReadonly(_readonly);

        }

    }


   public void doOkAction() {
       saveSheetsToBean(_bean);
       _isCancelled = false;
        _buttonPanel.setState(ButtonPanelState.CLEAN);
       if (_modal) {
        setVisible(false);
        super.dispose();
       }
       fireAppliedChangesEvent();

    }

    public void doApplyAction() {
        saveSheetsToBean(_bean);
             _isCancelled = false;
        _buttonPanel.setState(ButtonPanelState.CLEAN);
        fireAppliedChangesEvent();
    }

    public void doCancelAction() {
           _isCancelled = true;
            loadSheetsFromBean(_bean);
        _buttonPanel.setState(ButtonPanelState.CLEAN);
            if (_modal)  {
                super.hide();
                super.dispose();
            }
        }



    public void setReadonly(boolean readonly) {
        for (Iterator itr = _sheets.iterator(); itr.hasNext();) {
            IPropertySheet sheet = (IPropertySheet)itr.next();
            sheet.setReadonly(readonly);
        }
        _buttonPanel.setReadOnly(readonly);
        _readonly = readonly;
    }


    public static void main(String[] args) {

        JavaBeanModelAdapter modelAdapter = new JavaBeanModelAdapter();
        PropertySheetFactory.INSTANCE.registerPropertySheet(ExampleBean.class,  ExampleBeanPropertySheet_General.class);
        PropertySheetFactory.INSTANCE.registerPropertySheet(ExampleBean.class, ExampleBeanPropertySheet_Specific.class);
        modelAdapter.getFormatter().registerFormat(Locale.UK, ExampleTypeSafeEnum.class, new TypeSafeEnumFormat(ExampleTypeSafeEnum.class));

        PropertyInspector inspector = new PropertyInspector(null, modelAdapter, false);
        ExampleBean bean = new ExampleBean(20, 2.3, 3456, new Integer(45), new Double(67.6788), new Long(3456), "hello", ExampleTypeSafeEnum.ENUM_VALUE_3);

        inspector.setSize(300, 400);
        inspector.setReadonly(false);
        inspector.addWindowListener(new WindowListener(){
            public void windowOpened(WindowEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

            public void windowIconified(WindowEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void windowDeiconified(WindowEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void windowActivated(WindowEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void windowDeactivated(WindowEvent e) {

            }
        });

        inspector.inspect("Example Bean", bean);





        System.out.println("Completed Inspection (cancelled=" + inspector.isCancelled() + "), Bean is now: \n" + bean);
//        System.exit(0);
    }


    public boolean isCancelled() {
        return _isCancelled;
    }

    private boolean _isCancelled;

    private JTabbedPane _tabbedPane;
    private PropertySheetFactory _factory;
    private List _sheets = new ArrayList();
    private JavaBeanModelAdapter _modelAdapter;
    private Object _bean;
    private boolean _readonly;
    private PropertyInspectorButtons _buttonPanel;
    private boolean _modal;
    private boolean _isLoading;
    private List _listeners = new ArrayList();
    private boolean _savingBean = false;
}
