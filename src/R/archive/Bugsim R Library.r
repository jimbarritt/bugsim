

#library(Hmisc)
library(lattice)
source("~/Work/code/bugsim/src/R/archive/Summary Statistics Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/Geometry Library.r", echo=FALSE)
cat("Bugsim R Library Version 1.0\n")

#----------------------------------------------------------------------------------------------
#Function: ploteEdgeEffectRatios - draws a nice graph for us!
plotEdgeEffectRatios<-function(plot.df, factorToPlot, xaxisLabel, graphTitle) { 
		# Plot the graph ...
		pointType <- 19
		centreColor <- "blue"
		edgeColor <- "red"
		cornerColor <- "green" 
		
		plot(plot.df$CENTRE_RATIO, type="l", lty=2, col=centreColor, ylim=c(0,1), ann=FALSE, xaxt="n")
		title(main=graphTitle, ylab="ratioEggsPerPlant", xlab=xaxisLabel)
		points(plot.df$CENTRE_RATIO, pch=pointType, col=centreColor)
		lines(plot.df$EDGE_RATIO, type="l", lty=2, col=edgeColor)
		points(plot.df$EDGE_RATIO, pch=pointType, col=edgeColor)
		lines(plot.df$CORNER_RATIO, type="l", lty=2, col=cornerColor)
		points(plot.df$CORNER_RATIO, pch=pointType, col=cornerColor)
		axis(1, at=1:length(factorToPlot), labels=factorToPlot)
}





#input.df<-inp.df
#startIteration<-1
#endIteration<-5
#xFactor<-inp.df$B
#itr<-5
#controlFactor<-inp.df$iteration
#yFactorName<-"CENTRE_RATIO"
#levels(
#Given an input dataframe returns the summary stats.
getSummaryStats <- function(input.df, startIteration, endIteration, controlFactor,
			 xFactor, xFactorName, xLabel, yFactorName) {

	itrCount <- (endIteration - startIteration) + 1

	stats.df <- createStatsDataFrame(xFactor)
#	print(sprintf("Levels in X=%d", length(levels(stats.df$X))))

	for (itr in startIteration:endIteration) {	
		input.subset.df <- subset(input.df, controlFactor==itr)
		yFactor<-input.subset.df[[yFactorName]]
		i <- (itr-startIteration) + 1
		currentX<-levels(stats.df$X)[i]
		print(sprintf("Iteration %d, i=%d, currentX=%s", itr, i, currentX))
		
		stats.df <- populateStatsEntry(stats.df, yFactor, currentX)
	}		

	return (stats.df)	
}

extractCoordX<-function(input.str) {
	return (extractCoord(input.str)[[1]])
}
extractCoordY<-function(input.str) {
	return (extractCoord(input.str)[[2]])
}

extractCoord<-function(input.str) {
	split<-strsplit(input.str, ":")
	xstr<-split[[1]][[1]]
	ystr<-split[[1]][[2]]
	x<-as.numeric(strsplit(xstr, "=")[[1]][[2]])
	y<-as.numeric(strsplit(strsplit(ystr, "=")[[1]][[2]], ")")[[1]][[1]])
	return (list(x, y))
}

readBoundaryCrossings<-function(filename, experiment, trial) {
	cat(filename,", ", experiment, ", ",trial, "\n")
	directory<-createDirectoryName(filename, experiment, trial)
	boundaryFilename<-sprintf("%s/summary-boundary-%s-%s-%03d.csv", directory, filename, trial, experiment)
	cat("Reading boundary crossings:", boundaryFilename, "\n")
	
	boundary.df <- readBoundaryCrossingsFile(boundaryFilename)
	return (boundary.df)

}


readBoundaryCrossingsFile<-function(filename) {
	boundary.df <- read.csv(filename)

#   Cant do this with this information ....
#	boundary.df$DISTANCE_TO_BOUNDARY<-boundary.df$L-boundary.df$d	
#	boundary.df$DISTANCE_TO_CENTER<-boundary.df$DISTANCE_TO_BOUNDARY+boundary.df$r
	boundary.df$B<-factor(boundary.df$B)
	boundary.df$L<-factor(boundary.df$L)
	boundary.df$A<-factor(boundary.df$A)
	boundary.df$S<-factor(boundary.df$S)
	
#	startCoords<-levels(boundary.df$start)[boundary.df$start]
#	boundary.df$START_X<-apply(as.array(startCoords), 1, extractCoordX)
#	boundary.df$START_Y<-apply(as.array(startCoords), 1, extractCoordY)
	


	return (boundary.df)	
}


importExperimentPlan<-function(baseFilename, experimentNumber, trialId) {
	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)
	planFilename<-sprintf("%s/plan-%s-%s-%03d.csv", directory, baseFilename, trialId, experimentNumber)
	print(sprintf("Reading plan: %s", planFilename))

	plan.df <- read.csv(planFilename, as.is=TRUE)
	return (plan.df)
	
}



