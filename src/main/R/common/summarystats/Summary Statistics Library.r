cat("Summary Statistics Library Version 1.0\n")

SummaryStatistics.asNumeric<-function(x.factor) {
	as.numeric(levels(x.factor)[as.numeric(x.factor)])
}

#Creates an empty dataframe with all the right columns and the right number of rows for the X factor
#stats.df$X
#help(factor)
#str(stats.df$X)
createStatsDataFrame <- function(xFactor) {
	
	if (!is.factor(xFactor)) {
		stop("You must pass a factor to createStatsDataFrame!")
	}
	xLen<-length(levels(xFactor))
	xLevels<-c(1:xLen)
	xLabels<-c(1:xLen)
	for (i in xLevels) {
		xLabels[i]<-levels(xFactor)[i]
	}	
	xFactorSorted<-factor(c(1:xLen),levels=xLevels, labels=xLabels)
	stats.df <- data.frame("X"=xFactorSorted)
	stats.df$N <-0
	stats.df$TOTAL <-0
	stats.df$MEAN <- 0
	stats.df$MINUS_ERR <- 0
	stats.df$PLUS_ERR<-0
	stats.df$SD<-0
	stats.df$SE<-0
	stats.df$VARIANCE <-0
	stats.df$SD<-0
	stats.df$SE<-0
	stats.df$MINUS_SD <- 0
	stats.df$PLUS_SD<-0
	return (stats.df)
}




#eg:
#plotEdgeEffectRatios(plotFrame.df, S.levels, "X", "someTitle")
createDirectoryName <- function(basefilename, experimentNumber, trialId) {
	directory<-sprintf("%s-%s-%03d", baseFilename, trialId, experimentNumber)
	return (directory)
}

#deprecated - use writeDataFrame instead.
writeStats<-function(df.list,  basefilename, experimentNumber, trialId, title,  names=vector()) {
	writeDataFrame(df.list,  basefilename, experimentNumber, trialId, title,  names=vector())
}

#Just writes out a list of stats results so you dont have to 
writeDataFrame<-function(df.list,  basefilename, experimentNumber, trialId, title,  names=vector()) {
	
	directory<-createDirectoryName(basefilename, experimentNumber, trialId)
	writeDataFrameToDir(df.list, directory, title, names)
}

writeDataFrameToDir<-function(df.list,  directoryName, title,  names=vector()) {
	for (i in 1:length(df.list)) {
		stats.df<-df.list[[i]]
		if (length(names)==0) {
			name<-sprintf("%03d", i)
		} else {
			name<-names[i]
		}
		filename<-sprintf("%s/r-dataframe-%s-%s.csv", directoryName, title, name)
		print(sprintf("Writing dataframe file '%s'...", filename))
		write.csv(stats.df, filename)
	}
}
#yLim<-c(min(bc), max(bc))
SummaryStatistics.simpleYat<-function(yLim) {
	return (c(yLim[1], yLim[1]+((yLim[2]-yLim[1])/2), yLim[2]))
}

#log<-"y"
SummaryStatistics.plotLogStatsWithRegression<-function(stats.df,  xlab, ylab, titleString, log="xy", regCol="grey") {
		pointType <- 19
		lineColor <- "blue"
		errColor <- "grey"

		title <- sprintf("%s vs %s (%s)", xlab, ylab, titleString)

		xValues<-as.numeric(levels(stats.df$X))
		
		plot(xValues, stats.df$MEAN, type="l", lty=2, col=lineColor, log=log,xlim=c(1,max(xValues)), ann=FALSE) #xaxt="n"
		title(main=title, ylab=ylab, xlab=xlab)
		points(xValues, stats.df$MEAN, pch=pointType, col=lineColor)
				
		stats.df$XVAL<-as.numeric(levels(stats.df$X))		
				
		if (log=="xy") {
			stats.lm <- lm(log(stats.df$MEAN)~log(stats.df$XVAL), data=stats.df)
		} 	else if (log=="x") {
			stats.lm <- lm(log(stats.df$MEAN)~stats.df$XVAL, data=stats.df)
		} else if (log=="y") {
			stats.lm <- lm(log(stats.df$MEAN)~stats.df$XVAL, data=stats.df)
		} else {
			stats.lm <- lm(stats.df$MEAN~stats.df$XVAL, data=stats.df)
		}
						
		slope1<-stats.lm$coef[[2]]
		intercept1<-stats.lm$coef[[1]]

		#Manually plot the regression line as it doesnt appear in the right place otherwise
		i1<-length(stats.df$XVAL);
		i2<-length(stats.df$XVAL);
		xMax<-stats.df$XVAL[i2]*1.2 #use the upper X value so we get a line that covers the graph

		if (log=="x" || log=="xy") {
			xMaxVal<-log(xMax);	
		} else {
			xMaxVal<-xMax;	
		}
		yValue<-xMaxVal*slope1+intercept1
		
		if (log=="y" || log=="xy") {
			yMax<- exp(yValue)	
			interceptVal<-exp(intercept1)
		} else {
			yMax<-yValue
			interceptVal<-interecept1
		}
		
		lines(c(1, xMax), c(interceptVal,  yMax), col=regCol)

				
		text(4,max(stats.df$MEAN), sprintf("r1=%01.3f", slope1))
	
		return (stats.lm)
}

