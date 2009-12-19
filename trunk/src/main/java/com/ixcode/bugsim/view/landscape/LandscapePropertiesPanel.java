/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape;

import com.ixcode.bugsim.view.geometry.ScaledDistancePropertyEditor;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.swing.property.IPropertyValueEditor;
import com.ixcode.framework.swing.property.PropertyPanel;

import javax.swing.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapePropertiesPanel extends PropertyPanel {

    public LandscapePropertiesPanel(Landscape landscape) {
        super("Landscape Properties");



        _extentXEditor = super.addReadOnlyPropertyEditor("Extent X", Landscape.PROPERTY_EXTENT_X, "" + landscape.getExtentX());
        _extentYEditor = super.addReadOnlyPropertyEditor("Extent Y", Landscape.PROPERTY_EXTENT_Y, "" + landscape.getExtentY());



//        _scalePropertyEditor = new ScalePropertyGroupPanel(landscape.getScale());

//        super.add(_scalePropertyEditor);
        _scaledDistanceEditor = new ScaledDistancePropertyEditor("scale", "Scale: 1 Unit =", 150);
        super.add(_scaledDistanceEditor);
        _scaledDistanceEditor.setValue(landscape.getScale().toString());

        _landscapeWidth = new ScaledDistancePropertyEditor("landscapeWidth", "Landscape Width", 150);
        _landscapeHeight = new ScaledDistancePropertyEditor("landscapeHeight", "Landscape Height", 150 );

        _landscapeWidth.setValue(landscape.getScaledWidth().toString());
        _landscapeHeight.setValue(landscape.getScaledHeight().toString());

        super.add(_landscapeWidth);
        super.add(_landscapeHeight);

        super.setPanelAction(new UpdateLandscapeAction(this, landscape));
    }



    public int getExtentX() {
        return super.getIntTextFieldValue(Landscape.PROPERTY_EXTENT_X);
    }

    public int getExtentY() {
        return super.getIntTextFieldValue(Landscape.PROPERTY_EXTENT_Y);
    }

    public void setExtentX(int x) {
        _extentXField.setText("" + x);
        _extentXEditor.setValue("" + x);
    }

    public void setExtentY(int y) {
        _extentYField.setText("" + y);
        _extentYEditor.setValue("" + y);
    }

    public ScaledDistance getScale() {
        return ScaledDistance.parse(_scaledDistanceEditor.getValue());
    }

    public ScaledDistance getLandscapeWidth() {
        return ScaledDistance.parse(_landscapeWidth.getValue());
    }

    public ScaledDistance getLandscapeHeight() {
        return ScaledDistance.parse(_landscapeHeight.getValue());
    }

    private JTextField _extentXField = new JTextField();
    private JTextField _extentYField = new JTextField();



    private ScaledDistancePropertyEditor _scaledDistanceEditor;
    private ScaledDistancePropertyEditor _landscapeWidth;
    private ScaledDistancePropertyEditor _landscapeHeight;
    private IPropertyValueEditor _extentXEditor;
    private IPropertyValueEditor _extentYEditor;
}
