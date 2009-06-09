cat("Experiment 1a Boundary Crossing Library v(1.0)\n")

plotSummaryOfBorderCrossings<-function(crossings.df, groupingFactorName="B" ,baseFilename, experimentNumber, trialId, input.df, plan.df){		
	groupingFactor<-crossings.df[[groupingFactorName]]

	for (i in 1:length(levels(groupingFactor))) {
		i.group<-levels(groupingFactor)[[i]]
		group.df<-subset(crossings.df, crossings.df[[groupingFactorName]]==i.group)
		title<-sprintf("B=%s", i.group)
		plotSummaryCrossingGraphs(group.df, title, baseFilename, experimentNumber, trialId, input.df, plan.df, i.group)
	}
}

plotSummaryCrossingGraphs<-function(crossings.df, title, baseFilename, experimentNumber, trialId, input.df, plan.df, B.1) {
	quartz(width=10, height=10)
	par(mfrow=c(2,2))
	
	plotSummaryCrossingStartGraphsImpl(crossings.df,input.df, plan.df, experimentNumber, trialId, B.1)
	
	directory<-createDirectoryName(basefilename, experimentNumber, trialId)
	filename<-sprintf("%s/output/%s-%03d Crossing details - Start Locations - %s.pdf", directory, trialId, experimentNumber, title)

	cat("Writing graphs to : ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
	par(mfrow=c(2,2))
	plotSummaryCrossingStartGraphsImpl(crossings.df,input.df, plan.df, experimentNumber, trialId, B.1)
	dev.off()

	quartz(width=10, height=10)
	par(mfrow=c(2,2))
	
	plotSummaryCrossingEndGraphsImpl(crossings.df,input.df, plan.df, experimentNumber, trialId, B.1)
	
	filename<-sprintf("%s/output/%s-%03d Crossing details - End Locations - %s.pdf", directory, trialId, experimentNumber, title)

	cat("Writing graphs to : ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
	par(mfrow=c(2,2))
	plotSummaryCrossingEndGraphsImpl(crossings.df,input.df, plan.df, experimentNumber, trialId, B.1)
	dev.off()
	
		
}

plotSummaryCrossingStartGraphsImpl<-function(crossings.df, input.df, plan.df, experimentNumber, trialId, B.1) {
	stepLength<-80
	centreXY<-1713.137084990/2
	radiusInner<-113.13708499/2
	radiusOuter<-radiusInner+80
	N<-length(crossings.df$B)
	plot(crossings.df$startY~crossings.df$startX, pch=NA, xlab="x", ylab="y", main=sprintf("Start locations N=%d", N)) #xlim=c(700, 1000), ylim=c(700, 1000), 
	points(crossings.df$intersectionY~crossings.df$intersectionX, col="grey", cex=0.5)
	points(crossings.df$startY~crossings.df$startX, col="blue", cex=0.5)
	# symbols(x=c(centreXY), y=c(centreXY), circles=c(radiusOuter), add=TRUE, inches=FALSE)
	# lines(x=c(centreXY-radiusOuter, centreXY-radiusInner), y=c(centreXY, centreXY), col="grey", lwd=4)

	#Histogram of distances to the boundary:
	all.hist<-hist(crossings.df$d.toBoundary, main="Distance to boundary", xlab="Distance",plot=FALSE)
	reverse.mids<-vector(length=length(all.hist$mids))
	reverse.breaks<-vector(length=length(all.hist$mids))
	reverse.counts<-vector(length=length(all.hist$counts))
	i<-0
	for (i in 0:(length(all.hist$mids)-1)) {
		iNew<-((length(all.hist$mids))-i) 
		reverse.counts[iNew]<-all.hist$counts[[i+1]]
		reverse.mids[iNew]<-all.hist$mids[[i+1]]
		reverse.breaks[iNew]<-all.hist$breaks[[i+1]]+5
	}
	all.hist$counts<-reverse.counts
	plot(all.hist, main="Distance from Boundary", axes=FALSE,xlim=c(0, 80),xlab="Distance")
	axis(2)
	axis(1, at=all.hist$mids, labels=reverse.breaks)
	#Histogram of distances to the boundary:
	hist(crossings.df$d, main="Step Length Inside Boundary", xlab="Distance")
	
    plotParametersNoRatios(input.df, plan.df, NA, experimentNumber, trialId, B.1=B.1)
	
}

plotSummaryCrossingEndGraphsImpl<-function(crossings.df, input.df, plan.df, experimentNumber, trialId, B.1) {
	stepLength<-80
	centreXY<-1713.137084990/2
	radiusInner<-113.13708499/2
	radiusOuter<-radiusInner+80
	N<-length(crossings.df$B)
	plot(crossings.df$endY~crossings.df$endX, pch=NA, xlab="x", ylab="y", main=sprintf("End locations N=%d", N)) #xlim=c(700, 1000), ylim=c(700, 1000), 
	points(crossings.df$intersectionY~crossings.df$intersectionX, col="grey", cex=0.5)
	points(crossings.df$endY~crossings.df$endX, col="blue", cex=0.5)
	# symbols(x=c(centreXY), y=c(centreXY), circles=c(radiusOuter), add=TRUE, inches=FALSE)
	# lines(x=c(centreXY-radiusOuter, centreXY-radiusInner), y=c(centreXY, centreXY), col="grey", lwd=4)

	#Histogram of distances to the boundary:
	all.hist<-hist(crossings.df$d.end.toCentre, main="Distance (end) to Centre", xlab="Distance",plot=FALSE)
	reverse.mids<-vector(length=length(all.hist$mids))
	reverse.breaks<-vector(length=length(all.hist$mids))
	reverse.counts<-vector(length=length(all.hist$counts))
	i<-0
	for (i in 0:(length(all.hist$mids)-1)) {
		iNew<-((length(all.hist$mids))-i) 
		reverse.counts[iNew]<-all.hist$counts[[i+1]]
		reverse.mids[iNew]<-all.hist$mids[[i+1]]
		reverse.breaks[iNew]<-all.hist$breaks[[i+1]]+5
	}
	all.hist$counts<-reverse.counts
	plot(all.hist, main="Distance (end) to centre", axes=FALSE,xlim=c(0, 80),xlab="Distance")
	axis(2)
	axis(1, at=all.hist$mids, labels=reverse.breaks)
	#Histogram of distances to the boundary:
	hist(crossings.df$d, main="Step Length Inside Boundary", xlab="Distance")
	
    plotParametersNoRatios(input.df, plan.df, NA, experimentNumber, trialId, B.1=B.1)
	
}

#input.df<-inp.df
plot0vs800Results<-function(input.df, plan.df, basefilename, experimentNumber, trialId, numReplicants, B.1=0, B.2=800) {
		quartz(width=15, height=10)
		par(mfrow=c(2,3))

		tstats<-t.test(input.df$CENTRE_RATIO~input.df$B, data=input.df)
		
		plotSummaryGraphs(input.df, numReplicants, B.1=B.1, B.2=B.2)
		plotParameters(input.df, plan.df, tstats, experimentNumber, trialId, B.1=B.1, B.2=B.2)
		plotDetailGraphs(inp.df, baseFilename, experimentNumber, trialId, numReplicants)
		


		directory<-createDirectoryName(basefilename, experimentNumber, trialId)
		filename<-sprintf("%s/output/%s-%03d Centre Ratio reps=%d.pdf", directory, trialId, experimentNumber, numReplicants)
		print(sprintf("Writing file %s", filename))
		pdf(width=15, height=10, file=filename)
		par(mfrow=c(2,3))
		
		plotSummaryGraphs(input.df, numReplicants)
		plotParameters(input.df, plan.df, tstats, experimentNumber, trialId, B.1=B.1, B.2=B.2)
		plotDetailGraphs(inp.df, baseFilename, experimentNumber, trialId, numReplicants)
		
		dev.off()
		
		
		print(tstats)
		print("A P-value of 1 is means are identical")
	
}
#B.1=800
plotParameters<-function(input.df, plan.df, tstats.df, experimentNumber, trialId, B.1=0, B.2=800) {
	par(mar=c(1, 1, 1, 1))
		descLevels <- levels(plan.df$plan.description);
	 	planDescription<-descLevels[[1]]

		b0MeanCentre<-mean(input.df$CENTRE_RATIO[input.df$B==B.1])
		b0Butterflies<-input.df$butterflyCount[input.df$B==B.1][[1]]
		
		b0ButterfliesDead<-mean(input.df$butterfliesDead[input.df$B==B.1])
		b0ButterfliesEscaped<-mean(input.df$ESCAPED_BUTTERFLIES[input.df$B==B.1])
		b0ButterfliesReleased<-b0ButterfliesDead + b0ButterfliesEscaped
		b0TotalEggs<-mean(input.df$TOTAL_EGGS[input.df$B==B.1])
	
		if (!is.na(B.2)) {
			b800MeanCentre<-mean(input.df$CENTRE_RATIO[input.df$B==B.2])
			b800Butterflies<-input.df$butterflyCount[input.df$B==B.2][[1]]
			b800ButterfliesDead<-mean(input.df$butterfliesDead[input.df$B==B.2])
			b800ButterfliesEscaped<-mean(input.df$ESCAPED_BUTTERFLIES[input.df$B==B.2])
			b800ButterfliesReleased<-b800ButterfliesDead + b800ButterfliesEscaped
			b800TotalEggs<-mean(input.df$TOTAL_EGGS[input.df$B==B.2])
		}
		
		plot.new()
		cx<-1.2
		leftIndent<-0
		currentY<-1.0
		currentY<-parameterLine(leftIndent, currentY,sprintf("Experiment 1a, Trial %s-%03d", trialId, experimentNumber), adj=0, cex=cx)
		currentY<-parameterLine(leftIndent, currentY,sprintf("%s", planDescription), adj=0, cex=cx)
		
		if (!is.na(tstats.df)) {
			currentY<-parameterLine(leftIndent, currentY,sprintf("P-Value for t-test = %0.6f (1 == means are equal)", tstats.df$p.value), adj=0, cex=cx)
		}
		
		currentY<-parameterLine(leftIndent, currentY,sprintf("B=%3d mean   = %0.3f", B.1, b0MeanCentre), adj=0,cex=cx)
		if (!is.na(B.2)) {
			currentY<-parameterLine(leftIndent, currentY,sprintf("B=%3d mean = %0.3f (diff=%0.3f)", B.2, b800MeanCentre, abs(b800MeanCentre-b0MeanCentre)), adj=0,cex=cx)
		}
		currentY<-parameterLine(leftIndent, currentY,sprintf("B=%3d released = %0.0f of %d (escaped = %0.0f)",B.1,b0ButterfliesReleased, b0Butterflies, b0ButterfliesEscaped), adj=0,cex=cx)
		if (!is.na(B.2)) {
			currentY<-parameterLine(leftIndent, currentY, sprintf("B=%3d released = %0.0f of %d (escaped = %0.0f)",B.2,b800ButterfliesReleased, b800Butterflies, b800ButterfliesEscaped), adj=0,cex=cx)
		}		
		currentY<-parameterLine(leftIndent, currentY,sprintf("B=%3d Mean butterflies Dead = %0.3f", B.1, b0ButterfliesDead), adj=0,cex=cx)
		if (!is.na(B.2)) {
			currentY<-parameterLine(leftIndent, currentY, sprintf("B=%3d Mean butterflies Dead = %0.3f", B.2, b800ButterfliesDead), adj=0,cex=cx)
		}
				
		currentY<-parameterLine(leftIndent, currentY,sprintf("B=%3d Mean Total Eggs = %0.3f", B.1, b0TotalEggs), adj=0,cex=cx)
		if (!is.na(B.2)) {
			currentY<-parameterLine(leftIndent, currentY, sprintf("B=%3d Mean Total Eggs = %0.3f", B.2, b800TotalEggs), adj=0,cex=cx)
		}

}

plotParametersNoRatios<-function(input.df, plan.df, tstats.df, experimentNumber, trialId, B.1) {

		descLevels <- levels(plan.df$plan.description);
	 	planDescription<-descLevels[[1]]

		
		plot.new()
		cx<-1.2
		leftIndent<-0
		currentY<-1.0
		currentY<-parameterLine(leftIndent, currentY,sprintf("Experiment 1a, Trial %s-%03d", trialId, experimentNumber), adj=0, cex=cx)
		currentY<-parameterLine(leftIndent, currentY,sprintf("%s", planDescription), adj=0, cex=cx)
		currentY<-parameterLine(leftIndent, currentY,sprintf("B=%s", B.1), adj=0, cex=cx)
		

}

parameterLine<-function(leftIndent, currentY, text, ...) {
	text(leftIndent, currentY, text, ...)
	newY<-currentY-0.1
	return (newY)
}

plotSummary<-function(input.df, basefilename, experimentNumber, trialId, numReplicants) {
		quartz(width=8, height=4)
		par(mfrow=c(1,2))
		
		plotSummaryGraphs(input.df, numReplicants)
		
		tstats<-t.test(CENTRE_RATIO~B, data=inp.df)
		print(tstats)
		print("A P-value of 1 is means are identical")


		directory<-createDirectoryName(basefilename, experimentNumber, trialId)
		filename<-sprintf("%s/Summary Graphs of Centre Ratio reps=%d.pdf", directory, numReplicants)
		print(sprintf("Writing file %s", filename))
		pdf(width=8, height=4, file=filename)
		par(mfrow=c(1,2))
		
		plotSummaryGraphs(input.df)
		
		dev.off()
	
}

plotSummaryGraphs<-function(input.df, numReplicants, B.1=0, B.2=800) {
	par(mar=c(5, 5, 5, 2))
	plot(CENTRE_RATIO~B, data=input.df, ylab="Centre Ratio", ylim=c(0,1), main=sprintf("Comparison of means B1=%d, B2=%d, n=%d", B.1, B.2,numReplicants), cex.axis=1.2, cex.lab=1.2)
	par(mar=c(5, 5, 5, 2))
	plot(CENTRE_RATIO[B==B.1]~replicant[B==B.1], type="l", data=input.df,col="red", ylim=c(0,1), main=sprintf("Each Replicate, B1=%d, B2=%d, n=%d", B.1, B.2, numReplicants), xlab="replicant", ylab="Centre Ratio", cex.axis=1.2, cex.lab=1.2)
	lines(CENTRE_RATIO[B==B.2]~replicant[B==B.2], data=input.df, col="blue")	
	legend("topright",legend=c(sprintf("B1=%d", B.1), sprintf("B2=%d", B.2)), fill=c("red", "blue"), cex=1.5,inset=0.05)
}

plotDetails<-function(inp.df, baseFilename, experimentNumber, trialId, numReplicants) {
	quartz(width=12, height=4)
	par(mfrow=c(1,3))

	plotDetailGraphs(inp.df, baseFilename, experimentNumber, trialId, numReplicants)
	
	directory<-createDirectoryName(basefilename, experimentNumber, trialId)
	filename<-sprintf("%s/Detail Graphs of Centre Ratio reps=%d.pdf", directory, numReplicants)
	print(sprintf("Writing file %s", filename))
	pdf(width=12, height=4, file=filename)
	par(mfrow=c(1,3))
	plotDetailGraphs(inp.df, baseFilename, experimentNumber, trialId, numReplicants)
	dev.off()

}

plotDetailGraphs<-function(inp.df, baseFilename, experimentNumber, trialId, numReplicants) {
	plotTwoFactors("L", "B","CENTRE_RATIO", "Centre Ratio", inp.df, baseFilename, experimentNumber, trialId, numReplicants, newPlot=FALSE, plotReverse=FALSE, legendPosition="bottomright")
	plotTwoFactors("L", "B","TOTAL_EGGS", "Total Eggs", inp.df, baseFilename, experimentNumber, trialId, numReplicants, newPlot=FALSE, plotReverse=FALSE, legendPosition="bottomright")
	plotTwoFactors("L", "B","ESCAPED_BUTTERFLIES", "Escaped Butterflies", inp.df, baseFilename, experimentNumber, trialId, numReplicants, newPlot=FALSE, plotReverse=FALSE, legendPosition="bottomright")	
}

plotVaryingBetaDist<-function(inp.df,baseFilename, experimentNumber, trialId, numReplicants) {
	quartz(width=12, height=6)
	par(mfrow=c(1,2))
	plotVaryingBetaDistGraphs(inp.df,baseFilename, experimentNumber, trialId, numReplicants)

	directory<-createDirectoryName(basefilename, experimentNumber, trialId)
	filename<-sprintf("%s/Summary Graphs of Varying Deta Distribution reps=%d.pdf", directory, numReplicants)
	print(sprintf("Writing file %s", filename))
	pdf(width=12, height=6, file=filename)
	par(mfrow=c(1,2))
	plotVaryingBetaDistGraphs(inp.df,baseFilename, experimentNumber, trialId, numReplicants)
	dev.off()
}



plotVaryingBetaDistGraphs<-function(inp.df,baseFilename, experimentNumber, trialId, numReplicants) {
	plot(inp.df$CENTRE_RATIO~inp.df$RFML_ALPHA, data=inp.df)
	plotTwoFactors("B","RFML_ALPHA", "CENTRE_RATIO", "Centre Ratio", inp.df, baseFilename, experimentNumber, trialId, numReplicants, newPlot=FALSE, plotReverse=FALSE)
}

#comparisonTitle="test title"
plotComparisonWithInput<-function(comparisonTitle, crossingsA.df, crossingsB.df, basefilename, experimentNumber, trialId) {

	
	quartz(width=10, height=15)
	par(mfrow=c(3,2))

	plotFrequencyOfFactor(crossingsA.df, "L", "B", "d", 5, newPlot=FALSE)
	plotFrequencyOfFactor(crossingsB.df, "L", "B", "d", 5, newPlot=FALSE)
	
	plotFrequencyOfFactor(crossingsA.df, "L", "B", "h", 5, newPlot=FALSE)
	plotFrequencyOfFactor(crossingsB.df, "L", "B", "h", 5, newPlot=FALSE)

	plotFrequencyOfFactor(crossingsA.df, "L", "B", "I", 5, newPlot=FALSE)	
	plotFrequencyOfFactor(crossingsB.df, "L", "B", "I", 5, newPlot=FALSE)


	directory<-createDirectoryName(basefilename, experimentNumber, trialId)	
	filename<-sprintf("%s/%s.pdf",  directory,comparisonTitle)
	print(sprintf("Writing graphs to %s", filename))
	pdf(file=filename, width=10, height=15)
	par(mfrow=c(3,2))

	plotFrequencyOfFactor(crossingsA.df, "L", "B", "d", 5, newPlot=FALSE)
	plotFrequencyOfFactor(crossingsB.df, "L", "B", "d", 5, newPlot=FALSE)
	
	plotFrequencyOfFactor(crossingsA.df, "L", "B", "h", 5, newPlot=FALSE)
	plotFrequencyOfFactor(crossingsB.df, "L", "B", "h", 5, newPlot=FALSE)

	plotFrequencyOfFactor(crossingsA.df, "L", "B", "I", 5, newPlot=FALSE)	
	plotFrequencyOfFactor(crossingsB.df, "L", "B", "I", 5, newPlot=FALSE)

	dev.off()
	
}


#input.df<-crossings.df
#input.df<-B800.df
#BValue<-800
plotBorderCrossingResults<-function(input.df, basefilename, experimentNumber, trialId) {

#	group.df<-subset(input.df, input.df$B==BValue)
	group.df<-input.df

	
	quartz(width=8, height=12)
	par(mfrow=c(3,2))

	results.list<-plotFrequencyOfFactor(group.df, "L", "B", "d", 5, newPlot=FALSE)
	plotFrequencyOfFactor(group.df, "L", "B", "h", 5, newPlot=FALSE)
	plotFrequencyOfFactor(group.df, "L", "B", "I", 5, newPlot=FALSE)

	directory<-createDirectoryName(basefilename, experimentNumber, trialId)	
	filename<-sprintf("%s/output/%s-%03d Border Crossing Comparisons.pdf", directory, trialId, experimentNumber)
	print(sprintf("Writing graphs to %s", filename))
	pdf(file=filename, width=10, height=15)
	par(mfrow=c(3,2))
	plotFrequencyOfFactor(group.df, "L", "B", "d", 5, newPlot=FALSE)
	plotFrequencyOfFactor(group.df, "L", "B", "h", 5, newPlot=FALSE)
	plotFrequencyOfFactor(group.df, "L", "B", "I", 5, newPlot=FALSE)
	dev.off()


	BValue <-0
	group.df<-subset(input.df, input.df$B==BValue)
	if (length(group.df$B) >0) {
		filename<-sprintf("%s/output/%s-%03d L vs B Scatterplots B=%d.pdf", directory, trialId, experimentNumber, BValue)
		plotBorderCrossingScatterPlots(group.df, filename)
	}
	BValue <-800
	group.df<-subset(input.df, input.df$B==BValue)
	if (length(group.df$B) >0) {
		filename<-sprintf("%s/output/%s-%03d L vs B Scatterplots B=%d.pdf", directory, trialId, experimentNumber, BValue)
		plotBorderCrossingScatterPlots(group.df, filename)
	}
	return (results.list)
	
}
#colnames(group.df)
#input.df<-crossings.df
#input.df<-group.df
#str(input.df)
plotBorderCrossingScatterPlots<-function(input.df, filename="NONE") {
	quartz(width=12.25, height=8.25)

	drawBorderCrossingPlots(input.df)
	
	if (filename!="NONE") {
	
		pdf(width=12.25, height=8.25, file=filename)
		
		drawBorderCrossingPlots(input.df)
	
		dev.off()
	}

}

drawBorderCrossingPlots<-function(input.df) {
	par(mfrow=c(2,3))
	par(mar=c(5, 5, 2, 2))
	plot(input.df$I, input.df$d, ylim=c(0,max(input.df$d)), cex=1.2, cex.lab=1.5, xlab="I", ylab="d", cex.axis=1.2)

	plot(input.df$I, input.df$h,cex=1.2, cex.lab=1.5, xlab="I", ylab="h", cex.axis=1.2)
	plot(input.df$q~input.df$I ,ylim=c(0, max(input.df$q)*2),cex=1.2, cex.lab=1.5,xlab="I", ylab="q", cex.axis=1.2)
	plot(input.df$r, input.df$q,ylim=c(0, max(input.df$q)*2), cex=1.2, cex.lab=1.5, xlab="r", ylab="q", cex.axis=1.2)

	plot(input.df$h, input.df$d,cex=1.2, cex.lab=1.5, xlab="h", ylab="d", cex.axis=1.2)
	plot(input.df$intersectionX, input.df$intersectionY, cex.lab=1.5, xlab="iX", ylab="iY", cex.axis=1.2)
	
}


