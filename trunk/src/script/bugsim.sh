#! /bin/bash
# To make a script executable do chmod +x
PROGRESS_FACT=com.ixcode.bugsim.view.experiment.meandispersal.MeanDispersalProgressFactory
EXPERIMENT_FACT=com.ixcode.bugsim.model.experiment.experiment1b.Experiment1bFactory
LANDSCAPE_VIEW_FACT=com.ixcode.bugsim.view.experiment.meandispersal.MeanDispersalLandscapeViewFactory

CPTH=bugsim-experiment-1-v2.9.jar
CPTH=$CPTH:./lib/commons-beanutils-core.jar
CPTH=$CPTH:./lib/commons-logging-api.jar
CPTH=$CPTH:./lib/jcommon-1.0.0.jar
CPTH=$CPTH:./lib/servlet.jar
CPTH=$CPTH:./lib/xmlParserAPIs.jar
CPTH=$CPTH:./lib/commons-beanutils.jar
CPTH=$CPTH:./lib/commons-logging.jar
CPTH=$CPTH:./lib/jfreechart-1.0.0.jar
CPTH=$CPTH:./lib/xercesImpl.jar
CPTH=$CPTH:./lib/commons-jxpath-1.2.jar
CPTH=$CPTH:./lib/commons-math-1.1.jar
CPTH=$CPTH:./lib/log4j-1.2.13.jar
CPTH=$CPTH:./lib/xml-apis.jar

OUTPUT=/home/rialto2/barritjame/bugsim/results

#nohup runs the process so that there are "no holdups" this means that if you 
#close the terminal session that started it it still runs on the server.
nohup java -Xmx512M -Dbugsim.debug=false -cp $CPTH com.ixcode.bugsim.BugsimMain interactive=false output=$OUTPUT exp=$EXPERIMENT_FACT lscp=$LANDSCAPE_VIEW_FACT prog=$PROGRESS_FACT
