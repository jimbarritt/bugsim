@echo off
set EXPERIMENT_FACT=com.ixcode.bugsim.model.experiment.experimentX.ExperimentXFactory
set PROGRESS_FACT=com.ixcode.bugsim.view.experiment.experimentX.ExperimentXProgressFactory
set LANDSCAPE_VIEW_FACT=com.ixcode.bugsim.view.experiment.experimentX.ExperimentXLandscapeViewFactory



set VERSION=@version@
set DISTRIBUTION=@distribution@
set BUGSIM_JAR=bugsim-application-%VERSION%-%DISTRIBUTION%.jar

set CPATH=.\%BUGSIM_JAR%
set CPATH=%CPATH%;.\lib\commons-beanutils-core.jar
set CPATH=%CPATH%;.\lib\commons-logging-api.jar
set CPATH=%CPATH%;.\lib\jcommon-1.0.0.jar
set CPATH=%CPATH%;.\lib\servlet.jar
set CPATH=%CPATH%;.\lib\xmlParserAPIs.jar
set CPATH=%CPATH%;.\lib\commons-beanutils.jar
set CPATH=%CPATH%;.\lib\commons-logging.jar
set CPATH=%CPATH%;.\lib\jfreechart-1.0.0.jar
set CPATH=%CPATH%;.\lib\xercesImpl.jar
set CPATH=%CPATH%;.\lib\commons-jxpath-1.2.jar
set CPATH=%CPATH%;.\lib\commons-math-1.1.jar
set CPATH=%CPATH%;.\lib\log4j-1.2.13.jar
set CPATH=%CPATH%;.\lib\xml-apis.jar

rem -Dlog4j.configuration=log4j_test.xml - dont need this anymore.
set VM_ARGS=-Dbugsim.debug=false -Dlog4j.configuration=logging/log4j_file_only.xml


rem Specify -Dbugsim.defaultResourceLayoutFilename= if you want to overide the default file or just put the default file in the root of bugsim folder.
java %VM_ARGS% -cp %CPATH% com.ixcode.bugsim.view.experiment.editor.ExperimentPlanEditorDialog



