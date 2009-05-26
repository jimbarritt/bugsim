# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("OptimisationReport", representation(
    fieldCabbages="FieldCabbages",
	groupingFactorName="character",
	groupingFactorRawName="character",
	fieldComparisonStats.df="data.frame",
	plotSummaryDistributionBreakdown="logical",
	zoomIteration="ANY"
), contains="StandardReport")


#Add a constructor:
OptimisationReport<-function(experimentPlan, plotSummaryDistributionBreakdown=T, zoomIteration=list()) {
	instance<-new("OptimisationReport")
	
	
	instance<-StandardReport.initialise(instance, experimentPlan)
	instance@groupingFactorName<-experimentPlan@groupingFactorName
	instance@groupingFactorRawName<-experimentPlan@groupingFactorRawName
	instance@plotSummaryDistributionBreakdown<-plotSummaryDistributionBreakdown
	
	instance@zoomIteration<-zoomIteration
	
	instance@fieldCabbages<-experimentPlan@fieldCabbages
	
	filename<-sprintf("%s-report-optimisation", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)	
	
	instance@latexFile<-addTitle(instance@latexFile, sprintf("Optimisation (%s) - %s", experimentPlan@experimentDirName, instance@fieldCabbages@fieldExperimentName))			
	
	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-StandardReport.generateExecutionTimeByLandASection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")
	
#	instance@latexFile<-StandardReport.generateLongIterationSummarySection(instance@latexFile, experimentPlan, includeS=T)	
	
	
	
	return (instance)
}

OptimisationReport.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile
	
#	latexFile<-addForagerSuccessSection(latexFile, report)

	if (length(report@zoomIteration)>0) {
		latexFile<-addZoomIterationSection(latexFile, report)
	}
	
	
	latexFile<-addComparisonStatsSection(latexFile, report)
	
	latexFile<-addBrayCurtisWireframeSection(latexFile, report)
	
	if (report@plotSummaryDistributionBreakdown) {
		latexFile<-OptimisationReport.addSummaryDistributionSection(latexFile, report)
	}

	latexFile<-addBestWorstGraphSection(latexFile, report)
	return (latexFile)
}

#filter.GROUP=5;colnames(stats.df);report<-report.C2
addBestWorstGraphSection<-function(latexFile, report) {
	stats.df<-report@experimentPlan@fieldComparisonStats.df
	
	id<-1
	for (filter.GROUP in sort(as.numeric(levels(stats.df[[report@groupingFactorName]])))) {
		ss.df<-subset(stats.df, stats.df[[report@groupingFactorRawName]]==filter.GROUP)
				
		best.rowId<-which(stats.df$mean.bc==min(ss.df$mean.bc))
		worst.rowId<-which(stats.df$mean.bc==max(ss.df$mean.bc))
		
		latexFile<-addDetailGraphSection(latexFile, report, stats.df, best.rowId, sprintf("Best fit details (%s=%0.0f) ", report@groupingFactorRawName, filter.GROUP), id)		
		id<-id+1
		
		if(worst.rowId != best.rowId) {
			latexFile<-addDetailGraphSection(latexFile, report, stats.df, worst.rowId, sprintf("Worst fit details (%s=%0.0f) ", report@groupingFactorRawName, filter.GROUP), id)		
		}
		id<-id+1
	}

	return (latexFile)
	
}

#colnames(stats.df);report<-report.D6;criteria<-zoomIteration
addZoomIterationSection<-function(latexFile, report) {
	stats.df<-report@experimentPlan@fieldComparisonStats.df
	
	criteria<-report@zoomIteration
	
	c1<-stats.df$L==criteria$L
	c2<-stats.df$A==criteria$A
	c3<-stats.df[[report@groupingFactorRawName]]==criteria[[report@groupingFactorRawName]]
	
	zoom.rowId<-which(c1 & c2 & c3)

	title<- sprintf("Zoom Graph (%s=%0.0f, L=%0.0f, k=%0.0f) ", 
						report@groupingFactorRawName, criteria[[report@groupingFactorRawName]], criteria$L, criteria$A)
	latexFile<-addDetailGraphSection(latexFile, report, stats.df, zoom.rowId, title, 1001)		
	
	
	return (latexFile)
	
}
#rowId<-best.rowId
#stats.df<-ss.df
addDetailGraphSection<-function(latexFile, report, stats.df, rowId, title, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
		
	row.df<-subset(stats.df, stats.df$result.ID==rowId)
	eggDistribution<-row.df$dist.sim[[1]]
	if (is.null(eggDistribution)) {
		stop("No egg distribution found for row ", rowId, "!\n")
	}
	latexFile<-LevinReplicatereport.generateEggDistributionSummary(latexFile, report, eggDistribution, row.df$A, row.df$L, id, titlePrefix=title);	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	return (latexFile)
}

