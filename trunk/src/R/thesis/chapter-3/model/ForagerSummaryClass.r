#Represents an iteration - parameters and such ...



#Class definition for an IterationReplicate class

setClass("ForagerSummary", representation(
	iteration="ExperimentIteration",
	replicate="IterationReplicate",
	summary.df="data.frame",
	lifehistory.df="data.frame",
	anglesOfTurn.df="data.frame",
	foragerCount="numeric"
))


#Add a constructor:
ForagerSummary<-function(replicate, iteration, replicateNumber) {
	instance<-new("ForagerSummary")
		
	instance@iteration<-iteration
	instance@replicate<-replicate
	instance@summary.df<-ForagerSummary.readReplicatesDataFrame(replicate, iteration@iterationNumber, replicateNumber)	
	instance@foragerCount<-length(instance@summary.df$Id)
	
	if (iteration@includeLifeHistory==TRUE) {
		instance@lifehistory.df<-ForagerSummary.readLifeHistoryDataFrame(replicate, iteration@iterationNumber, replicateNumber)	
		instance@anglesOfTurn.df<-subset(instance@lifehistory.df, instance@lifehistory.df$Behaviour=="Searching")
	}

	
	
	return (instance)
}

ForagerSummary.readReplicatesDataFrame<-function(replicate, iterationNumber, replicateNumber) {
	foragerSummaryFile<-sprintf("%s/Replicate-foragers.csv", replicate@replicateDir)
	cat("Reading Forager Summary [",  iterationNumber, " : ", replicateNumber, "] from ", foragerSummaryFile, " \n")
	if (file.exists(foragerSummaryFile) == FALSE) {
		stop("Cannot find forager Summary File!")
	}
	foragerSummary.df<-read.csv(foragerSummaryFile, header=TRUE, as.is=TRUE)#as.is stops it creating factors
	return (foragerSummary.df)
	
}

ForagerSummary.readLifeHistoryDataFrame<-function(replicate, iterationNumber, replicateNumber) {
	lifeHistoryFile<-sprintf("%s/Replicate-forager-lifehistories.csv", replicate@replicateDir)
	cat("Reading Forager Summary [",  iterationNumber, " : ", replicateNumber, "] from ", lifeHistoryFile, " \n")
	lifeHistory.df<-read.csv(lifeHistoryFile, header=TRUE, as.is=TRUE)#as.is stops it creating factors
	return (lifeHistory.df)
	
}

ForagerSummary.readForagers<-function(foragerSummary.df, replicateCount) {
	
#	replicates<-list()
#	if (replicateCount>0) {
#		for (replicateNumber in 1:replicateCount) {
#			instance<-IterationReplicate()
#			
#			#TODO: need to update this to deal with the multiple generation stuff...
#			subset.replicate.df<-subset(replicateStats.df, replicateStats.df$replicate==replicateNumber)
#			subset.df<-subset(replicateStats.df, replicateStats.df$type=="IMMIGRATION")
#			instance@replicateNumber<-subset.df$replicate
#			
#			replicates<-collection.appendToList(replicates, instance)
#		}				
#	}
#
#	return (replicates)
}




#Add a "toString" which is a "show method"
setMethod("show", "ForagerSummary", 
	function(object) {

		cat("iterationNumber    : ",object@replicate@iterationNumber, "\n")
		cat("replicateNumber    : ",object@replicate@replicateNumber, "\n")
		cat("foragerCount       : ",object@foragerCount, "\n")
		cat("hasLifeHistories   : ",object@iteration@includeLifeHistory, "\n")
		cat("azimuthGenerator   : ",object@iteration@azimuthGenerator, "\n")
		cat("angleOfTurn        : ",object@iteration@angleOfTurn, "\n")
		cat("moveLength         : ",object@iteration@moveLength, "\n")

	}
)


#And a "summary" method
setMethod("summary", "ForagerSummary", 
	function(object) {
	}
)

