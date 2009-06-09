cat("Calculated Layout Library (Version 1.0)\n")

CalculatedLayout.ratioCaption<-function() {
	partA<-"Centre Ratio (Eggs/Plant"
	partC<-")"
	ex<-substitute(paste(a, b %+-% s.e.,c), list(a=partA, b=" ", c=partC))
	return (ex)
}


CalculatedLayout.efficiencyCaption<-function() {
	partA<-"Efficiency (Eggs/Forager"
	partC<-")"
	ex<-substitute(paste(a, b %+-% s.e.,c), list(a=partA, b=" ", c=partC))
	return (ex)
}

CalculatedLayout.aggregateRatioResults<-function(experimentPlan) {
	cat(sprintf("Aggregating Centre-Corner-Edge Ratio Results for %0.0f iterations and %0.0f replicates ...\n", experimentPlan@iterationCount, experimentPlan@replicateCount))
	ratio.df<-NULL
	for (itr in experimentPlan@iterations) {
		itr.ratio.df<-CalculatedLayout.loadCabbageRatios(experimentPlan, itr, experimentPlan@replicateCount)
		if (is.null(ratio.df)) {
			ratio.df<-itr.ratio.df
		} else {
			ratio.df<-merge(ratio.df, itr.ratio.df, all=T)
		}
	}
	ratio.df$iterationF<-as.factor(ratio.df$iteration)
	ratio.df$BF<-as.factor(ratio.df$B)
	ratio.df$EF<-as.factor(ratio.df$E)
	ratio.df$GF<-as.factor(ratio.df$G)
	ratio.df$LF<-as.factor(ratio.df$L)
	ratio.df$AF<-as.factor(ratio.df$A)
	ratio.df$SF<-as.factor(ratio.df$S)
	ratio.df$RF<-as.factor(ratio.df$R)
	ratio.df$PF<-as.factor(ratio.df$P)
	ratio.df$SINF<-as.factor(ratio.df$SIN)
	ratio.df$AREAF<-as.factor(ratio.df$areaCovered)
	ratio.df$SWF<-as.factor(ratio.df$SW)
	ratio.df$FDF<-as.factor(ratio.df$FD)

	
	
	maxPatchArea<-max(ratio.df$P)^2
	maxPatchSize<-max(ratio.df$P)
	ratio.df$REL.AREA<-(ratio.df$areaCovered*(ratio.df$P^2 / maxPatchArea))
	ratio.df$REL.AREAF<-as.factor(ratio.df$REL.AREA)

	# Old way (doesnt take into account relative spacing over all patches
#	ratio.df$REL.S<-ratio.df$S/ratio.df$R
	
	ratio.df$REL.S<-(ratio.df$S/ratio.df$P) / (ratio.df$R /maxPatchSize)
	ratio.df$REL.SF<-as.factor(ratio.df$REL.S)

	return (ratio.df)
}
#length(levels(ratio.df$REL.SF))
#ratio.df$REL.S<-ratio.df$S/ratio.df$R;ratio.df$REL.SF<-as.factor(ratio.df$REL.S)
CalculatedLayout.loadCabbageRatios<-function(experimentPlan, itr, replicateCount) {
	cabbages.df<-itr@cabbages.df

	rep.df<-data.frame("iteration"=rep(itr@iterationNumber, replicateCount))	
	rep.df$replicate<-1:replicateCount
	rep.df$L<-itr@moveLength
	rep.df$A<-itr@angleOfTurn
	rep.df$SIN<-itr@sinuosity
	rep.df$B<-itr@releaseDistance
	rep.df$E<-itr@eggLimit
	rep.df$G<-itr@eggsPerForager
	rep.df$R<-itr@resourceRadius
	rep.df$S<-itr@resourceSeparation

	rep.df$P<-itr@patchWidth	
	rep.df$areaCovered<-CalculatedLayout.calculateAreaCovered(itr)
	
	rep.df$SW<-itr@signalWidth
	rep.df$FD<-itr@visualFieldDepth

	
	rep.df$egg.total<-0
	rep.df$egg.corner<-0
	rep.df$egg.centre<-0
	rep.df$egg.edge<-0

	
	rep.df$egg.perplant.total<-0
	rep.df$egg.perplant.corner<-0
	rep.df$egg.perplant.centre<-0
	rep.df$egg.perplant.edge<-0
	
	rep.df$ratio.corner<-0
	rep.df$ratio.centre<-0
	rep.df$ratio.edge<-0
	rep.df$ratio.total<-0
	rep.df$foragers.dead<-0	
	rep.df$foragers.escaped<-0
	rep.df$foragers.success<-0
	rep.df$timesteps<-0
	

	for (i.rep in 1:replicateCount) {
		rep.in.df<-subset(cabbages.df, cabbages.df$Replicate==i.rep)		
	
		centre.total<-sum(rep.in.df$Egg.Count[rep.in.df$Isolation.Group=="Centre"])
		corner.total<-sum(rep.in.df$Egg.Count[rep.in.df$Isolation.Group=="Corner"])
		edge.total<-sum(rep.in.df$Egg.Count[rep.in.df$Isolation.Group=="Edge"])


		rep.df$egg.total[i.rep]<-corner.total+centre.total+edge.total
		rep.df$egg.corner[i.rep]<-corner.total
		rep.df$egg.centre[i.rep]<-centre.total	
		rep.df$egg.edge[i.rep]<-edge.total	
		
		rep.df$egg.perplant.corner[i.rep]<-corner.total/4
		rep.df$egg.perplant.centre[i.rep]<-centre.total/4
		rep.df$egg.perplant.edge[i.rep]<-edge.total/8	
		rep.df$egg.perplant.total[i.rep]<-(corner.total/4)+(centre.total/4)+(edge.total/8)
		
		
		#Calculates the raw ratio but not in terms of eggs per plant!
#		rep.df$ratio.corner[i.rep]<-rep.df$egg.corner[i.rep]/rep.df$egg.total[i.rep]
#		rep.df$ratio.centre[i.rep]<-rep.df$egg.centre[i.rep]/rep.df$egg.total[i.rep]
#		rep.df$ratio.edge[i.rep]<-rep.df$egg.edge[i.rep]/rep.df$egg.total[i.rep]
		
		#Calculates eggs per plant ratio - doesnt affect pattern but does affect absolute numbers.
		rep.df$ratio.corner[i.rep]<-rep.df$egg.perplant.corner[i.rep]/rep.df$egg.perplant.total[i.rep]
		rep.df$ratio.centre[i.rep]<-rep.df$egg.perplant.centre[i.rep]/rep.df$egg.perplant.total[i.rep]
		rep.df$ratio.edge[i.rep]<-rep.df$egg.perplant.edge[i.rep]/rep.df$egg.perplant.total[i.rep]
		
		rep.df$ratio.total[i.rep]<-rep.df$ratio.centre[i.rep]+rep.df$ratio.corner[i.rep]+rep.df$ratio.edge[i.rep]
	
		rep<-getReplicate(itr, i.rep)
		rep.df$foragers.dead[i.rep]<-rep@foragersDead
		rep.df$foragers.escaped[i.rep]<-rep@foragersEscaped
		rep.df$foragers.success[i.rep]<-rep.df$egg.total[i.rep]/(rep@foragersDead+rep@foragersEscaped)
		
		rep.df$timesteps<-rep@executedTimesteps
	}
	
	if (sum(is.na(rep.df$egg.total))>0) {
		if (sum(is.na(itr@eggLimit))==0) {
			rep.df$egg.total<-itr@eggLimit
			rep.df$foragers.success<-rep.df$egg.total/(rep.df$foragers.dead+rep.df$foragers.escaped)
		} else {
			rep.df$foragers.success<-rep.df$foragers.dead/(rep.df$foragers.dead+rep.df$foragers.escaped)
		}
	}

	return (rep.df)
}