#Creates a t-test statistic comparing two linear models.
#Null hypothesis is that the SLOPES are the same, therefore p.value of 1 supports the null hypothesis.
#low p-value significantly REJECT the null hypothesis
#Caluclation from "Linear Regression in R.pdf" not sure where this comes from
t.test.lm<-function(lm.expected, lm.observed, showDetails=TRUE) {
	slope.expected<-lm.expected$coef[[1]]

	summary.observed<-summary(lm.observed)
	slope.observed<-lm.observed$coef[[1]]
	stderr.slope.observed<-summary.observed$coefficients[[2,1]]
	df.observed<-lm.observed$df.residual

	#This is the key bit - take the difference in the slopes between the samples and divide by the sdandard error of the observed
	abs.t<-abs((slope.expected - slope.observed)/stderr.slope.observed)

	p.1.tail.t<-pt(abs.t, df.observed, lower.tail=FALSE) # get the t value from the t distribution for 1 
	p.2.tail.t<-p.1.tail.t*2 #calculate for two tail t distribution

	if (showDetails==TRUE) {
		cat("H0 - Slopes of regression line are the same\n")
		cat("Ha - Slopes of regression line are different\n")
		cat("p-value for observed vs expected lm : ", p.2.tail.t)
		if (p.2.tail.t<0.05) { 
			cat("*")
		} else if (p.2.tail.t<0.01) {
			cat("**")
		} else if (p.2.tail.t<0.001) {
			cat("***")
		}
		cat("\n")
		cat("Significance : p-value <0.05 (95%) *, <0.01 (99%) **, <0.001 (99.9%)\n")
		cat(" (1 means accept H0, low means significantly different slopes)\n")
	}
	return(p.2.tail.t)
}


#Draws a regression line for you given a vector of x values and a linear model
#lm<-lm.sim.1m
#x<-x.1m
#expects the linear model to be a model based on log(values) 
SummaryStatistics.plotLogRegressionLine<-function(x, lm, col="grey", ...) {
	
	intercept<-lm$coef[[1]]
	slope<-lm$coef[[2]]
	
	xCount<-length(x)
	
	x1<-x[[1]]*.2
	x2<-x[[xCount]]*1.2
	
	y1<-exp((log(x1)*slope)+intercept)
	y2<-exp((log(x2)*slope)+intercept)
	
	lines(x=c(x1, x2), y=c(y1, y2), col=col, ...)

}


#deprecated - use readDataFrame instead
SummaryStatistics.readStats<-function(basefilename, experimentNumber, trialId, title, names=vector()) {
	readDataFrame(basefilename, experimentNumber, trialId, title, names=vector())
}

readDataFrame<-function(basefilename, experimentNumber, trialId, title, names=vector()) {
	results<-list()
	directory<-createDirectoryName(basefilename, experimentNumber, trialId)
	stop<-FALSE
	i<-1
	while (stop==FALSE) {
		if (length(names)==0) {
			name<-sprintf("%03d", i)
		} else {
			name<-names[i]
		}
		filename<-sprintf("%s/r-dataframe-%s-%s.csv", directory, title,  name)
		
		if (file.exists(filename)) {
			print(sprintf("Reading dataframe file '%s'...", filename))
			stats.df<-read.csv(filename)
			results<-appendToList(results, stats.df)
		} else {
			stop<-TRUE
		}
		i<-i+1
	}
	return (results)
}