#startIteration=-1; endIteration=-1; summaryName="summary"; fileNumber=1
createSummaryDataFrame<-function(baseFilename, experimentNumber, trialId, startIteration=-1, endIteration=-1, summaryName="summary", fileNumber=1) {
	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)

	outputDir<-sprintf("%s/output", directory)
	cat("Creating output directory in : %s", outputDir, "\n")
	dir.create(outputDir)

	if (fileNumber >0) {
		fileNumberFormatted <-sprintf("-%03d", fileNumber)
	} else {
		fileNumberFormatted <- ""
	}
	summaryFilename<-sprintf("%s/%s-%s-%s-%03d%s.csv", directory, summaryName, baseFilename, trialId, experimentNumber, fileNumberFormatted)
	cat("Reading summary: ", summaryFilename)
	experiment.df <- read.csv(summaryFilename)	
	
	if (startIteration == -1) {
		experiment.subset.df<-experiment.df
		expSummary.df<-data.frame("row"=1:length(experiment.subset.df$iteration))
	} else {
		experiment.subset.df<-data.frame(experiment.df[startIteration:endIteration,])
		expSummary.df <- data.frame("row"=startIteration:endIteration)
	}
	results <- list(experiment.subset.df, expSummary.df) 

	return (results)
}
#filename<-"mdd-exp01b-011/summary-mdd-exp01b-011-001.csv"
#filename<-"L-1-A-10/summary-mdd-exp01b-007-001.csv"
#startIteration<-1
#endIteration<-itrBlock
#experimentNumber<-experimentId
#colnames(experiment.df)
#colnames(experiment.subset.df)
importIteration1bSummary <- function(baseFilename, experimentNumber, trialId, startIteration=-1, endIteration=-1) {

	dataframes <- createSummaryDataFrame(baseFilename, experimentNumber, trialId, startIteration, endIteration)
	
	experiment.df <- dataframes[[1]]
	expSummary.df <- dataframes[[2]]

	expSummary.df$iteration <- factor(experiment.df$iteration)
	expSummary.df$replicant <- experiment.df$replicant
	expSummary.df$timesteps <- experiment.df$executedTimesteps
	expSummary.df$elapsedTime<-experiment.df$elapsedTime
	
	expSummary.df$age <- factor(experiment.df$butterflyAgent.lifecycle.mortality.limitedAge.maxAge)
	expSummary.df$L <- factor(experiment.df$butterflyAgent.movementBehaviour.randomWalk.moveLength)
	expSummary.df$A <- factor(experiment.df$butterflyAgent.movementBehaviour.randomWalk.angleOfTurn)

	expSummary.df$N<-experiment.df$stats.dispsq.N
	expSummary.df$MSD_MEAN<-experiment.df$stats.dispsq.MEAN
	expSummary.df$MSD_SE<-experiment.df$stats.dispq.STD_ERR
	expSummary.df$MSD_MEAN_MINUS_ERR<-experiment.df$stats.dispsq.MEAN_MINUS_ERR
	expSummary.df$MSD_MEAN_PLUS_ERR<-experiment.df$stats.dispsq.MEAN_PLUS_ERR
	expSummary.df$MSD_MEAN_VAR<-experiment.df$stats.dispsq.VARIANCE
	
	
	return (expSummary.df)
}

#colnames(experiment.df)
importIteration1bDispersal <- function(baseFilename, experimentNumber, trialId, startIteration=-1, endIteration=-1) {

	dataframes <- createSummaryDataFrame(baseFilename, experimentNumber, trialId, startIteration, endIteration, summaryName="summary-dispersal", fileNumber=0)
	
	experiment.df <- dataframes[[1]]
	expSummary.df <- dataframes[[2]]

	expSummary.df$iteration <- factor(experiment.df$iteration)
	expSummary.df$replicant <- experiment.df$replicant
	expSummary.df$X <- factor(experiment.df$age)
	expSummary.df$N<-experiment.df$dispsq.N
	expSummary.df$MEAN<-experiment.df$dispsq.MEAN
	expSummary.df$SE<-experiment.df$dispq.STD_ERR
	expSummary.df$MEAN_MINUS_ERR<-experiment.df$dispsq.MEAN_MINUS_ERR
	expSummary.df$MEAN_PLUS_ERR<-experiment.df$dispsq.MEAN_PLUS_ERR
	expSummary.df$VARIANCE<-experiment.df$dispsq.VARIANCE
	
	
	return (expSummary.df)
}

#Imports butterfly Statistics from the summary file
#startIteration<-1
importButterflyStats<-function(baseFilename, experimentId, trialId, startIteration, endIteration) {
	s.df<-importIteration1bSummary(baseFilename, experimentId, trialId, startIteration, endIteration)
	stats.df<-createStatsDataFrame(s.df$age)
	
	stats.df$N<-s.df$N
	stats.df$MEAN<-s.df$MSD_MEAN
	stats.df$MINUS_ERR<-s.df$MSD_MEAN_MINUS
	stats.df$PLUS_ERR<-s.df$MSD_MEAN_PLUS
	stats.df$VARIANCE<-s.df$MSD_MEAN_VAR
	return(stats.df)
}

#----------------------------------------------------------------------------------------------
#Function: generatePallette
#Generates a set of colours between a hue range and reverses the order if you specify reverse=TRUE
#The range will have a predefined "zero" color.
#nColors<-11
#startHue<-.5
#endHue<-.6

#Hues: 
#0.5 - 0.6 blues
#zeroColor<-"#9999FF"
#zeroColor<-"white"
#e.g. colors.vector<-generatePalette(11, "#9999FF", .2, .4, reverse=FALSE)
generatePalette<-function(nColors, zeroColor, startHue, endHue, reverse=FALSE) {
	cat("ZeroColor is ", zeroColor)
	colors.vector<-rainbow(nColors, start=startHue, end=endHue)
	colors.vector[[1]]<-zeroColor

	if (reverse==TRUE) {
		temp.vector<-vector(length=nColors)
		temp.vector[[1]]<-colors.vector[[1]]
		iNewIndex<-nColors
		iColor<-2
		while(iColor<=nColors) {
			temp.vector[[iNewIndex]]<-colors.vector[[iColor]]
			iColor<-iColor+1
			iNewIndex<-iNewIndex-1
		}
		colors.vector<-temp.vector
	}
	return (colors.vector)
}

