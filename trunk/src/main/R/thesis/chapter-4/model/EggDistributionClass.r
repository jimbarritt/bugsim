#Class definition for a Signal Surface class...


setClass("EggDistribution", representation(
	name="character",
	eggCountFieldName="character",
	cabbages.df="data.frame",
	stats.1m.df="data.frame",
	stats.6m.df="data.frame",
	stats.48m.df="data.frame",
	stats.scaleCode.df="data.frame",
	totalEggs="numeric", 
	totalCabbages="numeric",
	fieldCabbages="FieldCabbages"
	
))

#EggDistribution.eggsPerPlantErrCaption()
#ex<-substitute(paste(a, b %+-% s.e.,c), list(a=partA, b="", c=partC))
#hist(rnorm(100), xlab=EggDistribution.eggsPerPlantErrCaption())
#mtext(side=1,)
EggDistribution.eggsPerPlantErrCaption<-function() {
	partA<-"Eggs / Plant ("
	partC<-")"
	ex<-substitute(paste(a, b %+-% s.e.,c), list(a=partA, b="", c=partC))
	return (ex)
}

EggDistribution.eggsPerPlantCaption<-function() {
	return ("Eggs / Plant")
}

#Add a constructor:colnames(cabbages.df)
EggDistribution<-function(name, cabbages.df, eggCountFieldName, fieldCabbages, applySQRTTransform=T) {
	instance<-new("EggDistribution")	
	if (length(cabbages.df[,1])==0) {
		stop("Passed an empty cabbages.df to EggDistribution Constructor")
	}

	instance@name<-name
	instance@fieldCabbages<-fieldCabbages
	instance@eggCountFieldName<-eggCountFieldName
	instance@cabbages.df<-cabbages.df
	instance@cabbages.df$SQRT_EGG_COUNT<-sqrt(instance@cabbages.df[[eggCountFieldName]])
		
	if (applySQRTTransform) {
		appliedFieldName<-"SQRT_EGG_COUNT"
	} else {
		appliedFieldName<-eggCountFieldName		
	}
	instance@stats.1m.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=instance@cabbages.df, xFactorName="X1m_dens", yFactorName=appliedFieldName)	
	instance@stats.6m.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=instance@cabbages.df, xFactorName="X6m_dens", yFactorName=appliedFieldName)		
	
	if (collection.contains(colnames(instance@cabbages.df),"X48m_dens")) {
		instance@stats.48m.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=instance@cabbages.df, xFactorName="X48m_dens", yFactorName=appliedFieldName)				
	}
	
	
	instance@stats.scaleCode.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=instance@cabbages.df, xFactorName="X3.scale.code", yFactorName=appliedFieldName)	

	
	instance@totalEggs<-sum(cabbages.df$Field.EggCount)
	instance@totalCabbages<-length(cabbages.df$Field.EggCount)
	return (instance)
}

EggDistribution.plotAggregatedDistribution<-function(instance, plotAxes=T,plotLegend=T, cex.axis=2, mgp=c(3, 1, 0),...) {

	if (plotAxes) {
		par(mar=c(7, 7,2, 2 ))
	} else {
		par(mar=c(4, 4, 2, 2))
	}
	stats.list<-list(instance@stats.1m.df, instance@stats.6m.df)
	lineTypes<-c(2, 1)
	lineColors<-c("blue", "blue")
	legendList<-c("1x1m", "6x6m")		
	
	if (!is.null(instance@stats.48m.df)) {
		stats.list<-collection.appendToList(stats.list, instance@stats.48m.df)
		lineTypes<-c(lineTypes, 5)
		lineColors<-c(lineColors, "blue")
		legendList<-c(legendList, "48x48m")				
	}


	label.y<-c(0, 1, 5, 15, 30, 60)
	at.y<-sqrt(label.y)
	
	SummaryStatistics.plotManyResults(stats.list,  "Plant Density", 
		plotErr=TRUE, legendList=c(), continuousX=FALSE,noDecoration=T,
		 lineColors=lineColors, yLim=c(0, sqrt(60)),at.y=at.y, labels.y=label.y,sqrtY=F,logY=F,
		 cex.pch=1.5, lineTypes=lineTypes,cex.axis=cex.axis,mgp=mgp, ...)			
		
	if (plotLegend) {
		pointSymbols<-c(19, 15, 17, 24, 22, 23, 15, 24, 25)
		legend("topright",horiz=T, legend=legendList,pt.bg=lineColors, pch=pointSymbols,
					 lty=lineTypes, col=lineColors,cex=1.2,inset=0.05)
	}
				
    if (plotAxes) {
		mtext(side=1, line=4, "Plant Denstity", cex=cex.axis)
		mtext(side=2, line=4, EggDistribution.eggsPerPlantErrCaption(), cex=cex.axis)
	}
}


