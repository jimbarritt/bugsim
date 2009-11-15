package com.ixcode.framework.logging;

import static com.ixcode.framework.io.IO.*;
import org.apache.log4j.*;
import org.apache.log4j.xml.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.net.*;

public class Log4JConfiguration {
    private static final Logger log = Logger.getLogger(Log4JConfiguration.class);

    public static void loadLog4JConfig() {
		URL log4jUri = null;
        InputStream inputStream = null;
        try {
			log4jUri = Thread.currentThread().getContextClassLoader().getResource("logging/log4j.xml");
			inputStream = log4jUri.openConnection().getInputStream();

			DocumentBuilderFactory newInstance = DocumentBuilderFactory.newInstance();
			newInstance.setValidating(false);
			DocumentBuilder newDocumentBuilder = newInstance.newDocumentBuilder();

			Document doc = newDocumentBuilder.parse(inputStream);
			DOMConfigurator conf = new DOMConfigurator();
			conf.doConfigure(doc.getDocumentElement(), new Hierarchy(Logger.getRootLogger()));

            log.info("Log4J initialised with log configuration [" + log4jUri.toExternalForm() + "]");
            log.info("Log4J Logging Level = " + Logger.getRootLogger().getLevel());
        } catch (Exception e) {
            e.printStackTrace();
            String uri = (log4jUri!= null) ? log4jUri.toExternalForm() : "not found";
			throw new RuntimeException("Could not initialise log4j @ " + uri, e);
		} finally {
             tryToClose(inputStream);
        }


    }
}