CalculatedLayout.calculateAreaCovered<-function(itr) {	
	areaCovered<-NA

	if (itr@resourceLayoutName=="corner-centre-edge") {
		layout.df<-Resourcelayout.createLayout(itr@patchWidth, 4, 0, 0, edges=T)
		if (itr@resourceSeparation<0 && abs(itr@resourceSeparation)>itr@resourceRadius) { #Really overlapping, need to estimate area
			areaCovered<-round(Geomtery.estimateAreaOfCircles(layout.df$x, layout.df$y, itr@resourceRadius, n=25))			
		#	cat("Estimating Area Covered: ", areaCovered, ", ",itr@patchWidth, ", ",  itr@resourceSeparation, ", ", itr@resourceRadius, "\n")		
		} else {
			areaCovered<-round(Geometry.areaCovered(layout.df$x, layout.df$y, radii=rep(itr@resourceRadius, 16)))		
		}				
	} else if (itr@resourceLayoutName=="corner-centre") {
		layout.df<-Resourcelayout.createLayout(itr@patchWidth, 4, 0, 0, edges=F)
		if (itr@resourceSeparation<0 && abs(itr@resourceSeparation)>itr@resourceRadius) { #Really overlapping, need to estimate area
			areaCovered<-round(Geomtery.estimateAreaOfCircles(layout.df$x, layout.df$y, itr@resourceRadius, n=25))			
		#	cat("Estimating Area Covered: ", areaCovered, ", ",itr@patchWidth, ", ",  itr@resourceSeparation, ", ", itr@resourceRadius, "\n")		
		} else {
			areaCovered<-round(Geometry.areaCovered(layout.df$x, layout.df$y, radii=rep(itr@resourceRadius, 8)))		
		}
	} 
	return (areaCovered)
}



CalculatedLayout.plotLayout<-function(P, R, countX, scaleSize, drawEdges=T, drawAxes=T, ...) {
	origin<-(scaleSize-P)/2
	if (drawAxes)	{	
		par(mar=c(7, 7, 5, 5))
	} else {
		par(mar=c(1, 1, 5, 1))
	}
	
	layout.df<-Resourcelayout.createLayout(P, countX, origin, origin, edges=drawEdges)
	radii<-rep(R, length(layout.df$x))
	plot(layout.df$y~layout.df$x ,pch=NA,  axes=F, ann=F, xlim=c(0, scaleSize), ylim=c(0, scaleSize), ...)

	symbols(layout.df$x, layout.df$y, circles=radii, bg="grey", fg="darkgrey",inches=F, add=T, lwd=2)
	rect(origin, origin, origin+P, origin+P, lwd=2, bg=NA, border="blue")
	points(layout.df$x, layout.df$y, pch=16, col="black", cex=.5)

	title(..., cex.main=1.2)

	if (drawAxes)	{	

		axis(1, at=c(0, scaleSize/2, scaleSize), cex.axis=1.5)
		axis(2, at=c(0, scaleSize/2, scaleSize), cex.axis=1.5)
	}
	box()
}

CalculatedLayout.addLayoutSubSection<-function(report, latexFile, P, R, S, countX, scaleSize, id, drawEdges=T) {
	title<-sprintf("P=%0.2f, R=%0.2f, S=%0.2f", P, R, S)
	latexFile<-addSubSection(latexFile, title, TRUE)

	name<-sprintf("ccr-layout-%d", id)
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		CalculatedLayout.plotLayout(P, R, 4, 120, drawEdges)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	return (latexFile)
}

CalculatedLayout.plotReplicateBreakdown<-function(ratio.df, filter.L, filter.A, nReplicates) {
	ss.df<-subset(ratio.df, ratio.df$L==filter.L)
	ss.df<-subset(ss.df, ss.df$A==filter.A)
	
	
	par(mar=c(7, 7, 5, 2))
	nB<-length(levels(ss.df$BF))

	plot(ratio.centre~replicate, pch=NA,axes=F,ann=F, data=ss.df,col="red", ylim=c(0,1), xlim=c(0, nReplicates))
	axis(1, cex.axis=1.5)
	axis(2, cex.axis=1.5)
	box()
#	title(main=sprintf("Each Replicate, n=%d", nReplicates), cex.main=1.5)
	mtext(expression("Replicate"), side=1, line=2.5, cex=1.5)
	
	
	
	mtext(CalculatedLayout.ratioCaption(), side=2, line=4, cex=1.5)

	colors<-c("blue", "red", "green", "brown", "orange", "yellow", "purple")
	lineTypes<-rep(1, nB)
	for (i in 1:nB) {
		i.B<-levels(ss.df$BF)[[i]]
		x<-ss.df$replicate[ss.df$BF==i.B]
		y<-ss.df$ratio.centre[ss.df$BF==i.B]
	
		mean.y<-mean(y)
		lines(c(0, max(x)), c(mean.y, mean.y), col="grey", lty=lineTypes[i], lwd=2)
		lines(y~x, data=ss.df, col=colors[i], lty=lineTypes[i])		
	}

	legend<-sprintf("B=%s",levels(ss.df$BF)) 
	legend("topright",legend=legend, lty=lineTypes, lwd=2,col=colors, cex=1.5,inset=0.05)
}

CalculatedLayout.plotReplicateBreakdownE<-function(ratio.df, filter.L, filter.A, nReplicates) {
	ss.df<-subset(ratio.df, L==filter.L)
	ss.df<-subset(ss.df, A==filter.A)
	
	
	par(mar=c(7, 7, 5, 2))
	nE<-length(levels(ss.df$EF))

	plot(ratio.centre~replicate, pch=NA,axes=F,ann=F, data=ss.df,col="red", ylim=c(0,1), xlim=c(0, nReplicates))
	axis(1, cex.axis=1.5)
	axis(2, cex.axis=1.5)
	box()
#	title(main=sprintf("Each Replicate, n=%d", nReplicates), cex.main=1.5)
	mtext(expression("Replicate"), side=1, line=2.5, cex=1.5)
	mtext(CalculatedLayout.ratioCaption(), side=2, line=4, cex=1.5)

	colors<-c("blue", "red", "green", "orange", "yellow")
	lineTypes<-rep(1, nE)
	for (i in 1:nE) {
		i.E<-levels(ss.df$EF)[[i]]
		x<-ss.df$replicate[ss.df$EF==i.E]
		y<-ss.df$ratio.centre[ss.df$EF==i.E]
	
		mean.y<-mean(y)
		lines(c(0, max(x)), c(mean.y, mean.y), col="grey", lty=lineTypes[i], lwd=2)
		lines(y~x, data=ss.df, col=colors[i], lty=lineTypes[i])		
	}

	legend<-sprintf("E=%s",levels(ss.df$EF)) 
	legend("topright",legend=legend, horiz=T,lty=lineTypes, lwd=2,col=colors, cex=1.2,inset=0.025)
}