EggDistribution.plotScaleCode<-function(instance, sortScale, ...) {	
	if (sortScale=="1M") {
		sortOrder<-instance@fieldCabbages@scaleCodeSortOrder.1M
		title<-expression("a) Sorted at 1M Scale")
	} else if (sortScale=="6M") {
		sortOrder<-instance@fieldCabbages@scaleCodeSortOrder.6M
		title<-expression("b) Sorted at 6M Scale")
 	} else if (sortScale=="48M") {
		sortOrder<-instance@fieldCabbages@scaleCodeSortOrder.48M
		title<-expression("c) Sorted at 48M Scale")
 	} else {
		stop("You must specify a sortScale of either '1M', '6M' or '48M'")
	}

	if (length(sortOrder)==0) {
		stop("Must specify a sort order in FieldCabbages class")
	}
	pch.unsorted<-instance@fieldCabbages@scaleCodeBlockSymbols
	
	stats.ScaleCode.df<-instance@stats.scaleCode.df

	y<-c()
	yPlusErr<-c()
	yMinusErr<-c()
	labels<-c()
	pch<-c()
	levels<-levels(stats.ScaleCode.df$X)
	for (x1 in sortOrder) {
		currentLevel<-levels[[x1]]
		y<-c(y, stats.ScaleCode.df$MEAN[stats.ScaleCode.df$X==currentLevel])
		yPlusErr<-c(yPlusErr, stats.ScaleCode.df$PLUS_ERR[stats.ScaleCode.df$X==currentLevel])
		yMinusErr<-c(yMinusErr, stats.ScaleCode.df$MINUS_ERR[stats.ScaleCode.df$X==currentLevel])
		
		labels<-c(labels, currentLevel)
		pch<-c(pch, pch.unsorted[x1])
	}


	x<-1:length(levels)
	par(mar=c(7, 7, 4, 5))
	plot(y~x, pch=NA, ylim=c(0, sqrt(60)), axes=F,
	 ylab="", xlab="",cex.lab=1.2, ...)
	
	mtext(side=2, line=4, EggDistribution.eggsPerPlantErrCaption(), cex=1)
	mtext(side=1, line=4, expression("Scale Code"), cex=1)
	SummaryStatistics.plotErrorBars(x, yPlusErr, yMinusErr, lwd=1.5, col="blue")
	points(y~x, pch=pch, cex=1.5)
	y.labels<-c(0, 1, 5,  15, 30, 60)
	y.at<-sqrt(y.labels)
	axis(2, at=y.at, labels=y.labels, cex.axis=1.2)
	axis(1,at=x, labels=labels, cex.axis=1.2)
	box()
	title(main=title, cex.main=1.5)


	legend("topright",legend=instance@fieldCabbages@blocks, pch=instance@fieldCabbages@blockSymbols, inset=0.025 )	
}



EggDistribution.breakdownByFactor<-function(eggDistribution, factorName, eggCountFieldName="Simulation.Egg.Count") {
	cabbages.df<-eggDistribution@cabbages.df
	
	x.factor<-cabbages.df[[factorName]]
	if (!is.factor(x.factor)) {
		x.factor<-as.factor(x.factor)
	}
	
	breakdown.list<-list()
	for (i.level in levels(x.factor)) {
		ss.df<-subset(cabbages.df, cabbages.df[[factorName]]==i.level)
	
		childDistribution<-EggDistribution(sprintf("EggDist %s", i.level), ss.df, eggCountFieldName, eggDistribution@fieldCabbages)
		
		breakdown.list<-collection.appendToList(breakdown.list, childDistribution)
	}

	return (breakdown.list)
	
}


