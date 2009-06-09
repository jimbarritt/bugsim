# This Library has some functions which apply to all chapters for setting up working directories, 
# copying things around and producing output...
cat("Bugsim(tm) Experiment Data Managment Library (v 1.0)\n")

lib.loadLibrary("/thesis/common/time/Time Library.r")
lib.loadLibrary("/thesis/common/experiment/ReplicateClass.r")
lib.loadLibrary("/thesis/common/experiment/IterationClass.r")
lib.loadLibrary("/thesis/common/experiment/ExperimentPlanClass.r")

# Make warnings appear as they occur rather than at the end of a lot of statements
options(warn=1)#set to 2 if you want warnings as errors

# These are constants, not global variables:)
ex.experimentRootDir<-"/Users/Jim/Work/Projects/bugsim/experimental-data"
ex.experimentPublishedDir<-"published"
ex.experimentRawDir<-"raw-output"
ex.experimentRawDownloadDir<-"raw-download"


ExperimentData.getPublishedRootDir<-function() {
	return (sprintf("%s/%s", ex.experimentRootDir, ex.experimentPublishedDir))
}

ExperimentData.getRawRootDir<-function() {
	return (sprintf("%s/%s", ex.experimentRootDir, ex.experimentRawDir))
}

ExperimentData.loadPlan<-function(chapterNumber, experimentDir, experimentName, trialName, experimentNumber, fieldCabbages=NULL, groupingFactorName="RF", groupingFactorRawName="R") {	
	formattedExperimentNumber<-sprintf("%03.0f", experimentNumber)
	if (chapterNumber == "raw") {
		experimentDirName<-sprintf("%s-%s-%s-%s", experimentDir, experimentName, trialName, formattedExperimentNumber)
		experimentParentDir<-sprintf("%s/%s", ex.experimentRootDir, ex.experimentRawDir)
	} else if (chapterNumber == "raw-download") {
		experimentDirName<-sprintf("%s-%s-%s-%s", experimentDir, experimentName, trialName, formattedExperimentNumber)
		experimentParentDir<-sprintf("%s/%s", ex.experimentRootDir, ex.experimentRawDownloadDir)		
	} else {
		experimentDirName<-sprintf("%s-%s-%s-%s", experimentDir, experimentName, trialName, formattedExperimentNumber)
		experimentParentDir<-sprintf("%s/chapter-%0.0f/simulation-data", ExperimentData.getPublishedRootDir(), chapterNumber)
	}
	
	return (ExperimentPlan(experimentParentDir, experimentDirName, experimentNumber, fieldCabbages, groupingFactorName, groupingFactorRawName))			
}





