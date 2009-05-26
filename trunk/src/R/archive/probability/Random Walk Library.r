source("~/Work/code/bugsim/src/R/archive/probability/Probability Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/Geometry Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/probability/Fractal Resource Distribution Library.r", echo=FALSE)
cat("Random Walk Library (v1.0)\n")
library(CircStats)


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



rwalk.uniformRandomWalk<-function(n,  randAzimuth,stepLength, startX, startY) {	
	path<-list()
	currentLocation<-c(startX, startY)
	path<-appendToList(path, currentLocation)
	for (i in 1:n) {
		step<-stepLength
		azimuth<-coord.uniformRandomAzimuthX(randAzimuth[i])
		currentLocation<-coord.moveTo(currentLocation, azimuth, step)
		path<-appendToList(path, currentLocation)			
	}
	
	return (path)
}

rwalk.plotPath<-function(path, scale, title) {
	x<-coord.extractX(path)
	y<-coord.extractY(path)

	xlim<-c(min(x), max(x))
	ylim<-c(min(y), max(y))

	area<-max(x)-min(x)*max(y)-min(y)

	title<-sprintf("%s, Scale=%d, Area=%0.2f", title, scale, area)



	xlim<-c(-scale, scale)
	ylim<-c(-scale, scale)
	plot(y~x, type="l", xlim=xlim, ylim=ylim, main=title)
	col<-"blue"
	lines(c(min(x), min(x)), c(min(y), max(y)), col=col)
	lines(c(max(x), max(x)), c(min(y), max(y)), col=col)
	lines(c(min(x), max(x)), c(min(y), min(y)), col=col)
	lines(c(min(x), max(x)), c(max(y), max(y)), col=col)
}