#----------------------------------------------------------------------------------------------
#Function: plotImageLegend - plots a legend showing the scale of colors for an image plot
#colors<-colors.v
#yLabels<-c(0:10)/10
#yData<-c(0:10)
#yBreakSize<-.1
plotImageLegend<-function(yData, yLabels, yBreakSize, colors) {
	nY<-length(yData)
	legend.mx<-matrix(0, nrow=1,ncol=nY)
	legend.mx[1, ]<-yData

	x<-c(1:1)
	y<-yLabels

	par(mar=c(1,1,1,3))
	image(x,y, legend.mx, col=colors, zlim=c(0, N) , yaxt="n", xaxt="n", xlab="", ylab="")
	box()
	axis(side=4, las=2)


	for(i.y in y) {	
		incr<-yBreakSize/2
		lines(x=c(0, 1.5), y=c((i.y-incr), (i.y-incr)), lwd=.5)
		lines(x=c(0, 1.5), y=c((i.y+incr), (i.y+incr)), lwd=.5)
	}
}


#----------------------------------------------------------------------------------------------
# Function: Import Data - Loads only columns we are interested in into a data frame
#str(experiment.df)
#str(expSummary.df)
#startIteration<--1;endIteration<--1;fileNumber=1
#colnames(experiment.df)
importExperiment1aSummary <- function(baseFilename, experimentNumber, trialId, startIteration=-1, endIteration=-1, fileNumber=1) {
	cat("baseFilename: ", baseFilename, ", experimentNumber: ", experimentNumber, ", trialId: ", trialId, ", startIteration: ", startIteration, ", endIteration: ", endIteration, ", fileNumber: ", fileNumber, "\n")
	
	
	dataframes <- createSummaryDataFrame(baseFilename, experimentNumber, trialId, startIteration, endIteration, fileNumber=fileNumber)
	
	experiment.df <- dataframes[[1]]
	expSummary.df <- dataframes[[2]]

	cn <- colnames(experiment.df)
	
	expSummary.df$iteration <- factor(experiment.df$iteration)
	expSummary.df$replicant <- experiment.df$replicant
	expSummary.df$timesteps <- experiment.df$executedTimesteps
	expSummary.df$butterflyCount<-experiment.df$simulation.numberOfButterflies
	expSummary.df$butterfliesDead<-experiment.df$stats.butterfliesDead
	if (contains(cn, "environment.cabbageLayout.edgeEffectLayout.cabbageRadius")) {
		expSummary.df$R <- factor(experiment.df$environment.cabbageLayout.edgeEffectLayout.cabbageRadius)
		expSummary.df$S <- factor(experiment.df$environment.cabbageLayout.edgeEffectLayout.interEdgeSeparation)
		expSummary.df$P <- factor(experiment.df$environment.cabbageLayout.edgeEffectLayout.TYPE)
	}
	
	if (contains(cn, "butterflyAgent.movementBehaviour.randomWalk.moveLength")) {
		expSummary.df$L <- factor(experiment.df$butterflyAgent.movementBehaviour.randomWalk.moveLength)
		expSummary.df$A <- factor(experiment.df$butterflyAgent.movementBehaviour.randomWalk.angleOfTurn)
		expSummary.df$RFML_ALPHA<-factor(experiment.df$butterflyAgent.movementBehaviour.randomWalk.rfmlAlpha)
		expSummary.df$RFML_BETA<-factor(experiment.df$butterflyAgent.movementBehaviour.randomWalk.rfmlBeta)		
	} else 	if (contains(cn, "butterflyAgent.movementBehaviour.informationRandomWalk")) {
		expSummary.df$L <- factor(experiment.df$butterflyAgent.movementBehaviour.informationRandomWalk.moveLength)
		expSummary.df$A <- factor(experiment.df$butterflyAgent.movementBehaviour.informationRandomWalk.angleOfTurn)
		expSummary.df$RFML_ALPHA<-factor(experiment.df$butterflyAgent.movementBehaviour.informationRandomWalk.rfmlAlpha)
		expSummary.df$RFML_BETA<-factor(experiment.df$butterflyAgent.movementBehaviour.informationRandomWalk.rfmlBeta)		
	}
	
	if (contains(cn, "butterflyAgent.lifecycle.birth.randomAroundBorder.borderDefinition.distanceBorder.distance")) {
		expSummary.df$B <- factor(experiment.df$butterflyAgent.lifecycle.birth.randomAroundBorder.borderDefinition.distanceBorder.distance)
	} else if (contains(cn, "butterflyAgent.lifecycle.release.randomAroundBoundary.boundaryDefinition.distancedBoundary.distance")) {
		expSummary.df$B <- factor(experiment.df$butterflyAgent.lifecycle.release.randomAroundBoundary.boundaryDefinition.distancedBoundary.distance)
	} else if (contains(cn, "butterflyAgent.lifecycle.release.randomInZone.boundaryDefinition.distancedBoundary.distance")) {
		expSummary.df$B <- factor(experiment.df$butterflyAgent.lifecycle.release.randomInZone.boundaryDefinition.distancedBoundary.distance)		
	} else {
		expSummary.df$B <- factor(experiment.df$butterflyAgent.lifecycle.birth.predefined.borderDefinition.distanceBorder.distance)
	}


	expSummary.df$CENTRE_RATIO <- experiment.df$stats.Centre.ratioEggsPerPlant
	expSummary.df$EDGE_RATIO <- experiment.df$stats.Edge.ratioEggsPerPlant
	expSummary.df$CORNER_RATIO <- experiment.df$stats.Corner.ratioEggsPerPlant
	expSummary.df$TOTAL_RATIO <- experiment.df$stats.total.eggsPerPlantRatio
	expSummary.df$CENTRE_EGGS <- experiment.df$stats.Centre.eggs
	expSummary.df$CENTRE_PLANTS <- experiment.df$stats.Centre.plants
	expSummary.df$EDGE_EGGS <- experiment.df$stats.Edge.eggs
	expSummary.df$EDGE_PLANTS <- experiment.df$stats.Edge.plants
	expSummary.df$CORNER_EGGS <- experiment.df$stats.Corner.eggs
	expSummary.df$CORNER_PLANTS <- experiment.df$stats.Corner.plants
	expSummary.df$TOTAL_EGGS <- experiment.df$stats.total.eggs
	expSummary.df$ESCAPED_BUTTERFLIES <- experiment.df$stats.butterfliesEscaped
	

	return (expSummary.df)
}

