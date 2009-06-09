# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3E1Report", representation(
	ratio.df="data.frame",
	includeEdges="logical",
	scaleSize="numeric"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3E1Report<-function(experimentPlan,scaleSize=600, includeEdges=F) {
	instance<-new("SimExp3E1Report")
	
	instance<-StandardReport.initialise(instance, experimentPlan)	
	
	
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)
	
	instance@latexFile<-addTitle(instance@latexFile, sprintf("Edge Effect Report (%s)", experimentPlan@experimentDirName))



	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-StandardReport.generateLongIterationSummarySection(instance@latexFile, experimentPlan, includeS=T)

	instance@ratio.df<-experimentPlan@ratio.df
	
	instance@includeEdges=includeEdges
	instance@scaleSize=scaleSize
	return (instance)
}


SimExp3E1Report.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile

	latexFile<-SimExp3E1Report.addLayoutSection(latexFile, report)

	latexFile<-SimExp3E1Report.addSignalSurfaceSection(latexFile, report)

	latexFile<-SimExp3E1Report.addSinuosityTableSection(latexFile, report)
	
	latexFile<-SimExp3E1Report.addRelativeSpacingTableSection(latexFile, report)
	
	latexFile<-SimExp3E1Report.addDetailedGraphs(latexFile, report)
	
	report@latexFile<-latexFile
	return (report)	
}

SimExp3E1Report.addDetailedGraphs<-function(latexFile, report) {
	id<-1
	for (i.G in levels(report@ratio.df$GF)) {
		
		
		latexFile<-SimExp3E1Report.addCornerCentreEdgeRatioSection(latexFile, report, i.G, id)

		latexFile<-SimExp3E1Report.addRatioVsLandASummarySection(latexFile, report, i.G, id)

		
#		latexFile<-SimExp3E1Report.addRatioVsLayoutSection(latexFile, report, i.G, id)

		latexFile<-SimExp3E1Report.addRatioVsLayoutSinuositySection(latexFile, report, i.G, id)

		latexFile<-SimExp3E1Report.addRatioVsSeparationSummarySection(latexFile, report, i.G, id)
				
#		latexFile<-SimExp3E1Report.addRatioVsSeparationSection(latexFile, report, i.G, id)
		

		latexFile<-SimExp3E1Report.addRatioVsRadiusAndPatchSizeSection(latexFile, report, i.G, id)
		
		latexFile<-SimExp3E1Report.addSuccessVsAreaSummarySection(latexFile, report, i.G, id)

		latexFile<-SimExp3E1Report.addSuccessVsLandASummarySection(latexFile, report, i.G, id)
		
#		latexFile<-SimExp3E1Report.addSuccessVsLayoutSection(latexFile, report, i.G, id)


		latexFile<-SimExp3E1Report.addSuccessVsLayoutSinuositySection(latexFile, report, i.G, id)

		latexFile<-SimExp3E1Report.addSuccessVsRadiusAndPatchSizeSection(latexFile, report, i.G, id)
		
#		latexFile<-SimExp3E1Report.addSuccessVsAreaAndPatchSizeSection(latexFile, report, i.G, id)
		
		
		
		id<-id+1
	}
	return (latexFile)
}



SimExp3E1Report.addSinuosityTableSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	

	title<-sprintf("Sinuosity Table")
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-beginTabular(latexFile, "rrr")
	
	latexFile<-addTableRow(latexFile	, list(
			"\\textbf{Sinuosity}", 
			"\\textbf{A}", 
			"\\textbf{L}"
	))
	
	latexFile<-addContent(latexFile, "\\hline")	
	
	for (i.S in levels(report@ratio.df$SINF)) {		
		ss.df<-subset(report@ratio.df, report@ratio.df$SINF==i.S)
		A.f<-as.factor(ss.df$A)
		L.f<-as.factor(ss.df$L)
		
		latexFile<-addTableRow(latexFile, list(
				sprintf("%0.3f", i.S),
				collection.buildListString(levels(A.f), " : "),
				collection.buildListString(levels(L.f), " : ")
		))
		
	}
	
	latexFile<-endTabular(latexFile)
	
}

SimExp3E1Report.addRelativeSpacingTableSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	

	title<-sprintf("Relative Spacing Table")
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-beginTabular(latexFile, "rrrr")
	

	latexFile<-addTableRow(latexFile	, list(
			"$\\mathbf{I_R}$", 
			"\\textbf{R}", 
			"\\textbf{P}",
			"\\textbf{S}"
	))
	
	latexFile<-addContent(latexFile, "\\hline")	
	
	report@ratio.df$REL.SF<-as.factor(report@ratio.df$REL.S)
	for (i.S in levels(report@ratio.df$REL.SF)) {		
		ss.df<-subset(report@ratio.df, report@ratio.df$REL.SF==i.S)
		S.f<-as.factor(ss.df$S)
		R.f<-as.factor(ss.df$R)
		P.f<-as.factor(ss.df$P)
		
		latexFile<-addTableRow(latexFile, list(
				sprintf("%0.3f", i.S),
				collection.buildListString(levels(R.f), " : "),
				collection.buildListString(levels(P.f), " : "),
				collection.buildListString(levels(S.f), " : ")
		))
		
	}
	
	latexFile<-endTabular(latexFile)
	
}