#Add a "toString" which is a "show method"
setMethod("show", "EggDistribution", 
	function(object) {
		cat("EggDistribution (", object@name, ")\n")
		cat("1m Statistics:\n")
		show(object@stats.1m.df)
		cat("6m Statistics:\n")
		show(object@stats.6m.df)
		cat("48m Statistics:\n")
		show(object@stats.48m.df)
		cat("Scale Code Statistics:\n")
		show(object@stats.scaleCode.df)
		
		levels<-levels(object@cabbages.df$X3.scale.code)
		for (x in 1:length(levels)) {
			cat(x, "\t", levels[[x]], "\n")
		}
	}
)



#And a "summary" method
setMethod("summary", "EggDistribution", 
	function(object) {
	}
)

setMethod("plot", "EggDistribution",
	function(x, y, ...) {
		
		instance<-x
		result<-EggDistribution.genericPlot(instance, ...)
				
		if (!is.null(result)) {
			return (result)
		} 

	}
)

EggDistribution.genericPlot<-function(instance, plotType="AGGREGATED", ...) {

	result<-NULL
 	if (plotType=="AGGREGATED") {
		EggDistribution.plotAggregatedDistribution(instance, ...)
	} else if (plotType=="AGGREGATED-LOGLOG") {		
		result<-EggDistribution.plotLogLogAggregated(dist=instance,instance@eggCountFieldName)		
	} else if (plotType=="AGGREGATED-SQSQ") {		
		result<-EggDistribution.plotSqSqAggregated(dist=instance,instance@eggCountFieldName)		
	}else if (plotType=="SCALE_CODE") {		
		EggDistribution.plotScaleCode(instance, ...)
	}else{
		stop("You must pass in either 'SCALE_CODE' or 'AGGREGATED' as the 'plotType' parameter to get a plot of an egg distribution")
	}
	return (result)

}

setMethod("boxplot", "EggDistribution", 
	function(x, y, ...) {
		eggDistribution<-x
		par(mfrow=c(2, 1))
		boxplot(eggDistribution@cabbages.df[[eggDistribution@eggCountFieldName]]~eggDistribution@cabbages.df$X1m_dens, main="1x1m")
		boxplot(eggDistribution@cabbages.df[[eggDistribution@eggCountFieldName]]~eggDistribution@cabbages.df$X6m_dens, main="6x6m")		
	}
)

