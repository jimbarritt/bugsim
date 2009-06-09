library("CircStats")
lib.loadLibrary("/thesis/common/probability/Probability Library.r")
cat("Von Mises Distribution Library (v1.0)\n")

#From Bovet and Benhamou 1988 - they use linear sd, but maybe it doesnt matter... has to be in radians!
# the result is rad.u^(-1/2) where u is the units of  stepLength  
circular.sinuosity<-function(sd.rad, stepLength) {
	return (sd.rad/sqrt(stepLength))
}

#From Bovet and Benhamou 1988 - this is the mean vector length
circular.rhoFromLinearSD<-function(sd) {
	return (exp(-((sd^2)/2)))
}

#Follows that you should be able to go the other way...
circular.gaussianSDFromRho<-function(rho) {
	return (sqrt(-2*log(rho)))
}

circular.kfromLinearSD<-function(sd.rad) {
	rho<-circular.rhoFromLinearSD(sd.rad)
	x<-2
	xK<-circular.kappaTable.df$k[1]
	yRho<-circular.kappaTable.df$rho[1]
	
	while (rho>yRho || x>(length(circular.kappaTable.df$k)-1)) {
		xK<-circular.kappaTable.df$k[x]
		yRho<-circular.kappaTable.df$rho[x]
		x<-x+1
	}
	return (xK)
}

circular.rhoFromK<-function(k) {
	x<-2
	xK<-circular.kappaTable.df$k[1]
	yRho<-circular.kappaTable.df$rho[1]
	
	while (k>xK || x>(length(circular.kappaTable.df$k)-1)) {
		xK<-circular.kappaTable.df$k[x]
		yRho<-circular.kappaTable.df$rho[x]
		x<-x+1
	}
	return (yRho)
}

circular.KFromRho<-function(rho) {
	x<-2
	xK<-circular.kappaTable.df$k[1]
	yRho<-circular.kappaTable.df$rho[1]
	
	while (rho>yRho || x>(length(circular.kappaTable.df$k)-1)) {
		xK<-circular.kappaTable.df$k[x]
		yRho<-circular.kappaTable.df$rho[x]
		x<-x+1
	}
	return (xK)
}

circular.sinuosityFromKAndL<-function(k, L) {
	rho<-circular.rhoFromK(k)
	lin.SD<-circular.gaussianSDFromRho(rho)
	return (circular.sinuosity(lin.SD, L))
}


circular.plotAngularDeviationAsK<-function() {
	x<-2:(length(circular.kappaTable.df$k)-1)
	xK<-circular.kappaTable.df$k[x]
	yS<-circular.kappaTable.df$s[x]

	par(mar=c(7, 7, 5, 5))
	plot(yS~xK, type="p", pch=NA, log="x" ,axes=F, ann=F, ylim=c(0, 80))
	lines(yS~xK,lty=1, lwd=2, col="blue")
	axis.at<-c(0.1, 1,10, 100, 500)
	axis(1, at=axis.at, cex.axis=1.5)
	axis(1, at=1:10* rep(axis.at[-1] / 10, each = 10), tcl = -0.5, labels = FALSE)
	axis(2, at=c(0, 20, 40, 60, 80), cex.axis=1.5)
	box()
	title(main="", ylab=expression(sigma), xlab="log(k)", cex.main=1.5,cex.lab=1.5, line=4)	
}

circular.plotLinearDeviationAsK<-function() {
	x<-2:(length(circular.kappaTable.df$k)-1)
	xK<-circular.kappaTable.df$k[x]
	xRho<-circular.kappaTable.df$rho[x]
	ySD<-toDegrees(sapply(xRho, circular.gaussianSDFromRho))
	par(mar=c(7, 7, 5, 5))
	plot(ySD~xK, type="p", pch=NA, log="x" ,axes=F, ann=F, ylim=c(0, 140))
	lines(ySD~xK,lty=1, lwd=2, col="blue")
	axis.at<-c(0.1, 1,10, 100, 500)
	axis(1, at=axis.at, cex.axis=1.5)
	axis(1, at=1:10* rep(axis.at[-1] / 10, each = 10), tcl = -0.5, labels = FALSE)
	
	axis(2,cex.axis=1.5)
	
	box()
	title(main="", ylab=expression(sigma), xlab=expression("k"), cex.main=1.5,cex.lab=2, line=4)	
}