#ratio.df<-report@ratio.df;filter.A<-0.5;report@ratio.df$AF;par(mfrow=c(1,1))
CalculatedLayout.plotLvsBForA<-function(ratio.df, filter.A, zoom=F, zoomYLim=c()) {
	ss.df<-subset(ratio.df, A==filter.A)

	stats.list<-SummaryStatistics.createSummaryStatistics(ss.df,groupingFactorName="LF", xFactorName="BF" ,yFactorName="ratio.centre")
	par(mar=c(7, 7, 5, 2))

	if (zoom) {
		if (length(zoomYLim)==2) {
			yLim<-zoomYLim
		} else {
			yLim<-SummaryStatistics.getYRange(stats.list)
		}
	} else {
		yLim<-c(0.1, 0.6)
	}
	
	i.first.df<-stats.list[[1]]
	
	par(mar=c(7, 7, 2, 2))
	plotFirstDataFrame(i.first.df, yLim, col="blue")
	
	
	if (length(stats.list)>1) {
		lineColors <- c("red", "green", "brown", "purple", "yellow", "purple", "grey")
		pointSymbols<-c(15, 17, 24, 22, 23, 15, 24, 25)

		legends<-levels(as.factor(ratio.df$L))
		
		plotRemainingDataFrames(stats.list[2:length(stats.list)], lineColors, pointSymbols)
		
		legend("topright",legend=sprintf("L=%0.0f  ", legends),x.intersp=0.6, lty=1,lwd=1.5,
		 horiz=T,inset=0.03, col=c("blue", lineColors), pch=c(19, pointSymbols), bty="o", pt.cex=2, cex=1.5)
	}

	
	mtext(side=1, expression("Release Distance (B)"), cex=1.8, line=4)
	mtext(side=2, CalculatedLayout.ratioCaption(), cex=1.8, line=4)	
}

#stats.df<-i.first.df;col<-"blue"
plotFirstDataFrame<-function(stats.df, yLim, col) {
	x<-as.numeric(levels(stats.df$X)[as.numeric(stats.df$X)])+1
	y<-stats.df$MEAN
	yPlus<-stats.df$PLUS_ERR
	yMinus<-stats.df$MINUS_ERR
	
	plot(y~x, pch=NA, ylim=yLim, log="x", xlim=c(1,800), axes=F, ann=F)
	box()
	axis(1, at=x, labels=x-1, cex.axis=1.5)
	axis(2, cex.axis=1.5)
	lines(y~x, col=col, lwd=2)
	SummaryStatistics.plotErrorBars(x, yPlus, yMinus, col=col, lwd=2)
	points(y~x, col=col, cex=2, pch=19)
}

#stats.list<-stats.list[2:5]
plotRemainingDataFrames<-function(stats.list, cols, pchs) {
	
	i<-1
	for (stats.df in stats.list) {
		x<-as.numeric(levels(stats.df$X)[as.numeric(stats.df$X)])+1
		y<-stats.df$MEAN
		yPlus<-stats.df$PLUS_ERR
		yMinus<-stats.df$MINUS_ERR
	
		lines(y~x, col=cols[i], lwd=1.5)
		SummaryStatistics.plotErrorBars(x, yPlus, yMinus, col=cols[i], lwd=2)
		points(y~x, col=cols[i], cex=2, pch=pchs[i])
		
		i<-i+1
	}
}
#xlabel<-"Egg Limit"


CalculatedLayout.plotSD<-function(stats.df, xlabel) {
	x<-as.numeric(levels(stats.df$X))
	par(mar=c(7, 7, 5, 2))
	
	plot(stats.df$SD~x, type="p",pch=19, ann=F, col="blue", xlim=c(0, max(x)), ylim=c(0, max(stats.df$SD)))
	lines(stats.df$SD~x, lty=2,col="blue" )
	mtext(side=1, substitute(a, list(a=xlabel)), cex=1.5, line=3)
	mtext(side=2, expression("s.d."), cex=1.5, line=4)
}

CalculatedLayout.plotLvsEForA<-function(ratio.df, filter.A) {
	ss.df<-subset(ratio.df, A==filter.A)

	stats.list<-SummaryStatistics.createSummaryStatistics(ss.df,groupingFactorName="LF", xFactorName="EF" ,yFactorName="ratio.centre")
	legends<-levels(as.factor(ratio.df$L))
	par(mar=c(7, 7, 5, 2))

	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendFormatString="L=%0.0f", legendList=legends, noDecoration=T, yLim=c(0, 1), minXZero=F)
	mtext(side=1, expression("Egg Limit (G)"), cex=1.5, line=3)
	mtext(side=2,CalculatedLayout.ratioCaption() , cex=1.5, line=4)
}

CalculatedLayout.plotBoxPlotBforLA<-function(ratio.df, filter.L, filter.A) {
	ss.df<-subset(ratio.df, L==filter.L)
	ss.df<-subset(ss.df, A==filter.A)
	
	par(mar=c(7, 7, 5, 2))
	
	plot(ratio.centre~BF, data=ss.df,xlab="", ylab="",notch=T, ann=F, ylim=c(0,1), cex.axis=1.2, cex.lab=1.2)
	mtext(side=1, expression("Release Distance (B)"), cex=1.5, line=3)
	mtext(side=2, CalculatedLayout.ratioCaption(), cex=1.5, line=4)
}

CalculatedLayout.plotBoxPlotEforLA<-function(ratio.df, filter.L, filter.A) {
	ss.df<-subset(ratio.df, L==filter.L)
	ss.df<-subset(ss.df, A==filter.A)
	
	par(mar=c(7, 7, 5, 2))
	
	plot(ratio.centre~EF, data=ss.df,xlab="", ylab="",notch=T, ann=F, ylim=c(0,1), cex.axis=1.2, cex.lab=1.2)
	mtext(side=1, expression("Egg Limit (E)"), cex=1.5, line=3)
	mtext(side=2, CalculatedLayout.ratioCaption(), cex=1.5, line=4)
}


CalculatedLayout.plotReplicateSD<-function(ratio.df, filter.L, filter.A, sampleSize) {
	ss.df<-subset(ratio.df, ratio.df$L==filter.L)
	ss.df<-subset(ss.df, ratio.df$A==filter.A)
	stats.full.df<-SummaryStatistics.createUngroupedSummaryStatistics(ss.df, xFactorName="EF" ,yFactorName="ratio.centre")
	rep.count<-max(ss.df$replicate)
	incr<-rep.count/4
	rep.samples<-seq(from=incr, to=rep.count, by=incr)

	statName<-"SD"

	samples<-sampleSize

	ys.df<-CalculatedLayout.sampleReplicatesFromSubset(ss.df, rep.samples, statName, samples)
	g.stats.list<-SummaryStatistics.createSummaryStatistics(ys.df, xFactorName="rep.count.F" , groupingFactorName="EF", yFactorName="stat")

	CalculatedLayout.plotReplicateSamples(g.stats.list, rep.samples, stats.full.df)
}

