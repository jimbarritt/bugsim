package com.ixcode.framework.xml.sax;


import org.xml.sax.SAXException;

import org.xml.sax.InputSource;
import org.apache.xerces.parsers.SAXParser;


import java.io.*;




/**
 * Created by IntelliJ IDEA.
 * User: jbarr
 * Date: 15-Dec-2004
 * Time: 05:00:36
 * To change this template use File | Settings | File Templates.
 */
public class SaxParserRunner {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: com.ixcode.framework.xml.sax.SaxParserRunner filename.xml");
            System.exit(-1);
        }
        SaxParserRunner parserRunner = new SaxParserRunner();
        try {
            File testFile = new File(args[0]);
            parserRunner.parse(testFile);
        } catch (Throwable t) {
            t.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.exit(-1);
        }
    }

    public SaxParserRunner() {
    }

    public void parse(File inputFile) throws IOException, SAXException {
        SAXParser parser = new SAXParser();
        parser.setContentHandler(new StubContentHandler());
        FileReader in = null;
        try {
            in = new FileReader(inputFile);
            long startParse = System.currentTimeMillis();
            parser.parse(new InputSource(in));

            long stopParse = System.currentTimeMillis();
            System.out.println("[timing] saxparser.parse() " + (stopParse - startParse));

        } finally {
            if (in == null) in.close();
        }
    }
}
