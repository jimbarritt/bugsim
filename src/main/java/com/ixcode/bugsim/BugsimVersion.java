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
        Properties properties = loadPropertiesFromClasspath("application/application.properties");

        String version = properties.getProperty("application.version")
                         + " dist(" + properties.getProperty("application.distnumber") +")";

        return version.trim();

    }

    private static Properties loadPropertiesFromClasspath(String propertiesFilename) {
        Properties properties = new Properties();

        InputStream in =  Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFilename);
        if (in == null) {
            throw new RuntimeException("No properties file 'application.application.version'");
        }

        try {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not load version from properties file 'application.application.version' on the classpath.");
        } finally {
            tryToClose(in);
        }
        return properties;
    }

    private static void tryToClose(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
