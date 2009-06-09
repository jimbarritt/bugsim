/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.io;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

class StreamGobbler extends Thread {
    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (log.isInfoEnabled()) {
                    log.info("[" + type + "] " + line);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    private static final Logger log = Logger.getLogger(StreamGobbler.class);
}