#given a factor and a data frame simply counts how many rows there are for each factor
#returns a matrix of the factor id and count
#input.df<-fieldResults.df
#groupingFactor<-fieldResults.df$Field
count<-function(input.df, groupingFactor) {
	nLevels<-length(levels(groupingFactor))
	counts<-matrix(nrow=nLevels, ncol=2)
	i.level<-1
	while (i.level<=nLevels) {
		i.group<-levels(groupingFactor)[[i.level]]
		group.df<-subset(input.df, groupingFactor==i.group)
		counts[[i.level, 1]]<-i.group
		counts[[i.level, 2]]<-length(group.df[[1]])
		i.level<-i.level+1
	}
	
	return (counts)	
}

#this is a very inefficient function so dont use it for hard processing!
contains<-function(in.list, search.string) {
	contains<-FALSE
	for (in.item in in.list) {
		if (in.item == search.string) {
			contains<-TRUE
		}
	}
	return (contains)
}

#----------------------------------------------------------------------------------------------
#Prints a graph of any 2 factors
plot2Factors <- function(src.df, xaxisFactorName, yaxisName, xaxisFactor, yaxisFactor) {

		pointType <- 19
		lineColor <- "blue"
		title <- sprintf("%s vs %s", xaxisFactorName, yaxisName)
		plot(yaxisFactor, type="l", lty=2, col=lineColor, ann=FALSE, xaxt="n")
		title(main=title, ylab=yaxisName, xlab=xaxisFactorName)
		points(yaxisFactor, pch=pointType, col=lineColor)
		#lines(yaxisFactor, type="l", lty=2, col=lineColor)
		factorToPlot <- levels(xaxisFactor)
		axis(1, at=1:length(factorToPlot), labels=factorToPlot)
	
}

#----------------------------------------------------------------------------------------------
#Outputs loads of cool graphs to a pdf file!! or to the screen
#Cant work out how to get the factors by name from the src.df!! damn annoying.

outputGraphs <- function(src.df, xaxisFactorName , factor1Name, factor2Name, 
							xaxisFactor, factor1, factor2, 
							makePDF=FALSE, pdfFilename="none") {
	if (makePDF==TRUE && pdfFilename != "none") {
		pdf(pdfFilename, width=11.75, height=8.25)
	} else {
		quartz(width=11.75, height=8.25)
	}

	ALL.df <- data.frame(src.df[xaxisFactorName], src.df[factor1Name], src.df[factor2Name],
	src.df["CENTRE_RATIO"], src.df["EDGE_RATIO"], src.df["CORNER_RATIO"])

	#Can make a subset of ALL here if nescessary.....

	PROCESS.df <- ALL.df
	par(mfrow=c(3, length(levels(factor2))))

	plotCount <- 0
	for (iLevel.F1 in levels(factor1)) {		
		F1.subset.df <- subset(PROCESS.df, PROCESS.df[factor1Name]==iLevel.F1)
		
		for (iLevel.F2 in levels(factor2)) {	
			title <- sprintf("%s=%s, %s=%s", factor1Name, iLevel.F1, factor2Name, iLevel.F2)
			print(title)
			plotFrame.df <- subset(F1.subset.df, F1.subset.df[factor2Name]==iLevel.F2)
			
			plotEdgeEffectRatios(plotFrame.df, levels(xaxisFactor), xaxisFactorName, title)
		}	
		plotCount <- plotCount + 1
		if (makePDF==FALSE && plotCount==3) {
			quartz(width=11.75, height=8.25)
			par(mfrow=c(3, length(levels(factor2))))
			plotCount <- 0
		}		
	}
	if (makePDF==TRUE) {
		dev.off()
	}
}

#e.g.
#outputGraphs(expSummary.df, "S", "L", "A", expSummary.df$S, expSummary.df$L, expSummary.df$A)


