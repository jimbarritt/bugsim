/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim;

import static com.ixcode.framework.io.IO.tryToClose;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import static java.lang.Thread.currentThread;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class BugsimVersion {

    public static String getVersion() {
        Properties properties = loadPropertiesFromClasspath("application/application.properties");

        return new StringBuilder()
                .append(properties.getProperty("application.version"))
                .append(" dist(").append(properties.getProperty("application.distnumber")).append(")")
                .append(" rev(").append(properties.getProperty("application.revision")).append(")")
                .toString();

    }

    private static Properties loadPropertiesFromClasspath(String propertiesFilename) {
        Properties properties = new Properties();

        InputStream in =  currentThread().getContextClassLoader().getResourceAsStream(propertiesFilename);
        if (in == null) {
            throw new RuntimeException(String.format("No properties file '%s'", propertiesFilename));
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

}
