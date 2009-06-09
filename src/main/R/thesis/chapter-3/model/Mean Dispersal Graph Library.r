# Plots graphs we need for the mean dispersal results

md.plotMSDWithKMultipleL<-function(Ls, timesteps) {
	par(mfrow=c(2, 2))

	L<-Ls[1]
	title<-sprintf("a) steps=%d, L=%d", timesteps, L)
	axis.at.y<-c(100, 200, 1000, 10000)
	ylim<-c(100, 10000)
	md.plotMSDWithK(L, timesteps,  axis.at.y=NA, title)

	L<-Ls[2]
	title<-sprintf("b) steps=%d, L=%d", timesteps, L)
	md.plotMSDWithK(L, timesteps, NA, title)

	L<-Ls[3]
	title<-sprintf("c) steps=%d, L=%d", timesteps, L)
	md.plotMSDWithK(L, timesteps, axis.at.y=NA, title)

	L<-Ls[4]
	title<-sprintf("d) steps=%d, L=%d", timesteps, L)
	md.plotMSDWithK(L, timesteps, axis.at.y=NA, title)
}


md.plotMSDWithK<-function(expectedL, timesteps, axis.at.y, title, ...) {
	x<-2:(length(circular.kappaTable.df$k)-1)
	xK<-circular.kappaTable.df$k[x]
	xRho<-circular.kappaTable.df$rho[x]
	ySJ<-sapply(xRho, md.Erho, timesteps, expectedL, "skellam-jones")


	par(mar=c(6, 6, 5, 5))
	plot(ySJ~xK, type="p", pch=NA, log="xy" ,axes=F, ann=F, ...)
	lines(ySJ~xK,lty=1, lwd=2, col="blue")
	axis.at<-c(0.1, 1,10, 100, 500)
	axis(1, at=axis.at, labels=c("", "1", "10", "100", "500"), cex.axis=1.5)
	axis(1, at=1:10* rep(axis.at[-1] / 10, each = 10), tcl = -0.5, labels = FALSE)

	if (sum(is.na(axis.at.y))==0) {
		axis(2,at=axis.at.y, cex.axis=1.5)
		axis(2, at=1:10* rep(axis.at[-1] / 10, each = 10), tcl = -0.5, labels = FALSE)
	} else {
		axis(2, cex.axis=1.5)
	}
	box()
	title(main="", ylab=expression("MSD"), xlab=expression("k"), cex.main=1.5,cex.lab=2, line=3)
	mtext(side=3, substitute(a, list(a=title)), cex.main=1.5, line=2)
	
}


md.plotResults<- function(iterationNumber, input.df, groupingFactorName, titleString, filename="NONE", newWindow=TRUE, ageLimit) {
	if (newWindow) {
		quartz(height=8.25, width=8.25)
	}

	in.df<-subset(input.df, input.df$iteration==iterationNumber)
	md.plotResultsGrouped(in.df, groupingFactorName, titleString, ageLimit);
}


md.plotResultsGrouped<-function(input.df, groupingFactorName, titleString, ageLimit) {
	groupingFactor<-input.df[[groupingFactorName]]


	if (is.null(groupingFactor)) {
		show(colnames(input.df))
		stop(sprintf("Could not find grouping factor '%s' in names.\n", groupingFactorName))
		
	}
	
	f<-groupingFactor
	if (is.factor(f) != TRUE) {
		f<-as.factor(groupingFactor)
	}
	for (groupLevel in levels(f)) {
		cat("Plotting level: ", groupLevel, "\n")
		stats.group.df<-subset(input.df, groupingFactor==groupLevel)
		stats.age.df<-subset(stats.group.df, stats.group.df$AGE<=ageLimit)
		stats.age.df$X<-as.factor(stats.age.df$AGE)	# to trim off old levels...
		xFactor<-stats.age.df$X
		md.plotLogFactorsWithRegression(stats.age.df, "log(Moves)", "log(MSD)", xFactor, stats.age.df$MINUS_ERR, stats.age.df$MEAN, stats.age.df$PLUS_ERR, titleString)
	}

}

