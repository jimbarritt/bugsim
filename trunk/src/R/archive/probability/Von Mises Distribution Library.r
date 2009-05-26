source("~/Work/code/bugsim/src/R/archive/probability/Probability Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/Geometry Library.r", echo=FALSE)
cat("Von Mises Distribution Library (v1.0)\n")


vmises.generateRandomPath<-function(steps,stepLength, N, k, mu, startX, startY) {
	path<-list()
	xr<-runif(n=steps)
	yr<-runif(n=steps)	
	
	j<-1:N
	pdist<-vmises.createCumulativeDensityFunction(N, k, mu)

	coordCurrent<-c(startX, startY)
	path<-appendToList(path, coordCurrent)
	azimuths<-c()
	currentDirection<-coord.uniformRandomAzimuth()
	for (iStep in 1:steps) {
		azimuth<-vmises.chooseRandomDirection(j, pdist, xr[iStep], yr[iStep])
		currentDirection<-coord.restrictAngle360(currentDirection + (azimuth))
		coordCurrent<-coord.moveTo(coordCurrent, currentDirection, stepLength)
		path<-appendToList(path, coordCurrent)				
		azimuths<-c(azimuths, azimuth)
	}
	
	return (list(path, azimuths))
}


#Generate random variable from von mises distribution from cain(1985)
#N - number of sgements
#k - width parameter for vmises dist
#mu - mean direction
vmises.createCumulativeDensityFunction<-function(N, k, mu) {	
	arcLength<-(2*pi/N)
	pdistX<-c()
	for (j in 1:N) {
		xj<-arcLength*(j-.5)
		pdistX<-c(pdistX, xj)
	}

	pdist<-vmises.densityFXRadians(pdistX, k, mu)
	pdistNorm<-prob.createDistribution(pdist)

	pdistCum<-prob.createCumulativeDistribution(pdistNorm)
	return (pdistCum)
}




vmises.chooseRandomDirection<-function(x, pdistCum, xr, yr) {
	jr<-prob.chooseRandomFromDistX(x, pdistCum, xr)
	randomThetaRadians<-((2*pi)/N)*(jr-1+yr) # Chooses it somehwere at uniform random in the interval
	randomTheta<-toDegrees(randomThetaRadians)
	return (randomTheta)
}

vmises.plotDensityFunction<-function(k, maxY=1) {
	x<-seq(from=-180, to=180, by=1)
	vmisesd<-vmises.densityFXDegrees(x, k=k, m=0)

	plot(vmisesd~x, type="l", ylim=c(0,maxY))
}

#k is width, mu is mean angle
vmises.densityFXDegrees<-function(x, kappa, mu) {
	besselK<-vmises.besselI0X(kappa)
	exprA<-1/(2*pi*(besselK))
	
	y<-c()
	for (i in 1:length(x)) {
		yi<-exprA*exp(kappa*cos(toRadians(x[i])-toRadians(mu)))
		y<-c(y, yi)
	}
	return (y)
}

vmises.densityFXRadians<-function(x, k, mu) {
	besselK<-vmises.besselI0X(k)
	exprA<-1/(2*pi*(besselK))
	
	y<-c()
	for (i in 1:length(x)) {
		yi<-exprA*exp(k*cos(x[i]-mu))
		y<-c(y, yi)
	}
	return (y)
}



vmises.besselI0<-function(x) {
	y<-c()
	for (i in 1:length(x)) {
		y<-c(y, vmises.besselI0X(x[i]))
	}
	return (y)
}
#All this is from Batschlett Circular Distributions except the method of rectangular integration 
#which is from wikkepedia - need to find a more definite reference.
vmises.besselI0X<-function(x) {
	arcs<-(1/(2*pi)) #represent the radians in a cricle as a proportion.	
	integral<-vmises.rectangularBesselI0Integration(x)
	return (arcs*integral)
}

vmises.besselI0Integral<-function(x, thetaRadians) {
	intg<-exp(x*cos(thetaRadians))
}



vmises.rectangularBesselI0Integration<-function(x) {
	areaSum<-0
	n<-100
	a<-0
	b<-2*pi
	deltaX<-(b-a)/n
	for (i in 1:n) {	
		areaAtI<-vmises.besselI0Integral(x, a+(i-.5)*deltaX)*deltaX#midpoint
		areaSum<-areaSum+areaAtI
	}
	return (areaSum)
}

