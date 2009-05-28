/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.junit;

import javax.swing.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SwingUnitTestHelper {

    public static void runFrame(final JFrame f) throws InterruptedException {

        Thread t = new Thread(new Runnable() {
            public void run() {
                f.pack();
                f.show();
            }
        }, "Test Experiment Thread");


        t.start();

        Thread.currentThread().sleep(1000);
        while (f.isVisible()) {
            Thread.currentThread().sleep(1000);
        }

    }

}
