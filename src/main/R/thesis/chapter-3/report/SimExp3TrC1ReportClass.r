# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3C1Report", representation(
	experimentDir="character",
	lh.1.df="data.frame",
	lh.2.df="data.frame",
	lh.3.df="data.frame",
	L1="numeric",
	L2="numeric",
	L3="numeric",
	K1="numeric",
	K2="numeric",
	K3="numeric"	
), contains="StandardReport")


#Add a constructor:
#experimentPlan<-experimentPlan.C1
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3C1Report<-function(experimentPlan) {
	instance<-new("SimExp3C1Report")
	
	instance<-StandardReport.initialise(instance, experimentPlan)	
	
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)
	
	instance@latexFile<-addTitle(instance@latexFile, sprintf("Path Analysis Report (%s)", experimentPlan@experimentDirName))


	itr<-getIteration(experimentPlan, 1)
	rep<-getReplicate(itr, 1)
	foragerSummary<-ForagerSummary(rep, itr, 1)		

	instance@L1<-itr@moveLength	
	instance@K1<-itr@angleOfTurn
	instance@lh.1.df<-foragerSummary@lifehistory.df
	
	itr<-getIteration(experimentPlan, 2)
	rep<-getReplicate(itr, 1)
	foragerSummary<-ForagerSummary(rep, itr, 1)	
		
	instance@L2<-itr@moveLength
	instance@K2<-itr@angleOfTurn	
	instance@lh.2.df<-foragerSummary@lifehistory.df
	
	itr<-getIteration(experimentPlan, 3)
	rep<-getReplicate(itr, 1)
	foragerSummary<-ForagerSummary(rep, itr, 1)		
	
	instance@L3<-itr@moveLength
	instance@K3<-itr@angleOfTurn	
	instance@lh.3.df<-foragerSummary@lifehistory.df

	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-StandardReport.generateIterationSummarySection(instance@latexFile, experimentPlan)


	return (instance)
}

SimExp3C1Report.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile
	latexFile<-addContent(latexFile, "\\vspace{12pt} \\noindent \\textit{In all of the following graphs, except for the Patch Size / radius Graphs, the patch size is 50 and the radius of plants is 3}")	
	latexFile<-addContent(latexFile, "\\clearpage")	

	latexFile<-addExamplePathGraph(latexFile, report)
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	latexFile<-addExampleLengthPathGraph(latexFile, report)
	latexFile<-addContent(latexFile, "\\clearpage")	

	latexFile<-addExampleResourcePathGraph(latexFile, report, iterationNumber=1, foragerIndex=1, id=1)
	latexFile<-addContent(latexFile, "\\clearpage")	

	latexFile<-addExampleResourcePathGraph(latexFile, report, iterationNumber=2, foragerIndex=5, id=2)
	latexFile<-addContent(latexFile, "\\clearpage")	

	latexFile<-addExampleResourcePathGraph(latexFile, report, iterationNumber=3, foragerIndex=12, id=3)
	latexFile<-addContent(latexFile, "\\clearpage")	
	
		
	latexFile<-addExamplePathBreakDown(latexFile, report)

	latexFile<-addContent(latexFile, "\\clearpage")	

	latexFile<-addAngularDeviationGraph(latexFile, report)
	
	latexFile<-addLinearDeviationGraph(latexFile, report)
	
	latexFile<-addSinuosityGraph(latexFile, report)

	latexFile<-addContent(latexFile, "\\clearpage")	
	
	latexFile<-addMSDGraphs(latexFile, report)


	report@latexFile<-latexFile
	return (report)	
}


addExamplePathGraph<-function(latexFile, report) {
	latexFile<-addSection(latexFile, "Example Paths for various $\\mathbf{k}$ (L=5, 50 steps)", TRUE)
	
	filename<-sprintf("%s/example-path.pdf", report@figOutputDir)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=9, height=6, bg="white")
		par(mfrow=c(2, 3))

		circular.vm.plotDensityFunctionRadians(10, "a)")
		circular.vm.plotDensityFunctionRadians(3, "b)")
		circular.vm.plotDensityFunctionRadians(0.5, "c)")
		
		ForagerSummary.plotForagerTrail(report@lh.1.df,1,  120,"topright", report@L1, 5, drawScale=T, drawBox=F)
		ForagerSummary.plotForagerTrail(report@lh.2.df,5,  120,"topright", report@L2, 5, drawScale=F, drawBox=F)
		ForagerSummary.plotForagerTrail(report@lh.3.df,12,  120,"topright", report@L3, 5, drawScale=F, drawBox=F)
		

	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Example Paths", "fig:example-paths")
	
	return (latexFile)
}

