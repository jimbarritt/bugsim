# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3D1AReport", representation(
	ratio.df="data.frame"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3D1AReport<-function(experimentPlan) {
	instance<-new("SimExp3D1AReport")
	
	instance<-StandardReport.initialise(instance, experimentPlan)	
	
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)
	
	instance@latexFile<-addTitle(instance@latexFile, sprintf("Boundary Effect Report (%s)", experimentPlan@experimentDirName))



	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-StandardReport.generateIterationSummarySection(instance@latexFile, experimentPlan, includeS=T)

#	instance@ratio.df<-CalculatedLayout.aggregateRatioResults(experimentPlan)
	instance@ratio.df<-experimentPlan@ratio.df

	return (instance)
}


SimExp3D1AReport.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile

	latexFile<-SimExp3TrD1A.addStatisticsSection(latexFile, report)


	id<-1
	for (filter.A in levels(report@ratio.df$AF)) {
		for (filter.L in levels(report@ratio.df$LF)) {		
			latexFile<-addEggCountDistanceBoxPlot(latexFile, report, filter.L, filter.A, id)
			id<-id+1
		}
	}

	id<-1
	for (filter.A in levels(report@ratio.df$AF)) {
		for (filter.L in levels(report@ratio.df$LF)) {		
			latexFile<-addEggCountVariancePlot(latexFile, report, filter.L, filter.A, id)
			latexFile<-addReplicateSDBreakdownPlot(latexFile, report, filter.L, filter.A, id)
			id<-id+1
		}
	}
	
	id<-1
	for (filter.A in levels(report@ratio.df$AF)) {
		latexFile<-	addEggCountMeanRatioPlot(latexFile, report, filter.A, id)
		id<-id+1
	}
	


	id<-1
	for (filter.A in levels(report@ratio.df$AF)) {
		for (filter.L in levels(report@ratio.df$LF)) {
			latexFile<-SimExp3TrD1A.addReplicateBreakdown(latexFile, report, filter.L, filter.A, id)
			id<-id+1
		}
	}
	

	latexFile<-SimExp3TrD1A.addLayoutSection(latexFile, report)
	

	report@latexFile<-latexFile
	return (report)	
}

SimExp3TrD1A.addStatisticsSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	latexFile<-addSection(latexFile, "Statistics", TRUE)
	
	latexFile<-SimExp3TrD1A.addVarianceSection(latexFile, report)	
	
	latexFile<-SimExp3TrD1A.addANOVASubSection(latexFile, report)

	
	return (latexFile)
}
	
SimExp3TrD1A.addANOVASubSection<-function(latexFile, report) {
	title<-sprintf("One Way Anova With E as Factor")
	latexFile<-addSubSection(latexFile, title, TRUE)
	latexFile<-addContent(latexFile, "\\vspace{12pt}")
	latexFile<-addContent(latexFile, "\\noindent\\textit{We show the p-value for the one way anova - p of 1 indicates it is likely that all means are equal. $p<0.01$ less than 1\\% chance means are equal. The S-W p-value is the Shapiro-Wilk test for normality of the residuals this must not be $<0.05$ or the residuals are not normal and ANOVA cant be used. In this case we have a backup of the non-parametric Kruskall-Wallace test (K-w)}")	
	latexFile<-addContent(latexFile, "\\vspace{12pt}")


	latexFile<-beginTabular(latexFile, "rrrrrrr")
		latexFile<-addTableRow(latexFile, list(			
			"\\textbf{A}", 
			"\\textbf{L}", 
			"\\textbf{d.f.}", 			
			"\\textbf{p-value}",
			"\\textbf{S-W p-value}"	,		
			"\\textbf{K-W p-value}"	
		
		))
		latexFile<-addContent(latexFile, "\\hline")	
		
		for (filter.A in levels(report@ratio.df$AF)) {
			for (filter.L in levels(report@ratio.df$LF)) {
				latexFile<-SimExp3TrD1A.addAOVRow(latexFile, report, filter.L, filter.A)
			}
		}
	
	latexFile<-endTabular(latexFile)
	
	
	return (latexFile)
}

SimExp3TrD1A.addAOVRow<-function(latexFile, report, filter.L, filter.A) {
	ss.df<-subset(report@ratio.df, L==filter.L)
	ss.df<-subset(ss.df, A==filter.A)

	#Can't work out how to get at the other information...
	av<-aov(ratio.centre~EF, data=ss.df)	
	s<-summary(av)
	#Strange incantation to extract the p-value!
	p.value<-s[[1]]$'Pr(>F)'[1]
	df<-av$df.residual

	kr<-kruskal.test(ratio.centre~EF, data=ss.df)

	res<-shapiro.test(ss.df$ratio.centre)

	
	latexFile<-addTableRow(latexFile, list(
		sprintf("%0.1f", filter.A), 		
		sprintf("%0.0f", filter.L), 
		sprintf("(1, %0.0f)", df), 
		StandardReport.printPValue(p.value),
		StandardReport.printPValue(res$p.value),
		StandardReport.printPValue(kr$p.value)
	))
	
	
}



