cat("Random Walk Library (v1.0)\n")
library(CircStats)


rwalk.plotComparisonWalks<-function(steps) {
	path.uniform<-rwalk.uniformRandomWalk(n=steps, stepLength=1,startX=0, startY=0)
	path.vm<-rwalk.vonMisesRandomWalk(n=steps, stepLength=2, k=10, mu=0,startX=0, startY=0)
	path.levy<-rwalk.levyRandomWalk(n=steps, minStepLength=2, maxStepLength=1000,fractalDimension=1, startX=0, startY=0)


	layout.mx<-rbind(c(1, 2, 3, 4),
					 c(5, 6, 6, 6))
	layout(layout.mx, widths=c(5, 30, 30, 30), heights=c(90, 10))
	par(mar=c(0, 0, 0, 0))
	plot.new()
	mtext(side=2, line=-4, expression("Y"), cex=2)

	scale<-90
	pointPattern<-F

	rwalk.plotPath(path.uniform, scale, expression("a) Uniform"), points=pointPattern, axesLab=F)
	rwalk.plotPath(path.vm, scale, expression("b) Von Mises (k=10)"), points=pointPattern, axesLab=F)
	rwalk.plotPath(path.levy, scale, expression("c) Levy Flight (D=1)"), points=pointPattern, axesLab=F)

	par(mar=c(0, 0, 0, 0))
	plot.new()
	plot.new()
	mtext(side=1, line=-2, expression("X"), cex=2)
}






rwalk.createPath<-function(n,  azimuths ,stepLength, startX, startY) {	
	path<-list()
	currentLocation<-c(startX, startY)
	path<-appendToList(path, currentLocation)
	for (i in 1:n) {
		step<-stepLength
		azimuth<-azimuths[i]
		currentLocation<-coord.moveTo(currentLocation, azimuth, step)
		path<-appendToList(path, currentLocation)			
	}
	
	return (path)
}



rwalk.uniformRandomWalk<-function(n,stepLength, startX=0, startY=0) {	
	randU<-runif(n)
	path<-list()
	currentLocation<-c(startX, startY)
	path<-appendToList(path, currentLocation)
	for (i in 1:n) {
		step<-stepLength
		azimuth<-coord.uniformRandomAzimuthX(randU[i])
		currentLocation<-coord.moveTo(currentLocation, azimuth, step)
		path<-appendToList(path, currentLocation)			
	}
	
	return (path)
}
#n<-20;str(result)
rwalk.vonMisesRandomWalk<-function(n, stepLength, k, mu, startX=0, startY=0) {
	result<-circular.generateCircStatsRandomPath(n,stepLength, k, mu, startX, startY) 
	return (result)
}

rwalk.levyRandomWalk<-function(n, minStepLength, maxStepLength,fractalDimension, startX=0, startY=0) {
	stepBy<-1
	randAzimuth<-runif(n)
	randStep<-runif(n)
	path.levy<-frez.ogata.levyWalk(n,  randAzimuth=randAzimuth, randStep=randStep,
		startX=0, startY=0, minStep=minStepLength, maxStep=maxStepLength 
		,fractalDimension=fractalDimension)
	
}

#scale
rwalk.plotPath<-function(path, scale, title=NULL, points=F, axesLab=T) {
	x<-coord.extractX(path)
	y<-coord.extractY(path)

	pathlimX<-c(min(x), max(x))
	pathlimY<-c(min(y), max(y))

	area<-max(x)-min(x)*max(y)-min(y)

	
	widthPath<-xlim[2]-xlim[1]
	heightPath<-xlim[2]-xlim[1]
	
	if (scale*2 < widthPath) {
		warning("Scale is too small to fit path - path is ", widthPath ," wide\n")
		indentX<-0
	} else {
		indentX<-(scale-widthPath)/2
	}
	if (scale*2 < heightPath) {
		warning("Scale is too small to fit path - path is ", heightPath ," high\n")		
		indentY<-0
	} else {
		indentY<-(scale-heightPath)/2
	}
		
	xlim<-c(pathlimX[1]-indentX, pathlimX[2]+indentX)
	ylim<-c(pathlimY[1]-indentY, pathlimY[2]+indentY)
	
	if (is.null(title)) {
		top<-2
		right<2
	} else {
		top<-4
		right<-4
	}
	
	if (axesLab) {
		bottom<-6
		left<-6
	} else {
		bottom<-2
		left<-2
	}
	
	par(mar=c(bottom, left, top, right))		

	plot(y~x, pch=NA,xlim=xlim, ylim=ylim, main=title,axes=F,ann=F)
#	rect(pathlimX[1], pathlimY[1], pathlimX[2], pathlimY[2], border=rgb(0.6, 0.6, 0.6), lwd=2)
	
	title(title, cex.main=2)
	if (points) {
		points(y~x, pch=21, cex=0.8, col=rgb(0.3,0.3, 0.3))
	} else {
		lines(y~x, col=rgb(0.3,0.3, 0.3))
	}
	
	x.at<-SummaryStatistics.simpleYat(xlim)
	axis(1, at=x.at, c("0", scale, scale*2), cex.axis=2,mgp=c(3, 1.5, 0))
	y.at<-SummaryStatistics.simpleYat(ylim)
	axis(2, at=y.at, c("0", scale, scale*2),cex.axis=2)

	if (axesLab) {
		mtext(side=1, line=4, "X", cex=2)
		mtext(side=2, line=4, "Y", cex=2)
	}
	box()
}

