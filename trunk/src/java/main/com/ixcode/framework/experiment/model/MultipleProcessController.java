/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.experiment.model;

import java.util.StringTokenizer;

/**
 *  Description : We can split an experimental run up into multiple processes and so this defines
 *                which process and how many there are in total..
 *  Created     : Mar 31, 2007 @ 12:12:18 PM by jim
 */
public class MultipleProcessController {

    public static MultipleProcessController parse(String s) {
        StringTokenizer st = new StringTokenizer(s, "of");
        int id = Integer.parseInt(st.nextToken());
        int count = Integer.parseInt(st.nextToken());
        return new MultipleProcessController(id, count);
    }

    public MultipleProcessController(int processId, int processCount) {
        _processId = processId;
        _processCount = processCount;
    }

    public int[] getConfigurationIndexes(int iterationCount) {

        int perProcess = (_processCount <= iterationCount) ? iterationCount / _processCount : 1;



        int start = perProcess * (_processId-1);
        int end = perProcess * _processId;

        if (end > iterationCount) {
            end = iterationCount;
        }
        
        int remainder = end-iterationCount;
        if (remainder > 0 || _processId == _processCount) {
            end-=remainder;
        }

        int[] iterations = new int[end-start];
        for (int i=start;i<end;++i) {
            iterations[i-start]=i;
        }
        return iterations;
    }

    public int getProcessId() {
        return _processId;
    }

    public int getProcessCount() {
        return _processCount;
    }


    public String toString() {
        return _processId + " of " + _processCount;
    }

    private int _processId;
    private int _processCount;
    public static final MultipleProcessController SINGLE_PROCESS = new MultipleProcessController(1, 1);
}
