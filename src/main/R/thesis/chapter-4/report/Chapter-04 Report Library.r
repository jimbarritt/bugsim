cat("Chapter-04 Report Library (version 1.0)\n")


Chapter04Report.generateLevinSummarySection<-function(latexFile, report, eggDistribution, sectionTitle, id) {
	latexFile<-addContent(latexFile, "\\clearpage")		
	starred<-FALSE
	latexFile<-addSubSection(latexFile, sectionTitle, starred=starred)
	

	latexFile<-addSubSubSection(latexFile, "Egg Distribution Graphs")

	name<-sprintf("egg-dist-%03.0f", id)
	filename<-StandardReport.makeFigureFileName(report, name)		
	pdf(file=filename, width=8, height=8, bg="white")
		plot(eggDistribution, plotType="AGGREGATED")
	dev.off()
	
	latexFile<-addFigure(latexFile, filename, "h", 5,"Aggregated Egg Distribution", sprintf("fig:%s", name))
	
	
	name<-sprintf("scale-code-%03.0f", id)
	filename<-StandardReport.makeFigureFileName(report, name)	
	pdf(file=filename, width=8, height=9.5, bg="white")
		par(mfrow=c(3, 1))
		plot(eggDistribution, plotType="SCALE_CODE", sortScale="1M")		
		plot(eggDistribution, plotType="SCALE_CODE", sortScale="6M")
		if (eggDistribution@fieldCabbages@has48mScale) {
			plot(eggDistribution, plotType="SCALE_CODE", sortScale="48M")
		}
	dev.off()
	
	latexFile<-addFigure(latexFile, filename, "h", 7,"Detailed Egg Distribution", sprintf("fig:%s", name))
	

	latexFile<-addContent(latexFile, "\\clearpage")	
	
	
	return (latexFile)
}

#report<-report.A1VM2
Chapter04Report.generateLevinFieldBreakdownSection<-function(latexFile, report, eggDistribution, sectionTitle, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	inputSummary.df<-report@experimentPlan@iterationSummary.df
	
	fieldDistributions.list<-EggDistribution.breakdownByFactor(eggDistribution, "resourceLayoutName")
	
	if (length(fieldDistributions.list)==0) {
		stop("No Egg Distributions found!")
	}
	
	report@latexFile<-addContent(report@latexFile, "\\clearpage")		
	starred<-FALSE

	latexFile<-addSubSection(latexFile, sectionTitle, starred=starred)
	
	name<-sprintf("field-breakdown-%03.0f", id)
	filename<-StandardReport.makeFigureFileName(report, name)		
	pdf(file=filename, width=12, height=12, bg="white")
	par(mfrow=c(2,2))
	for (i.dist in fieldDistributions.list) {
		plot(i.dist, plotType="AGGREGATED",  plotTitle=sprintf("%s", i.dist@name))
	}
	dev.off()
	
	latexFile<-addFigure(latexFile, filename, "ht", 5,sectionTitle, sprintf("fig:%s", name))
	return (latexFile)
}

Chapter04Report.generateLevinFieldReplicateBreakdownSection<-function(latexFile, report, eggDistribution, sectionTitle, id) {
	fieldDistributions.list<-EggDistribution.breakdownByFactor(eggDistribution, "resourceLayoutName")
	
	latexFile<-addContent(latexFile, "\\clearpage")		
	starred<-FALSE
	
	for (dist in fieldDistributions.list) {	
		latexFile<-Chapter04Report.generateReplicateBreakDownSection(latexFile, report, dist, sprintf("%s field %s", dist@name, sectionTitle), id)
	}
	return (latexFile)
}

Chapter04Report.generateReplicateBreakDownSection<-function(latexFile, report, dist,sectionTitle, id) {
	latexFile<-addSubSection(latexFile, sectionTitle, starred=FALSE)
	
	dists<-ExperimentPlan.breakDownEggDistributionByReplicate(dist)

	name<-sprintf("Replicate-Breakdown-%s-%03.0f", dist@name, id)
	filename<-StandardReport.makeFigureFileName(report, name)		
	pdf(file=filename, width=15, height=15, bg="white")
		par(mfrow=c(3,4))
		for (subDist in dists) {
			plot(subDist, plotType="AGGREGATED")
		}	
	dev.off()

	caption<-sprintf("%s Replicate Breakdown Egg Distribution", dist@name)
	latexFile<-addFigure(latexFile, filename, "ht", 6,caption, sprintf("fig:%s", name))	
	
	latexFile<-addContent(latexFile, "\\clearpage")		
	return (latexFile)
}