SimExp3TrD1A.addVarianceSection<-function(latexFile, report) {
#	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("Variance in Results")
	latexFile<-addSubSection(latexFile, title, TRUE)

	latexFile<-addContent(latexFile, "\\noindent\\textit{The stats are all based on summarising across replicates the CENTRE:CORNER ratio.}\\vspace{12pt}")	

	for (filter.A in levels(report@ratio.df$AF)) {
		for (filter.L in levels(report@ratio.df$LF)) {
			latexFile<-SimExp3TrD1A.addVarianceTable(latexFile, report, filter.L, filter.A)
		}
	}

		
	return (latexFile)
}

SimExp3TrD1A.addVarianceTable<-function(latexFile, report, filter.L, filter.A) {
	latexFile<-addContent(latexFile, "\\vspace{12pt}")	
	title<-sprintf("\\noindent\\textbf{A=%0.1f, L=%0.0f}", filter.A, filter.L)
	latexFile<-addContent(latexFile, title)	
	
	ss.df<-subset(report@ratio.df, L==filter.L)
	ss.df<-subset(ss.df, A==filter.A)
	
	stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(ss.df, xFactorName="EF" ,yFactorName="ratio.centre")
	
	latexFile<-StandardReport.addSummaryStatisticsTable(latexFile, stats.df, "E")
	return (latexFile)
}

#filter.L<-10; filter.A<-10
addEggCountVariancePlot<-function(latexFile, report, filter.L, filter.A, id) {
	ss.df<-subset(report@ratio.df, L==filter.L)
	ss.df<-subset(ss.df, A==filter.A)
	
	stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(ss.df, xFactorName="EF" ,yFactorName="ratio.centre")
	
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("Standard Deviation of Centre:Corner Ratio (L=%0.0f, A=%0.2f)", filter.L, filter.A)
	latexFile<-addSection(latexFile, title, TRUE)

	
	name<-sprintf("ccr-sd-%03d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		CalculatedLayout.plotSD(stats.df, "Egg Limit (E)")
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	return (latexFile)
}


addReplicateSDBreakdownPlot<-function(latexFile, report, filter.L, filter.A, id) {
	
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("Breakdown of SD for Centre:Corner Ratio (L=%0.0f, A=%0.2f, n=50)", filter.L, filter.A)
	latexFile<-addSection(latexFile, title, TRUE)

	
	name<-sprintf("ccr-sd-breakdown-%03d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		CalculatedLayout.plotReplicateSD(report@ratio.df, filter.L, filter.A,  sampleSize=50)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	return (latexFile)
}




addEggCountDistanceBoxPlot<-function(latexFile, report, filter.L, filter.A, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("BoxPlot of Centre:Corner Ratio (L=%0.0f, A=%0.2f)", filter.L, filter.A)
	latexFile<-addSection(latexFile, title, TRUE)

	
	name<-sprintf("ccr-box-E-%03d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		CalculatedLayout.plotBoxPlotEforLA(report@ratio.df, filter.L, filter.A)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	return (latexFile)
}



addEggCountMeanRatioPlot<-function(latexFile, report, filter.A, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("Centre:Corner Ratio L vs B (A=%0.2f)", filter.A)
	latexFile<-addSection(latexFile, title, TRUE)


	name<-sprintf("ccr-L-B-%03d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		CalculatedLayout.plotLvsEForA(report@ratio.df, filter.A)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	

	return (latexFile)
}

SimExp3TrD1A.addReplicateBreakdown<-function(latexFile, report, filter.L, filter.A, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("Replicate Breakdown (L=%0.0f, A=%0.2f)", filter.L, filter.A)
	latexFile<-addSection(latexFile, title, TRUE)


	name<-sprintf("ccr-replicates-%03d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		CalculatedLayout.plotReplicateBreakdownE(report@ratio.df, filter.L, filter.A, report@experimentPlan@replicateCount)		
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	

	return (latexFile)
}

SimExp3TrD1A.addLayoutSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Resource Layouts")
	latexFile<-addSection(latexFile, title, TRUE)
	
	ratio.df<-report@ratio.df
	levels.P<-levels(ratio.df$PF)
	levels.R<-levels(ratio.df$RF)
	levels.S<-levels(ratio.df$SF)


	id<-1
	for (i in 1:length(levels.P)) {
		for (i.R in as.numeric(levels.R)) {
			i.P<-as.numeric(levels.P[[i]])
			i.S<-as.numeric(levels.S[[i]])
			latexFile<-CalculatedLayout.addLayoutSubSection(latexFile, i.P, i.R, i.S, 4, 120, id, drawEdges=F)
			id<-id+1
		}
	}


	return (latexFile)
}



setMethod("generateReport", signature=c("SimExp3D1AReport", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3D1AReport.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)



#Add a "toString" which is a "show method"
setMethod("show", "SimExp3D1AReport", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3D1AReport", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3D1AReport",
	function(x, y, ...) {
		
	}
)