#all.stats.df <- createSummaryStatistics(inp.df$L, inp.df$CENTRE_RATIO, inp.df$B)
#xFactorName<-"A"
#yFactorName<-"CENTRE_RATIO"
#groupingFactorName<-"B"
#input.df<-inp.df
#group<-0
#levels(xFactor)
#x<-10
# In theory grouping factor could be a vector of factors creating a hierarchy of groups
SummaryStatistics.createSummaryStatistics<-function(input.df, groupingFactorName , xFactorName, yFactorName) {
	xFactor<-input.df[[xFactorName]]
	yFactor<-input.df[[yFactorName]]
	if (!is.null(yFactor) && !is.nan(yFactor)) {
		groupingFactor<-input.df[[groupingFactorName]]
		groups<-levels(groupingFactor)
	
		results.list<-list()
		for (group.level in groups) { 
			group.df<-subset(input.df, groupingFactor==group.level)
		#	print(sprintf("Creating stats for (%s) Group=%s", groupingFactorName ,group.level))
			stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(group.df, xFactorName, yFactorName)	
			results.list<-collection.appendToList(results.list, stats.df)
		}
	
		return (results.list)
	} else {
		return (NA)
	}
}
#input.df<-experiment.df
#xFactorName<-"X1"
#yFactorName<-"Teggs_240106"
#xR<-c("1")
#str(input.df)
#ss<-subset(input.df, input.df$X1==xR)
#length(ss$id)
#str(input.df)
#colnames(input.df)
#input.df<-layout.df
#input.df<-field.df;xFactorName<-"X1m_dens"; yFactorName<-"eggs_24.02.2006"
SummaryStatistics.createUngroupedSummaryStatistics<-function(input.df, xFactorName, yFactorName) {
		if (length(input.df[,1])==0) {
			stop("Passed no data in input.df to 'SummaryStatistics.createUngroupedSummaryStatistics'")
		}
		xFactor<-input.df[[xFactorName]]
		yFactor<-input.df[[yFactorName]]
		
		if (is.null(xFactor) || is.null(yFactor)) {
			stop(sprintf("xFactor or yFactor could not be found: xFactorName=%s, yFactorName=%s", xFactorName, yFactorName))
		}
		
		stats.df<-createStatsDataFrame(xFactor)	

		for (xFactor.level in levels(xFactor)) {
			#You have to be careful that there is no column called xFactor.level or it will  try to compare the 2 column values
			x.df<-subset(input.df, input.df[[xFactorName]]==xFactor.level)
		#	print(sprintf("Calculating for xFactor (%s) x=%3s yFactor (%s) N=%d", xFactorName, xFactor.level, yFactorName, length(x.df[[yFactorName]])))
			stats.df<-populateStatsEntry(stats.df, x.df[[yFactorName]], xFactor.level)
		}	
		return (stats.df)
}

SummaryStatistics.createSimpleSummaryStatistics<-function(observation.list, xFactor) {
	stats.df<-createStatsDataFrame(xFactor)	

	for (xFactor.level in levels(xFactor)) {
		yObservations<-observation.list[[xFactor.level]]		
		stats.df<-populateStatsEntry(stats.df, yObservations, xFactor.level)
	}	
	return (stats.df)
}

