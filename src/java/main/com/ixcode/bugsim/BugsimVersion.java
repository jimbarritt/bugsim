/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BugsimVersion {

    public static String getVersion() {
        Properties p = new Properties();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream in =  cl.getResourceAsStream("application/application.properties");
        if (in == null) {
            throw new RuntimeException("No properties file 'application.application.version'"); 
        }
        String version  = "";
        try {
            p.load(in);
            version = p.getProperty("application.version") + " dist(" + p.getProperty("application.distnumber") +")";
        } catch (IOException e) {
            throw new RuntimeException("Could not load version from properties file 'application.application.version' on the classpath.");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        return version.trim();

    }
}
