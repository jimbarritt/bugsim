/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.simulation.gravitymachine;

import com.ixcode.framework.math.DoubleSequence;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.simulation.model.landscape.information.ISignalFunction;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSource;
import org.jfree.data.KeyedValues;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class KeyedValuesFunctionWrapper implements KeyedValues {


    public KeyedValuesFunctionWrapper(ISignalFunction function, ChartSamplingParameters samplingParameters) {
        ISignalSource sourceStub = new StubSignalSource(1);

        double interval =  samplingParameters.getSampleFrequency();
        _categorySequence = new DoubleSequence(samplingParameters.getSampleRangeStart(), samplingParameters.getSampleRangeEnd(), interval, DoubleMath.DOUBLE_PRECISION_DELTA);

        _valueList = new ArrayList();
        _keyList = new ArrayList();
        for (int i = 0; i < _categorySequence.getSequence().length; ++i) {
            double x = _categorySequence.getSequence()[i];
            _keyList.add(new Double(x));
            _valueList.add(new Double(function.calculateSensoryInformationValue(sourceStub, x)));
        }

    }

    public Comparable getKey(int i) {
        return new Double(_categorySequence.getSequence()[i]);
    }

    public int getIndex(Comparable comparable) {
        return _valueList.indexOf(comparable);
    }

    public List getKeys() {
        return _keyList;
    }

    public Number getValue(Comparable comparable) {
        return getValue(getIndex(comparable));
    }

    public int getItemCount() {
        return _categorySequence.getSequence().length;
    }

    public Number getValue(int i) {
        return new NumberWrapper((Double)_valueList.get(i));
    }


    private DoubleSequence _categorySequence;

    private List _valueList;
    private List _keyList;
}