#Plot 2 factors against each other with standard error
# The control factor is the thing which you want to average over - in most of our cases it is "iteration" because we want the mean for each iteration
# at the moment, this must be a number between 1 and number of factors.
# e.g.
# plot2FactorsWithStdErr(expSummary.df, expSummary.df$iteration, "B", "Release Distance", "CENTRE_RATIO")
#
plot2FactorsWithStdErr <- function(input.df, startIteration, endIteration, controlFactor, xFactor, xFactorName, xLabel, yfactorName) {	
	itrCount <- (endIteration - startIteration) + 1

	toPlot.df <- as.data.frame(c(1:itrCount))
	toPlot.df$X <- 0

	#toPlot.df$X <- 0
	toPlot.df$MINUS_ERR <-0
	toPlot.df$MEAN <- 0
	toPlot.df$PLUS_ERR <-0

	for (itr in startIteration:endIteration) {
		
		i <- (itr-startIteration) + 1
		print(sprintf("Iteration %d, i=%d", itr, i))
		stats.df <- subset(input.df, controlFactor==itr)

	#Much easier way above (toPlot.df$X <- Factor(...)) ...
	#	toPlot.df$X[itr] <- as.numeric(levels(stats.df$B)[stats.df$B[1]])
	
		n <- length(stats.df$iteration) #should be number of replicants
		obs.mean <-mean(stats.df[yfactorName])
		obs.sd <- sd(stats.df[yfactorName])
		obs.stdErr <- obs.sd / sqrt(n)
		toPlot.df$X[i]<-as.numeric(levels(xFactor)[i])
		toPlot.df$MINUS_ERR[i] <- (obs.mean - obs.stdErr)
		toPlot.df$MEAN[i] <- obs.mean
		toPlot.df$PLUS_ERR[i] <- (obs.mean + obs.stdErr)
	
	}	
	
	yLabel <- sprintf("Mean %s for 100 eggs", yfactorName)
	plot2FactorsWithStdError(toPlot.df, xLabel, yLabel, toPlot.df$X, toPlot.df$MINUS_ERR, toPlot.df$MEAN, toPlot.df$PLUS_ERR)

}


#----------------------------------------------------------------------------------------------
#Prints a graph of any 2 factors with plus and minus std error
plot2FactorsWithStdError <- function(src.df, xaxisFactorName, yaxisName, xaxisFactor, minusStdError, yaxisFactor, plusStdError) {

		pointType <- 19
		lineColor <- "blue"
		errColor <- "grey"

		title <- sprintf("%s vs %s", xaxisFactorName, yaxisName)
		plot(xaxisFactor, yaxisFactor, type="l", lty=2, col=lineColor, ann=FALSE) #xaxt="n"
		title(main=title, ylab=yaxisName, xlab=xaxisFactorName)
		points(xaxisFactor, yaxisFactor, pch=pointType, col=lineColor)
		
		lines(xaxisFactor, minusStdError, type="l", lty=2, col=errColor)
		lines(xaxisFactor, plusStdError, type="l", lty=2, col=errColor)
}

#----------------------------------------------------------------------------------------------
#Prints a graph of any 2 factors with plus and minus std error
plot2Results1A <- function(srcA.df, srcB.df, xaxisFactorName, yaxisName, legendA, legendB) {
	plot2StdErrors(xaxisFactorName, yaxisName, srcA.df$X, srcA.df$MINUS_ERR, srcA.df$MEAN, srcA.df$PLUS_ERR, srcB.df$MINUS_ERR, srcB.df$MEAN, srcB.df$PLUS_ERR, legendA, legendB)
}
plot2StdErrors <- function(xaxisFactorName, yaxisName, xaxisFactor, minusStdErrorA, yaxisFactorA, 
plusStdErrorA, minusStdErrorB, yaxisFactorB, plusStdErrorB, legendA, legendB) {

		pointType <- 19
		lineColorA <- "blue"
		errColorA <- "grey"
		
		lineColorB <- "green"
		errColorB <- "pink"
	
		maxY <- max(plusStdErrorB, plusStdErrorA)
		minY <- min(minusStdErrorA,minusStdErrorB);

		title <- sprintf("%s vs %s", xaxisFactorName, yaxisName)
		plot(xaxisFactor, yaxisFactorA, type="l", lty=2, col=lineColorA, ann=FALSE, ylim=c(minY-minY*.1,maxY*1.1)) #xaxt="n" yaxt="n"
		
		legend("topleft",legend=(c(legendA, legendB)), fill=c(lineColorA, lineColorB), inset=0.05)
		
		points(xaxisFactor, yaxisFactorA, pch=pointType, col=lineColorA)
		lines(xaxisFactor, minusStdErrorA, type="l", lty=2, col=errColorA)
		lines(xaxisFactor, plusStdErrorA, type="l", lty=2, col=errColorA)
		
		lines(xaxisFactor, yaxisFactorB, type="l", lty=2, col=lineColorB)
		points(xaxisFactor, yaxisFactorB, pch=pointType, col=lineColorB)
		lines(xaxisFactor, minusStdErrorB, type="l", lty=2, col=errColorB)
		lines(xaxisFactor, plusStdErrorB, type="l", lty=2, col=errColorB)
		
		title(main=title, ylab=yaxisName, xlab=xaxisFactorName)
}