#report<-report.C1
addExampleLengthPathGraph<-function(latexFile, report) {
	latexFile<-addSection(latexFile, sprintf("Example Paths for various L and k (Max 20 steps)"), TRUE)

	filename<-sprintf("%s/example-length-path.pdf", report@figOutputDir)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=9, height=9, bg="white")
	
		layout.mx<-rbind(c( 1,  2,  3,  4),
						 c( 5,  6,  7,  8),
						 c( 9, 10, 11, 12),
						 c(13, 14, 15, 16))
		layout(layout.mx, widths=c(7, 33, 33, 33), heights=c(33, 33, 33, 5))

	
		addExampleLengthPathGraphX(report@experimentPlan, 1, 16, graphIds=c("a", "b", "c"), drawScale=T)
		addExampleLengthPathGraphX(report@experimentPlan, 2, 10, graphIds=c("d", "e", "f"), drawScale=F)
		addExampleLengthPathGraphX(report@experimentPlan, 3, 10, graphIds=c("g", "h", "i"), drawScale=F)

		par(mar=c(0, 0,0,0))
		plot.new()#bottom left
		plot.new()
		mtext(side=1, line=-3, expression("L=1"), cex=1.5)		
		plot.new()
		
		mtext(side=1, line=-3, expression("L=5"), cex=1.5)		
		plot.new()
		
		mtext(side=1, line=-3, expression("L=10"), cex=1.5)		
		

	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Example Paths Varying L and k (20 steps)", "fig:example-paths-L-K")
	
	return (latexFile)
}


#experimentPlan<-experimentPlan.C1;iterationNumber<-1;foragerIndex<-16
addExampleLengthPathGraphX<-function(experimentPlan, iterationNumber, foragerIndex, graphIds, ...) {

	itr<-getIteration(experimentPlan, iterationNumber)
	k<-itr@angleOfTurn
	rep<-getReplicate(itr, 1)
	foragerSummary<-ForagerSummary(rep, itr, 1)		
	lh.df<-foragerSummary@lifehistory.df

	id.factor<-as.factor(lh.df$Id)
	foragerId<-levels(id.factor)[foragerIndex]
	f.df<-subset(lh.df, lh.df$Id==foragerId)

	azimuths<-f.df$Azimuth

	steps<-20
	size<-120

	par(mar=c(0,0,0,0))
	plot.new()
	if (k<1) {
		fmt<-"k=%0.1f"
	} else {
		fmt<-"k=%0.0f"
	}

	mtext(side=2, line=-4, substitute(a, list(a=sprintf(fmt, k))), cex=1.5)

	cex.labels<-1.2
	plotLTrail(azimuths, steps, 1, title=NA , size, 1, ...)
	mtext(side=3, line=-2.5, sprintf("  %s)", graphIds[1]), cex=cex.labels, adj=0)
	
	plotLTrail(azimuths, steps, 5, title=NA, size, 1, ...)
	mtext(side=3, line=-2.5, sprintf("  %s)", graphIds[2]), cex=cex.labels, adj=0)
	
	plotLTrail(azimuths, steps, 10,title=NA , size, 1, ...)
	mtext(side=3, line=-2.5, sprintf("  %s)", graphIds[3]), cex=cex.labels, adj=0)
	
}
#par(mfrow=c(1,1))
#plotLTrail(azimuths, steps, 1, title=NA, size, 1)
#mtext(side=3, line=-3, sprintf("  %s)", graphIds[1]), cex=2, adj=0)