circular.plotSinuosityWithK<-function() {
	x<-2:(length(circular.kappaTable.df$k)-1)
	xK<-circular.kappaTable.df$k[x]
	xRho<-circular.kappaTable.df$rho[x]
	ySD<-sapply(xRho, circular.gaussianSDFromRho)

	L<-1
	yS.1<-sapply(ySD, circular.sinuosity, L)
	L<-2
	yS.2<-sapply(ySD, circular.sinuosity, L)
	
	L<-5
	yS.3<-sapply(ySD, circular.sinuosity, L)
	L<-10
	yS.4<-sapply(ySD, circular.sinuosity, L)
	L<-20
	yS.5<-sapply(ySD, circular.sinuosity, L)
	L<-100
	yS.6<-sapply(ySD, circular.sinuosity, L)
	L<-250
	yS.7<-sapply(ySD, circular.sinuosity, L)


	par(mar=c(7, 7, 5, 5))
	plot(yS.1~xK, type="p", pch=NA, log="x" ,axes=F, ann=F)
	lines(yS.1~xK,lty=1, lwd=2,col="blue")
	lines(yS.2~xK,lty=1, lwd=2,col="blue")
	lines(yS.3~xK,lty=1, lwd=2,col="blue")
	lines(yS.4~xK,lty=1, lwd=2, col="blue")
	lines(yS.5~xK,lty=1, lwd=2, col="blue")
	lines(yS.6~xK,lty=1, lwd=2, col="blue")
	lines(yS.7~xK,lty=1, lwd=2, col="blue")


	axis.at<-c(0.1, 1,10, 100, 500)
	axis(1, at=axis.at, cex.axis=1.5)
	axis(1, at=1:10* rep(axis.at[-1] / 10, each = 10), tcl = -0.5, labels = FALSE)

	axis(2,cex.axis=1.5)
	axis(2, at=seq(from=0, to=2.5, by=0.1), tcl = -0.5, labels = FALSE)

	box()
	title(main="", ylab=expression("S*"), xlab=expression("k"), cex.main=1.5,cex.lab=2, line=4)	
	text(.15, 2.5  , "L=1", cex=1.2)
	text(.15, 1.75  , "L=2", cex=1.2)
	text(.15, 1.15 , "L=5", cex=1.2)
	text(.15, 0.82 , "L=10", cex=1.2)
	text(.15, 0.6 , "L=20", cex=1.2)
	text(.155, 0.3 , "L=100", cex=1.2)
	text(.155, 0.09 , "L=250", cex=1.2)

}
circular.readBesselFunctionTable<-function() {
	filename<-sprintf("%s/%s", ExperimentData.getPublishedRootDir(), "/appendix/simulation/mathematical-tables/bessel-function.csv")
	cat("Reading Bessel Function Table From '", filename, "'\n")
	
	besselFunction.df<-read.csv(file=filename)
	return (besselFunction.df)
}


circular.readKappaToSDConversionTable<-function() {
	filename<-sprintf("%s/%s", ExperimentData.getPublishedRootDir(), "/appendix/simulation/mathematical-tables/kappa-to-sd-conversion-table.csv")
	cat("Reading Kappa To SD Table From '", filename, "'\n")
	
	besselFunction.df<-read.csv(file=filename)
	return (besselFunction.df)
}

circular.kappaTable.df<-circular.readKappaToSDConversionTable()


circular.readSDToKappaConversionTable<-function() {
	filename<-sprintf("%s/%s", ExperimentData.getPublishedRootDir(), "/appendix/simulation/mathematical-tables/sd-to-kappa-conversion-table.csv")
	cat("Reading SD To Kappa Table From '", filename, "'\n")
	
	besselFunction.df<-read.csv(file=filename)
	return (besselFunction.df)
}