#eggCountColumnName="Field.EggCount"
EggDistribution.plotLogLogAggregated<-function(dist, eggCountColumnName, plot=T) {
	stats.1m.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=dist@cabbages.df, xFactorName="X1m_dens", yFactorName=eggCountColumnName)	
	stats.6m.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=dist@cabbages.df, xFactorName="X6m_dens", yFactorName=eggCountColumnName)	

	if (dist@fieldCabbages@has48mScale) {
		stats.48m.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=dist@cabbages.df, xFactorName="X48m_dens", yFactorName=eggCountColumnName)			
	}

	x.1m<-as.numeric(levels(stats.1m.df$X))
	y.1m<-stats.1m.df$MEAN
	lm.1m<-lm(log(y.1m)~log(x.1m))

	x.6m<-as.numeric(levels(stats.6m.df$X))
	y.6m<-stats.6m.df$MEAN
	lm.6m<-lm(log(y.6m)~log(x.6m))
	
	x.48m<-c()
	if (dist@fieldCabbages@has48mScale) {
		x.48m<-as.numeric(levels(stats.48m.df$X))
		y.48m<-stats.48m.df$MEAN
		lm.48m<-lm(log(y.48m)~log(x.48m))
	}

	if (plot) {
		pointSymbols<-c(19, 15, 17, 24, 22, 23, 15, 24, 25)
		par(mar=c(7, 7, 2, 2))
		plot(y.1m~x.1m, pch=NA, ylim=c(1, 60), xlim=c(1, 40),axes=F, log="xy",ann=F)

		points(y.1m~x.1m, cex=2, pch=1, col="blue", lwd=2)
		points(y.6m~x.6m, cex=2, pch=22, col="blue", lwd=2)

		labels.y<-c(1, 5, 15, 30, 60)
		at.y<-sqrt(labels.y)
		at.y<-labels.y
		axis(1, at=c(1, 4, 16, 40), cex.axis=1.5)
		axis(2, at=at.y, labels=labels.y,cex.axis=1.5 )
		mtext(side=1, line=4, cex=1.5, "Plant Density")
		mtext(side=2, line=4, cex=1.5, EggDistribution.eggsPerPlantCaption())
		box()

		GraphUtil.plotLogRegressionLine(x.1m, lm.1m, col="blue", lty=2, lwd=1.5)
		GraphUtil.plotLogRegressionLine(x.6m, lm.6m, col="blue", lty=1, lwd=1.5)
		
		if (dist@fieldCabbages@has48mScale) {
			points(y.48m~x.48m, cex=2, pch=2, col="blue", lwd=2)
			GraphUtil.plotLogRegressionLine(x.48m, lm.48m, col="blue", lty=5, lwd=1.5)			
		}
	}
	
	results<-list("lm.1m"=lm.1m, "lm.6m"=lm.6m)
	regressionLabel.1<-"lm(y.1m~x)"
	regressionLabel.2<-"lm(y.6m~x)"
	regressionLabel.3<-"lm(y.48m~x)"
	legends<-c("1x1m", "6x6m",regressionLabel.1, regressionLabel.2)
	legend.pch<-c(1,22, NA, NA)
	legend.lty<-c(NA,NA,2,1)
	if (dist@fieldCabbages@has48mScale) {
		results<-list("lm.1m"=lm.1m, "lm.6m"=lm.6m, "lm.48m"=lm.48m)
		legends<-c("1x1m","6x6m","48x48m", regressionLabel.1, regressionLabel.2, regressionLabel.3)
		legend.pch<-c(1,  22, 2, NA, NA, NA)
		legend.lty<-c(NA, NA, NA,2,  1,  5)		
	}
	
	legend("topright", legend=legends, ncol=2,pch=legend.pch, lty=legend.lty, lwd=2, y.intersp=1.5,
		col=rep("blue", 6),xjust=0.5,cex=1.2,pt.cex=2, inset=0.05)
	return (results)
}


