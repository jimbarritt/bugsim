package com.ixcode.framework.xml.sax.speedsax;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 */
public class NodeHandlerTestHelper {
    public static void executeParse(TestCase testCase, String inputResource, ContentHandler handlerToTest) throws IOException, SAXException {

        InputStreamReader in = new InputStreamReader(testCase.getClass().getResourceAsStream(inputResource));
        Assert.assertNotNull("Could not find resource '" + inputResource + "' it should be on the classpath in the same location as this testcase, " + NodeHandlerTestHelper.class.getName(), in);

        SAXParser parser = new SAXParser();
        parser.setContentHandler(handlerToTest);
        try {
            long startParse = System.currentTimeMillis();

            InputSource inputSource = new InputSource(in);
            parser.parse(inputSource);

            long stopParse = System.currentTimeMillis();

            if (log.isInfoEnabled()) {
                log.info("executedParse() in: " + (stopParse - startParse));
            }


        } finally {
            if (in != null) in.close();
        }
    }

    private static final Logger log = Logger.getLogger(NodeHandlerTestHelper.class);
}
