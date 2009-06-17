/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim;

import com.ixcode.bugsim.model.experiment.*;
import com.ixcode.bugsim.server.*;
import com.ixcode.bugsim.view.experiment.*;
import com.ixcode.bugsim.view.experiment.editor.*;
import com.ixcode.bugsim.view.landscape.*;
import com.ixcode.framework.experiment.model.*;
import com.ixcode.framework.experiment.model.xml.*;
import com.ixcode.framework.io.*;
import com.ixcode.framework.javabean.*;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.*;
import com.ixcode.framework.swing.*;
import org.apache.log4j.*;
import org.apache.log4j.xml.*;
import org.w3c.dom.*;
import org.xml.sax.*;

import javax.swing.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class BugsimMain {

    public static final String VERSION = BugsimVersion.getVersion();

    public static void main(String[] args) {
        loadLog4JConfig();
        if (log.isInfoEnabled()) {
            log.info("Welcome to bugsim version " + getVersion() + "");
            log.info("Logs will be output to : " + new File("logs").getAbsolutePath());
        }

        JFrameExtension.setSystemLookAndFeel();

        ExperimentTemplateRegistry.setInstance(new BugsimExperimentTemplateRegistry());
        BugsimExtensionJavaBeanValueFormats.registerBugsimExtensionFormats();

        BugsimMainArgs ba = new BugsimMainArgs(args);

        BugsimMain.initialiseExperiment(ba);
    }

    private static void loadLog4JConfig() {
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

    private static void tryToClose(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Could not close input stream.", e);
            }
        }
    }


    public static void initialiseExperiment(BugsimMainArgs ba) {
        try {
            IExperimentFactory experimentFactory = (IExperimentFactory)instantiateClass(ba.getExperimentFactoryName());
            IExperimentProgressFactory progressPanelFactory = (IExperimentProgressFactory)instantiateClass(ba.getProgressPanelFactoryName());
            ILandscapeViewFactory landscapeViewFactory = (ILandscapeViewFactory)instantiateClass(ba.getLandscapeViewFactoryName());

            IExperiment experiment = null;
            if (experimentFactory instanceof ILoadedExperimentFactory) {
                ILoadedExperimentFactory loadedFactory = (ILoadedExperimentFactory)experimentFactory;
                ExperimentPlan plan = loadExperimentPlan(ba);

                if (plan != null && ba.isEditPlan()) {
                    plan = editPlan(plan);
                }

                if (plan != null) {
                    experiment = loadedFactory.createExperiment(plan);
                } else {
                    if (log.isInfoEnabled()) {
                        log.info("Plan is null from edit plan, exiting...");
                    }
                    System.exit(0);
                }
            }

            if (experiment == null) {
                if (log.isInfoEnabled()) {
                    log.info("Experiment is null from edit plan, creating default...");
                }
                experiment = experimentFactory.createExperiment(ba.getTrialName());
            }

            ParameterMapDebug.debugParams(experiment.getExperimentPlan().getParameterTemplate());

            MultipleProcessController processController = ba.getMultipleProcessController();
            ExperimentController controller = new ExperimentController(experiment, processController);

            controller.setStartupLogAppenderName("BugsimStartupFileAppender");
            controller.setOutputRootPath(ba.getOutputPath());
            controller.setOutputIterationDetails(ba.isOutputIterationDetails());

            if (ba.getStatusServer()) {
                initialiseStatusServer(controller);
            }

            controller.reset();

            if (ba.isInteractiveMode()) {
                controller.setAutopauseOn(true); // could just set this to teh value of interactiveMode but its weak because it is bound to that variable so if something changes it will break later
                initialiseUI(progressPanelFactory, experiment, controller, landscapeViewFactory);
            } else {
                controller.setAutopauseOn(false);
                controller.run();
            }
        } catch (Throwable e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            System.exit(-1);
        }
    }

    //mail -s "Experiment $EXPERIMENT_FACT Plan: $PLAN Complete!" jim@planet-ix.com < complete.txt
    public static void execCompleteScript(ExperimentController controller) {


            File completeScript = new File(controller.getExperimentOutputDir().getParentFile().getAbsolutePath() + "/complete.sh");

            if (completeScript.exists()) {
                String cmd = completeScript.getAbsolutePath() +
                         " " + getHostName() +
                         " " + controller.getExperimentOutputDir().getName() +
                         " " + controller.getExperiment().getExperimentPlan().getDescription().replace(' ', '%');

                CommandExec.execute(cmd);
                if (log.isInfoEnabled()) {
                    log.info("Completion Script Executed.");
                }
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Could not find " + completeScript.getAbsolutePath() + " to execute.");
                }
            }


    }

    private static String getSafeExperimentName(ExperimentController controller) {
        return controller.getExperimentOutputDir().getName().replace('-', 'z');
    }

    public static String getHostName() {
        String hostname = "Unkown Host";
        try {
            InetAddress addr = InetAddress.getLocalHost();

            // Get IP Address
            byte[] ipAddr = addr.getAddress();

            // Get hostname
            hostname = addr.getHostName();
        } catch (UnknownHostException e) {
        }
        return hostname;
    }

    private static void initialiseStatusServer(ExperimentController controller) {
        BugsimSocketServer server = new BugsimSocketServer(controller);
        Thread t = new Thread(server, "BugsimSocketServer");
        t.start();
    }

    private static ExperimentPlan editPlan(ExperimentPlan plan) {
        ExperimentPlanEditorDialog editor = new ExperimentPlanEditorDialog(true);
        editor.editPlan(plan, true);
        ExperimentPlan editedPlan = null;
        if (!editor.isCancelled()) {
            editedPlan = editor.getPlan();     // Might have changed.
        }
        return editedPlan;

    }

    private static ExperimentPlan loadExperimentPlan(BugsimMainArgs ba) {
        String planFileName = ba.getExperimentPlanFileName();

        ExperimentPlan plan = null;
        if (planFileName.length() != 0) {
            plan = loadPlan(planFileName);
        } else if (ba.isInteractiveMode()) {
            planFileName = choosePlan();
            if (planFileName != null) {
                plan = loadPlan(planFileName);
            }
        }
        return plan;
    }

    private static String choosePlan() {
        OpenExperimentPlanDialog dialog = new OpenExperimentPlanDialog();
        dialog.showOpenPlanDialog();
        String planFileName = null;
        if (!dialog.isCancelled()) {
            planFileName = dialog.getSelectedPlanFile().getAbsolutePath();
        }
        return planFileName;
    }

    private static ExperimentPlan loadPlan(String planFileName) {

        File planFile = new File(planFileName);
        if (!planFile.exists()) {
            throw new IllegalStateException("Specified a Plan file-name which does not exist : " + planFile.getAbsolutePath());
        }
        try {
            if (log.isInfoEnabled()) {
                log.info("Loading Plan From : " + planFile.getAbsolutePath());
            }
            return ExperimentPlanXML.INSTANCE.importPlan(planFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

    }

    private static void initialiseUI(IExperimentProgressFactory progressPanelFactory, IExperiment experiment, ExperimentController controller, ILandscapeViewFactory landscapeViewFactory) throws JavaBeanException {
        ExperimentControllerFrame experimentController = null;
        JComponent progressPanel = progressPanelFactory.createExperimentProgressPanel(experiment);
        experimentController = new ExperimentControllerFrame(controller, progressPanel);
        experimentController.pack();
        experimentController.setLocation(700, 0);
        experimentController.setSize(550, experimentController.getSize().height);
        experimentController.show();
        if (landscapeViewFactory.isShowLandscapeView()) {
            JFrame landscapeFrame = landscapeViewFactory.initialiseLandscapeView(experiment, controller);
            landscapeFrame.setLocation(0, 0);

//             landscapeFrame.setSize(450, 500);

            landscapeFrame.show();
        }
    }

    public static Object instantiateClass(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class c = Thread.currentThread().getContextClassLoader().loadClass(className);
        return c.newInstance();
    }


    public static boolean isDebug() {
        return true;
    }


    /**
     * @return
     */
    public static String getVersion() {
        return VERSION;
    }

    private static final Logger log = Logger.getLogger(BugsimMain.class);
}
