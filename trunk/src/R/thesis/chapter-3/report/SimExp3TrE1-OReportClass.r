# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3E1OReport", representation(
	ratio.df="data.frame"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3E1OReport<-function(experimentPlan) {
	instance<-new("SimExp3E1OReport")
	
	instance<-StandardReport.initialise(instance, experimentPlan)	
	
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)
	
	instance@latexFile<-addTitle(instance@latexFile, sprintf("CORNER:CENTRE Ratio Report (%s)", experimentPlan@experimentDirName))



	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-StandardReport.generateIterationSummarySection(instance@latexFile, experimentPlan, includeS=T)

	instance@ratio.df<-CalculatedLayout.aggregateRatioResults(experimentPlan)

	return (instance)
}


SimExp3E1OReport.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile
	

	report@latexFile<-latexFile
	return (report)	
}



setMethod("generateReport", signature=c("SimExp3E1OReport", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3E1OReport.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)



#Add a "toString" which is a "show method"
setMethod("show", "SimExp3E1OReport", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3E1OReport", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3E1OReport",
	function(x, y, ...) {
		
	}
)