plotLTrail<-function(azimuths, steps, stepLength, title, size, scaleL, ...) {
	path<-ForagerSummary.createTrail(steps, azimuths, stepLength, 50, 50)
	x<-coord.extractX(path)
	y<-coord.extractY(path)
	ForagerSummary.plotTrail(x, y, size, "topright", stepLength, scaleL,drawBox=T, title=title, addResources=T, ...)
}
#report<-report.C1;iterationNumber=1; foragerIndex=2;id=1
addExampleResourcePathGraph<-function(latexFile, report, iterationNumber, foragerIndex, id) {
	itr<-getIteration(report@experimentPlan, iterationNumber)	

	k<-itr@angleOfTurn
	L<-itr@moveLength
	title<-sprintf("Example Path with varying R and P (k=%0.1f, L=%0.0f, Max 20 steps)", k, L)
	latexFile<-addSection(latexFile, title, TRUE)

	filename<-sprintf("%s/example-resource-path-%03.0f.pdf", report@figOutputDir, id)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=9, height=9, bg="white")
	
	
		layout.mx<-rbind(c( 1,  2,  3,  4),
						 c( 5,  6,  7,  8),
						 c( 9, 10, 11, 12),
						 c(13, 14, 15, 16))
		layout(layout.mx, widths=c(7, 33, 33, 33), heights=c(33, 33, 33, 5))


		addExampleResourcePathGraphX(report@experimentPlan, itr, foragerIndex, radius=3, graphIds=c("a", "b", "c"), drawScale=T)
		addExampleResourcePathGraphX(report@experimentPlan, itr, foragerIndex, radius=5, graphIds=c("d", "e", "f"), drawScale=F)
		addExampleResourcePathGraphX(report@experimentPlan, itr, foragerIndex, radius=15, graphIds=c("g", "h", "i"), drawScale=F)

		par(mar=c(0, 0,0,0))
		plot.new()#bottom left
		plot.new()
		mtext(side=1, line=-3, expression("P=25"), cex=1.5)		
		plot.new()
	
		mtext(side=1, line=-3, expression("P=50"), cex=1.5)		
		plot.new()
	
		mtext(side=1, line=-3, expression("P=120"), cex=1.5)		
	
	
	#	par(mfrow=c(3, 3))	

	#	addExampleResourcePathGraphX(itr, foragerIndex, graphIds=letters[1:9], drawScale=T, id)

	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:example-paths-resource-%03.0f", id))
	
	return (latexFile)
}

addExampleResourcePathGraphX<-function(experimentPlan, itr, foragerIndex, radius, graphIds, ...) {

	steps<-20
	size<-120

	par(mar=c(0,0,0,0))
	plot.new()
	fmt<-"R=%0.0f"

	mtext(side=2, line=-4, substitute(a, list(a=sprintf(fmt, radius))), cex=1.5)

	azimuths<-getAzimuthsForForager(itr, foragerIndex)

	steps<-20
	size<-120

	cex.labels<-1.2
	plotLTrail(azimuths, steps, title=NA, size=size, stepLength=5, patchSize=25, resourceRadius=radius, drawCentre=T,drawScale=T, scaleL=1)
	mtext(side=3, line=-2.5, sprintf("  %s)", graphIds[1]), cex=cex.labels, adj=0)
	
	plotLTrail(azimuths, steps, title=NA, size=size, stepLength=5, patchSize=50, resourceRadius=radius, drawCentre=T,drawScale=F,scaleL=1)
	mtext(side=3, line=-2.5, sprintf("  %s)", graphIds[2]), cex=cex.labels, adj=0)
	
	plotLTrail(azimuths, steps, title=NA, size=size, stepLength=5, patchSize=120, resourceRadius=radius, drawCentre=T,drawScale=F,scaleL=1)
	mtext(side=3, line=-2.5, sprintf("  %s)", graphIds[3]), cex=cex.labels, adj=0)
	
}

addExampleResourcePathGraphX_OLD<-function(itr, foragerIndex, graphIds,  ...) {
	azimuths<-getAzimuthsForForager(itr, foragerIndex)

	steps<-20
	size<-120

	graphIds<-c("a", "b", "c")
	par(mfrow=c(3, 3))

	radius<-3
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=25", graphIds[1], radius), size, 1, patchSize=25, resourceRadius=radius, drawCentre=T,drawScale=T)
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=50", graphIds[2], radius), size, 1, patchSize=50, resourceRadius=radius, drawCentre=T,drawScale=F)
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=120", graphIds[3], radius), size, 1, patchSize=120, resourceRadius=radius, drawCentre=T,drawScale=F)

	radius<-5
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=25", graphIds[4], radius), size, 1, patchSize=25, resourceRadius=radius, drawCentre=T,drawScale=F)
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=50", graphIds[5], radius), size, 1, patchSize=50, resourceRadius=radius, drawCentre=T,drawScale=F)
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=120", graphIds[6], radius), size, 1, patchSize=120, resourceRadius=radius, drawCentre=T,drawScale=F)

	radius=15
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=25", graphIds[7], radius), size, 1, patchSize=25, resourceRadius=radius, drawCentre=T,drawScale=F)
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=50", graphIds[8], radius), size, 1, patchSize=50, resourceRadius=radius, drawCentre=T,drawScale=F)
	plotLTrail(azimuths, steps, 5, sprintf("%s) R=%d, P=120", graphIds[9], radius), size, 1, patchSize=120, resourceRadius=radius, drawCentre=T,drawScale=F)

	
}
addTrail<-function(azimuths, steps, stepLength, ...) {
	path<-ForagerSummary.createTrail(steps, azimuths, stepLength, 50, 50)
	x<-coord.extractX(path)
	y<-coord.extractY(path)
	lines(y~x, lwd=1.5, ...)
}


