# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3D1Report", representation(
	ratio.df="data.frame",
	zoom="logical",
	zoomYLim="vector"

), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3D1Report<-function(experimentPlan, zoom=F, zoomYLim=c()) {
	instance<-new("SimExp3D1Report")
	

	instance@zoom<-zoom
	instance@zoomYLim<-zoomYLim

	
	instance<-StandardReport.initialise(instance, experimentPlan)	
	
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)
	
	instance@latexFile<-addTitle(instance@latexFile, sprintf("Boundary Effect Report (%s)", experimentPlan@experimentDirName))



	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-StandardReport.generateExecutionTimeByLandASection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
	
	instance@latexFile<-StandardReport.generateLongIterationSummarySection(instance@latexFile, experimentPlan, includeS=T)

	instance@ratio.df<-experimentPlan@ratio.df

	return (instance)
}


SimExp3D1Report.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile

	latexFile<-addStatisticsSection(latexFile, report)


	id<-1
	for (filter.A in levels(report@ratio.df$AF)) {
		for (filter.L in levels(report@ratio.df$LF)) {		
			latexFile<-addReleaseDistanceBoxPlot(latexFile, report, filter.L, filter.A, id)
			id<-id+1
		}
	}
	
	id<-1
	for (filter.A in levels(report@ratio.df$AF)) {
		latexFile<-	addReleaseDistanceMeanRatioPlot(latexFile, report, filter.A, id)
		id<-id+1
	}
	


	id<-1
	for (filter.A in levels(report@ratio.df$AF)) {
		for (filter.L in levels(report@ratio.df$LF)) {
			latexFile<-addReplicateBreakdown(latexFile, report, filter.L, filter.A, id)
			id<-id+1
		}
	}
	

	latexFile<-addLayoutSection(latexFile, report)
	

	report@latexFile<-latexFile
	return (report)	
}

addStatisticsSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	latexFile<-addSection(latexFile, "Statistics", TRUE)
	title<-sprintf("One Way Anova With B as Factor")
	latexFile<-addSubSection(latexFile, title, TRUE)
	latexFile<-addContent(latexFile, "\\vspace{12pt}")
	latexFile<-addContent(latexFile, "\\noindent\\textit{We show the p-value for the one way anova - p of 1 indicates it is likely that all means are equal. $p<0.01$ less than 1\\% chance means are equal. The S-W p-value is the Shapiro-Wilk test for normality of the residuals this must not be $<0.05$ or the residuals are not normal and ANOVA cant be used. In this case we have a backup of the non-parametric Kruskall-Wallace test (K-w)}")	
	latexFile<-addContent(latexFile, "\\vspace{12pt}")
	latexFile<-addContent(latexFile, "\\begin{footnotesize}")

	latexFile<-beginTabular(latexFile, "rrrrrrrr")
		latexFile<-addTableRow(latexFile, list(						
			"\\textbf{A}", 
			"\\textbf{L}", 
			"\\textbf{d.f.}", 			
			"\\textbf{p-value}",
			"\\textbf{S-W p-value}",			
			"\\textbf{K-W}",
			"\\textbf{K-W d.f}",
			"\\textbf{K-W p-value}"			
		
		))
		latexFile<-addContent(latexFile, "\\hline")	
		
		for (filter.A in levels(report@ratio.df$AF)) {
			for (filter.L in levels(report@ratio.df$LF)) {
				latexFile<-addAOVRow(latexFile, report, filter.L, filter.A)
			}
		}
	
	latexFile<-endTabular(latexFile)
	
	latexFile<-addContent(latexFile, "\\end{footnotesize}")
	
	latexFile<-addVarianceSection(latexFile, report)

	
	return (latexFile)
}

#report<-report.D1F;filter.L<-80;filter.A=8.8
addAOVRow<-function(latexFile, report, filter.L, filter.A) {
	ss.df<-subset(report@ratio.df, report@ratio.df$L==filter.L)
	ss.df<-subset(ss.df, ss.df$A==filter.A)
	
	if (length(levels(ss.df$BF))>1) { 
		#Can't work out how to get at the other information...
		av<-aov(ratio.centre~BF, data=ss.df)	
		s<-summary(av)
		#Strange incantation to extract the p-value!
		p.value<-s[[1]]$'Pr(>F)'[1]
		df<-av$df.residual

		kr<-kruskal.test(ratio.centre~BF, data=ss.df)

		resid<-av$residuals
		
		if (length(resid) >3) {
			res<-shapiro.test(resid)
		} else {
			cat("Cannot perform Shapiro-Wilk Test - only ", length(resid) , " observations\n")
			res<-NA
		}

	
		latexFile<-addTableRow(latexFile, list(
			sprintf("%0.1f", filter.A), 		
			sprintf("%0.0f", filter.L), 
			sprintf("(1, %0.0f)", df), 
			StandardReport.printPValue(p.value),
			StandardReport.printPValue(res$p.value),
			sprintf("(%0.2f)", kr$statistic),
			sprintf("(%0.0f)", kr$parameter[[1]]),					
			StandardReport.printPValue(kr$p.value)
		))
	} else {
		cat("Only one level in Release distance, cannot calculate anova.\n")
	}
	return(latexFile)	
	
}