#eggCountColumnName="Field.EggCount"
EggDistribution.plotSqSqAggregated<-function(dist, eggCountColumnName, plot=T) {
	stats.1m.df<-dist@stats.1m.df
	stats.6m.df<-dist@stats.6m.df

	if (dist@name=="KAITOKE-04 Field Distribution") {
		stats.48m.df<-dist@stats.48m.df
	}

	x.1m<-sqrt(as.numeric(levels(stats.1m.df$X)))
	y.1m<-stats.1m.df$MEAN
	lm.1m<-lm(y.1m~x.1m)

	x.6m<-sqrt(as.numeric(levels(stats.6m.df$X)))
	y.6m<-stats.6m.df$MEAN
	lm.6m<-lm(y.6m~x.6m)
	
	x.48m<-c()
	if (dist@name=="KAITOKE-04 Field Distribution") {
		x.48m<-sqrt(as.numeric(levels(stats.48m.df$X)))
		y.48m<-stats.48m.df$MEAN
		lm.48m<-lm(y.48m~x.48m)
	}

	if (plot) {
		pointSymbols<-c(19, 15, 17, 24, 22, 23, 15, 24, 25)
		par(mar=c(7, 7, 2, 2))
		plot(y.1m~x.1m, pch=NA, ylim=c(0, sqrt(60)), xlim=c(0, sqrt(40)),axes=F,ann=F)

		points(y.1m~x.1m, cex=2, pch=1, col="blue", lwd=2)
		points(y.6m~x.6m, cex=2, pch=22, col="blue", lwd=2)

		labels.x<-c(0, 1, 4, 16, 40)
		at.x<-sqrt(labels.x)

		labels.y<-c(0, 1, 5, 15, 30, 60)
		at.y<-sqrt(labels.y)

		axis(1, at=at.x, labels=labels.x, cex.axis=1.5)
		axis(2, at=at.y, labels=labels.y,cex.axis=1.5 )
		mtext(side=1, line=4, cex=1.5, "Plant Density")
		mtext(side=2, line=4, cex=1.5, EggDistribution.eggsPerPlantErrCaption())
		box()

		GraphUtil.plotSqrtRegressionLine(x.1m, lm.1m, col="blue", lty=2, lwd=1.5)
		GraphUtil.plotSqrtRegressionLine(x.6m, lm.6m, col="blue", lty=1, lwd=1.5)
		
		if (dist@name=="KAITOKE-04 Field Distribution") {
			points(y.48m~x.48m, cex=2, pch=2, col="blue", lwd=2)
			GraphUtil.plotSqrtRegressionLine(x.48m, lm.48m, col="blue" , lty=5, lwd=1.5)			
		}
	}
	
	results<-list("lm.1m"=lm.1m, "lm.6m"=lm.6m)
	regressionLabel.1<-"lm(y.1m~x)"
	regressionLabel.2<-"lm(y.6m~x)"
	regressionLabel.3<-"lm(y.48m~x)"
	legends<-c("1x1m", "6x6m",regressionLabel.1, regressionLabel.2)
	legend.pch<-c(1,22, NA, NA)
	legend.lty<-c(NA,NA,2,1)
	if (dist@name=="KAITOKE-04 Field Distribution") {
		results<-list("lm.1m"=lm.1m, "lm.6m"=lm.6m, "lm.48m"=lm.48m)
		legends<-c("1x1m","6x6m","48x48m", regressionLabel.1, regressionLabel.2, regressionLabel.3)
		legend.pch<-c(1,  22, 2, NA, NA, NA)
		legend.lty<-c(NA, NA, NA,2,  1,  5)		
	}
	
	legend("topright", legend=legends, ncol=2,pch=legend.pch, lty=legend.lty, lwd=2, y.intersp=1.5,
		col=rep("blue", 6),xjust=0.5,cex=1.2,pt.cex=2, inset=0.05)
	
	return (results)
}
#quartz(width=16, height=6) dist@has48m
EggDistribution.plotLogLogBreakdownMultiplePlot<-function(dist) {

	xlevels.1m<-as.numeric(levels(dist@cabbages.df$X1m_dens))				
	x.1m<-as.numeric(levels(dist@cabbages.df$X1m_dens)[dist@cabbages.df$X1m_dens])
	y.1m<-(dist@cabbages.df[[dist@eggCountFieldName]])+1	

		
	xlevels.6m<-as.numeric(levels(dist@cabbages.df$X6m_dens))				
	x.6m<-as.numeric(levels(dist@cabbages.df$X6m_dens)[dist@cabbages.df$X6m_dens])
	y.6m<-(dist@cabbages.df[[dist@eggCountFieldName]])+1


	if (dist@fieldCabbages@has48mScale) {
		layout.mx<-rbind(c(1,2, 3, 4),
		 				 c(5, 5, 5, 5))
		layout(layout.mx, heights=c(93, 7), widths=c(3, 33, 33, 33))
		
	} else {
		layout.mx<-rbind(c(1, 2, 3, 4, 5),
		 				 c(6, 6, 6, 6, 6))
		layout(layout.mx, heights=c(93, 7), widths=c(16, 3, 33, 33, 16))		
		
		par(mar=c(0, 0, 0,0))
		plot.new()
	}
	par(mar=c(0, 0, 0,0))
	plot.new()
	
	mtext(side=2,line=-3, EggDistribution.eggsPerPlantCaption(), cex=2)
	result.1m<-plotLogLogBreakdown(x.1m, y.1m, pch=1, "1x1m", "lm(y.1m~x)",xlevels.1m, expression("a) 1x1m Scale"))
	result.6m<-plotLogLogBreakdown(x.6m, y.6m, pch=22, "6x6m", "lm(y.6m~x)",xlevels.6m, expression("b) 6x6m Scale"))

	results<-list("lm.1m"=result.1m, "lm.6m"=result.6m)
	if (dist@fieldCabbages@has48mScale) {
		xlevels.48m<-as.numeric(levels(dist@cabbages.df$X48m_dens))				
		x.48m<-as.numeric(levels(dist@cabbages.df$X48m_dens)[dist@cabbages.df$X48m_dens])
		y.48m<-(dist@cabbages.df[[dist@eggCountFieldName]])+1				
		
		result.48m<-plotLogLogBreakdown(x.48m, y.48m, pch=2, "48x48m", "lm(y.48m~x)",xlevels.48m, expression("c) 48x48m Scale"))		
		results<-list("lm.1m"=result.1m, "lm.6m"=result.6m, "lm.48m"=result.48m)
	} else {
		plot.new()
	}
	
	par(mar=c(0, 0, 0,0))
	plot.new()	
	mtext(side=1,line=-2, "Plant Density", cex=2)

	
	return (results)		
}