#report<-report.C2;report<-report.D4
#stats.df<-experimentPlan.C1@fieldComparisonStats.df
#groupingFactorName<-experimentPlan.C1@groupingFactorRawName
#OptimisationReport.plotBrayCurtisWireframe(report@experimentPlan@fieldComparisonStats.df, groupingFactorName=report@groupingFactorName, groupingFactorLabel=report@roupingFactorRawName)	
addBrayCurtisWireframeSection<-function(latexFile, report) {
	stats.df<-report@experimentPlan@fieldComparisonStats.df
	
	latexFile<-addContent(latexFile, "\\clearpage")	

	caption<-"Bray-Curtis Wireframe Summary"
	
	latexFile<-addSection(latexFile, caption, starred=T)
	
	
	#latexFile<-addSection(latexFile, caption, F)
	
	name<-"bray-curtis-wireframe"
	filename<-StandardReport.makeFigureFileName(report, name)		
	pdf(file=filename, width=16, height=6, bg="white")		
		OptimisationReport.plotBrayCurtisWireframe(stats.df, groupingFactorName=report@groupingFactorName, groupingFactorLabel=report@groupingFactorRawName)	
	dev.off()	
	latexFile<-addFigure(latexFile, filename, "h", 8,caption, sprintf("fig:%s", name))
	
	
	return (latexFile)
}
addComparisonStatsSection<-function(latexFile, report) {
	stats.df<-report@experimentPlan@fieldComparisonStats.df
	
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	latexFile<-addSection(latexFile, sprintf("Field Comparison Stats (%s)", report@fieldCabbages@fieldExperimentName), starred=T)
	
	latexFile<-addContent(latexFile, "\\renewcommand{\\arraystretch}{1.5}")
	latexFile<-addContent(latexFile, "\\setlength{\\tabcolsep}{1mm}")

	latexFile<-beginTabular(latexFile, "rrr|rrr|rrr|rrr|rrr|r")		
	latexFile<-addContent(latexFile, "\\multicolumn{3}{l}{\\textbf{Parameters}} & \\multicolumn{3}{l}{\\textbf{1x1m (2 d.f.)}} & \\multicolumn{3}{l}{\\textbf{6x6m 3 d.f.}} & \\multicolumn{3}{l}{\\textbf{48x48m (2 d.f.)}} & \\multicolumn{3}{l}{\\textbf{ScaleCode (5/6 d.f.)}} \\\\")
	
	latexFile<-addTableRow(latexFile, list(
			sprintf("\\textbf{%s}", report@groupingFactorRawName), 
			"\\textbf{L}", 
			"\\textbf{A}", 
			"\\textbf{B-C}", 
			"$\\mathbf{\\chi^2}$", 
			"\\textbf{(p)}", 
			"\\textbf{B-C}",  
			"$\\mathbf{\\chi^2}$", 
			"\\textbf{(p)}", 
			"\\textbf{B-C}", 
			"$\\mathbf{\\chi^2}$", 
			"\\textbf{(p)}", 
			"\\textbf{B-C}", 
			"$\\mathbf{\\chi^2}$", 
			"\\textbf{(p)}",
			"\\textbf{mean B-C}"
			
	))

	latexFile<-addContent(latexFile, "\\hline")

	min.rowIds<-c()
	for (filter.GROUP in levels(stats.df[[report@groupingFactorName]])) {
		ss.df<-subset(stats.df, stats.df[[report@groupingFactorRawName]]==filter.GROUP)
		min.rowIds<-c(min.rowIds, which(stats.df$mean.bc==min(ss.df$mean.bc)))		
	}

	for (i in 1:length(stats.df$result.ID)) {
		i.row<-subset(stats.df, stats.df$result.ID==i)
		totalBC<-0
		
		data<-list(
				sprintf("%0.0f", i.row[[report@groupingFactorRawName]]),
				sprintf("%0.0f", i.row$L),
				sprintf("%0.1f", i.row$A),
				sprintf("%0.3f", i.row$X1m_dens.bc),
				sprintf("%0.2f", i.row$X1m_dens.chisq.statistic),
				StandardReport.printPValue(i.row$X1m_dens.chisq.p.value),				
				
				sprintf("%0.3f", i.row$X6m_dens.bc),
				sprintf("%0.2f", i.row$X6m_dens.chisq.statistic),
				StandardReport.printPValue(i.row$X6m_dens.chisq.p.value),							
		)
				
			
		if (!is.na(i.row$X48m_dens.bc)) {
			data<-collection.appendToList(data, sprintf("%0.3f", i.row$X48m_dens.bc))
			data<-collection.appendToList(data, sprintf("%0.2f", i.row$X48m_dens.chisq.statistic))
			data<-collection.appendToList(data, StandardReport.printPValue(i.row$X48m_dens.chisq.p.value))
		} else {
			data<-collection.appendToList(data, "-")			
			data<-collection.appendToList(data, "-")
			data<-collection.appendToList(data, "-")
			meanBC<-totalBC/3
		}
		
		data<-collection.appendToList(data, sprintf("%0.3f", i.row$X3.scale.code.bc))
		data<-collection.appendToList(data,sprintf("%0.2f", i.row$X3.scale.code.chisq.statistic))
		data<-collection.appendToList(data, StandardReport.printPValue(i.row$X3.scale.code.chisq.p.value))
		
		if (i%in%min.rowIds) {
			data<-collection.appendToList(data, sprintf("\\textbf{\\color{blue}{%0.4f}}", i.row$mean.bc))
			
		} else {
			data<-collection.appendToList(data, sprintf("%0.4f", i.row$mean.bc))
		}
		
		
		if (i%in%min.rowIds) {
			latexFile<-addContent(latexFile, "\\hline")
		}
		
		latexFile<-addTableRow(latexFile, data)
		
		if (i%in%min.rowIds) {
			latexFile<-addContent(latexFile, "\\hline")
		}
		
	}

	latexFile<-addContent(latexFile, "\\hline")
	
	latexFile<-endTabular(latexFile)



	return (latexFile)
}

addForagerSuccessSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	

	caption<-"Forager Success Summary"
	
	latexFile<-addSection(latexFile, caption, starred=T)
	
	
	#latexFile<-addSection(latexFile, caption, F)
	
	name<-"forager-success"
	filename<-StandardReport.makeFigureFileName(report, name)		
	pdf(file=filename, width=16, height=6, bg="white")		
		results.lm<-OptimisationReport.plotForagerSuccessForRvsAandL(report, report@experimentPlan@ratio.df)
	dev.off()	
	latexFile<-addFigure(latexFile, filename, "h", 8,caption, sprintf("fig:%s", name))
		
	latexFile<-addContent(latexFile, "\\clearpage")	
	latexFile<-LevinReplicateReport.generateForagerSuccessSummarySection(latexFile, report)
		
	latexFile<-addContent(latexFile, "\\clearpage")	
	return (latexFile)
}




OptimisationReport.plotForagerSuccessForRvsAandL<-function(report, ratio.df) {

	layout.mx<-rbind(c(1,2, 3, 4),
	 				 c(5, 5, 5, 5))
	layout(layout.mx, heights=c(93, 7), widths=c(3, 33, 33, 33))
	par(mar=c(0,0,0,0))
	plot.new()
	mtext(side=2, line=-3, "Eggs Per Forager", cex=1.5)

	graphIds<-c("a", "b", "c")
	first<-T
	id<-1
	for (filter.GROUP in levels(ratio.df[[report@groupingFactorName]])) {
		par(mar=c(5, 5, 5, 5))
		ss.df<-subset(ratio.df, ratio.df[[report@groupingFactorRawName]]==filter.GROUP)
		stats.list<-SummaryStatistics.createSummaryStatistics(ss.df, "AF" , "LF", "foragers.success")

		xLim<-range(ratio.df$L)
		yLim<-range(ratio.df$foragers.success)
	
		if (first) {
			legends<-levels(ratio.df$AF)
		} else {
			legends<-c()
		}
		first<-F
		SummaryStatistics.plotManyResults(stats.list, plotAxes=F,xLim=xLim, yLim=c(0, yLim[2]+(yLim[2]*0.02)),legendList=legends, 
										legendFormatString="k=%s",legendPos="topright", noDecoration=T, plotErr=T)
		axis(1, cex.axis=1.5)
		axis(2, cex.axis=1.5)

		title<-sprintf("%s) R=%0.0f", graphIds[id], filter.R)
		title(main=title, cex.main=1.5)
		id<-id+1
	}
	par(mar=c(0,0,0,0))
	plot.new()
	mtext(side=1, line=-3, "Step Length (L)", cex=1.5)
}