CalculatedLayout.plotReplicateSamples<-function(g.stats.list, rep.samples, stats.full.df) {
	errCol="black"
	ys<-list()
	plusErr<-list()
	minusErr<-list()
	for (i.stat in g.stats.list) {
		ys<-appendToList(ys, i.stat$MEAN)
		plusErr<-appendToList(plusErr, i.stat$PLUS_SD)
		minusErr<-appendToList(minusErr, i.stat$MINUS_SD)
	}

	par(mar=c(7, 7, 5, 2))
	x<-rep.samples
	maxY<-max(sapply(plusErr, max))
	linecols<-c("blue", "red","green", "orange", "brown")
	plot(ys[[1]]~x, ylim=c(0, maxY), xlim=c(min(x), max(x)), pch=NA, ann=F, axes=F)
	for (i.X in levels(stats.full.df$X)) {
		varY<-stats.full.df[[statName]][stats.full.df$X==i.X]
		lines(c(min(x), max(x)), c(varY, varY), col="grey", lwd=2)
	}
	
	plotErrorBars(x,ys[[1]],plusErr[[1]],minusErr[[1]], add=TRUE, col=linecols[1])
	
	lines(ys[[1]]~x, lty=2, col=linecols[1])
	points(ys[[1]]~x, lty=2, col=linecols[1], pch=19)

	for (i.y in 2:length(ys)) {
		plotErrorBars(x,ys[[i.y]],plusErr[[i.y]],minusErr[[i.y]], add=TRUE, col=linecols[i.y])
		
		lines(ys[[i.y]]~x, lty=2, col=linecols[i.y])
		points(ys[[i.y]]~x, lty=2, col=linecols[i.y], pch=19)
	
	}
	axis(1, cex.axis=1.5)
	axis(2, cex.axis=1.5)
	box()
	mtext(side=1, expression("Replicates"), cex=1.5, line=3)
	mtext(side=2, expression("s.d."), cex=1.5, line=4)
	legends<-levels(stats.full.df$X)
	legend("topright",horiz=T, legend=sprintf("%0.0f", legends),pch=19, lty=rep(2, length(stats.full.df$X)), col=linecols,cex=1.2,inset=0.025)
}


CalculatedLayout.sampleReplicatesFromSubset<-function(ss.df, rep.samples, statName, samples) {
	ys.agg.df<-NULL
	cat("Resampling replicates ", samples, " times\n")
	
	for (i.replicate in 1:samples) {
		ys<-CalculatedLayout.sampleReplicates(ss.df, rep.samples, xFactorName="EF", yFactorName="ratio.centre", sampleStatName=statName)
	
		N<-length(levels(ss.df$EF))*length(rep.samples)
		ys.df<-data.frame("Replicate"=rep(i.replicate, N))
		ys.df$E<-NA
		ys.df$rep.count<-0
		ys.df$stat<-0
	
		i.row<-1
		for (i.E in 1:length(levels(ss.df$EF))) {
			i.ys<-ys[[i.E]]
			for (i.sample in 1:length(i.ys)) {			
				ys.df$E[i.row]<-as.numeric(levels(ss.df$EF)[i.E])
				ys.df$rep.count[i.row]<-rep.samples[i.sample]
				ys.df$stat[i.row]<-i.ys[[i.sample]]
				i.row<-i.row+1
			}
		}
		
		if (is.null(ys.agg.df)) {
			ys.agg.df<-ys.df
		} else {
			ys.agg.df<-merge(ys.agg.df, ys.df, all=T)
		}
	}
	
	ys.agg.df$rep.count.F<-as.factor(ys.agg.df$rep.count)
	ys.agg.df$EF<-as.factor(ys.agg.df$E)
	return (ys.agg.df)
}

CalculatedLayout.sampleReplicates<-function(data.df, rep.samples, xFactorName, yFactorName, sampleStatName) {
	stats.list<-list()
	for (i.sample in rep.samples) {
		ids<-sample(1:rep.count, i.sample)
		sample.df<-subset(data.df, data.df$replicate%in%ids)
		stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(sample.df, xFactorName=xFactorName ,yFactorName=yFactorName)
		stats.list<-appendToList(stats.list, stats.df)
	}


	ys<-list()
	for (i.level in levels(data.df$EF)) {
		vars<-c()
		for (stats.df in stats.list) {
			variance<-stats.df[[sampleStatName]][stats.df$X==i.level]
			vars<-c(vars, variance)
		}
		ys<-appendToList(ys, vars)
	}
	return (ys)
}

CalculatedLayout.plotRatioVsLayout<-function(ratio.df, includeEdge=T, ...) {
	levels.R<-levels(ratio.df$RF)
	levels.P<-levels(ratio.df$PF)
	levels.L<-levels(ratio.df$LF)
	levels.A<-levels(ratio.df$AF)
	graphIds<-letters[1:(length(levels.P) * length(levels.R))]

	par(mfrow=c(length(levels.P), length(levels.R)))
	i<-1
	for (i.R in levels.R) {
		for (i.P in levels.P) {
			S<-Resourcelayout.calculateSeparationFromPatchSize(patchSize=as.numeric(i.P),radius=as.numeric(i.R), countX=4 )
			
			title<-sprintf("%s) R=%0.0f, P=%0.0f, S=%0.0f",graphIds[i], i.R, i.P, S)
			legend<-(i==1)
			if (includeEdge) {
				linesY<-c(0.25, 0.25)
			} else {
				linesY<-c(0.5, 0.5)
			}
			
			CalculatedLayout.plotResponseVsLForA(ratio.df, i.P, i.R, response="ratio.centre" ,
					linesY=linesY,legendPosition="topright", title=title, xaxisLabel="",
					 yaxisLabel="", legend=legend, yLim=c(0, 1),...)
					
			at.y<-c(0, 0.5, 1)
			axis(2, at=at.y, cex.axis=1.5)
			i<-i+1
			
		}
	
	}
}

 
#i.R<-50;i.P<-100
CalculatedLayout.plotSuccessVsLayout<-function(ratio.df, ...) {
	levels.R<-levels(ratio.df$RF)
	levels.P<-levels(ratio.df$PF)
	levels.L<-levels(ratio.df$LF)
	levels.A<-levels(ratio.df$AF)
	levels.AREA<-levels(ratio.df$AREAF)
	graphIds<-letters[1:(length(levels.P) * length(levels.R))]

	par(mfrow=c(length(levels.P), length(levels.R)))
	i<-1
	for (i.R in levels.R) {		
		for (i.P in levels.P) {
			S<-Resourcelayout.calculateSeparationFromPatchSize(patchSize=as.numeric(i.P),radius=as.numeric(i.R), countX=4 )
			ss.df<-subset(ratio.df, ratio.df$R==i.R)
			AREA<-levels.AREA[ss.df$AREA[ss.df$S==S]][1]
			title<-sprintf("%s) R=%0.0f, P=%0.0f, S=%0.0f, AREA=%s",graphIds[i], i.R, i.P, S, AREA)
			legend<-(i==1)
			linesY<-c(1, 1)
			yLim<-c(0, max(ratio.df$foragers.success))
			CalculatedLayout.plotResponseVsLForA(ratio.df, i.P, i.R, response="foragers.success", 
					linesY=linesY, legendPosition="topright", title=title, xaxisLabel="", yaxisLabel="", 
					legend=legend,yLim=yLim,...)
			axis(2, cex.axis=1.5)
			
			i<-i+1
		}
	
	}
}



#filter.P<-"100";filter.R="5"
CalculatedLayout.plotResponseVsLForA<-function(ratio.df, filter.P, filter.R,response, 
		 linesY, at.y,legend=T, ...) {
	ss.df<-subset(ratio.df, ratio.df$P==filter.P)
	ss.df<-subset(ss.df, ss.df$R==filter.R)

	stats.list<-SummaryStatistics.createSummaryStatistics(ss.df,groupingFactorName="AF", xFactorName="LF" ,yFactorName=response)
	par(mar=c(3, 3, 5, 1))

	if (legend) {
		legends<-levels(ratio.df$AF)
	} else {
		legends<-c()
	}
	linesX<-c(min(ss.df$L), max(ss.df$L))
	
	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendHoriz=T, 
		legendFormatString="k=%0.1f", legendList=legends, noDecoration=F, 
		 minXZero=F,plotAxes=F,linesX=linesX, linesY=linesY,...)

		axis(1, cex.axis=1.5)


}