#x<-x.48m
jitterLogXs<-function(x, jitterFactors) {
	x[x==1]<-jitter(x[x==1], factor=jitterFactors[1])
	x[x==4]<-jitter(x[x==4], factor=jitterFactors[2])
	x[x==16]<-jitter(x[x==16], factor=jitterFactors[3])
	x[x==40]<-jitter(x[x==40], factor=jitterFactors[3])
	return (x)
}

plotLogLogBreakdown<-function(x, y, pch, legendLabel, regressionLabel, xlevels, title) {
	lm.result<-lm(log(y)~log(x))
	
	pch.col<-"#666666"
	line.col<-"blue"
	jitterFactors<-c(15, 15, 15, 15)
	x<-jitterLogXs(x, jitterFactors)
	
	par(mar=c(4, 4, 4, 4))
	
	plot(y~x,pch=NA,log="xy", xlim=c(0.7, 50), ylim=c(1, 100), ann=F, axes=F)
	points(y~x, pch=pch, col=pch.col, cex=1.5)
		
	labels.x<-c(1, 4, 16, 40)
	at.x<-labels.x
	labels.y<-c(0,1, 5, 15, 30, 60)
	at.y<-c(1, 2, 6, 16, 31, 61)
	axis(1, at=at.x, labels=labels.x, cex.axis=1.7)
	axis(2, at=at.y, labels=labels.y, cex.axis=1.7)
	box()

	

	GraphUtil.plotLogRegressionLine(xlevels, lm.result, col=line.col, lty=1, lwd=2)	

	legends<-c(legendLabel, regressionLabel)
	legend.pch<-c(pch, NA)
	legend.lty<-c(NA,1)
	
	legend("topright", legend=legends, ncol=2,pch=legend.pch, lty=legend.lty, lwd=1.5, 
		col=c(pch.col, line.col),cex=1.5,pt.cex=1.5, inset=0.025)
	
	
	#mtext(side=1,line=4, "Plant Density", cex=1.2)
	#mtext(side=2,line=4, "Eggs Per Plant", cex=1.2)
	title(main=title, cex.main=2.5)
	return (lm.result)
}