plot2StdErrors <- function(xaxisFactorName, yaxisName, xaxisFactor, minusStdErrorA, yaxisFactorA, 
plusStdErrorA, minusStdErrorB, yaxisFactorB, plusStdErrorB, legendA, legendB) {

		pointType <- 19
		lineColorA <- "blue"
		errColorA <- "grey"
		
		lineColorB <- "green"
		errColorB <- "pink"
	
		maxY <- max(plusStdErrorB, plusStdErrorA)
		minY <- min(minusStdErrorA,minusStdErrorB);

		title <- sprintf("%s vs %s", xaxisFactorName, yaxisName)
		plot(xaxisFactor, yaxisFactorA, type="l", lty=2, col=lineColorA, ann=FALSE, ylim=c(minY-minY*.1,maxY*1.1)) #xaxt="n" yaxt="n"
		
		legend("topleft",legend=(c(legendA, legendB)), fill=c(lineColorA, lineColorB), inset=0.05)
		
		points(xaxisFactor, yaxisFactorA, pch=pointType, col=lineColorA)
		lines(xaxisFactor, minusStdErrorA, type="l", lty=2, col=errColorA)
		lines(xaxisFactor, plusStdErrorA, type="l", lty=2, col=errColorA)
		
		lines(xaxisFactor, yaxisFactorB, type="l", lty=2, col=lineColorB)
		points(xaxisFactor, yaxisFactorB, pch=pointType, col=lineColorB)
		lines(xaxisFactor, minusStdErrorB, type="l", lty=2, col=errColorB)
		lines(xaxisFactor, plusStdErrorB, type="l", lty=2, col=errColorB)
		
		title(main=title, ylab=yaxisName, xlab=xaxisFactorName)
}






plotResults1B <- function(input.df, groupingFactorName, titleString, filename="NONE") {
	quartz(height=8.25, width=8.25)
	par(mfrow=c(2, 2))
	
	plotResults1BGrouped(input.df, groupingFactorName, titleString);
	
	if (filename != "NONE") {
		print(sprintf("Writing Experiment 1B results to %s...", filename))
		pdf(height=8.25, width=8.25, file=filename)
		par(mfrow=c(2, 2))
		
		plotResults1BGrouped(input.df, groupingFactorName, titleString);
		
		dev.off()
	}
}

plotResults1BGrouped<-function(input.df, groupingFactorName, titleString) {
	groupingFactor<-input.df[[groupingFactorName]]
	
	for (groupLevel in levels(groupingFactor)) {
		
		stats.df<-subset(input.df, groupingFactor==groupLevel)
		
		plotLogFactorsWithRegression(stats.df, "log(Moves)", "log(MSD)", stats.df$X, stats.df$MINUS_ERR, stats.df$MEAN, stats.df$PLUS_ERR, titleString)
	}
	
}

#Its going to draw 2 regression lines - 1 for the timesteps < 7 and one for timesteps > 400 as per Johnson paper(Ecology 73(6) 1992 pp 1968-1983
#not sure how they picked these values though! i think its because it happened to fit their data nicely!
#c1Upper is the index of the row which contains the last value for the first correlation (i.e. <7)
#c2Lower is the start of those steplengths > 400
#src.df<-inp.df

#xaxisFactorName<-"Max Age"
#yaxisName<-"MSD"
#xaxisFactor<-src.df$X
#minusStdError<-stats.df$MINUS_ERR
#yaxisFactor<-src.df$MEAN
#plusStdError<-stats.df$PLUS_ERR
#titleString<-"Test Graph"
plotLogFactorsWithRegression <- function(src.df, xaxisFactorName, yaxisName, xaxisFactor, minusStdError, yaxisFactor, plusStdError,  titleString) {
		pointType <- 19
		lineColor <- "blue"
		errColor <- "grey"

		title <- sprintf("%s vs %s (%s)", xaxisFactorName, yaxisName, titleString)

		xValues<-as.numeric(levels(xaxisFactor))
		plot(xValues, yaxisFactor, type="l", lty=2, col=lineColor, log="xy", xlim=c(1,1000), ann=FALSE) #xaxt="n"
		title(main=title, ylab=yaxisName, xlab=xaxisFactorName)
		points(xValues, yaxisFactor, pch=pointType, col=lineColor)
				
		src.df$XVAL<-as.numeric(levels(src.df$X))		
				
		lower.df<-subset(src.df, src.df$XVAL<=7)
		lower.lm <- lm(log(lower.df$MEAN)~log(lower.df$XVAL), data=lower.df)
		
		upper.df<-subset(src.df, src.df$XVAL >=400)
		upper.lm <- lm(log(upper.df$MEAN)~log(upper.df$XVAL), data=upper.df)
		
		results <- c(lower.lm, upper.lm)
		
		slope1<-lower.lm$coef[[2]]
		intercept1<-lower.lm$coef[[1]]

		#Manually plot the regression line as it doesnt appear in the right place otherwise
		i1<-length(lower.df$XVAL);
		i2<-length(upper.df$XVAL);
		xMax<-upper.df$XVAL[i2]*1.2 #use the upper X value so we get a line that covers the graph
		yMax<- exp(log(xMax)*slope1+intercept1)	
		lines(c(1, xMax), c(exp(intercept1),  yMax), col="grey")

		
		slope2<-upper.lm$coef[[2]]
		intercept<-upper.lm$coef[[1]]

		xMax<-upper.df$XVAL[i2]*1.2
		yMax<- exp(log(xMax)*slope2+intercept)	
		lines(c(1, xMax), c(exp(intercept),  yMax), col="grey")
		
		text(4,max(src.df$MEAN), sprintf("r1=%01.3f, r2=%01.3f", slope1, slope2))
	
		return (results)
}

