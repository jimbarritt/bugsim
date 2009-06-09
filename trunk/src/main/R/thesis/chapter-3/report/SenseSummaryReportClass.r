# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...


#Class definition for an IterationReplicate class

setClass("SenseSummaryReport", representation(
	signalSurvey="SignalSurvey",
	z.rotation="numeric",
	iteration="numeric",
	maxZ="numeric",
	byZ="numeric"
	
), contains="StandardReport")


#Add a constructor:
SenseSummaryReport<-function(experimentPlan, iterationNumber, z.rotation=0, iteration=0, maxZ=0.014, byZ=0.013/10) {
	instance<-new("SenseSummaryReport")
		
	instance<-StandardReport.initialise(instance, experimentPlan)	
	
	filename<-sprintf("%s-signal-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)
	instance@z.rotation<-z.rotation
	instance@iteration<-iteration
	instance@maxZ<-maxZ
	instance@byZ<-byZ

	
	instance@latexFile<-addTitle(instance@latexFile, sprintf("Sense Summary Report (%s)", experimentPlan@experimentDirName))

	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@experimentPlan<-experimentPlan

	
	return (instance)
}



setMethod("generateReport", signature=c("SenseSummaryReport", "logical", "logical"),
	function(report, showPreview, clean) {		

		report<-SenseSummaryReport.generateSignalSurveySection(report, report@iteration, report@z.rotation)			
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)




#Add a "toString" which is a "show method"
setMethod("show", "SenseSummaryReport", 
	function(object) {
		show(object@latexFile)

	}
)


#And a "summary" method
setMethod("summary", "SenseSummaryReport", 
	function(object) {
	}
)
#report<-report.sense.D4;iterationNumber<-1
SenseSummaryReport.generateSignalSurveySection<-function(report, iterationNumbers=c(), z.rotations=rep(0, length(iterationNumbers))) {
	latexFile<-report@latexFile
	latexFile<-addContent(latexFile, "\\clearpage")	
	latexFile<-addSection(latexFile, "Signal Surfaces", starred=T)
	
	#Need to do this for each iteration and each surface... but only have 1 surface at the moment...
	if (length(iterationNumbers)==0) {
		for (iteration in report@experimentPlan@iterations) {	
			latexFile<-SenseSummaryReport.generateIterationSurfaceSection(latexFile, report, iteration)
		}
	} else {
		i<-1
		for (iterationNumber in iterationNumbers) {			
			iteration<-report@experimentPlan@iterations[[iterationNumber]]
			latexFile<-SenseSummaryReport.generateIterationSurfaceSection(latexFile, report, iteration, rotation=z.rotations[i])
			i<-i+1
		}
	}
	
	report@latexFile<-latexFile
	
	return (report)
}

SenseSummaryReport.generateIterationSurfaceSection<-function(latexFile, report, iteration, rotation) {

	
	signalSurvey<-SignalSurvey(report@experimentPlan, iteration@iterationNumber)

	report@signalSurvey<-signalSurvey

	surfaceNumber<-1	
	report@latexFile<-latexFile
	latexFile<-SenseSummaryReport.generateSurfaceSection(latexFile, report, signalSurvey, surfaceNumber, iteration, rotation)

	return (latexFile)
}

SenseSummaryReport.generateSurfaceSection<-function(latexFile, report, signalSurvey, surfaceNumber, iteration, rotation) {

	surface<-getSurface(signalSurvey, surfaceNumber)
	title<-sprintf("%s (s.d.=%0.2f, Mag.=%0.2f)", surface@name, surface@surfaceSD, surface@surfaceMagnification)
	latexFile<-addSection(latexFile, title, starred=T)	
	
	surfaceDetails<-sprintf("Survey Size (xcount=%0.0f, ycount=%0.0f, interval=%0.0f, bounds=(%0.0f, %0.0f, %0.0f, %0.0f))", 
		surface@countX,
		surface@countY,	
		surface@intervalX,
		surface@minX,
		surface@minY,
		surface@maxX,
		surface@maxY		
	)
	
#	latexFile<-addContent(latexFile, surfaceDetails)

	survey.mx<-surface@surfaceMatrix
	
	
	cabbageLayout.df<-iteration@cabbages.df

	latexFile<-SenseSummaryReport.addSurfacePlot(report, latexFile, surface, survey.mx, cabbageLayout.df, rotation, iteration)
	
	

	return (latexFile)
}

#Not a real function but is here to help debugging gets you to the important object quick
SenseSummaryReport.setupSurfaceVariables<-function() {
	report<-report.sense.D4
	for (iteration in report@experimentPlan@iterations) {
		cat("Iteration ", iteration@iterationNumber, ", field ", iteration@resourceLayoutName, "\n")
	}
	
	
	iterationNumber<-1
	iteration<-report@experimentPlan@iterations[[iterationNumber]]
	
	cabbageLayout.df<-iteration@cabbages.df


	
	signalSurvey<-SignalSurvey(report@experimentPlan, iteration@iterationNumber)
	surfaceNumber<-1	
	
	surface<-getSurface(signalSurvey, surfaceNumber)
	surfaceDetails<-sprintf("Survey Size (xcount=%0.0f, ycount=%0.0f, interval=%0.0f, bounds=(%0.0f, %0.0f, %0.0f, %0.0f))", 
		surface@countX,
		surface@countY,	
		surface@intervalX,
		surface@minX,
		surface@minY,
		surface@maxX,
		surface@maxY		
	)
	survey.mx<-surface@surfaceMatrix
	survey.tx<-SenseSummaryReport.transformMatrixForImageMap(survey.mx)

	
	cabb.x<-cabbageLayout.df$X
	cabb.y<-cabbageLayout.df$Y
#	quartz(width=10, height=10)
	par(mfrow=c(1,1))
	plot(cabb.y~cabb.x, type="p", pch=19, cex=1, col="blue", xlim=c(surface@minX, surface@maxX), ylim=c(surface@minY, surface@maxY))


	report@z.rotation<-20
	plotSurfaceWireFrame(report, survey.tx, surface, rotation=0)
			#"a) Image Map"
	plotImageMap(survey.tx, surface, title="", cabbageLayout.df)

		
			
}

SenseSummaryReport.transformMatrixForImageMap<-function(input.mx) {
	maxRow<-length(input.mx[,1])
	maxCol<-length(input.mx[1,])		
	col.out<-1
	row.out<-1
	output.mx<-matrix(0, ncol=length(input.mx[1,]), nrow=length(input.mx[,2]))		
	for (row in 1:maxRow) {
		for (col in 1:maxCol) {
			output.mx[row.out, col.out]<-input.mx[row, col]
			row.out<-row.out+1
		}
		row.out<-1
		col.out<-col.out+1
	}
	return (output.mx)
	
}


#Wierd Shit!!!
testMatrixLayout<-function() {
	survey.mx<-rbind("a"=c(1, 2 , 3),
					 "b"=c(4, 5, 6),
					 "c"=c(7, 8, 9))
	

	survey.mx<-rbind("a"=c(7, 4, 1),
					 "b"=c(8, 5, 2),
					 "c"=c(9, 6, 3))

	survey.mx<-rbind("a"=c(1, 4, 7),
					 "b"=c(2, 5, 8),
					 "c"=c(3, 6, 9))

	
						par(mar=c(6, 6, 6, 6))				
						image(1:3,1:3, survey.mx, col=heat.colors(9))
	
}

plotSurfaceWireFrame<-function(report, survey.tx, surface, title="", rotation=0) {
		z.key<-seq(from=0.0, to=report@maxZ, by=report@byZ)
		colormap<-rainbow(length(z.key)-1, start=0.49, end=0.7, s=.6 )
		colormap<-collection.reverse(colormap)

		x<-seq(from=0, to=surface@maxX, by=surface@maxX/surface@countX)
		y<-seq(from=0, to=surface@maxY, by=surface@maxY/surface@countY)

		x.at<-c(0, max(x))
		y.at<-c(0, max(y))

		z.at<-SummaryStatistics.simpleYat(c(min(z.key), max(z.key)))
		cex.axes<-0.7
		scales<-list(	arrows = FALSE,
						x=list(
							cex=cex.axes,
							at=c(0.5, surface@countX+0.5),
							labels=c("0",surface@maxX/100)

						),
						y=list(
							cex=cex.axes,
							at=c(0.5, surface@countY+0.5),
							labels=c("0",surface@maxY/100)
						),
						z=list(
							cex=cex.axes,
							arrows=F,
							mgp=c(3, 1.9, 0),
							at=c(0, 0.012),
							labels=c("0", sprintf("%0.3f", max(z.key)))
						)							
		)

		fontsize<-trellis.par.get("fontsize")
		fontsize$text<-18
		trellis.par.set("fontsize", fontsize)

		main.text<-trellis.par.get("par.main.text")
		main.text$font<-2
		trellis.par.set("par.main.text", main.text)
		
	#xlim=c(0, x), ylim=c(0, y)), 	at=z.key,col.regions=colormap, 
		print(wireframe(survey.tx,col.regions=colormap, at=z.key,
			drape=TRUE, zoom=0.9, scale=scales,
			aspect=c(1, 1), screen=list(z=rotation, x=-45, y=30),
		 	xlab="X (m)", ylab="Y (m)", zlab="S", colorkey=T,
			xlim=c(0, surface@countX+1), ylim=c(0, surface@countY+1), zlim=c(0, max(z.key)),
			#main=substitute(a, list(a=title), cex.main=2)
		))
		

	
}
#report@z.rotation<- 100
SenseSummaryReport.addSurfacePlot<-function(report, latexFile, surface, survey.mx, cabbageLayout.df, rotation, iteration) {

#	title<-sprintf("Signal Surface (name=%s, s.d.=%0.0f)", surface@name, surface@surfaceSD)
#	latexFile<-addSection(latexFile, title, TRUE)

	x<-1:surface@countX
	y<-1:surface@countY

	trellis.par.set(theme = col.whitebg())
	
	survey.tx<-SenseSummaryReport.transformMatrixForImageMap(survey.mx)
	
	
	name.1<-sprintf("%s-wireframe-%03.0f", surface@name, surface@iterationNumber)
	filename.1<-StandardReport.makeFigureFileName(report, name.1)	
	pdf(filename.1, width=10, height=10, bg="white")
#		print(wireframe(survey.mx, scales = list(arrows = FALSE),shade=TRUE, aspect = c(1, 1), screen=list(z=0, x=-45, y=30),main="", xlab="X", ylab="Y", zlab="S"))
#"b) 3D Surface"
		plotSurfaceWireFrame(report, survey.tx, surface, title="", rotation=rotation)

		
		
	dev.off()

#	name.2<-sprintf("%s-contour-%03.0f", surface@name, surface@iterationNumber)
#	filename.2<-StandardReport.makeFigureFileName(report, name.2)	
#	pdf(filename.2, width=10, height=10, bg="white")
#		x<-1:surface@countX
#		y<-1:surface@countY
#		contour(x,y, z=survey.mx, main="")		
#	dev.off()

	name.3<-sprintf("%s-imagemap-%03.0f", surface@name, surface@iterationNumber)
	filename.3<-StandardReport.makeFigureFileName(report, name.3)	
	pdf(filename.3, width=10, height=10, bg="white")
	#"a) Image Map"
		plotImageMap(report, survey.tx, surface, title="", cabbageLayout.df)		
	dev.off()


	latexFile<-addContent(latexFile, "\\begin{figure}[ht]")
	latexFile<-addContent(latexFile, "\\centering")
	latexFile<-addContent(latexFile, "\\begin{tabular}{cc}")
	latexFile<-addContent(latexFile, sprintf("\\includegraphics[width=2.3in]{%s}", filename.3))
#	latexFile<-addContent(latexFile, sprintf("\\includegraphics[width=2.3in]{%s}", filename.2))
	latexFile<-addContent(latexFile, sprintf("\\includegraphics[width=2.3in]{%s}", filename.1))
	latexFile<-addContent(latexFile, "\\end{tabular}")
	latexFile<-addContent(latexFile, sprintf("\\label{fig:signal-surface-%0.0f}", surface@iterationNumber))
	latexFile<-addContent(latexFile, sprintf("\\caption{\\textit{Signal Surface (Iteration %0.0f, Field %s, SW=%0.0f)}}", surface@iterationNumber, iteration@resourceLayoutName, surface@surfaceSD))
	
	latexFile<-addContent(latexFile, "\\end{figure}")
	
	return (latexFile)
	
}
#title<-"a) Image Map";par(mfrow=c(1, 1));quartz(width=10, height=10)
plotImageMap<-function(report, survey.tx, surface, title=NA, cabbageLayout.df=NA) {
	
	
	x<-seq(from=0, to=surface@maxX, by=surface@maxX/surface@countX)
	y<-seq(from=0, to=surface@maxY, by=surface@maxY/surface@countY)
	
	
	layout.mx<-rbind(c(1, 1, 1),
					 c(2, 3, 4),
					 c(5, 6, 7))
	layout(layout.mx, widths=c(7, 93, 2), heights=c(3, 93,3))
	par(mar=c(0,0, 0, 0))
	plot.new() #1
	if(!is.na(title)) {
		mtext(side=3, line=-5, substitute(a, list(a=title)), cex=3.5)
	}
	

	par(mar=c(0,0, 0, 0))
	plot.new() #2

	mtext(side=2, line=-4, expression("Y (m)"), cex=3)

	par(mar=c(5, 5, 5, 5))
	
	maxX.at<-max(x)
	maxX.label<-surface@maxX

	at.indexes<-c(0.5, maxX.at+0.5)
	labels<-c(0, maxX.label/100)
	z.key<-seq(from=0.0, to=report@maxZ, by=report@byZ)
	colormap<-rainbow(length(z.key)-1, start=0.49, end=0.7, s=.6 )
	colormap<-collection.reverse(colormap)
	image(x,y, z=survey.tx, axes=F, ann=F, col=colormap, breaks=z.key)
	
	
	if (!is.na(cabbageLayout.df)) {
#		itr<-getIteration(report@experimentPlan, surface@iterationNumber)
#		layoutW<-itr@resourceLayoutWidth
#		surveyW<-surface@maxX
		indent<-(surface@maxX/surface@countX)/2

		cx<-cabbageLayout.df$X+indent
		cy<-cabbageLayout.df$Y+indent


		points(cy~cx, pch=19, cex=1, col="black")	
	}


	
	axis(1, at=c(0, surface@maxX),label=c(0, surface@maxX/100), cex.axis=4, mgp=c(3, 3, 0) )
	axis(2, at=c(0, surface@maxY), label=c(0, surface@maxY/100), cex.axis=4, mgp=c(3, 3, 0) )
	box()
	par(mar=c(0, 0, 0, 0))	
	plot.new() #4
	
	plot.new() #5


	par(mar=c(0,0, 0, 0))
	plot.new() #6
	mtext(side=1, line=-2, expression("X (m)"), cex=3)

	
	
}