#Plot unaggregated LOG-LOG on a single plot
EggDistribution.plotLogLogBreakdownSinglePlot<-function(dist) {
	par(mar=c(7, 7, 2, 2))
	adj.1<-0.05
	adj.4<-0.25
	adj.16<-1
	adj.40<-2
	
	x.1m<-as.numeric(levels(dist@cabbages.df$X1m_dens)[dist@cabbages.df$X1m_dens])
	y.1m<-(dist@cabbages.df$Field.EggCount/x.1m)+1
	x.1m[x.1m==1]<-x.1m[x.1m==1]-adj.1
	x.1m[x.1m==4]<-x.1m[x.1m==4]-adj.4
	x.1m[x.1m==16]<-x.1m[x.1m==16]-adj.16
	x.1m[x.1m==40]<-x.1m[x.1m==40]-adj.40

	x.6m<-as.numeric(levels(dist@cabbages.df$X6m_dens)[dist@cabbages.df$X6m_dens])
	y.6m<-(dist@cabbages.df$Field.EggCount/x.1m)+1


	plot(y.1m~x.1m,pch=NA,log="xy", xlim=c(1, 40), ylim=c(1, 60), ann=F, axes=F)

	
	if (dist@name=="KAITOKE-04 Field Distribution") {
		x.6m[x.6m==1]<-x.6m[x.6m==1]
		x.6m[x.6m==4]<-x.6m[x.6m==4]
		x.6m[x.6m==16]<-x.6m[x.6m==16]
		x.6m[x.6m==40]<-x.6m[x.6m==40]
		
		x.48m<-as.numeric(levels(dist@cabbages.df$X48m_dens)[dist@cabbages.df$X48m_dens])
		y.48m<-(dist@cabbages.df$Field.EggCount/x.1m)+1
		x.48m[x.48m==1]<-x.48m[x.48m==1]+adj.1
		x.48m[x.48m==4]<-x.48m[x.48m==4]+adj.4
		x.48m[x.48m==16]<-x.48m[x.48m==16]+adj.16
		x.48m[x.48m==40]<-x.48m[x.48m==40]+adj.40		
		
		points(y.48m~x.48m, pch=2,bg="blue", col="blue",cex=1)
		
	} else {
		x.6m[x.6m==1]<-x.6m[x.6m==1]+adj.1
		x.6m[x.6m==4]<-x.6m[x.6m==4]+adj.4
		x.6m[x.6m==16]<-x.6m[x.6m==16]+adj.16
		x.6m[x.6m==40]<-x.6m[x.6m==40]+adj.40		
	}
	points(y.1m~x.1m, pch=1, col="blue", cex=1)
	points(y.6m~x.6m, pch=22, col="blue",cex=1)
		
	labels.x<-c(1, 4, 16, 40)
	at.x<-labels.x
	labels.y<-c(1, 5, 15, 30, 60)
	at.y<-labels.y

	axis(1, at=at.x, labels=labels.x, cex.axis=1.5)
	axis(2, at=at.y, labels=labels.y, cex.axis=1.5)
	box()
	lm.1m<-lm(log(y.1m)~log(x.1m))
	lm.6m<-lm(log(y.6m)~log(x.6m))

	xlevels.1m<-as.numeric(levels(dist@cabbages.df$X1m_dens))
	xlevels.6m<-as.numeric(levels(dist@cabbages.df$X6m_dens))	

	GraphUtil.plotLogRegressionLine(xlevels.1m, lm.1m, col="blue", lty=2, lwd=1.5)
	GraphUtil.plotLogRegressionLine(xlevels.6m, lm.6m, col="blue", lty=1, lwd=1.5)
	
	results<-list("lm.1m"=lm.1m, "lm.6m"=lm.6m)
	if (dist@name=="KAITOKE-04 Field Distribution") {
		lm.48m<-lm(log(y.48m)~log(x.48m))		
		xlevels.48m<-as.numeric(levels(dist@cabbages.df$X48m_dens))
		GraphUtil.plotLogRegressionLine(xlevels.48m, lm.48m, col="blue", lty=5, lwd=1.5)		
		results<-list("lm.1m"=lm.1m, "lm.6m"=lm.6m, "lm.48m"=lm.48m)		
	}
	
	regressionLabel.1<-"lm(y.1m~x)"
	regressionLabel.2<-"lm(y.6m~x)"
	regressionLabel.3<-"lm(y.48m~x)"
	legends<-c("1x1m", "6x6m",regressionLabel.1, regressionLabel.2)
	legend.pch<-c(1,22, NA, NA)
	legend.lty<-c(NA,NA,2,1)
	if (dist@name=="KAITOKE-04 Field Distribution") {
		legends<-c("1x1m","6x6m","48x48m", regressionLabel.1, regressionLabel.2, regressionLabel.3)
		legend.pch<-c(1,  22, 2, NA, NA, NA)
		legend.lty<-c(NA, NA, NA,2,  1,  5)		
	}
	
	legend("top", legend=legends, ncol=2,pch=legend.pch, lty=legend.lty, lwd=2, y.intersp=1.5,
		col=rep("blue", 6),xjust=0.5,cex=1.2,pt.cex=2, inset=0.05)
	
	
	mtext(side=1,line=4, "Plant Density", cex=1.5)
	mtext(side=2,line=4, EggDistribution.eggsPerPlantCaption(), cex=1.5)
	
	return (results)		
}