#deprecated use plotTwoFactors insteas
plotLvsB<-function(yFactorName, yFactorTitle, inp.df, baseFilename, experimentNumber, trialId, numReplicants, newPlot=TRUE) {
	plotTwoFactors("L", "B", yFactorName, yFactorTitle, inp.df, baseFilename, experimentNumber, trialId, numReplicants, newPlot) 
}
#yFactorName<-"CENTRE_RATIO"
#yFactorTitle<-"Centre Ratio"
#newPlot<-FALSE
#"CENTRE_RATIO", "Centre Ratio", inp.df, baseFilename, experimentNumber, trialId, numReplicants, newPlot=FALSE
plotTwoFactors<-function(xFactor1Name, xFactor2Name, yFactorName, yFactorTitle, inp.df, baseFilename, experimentNumber, trialId, numReplicants, newPlot=TRUE, plotReverse=TRUE, legendPosition="topleft") {
	print(sprintf("newPlot==%s", newPlot))
	
	xFactor1<-inp.df[[xFactor1Name]]
	xFactor2<-inp.df[[xFactor2Name]]
	lenL<-length(levels(xFactor1))
	lenB<-length(levels(xFactor2))
	allLineColors<-rainbow(lenL + lenB)
	
	lineColsL <- allLineColors[1:lenL]
	lineColsB <- allLineColors[(lenL+1):(lenL+lenB)]

	all.stats <- createSummaryStatistics(inp.df, xFactor1Name, xFactor2Name, yFactorName)
	directory <- createDirectoryName(baseFilename, experimentNumber, trialId)
	title<-sprintf("%s for %s vs %s (A=20, S=10, REPS=%d)", yFactorTitle, xFactor1Name, xFactor2Name, numReplicants)
	filename<-sprintf("%s/TRIAL %s - %s.pdf", directory, trialId, title) 
	

	plotSummaryStatistics(all.stats, title, xFactor2Name, yFactorTitle, sprintf("%s=%s", xFactor1Name, "%s"), xFactor1, lineColors=lineColsL,	filename=filename, newPlot=newPlot, legendPosition=legendPosition) 

	writeStats(all.stats, baseFilename, experimentNumber, trialId, title)

	if(plotReverse==TRUE) {
		all.stats <- createSummaryStatistics(inp.df, "B", "L", yFactorName)
		directory <- createDirectoryName(baseFilename, experimentNumber, trialId)
		title<-sprintf("%s for B vs L (A=20, S=10, REPS=%d)",yFactorTitle, numReplicants)
		filename<-sprintf("%s/TRIAL %s - %s.pdf", directory, trialId, title) 
		plotSummaryStatistics(all.stats, title, "L - move length", yFactorTitle, "B=%s", inp.df$B, lineColsB, 	filename=filename, newPlot=newPlot) 
	
		writeStats(all.stats, baseFilename, experimentNumber, trialId, title)
	}
	
	return (all.stats)
}

util.indexOfArrayValue<-function(array, searchValue) {
	index<- -1
	for (i in 1:length(array)) {
		if (array[i] == searchValue) {
			index<-i
		}
	}
	return (index)
}
indexOfFactorValue<-function(factor, value) {
	found<-NA
	levels<-levels(factor)
	for (i in 1:length(levels)) {
		if (levels[i] == value) {
			found<-i
		}
	}
	return (found)
}


summarise<-function(inp.df, plan.df) {
	cn<-colnames(plan.df)
	if(contains(cn, "experiment.id")) {
		experimentIdStr <- sprintf("-%03d", plan.df$experiment.id)
	} else {
		experimentIdStr<-""
	}
	cat("Experiment: ", plan.df$experiment.name, ",  ", plan.df$plan.name, "-", plan.df$plan.trial.name, experimentIdStr, "\n")
	cat("Iterations: ", levels(inp.df$iteration), "\n")
	cat("L: ", levels(inp.df$L), "\n")
	cat("B:",levels(inp.df$B), "\n")
	
	numReplicants<-length(inp.df$replicant)/length(levels(inp.df$iteration))
	cat("Replicates",numReplicants, "\n")

	summariseCabbageStats(inp.df)
	
	cat("Mean Eggs Laid: ", mean(inp.df$TOTAL_EGGS), "\n")
	cat("Mean Butterflies Dead: ", mean(inp.df$butterfliesDead), "\n")
	cat("Mean Butterflies Escaped: ", mean(inp.df$ESCAPED_BUTTERFLIES), "\n")
	
	return (numReplicants)
}

#groupingFactorName<-"B"
summariseCabbageStats<-function(inp.df, groupingFactorName="B") {

	results.list<-summariseStats(inp.df, groupingFactorName , "CENTRE_RATIO")
	results.list<-summariseStats(inp.df, groupingFactorName , "CORNER_RATIO")
	results.list<-summariseStats(inp.df, groupingFactorName , "TOTAL_EGGS")
	
}

#yFactorName<-"CENTRE_RATIO"
summariseStats<-function(inp.df, groupingFactorName="B", yFactorName) {
	stats.list<-createSummaryStatistics(inp.df, groupingFactorName , "A", yFactorName)	
	i<-1
	while(i<=length(stats.list)) {
		stats.group<-stats.list[[i]]
		lvl<-levels(inp.df[[groupingFactorName]])[[i]]
		cat(groupingFactorName, "=" , sprintf("%3s", lvl), " - ", yFactorName, "(N=" ,stats.group$N,  ") mean: ", stats.group$MEAN, "\n")
		i<-i+1
	}
	return (stats.list)
}