OptimisationReport.addSummaryDistributionSection<-function(latexFile, report) {
	stats.df<-report@experimentPlan@fieldComparisonStats.df
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	id<-1
	for (filter.GROUP in sort(as.numeric(levels(stats.df[[report@groupingFactorName]])))) {
		latexFile<-addSummaryDistributionGraph(latexFile, report, filter.GROUP, stats.df, id)
		id<-id+1
	}
	
	return (latexFile)
	
}

addSummaryDistributionGraph<-function(latexFile, report, filter.GROUP, stats.df, id) {

	caption<-sprintf("Aggregated Egg Distribution Summary (%s=%0.0f)",report@groupingFactorRawName, filter.GROUP) 
	
#	latexFile<-addSubSection(latexFile, caption, starred=T)
	
	
	#latexFile<-addSection(latexFile, caption, F)
	
	name<-sprintf("egg-dist-L-A-%03.0f", id)
	filename<-StandardReport.makeFigureFileName(report, name)		
	pdf(file=filename, width=12, height=12, bg="white")		
		results.lm<-OptimisationReport.plotSummaryDistributionsByLandAForR(report, stats.df, filter.GROUP)
	dev.off()	
	latexFile<-addFigure(latexFile, filename, "h", 8,caption, sprintf("fig:%s", name))
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	return (latexFile)
}

OptimisationReport.plotSummaryDistributionsByLandAForR<-function(report, stats.df, filter.GROUP) {
	ss.df<-subset(stats.df, stats.df[[report@groupingFactorRawName]]==filter.GROUP)
	ss.df$LF<-as.factor(ss.df$L)
	ss.df$AF<-as.factor(ss.df$A)
	levels.L<-sort(as.numeric(levels(ss.df$LF)))
	levels.A<-sort(as.numeric(levels(ss.df$AF)))
	

	layout.mx<-rbind(c( 1, 13, 14, 15,  1),
					 c( 2,  4,  5,  6, 16),
					 c( 2,  7,  8,  9, 17),
					 c( 2, 10, 11, 12, 18),
					 c( 3,  3,  3,  3,  3)
					)
	layout(layout.mx,heights=c(5, 30, 30, 30, 5), widths=c(5, 30, 30, 30, 5))
#	layout.show(13)
	par(mar=c(0, 0, 0, 0))
	plot.new() # 1
	plot.new() # 2
	mtext(side=2, line=-3, cex=1.5, EggDistribution.eggsPerPlantErrCaption())
	plot.new() # 3
	mtext(side=1, line=-3, cex=1.5, "Plant Density")
	
	

	
	first<-T
	for (filter.L in levels.L) {
		for (filter.A in levels.A) {
			
			ss.dist.df<-subset(ss.df, ss.df$L==filter.L)
			ss.dist.df<-subset(ss.dist.df, ss.dist.df$A==filter.A)
			
			dist.sim<-ss.dist.df$dist.sim[[1]]
			EggDistribution.plotAggregatedDistribution(dist.sim, plotAxes=F, plotLegend=first)
		
			first<-F
		}
	}
	par(mar=c(0, 0, 0, 0))
	plot.new() # 13
	mtext(side=3, line=-4, cex=1.5, sprintf("k=%0.1f", levels.A[[1]]))
	plot.new() # 14
	mtext(side=3, line=-4, cex=1.5, sprintf("k=%0.1f", levels.A[[2]]))
	plot.new() # 15
	mtext(side=3, line=-4, cex=1.5, sprintf("k=%0.1f", levels.A[[3]]))

	plot.new() # 16
	mtext(side=4, line=-4, cex=1.5, sprintf("L=%0.0f", levels.L[[1]]))
	plot.new() # 17
	mtext(side=4, line=-4, cex=1.5, sprintf("L=%0.0f", levels.L[[2]]))
	plot.new() # 18
	mtext(side=4, line=-4, cex=1.5, sprintf("L=%0.0f", levels.L[[3]]))
}