#currentX<-"100"
#str(yFactor)
#length(yFactor)
populateStatsEntry<-function(stats.df, yObservations, currentX) {
		obs.N <- length(yObservations)
		obs.mean <-mean(yObservations)
		obs.sd <- sd(yObservations)
		obs.stdErr <- obs.sd / sqrt(obs.N)
		obs.variance <- var(yObservations)
		

		stats.df$MINUS_SD[stats.df$X==currentX] <- (obs.mean - obs.sd)			
		stats.df$MINUS_ERR[stats.df$X==currentX] <- (obs.mean - obs.stdErr)
		
		stats.df$MEAN[stats.df$X==currentX] <- obs.mean
		
		stats.df$PLUS_ERR[stats.df$X==currentX] <- (obs.mean + obs.stdErr)
		stats.df$PLUS_SD[stats.df$X==currentX] <- (obs.mean + obs.sd)			
		
		stats.df$N[stats.df$X==currentX] <- obs.N
		stats.df$TOTAL[stats.df$X==currentX] <- sum(yObservations)
		

		stats.df$SD[stats.df$X==currentX]<-obs.sd
		
		stats.df$SE[stats.df$X==currentX]<-obs.stdErr
		stats.df$VARIANCE[stats.df$X==currentX] <- obs.variance
		return (stats.df)

}

se<-function(x) {
	return (sd(x) / sqrt(length(x)))
}


#This actually plots the results - if you pass it a file name it will print it to a file aswell
#stats.list<-all.stats
#xLabel<-"B - release boundary"
#yLabel<-yFactorTitle
#legendFormatString<-"L=%s"
#legendFactor<-inp.df$L
#lineColors<-lineColsL
#all.stats, title, "B - release boundary", yFactorTitle, "L=%s", inp.df$L, lineColors=lineColsL,	filename=filename, newPlot
plotSummaryStatistics<-function(stats.list,  title, xLabel, yLabel, legendFormatString=FALSE,legendPosition="topleft", legendList=FALSE, lineColors=FALSE, filename=FALSE, continuousX=TRUE, plotErr=TRUE, newPlot=TRUE) {
	if (newPlot==TRUE) {
		print("Creating new Plot!!")
		quartz(width=8.25, height=8.2)
	}
	plotManyResults(stats.list,  title, xLabel, yLabel, legendFormatString, legendList, plotErr, continuousX, lineColors, legendPosition=legendPosition)
	
	if ((filename != FALSE) && newPlot==TRUE) {
		print(sprintf("Writing Graph to '%s'", filename))
		pdf(filename, width=8.75, height=8.25)
		plotManyResults(stats.list,  title, xLabel, yLabel, legendFormatString, legendList, plotErr, continuousX, lineColors)
		dev.off()
	}
}

SummaryStatistics.isNa<-function(stats.df, fieldName) {
	return (sum(is.na(stats.df[[fieldName]]))!=0)
}

#debug(SummaryStatistics.getYRange)
SummaryStatistics.getYRange<-function(stats.list, expansionFactor=0, fixedMin=NA) {
	yMaxCurr<-0
	yMinCurr<-100000000
	
	
	for (i.stats.df in stats.list) {
		
		if (!SummaryStatistics.isNa(i.stats.df, "PLUS_ERR")) {
			max<-max(i.stats.df$PLUS_ERR)
		} else {
			max<-max(i.stats.df$MEAN)
		}

		if (!SummaryStatistics.isNa(i.stats.df, "MINUS_ERR")) {
			min<-min(i.stats.df$MINUS_ERR)
		} else {
			min<-min(i.stats.df$MEAN)
		}
		
		if (max>yMaxCurr) {
			yMaxCurr<-max
		}
		
		if (min<yMinCurr) {
			yMinCurr<-min
		}		
	}
	
	if (expansionFactor>0) {
		yMaxCurr<-yMaxCurr+(yMaxCurr*expansionFactor)
	}
	if (!is.na(fixedMin)) {
		yMinCurr<-fixedMin
	}
	return (c(yMinCurr, yMaxCurr))
}

SummaryStatistics.getXRange<-function(stats.list, expansionFactor=0, fixedMin=NA) {
	xMaxCurr<-0
	xMinCurr<-100000000
	
	
	for (i.stats.df in stats.list) {
		
		x<-SummaryStatistics.asNumeric(i.stats.df$X)
		max<-max(x)
		min<-min(x)

		if (max>xMaxCurr) {
			xMaxCurr<-max
		}
		
		if (min<xMinCurr) {
			xMinCurr<-min
		}		
	}
	
	if (expansionFactor>0) {
		xMaxCurr<-xMaxCurr+(xMaxCurr*expansionFactor)
	}
	if (!is.na(fixedMin)) {
		xMinCurr<-fixedMin
	}
	return (c(xMinCurr, xMaxCurr))
}


