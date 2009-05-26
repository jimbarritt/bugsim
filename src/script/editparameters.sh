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

#-Dlog4j.configuration=log4j_test.xml - dont need this anymore.
VM_ARGS="-Xmx512M -Dbugsim.debug=false -Dlog4j.configuration=logging/log4j_file_only.xml"

#nohup runs the process so that there are "no holdups" this means that if you 
#close the terminal session that started it it still runs on the server.

# Specify -Dbugsim.defaultResourceLayoutFilename= if you want to overide the default file or just put the default file in the root of bugsim folder.
java $VM_ARGS -cp $CPTH com.ixcode.bugsim.view.experiment.editor.ExperimentPlanEditorDialog


