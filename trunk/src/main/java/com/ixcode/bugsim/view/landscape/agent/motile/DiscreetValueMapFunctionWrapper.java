/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.agent.motile;

import com.ixcode.bugsim.view.simulation.gravitymachine.NumberWrapper;
import com.ixcode.framework.math.DiscreetValueMap;
import org.jfree.data.KeyedValues;

import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class DiscreetValueMapFunctionWrapper implements KeyedValues {
    public DiscreetValueMapFunctionWrapper(DiscreetValueMap valueMap) {
        _valueMap = valueMap;
    }

    public Comparable getKey(int i) {
        return new Double(_valueMap.getKey(i));
    }

    public int getIndex(Comparable comparable) {
        return _valueMap.getIndexOfValue((Double)comparable);
    }

    public List getKeys() {
        return _valueMap.getKeyList();
    }

    public Number getValue(Comparable comparable) {
        return new NumberWrapper(_valueMap.getValue(((Double)comparable).doubleValue()));
    }

    public int getItemCount() {
        return _valueMap.getSize();
    }

    public Number getValue(int i) {
        return new NumberWrapper(_valueMap.getValue(i));
    }

    private DiscreetValueMap _valueMap;
}