getAzimuthsForForager<-function(itr, foragerIndex) {
	rep<-getReplicate(itr, 1)
	foragerSummary<-ForagerSummary(rep, itr, 1)		
	lh.df<-foragerSummary@lifehistory.df

	id.factor<-as.factor(lh.df$Id)
	foragerId<-levels(id.factor)[foragerIndex]
	f.df<-subset(lh.df, lh.df$Id==foragerId)

	azimuths<-f.df$Azimuth
	return (azimuths)
}

addExamplePathBreakDown<-function(latexFile, report) {

	par(mfrow=c(4, 4))


	latexFile<-addExamplePathBreakDownDetail(report, latexFile, report@lh.1.df, report@L1, report@K1, 1)
	latexFile<-addExamplePathBreakDownDetail(report, latexFile, report@lh.2.df, report@L2, report@K2, 2)
	latexFile<-addExamplePathBreakDownDetail(report, latexFile, report@lh.3.df, report@L3, report@K3, 3)


	return (latexFile)
}
addExamplePathBreakDownDetail<-function(report, latexFile, lh.df, L, k, id) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	latexFile<-addSection(latexFile, sprintf("Detail of Paths ($\\mathbf{k=%0.2f}$, L=%0.0f)", k, L), TRUE)
	
	filename<-sprintf("%s/example-path-detail-%03.0f.pdf", report@figOutputDir, id)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		par(mfrow=c(4, 4))

		for(forager in 1:16) {
			ForagerSummary.plotForagerTrail(lh.df,forager,  220,"topright", L, 5, drawScale=(forager==1), drawBox=T)
		}
		

	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,sprintf("Example Paths ($\\mathbf{k=%0.2f}$, L=%0.0f)", k, L), sprintf("fig:example-path-detail-%03.0f", id))
	
	return (latexFile)
}

addAngularDeviationGraph<-function(latexFile, report) {
	latexFile<-addSection(latexFile, "Angular Deviation ($\\mathbf{\\sigma}$) Varies With $\\mathbf{k}$", TRUE)
	
	filename<-sprintf("%s/sigma-with-K.pdf", report@figOutputDir)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		circular.plotAngularDeviationAsK()		
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"$\\mathbf{\\sigma}$ Varies With  $\\mathbf{\\kappa}$", "fig:sigma-k")
	
	return (latexFile)
}

addLinearDeviationGraph<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	latexFile<-addSection(latexFile, "Linear Deviation ($\\mathbf{\\sigma}$) Varies With $\\mathbf{k}$", TRUE)
	
	filename<-sprintf("%s/linear-sigma-with-K.pdf", report@figOutputDir)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		circular.plotLinearDeviationAsK()
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Linear $\\mathbf{\\sigma}$ Varies With  $\\mathbf{\\kappa}$", "fig:linear-sigma-k")
	
	return (latexFile)
}
addSinuosityGraph<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")	
	latexFile<-addSection(latexFile, "Sinuosity ($\\mathbf{S^*}$) Varies With $\\mathbf{k}$", TRUE)
	
	filename<-sprintf("%s/sinuosity-with-K.pdf", report@figOutputDir)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		circular.plotSinuosityWithK()
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Sinuosity ($\\mathbf{S^*}$) Varies With  $\\mathbf{\\kappa}$", "fig:linear-sigma-k")
	
	return (latexFile)
}


addMSDGraphs<-function(latexFile, report) {
	
	Ls<-c(1, 10, 100, 250)
	
	latexFile<-addMSDGraph(latexFile, Ls, 10, report)
	latexFile<-addMSDGraph(latexFile, Ls, 100, report)
	latexFile<-addMSDGraph(latexFile, Ls, 1000, report)
	latexFile<-addMSDGraph(latexFile, Ls, 2500, report)
	latexFile<-addMSDGraph(latexFile, Ls, 5000, report)
	
	return (latexFile)
}

addMSDGraph<-function(latexFile, Ls, timesteps, report) {
	latexFile<-addSection(latexFile, sprintf("MSD Varies With $\\mathbf{k}$, steps=%d ", timesteps), TRUE)
	
	filename<-sprintf("%s/msd-with-K-n-%d.pdf", report@figOutputDir, timesteps)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		md.plotMSDWithKMultipleL(Ls, timesteps)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"MSD Varies With  $\\mathbf{\\kappa}$", sprintf("fig:msd-k-%d", timesteps))
	latexFile<-addContent(latexFile, "\\clearpage")	
	return (latexFile)
}



setMethod("generateReport", signature=c("SimExp3C1Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3C1Report.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)



#Add a "toString" which is a "show method"
setMethod("show", "SimExp3C1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3C1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3C1Report",
	function(x, y, ...) {
		
	}
)