circular.writeBesselFunctionTable<-function() {
	#Test IBessel function
	#FROM BATSCHSLETT - TABLE OF I0 Bessel Values (Appendix Table F - pp 332)
	#as batschlett only gives accuracy to 5 decimals we compare with delta 1-e05
	z<-c(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0)
	expected.besselI0<-c(1.00000, 1.00250, 1.01003, 1.02263, 1.04040, 1.06348, 1.09204, 1.12630, 1.16652, 1.21299, 1.26607, 1.64672, 2.27959, 3.28984, 4.88079, 7.37820, 11.30192, 17.48117, 27.23987)
	actual.circStatsBesselI0<-besselI(x = z, nu = 0, expon.scaled = FALSE)
	actual.bugsimStatsBesselI0<-circular.besselI0(z)
	delta<-1e-05

	#Write out the bessel values to a file
	besselFunctionI0.df<-data.frame(z)
	besselFunctionI0.df$kappa<-besselFunctionI0.df$z
	besselFunctionI0.df$besselI0<-expected.besselI0
	besselFunctionI0.df$z<-NULL
	str(besselFunctionI0.df)

	filename<-sprintf("%s/%s", ExperimentData.getPublishedRootDir(), "/appendix/simulation/mathematical-tables/bessel-function.csv")
	cat("Writing Bessel Function to '", filename, "'\n")
	write.csv(file=filename, besselFunctionI0.df, row.names=FALSE)	
}

circular.generateRandomPath<-function(steps,stepLength, N, k, mu, startX, startY) {
	path<-list()
	xr<-runif(n=steps)
	yr<-runif(n=steps)	
	
	j<-1:N
	pdist<-circular.createCumulativeDensityFunction(N, k, mu)

	coordCurrent<-c(startX, startY)
	path<-appendToList(path, coordCurrent)
	azimuths<-c()
	anglesOfTurn<-c()
	currentDirection<-coord.uniformRandomAzimuth()
	angleOfTurn<-0

	for (iStep in 1:steps) {
		coordCurrent<-coord.moveTo(coordCurrent, currentDirection, stepLength)
		path<-appendToList(path, coordCurrent)				
		anglesOfTurn<-c(anglesOfTurn, angleOfTurn)
		azimuths<-c(azimuths, currentDirection)
		
		randomAzimuth<-circular.chooseRandomDirection(j, pdist, xr[iStep], yr[iStep])
		previousDirection<-currentDirection
		currentDirection<-coord.restrictAngle360(currentDirection + randomAzimuth)
		
		if (randomAzimuth < 180) {
            angleOfTurn = randomAzimuth;
        } else if (randomAzimuth >= 180) {
            angleOfTurn = randomAzimuth - 360;
        }
	 
	}
	
	return (list("path"=path, "azimuths"=azimuths,"anglesOfTurn"=anglesOfTurn))
}
#steps=200;k=10;mu=0
circular.generateCircStatsRandomPath<-function(steps,stepLength, k, mu, startX, startY) {
	path<-list()

		
	coordCurrent<-c(startX, startY)
	path<-appendToList(path, coordCurrent)
	azimuths<-rvm(steps,mu, k)
	currentDirection<-coord.uniformRandomAzimuth()
	angleOfTurn<-0

	for (iStep in 1:steps) {
		coordCurrent<-coord.moveTo(coordCurrent, currentDirection, stepLength)
		path<-appendToList(path, coordCurrent)				
		
		randomAzimuth<-azimuths[iStep]
		previousDirection<-currentDirection
		currentDirection<-coord.restrictAngle360(currentDirection + toDegrees(randomAzimuth))
		
		if (randomAzimuth < 180) {
            angleOfTurn = randomAzimuth;
        } else if (randomAzimuth >= 180) {
            angleOfTurn = randomAzimuth - 360;
        }
	 
	}
	
	return (path)
}


