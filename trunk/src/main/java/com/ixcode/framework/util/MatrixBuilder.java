/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : This class takes a list of lists and constructs a matrix of each combination of values
 *                the matrix is returned as another list of lists.
 *

 */
public class MatrixBuilder {


    /**
     *
     *                e.g. you have the following :
     *
     *                [0] A1, B1, C1
     *                [1] A2, B2, C2
     *                [2] A3, B3, C3
     *
     *                Matrix Builder will return
     *
     *                [0] A1, A2, A3
     *                [1] A1, A2, B3
     *                [2] A1, A2, C3
     *                [3] A1, B2, A3
     *                [4] A1, B2, B3
     *                [5] A1, B2, C3
     *
     *                And so on until all combinations are represented (in this case, 27)
     *
     * @param listOfLists
     * @return
     */
    public List build(List listOfLists) {
        List results = new ArrayList();

        int iRootIndex = 0;
        List currentValueSet = new ArrayList();
        recurseLists(iRootIndex, listOfLists, currentValueSet, results);

        return results;
    }

    private void recurseLists(int iRootIndex, List rootList, List parentValueSet, List results) {
        if (iRootIndex < rootList.size()) {
            List currentLevel = (List)rootList.get(iRootIndex);
            int nextLevelIndex = iRootIndex + 1;
            for (Iterator itr = currentLevel.iterator(); itr.hasNext();) {
                List currentValueSet = new ArrayList(parentValueSet);

                Object value = itr.next();
                currentValueSet.add(value);
                recurseLists(nextLevelIndex, rootList, currentValueSet, results);
            }
        } else {
            results.add(parentValueSet);
        }
    }
}