CalculatedLayout.plotRatioVsLandA<-function(ratio.df, ...) {
	stats.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="AF", xFactorName="LF" ,yFactorName="ratio.centre")
	
	yLim<-SummaryStatistics.getYRange(stats.list, fixedMin=0, expansionFactor=0.15)
	xLim<-SummaryStatistics.getXRange(stats.list)
	
	par(mar=c(7, 7, 5, 5))
	legends=levels(ratio.df$AF)
	at.Y<-SummaryStatistics.simpleYat(yLim)	
	linesX<-c(xLim[1], xLim[2])
	linesY<-c(at.Y[2], at.Y[2])
	
	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendPosition="topright", legendHoriz=T, 
		legendFormatString="k=%0.1f", legendList=legends, lineTypes=rep(1, length(stats.list)),
		 noDecoration=F,plotAxes=F, cex.legend=1.5,linesX=linesX, linesY=linesY,
		yLim=yLim, minXZero=F, xaxisLabel="", yaxisLabel="")
	axis(1, cex.axis=1.5)
	
	axis(2, at=at.Y, label=c("0", sprintf("%0.1f", at.Y[2:3])),cex.axis=1.5)

	mtext(side=1, line=4, expression("Step Length (L)"), cex=2)
	mtext(side=2, line=4, CalculatedLayout.ratioCaption(), cex=2)
	
}

CalculatedLayout.plotSuccessVsLandA<-function(ratio.df, filter.G, ...) {
	stats.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="AF", xFactorName="LF" ,yFactorName="foragers.success")
	
	yLim<-SummaryStatistics.getYRange(stats.list, fixedMin=0, expansionFactor=0.15)
	xLim<-SummaryStatistics.getXRange(stats.list)
	 
	par(mar=c(7, 7, 5, 5))
	legends=levels(ratio.df$AF)
	at.Y<-SummaryStatistics.simpleYat(yLim)		
	linesX<-c(xLim[1], xLim[2])
	linesY<-c(at.Y[2], at.Y[2])
	
	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendPosition="topright",  legendHoriz=T, 
		legendFormatString="k=%0.1f", legendList=legends, lineTypes=rep(1, length(stats.list)),
		noDecoration=F,plotAxes=F,linesX=linesX, linesY=linesY, cex.legend=1.5,
		yLim=yLim, minXZero=F, xaxisLabel="", yaxisLabel="")
	axis(1, cex.axis=1.5)
	axis(2, cex.axis=1.5)

	mtext(side=1, line=4, expression("Step Length (L)"), cex=2)
	mtext(side=2, line=4, CalculatedLayout.efficiencyCaption(), cex=2)
	
}

CalculatedLayout.plotRatioVsLayoutSinuosity<-function(ratio.df,  ...) {
	CalculatedLayout.plotFactorVsLayoutSinuosity(ratio.df, factorName="ratio.centre")
}

CalculatedLayout.plotSuccessVsLayoutSinuosity<-function(ratio.df,  ...) {
	CalculatedLayout.plotFactorVsLayoutSinuosity(ratio.df, factorName="foragers.success")
}

CalculatedLayout.plotRatioVsLayoutSinuositySummary<-function(ratio.df,  ...) {
	CalculatedLayout.plotFactorVsLayoutSinuositySummary(ratio.df, factorName="ratio.centre", yLabel=CalculatedLayout.ratioCaption())
}

CalculatedLayout.plotSuccessVsLayoutSinuositySummary<-function(ratio.df,  ...) {
	CalculatedLayout.plotFactorVsLayoutSinuositySummary(ratio.df, factorName="foragers.success", yLabel=CalculatedLayout.efficiencyCaption())
}

#ratio.df<-
CalculatedLayout.plotFactorVsLayoutSinuosity<-function(ratio.df, factorName, includeEdge=T, ...) {
	levels.R<-levels(ratio.df$RF)
	levels.P<-levels(ratio.df$PF)
	levels.L<-levels(ratio.df$LF)
	levels.A<-levels(ratio.df$AF)
	graphIds<-letters[1:(length(levels.P) * length(levels.R))]

	i<-1
	stats.list<-list()
	for (i.R in levels.R) {
		for (i.P in levels.P) {
			S<-Resourcelayout.calculateSeparationFromPatchSize(patchSize=as.numeric(i.P),radius=as.numeric(i.R), countX=4 )
						
			ss.df<-subset(ratio.df, ratio.df$P==i.P)
			ss.df<-subset(ss.df, ss.df$R==i.R)
			ss.df$SINF<-as.factor(round(ss.df$SIN, 3))

			stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(ss.df, xFactorName="SINF" ,yFactorName=factorName)
			
			if (length(stats.list)==0) {			
				stats.list<-list(stats.df)
			} else {
				stats.list<-collection.appendToList(stats.list, stats.df)
			}
		}
	}
	
	layout.mx<-rbind(c( 1, 2, 3, 4),
					 c( 5, 6, 7, 8),
					 c( 9,10, 11,12),
					 c(13,14,15,16))
					
	layout(layout.mx, widths=c(5, 30, 30, 30), heights=c(30, 30, 30, 5))	

	yLim<-SummaryStatistics.getYRange(stats.list, expansionFactor=0.1, fixedMin=0)
	xLim<-SummaryStatistics.getXRange(stats.list)

	par(mar=c(0, 0, 0, 0))
	plot.new()#1
	yLabel<-substitute(a, list(a=sprintf("R=%0.0f", levels.R[[1]])))
	mtext(yLabel, side=2, cex=1.5, line=-4)

	plotStat(stats.list[[1]], xLim, yLim, "a)")
	plotStat(stats.list[[2]], xLim, yLim, "b)")
	plotStat(stats.list[[3]], xLim, yLim, "c)")

	par(mar=c(0, 0, 0, 0))
	plot.new()#5
	yLabel<-substitute(a, list(a=sprintf("R=%0.0f", levels.R[[2]])))
	mtext(yLabel, side=2, cex=1.5, line=-4)

	plotStat(stats.list[[4]], xLim, yLim, "d)")
	plotStat(stats.list[[5]], xLim, yLim, "e)")
	plotStat(stats.list[[6]], xLim, yLim, "f)")

	par(mar=c(0, 0, 0, 0))
	plot.new()#9
	yLabel<-substitute(a, list(a=sprintf("R=%0.0f", levels.R[[3]])))
	mtext(yLabel, side=2, cex=1.5, line=-4)

	plotStat(stats.list[[7]], xLim, yLim, "g)")
	plotStat(stats.list[[8]], xLim, yLim, "h)")
	plotStat(stats.list[[9]], xLim, yLim, "i)")

	par(mar=c(0, 0, 0, 0))
	plot.new()#13
	
	plot.new()#14
	xLabel<-substitute(a, list(a=sprintf("P=%0.0f", levels.P[[1]])))
	mtext(xLabel, side=1, cex=1.5, line=-4)

	plot.new()#15
	xLabel<-substitute(a, list(a=sprintf("P=%0.0f", levels.P[[2]])))
	mtext(xLabel, side=1, cex=1.5, line=-4)
	
	plot.new()#16
	xLabel<-substitute(a, list(a=sprintf("P=%0.0f", levels.P[[3]])))
	mtext(xLabel, side=1, cex=1.5, line=-4)
	

			
}

