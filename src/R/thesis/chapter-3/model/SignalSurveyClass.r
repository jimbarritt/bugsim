
#Class definition for a Signal Surface class...


setClass("SignalSurvey", representation(
	iterationNumber="numeric",
	survey.df="data.frame",
	surfaceCount="numeric",
	surfaces="list"
))




#Add a constructor:
SignalSurvey<-function(experimentPlan, iterationNumber) {
	instance<-new("SignalSurvey")
	
	instance@iterationNumber<-iterationNumber
	instance@survey.df<-SignalSurvey.loadSurvey(experimentPlan, iterationNumber)
	instance@surfaceCount<-length(levels(instance@survey.df$signalSurfaceName))
	
	instance@surfaces<-SignalSurvey.loadSurfaces(instance@survey.df, experimentPlan, iterationNumber)
	
	return (instance)
}


#Add a "toString" which is a "show method"
setMethod("show", "SignalSurvey", 
	function(object) {
		cat("Signal Survey (Iteration  : ", sprintf("%03.0f", object@iterationNumber), ")\n")
		cat("surfaceCount  : ", object@surfaceCount, "\n")
		cat("surfaces      :\n")
		show(object@surfaces)
	}
)



#And a "summary" method
setMethod("summary", "SignalSurvey", 
	function(object) {
#		cat("Experiment      : ", object@experimentName, " (", object@directoryName, ")\n")
	}
)


SignalSurvey.loadSurvey<-function(experimentPlan, iterationNumber) {	
	surveyFileName<-sprintf("%s/Iteration-%03.0f/Iteration-signal-survey.csv", experimentPlan@experimentDir, iterationNumber)	
#	cat(sprintf("Reading survey: %s", surveyFileName), "\n")
	survey.df <- read.csv(surveyFileName)
	
	return (survey.df)
}

SignalSurvey.loadSurfaces<-function(survey.df, experimentPlan, iterationNumber) {
	surfaceNameF<-survey.df$signalSurfaceName
	surfaceNameLevels<-levels(surfaceNameF)

	surfaces<-list()
	for (i in 1:length(surfaceNameLevels)) {
		surfaceName<-surfaceNameLevels[[i]]
	#	cat("Loading surface '", surfaceName , "' ...\n")
		surface.df<-subset(survey.df, signalSurfaceName==surfaceName)
		surface<-SignalSurface(surfaceName,surface.df, experimentPlan, iterationNumber)
		surfaces<-collection.appendToList(surfaces, surface)
	}
	return (surfaces)
	
}

setGeneric("getSurface", 
	function(survey, index) {
		standardGeneric("getSurface")
	}
)

setMethod("getSurface", signature=c("SignalSurvey", "numeric"),
	function(survey, index) {
		return (survey@surfaces[[index]])
	}
)