#Generate random variable from von mises distribution from cain(1985)
#N - number of sgements
#k - width parameter for vmises dist
#mu - mean direction
circular.createCumulativeDensityFunction<-function(N, k, mu) {	
	arcLength<-(2*pi/N)
	pdistX<-c()
	for (j in 1:N) {
		xj<-arcLength*(j-.5)
		pdistX<-c(pdistX, xj)
	}

	pdist<-circular.vm.densityFXRadians(pdistX, k, mu)
	pdistNorm<-prob.createDistribution(pdist)

	pdistCum<-prob.createCumulativeDistribution(pdistNorm)
	return (pdistCum)
}




circular.chooseRandomDirection<-function(x, pdistCum, xr, yr, N=360) {
	jr<-prob.chooseRandomFromDistX(x, pdistCum, xr)
	randomThetaRadians<-((2*pi)/N)*(jr-1+yr) # Chooses it somehwere at uniform random in the interval
	randomTheta<-toDegrees(randomThetaRadians)
	return (randomTheta)
}

circular.vm.plotDensityFunctionDegrees<-function(k, maxY=1) {
	x<-seq(from=-180, to=180, by=1)
	vmisesd<-circular.vm.densityFXDegrees(x, k=k, m=0)

	plot(vmisesd~x, type="l", ylim=c(0,maxY))
}

#k is width, mu is mean angle
circular.vm.densityFXDegrees<-function(x, kappa, mu) {
	besselK<-circular.besselI0X(kappa)
	exprA<-1/(2*pi*(besselK))
	
	y<-c()
	for (i in 1:length(x)) {
		yi<-exprA*exp(kappa*cos(toRadians(x[i])-toRadians(mu)))
		y<-c(y, yi)
	}
	return (y)
}

circular.vm.densityFXRadians<-function(x, k, mu) {
	besselK<-circular.besselI0X(k)
	exprA<-1/(2*pi*(besselK))
	
	y<-c()
	for (i in 1:length(x)) {
		yi<-exprA*exp(k*cos(x[i]-mu))
		y<-c(y, yi)
	}
	return (y)
}

circular.vm.plotDensityFunctionRadians<-function(k, graphId,...) {
	x<-seq(from=-pi, to=pi, by=pi/90)
	y<-sapply(x, FUN=dvm, 0, k)
	par(mar=c(3, 3, 2, 2))
	plot(y~x, type="l", lwd=2, col="blue", ylim=c(0, 1.3), ann=F, axes=F)
	
	x.axis.at<-c(-pi, 0, pi)
	axis(1, at=x.axis.at, labels=c("-180","0","+180"),cex.axis=1.5)
	labels<-c(expression(-pi),expression(0), expression(+pi) )
	box()
#	mtext(side=3, at=-pi,sprintf("%s", graphId), cex=1.5, line=1)
	text(-pi+1.3, 1.2,  sprintf("k=%0.2f", k), cex=1.5)
}




circular.besselI0<-function(x) {
	y<-c()
	for (i in 1:length(x)) {
		y<-c(y, circular.besselI0X(x[i]))
	}
	return (y)
}
#All this is from Batschlett Circular Distributions except the method of rectangular integration 
#which is from wikkepedia - need to find a more definite reference.
circular.besselI0X<-function(x) {
	arcs<-(1/(2*pi)) #represent the radians in a cricle as a proportion.	
	integral<-circular.rectangularBesselI0Integration(x)
	return (arcs*integral)
}

circular.besselI0Integral<-function(x, thetaRadians) {
	intg<-exp(x*cos(thetaRadians))
}



circular.rectangularBesselI0Integration<-function(x) {
	areaSum<-0
	n<-100
	a<-0
	b<-2*pi
	deltaX<-(b-a)/n
	for (i in 1:n) {	
		areaAtI<-circular.besselI0Integral(x, a+(i-.5)*deltaX)*deltaX#midpoint
		areaSum<-areaSum+areaAtI
	}
	return (areaSum)
}