ForagerSummary.plotForagerTrail<-function(lh.df, foragerIndex, plotWidth, scalePos, L, scaleL,drawScale,drawBox=F,addResources=T, patchSize=50, resourceRadius=3,...) {

	id.factor<-as.factor(lh.df$Id)
	
	foragerId<-levels(id.factor)[foragerIndex]
	f.df<-subset(lh.df, lh.df$Id==foragerId)
	
	xlim<-range(f.df$X)
	ylim<-range(f.df$Y)
	
	
	# Try to make all plots the same width
	midX<-as.integer(min(xlim)+ ((max(xlim)-min(xlim)) /2) )
	midY<-as.integer(min(ylim)+ ((max(ylim)-min(ylim)) /2) )
		
	xlim<-c(as.integer(midX-plotWidth/2), as.integer(midX+plotWidth/2))
	ylim<-c(as.integer(midY-plotWidth/2), as.integer(midY+plotWidth/2))

	par(mar=c(2, 2, 2, 2))
	plot(f.df$Y~f.df$X, pch=NA, ann=F, axes=F, xlim=xlim, ylim=ylim)
	if (drawBox) {
		box()
	}
	if (addResources) {
		resources.df<-Resourcelayout.createLayout(patchSize, 4, midX-patchSize/2, midY-patchSize/2)
#		points(resources.df$x, resources.df$y, pch=16, col="grey", cex=2)		
		radii<-rep(resourceRadius, length(resources.df$x))
		symbols(resources.df$x, resources.df$y, circles=radii, bg="grey", fg=NA,inches=F, add=T)

	}
	lines(f.df$Y~f.df$X, lwd=1.5, col="blue")

	
	if (scalePos=="topright" && drawScale) {
		xs<-xlim[2]-(xlim[2]*.05)
		ys<-ylim[2]-(ylim[2]*.05)
		halfL<-((L*scaleL)/2)
		d<-halfL*2
	#	rect(xs-d, ys-d, xs+d, ys+d, col="white", border=NA)
		text(xs, ys, pos=1,sprintf("%0.0fL", scaleL),cex=1.5)

		lines(c(xs-halfL,xs+halfL), c(ys, ys), lwd=2, col="blue")
#		points(c(xs-halfL,xs+halfL), c(ys, ys), pch=16, cex=.5, col="blue")
	}
	if (drawBox) {
		box()
	}
}

ForagerSummary.createTrail<-function(n,  azimuths ,stepLength, startX, startY) {	
	path<-list()
	currentLocation<-c(startX, startY)
	path<-collection.appendToList(path, currentLocation)
	for (i in 1:n) {
		step<-stepLength
		azimuth<-azimuths[i]
		currentLocation<-coord.moveTo(currentLocation, azimuth, step)
		path<-collection.appendToList(path, currentLocation)			
	}
	
	return (path)
}

ForagerSummary.plotTrail<-function(x, y, plotWidth, scalePos, L, scaleL,drawScale,drawCentre=F,drawBox=F, title, addResources=F, patchSize=50, resourceRadius=3,...) {
	
	xlim<-range(x)
	ylim<-range(y)


	# Try to make all plots the same width
	midX<-as.integer(min(xlim)+ ((max(xlim)-min(xlim)) /2) )
	midY<-as.integer(min(ylim)+ ((max(ylim)-min(ylim)) /2) )

	xlim<-c(as.integer(midX-plotWidth/2), as.integer(midX+plotWidth/2))
	ylim<-c(as.integer(midY-plotWidth/2), as.integer(midY+plotWidth/2))

	if (is.na(title)) {
		par(mar=c(2, 2, 2, 2))
	} else {
		par(mar=c(1, 2, 4, 2))
	}
	plot(y~x, type="l", ann=F, axes=F, pch=NA,xlim=xlim, ylim=ylim)
	if (drawBox) {
		box()
	}
	if (addResources) {
		resources.df<-Resourcelayout.createLayout(patchSize, 4, midX-patchSize/2, midY-patchSize/2)
		radii<-rep(resourceRadius, length(resources.df$x))
		symbols(resources.df$x, resources.df$y, circles=radii, bg="grey", fg=NA,inches=F, add=T)
		if (drawCentre) {
			points(resources.df$x, resources.df$y, pch=16, col="black", cex=.5)		
		}

	}
	lines(y~x, lwd=1.5, col="blue")
	
	if (!is.na(title)) {
		title(main=title, cex.main=1.5)
	}

	if (scalePos=="topright" && drawScale) {
		xs<-xlim[2]-(xlim[2]*.05)
		ys<-ylim[2]-(ylim[2]*.05)
		halfL<-((L*scaleL)/2)
		d<-halfL*2
	#	rect(xs-d, ys-d, xs+d, ys+d, col="white", border=NA)
		text(xs, ys, pos=1,sprintf("%0.0fL", scaleL),cex=1.5)

		lines(c(xs-halfL,xs+halfL), c(ys, ys), lwd=2, col="blue")
#		points(c(xs-halfL,xs+halfL), c(ys, ys), pch=16, cex=.5, col="blue")
	}

}