#break size is the size of th e"bucket" you want to acucmulate into - or the range 
#break count is the number of these buckets
createHistogram <- function(factor, startRow, numberOfRows, breakSize, breakCount) {
	subset.df <- factor[startRow:numberOfRows]	
	subset.df.hist<-hist(subset.df,freq=TRUE, 
			br=breakSize*0:breakCount, plot=FALSE)
	return (subset.df.hist)
}
#data.hist.vector<-data.hist.list
#graphTitle<- "Frequency Of Intial turn"
#xaxisLabel<- "Angle"

plotManyHistograms <- function(data.hist.vector, graphTitle, xaxisLabel) {
	#quartz(width=11, height=8)

	maxY<-0
	for (data.hist in data.hist.vector) {
		maxCurr<-max(data.hist$counts)
		if (maxCurr > maxY) {
			maxY <- maxCurr
		}
	}
	maxY <- maxY + maxY*.10
	
    minY<-maxY
	for (data.hist in data.hist.vector) {
		minCurr<-min(data.hist$counts)
		if (minCurr < minY) {
			minY <- minCurr
		}
	}
	
	minY <- minY - minY*.10
	if (minY==0) {
		minY <-0
	}
	minY<-0
			
	first.hist <- data.hist.vector[[1]]
#	barplot(first.hist$counts, names.arg=c(1:18), axis.lty=0, col=c("red"))
par(lend=2)#square ends to lines
	par(mar=c(5, 5, 2, 2))
	plot(first.hist$counts, type="h", lty=1, col="blue", xaxt="n",xlim=c(0, length(first.hist$counts)), ylim=c(minY, maxY), ann=FALSE, lwd=10, cex.axis=1.2, cex.lab=1.2)
	#the last params are lwd - line width 
#	help(plot)
	title(main=graphTitle, ylab="count", xlab=xaxisLabel, cex.lab=1.2)
	labelSeq <- seq(1, length(first.hist$breaks), 1)
	breaks.labels <- first.hist$breaks[labelSeq]
	length(breaks.labels)
	axis(1, at=labelSeq, labels=breaks.labels, cex.lab=1.2, cex=1.2,cex.axis=1.2)
	
	histCount <- length(data.hist.vector)
	colourList <- rainbow(20)
	iCol <-1
	if (histCount > 1) {
		others.list <- data.hist.vector[2:histCount]	
		for (other.hist in others.list) {
			lines(other.hist$counts, type="l", lty=2, col=colourList[iCol])
			iCol <- iCol+1
		}
	}			
}




#str(input.df)
#input.df<-group.df
#input.df<-inputCrossings.df
#groupingFactorName<-"L"
#xFactorName<-"B"
#yFactorName<-"d"
#breakSize<-5
plotFrequencyOfFactor<-function(input.df, groupingFactorName, xFactorName, yFactorName, breakSize, filename="NONE", newPlot=TRUE) {
	
	groupingFactor<-input.df[[groupingFactorName]]
	xFactor<-input.df[[xFactorName]]
	yFactor<-input.df[[yFactorName]]
	
	lenG<-length(levels(groupingFactor))
	lenX<-length(levels(xFactor))
	
	w<-4*lenX
	h<-4*lenG

	if (newPlot == TRUE) {	
		quartz(width=w, height=h)
		par(mfrow=c(lenG, lenX))
	}
	
	results.list<-plotGroupedHistograms(input.df, groupingFactor, groupingFactorName, xFactorName, yFactorName, breakSize)
	
	if (filename!="NONE" && newPlot==TRUE) {
		print(sprintf("Writing Graphs to '%s'", filename))
		pdf(filename, width=w, height=h)
		par(mfrow=c(lenG, lenX))
		plotGroupedHistograms(input.df, groupingFactor, groupingFactorName, xFactorName, yFactorName, breakSize)
		dev.off()
		print("Complete.")
			
	}
	return (results.list)
	
}

plotGroupedHistograms<-function(input.df, groupingFactor, groupingFactorName,  xFactorName, yFactorName, breakSize) {
	results.list<-list()

	
	for (groupLevel in levels(groupingFactor)) {
		group.subset.df<-subset(input.df, groupingFactor==groupLevel)		
		xFactor<-group.subset.df[[xFactorName]]
		
		for (xFactorLevel in levels(xFactor)) {					
			xLevel.subset.df<-subset(group.subset.df, xFactor==xFactorLevel)
			title<-sprintf("%s=%s, %s=%s", groupingFactorName, groupLevel, xFactorName, xFactorLevel)
			
			result.hist<-plotHistogram(xLevel.subset.df, yFactorName, breakSize, title)
			results.list<-appendToList(results.list, result.hist)						
			
		}
	}
	return (results.list)
}



#xLevel.subset.df$distance
#xFactor
#xFactorLevel

#titleString<-"Test Graph"
plotHistogram<-function(input.df, yFactorName, breakSize, titleString) {
	yFactor<-input.df[[yFactorName]]
	maxD<-max(yFactor)
	minD<-min(yFactor)
#	breakCount<-16
#	breakSize<-maxD/breakCount
	breakCount<-(maxD/breakSize) + 1

	result.hist <- createHistogram(yFactor, 1, length(yFactor), breakSize, breakCount)
	title<-sprintf("%s - n=%d", titleString, length(yFactor), maxD)
	
	plotManyHistograms(list(result.hist), title, yFactorName)
	return (result.hist)
}