md.simplePlot<-function(dispersalStats,iterationNumber,  titleString, ageLimit, ...) {
		input.df<-dispersalStats@summary.df
		
		input.df$MEAN<-as.numeric(input.df$MEAN)
		input.df$AGE<-as.numeric(input.df$AGE)
		input.df$MEAN_MINUS_ERR<-as.numeric(input.df$MEAN_MINUS_ERR)
		input.df$MEAN_PLUS_ERR<-as.numeric(input.df$MEAN_PLUS_ERR)
		input.df$VARIANCE<-as.numeric(input.df$VARIANCE)
		input.df$SE<-as.numeric(input.df$SE)
		
		stats.age.df<-subset(input.df, input.df$AGE<=ageLimit)
		stats.age.df$X<-as.factor(stats.age.df$AGE)	# to trim off old levels...
		xFactor<-stats.age.df$X
		results<-md.plotLogFactorsWithRegression(stats.age.df, "log(Timesteps)", "log(MSD)", xFactor, stats.age.df$MINUS_ERR, stats.age.df$MEAN, stats.age.df$PLUS_ERR, titleString, ...)
		return(results)
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
md.plotLogFactorsWithRegression <- function(src.df, xaxisFactorName, yaxisName, xaxisFactor, minusStdError, yaxisFactor, plusStdError,  titleString, stats=T,ageSplit=7,ageSplitUpper=9,ageLines=T,expectedMSD=T, expectedK=-1, expectedL=1,...) {
		pointType <- 19
		lineColor <- "blue"
		errColor <- "grey"
		point.cex<-1.5
		
		title <- titleString

		xValues<-src.df$AGE
		par(mar=c(6, 6, 3, 3))
		plot(xValues, yaxisFactor, axes=F, type="l", lty=2, col=lineColor, log="xy", xlim=c(1,2000), ann=FALSE) #xaxt="n"
		title(main=title, ylab=yaxisName, xlab=xaxisFactorName, lwd=0.1, tcl=2, cex.main=1.5,cex.lab=1.5)
		axis.at<-c(1,10, 100, 1000)
		axis(1, at=axis.at, cex.axis=1.5)
		axis(1, at=1:10* rep(axis.at[-1] / 10, each = 10), tcl = -0.5, labels = FALSE)
		axis(2, cex.axis=1.5)
		points(xValues, yaxisFactor, pch=pointType, col=lineColor)

		box()
		lower.df<-subset(src.df, src.df$AGE<=ageSplit)		
		lower.lm <- lm(log(lower.df$MEAN)~log(lower.df$AGE), data=lower.df)

		upper.df<-subset(src.df, src.df$AGE >ageSplitUpper)
		

		upper.lm <- lm(log(upper.df$MEAN)~log(upper.df$AGE), data=upper.df)


		slope1<-lower.lm$coef[[2]]
		intercept1<-lower.lm$coef[[1]]

		#Manually plot the regression line as it doesnt appear in the right place otherwise
		i1<-length(lower.df$AGE);
		i2<-length(upper.df$AGE);
		xMax<-upper.df$AGE[i2]*1.2 #use the upper X value so we get a line that covers the graph
		yMax<- exp(log(xMax)*slope1+intercept1)
		lines(c(1, xMax), c(exp(intercept1),  yMax), col="grey")


		slope2<-upper.lm$coef[[2]]
		intercept2<-upper.lm$coef[[1]]

		xMax<-upper.df$AGE[i2]*1.2
		yMax<- exp(log(xMax)*slope2+intercept2)
		lines(c(1, xMax), c(exp(intercept2),  yMax), col="grey")

		intersection<-Geometry.calculateIntersection(slope1, intercept1, slope2, intercept2)

		if (sum(is.na(intersection))==0) {
			intersectionAge<-exp(intersection$x)
			intersectionMSD<-exp(intersection$y)
			if (ageLines ) {
				points(intersectionAge, intersectionMSD, col="red", cex=point.cex)
				lines(x=c(intersectionAge, intersectionAge), y=c(0.1, intersectionMSD), col="red")
				lines(x=c(0.1, intersectionAge), y=c(intersectionMSD, intersectionMSD), col="red")
			}
			if (stats) {
				text(4,max(src.df$MEAN[!is.na(src.df$MEAN)]), pos=1,sprintf("r1=%01.3f, r2=%01.3f \nage=%0.0f, msd=%0.0f", slope1, slope2, intersectionAge, intersectionMSD))
			}
			
		} else {
			intersectionAge<-NA
			intersectionMSD<-NA
		}
		
		if (expectedMSD) {
			rho<-lookupRho(expectedK)
			if (!is.na(rho)) {
				x<-1:max(src.df$AGE)
				ySkellamJones<-sapply(x, md.E, rho, expectedL, "skellam-jones")
				lines(ySkellamJones~x, col="green")
			}
		}
		
		
		
		results <- list("slope1"=slope1, "slope2"=slope2, "intercept1"=intercept1, "intercept2"=intercept2,
				"intersectionAge"=intersectionAge, "intersectionMSD"=intersectionMSD,
				"lower.lm"=lower.lm, "upper.lm"=upper.lm)

		return (results)
}

lookupRho<-function(expectedK) {
	rho<-NA
	if (expectedK==0.5) {
		rho<-0.24250
	} else if (expectedK==0.8) {
		rho<-0.37108
	} else if (expectedK==3) {
		rho<-0.80999
	} else if (expectedK==10) {
		rho<-0.94860
	} else if (expectedK==20) {
		rho<-0.97467
	}
	return (rho)
		
}
md.generateSelectedEMSD<-function(rowNums, graphIds, expectedLs) {
	par(mfrow=c(2, 2))
	result.lst<-list()
	for (i in 1:length(rowNums)) {
		expectedK<-circular.kappaTable.df$k[rowNums[i]]
		rho<-circular.kappaTable.df$rho[rowNums[i]]
		expectedL<-expectedLs[i]

		title<-sprintf("%s) k=%0.2f, L=%0.0f", graphIds[i], expectedK, expectedL)

		results<-md.plotExpectedMSD(rho, expectedL, title, plot=T)
		result.lst<-collection.appendToList(result.lst, results)
	}
	return (result.lst)
}

md.plotXAGEWithK<-function() {
	x<-2:(length(circular.kappaTable.df$k)-1)
	k<-c()
	xAge<-c()
	for (i in x) {
		expectedK<-circular.kappaTable.df$k[i]
		rho<-circular.kappaTable.df$rho[i]
		expectedL<-1

		results<-md.plotExpectedMSD(rho, expectedL, title, plot=F)
		k<-c(k, expectedK)
		xAge<-c(xAge, results$intersectionAge)
	}

	par(mar=c(7, 7,5 ,5))
	plot(xAge~k, type="p", pch=NA, log="xy" ,axes=F, ann=F, ylim=c(5, 300))

	axis.at<-c(0.1, 1,10, 100, 500)
	axis(1, at=axis.at, cex.axis=1.5)
	axis(1, at=1:10* rep(axis.at[-1] / 10, each = 10), tcl = -0.5, labels = FALSE)
	axis(2, at=c(5,10, 20, 50, 100, 200, 300), cex.axis=1.5)
	lines(xAge~k,lty=2, col="blue", )
	points(xAge~k,pch=19, col="blue", cex=1)
	box()
	title(main="", ylab="log(xAGE)", xlab="log(k)", cex.main=1.5,cex.lab=1.5, line=4)	
	
	results.df<-data.frame(k, xAge)
	return (results.df)
}

#par(mfrow=c(2,2)); md.plotExpectedMSD(0.8, 1, "Hello?")
md.plotExpectedMSD<-function(rho, expectedL, title, plot=T){
	x<-DispersalStatistics.generateLogTimeIntervals()
	ySkellamJones<-sapply(x, md.E, rho, expectedL, "skellam-jones")

	data.df<-data.frame(x)
	data.df$y<-ySkellamJones

	lower.df<-subset(data.df, data.df$x<=7)
	upper.df<-subset(data.df, data.df$x>=400)

	lower.lm <- lm(log(lower.df$y)~log(lower.df$x), data=lower.df)	
	upper.lm <- lm(log(upper.df$y)~log(upper.df$x), data=upper.df)

	if(plot){
		par(mar=c(6, 6, 3, 3))
		plot(ySkellamJones~x, axes=F, type="l", lty=2, col="blue", log="xy", xlim=c(1,2000), ann=FALSE)
		title(main="", ylab="MSD", xlab="Timesteps", lwd=0.1, tcl=2, cex.main=2,cex.lab=2)
		mtext(side=3, line=1,substitute(a, list(a=title)), cex=1.5)
		axis.at<-c(1,10, 100, 1000)
		axis(1, at=axis.at, cex.axis=1.5)
		axis(1, at=1:10* rep(axis.at[-1] / 10, each = 10), tcl = -0.5, labels = FALSE)
		axis(2, cex.axis=1.5)
		points(ySkellamJones~x, pch=16, col="blue")

		box()
	}
	slope1<-lower.lm$coef[[2]]
	intercept1<-lower.lm$coef[[1]]

	#Manually plot the regression line as it doesnt appear in the right place otherwise
	i1<-length(lower.df$x);
	i2<-length(upper.df$x);
	xMax<-upper.df$x[i2]*1.2 #use the upper X value so we get a line that covers the graph
	yMax<- exp(log(xMax)*slope1+intercept1)
	
	if (plot) {
		lines(c(1, xMax), c(exp(intercept1),  yMax), col="grey")
	}

	slope2<-upper.lm$coef[[2]]
	intercept2<-upper.lm$coef[[1]]

	xMax<-upper.df$x[i2]*1.2
	yMax<- exp(log(xMax)*slope2+intercept2)
	
	if (plot) {
 		lines(c(1, xMax), c(exp(intercept2),  yMax), col="grey")
	}
	intersection<-Geometry.calculateIntersection(slope1, intercept1, slope2, intercept2)

	if (sum(is.na(intersection))==0) {
		intersectionAge<-exp(intersection$x)
		intersectionMSD<-exp(intersection$y)
		if (plot) {
			points(intersectionAge, intersectionMSD, col="red", cex=1.5)
			lines(x=c(intersectionAge, intersectionAge), y=c(0.1, intersectionMSD), col="red")
			lines(x=c(0.1, intersectionAge), y=c(intersectionMSD, intersectionMSD), col="red")		
		}
	} else {
		intersectionAge<-NA
		intersectionMSD<-NA
	}
		
	
	results <- list("slope1"=slope1, "slope2"=slope2, "intercept1"=intercept1, "intercept2"=intercept2,
			"intersectionAge"=intersectionAge, "intersectionMSD"=intersectionMSD,
			"lower.lm"=lower.lm, "upper.lm"=upper.lm)

	return (results)
}



