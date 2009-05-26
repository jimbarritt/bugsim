/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.experiment.model.xml;

import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.ExperimentPlanFile;
import com.ixcode.framework.javabean.format.JavaBeanFormatter;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.xml.XMLAttributes;
import com.ixcode.framework.xml.XMLWriter;
import com.ixcode.framework.xml.sax.speedsax.NodeHandler;
import com.ixcode.framework.xml.sax.speedsax.format.JavaBeanXMLFormatter;
import com.ixcode.bugsim.BugsimMain;
import org.apache.log4j.Logger;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Description : Exports an ExperimentPlan to XML.
 *
 * @todo make all the tag names come from the XMLNodes - infact make a generic writer that uses the XML nodes...
 */
public class ExperimentPlanXML extends XMLWriter {

    public static final String PLAN_FILE_EXTENSION = "xml";

    public ExperimentPlanXML() {
    }

    public List readPlanFiles(File directory) throws IOException, SAXException {
        List files = new ArrayList();
        File[] listing = directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith("." + PLAN_FILE_EXTENSION);
            }
        });

        for (int i = 0; i < listing.length; ++i) {
            ExperimentPlanFile planFile = createPlanFile(listing[i]);
            planFile = new ExperimentPlanFile(listing[i].getParentFile(), listing[i].getName(), planFile.getDescription());
            files.add(planFile);
        }
        return files;
    }

    private ExperimentPlanFile createPlanFile(File file) throws IOException, SAXException {
        ExperimentPlanFileXMLNode experimentPlanFileNode = new ExperimentPlanFileXMLNode();
        NodeHandler handler = new NodeHandler(experimentPlanFileNode);
        parseXMLFile(file, handler);
        return (ExperimentPlanFile)handler.getRootNode().getCreatedObject();
    }

    private void parseXMLFile(File file, ContentHandler handler) throws IOException, SAXException {
        parseXMLFile(file.getAbsolutePath(), new FileInputStream(file), handler);
    }

    private void parseXMLFile(String resourceName, InputStream input, ContentHandler handler) throws IOException, SAXException {
        InputStreamReader in = new InputStreamReader(input);

        SAXParser parser = new SAXParser();
        parser.setContentHandler(handler);
        try {
            long startParse = System.currentTimeMillis();

            InputSource inputSource = new InputSource(in);
            parser.parse(inputSource);

            long stopParse = System.currentTimeMillis();

            if (log.isDebugEnabled()) {
                log.debug("Parsed '" + resourceName + "' in : " + (stopParse - startParse) + " ms.");
            }

        } finally {
            if (in != null) in.close();
        }
    }

    public ExperimentPlan importPlan(URL url) throws IOException, SAXException {
                    InputStream input = null;
        try {

            input = url.openStream();
            return importPlan(url.toExternalForm(), input);
        } finally {
            if (input != null) {
                input.close();
            }
        }

    }

    public ExperimentPlan importPlan(File file) throws IOException, SAXException {
        InputStream input =null;
        try {
            input= new FileInputStream(file);
            return importPlan(file.getAbsolutePath(), input);
        } finally {
            if (input != null) {
                input.close();
            }
        }

    }

    public ExperimentPlan importPlan(String resourceName, InputStream in) throws IOException, SAXException {


        ExperimentPlanNode experimentPlanNode = new ExperimentPlanNode();
        JavaBeanFormatter formatter = new JavaBeanFormatter();
        ParameterMapXML.initFormatter(formatter);
        NodeHandler handler = new NodeHandler(experimentPlanNode, new JavaBeanXMLFormatter(formatter));
        parseXMLFile(resourceName, in, handler);
        ExperimentPlan plan = (ExperimentPlan)handler.getRootNode().getCreatedObject();
        // Dont do this anymore because we leave them as references!
//            plan.getParameterTemplate().rewireParameterReferences();

        // Still need to do this until we can think of a clever way to make the Parameter References work in both
        // as children of a DerivedParameter and as children of a Manipulation.
        plan.rewireManipulationParameterReferences();
        return plan;

    }

    public void exportPlan(File outputFile, ExperimentPlan plan, boolean overwrite) throws IOException {
        setFormatter(new JavaBeanFormatter());
        ParameterMapXML.initFormatter(getFormatter());
        if (outputFile.exists() && !overwrite) {
            throw new IOException("File already Exists: " + outputFile.getAbsolutePath());
        }


        PrintWriter out = null;
        try {
            if (outputFile.exists()) {
                File backup = new File(outputFile.getParentFile(), outputFile.getName() + ".bak");
                if (backup.exists()) {
                    backup.delete();
                }
                outputFile.renameTo(backup);
            }
            outputFile.createNewFile();
            out = new PrintWriter(new FileOutputStream(outputFile));

            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            openComment(0, out, "File generated by bugsim on " + F.format(new Date()), true);

            String fileId = outputFile.getName().substring(0, outputFile.getName().lastIndexOf("."));

            XMLAttributes planAttr = createPlanAttributes(fileId, plan);

            openTag(0, out, planAttr, "experimentPlan");


            XMLAttributes templateAttr = new XMLAttributes();
            if (plan.getTemplate() != null) {
                templateAttr.add("name", plan.getTemplateName());
                templateAttr.add("description", plan.getTemplate().getShortDescription());
            }
            openTag(1, out, templateAttr, "template", false, false);
            if (plan.getTemplate() != null) {
                out.print(plan.getTemplate().getLongDescription());
            }
            closeTag(0, out, "template");

            openTag(1, out, EMPTY_ATTRIBUTES, "description", false, false);
            out.print(plan.getDescription());
            closeTag(0, out, "description");

            openTag(1, out, EMPTY_ATTRIBUTES, "bugsim-version", false, false);
                        out.print(BugsimMain.getVersion());
                        closeTag(0, out, "bugsim-version");

            exportParameterMap(1, out, plan);

            exportParameterManipulations(1, out, plan.getParameterManipulationSequences());

            closeTag(0, out, "experimentPlan");

        } finally {

            if (out != null) {
                out.close();
            }
        }

    }


    private void exportParameterManipulations(int indent, PrintWriter out, List parameterManipulations) {
        openTag(indent, out, EMPTY_ATTRIBUTES, "parameterManipulations");
        for (Iterator itr = parameterManipulations.iterator(); itr.hasNext();) {
            List manipulationValues = (List)itr.next();
            exportParameterManipulationSequence(indent + 1, out, manipulationValues);
        }

        closeTag(indent, out, "parameterManipulations");
    }

    private void exportParameterManipulationSequence(int indent, PrintWriter out, List manipulationValues) {
        openTag(indent, out, EMPTY_ATTRIBUTES, "manipulationSequence");
        for (Iterator itr = manipulationValues.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            exportManipulation(indent + 1, out, manipulation);
        }
        closeTag(indent, out, "manipulationSequence");
    }

    private void exportManipulation(int indent, PrintWriter out, IParameterManipulation manipulation) {
        if (manipulation instanceof MultipleParameterManipulation) {
            exportMultipleParameterManipulation(indent, out, (MultipleParameterManipulation)manipulation);
        } else if (manipulation instanceof DerivedParameterManipulation) {
            exportDerivedParameterManipulation(indent, out, (DerivedParameterManipulation)manipulation);
        } else if (manipulation instanceof ParameterManipulation) {
            if (((ParameterManipulation)manipulation).getValueToSet() instanceof StrategyDefinitionParameter) {
                exportStrategyManipulation(indent, out, (ParameterManipulation)manipulation);
            } else {
                exportSimpleParameterManipulation(indent, out, (ParameterManipulation)manipulation);
            }
        } else {
            throw new IllegalStateException("Manipulation Type Not Recognised: " + manipulation.getClass().getName());
        }
    }


    private void exportMultipleParameterManipulation(int indent, PrintWriter out, MultipleParameterManipulation multipleParameterManipulation) {
        openTag(indent, out, EMPTY_ATTRIBUTES, "multipleManipulation");
        List manipulations = multipleParameterManipulation.getManipulations();
        openTag(indent + 1, out, EMPTY_ATTRIBUTES, "manipulations");
        for (Iterator itr = manipulations.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            exportManipulation(indent + 2, out, manipulation);
        }

        closeTag(indent + 1, out, "manipulations");
        closeTag(indent, out, "multipleManipulation");
    }

    private void exportDerivedParameterManipulation(int indent, PrintWriter out, DerivedParameterManipulation derivedParameterManipulation) {
        throw new IllegalStateException("We are not supposed to be using DerivedManipulations yet!! better update this!");
    }

    private void exportSimpleParameterManipulation(int indent, PrintWriter out, ParameterManipulation manipulation) {
        XMLAttributes attr = createSimpleManipulationAttributes(manipulation);
        openTag(indent, out, attr, "parameterManipulation");

        ParameterMapXML.exportParameterReference(manipulation.getParameter(), indent + 1, out);

        closeTag(indent, out, "parameterManipulation");
    }

    private void exportStrategyManipulation(int indent, PrintWriter out, ParameterManipulation manipulation) {
        XMLAttributes att = createStrategyManipulationAttributes(manipulation);
        openTag(indent, out, att, "strategyManipulation");

        ParameterMapXML.exportParameterReference(manipulation.getParameter(), indent + 1, out);

        ParameterMapXML.exportStrategyParameter(indent + 1, out, (StrategyDefinitionParameter)manipulation.getValueToSet(), false);

        closeTag(indent, out, "strategyManipulation");
    }

    private XMLAttributes createStrategyManipulationAttributes(ParameterManipulation manipulation) {
        XMLAttributes attr = new XMLAttributes();
        attr.add("name", manipulation.getParameter().getName());
        if (manipulation.isParameterStructureChanged()) {
            attr.add("structureChanged", new Boolean(manipulation.isParameterStructureChanged()));
        }

        return attr;
    }

    private XMLAttributes createSimpleManipulationAttributes(ParameterManipulation manipulation) {
        XMLAttributes attr = new XMLAttributes();
        attr.add("name", manipulation.getParameter().getName());
        if (manipulation.isParameterStructureChanged()) {
            attr.add("structureChanged", new Boolean(manipulation.isParameterStructureChanged()));
        }
        attr.add("valueToSet", manipulation.getValueToSet());
        attr.add("valueType", manipulation.getValueToSet().getClass().getName());
        return attr;

    }


    /**
     * @param indent
     * @param out
     * @param plan
     * @todo need to make it so you don't need to add the categories element....
     */
    private void exportParameterMap(int indent, PrintWriter out, ExperimentPlan plan) {
        openTag(indent, out, EMPTY_ATTRIBUTES, "parameterMap");

        openTag(indent + 1, out, EMPTY_ATTRIBUTES, "categories");
        ParameterMapXML.exportCategories(indent + 2, out, plan.getParameterTemplate().getCategories());
        closeTag(indent + 1, out, "categories");

        closeTag(indent, out, "parameterMap");
    }

    private XMLAttributes createPlanAttributes(String fileId, ExperimentPlan plan) {
        XMLAttributes attr = new XMLAttributes();
        attr.add("name", plan.getName());
        attr.add("trialName", plan.getTrialName());
        attr.add("fileId", fileId);
        attr.add(ExperimentPlanNode.A_EXPERIMENT_NAME, plan.getExperimentName());
        attr.add(ExperimentPlanNode.A_OUTPUT_DIR_NAME, plan.getOutputDirName());
        return attr;
    }

    private static final Logger log = Logger.getLogger(ExperimentPlanXML.class);
    private final static DateFormat F = new SimpleDateFormat("dd/mm/yyy");

    public static final ExperimentPlanXML INSTANCE = new ExperimentPlanXML();

    private ParameterMapFactory _parameterMapFactory = new ParameterMapFactory();
}
