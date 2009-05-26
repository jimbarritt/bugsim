# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...



#Class definition for an IterationReplicate class

setClass("LevinReplicateReport", representation(
    levin06IICabbages="FieldCabbages",
	simulation.cabbages.df="data.frame",
	eggDistribution="EggDistribution"	
), contains="StandardReport")


#Add a constructor:
LevinReplicateReport<-function(experimentPlan) {
	instance<-new("LevinReplicateReport")

	instance<-StandardReport.initialise(instance, experimentPlan)
	

	instance@levin06IICabbages<-FieldCabbages.LEVINII
	
	filename<-sprintf("%s-report-replicate", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)

	instance@latexFile<-addTitle(instance@latexFile, sprintf("Levin Replicate Report (%s)", experimentPlan@experimentDirName))			
	
	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)

	instance@latexFile<-StandardReport.generateExecutionTimeByLandASection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	


	
	instance@latexFile<-StandardReport.generateLongIterationSummarySection(instance@latexFile, experimentPlan, includeS=T)


	cabbages.df<-getAggregatedCabbages(instance@experimentPlan)	
	instance@simulation.cabbages.df<-IterationEggDistribution.mergeCabbages(instance@levin06IICabbages, cabbages.df)
	instance@eggDistribution<-EggDistribution(instance@experimentPlan@experimentName, instance@simulation.cabbages.df, "Simulation.Egg.Count", instance@levin06IICabbages)
	
	

	
	return (instance)
}


#filter.L<-250;filter.A=3
LevinReplicateReport.generateDescriptiveSection<-function(latexFile, report) {
	dist<-report@eggDistribution
	
	cabbages.df<-report@simulation.cabbages.df
	
	latexFile<-addContent(latexFile, "\\clearpage")
	
	latexFile<-addSection(latexFile, "Forager Success Summary", TRUE)
	
	latexFile<-LevinReplicateReport.generateForagerSuccessSummarySection(latexFile, report)
	
	id<-1
	for (filter.L in levels(cabbages.df$LF)) {
		for (filter.A in levels(cabbages.df$AF)) {		
			ss.df<-subset(cabbages.df, cabbages.df$A==filter.A)
			ss.df<-subset(ss.df, ss.df$L==filter.L)

			eggDistribution<-EggDistribution(report@experimentPlan@experimentName, ss.df, "Simulation.Egg.Count", report@levin06IICabbages)
				
			latexFile<-LevinReplicatereport.generateEggDistributionSummary(latexFile, report, eggDistribution, filter.A, filter.L, id)
			id<-id+1
		}
	}
	
	
	return (latexFile)
}

LevinReplicateReport.generateForagerSuccessSummarySection<-function(latexFile, report) {
	
	ratio.df<-report@experimentPlan@ratio.df
	
	latexFile<-addContent(latexFile, "\\renewcommand{\\arraystretch}{1.5}")
	latexFile<-addContent(latexFile, "\\setlength{\\tabcolsep}{5mm}")
	
	latexFile<-beginLongTabular(latexFile, "rrrrrrrr",  list(
		"\\textbf{R}", 	
		"\\textbf{L}", 		
		"\\textbf{A}", 	
		"\\textbf{Dead}",
		"\\textbf{Escaped}",	
		"\\textbf{EggCount}",
		"\\textbf{Success}", 
		"\\textbf{Timesteps}"
	), caption="Forager Success Summary", "tbl:forager-success-summary")
	
		
			
		for (filter.R in levels(ratio.df$RF)) {
			for (filter.L in levels(ratio.df$LF)) {
				for (filter.A in levels(ratio.df$AF)) {
					ss.df<-subset(ratio.df, ratio.df$A==filter.A)
					ss.df<-subset(ss.df, ss.df$L==filter.L)
					ss.df<-subset(ss.df, ss.df$R==filter.R)
				
					dead<-mean(as.numeric(ss.df$foragers.dead))
					escaped<-mean(as.numeric(ss.df$foragers.escaped))
					eggCount<-mean(as.numeric(ss.df$egg.total))
					success<-mean(as.numeric(ss.df$foragers.success))
					
					timesteps<-mean(as.numeric(ss.df$timesteps))
				
					latexFile<-addTableRow(latexFile, data=list(
						filter.R,
						filter.L,
						filter.A,
						sprintf("%0.0f", dead),
						sprintf("%0.0f", escaped),						
						sprintf("%0.0f", eggCount),						
						sprintf("%0.4f", success),
						sprintf("%0.0f", timesteps)									
					))
		
				}
			
			}
		}
	latexFile<-endLongTabular(latexFile)
	
	return (latexFile)
}


LevinReplicatereport.generateEggDistributionSummary<-function(latexFile, report, eggDistribution, filter.A, filter.L,  id, titlePrefix="") {
	
	
	sectionTitle<-sprintf("%sSummary Egg Distribution L=%0.0f, A=%0.1f", titlePrefix,filter.L, filter.A )	
	latexFile<-Chapter04Report.generateLevinSummarySection(latexFile, report, eggDistribution, sectionTitle, id)

	sectionTitle<-sprintf("%sRegressionBreakdown L=%0.0f, A=%0.1f",titlePrefix, filter.L, filter.A )	
	latexFile<-addBreakdownRegressionPlots(latexFile, report, eggDistribution, sectionTitle, sprintf("regression-breakdown-%03.0f", id)) 

	sectionTitle<-sprintf("%sField Breakdown L=%0.0f, A=%0.1f",titlePrefix, filter.L, filter.A )
	latexFile<-Chapter04Report.generateLevinFieldBreakdownSection(latexFile, report, eggDistribution, sectionTitle, id)
	
	sectionTitle<-sprintf("%sReplicate Breakdown L=%0.0f, A=%0.1f",titlePrefix, filter.L, filter.A )
	latexFile<-Chapter04Report.generateLevinFieldReplicateBreakdownSection(latexFile, report, eggDistribution, sectionTitle, id)
	
	return (latexFile)
}

#quartz(width=16, height=6)
#report<-report.C1
#dist<-eggDistribution
#name<-"jim"
addBreakdownRegressionPlots<-function(latexFile, report, dist, caption, name) {
	latexFile<-addSubSection(latexFile, caption, F)
	
	
	filename<-StandardReport.makeFigureFileName(report, name)	
	cat("Writing Regression Breakdown figure to: ", filename, "\n")
	pdf(file=filename, width=16, height=6, bg="white")		
		results.lm<-EggDistribution.plotLogLogBreakdownMultiplePlot(dist)
	dev.off()	
	latexFile<-addFigure(latexFile, filename, "h", 8,caption, sprintf("fig:%s", name))
	
	latexFile<-FieldDataReport.addLmStats(latexFile, results.lm, "Regression Statistics")
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	return (latexFile)
}


setMethod("generateReport", signature=c("LevinReplicateReport", "logical", "logical"),
	function(report, showPreview, clean) {		
		report@latexFile<-LevinReplicateReport.generateDescriptiveSection(report@latexFile, report)
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)




#Add a "toString" which is a "show method"
setMethod("show", "LevinReplicateReport", 
	function(object) {
		show(object@latexFile)

	}
)


#And a "summary" method
setMethod("summary", "LevinReplicateReport", 
	function(object) {
	}
)
setMethod("plot", "LevinReplicateReport",
	function(x, y, ...) {
	}
)