#stat.df<-stats.A.df
#first.stat.df<-stats.A.df
#df.list<-all.stats
#continuousX<-TRUE
#i<-2
#df.list<-stats.list
#plotErr<-TRUE
#If you pass continuousX=TRUE to this function it assumes that the factor of X will be a continuous numeric, rather than discreet numeric or discreet Character (e.g. "A", "B", "C" etc)
SummaryStatistics.plotManyResults<-function(df.list, title="", xaxisLabel="", yaxisLabel="", 
	legendFormatString=FALSE, legendList=c(), plotErr=FALSE, continuousX=TRUE, 
	lineColors=c(), lineTypes=c(), legendPosition="topleft",legendHoriz=T, xLim=c(), minXZero=T, 
	yLim=c(), errCol="#333333", noDecoration=F,plotAxes=T, at.y=c(),labels.y=c(),
	plotTitle="",linesX=c(), linesY=c(),logY=F,sqrtY=F,cex.pch=2, cex.legend=1.2,legendInset=0.05, 
		cex.axis=1.5,mgp=c(3, 1, 0), ...) {
	
		
	if (is.data.frame(df.list)) {
		stop("You passed in a dataframe - must pass in a list of dataframes.")
	}
	first.stat.df<-df.list[[1]]

	if(length(yLim) ==0) {
		ylims<-calculatePlotYLim(df.list)	
	} else {
		ylims<-yLim
	}

	minY<-ylims[[1]]
	maxY<-ylims[[2]]
	

	
	if (length(xLim) == 0) {
		xlims<-calculatePlotXLim(df.list, minXZero=minXZero)	
	} else {
		xlims<-xLim
	}
	
	
	minX<-xlims[[1]]
	maxX<-xlims[[2]]
	
	
	pointType <- 19
	
	if (length(lineColors)==0) {
		lineColors <- c("blue", "red", "green", "brown", "purple", "yellow", "purple", "grey")
	}else if (length(lineColors)!= length(df.list)) {
		stop(sprintf("You must specify the same number of linecolors as you have summary stats to plot. Your LineType count=%d, your stats count=%d", length(lineColors), length(df.list)))
	}
	if (length(lineTypes)==0) {
		lineTypes <- rep(2, length(df.list))
	} else if (length(lineTypes)!= length(df.list)) {
		stop(sprintf("You must specify the same number of linetypes as you have summary stats to plot. Your LineType count=%d, your stats count=%d", length(lineTypes), length(df.list)))
	}
	
	if (length(df.list)>4) {
		lineTypes<-rep(1, length(df.list))
		cex.pch<-.9
	} 

	pointSymbols<-c(19, 15, 17, 24, 22, 23, 15, 24, 25)
	

	if (is.factor(first.stat.df$X)) {
		xValues<-as.numeric(levels(first.stat.df$X)) #This makes sure that plot treats it right - otherwise it will not plot it as a continuous variable
	} else {
			xValues<-first.stat.df$X		
	}

	if (logY) {
		logparam<-"y"
		if (minY<=0) {
			minY<-1
		}
		yValues<-first.stat.df$MEAN+1
		yPlusErr<-first.stat.df$PLUS_ERR+1
		yMinusErr<-first.stat.df$MINUS_ERR+1
		
	} else if (sqrtY){
		yValues<-sqrt(first.stat.df$MEAN)
		yPlusErr<-sqrt(first.stat.df$PLUS_ERR)
		yMinusErr<-sqrt(first.stat.df$MINUS_ERR)
		minY<-sqrt(minY)
		maxY<-sqrt(maxY)
		logparam<-""
	} else {
		logparam<-""
		yValues<-first.stat.df$MEAN
		yPlusErr<-first.stat.df$PLUS_ERR
		yMinusErr<-first.stat.df$MINUS_ERR
		
	}

	plot(xValues, yValues, type="l", lty=lineTypes[1], col=lineColors[1], 
		ann=F, xlim=c(minX, maxX), ylim=c(minY,maxY), 
		log=logparam, xaxt="n", yaxt="n",  ...) #xaxt="n" yaxt="n"	
	
	if (length(linesX)>0) {
		lines(linesX, linesY,lwd=1.5,col="black", lty=2)
	}
	
	plottedXAxis<-FALSE

	if (max(xValues)>=maxX) {
		if (plotAxes) {
			axis(side=1, at=xValues,cex.axis=cex.axis, cex.lab=1.2,mgp=mgp,...)
		}
		plottedXAxis<-TRUE
	}
	#las=2 - puts them horizontally

		if (plotAxes) {
			if (length(at.y) ==0) {
				axis(side=2,cex.axis=cex.axis, cex.lab=1.2, ...)
			} else {
				axis(side=2,at=at.y, labels=labels.y, cex.axis=cex.axis, cex.lab=1.2, ...)
			}
		}



	if (plotErr) {
		xValues<-as.numeric(levels(first.stat.df$X)) #Always do this for err bar
		SummaryStatistics.plotErrorBars(xValues,yPlusErr,yMinusErr, col=lineColors[1], lwd=1.5)
#		lines(first.stat.df$X, first.stat.df$PLUS_ERR, type="l", lty=2, col=errColor)
#		lines(first.stat.df$X, first.stat.df$MINUS_ERR, type="l", lty=2, col=errColor)
	}
	
		points(xValues, yValues, pch=pointSymbols[1], cex=cex.pch,bg=lineColors[1],col=lineColors[1])
#i<-1	
	iCol<-2
	statCount<-length(df.list)
	if (statCount >1) {
		others.list <- df.list[2:statCount]
		for (i in 1:length(others.list)) {
			#print(sprintf("id:%d, i=%d", iCol, i))		
			stat.df<-others.list[[i]]			
			if (is.factor(stat.df$X)) {
				xValues<-as.numeric(levels(stat.df$X)) #This makes sure that plot treats it right - otherwise it will not plot it as a continuous variable
			} else {
				xValues<-stat.df$X		
			}
			
			if (!plottedXAxis) {
				if (max(xValues)>=maxX) {
						if (plotAxes) {
 							axis(side=1, cex.axis=cex.axis,at=xValues,mgp=mgp, ...)
							plottedXAxis<-TRUE
						}
					
				}
			}

			if (logY) {
				yValues<-stat.df$MEAN+1
				yPlusErr<-stat.df$PLUS_ERR+1
				yMinusErr<-stat.df$MINUS_ERR+1
			} else if (sqrtY){
				yValues<-sqrt(stat.df$MEAN)
				yPlusErr<-sqrt(stat.df$PLUS_ERR)
				yMinusErr<-sqrt(stat.df$MINUS_ERR)				
			} else {
				yValues<-stat.df$MEAN
				yPlusErr<-stat.df$PLUS_ERR
				yMinusErr<-stat.df$MINUS_ERR
			}
			
			lines(xValues, yValues, pch=pointType, type="l",lty=lineTypes[iCol] ,col=lineColors[iCol])
			if (plotErr) {
				xValues<-as.numeric(levels(stat.df$X))
				SummaryStatistics.plotErrorBars(xValues,yPlusErr,yMinusErr, col=lineColors[iCol], lwd=1.5)
				#lines(stat.df$X, stat.df$PLUS_ERR, type="l", lty=2, col=errColor)
				#lines(stat.df$X, stat.df$MINUS_ERR, type="l", lty=2, col=errColor)
			}
			points(xValues, yValues, pch=pointSymbols[iCol],cex=cex.pch,bg=lineColors[iCol], col=lineColors[iCol])

			iCol<-iCol+1
		}
	}
	
	if (!noDecoration) {
		title(main=title, ylab=yaxisLabel, xlab=xaxisLabel, cex.lab=1.2,  ...)
	}
	
	if (length(legendList)>0 && length(legendList)!=length(df.list)) {
		stop(sprintf("You must specify a legend List with the same number of levels ad the stats list. Your legendList=%d, number of stats=%d", length(legendList), length(df.list)) )
	}
	if (legendFormatString != FALSE && length(legendList)>0) {
		legends<-createFactorLegend(legendFormatString, legendList)
		legend(legendPosition,horiz=legendHoriz, x.intersp=0.4,legend=legends,pt.bg=lineColors, pt.cex=2,pch=pointSymbols,
			 lty=lineTypes, col=lineColors,cex=cex.legend,inset=legendInset)
	}
}


