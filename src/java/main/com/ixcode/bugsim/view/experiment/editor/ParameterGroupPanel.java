/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.editor;

import com.ixcode.bugsim.view.landscape.geometry.CartesianBoundsPropertyEditor;
import com.ixcode.bugsim.view.landscape.geometry.CartesianDimensionsPropertyEditor;
import com.ixcode.bugsim.view.landscape.geometry.RectangularCoordinatePropertyEditor;
import com.ixcode.bugsim.view.landscape.geometry.ScaledDistancePropertyEditor;
import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.info.PropertyBundle;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.PropertyBinding;
import com.ixcode.framework.swing.property.PropertyGroupPanel;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ParameterGroupPanel extends PropertyGroupPanel {

    public ParameterGroupPanel(IModelAdapter modelAdapter, String title) {
        super(title);
        _modelAdapter = modelAdapter;

    }

    public ParameterGroupPanel(IModelAdapter modelAdapter) {
        _modelAdapter = modelAdapter;
    }


    public IPropertyValueEditor addParameterEditor(String className, String name, int minWidth) {
        return addParameterEditor(className, name, null, minWidth);

    }

    public IPropertyValueEditor addParameterEditor(String className, String name, String label, int minWidth) {
        PropertyBundle bundle = _modelAdapter.getPropertyBundle(className, name, Locale.UK);
        Class type = _modelAdapter.getPropertyType(className, name);

        String labelToAdd = (label == null) ? bundle.getLabel() : label;
        return super.addPropertyEditor(name, type, labelToAdd, bundle.getDisplayCharacterCount(), bundle.getTextAlignment(), minWidth);
    }


    public IPropertyValueEditor addScaledDistanceParameterEditor(String className, String name, String label, int minWidth, boolean includeLogical, IDistanceUnit defaultUnit) {
        PropertyBundle bundle = _modelAdapter.getPropertyBundle(className, name, Locale.UK);
        Class type = _modelAdapter.getPropertyType(className, name);
        if (!ScaledDistance.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Tried to create a scaled Distance editor with a property of type ; " + type.getName());
        }
        String labelToAdd = (label == null) ? bundle.getLabel() : label;
        ScaledDistancePropertyEditor editor = new ScaledDistancePropertyEditor(name, labelToAdd, minWidth, includeLogical);
        editor.setUnit(defaultUnit);
        super.addPropertyEditor(editor);
        return editor;
    }


    public CartesianBoundsPropertyEditor addCartesianBoundsParameterEditor(String className, String name, String label, int minWidth, int decimalPlaces) {
        PropertyBundle bundle = _modelAdapter.getPropertyBundle(className, name, Locale.UK);
        Class type = _modelAdapter.getPropertyType(className, name);
        if (!CartesianBounds.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Tried to create a scaled Distance editor with a property of type ; " + type.getName());
        }
        String labelToAdd = (label == null) ? bundle.getLabel() : label;
        CartesianBoundsPropertyEditor editor = new CartesianBoundsPropertyEditor(name, labelToAdd, minWidth, decimalPlaces);
        addPropertyEditor(editor);
        return editor;
    }


    public  RectangularCoordinatePropertyEditor addRectangularCoordinateParameterEditor(String className, String name, String label, int minWidth, int decimalPlaces) {
        PropertyBundle bundle = _modelAdapter.getPropertyBundle(className, name, Locale.UK);
        Class type = _modelAdapter.getPropertyType(className, name);
        if (!RectangularCoordinate.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Tried to create a rectangular coordinate editor with a property of type ; " + type.getName());
        }
        String labelToAdd = (label == null) ? bundle.getLabel() : label;
        RectangularCoordinatePropertyEditor editor = new RectangularCoordinatePropertyEditor(name, labelToAdd, minWidth, decimalPlaces);
        addPropertyEditor(editor);
        return editor;
    }

    public CartesianDimensionsPropertyEditor  addCartesianDimensionsParameterEditor(String className, String name, String label, int minWidth, int decimalPlaces) {
        PropertyBundle bundle = _modelAdapter.getPropertyBundle(className, name, Locale.UK);
        Class type = _modelAdapter.getPropertyType(className, name);
        if (!CartesianDimensions.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException("Tried to create a cartesian dimensions editor with a property of type ; " + type.getName());
        }
        String labelToAdd = (label == null) ? bundle.getLabel() : label;
        CartesianDimensionsPropertyEditor editor = new CartesianDimensionsPropertyEditor(name, labelToAdd, minWidth, decimalPlaces);
        addPropertyEditor(editor);
        return editor;
    }


    public IModelAdapter getModelAdapter() {
        return _modelAdapter;
    }



    public void addPropertyEditorBinding(final IPropertyValueEditor editor) {

        PropertyBinding binding = new PropertyBinding(editor, _modelAdapter);
        _bindings.add(binding);
        _bindingMap.put(editor.getPropertyName(), binding);
    }

    public void setModel(Object model) {

        try {
            for (Iterator itr = _bindings.iterator(); itr.hasNext();) {
                PropertyBinding propertyBinding = (PropertyBinding)itr.next();
                propertyBinding.release(_model);
                propertyBinding.bind(model);
            }
            _model = model;
        } catch (JavaBeanException e) {
            throw new RuntimeException(e);
        }
    }

    protected PropertyBinding getPropertyBinding(String propertyName) {
        if (!_bindingMap.containsKey(propertyName)) {
            throw new IllegalArgumentException("No PropertyBinding for property called '" + propertyName + "'");
        }
        return (PropertyBinding)_bindingMap.get(propertyName);        
    }

    private static final Logger log = Logger.getLogger(ParameterGroupPanel.class);
    private IModelAdapter _modelAdapter;
    private Object _model;
    private List _bindings = new ArrayList();
    private Map _bindingMap = new HashMap();
}
