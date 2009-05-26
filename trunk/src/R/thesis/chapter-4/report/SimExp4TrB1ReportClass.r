# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...



#Class definition for an IterationReplicate class

setClass("SimExp4TrB1Report", representation(
    levin06IICabbages="FieldCabbages",
	outputDir="character",
	figOutputDir="character",
	simulation.cabbages.df="data.frame",
	eggDistribution="EggDistribution"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp4TrB1Report<-function(experimentPlan) {
	instance<-new("SimExp4TrB1Report")
	
	instance@experimentPlan<-experimentPlan
	instance@levin06IICabbages<-FieldCabbages("LEVIN-II")
	
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)

	instance@latexFile<-addTitle(instance@latexFile, sprintf("Trial B1 Report (%s)", experimentPlan@experimentDirName))			
	
	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@outputDir<-sprintf("%s/output", experimentPlan@experimentDir)
	if (file.exists(instance@outputDir) == FALSE) {
		dir.create(instance@outputDir)
	}

	instance@figOutputDir<-sprintf("%s/figures", instance@outputDir)
	if (file.exists(instance@figOutputDir) == FALSE) {
		dir.create(instance@figOutputDir)
	}

	cabbages.df<-getAggregatedCabbages(instance@experimentPlan)	
	instance@simulation.cabbages.df<-IterationEggDistribution.mergeCabbages(instance@levin06IICabbages, cabbages.df)
	instance@eggDistribution<-EggDistribution(instance@experimentPlan@experimentName, instance@simulation.cabbages.df, "Simulation.Egg.Count", instance@levin06IICabbages)
	
	
	return (instance)
}

SimExp4TrB1Report.generateLevinSection<-function(instance) {
	dist<-instance@eggDistribution
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")		
	starred<-FALSE
	instance@latexFile<-addSubSection(instance@latexFile, sprintf("Trial B1 : Field comparison - Levin"), starred=starred)
	

	instance@latexFile<-addSubSubSection(instance@latexFile, "Egg Distribution Graphs")

	filename<-sprintf("%s/Trial-B1-aggregated-egg-distribution.pdf", instance@figOutputDir)
	cat("Writing TrialB1 - aggregated egg distribution figure to: ", filename, "\n")
	
	
	pdf(file=filename, width=8, height=8, bg="white")
	plot(dist, plotType, plotType="AGGREGATED")
	dev.off()
	
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 5,"Trial B1 Aggregated Egg Distribution", "fig:sim-trial-B2-aggregated-egg-dist")
	
	
	filename<-sprintf("%s/Trial-B1-detailed-egg-distribution.pdf", instance@figOutputDir)
	cat("Writing TrialB1 detailed egg distribution figure to: ", filename, "\n")
	pdf(file=filename, width=8, height=9.5, bg="white")
	par(mfrow=c(3, 1))
	plot(dist, plotType="SCALE_CODE", sortScale="1M")
	plot(dist, plotType="SCALE_CODE", sortScale="6M")

	dev.off()
	
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Trial B2 Detailed Egg Distribution", "fig:sim-trial-B2-detailed-egg-dist")
	

	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
	
	
	return (instance)
}




setMethod("generateReport", signature=c("SimExp4TrB1Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp4TrB1Report.generateLevinSection(report)

		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)




#Add a "toString" which is a "show method"
setMethod("show", "SimExp4TrB1Report", 
	function(object) {
		show(object@latexFile)

	}
)


#And a "summary" method
setMethod("summary", "SimExp4TrB1Report", 
	function(object) {
	}
)
setMethod("plot", "SimExp4TrB1Report",
	function(x, y, ...) {
	}
)


