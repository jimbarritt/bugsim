# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...



#Class definition for an IterationReplicate class

setClass("SimExp4TrB2Report", representation(
    kaitoke04Cabbages="FieldCabbages",
	outputDir="character",
	figOutputDir="character"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp4TrB2Report<-function(experimentPlan) {
	instance<-new("SimExp4TrB2Report")
	
	instance@experimentPlan<-experimentPlan
	instance@kaitoke04Cabbages<-FieldCabbages("KAITOKE-04")
	
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)

	instance@latexFile<-addTitle(instance@latexFile, sprintf("Trial B2 Report (%s)", experimentPlan@experimentDirName))			
	
	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@outputDir<-sprintf("%s/output", experimentPlan@experimentDir)
	if (file.exists(instance@outputDir) == FALSE) {
		dir.create(instance@outputDir)
	}

	instance@figOutputDir<-sprintf("%s/figures", instance@outputDir)
	if (file.exists(instance@figOutputDir) == FALSE) {
		dir.create(instance@figOutputDir)
	}
	
	
	return (instance)
}


SimExp4TrB2Report.generateKaitokeSection<-function(instance) {
	#Need to make it deal with multiple iterations!
	iterationNumber<-1
	iteration<-getIteration(instance@experimentPlan, iterationNumber)
	replicate<-getReplicate(iteration, 1)
	eggDist<-IterationEggDistribution(instance@kaitoke04Cabbages, iteration, iterationNumber)

	dist<-eggDist@eggDistribution
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")		
	starred<-FALSE
	instance@latexFile<-addSubSection(instance@latexFile, sprintf("Trial B2 : Iteration %s", iterationNumber), starred=starred)
	
	
	instance@latexFile<-addSubSubSection(instance@latexFile, "Egg Distribution Graphs")

	filename<-sprintf("%s/Trial-B2-aggregated-egg-distribution.pdf", instance@figOutputDir)
	cat("Writing TrialB1 - aggregated egg distribution figure to: ", filename, "\n")
	
	
	pdf(file=filename, width=8, height=8, bg="white")
	plot(dist, plotType, plotType="AGGREGATED")
	dev.off()
	
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 5,"Trial B2 Aggregated Egg Distribution", "fig:sim-trial-B2-aggregated-egg-dist")
	
	
	filename<-sprintf("%s/Trial-B2-detailed-egg-distribution.pdf", instance@figOutputDir)
	cat("Writing TrialB2 detailed egg distribution figure to: ", filename, "\n")
	pdf(file=filename, width=8, height=9.5, bg="white")
	par(mfrow=c(3, 1))
	plot(dist, plotType="SCALE_CODE", sortScale="1M")
	plot(dist, plotType="SCALE_CODE", sortScale="6M")
	plot(dist, plotType="SCALE_CODE", sortScale="48M")
	dev.off()
	
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Trial B2 Detailed Egg Distribution", "fig:sim-trial-B2-detailed-egg-dist")
	

	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
	return (instance)
}


setMethod("generateReport", signature=c("SimExp4TrB2Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp4TrB2Report.generateKaitokeSection(report)

		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)




#Add a "toString" which is a "show method"
setMethod("show", "SimExp4TrB2Report", 
	function(object) {
		show(object@latexFile)

	}
)


#And a "summary" method
setMethod("summary", "SimExp4TrB2Report", 
	function(object) {
	}
)
setMethod("plot", "SimExp4TrB2Report",
	function(x, y, ...) {
	}
)