#Need to factor these two methods out so that you can pass in wether to do mean or sd
SummaryStatistics.plotManySDs<-function(df.list, title="", xaxisLabel, yaxisLabel, 
	legendFormatString=FALSE, legendList, continuousX=TRUE, 
	lineColors=c(), lineTypes=c(), legendPosition="topleft", xLim=c(), minXZero=T, 
	yLim=c(), errCol="#333333", noDecoration=FALSE,plotTitle="",...) {
			
	first.stat.df<-df.list[[1]]

	if(length(yLim) ==0) {
		ylims<-calculatePlotYLimSD(df.list)	
	} else {
		ylims<-yLim
	}

	minY<-ylims[[1]]
	maxY<-ylims[[2]]
	

	
	if (length(xLim) == 0) {
		xlims<-calculatePlotXLim(df.list, minXZero=minXZero)	
	} else {
		xlims<-xLim
	}
	
	
	minX<-xlims[[1]]
	maxX<-xlims[[2]]
	
	
	pointType <- 19
	
	if (length(lineColors)==0) {
		lineColors <- rainbow(length(df.list))	
	}else if (length(lineColors)!= length(df.list)) {
		stop(sprintf("You must specify the same number of linecolors as you have summary stats to plot. Your LineType count=%d, your stats count=%d", length(lineColors), length(df.list)))
	}
	if (length(lineTypes)==0) {
		lineTypes <- rep(2, length(df.list))
	} else if (length(lineTypes)!= length(df.list)) {
		stop(sprintf("You must specify the same number of linetypes as you have summary stats to plot. Your LineType count=%d, your stats count=%d", length(lineTypes), length(df.list)))
	}


	

	if (is.factor(first.stat.df$X)) {
		xValues<-as.numeric(levels(first.stat.df$X)) #This makes sure that plot treats it right - otherwise it will not plot it as a continuous variable
	} else {
		xValues<-first.stat.df$X		
	}

	plot(xValues, first.stat.df$SD, type="l", lty=lineTypes[1], col=lineColors[1], ann=FALSE, xlim=c(minX, maxX), ylim=c(minY,maxY), xaxt="n", yaxt="n", cex.axis=1.2, cex.lab=1.2,...) #xaxt="n" yaxt="n"	
	
	plottedXAxis<-FALSE

	if (max(xValues)>=maxX) {
		axis(side=1, at=xValues,cex=1.2,cex=1.2,  cex.axis=1.2, cex.lab=1.2,...)
		plottedXAxis<-TRUE
	}
	#las=2 - puts them horizontally
	axis(side=2,cex=1.2, cex=1.2, cex.axis=1.2, cex.lab=1.2, ...)

	points(xValues, first.stat.df$SD, pch=pointType, col=lineColors[1])
	
	iCol<-2
	statCount<-length(df.list)
	if (statCount >1) {
		others.list <- df.list[2:statCount]
		for (i in 1:length(others.list)) {
			#print(sprintf("id:%d, i=%d", iCol, i))		
			stat.df<-others.list[[i]]			
			if (is.factor(stat.df$X)) {
				xValues<-as.numeric(levels(stat.df$X)) #This makes sure that plot treats it right - otherwise it will not plot it as a continuous variable
			} else {
					xValues<-stat.df$X		
			}
			
			if (!plottedXAxis) {
				if (max(xValues)>=maxX) {					
					axis(side=1, at=xValues, ...)
					plottedXAxis<-TRUE				
				}
			}
			
			
			lines(xValues, stat.df$SD, pch=pointType, type="l",lty=lineTypes[iCol] ,col=lineColors[iCol])
			points(xValues, stat.df$SD, pch=pointType, col=lineColors[iCol])

			iCol<-iCol+1
		}
	}
	
	if (!noDecoration) {
		title(main=title, ylab=yaxisLabel, xlab=xaxisLabel, cex.lab=1.2,  ...)
	}
	
	if (length(legendList)>0 && length(legendList)!=length(df.list)) {
		stop(sprintf("You must specify a legend List with the same number of levels ad the stats list. Your legendList=%d, number of stats=%d", length(legendList), length(df.list)) )
	}
	if (legendFormatString != FALSE && length(legendList)>0) {
		legends<-createFactorLegend(legendFormatString, legendList)
		legend(legendPosition,horiz=TRUE, legend=legends, lty=lineTypes, col=lineColors,cex=1.2,inset=0.05)
	}
}