#par(mfrow=c(1,1));factorName<-"foragers.success"
CalculatedLayout.plotFactorVsLayoutSinuositySummary<-function(ratio.df, factorName, yLabel=factorName,includeEdge=T, ...) {

	ratio.df$SINF<-as.factor(round(ratio.df$SIN, 3))
	stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(ratio.df, xFactorName="SINF" ,yFactorName=factorName)
	stats.list<-list(stats.df)
	

	yLim<-SummaryStatistics.getYRange(stats.list, expansionFactor=0.1, fixedMin=0)
	xLim<-SummaryStatistics.getXRange(stats.list)

	plotStat(stats.df, xLim, yLim, spaceForLabels=T)
	
	partA<-"Sinuosity ("
	partB<-"*"
	partC<-")"
	mtext(side=1, line=5, substitute(paste(a, S*b, c), list(a=partA, b=partB, c=partC)), cex=2)
	mtext(side=2, line=5, substitute(a, list(a=yLabel)), cex=2)
	
}

#spaceForLabels=T
plotStat<-function(stats.df, xLim, yLim, title=NA, spaceForLabels=F) {
	x<-SummaryStatistics.asNumeric(stats.df$X)
	y<-stats.df$MEAN
	yMinus<-stats.df$MINUS_ERR
	yPlus<-stats.df$PLUS_ERR

	if (spaceForLabels) {
		lb<-7
	} else {
		lb<-5
	}

	if (is.na(title)) {
		par(mar=c(lb, lb, 2, 2))
	} else {
		par(mar=c(lb, lb, 4, 2))
	}
	
	
	plot(y~x, pch=NA, ylim=yLim, xlim=range(x), ann=F, axes=F, log="x")
	box()
			
	axis(1, at=x, label=sprintf("%0.2f", x), cex.axis=2, mgp=c(3, 1.5, 0))

	at.Y<-SummaryStatistics.simpleYat(yLim)
	axis(2, at=at.Y, label=c(0, sprintf("%0.2f", at.Y[2:3])), cex.axis=2)

	lines(c(xLim[1], xLim[2]),c(at.Y[2], at.Y[2]), col="black", lty=2,lwd=1.5  )
	lines(y~x, col="blue", lwd=1.5)
	points(y~x, col="blue", pch=19, cex=2)
	SummaryStatistics.plotErrorBars(x, yMinus, yPlus, col="blue", lwd=1.5)
	
	mtext(side=3, line=1,substitute(a, list(a=title)), cex=1.5)
	
}

#filter.P=600;filter.R=15;response="ratio.centre"
CalculatedLayout.plotResponseVsSIN<-function(ratio.df, filter.P, filter.R,response, 
		 linesY, at.y, ...) {
	ss.df<-subset(ratio.df, ratio.df$P==filter.P)
	ss.df<-subset(ss.df, ss.df$R==filter.R)
	ss.df$SINF<-as.factor(round(ss.df$SIN, 3))

	stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(ss.df, xFactorName="SINF" ,yFactorName=response)
	stats.list<-list(stats.df)
	
	par(mar=c(3, 3, 5, 1))

	linesX<-c(min(ss.df$SIN), max(ss.df$SIN))
	labels<-round(as.numeric(levels(ss.df$SINF)), 3)
	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendHoriz=T, 
		legendFormatString="", legendList=c(),lineTypes=rep(1, length(stats.list)) ,noDecoration=F, 
		 minXZero=T,plotAxes=F)


}






CalculatedLayout.plotRatioVsSeparationByA<-function(ratio.df, ...) {
	levels.R<-levels(ratio.df$RF)
	levels.A<-levels(ratio.df$AF)
	
	graphIds<-letters[1:3]
	par(mfrow=c(1,3))
	i<-1

	for (i.A in levels.A)	{
		title<-sprintf("%s) k=%0.1f", graphIds[i], i.A)
		CalculatedLayout.plotRatioVsSeparationByAGraph(ratio.df, i.A, xLim=c(-100, 150), plotLegend=(i==1),xaxisLabel="", yaxisLabel="",title=title, ...)
		i<-1+1
	}

}

#filter.A<-10
CalculatedLayout.plotRatioVsSeparationByAGraph<-function(ratio.df, filter.A, plotLegend=T,includeEdge=F,...) {
	ss.df<-subset(ratio.df, ratio.df$A==filter.A)
	ss.df$SF<-as.factor(ss.df$S)
	
	stats.list<-SummaryStatistics.createSummaryStatistics(ss.df,groupingFactorName="LF", xFactorName="SF" ,yFactorName="ratio.centre")

	if (plotLegend) {
		legends<-levels(ratio.df$LF)
	} else {
		legends<-c()
	}
	linesX<-c(min(ss.df$S), max(ss.df$S))
	if (includeEdge) {
		linesY<-c(0.25, 0.25)
	} else {
		linesY<-c(0.5, 0.5)
	}

#	par(mar=c(7, 7, 5, 5))
	par(mar=c(3, 3, 5, 1))

	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendHoriz=T, 
		legendFormatString="L=%0.1f", legendList=legends, noDecoration=F,plotAxes=F, 
		yLim=c(0, 1),minXZero=F,linesX=linesX, linesY=linesY, cex.main=1.5, ...)
	axis(1, cex.axis=1.5)
	axis(2, at=c(0, 0.5, 1), cex.axis=1.5)
#	mtext("S",side=1, cex=1.5, line=4)
#	mtext("Ratio", side=2, cex=1.5, line=4)
}


CalculatedLayout.plotRatioVsSeparationByL<-function(ratio.df, ...) {
	levels.R<-levels(ratio.df$RF)
	levels.L<-levels(ratio.df$LF)
	
	graphIds<-letters[1:3]
	
	N.L<-length(levels.L)

	par(mfrow=c(ceiling(N.L/3),3))


	i<-1

	for (i.L in levels.L)	{
		title<-sprintf("%s) L=%0.1f", graphIds[i], i.L)
		CalculatedLayout.plotRatioVsSeparationByLGraph(ratio.df, i.L, xLim=c(-100, 150), plotLegend=(i==1), xaxisLabel="", yaxisLabel="",title=title, ...)
		i<-1+1
	}

}

#filter.L<-50
CalculatedLayout.plotRatioVsSeparationByLGraph<-function(ratio.df, filter.L,plotLegend=T,includeEdge=F, ...) {
	ss.df<-subset(ratio.df, ratio.df$L==filter.L)
	ss.df$SF<-as.factor(ss.df$S)
	
	stats.list<-SummaryStatistics.createSummaryStatistics(ss.df,groupingFactorName="AF", xFactorName="SF" ,yFactorName="ratio.centre")

	if (plotLegend) {
		legends<-levels(ratio.df$AF)
	} else {
		legends<-c()
	}
	linesX<-c(min(ss.df$S), max(ss.df$S))
	
	
	if (includeEdge) {
		linesY<-c(0.25, 0.25)
	} else {
		linesY<-c(0.5, 0.5)
	}
	

#	par(mar=c(7, 7, 5, 5))
	par(mar=c(3, 3, 5, 1))

	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendHoriz=T, 
		legendFormatString="k=%0.1f", legendList=legends, noDecoration=F,plotAxes=F, 
		yLim=c(0, 1),minXZero=F,linesX=linesX, linesY=linesY, cex.main=1.5, ...)
	axis(1, cex.axis=1.5)
	axis(2, at=c(0, 0.5, 1), cex.axis=1.5)
	
	mtext(side=1, expression("Release Distance (B)"), cex=1.8, line=4)
	mtext(side=2, CalculatedLayout.ratioCaption(), cex=1.8, line=4)	
	
