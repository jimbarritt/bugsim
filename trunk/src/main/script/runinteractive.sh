#! /bin/bash
# To make a script executable do chmod +x
EXPERIMENT_FACT=com.ixcode.bugsim.model.experiment.experimentX.ExperimentXFactory
PROGRESS_FACT=com.ixcode.bugsim.view.experiment.experimentX.ExperimentXProgressFactory
LANDSCAPE_VIEW_FACT=com.ixcode.bugsim.view.experiment.experimentX.ExperimentXLandscapeViewFactory



VERSION=@version@ 
DISTRIBUTION=@distribution@
BUGSIM_JAR=bugsim-application-$VERSION-$DISTRIBUTION.jar
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



ARGS="interactive=true editPlan=false outputItrDetails=true exp=$EXPERIMENT_FACT lscp=$LANDSCAPE_VIEW_FACT prog=$PROGRESS_FACT"

echo
echo 
echo Executing with the following arguments :
echo
echo $ARGS
echo 
echo 
#-Dlog4j.configuration=log4j_test.xml - dont need this anymore.
VM_ARGS="-Xmx512M -Dbugsim.debug=true -Dlog4j.configuration=logging/log4j_file_only.xml"

# Specify -Dbugsim.defaultResourceLayoutFilename= if you want to overide the default file or just put the default file in the root of bugsim folder.
java $VM_ARGS -cp $CPTH com.ixcode.bugsim.BugsimMain  $ARGS