addVarianceSection<-function(latexFile, report) {
#	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("Variance in Results")
	latexFile<-addSubSection(latexFile, title, TRUE)

	latexFile<-addContent(latexFile, "\\noindent\\textit{The stats are all based on summarising across replicates the CENTRE:CORNER ratio.}\\vspace{12pt}")	

	for (filter.A in levels(report@ratio.df$AF)) {
		for (filter.L in levels(report@ratio.df$LF)) {
			latexFile<-addVarianceTable(latexFile, report, filter.L, filter.A)
		}
	}

		
	return (latexFile)
}

addVarianceTable<-function(latexFile, report, filter.L, filter.A) {
	latexFile<-addContent(latexFile, "\\vspace{12pt}")	
	title<-sprintf("\\noindent\\textbf{A=%0.1f, L=%0.0f}", filter.A, filter.L)
	latexFile<-addContent(latexFile, title)	
	
	ss.df<-subset(report@ratio.df, report@ratio.df$L==filter.L)
	ss.df<-subset(ss.df, ss.df$A==filter.A)
	
	stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(ss.df, xFactorName="BF" ,yFactorName="ratio.centre")
	
	latexFile<-StandardReport.addSummaryStatisticsTable(latexFile, stats.df, "B")
	return (latexFile)
}



addReleaseDistanceBoxPlot<-function(latexFile, report, filter.L, filter.A, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("BoxPlot of Centre:Corner Ratio (L=%0.0f, A=%0.2f)", filter.L, filter.A)
	latexFile<-addSection(latexFile, title, TRUE)

	
	name<-sprintf("ccr-box-B-%03d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		CalculatedLayout.plotBoxPlotBforLA(report@ratio.df, filter.L, filter.A)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	return (latexFile)
}


#report<-report.D1B, filter.A<-8.8; par(mfrow=c(1, 1))
addReleaseDistanceMeanRatioPlot<-function(latexFile, report, filter.A, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("Centre:Corner Ratio L vs B (A=%0.2f)", filter.A)
	latexFile<-addSection(latexFile, title, TRUE)


	name<-sprintf("ccr-L-B-%03d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=8, bg="white")	
		CalculatedLayout.plotLvsBForA(report@ratio.df, filter.A, zoom=report@zoom, zoomYLim=report@zoomYLim)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	

	return (latexFile)
}

addReplicateBreakdown<-function(latexFile, report, filter.L, filter.A, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	title<-sprintf("Replicate Breakdown (L=%0.0f, A=%0.2f)", filter.L, filter.A)
	latexFile<-addSection(latexFile, title, TRUE)


	name<-sprintf("ccr-replicates-%03d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		CalculatedLayout.plotReplicateBreakdown(report@ratio.df, filter.L, filter.A, report@experimentPlan@replicateCount)		
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	

	return (latexFile)
}

addLayoutSection<-function(latexFile, report) {
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
			latexFile<-CalculatedLayout.addLayoutSubSection(report, latexFile, i.P, i.R, i.S, 4, 120, id, drawEdges=F)
			id<-id+1
		}
	}


	return (latexFile)
}



setMethod("generateReport", signature=c("SimExp3D1Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3D1Report.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)



#Add a "toString" which is a "show method"
setMethod("show", "SimExp3D1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3D1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3D1Report",
	function(x, y, ...) {
		
	}
)


#Generate specific graph for thesis which combines D1C, D1C2 and D1D
SimExp3D1Report.createCombinationGraph<-function(report.D1C,report.D1C2, report.D1D) {
	zoomYLim=c(0.4, 0.46)
	ratio.C1.df<-report.D1C@ratio.df
#	ratio.C1.df<-merge(report.D1C@ratio.df, report.D1C2@ratio.df, all=T)
#	ratio.C1.df$BF<-as.factor(ratio.C1.df$B)
	ratio.D1.df<-report.D1D@ratio.df

	ss.C1.df<-subset(ratio.C1.df, A==10)
	ss.D1.df<-subset(ratio.D1.df, A==10)

	c1.list<-SummaryStatistics.createSummaryStatistics(ss.C1.df,groupingFactorName="LF", xFactorName="BF" ,yFactorName="ratio.centre")
	d1.list<-SummaryStatistics.createSummaryStatistics(ss.D1.df,groupingFactorName="LF", xFactorName="BF" ,yFactorName="ratio.centre")

	stats.list<-list(c1.list[[1]], d1.list[[1]])

	par(mar=c(7, 7, 5, 2))


	yLim<-c(0.4, 0.46)

	i.first.df<-stats.list[[1]]

	par(mar=c(7, 7, 2, 2))
	plotFirstDataFrame(i.first.df, yLim, col="blue")


	if (length(stats.list)>1) {
		lineColors <- c("red", "green", "brown", "purple", "yellow", "purple", "grey")
		pointSymbols<-c(15, 17, 24, 22, 23, 15, 24, 25)

		legends<-c("Trial C  ", "Trial D")
	
		plotRemainingDataFrames(stats.list[2:length(stats.list)], lineColors, pointSymbols)
	
		legend("topleft",legend=legends,x.intersp=0.6, lty=1,lwd=1.5,
		 horiz=T,inset=0.03, col=c("blue", lineColors), pch=c(19, pointSymbols), bty="o", pt.cex=2, cex=1.5)
	}

	mtext(side=1, expression("Release Distance (B)"), cex=1.8, line=4)
	mtext(side=2, CalculatedLayout.ratioCaption(), cex=1.8, line=4)	
}