#	mtext("S",side=1, cex=1.2, line=4)
#	mtext("Ratio", side=2, cex=1.2, line=4)
}


CalculatedLayout.plotRatioVsSeparationSummary<-function(ratio.df, ...) {
	stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(ratio.df, xFactorName="REL.SF" ,yFactorName="ratio.centre")

	
	
	x<-SummaryStatistics.asNumeric(stats.df$X)
	y<-stats.df$MEAN
	yPlus<-stats.df$PLUS_ERR
	yMinus<-stats.df$MINUS_ERR

	stats.list<-list(stats.df)
	yLim<-SummaryStatistics.getYRange(stats.list)
	xLim<-c(-10, 30)
	
	par(mar=c(7, 7, 2, 2))	
	plot(y~x, pch=NA, ann=F, axes=F, xlim=xLim, ylim=yLim)
	box()
	lines(y~x, col="blue", lwd=1.5)
	points(y~x, col="blue", cex=2, pch=19)
	axis(1, cex.axis=1.5)
	axis(2, cex.axis=1.5)
	SummaryStatistics.plotErrorBars(x, yPlus, yMinus, col="blue", lwd=1.5)
	

	
	mtext(side=1, 	expression(paste("Relative Resource Spacing (", I[R], ")")), cex=1.8, line=4)
	mtext(side=2, CalculatedLayout.ratioCaption(), cex=1.8, line=4)	

	
}
#ratio.df$AREA;par(mfrow=c(1,1))
#colnames(ratio.df)
#ratio.df$proportionOfPatchCovered<-ratio.df$areaCovered/ratio.df$P
#maxPatchArea<-max(ratio.df$P)^2
#ratio.df$REL.AREA<-(ratio.df$areaCovered*(ratio.df$P / maxPatchArea));ratio.df$REL.AREAF<-as.factor(ratio.df$REL.AREA)
CalculatedLayout.plotSuccessVsAreaSummary<-function(ratio.df, ...) {
	
	
	stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(ratio.df, xFactorName="REL.AREAF" ,yFactorName="foragers.success")

	
	
	x<-SummaryStatistics.asNumeric(stats.df$X)
	y<-stats.df$MEAN
	yPlus<-stats.df$PLUS_ERR
	yMinus<-stats.df$MINUS_ERR

	stats.list<-list(stats.df)
	yLim<-SummaryStatistics.getYRange(stats.list, fixedMin=0, expansionFactor=0.1)
	
	
	par(mar=c(7, 7, 2, 2))	
	plot(y~x, pch=NA, ann=F, axes=F, ylim=yLim, log="x")
	box()
	lines(y~x, col="blue", lwd=1.5)
	points(y~x, col="blue", cex=2, pch=19)
	axis(1, at=x,label=sprintf("%0.1f", x), cex.axis=1.5)
	axis(2, at=c(yLim[1], yLim[2]/2, yLim[2]), label=c("0", sprintf("%0.1f", c(yLim[2]/2, yLim[2]))),cex.axis=1.5)
	SummaryStatistics.plotErrorBars(x, yPlus, yMinus, col="blue", lwd=1.5)
	

	
	mtext(side=1, 	expression(paste("Relative Area (", R[P], ")")), cex=1.8, line=4.5)
	mtext(side=2, CalculatedLayout.efficiencyCaption(), cex=1.8, line=4)	

	
}

#perPlant=F;sum(obs.list$Centre)
CalculatedLayout.plotCornerCentreEdgeRatio<-function(ratio.df, perPlant=F,addLegend=T, ...) {
	if (perPlant) {		
		perplant.centre<-ratio.df$egg.centre/4
		perplant.edge<-ratio.df$egg.edge/8
		perplant.corner<-ratio.df$egg.corner/4
		perplant.total<-perplant.centre+perplant.edge+perplant.corner
		obs.list<-list(	"Centre"=perplant.centre/perplant.total, 
						"Corner"=perplant.corner/perplant.total,
						"Edge"=perplant.edge/perplant.total)
		
	} else {
		centre<-ratio.df$egg.centre
		edge<-ratio.df$egg.edge
		corner<-ratio.df$egg.corner
		total<-centre+edge+corner
		
		obs.list<-list(	"Centre"=ratio.df$egg.centre/total, 
						"Corner"=ratio.df$egg.corner/total,
						"Edge"=ratio.df$egg.edge/total)
	}
	
	xFactor<-as.factor(c("Centre", "Corner", "Edge"))
	
	stats.df<-SummaryStatistics.createSimpleSummaryStatistics(obs.list, xFactor)

	col.1<-rgb(red=0.1, green=0.1, blue=0.1)
	col.2<-rgb(red=0.6, green=0.6, blue=0.6)
	col.3<-rgb(red=0.9, green=0.9, blue=0.9) 
	colors<-c(col.1, col.2, col.3)

	labels<-c()
	for (i.X in levels(xFactor)) {
		label<-sprintf("%0.0f%%", stats.df$MEAN[stats.df$X==i.X]*100)
		labels<-c(labels, label)
	}

	pie(stats.df$MEAN[stats.df$MEAN>0], col=colors,clockwise=T, labels=labels, init.angle=90, cex=2, radius=.7)
	if (addLegend) {
		legend("top", legend=levels(xFactor), fill=colors, horiz=T , cex=1.8, inset=0.025)
	}
	box()
}


CalculatedLayout.plotSuccessVsAreaAndPatchSize<-function(ratio.df) {
	par(mfrow=c(1, 2))
	par(mar=c(7, 7, 5, 5))
	plot(ratio.df$foragers.success~ratio.df$AREA, xlab="", ylab="",ann=F, axes=F, ann=FALSE, ylim=c(0, max(ratio.df$foragers.success)))
	axis(1, at=1:length(levels(ratio.df$AREAF)), labels=levels(ratio.df$AREAF),  cex.axis=1.2)
	axis(2, cex.axis=1.2)
	box()
	title(main=expression("a)"), cex.main=1.5)
	mtext(side=1, expression("Area Covered"), line=4, cex=1.5)
	mtext(side=2, CalculatedLayout.efficiencyCaption(), line=4, cex=1.5)


	plot(ratio.df$foragers.success~ratio.df$PF, xlab="", ylab="",ann=F, axes=F, ann=FALSE, ylim=c(0, max(ratio.df$foragers.success)))
	axis(1, at=1:length(levels(ratio.df$PF)), labels=levels(ratio.df$PF),  cex.axis=1.2)
	axis(2, cex.axis=1.2)
	box()
	title(main=expression("b)"), cex.main=1.5)
	mtext(side=1, expression("Patch Size (P)"), line=4, cex=1.5)
	mtext(side=2, CalculatedLayout.efficiencyCaption(), line=4, cex=1.5)
}