setMethod("generateReport", signature=c("OptimisationReport", "logical", "logical"),
	function(report, showPreview, clean) {		
		report@latexFile<-OptimisationReport.generateDescriptiveSection(report)

		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)

#report<-report.exp4.C1
#OptimisationReport.plotBrayCurtisWireframe(stats.df=report@experimentPlan@fieldComparisonStats.df, groupingFactorName=report@groupingFactorName, groupingFactorLabel=report@groupingFactorRawName)	
OptimisationReport.plotBrayCurtisWireframe<-function(stats.df, groupingFactorName, groupingFactorLabel) {
	ss.df<-stats.df
	A<-as.numeric(ss.df$A)
	L<-as.numeric(ss.df$L)
	R<-as.numeric(ss.df[[groupingFactorLabel]])
	bc<-ss.df$mean.bc

	x.at<-as.numeric(levels(as.factor(A)))
	y.at<-as.numeric(levels(as.factor(L)))

	if (y.at[2]==1000) { ## Yeah its madness but dont have time to work anything better out.
		y.at<-c(y.at[1], y.at[3])
	}
	# z.at<-SummaryStatistics.simpleYat(c(min(bc), max(bc)))
	z.at<-c(0.05, 0.2, 0.4)
	cex.axes<-0.8
	scales<-list(	arrows = FALSE,
					x=list(
						cex=cex.axes,
						at=x.at

					),
					y=list(
						cex=cex.axes,
						at=y.at,

				
					),
					z=list(
						cex=cex.axes,
						arrows=F,
						at=c(z.at[1], as.numeric(z.at[3])),
						labels=c(sprintf("%0.2f    ", z.at[1]), sprintf("%0.2f    ", z.at[3]))
					)							
	)


	fontsize<-trellis.par.get("fontsize")
	fontsize$text<-18
	trellis.par.set("fontsize", fontsize)
	
	layout.widths<-trellis.par.get("layout.widths")
	layout.widths$left.padding=1
	trellis.par.set("layout.widths", layout.widths)

	layout.heights<-trellis.par.get("layout.heights")
	layout.heights$top.padding<-5
	trellis.par.set("layout.heights", layout.heights)

	layout.widths<-trellis.par.get("layout.widths")
	layout.widths$right.padding<-5
	trellis.par.set("layout.widths", layout.widths)
	
	xLim<-c(min(A)-(max(A)*0.1), max(A)+(max(A)*0.1))
	yLim<-c(min(L)-(max(L)*0.1), max(L)+(max(L)*0.1))
	#zLim<-c(min(bc)-(max(bc)*0.1), max(bc)+(max(bc)*0.1))
	zLim<-c(0, 0.45)
	
	key.at<-seq(from=0.05, to=0.4, by=0.025)
	regions<-rainbow(length(key.at), start=0, end=0.95, s=.6)
	print(wireframe(bc~A*L|R,drape=T,layout=c(3, 1), at=key.at,col.regions=regions,
				scales=scales, colorkey=T, zlab="B-C", xlab="   k", 
				ylab="L          ",ylim=yLim, 
				xlim=xLim, zlim=zLim,zoom=0.85))

	yPos<-421
	ltext(190, yPos, sprintf("a) %s=%0.0f", groupingFactorLabel, levels(as.factor(R))[[1]]), cex=1.5)
	ltext(570, yPos, sprintf("b) %s=%0.0f", groupingFactorLabel, levels(as.factor(R))[[2]]), cex=1.5)
	ltext(950, yPos, sprintf("c) %s=%0.0f", groupingFactorLabel, levels(as.factor(R))[[3]]), cex=1.5)
	
	
}





#Add a "toString" which is a "show method"
setMethod("show", "OptimisationReport", 
	function(object) {
		show(object@latexFile)

	}
)


#And a "summary" method
setMethod("summary", "OptimisationReport", 
	function(object) {
	}
)
setMethod("plot", "OptimisationReport",
	function(x, y, ...) {
	}
)


