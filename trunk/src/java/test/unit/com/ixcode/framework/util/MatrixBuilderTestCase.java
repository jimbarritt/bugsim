/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.util;

import junit.framework.TestCase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TestCase for class : MatrixBuilder
 */
public class MatrixBuilderTestCase extends TestCase {

    public void testMatrixBuilder_simple() {
            List level1 = new ArrayList();
            List level2 = new ArrayList();
            List level3 = new ArrayList();


            List listOfLists = new ArrayList();

            level1.add("A-1");
            level1.add("B-1");
            level1.add("C-1");

            level2.add("A-2");
            level2.add("B-2");
            level2.add("C-2");



            level3.add("A-3");
            level3.add("B-3");
            level3.add("C-3");


            listOfLists.add(level1);
            listOfLists.add(level2);
            listOfLists.add(level3);

            MatrixBuilder b = new MatrixBuilder();

            List results = b.build(listOfLists);

            debugResults(results);
            int expected = level1.size() * level2.size() * level3.size();
            assertEquals("Number of results", expected, results.size());

        }

    public void testMatrixBuilder_complex() {
        List level1 = new ArrayList();
        List level2 = new ArrayList();
        List level3 = new ArrayList();
        List level4 = new ArrayList();

        List listOfLists = new ArrayList();

        level1.add("A-1");
        level1.add("B-1");
        level1.add("C-1");

        level2.add("A-2");
        level2.add("B-2");
        level2.add("C-2");
        level2.add("D-2");


        level3.add("A-3");
        level3.add("B-3");
        level3.add("C-3");

        level4.add("A-4");
        level4.add("B-4");


        listOfLists.add(level1);
        listOfLists.add(level2);
        listOfLists.add(level3);
        listOfLists.add(level4);

        MatrixBuilder b = new MatrixBuilder();

        List results = b.build(listOfLists);

        debugResults(results);
        int expected = level1.size() * level2.size() * level3.size() * level4.size();
         assertEquals("Number of results", expected, results.size());

    }

    private void debugResults(List results) {
        StringBuffer sb = new StringBuffer();
        DecimalFormat f = new DecimalFormat("00");
        int iResult = 0;
        for (Iterator itr = results.iterator(); itr.hasNext();) {
            List list = (List)itr.next();
            sb.append("[").append(f.format(iResult+1)).append("] : ");

            sb.append("(");
            for (Iterator itrValues = list.iterator(); itrValues.hasNext();) {
                String value = (String)itrValues.next();
                sb.append(value);
                if (itrValues.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append(")\n");
            iResult++;
        }
        System.out.println("Results:");
        System.out.println(sb.toString());
    }


}