#stat.df<-df.list[[1]]
findMaxXValues<-function(df.list) {
	maxCurr<-0
	maxX<-0
	result.values<-df.list[[1]]$X
	for (stat.df in df.list) {
		maxCurr<-max(as.numeric(levels(stat.df$X)))
		if (!is.na(maxCurr) && maxCurr > maxX) {
			result.values<-stat.df$X
		}
	}
	return (result.values)
	
}
#stat.df<-df.list[[2]]
calculatePlotYLim<-function(df.list) {
	maxY<-0
	for (stat.df in df.list) {
		maxCurr<-max(stat.df$MEAN)	
		if (!is.na(maxCurr) && maxCurr > maxY) {
			ss.df<-subset(stat.df, stat.df$MEAN==maxCurr)
			plusErr<-ss.df$PLUS_ERR[[1]]
			if (is.na(plusErr)) {
				maxY<-maxCurr
			} else {
				maxY<-plusErr
			}

		}
		
	}
	

	
	maxY <- maxY + maxY*.10
	
    minY<-maxY
	for (stat.df in df.list) {
		minCurr<-min(stat.df$MEAN)
		if (!is.na(minCurr) && minCurr < minY) {
			ss.df<-subset(stat.df, stat.df$MEAN==minCurr)
			minusErr<-ss.df$MINUS_ERR[[1]]
			if (is.na(minusErr)) {
				minY<-minCurr
			} else {
				minY<-min(ss.df$MINUS_ERR)
			}

		}
	}
	
	minY <- minY - minY*.10
	if (minY==0) {
		minY <-0
	}
	minY<-0
	
	return (c(minY, maxY))

}