SimExp3E1Report.addLayoutSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Resource Layouts")
	latexFile<-addSection(latexFile, title, TRUE)

	name<-sprintf("ccr-layout-summary")
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		SimExp3E1Report.plotLayoutSummary(report)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}

#report<-report.E1B;quartz(width=10, height=10);i.R<-50
SimExp3E1Report.plotLayoutSummary<-function(report) {
	ratio.df<-report@ratio.df
	levels.P<-levels(ratio.df$PF)
	levels.R<-levels(ratio.df$RF)
	levels.S<-levels(ratio.df$SF)
	levels.AREA<-levels(ratio.df$AREAF)
	graphIds<-letters[1:(length(levels.P)*length(levels.R))]
	
	layout.mx<-rbind(c( 1,  2,  3,  4),
					 c( 5,  6,  7,  8),
					 c( 9, 10, 11, 12),
					 c(13, 14, 15, 16))
	layout(layout.mx, widths=c(7, 33, 33, 33), heights=c(33, 33, 33, 5))
	
	cex.labels<-1.5
	id<-1
	for (i.R in as.numeric(levels.R)) {
		par(mar=c(0, 0,0,0))
		plot.new() #Row
		
		mtext(side=2, line=-4, sprintf("R=%0.0f", i.R), cex=cex.labels)
		for (i.P in as.numeric(levels.P)) {
			S<-Resourcelayout.calculateSeparationFromPatchSize(patchSize=i.P,radius=i.R, countX=4 )			
			ss.df<-subset(ratio.df, ratio.df$R==i.R)
			AREA<-ss.df$areaCovered[ss.df$S==S][1]
			REL.S<-ss.df$REL.S[ss.df$S==S][1]
			
			partA<-sprintf("%s) I=%0.0f, ", graphIds[id],S)
			partB<-sprintf("=%0.1f, ",REL.S)
			partC<-sprintf("=%0.1f", ss.df$REL.AREA[ss.df$S==S][1])
			title<-substitute(paste(a, I[R], b, R[P], c), list(a=partA, b=partB, c=partC))
			CalculatedLayout.plotLayout(i.P, i.R, 4, scaleSize=report@scaleSize, drawEdges=report@includeEdges, main="", drawAxes=F)
			title(title, cex.main=1.5)
			id<-id+1
		}
	}

	par(mar=c(0, 0,0,0))
	plot.new()#bottom left
	for (i.P in as.numeric(levels.P)) {
		par(mar=c(0, 0,0,0))
		plot.new()
		mtext(side=1, line=-3, sprintf("P=%0.0f", i.P), cex=cex.labels)		
	}
	
}

#report<-report.E1B;ratio.df<-report@ratio.df;filter.G<-1;report<-report.E1C
SimExp3E1Report.addCornerCentreEdgeRatioSection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Ratio Summary (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)

	name<-sprintf("ccr-ratio-summary-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=15, height=8, bg="white")
		par(mfrow=c(1, 2))
		par(mar=c(1, 1, 5, 1))		
		CalculatedLayout.plotCornerCentreEdgeRatio(ss.df, includeEdge=report@includeEdges)		
		mtext(side=3, line=2,expression("a) Total Eggs"), cex=2)
		
		par(mar=c(1, 1, 5, 1))	
		CalculatedLayout.plotCornerCentreEdgeRatio(ss.df, includeEdge=report@includeEdges, perPlant=T, addLegend=F)		
		mtext(side=3, line=2, expression("b) Eggs Per Plant"), cex=2)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}

#par(mfrow=c(1, 1))
SimExp3E1Report.addRatioVsLandASummarySection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Ratio Summary Vs L and A (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)

	name<-sprintf("ccr-ratio-L-A-summary-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing Ratio graph to %s...\n", filename))
	pdf(filename, width=9, height=9, bg="white")
		CalculatedLayout.plotRatioVsLandA(ss.df)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}

SimExp3E1Report.addSuccessVsLandASummarySection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Forager Success vs L and A Summary (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)

	name<-sprintf("ccr-success-L-A-summary-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing Success graph to %s...\n", filename))
	pdf(filename, width=9, height=9, bg="white")
		CalculatedLayout.plotSuccessVsLandA(ss.df, filter.G)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}


