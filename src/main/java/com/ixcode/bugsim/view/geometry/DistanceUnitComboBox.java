/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.geometry;

import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.swing.property.EnumerationComboBox;

import java.util.Iterator;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DistanceUnitComboBox extends EnumerationComboBox {

    public DistanceUnitComboBox(boolean includeLogical) {
        List units = DistanceUnitRegistry.INSTANCE.getDistanceUnits();
        if (includeLogical) {
            super.addValue("logical", "logical");
        }

        for (Iterator itr = units.iterator(); itr.hasNext();) {
            IDistanceUnit unit = (IDistanceUnit)itr.next();
            super.addValue(unit.getSymbol(), unit.getClass().getName());    
        }

    }

    public String getSelectedUnitSymbol() {
        return (String)super.getSelectedItem();
    }

    public void setSelectedUnitSymbol(String unitSymbol) {
        super.setSelectedItem(unitSymbol);
    }

    public void setSelectedUnitSymbol(IDistanceUnit defaultUnit) {
        setSelectedUnitSymbol(defaultUnit.getSymbol());
    }

    public IDistanceUnit getSelectedUnit() {
        return DistanceUnitRegistry.INSTANCE.resolveUnitFromSymbol(getSelectedUnitSymbol());
    }
}