CalculatedLayout.plotSuccessForPandRSummary_old<-function(ratio.df) {
	linesY<-c(1, 1)

	par(mfrow=c(1, 2))
	par(mar=c(7, 7, 5, 5))
	
	yLim=c(0, max(ratio.df$foragers.success) +(max(ratio.df$foragers.success)*.2))
	
	stats.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="PF", xFactorName="RF" ,yFactorName="foragers.success")
	
	legends<-levels(ratio.df$PF)	
	linesX<-c(0, max(ratio.df$R))	
	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendHoriz=T, 
		legendFormatString="P=%0.1f", legendList=legends, noDecoration=F, 
		 minXZero=F,plotAxes=F,xaxisLabel="", linesX=linesX, linesY=linesY,
		yaxisLabel="", yLim=yLim, xLim=c(0, 50))
	axis(1, at=c(0, 5, 15, 50), cex.axis=1.5 )
	axis(2, cex.axis=1.5)
	mtext(side=1, expression("Radius Of Attraction (R)"), line=4,cex=1.5)
	mtext(side=2, CalculatedLayout.efficiencyCaption(), line=4,cex=1.5)
	title(main=expresion("b)"), cex=1.5)



	stats.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="RF", xFactorName="PF" ,yFactorName="foragers.success")
	legends<-levels(ratio.df$RF)
	linesX<-c(0, max(ratio.df$P))	
	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendHoriz=T, 
		legendFormatString="R=%0.1f", legendList=legends, noDecoration=F, 
		 minXZero=F,plotAxes=F,xaxisLabel="", yLim=yLim, yaxisLabel="",
		linesX=linesX, linesY=linesY, xLim=c(0, 600))
	axis(1, at=c(0, 100, 300, 600), cex.axis=1.5 )
	axis(2, cex.axis=1.5)
	mtext(side=1, expression("Patch Size (P)"), line=4,cex=1.5)
	mtext(side=2, CalculatedLayout.efficiencyCaption(), line=4,cex=1.5)
	title(main="b) ", cex=1.5)
}

CalculatedLayout.plotSuccessForPandRSummary<-function(ratio.df, includeEdge=T) {
	par(mfrow=c(1, 2))
	par(mar=c(7, 7, 5, 5))
	if (includeEdge) {
		linesY<-c(0.25, 0.25)
	} else {
		linesY<-c(0.5, 0.5)
	}
	stats.R.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="PF", xFactorName="RF" ,yFactorName="foragers.success")
	stats.P.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="RF", xFactorName="PF" ,yFactorName="foragers.success")
	stats.all<-c(stats.P.list, stats.R.list)
	yLim<-SummaryStatistics.getYRange(stats.all, expansionFactor=0.2, fixedMin=0)
	
	layout.mx<-rbind(c(1, 2, 3),
					 c(4, 5, 6))
	
	layout(layout.mx, widths=c(5, 45, 45), heights=c(45, 5))
	par(mar=c(0, 0, 0, 0))
	plot.new()
	
	mtext(side=2, CalculatedLayout.efficiencyCaption(), cex=1.8, line=-5)	
	
	

	legends<-sprintf("%0.0f", levels(ratio.df$PF))	
	linesX<-c(0, max(ratio.df$R))	
	
	par(mar=c(2, 5, 5, 5))
	
	SummaryStatistics.plotManyResults(stats.R.list, plotErr=T, legendHoriz=T, legendPos="topright",
		legendFormatString="P=%0.0f   ", legendList=legends, noDecoration=F, lineTypes=rep(1, length(stats.P.list)),
		 minXZero=F,plotAxes=F,xaxisLabel="", yaxisLabel="", xLim=c(5, 50), yLim=yLim, cex.legend=2)
	
	axis(1, at=c(0, 5, 15, 50), cex.axis=2, mgp=c(3, 1.5, 0))
	axis(2, at=c(yLim[1], yLim[2]/2, yLim[2]), label=c("0", sprintf("%0.2f", c(yLim[2]/2, yLim[2]))), cex.axis=2)
	title(main=expression("a)"), cex.main=2.5)



	legends<-sprintf("%0.0f", levels(ratio.df$RF))
	SummaryStatistics.plotManyResults(stats.P.list, plotErr=T, legendHoriz=T, lineTypes=rep(1, length(stats.P.list)),
		legendFormatString="R=%0.0f   ", legendList=legends, noDecoration=F,cex.legend=2, legendPos="topright",
		 minXZero=F,plotAxes=F,xaxisLabel="", yaxisLabel="", xLim=c(100, 600), yLim=yLim)
	axis(1, at=c(100, 300, 600), cex.axis=2, mgp=c(3, 1.5, 0))
	axis(2, at=c(yLim[1], yLim[2]/2, yLim[2]), label=c("0", sprintf("%0.2f", c(yLim[2]/2, yLim[2]))), cex.axis=2)
	title(main=expression("b)"), cex.main=2.5)

	par(mar=c(0, 0, 0, 0))
	plot.new()
	plot.new()
	mtext(side=1, expression("Radius Of Attraction (R)"), line=-2,cex=1.7)

	plot.new()
	mtext(side=1, expression("Patch Size (P)"), line=-2,cex=1.7)
	
	
}



CalculatedLayout.plotRatioForPandRSummary<-function(ratio.df, includeEdge=T) {
	par(mfrow=c(1, 2))
	par(mar=c(7, 7, 5, 5))
	if (includeEdge) {
		linesY<-c(0.25, 0.25)
	} else {
		linesY<-c(0.5, 0.5)
	}
	stats.R.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="PF", xFactorName="RF" ,yFactorName="ratio.centre")
	stats.P.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="RF", xFactorName="PF" ,yFactorName="ratio.centre")
	stats.all<-c(stats.P.list, stats.R.list)
	yLim<-SummaryStatistics.getYRange(stats.all, expansionFactor=0.2, fixedMin=0)
	
	layout.mx<-rbind(c(1, 2, 3),
					 c(4, 5, 6))
	
	layout(layout.mx, widths=c(5, 45, 45), heights=c(45, 5))
	par(mar=c(0, 0, 0, 0))
	plot.new()
	
	mtext(side=2, CalculatedLayout.ratioCaption(), cex=1.8, line=-5)	
	
	

	legends<-sprintf("%0.0f", levels(ratio.df$PF))	
	linesX<-c(0, max(ratio.df$R))	
	
	par(mar=c(2, 5, 5, 5))
	
	SummaryStatistics.plotManyResults(stats.R.list, plotErr=T, legendHoriz=T, legendPos="topright",
		legendFormatString="P=%0.0f   ", legendList=legends, noDecoration=F, lineTypes=rep(1, length(stats.P.list)),
		 minXZero=F,plotAxes=F,xaxisLabel="", yaxisLabel="", xLim=c(5, 50), yLim=yLim, cex.legend=2)
	
	axis(1, at=c(0, 5, 15, 50), cex.axis=2, mgp=c(3, 1.5, 0))
	axis(2, at=c(yLim[1], yLim[2]/2, yLim[2]), label=c("0", sprintf("%0.2f", c(yLim[2]/2, yLim[2]))), cex.axis=2)
	title(main=expression("a)"), cex.main=2.5)



	legends<-sprintf("%0.0f", levels(ratio.df$RF))
	SummaryStatistics.plotManyResults(stats.P.list, plotErr=T, legendHoriz=T, lineTypes=rep(1, length(stats.P.list)),
		legendFormatString="R=%0.0f   ", legendList=legends, noDecoration=F,cex.legend=2, legendPos="topright",
		 minXZero=F,plotAxes=F,xaxisLabel="", yaxisLabel="", xLim=c(100, 600), yLim=yLim)
	axis(1, at=c(100, 300, 600), cex.axis=2, mgp=c(3, 1.5, 0))
	axis(2, at=c(yLim[1], yLim[2]/2, yLim[2]), label=c("0", sprintf("%0.2f", c(yLim[2]/2, yLim[2]))), cex.axis=2)
	title(main=expression("b)"), cex.main=2.5)

	par(mar=c(0, 0, 0, 0))
	plot.new()
	plot.new()
	mtext(side=1, expression("Radius Of Attraction (R)"), line=-2,cex=1.7)

	plot.new()
	mtext(side=1, expression("Patch Size (P)"), line=-2,cex=1.7)
	
	
}