SimExp3E1Report.addRatioVsLayoutSection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Centre Ratio over different layouts (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{Here we show one graph for each of the layouts previously shown. Each graph has L plotted along the x and Centre:Corner ratio on the y.}")	

	name<-sprintf("ccr-ratio-layout-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=11, height=11, bg="white")
		CalculatedLayout.plotRatioVsLayout(ss.df, includeEdge=report@includeEdges)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}
#report<-report.E1B;filter.G<-1;ratio.df<-ss.df
SimExp3E1Report.addRatioVsLayoutSinuositySection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Centre Ratio By Sinuosity Aggregated (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{Everything Aggregated}")	

	name<-sprintf("ccr-ratio-summary-sinuosity-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=12, height=7, bg="white")
		CalculatedLayout.plotRatioVsLayoutSinuositySummary(ss.df, includeEdge=report@includeEdges)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))


	title<-sprintf("Centre Ratio over different layouts By Sinuosity (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{Here we show one graph for each of the layouts previously shown. Each graph has L plotted along the x and Centre:Corner ratio on the y.}")	

	name<-sprintf("ccr-ratio-layout-sinuosity-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=11, height=11, bg="white")
		CalculatedLayout.plotRatioVsLayoutSinuosity(ss.df, includeEdge=report@includeEdges)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}
SimExp3E1Report.addSuccessVsLayoutSinuositySection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Forager Success (Eggs Per Forager) Summary By Sinuosity (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{All results aggregated}")	

	name<-sprintf("ccr-success-summary-sinuosity-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=12, height=7, bg="white")
		CalculatedLayout.plotSuccessVsLayoutSinuositySummary(ss.df, includeEdge=report@includeEdges)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))

	title<-sprintf("Forager Success (Eggs Per Forager) over different layouts By Sinuosity (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{Here we show one graph for each of the layouts previously shown. Each graph has L plotted along the x and Centre:Corner ratio on the y.}")	

	name<-sprintf("ccr-success-layout-sinuosity-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=11, height=11, bg="white")
		CalculatedLayout.plotSuccessVsLayoutSinuosity(ss.df, includeEdge=report@includeEdges)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}
SimExp3E1Report.addRatioVsSeparationSection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Centre Ratio vs Inter-Edge Separation (S) , G=%0.0f", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)

	title<-sprintf("Breakdown By A")
	latexFile<-addSubSection(latexFile, title, TRUE)
	

	name<-sprintf("ccr-ratio-separation-A-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=11, height=4, bg="white")
		CalculatedLayout.plotRatioVsSeparationByA(ss.df, includeEdge=report@includeEdges)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))


	name<-sprintf("ccr-ratio-separation-L-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	
	N.L<-length(levels(ss.df$LF))
	height<-ceiling(N.L/3)*4
	
	if (height>4) {
		latexFile<-addContent(latexFile, "\\clearpage")			
	}
	title<-sprintf("Breakdown By L")
	latexFile<-addSubSection(latexFile, title, TRUE)
	
	pdf(filename, width=11, height=height, bg="white")
		CalculatedLayout.plotRatioVsSeparationByL(ss.df, includeEdge=report@includeEdges)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))



	
	
	return (latexFile)
}