calculatePlotYLimSD<-function(df.list) {
	maxY<-0
	for (stat.df in df.list) {
		maxCurr<-max(stat.df$SD)			
	}
	

	
	maxY <- maxY + maxY*.10
	
    minY<-maxY
	for (stat.df in df.list) {
		minCurr<-min(stat.df$SD)
	}
	
	minY <- minY - minY*.10
	if (minY==0) {
		minY <-0
	}
	minY<-0
	
	return (c(minY, maxY))

}

#df.list<-all.stats
#stat.df<-all.stats[[1]]minXZero=minXZero
calculatePlotXLim<-function(df.list, minXZero=T) {
	maxX<-0
	for (stat.df in df.list) {
		maxCurr<-max(as.numeric(levels(stat.df$X)))
		if (maxCurr > maxX) {
			maxX <- maxCurr
		}
	}
#	maxX <- maxX + maxX*.10
	
    minX<-maxX
	for (stat.df in df.list) {
		minCurr<-min(as.numeric(levels(stat.df$X)))
		if (minCurr < minX) {
			minX <- minCurr
		}
	}
	
#	minX <- minX - minX*.10
	if (minXZero) {
		minX<-0
	}
	
	return (c(minX, maxX))

}

#lwd=2
SummaryStatistics.plotErrorBars<-function(x, yPlus, yMinus, col, lwd) {
	cols<-rep(col, length(x))
	lwds<-rep(lwd, length(x))
	s<-mapply(x, yPlus, yMinus,cols, lwds,FUN=SummaryStatistics.plotSingleErrorBar)	
}

SummaryStatistics.plotSingleErrorBar<-function(x, yMinus, yPlus, col, lwd) {

		arrows(x, yMinus, x,yPlus, angle=90,lwd=lwd, length=0.05, code=3,col=col)	

}


createFactorLegend<-function(formatString, factor) {
	labels<-factor
	legends<-vector();
	for (label in labels) {
		legend<- sprintf(formatString, label)
		legends<-c(legends, legend)
	}
	return (legends)
}

plotErrorBars<-function (...) {
	SummaryStatistics.plotErrorBars(...)
}


