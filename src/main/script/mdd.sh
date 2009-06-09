#! /bin/bash

# To make a script executable do chmod +x
PROGRESS_FACT=com.ixcode.bugsim.view.experiment.meandispersal.MeanDispersalProgressFactory
EXPERIMENT_FACT=com.ixcode.bugsim.model.experiment.experiment1b.Experiment1bFactory
LANDSCAPE_VIEW_FACT=com.ixcode.bugsim.view.experiment.meandispersal.MeanDispersalLandscapeViewFactory


VERSION=2.15
BUGSIM_JAR=bugsim-experiment-1-v$VERSION.jar
CPTH=$BUGSIM_JAR
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

echo
echo BugSim version $VERSION [$BUGSIM_JAR]
echo ------------------------------------------------------------ 
echo Experiment factory [$EXPERIMENT_FACT]
echo
echo -n "Please tell me which trial to execute: "
read -e TRIAL

ARGS="interactive=false output=$OUTPUT outputItrDetails=false exp=$EXPERIMENT_FACT lscp=$LANDSCAPE_VIEW_FACT prog=$PROGRESS_FACT trial=$TRIAL"

echo
echo 
echo Executing with the following arguments :
echo
echo $ARGS
echo 
echo 
#-Dlog4j.configuration=log4j_test.xml - dont need this anymore.
VM_ARGS="-Xmx512M -Dbugsim.debug=false -Dlog4j.configuration=logging/log4j_file_only.xml"

#nohup runs the process so that there are "no holdups" this means that if you 
#close the terminal session that started it it still runs on the server.
nohup java $VM_ARGS -cp $CPTH com.ixcode.bugsim.BugsimMain  $ARGS