SimExp3E1Report.addSuccessVsLayoutSection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Forager Success (Eggs Per Forager) over different layouts (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{Here we show one graph for each of the layouts previously shown. Each graph has L plotted along the x and Forager Success (Eggs Per Forager) ratio on the y.}")	

	name<-sprintf("ccr-success-layout-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=11, height=11, bg="white")
		CalculatedLayout.plotSuccessVsLayout(ss.df)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}



SimExp3E1Report.addSuccessVsAreaAndPatchSizeSection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Forager Success (Eggs Per Forager) vs Area And Patch Size (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{This is aggregated accross all movement parameters to see the general trend}")	

	name<-sprintf("ccr-success-area-patch-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=16, height=8, bg="white")
		CalculatedLayout.plotSuccessVsAreaAndPatchSize(ss.df)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}

SimExp3E1Report.addSuccessVsAreaSummarySection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Forager Success vs Plant Spacing Summary (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{This is aggregated accross all movement parameters to see the general trend}")	

	name<-sprintf("ccr-success-spacing-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=12, height=8, bg="white")
		CalculatedLayout.plotSuccessVsAreaSummary(ss.df, report@includeEdges)		
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}


#ratio.df<-report@ratio.df
SimExp3E1Report.addRatioVsSeparationSummarySection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Centre Ratio vs Plant Spacing Summary (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{This is aggregated accross all movement parameters to see the general trend}")	

	name<-sprintf("ccr-ratio-spacing-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=12, height=8, bg="white")
		CalculatedLayout.plotRatioVsSeparationSummary(ss.df, report@includeEdges)		
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}

#filter.G<-1
SimExp3E1Report.addRatioVsRadiusAndPatchSizeSection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Centre Ratio vs Radius And Patch Size (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{This is aggregated accross all movement parameters to see the general trend}")	

	name<-sprintf("ccr-ratio-P-R-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=14, height=7, bg="white")
		CalculatedLayout.plotRatioForPandRSummary(ss.df, report@includeEdges)		
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}

SimExp3E1Report.addSuccessVsRadiusAndPatchSizeSection<-function(latexFile, report, filter.G, id) {
	ss.df<-subset(report@ratio.df, report@ratio.df$G==filter.G)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	title<-sprintf("Forager Success (Eggs Per Forager) vs Radius And Patch Size (G=%0.0f)", filter.G)
	latexFile<-addSection(latexFile, title, TRUE)
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{This is aggregated accross all movement parameters to see the general trend}")	

	name<-sprintf("ccr-success-P-R-%03.0f", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=14, height=7, bg="white")
		CalculatedLayout.plotSuccessForPandRSummary(ss.df)		
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	return (latexFile)
}



SimExp3E1Report.addSignalSurfaceSection<-function(latexFile, report) {
	if (sum(as.logical(report@experimentPlan@iterationSummary.df$olfactionEnabled))>0) {
		SWF<-report@experimentPlan@iterationSummary.df$signalWidth

		for (i.SW in levels(SWF)) {
			latexFile<-SimExp3E1Report.addSignalSurface(latexFile, report, i.SW)
		}
		
	}
	
	return (latexFile)
}
SimExp3E1Report.addSignalSurface<-function(latexFile, report, filter.SW) {
	ratio.df<-subset(report@ratio.df, report@ratio.df$SW==filter.SW)
	
	
	latexFile<-addContent(latexFile, "\\clearpage")	

	title.section<-sprintf("Signal Surface (SW=%0.0f)", filter.SW)
	
	latexFile<-addSection(latexFile, title.section, TRUE)
	
	levels.P<-levels(ratio.df$PF)
	levels.R<-levels(ratio.df$RF)
	levels.S<-levels(ratio.df$SF)
	levels.AREA<-levels(ratio.df$AREAF)
	graphIds<-letters[1:(length(levels.P)*length(levels.R))]
	par(mfrow=c(length(levels.P), length(levels.R)))

	trellis.par.set(theme = col.whitebg())
	trellis.par.set("fontsize", list(text=40))
	
	latexFile<-addContent(latexFile, "\\begin{figure}[ht]")
	latexFile<-addContent(latexFile, "\\centering")
	latexFile<-addContent(latexFile, "\\begin{tabular}{ccc}")

	id<-1
	i.R<-5
#	for (i.R in as.numeric(levels.R)) {
		for (i.P in as.numeric(levels.P)) {
			i.S<-Resourcelayout.calculateSeparationFromPatchSize(patchSize=i.P,radius=i.R, countX=4 )			
			ss.df<-subset(ratio.df, ratio.df$R==i.R)
			ss.df<-subset(ss.df, ss.df$S==i.S)			
			
			
			title<-sprintf("%s) P=%0.0f",graphIds[id], i.P)

			iterationNumber<-ss.df$iteration[[1]]

			itr<-getIteration(report@experimentPlan, iterationNumber)

			surface<-itr@signalSurfaces[[1]] #only have one at the moment
			survey.mx<-surface@surfaceMatrix
			x<-1:surface@countX
			y<-1:surface@countY
			surface@maxX

			name<-sprintf("%s-wireframe-%03.0f", surface@name, id)
			filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
			cat(sprintf("Writing graph to %s...\n", filename))			
			
			pdf(filename, width=15, height=15, bg="white")
			
				scales<-list(
							arrows=T,
							)			
				
				print(wireframe(survey.mx, scales = scales,
					shade=TRUE, 
					zoom=0.9,
				aspect=c(1, 1), screen=list(z=0, x=-45, y=30),
				main=title, xlab="X", ylab="Y", zlab="S",
				xlim=c(0, x), ylim=c(0, y)))
			dev.off()

			latexFile<-addContent(latexFile, sprintf("\\includegraphics[width=2.3in]{%s} ", filename))


			id<-id+1
		}
		latexFile<-addContent(latexFile, "\\\\")
		
#	}
	latexFile<-addContent(latexFile, "\\end{tabular}")
	latexFile<-addContent(latexFile, sprintf("\\label{fig:%s}", name))
	latexFile<-addContent(latexFile, sprintf("\\caption{\\textit{%s}}", title.section))

	latexFile<-addContent(latexFile, "\\end{figure}")
	
	return (latexFile)
}


setMethod("generateReport", signature=c("SimExp3E1Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3E1Report.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)



#Add a "toString" which is a "show method"
setMethod("show", "SimExp3E1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3E1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3E1Report",
	function(x, y, ...) {
		
	}
)




