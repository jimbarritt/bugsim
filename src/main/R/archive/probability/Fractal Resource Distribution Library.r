source("~/Work/code/bugsim/src/R/archive/probability/Probability Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/probability/Box Counting Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/Geometry Library.r", echo=FALSE)
cat("Fractal Resource Distribution Library (v1.0)\n")

frez.plotUpperVsLowerTailedDivision<-function() {
	x<-seq(from=0, to=4, by=0.01)
	y<-exp(-.7*x)
	par(mar=c(7, 4, 2, 2))
	plot(y~x, pch=NA, axes=FALSE, xlim=c(0, 4),ylim=c(0, 1), ann=FALSE)
	lines(y~x, col=rgb(0.3, 0.3, 0.3), lwd=2)
	box()
#	lines(c(0, 4), c(0, 0))
	ya<-exp(-.7*2.5)+.1
	lines(c(2.5, 2.5), c(-0.03, ya), lty=2, lwd=2)
#	lines(c(0, 0), c(0, 1))
	axis(1, at=(2.5),"")
	mtext(side=1, at=2.5, line=1.3, expression(r[i]), cex=2)
	mtext(side=2, line=1.2,expression("f(r)"),cex=2)
	text(2, .05, expression(P(R<=r[i])), cex=1.8)
	text(3, .05, expression(P(R>r[i])), cex=1.8)
	mtext(side=1, line=4, expression("Step Length (r)"), cex=2)
}

frez.plotUpperVsLowerTailed<-function(D, r0) {

	r<-seq(from=0, to=50, by=.01)
	u1<-frez.powerdist(r, r0, D)
	u2<-1-frez.powerdist(r, r0, D)

	par(mar=c(7, 7, 2, 2))
	plot(u1~r, pch=NA, main=sprintf("D=%0.0f", D), axes=FALSE, ylab="u", ylim=c(0, 1), cex.lab=1.5, ann=FALSE)
	axis(1, c(0, r0, 25, 50), c(0, "", 25, 50), cex.axis=1.5)
	mtext(side=1, line=1.2, at=r0, expression(r[0]), cex=1.5)
	axis(2, c(0, 0.5, 1), c(0, 0.5 ,  1), cex.axis=1.5)
	col.1<-rgb(0.3, 0.3, 0.3)
	col.2<-rgb(0.6, 0.6, 0.6)
	lines(u2~r, col=col.2, lwd=2)
	
	lines(u1~r, col=col.1, lwd=2)

	mtext(expression("Step Length (r)"), 1, 4, cex=2)
	mtext(expression("Cumulative Probability"), 2, 4, cex=2)
	legendA<-expression(P(R>r))
	legendB<-expression(P(R<=r))
	

	lines(c(r0, r0), c(-.1, 1), col="grey", lty=2)
	
	legend("right", c(legendA, legendB, "D=2"), lty=c(1, 1, NA),lwd=3, col=c(col.1, col.2), y.intersp=1.5, inset=0.05, cex=1.5)
	box()
}

frez.powerdist<-function(x, xmin, D) {
	U<-c()
	for (i in 1:length(x)) {
		u<-frez.powerdistX(x[i], xmin, D)
		U<-c(U, u)
	}
	return (U)
}

frez.powerdistX<-function(x, xmin, D) {
	u<-1
	if (x > xmin) {
		u<-(xmin/x)^D
	}
	return (u)	
}


frez.generatePointPattern<-function(type="LEVY", steps, randAzimuth, randStep, D, scale, minStep, maxStep, stepBy) {

	if (type=="LEVY") {
		path<-levyWalk(n=steps,  randAzimuth=randAzimuth, randStep=randStep,startX=0, startY=0, minStep=minStep, maxStep=maxStep , stepBy=stepBy,fractalDimension=D)
	} else if (type=="BROWNIAN") {
		path<-brownianWalk(n=steps,  randAzimuth=randAzimuth, randStep=randStep,startX=0, startY=0, minStep=1, maxStep=200 , stepBy=1, sd=1)		
	} else if (type=="OGATA") {
		path<-frez.ogata.levyWalk(n=steps,  randAzimuth=randAzimuth, randStep=randStep,startX=0, startY=0, minStep=minStep, maxStep=maxStep , stepBy=stepBy,fractalDimension=D)
	}
	
	x<-coord.extractX(path)
	y<-coord.extractY(path)

	xlim<-c(min(x), max(x))
	ylim<-c(min(y), max(y))

	area<-max(x)-min(x)*max(y)-min(y)
	
	
	if (type=="LEVY") {
		title<-sprintf("Levy (D=%0.4f), Scale=%d, Area=%0.2f", D, scale, area)
	} else if (type=="BROWNIAN") {
		title<-sprintf("Brownian (D=2), Scale=%d, Area=%0.2f", D, scale, area)
	} else if (type=="OGATA") {
		title<-sprintf("Ogata Levy (D=%0.4f), Scale=%d, Area=%0.2f", D, scale, area)
	}

	xlim<-c(-scale, scale)
	ylim<-c(-scale, scale)
	plot(y~x, xlim=xlim, ylim=ylim, main=title)
	col<-"blue"
	lines(c(min(x), min(x)), c(min(y), max(y)), col=col)
	lines(c(max(x), max(x)), c(min(y), max(y)), col=col)
	lines(c(min(x), max(x)), c(min(y), min(y)), col=col)
	lines(c(min(x), max(x)), c(max(y), max(y)), col=col)
	#lines(y~x)
	
	return (path)
}

frez.levyWalk<-function(n, randAzimuth, randStep, startX, startY, minStep, maxStep, stepBy, fractalDimension) {
	x<-seq(from=minStep, to=maxStep, by=stepBy)
	#y<-pstepLength(x, minStep, fractalDimension)	
	y<-reynolds.pstepLengthList(x, minStep, fractalDimension)	
	y2<-prob.createDistribution(y)
	pDist<-prob.createCumulativeDistribution(y2)
	
	path<-list()
	currentLocation<-c(startX, startY)
	path<-appendToList(path, currentLocation)
	for (i in 1:n) {
		step<-prob.chooseRandomFromDistX(x, pDist, randStep[i])
		azimuth<-coord.uniformRandomAzimuthX(randAzimuth[i])
		currentLocation<-coord.moveTo(currentLocation, azimuth, step)
		path<-appendToList(path, currentLocation)	
	}
	
	return (path)
}


frez.ogata.chooseRandom<-function(u, minStep, D) {
	R<-minStep/((1-u)^(1/D))
	return (R)
}

frez.ogata.levyWalk<-function(n, randAzimuth, randStep, startX, startY, minStep, maxStep, stepBy, fractalDimension) {
	path<-list()
	currentLocation<-c(startX, startY)
	path<-appendToList(path, currentLocation)
	for (i in 1:n) {
		step<-frez.ogata.chooseRandom(randStep[i], minStep, fractalDimension)
		azimuth<-coord.uniformRandomAzimuthX(randAzimuth[i])
		currentLocation<-coord.moveTo(currentLocation, azimuth, step)
		path<-appendToList(path, currentLocation)	
	}
	
	return (path)
}

frez.brownianWalk<-function(n,  randAzimuth,randStep, startX, startY, minStep, maxStep, stepBy, sd) {
	x<-seq(from=minStep-maxStep, to=maxStep, by=stepBy)
	y<-dnorm(x, sd)	

	#y<-reynolds.pstepLengthList(x, minStep, fractalDimension)	
	#y2<-prob.createDistribution(y)
	pDist<-prob.createCumulativeDistribution(y)
	
	path<-list()
	currentLocation<-c(startX, startY)
	path<-appendToList(path, currentLocation)
	for (i in 1:n) {
#		step<-prob.uniformInRangeX(randStep[i], minStep, maxStep)
		step<-prob.chooseRandomFromDistX(x, pDist, randStep[i])
		azimuth<-coord.uniformRandomAzimuthX(randAzimuth[i])
		currentLocation<-coord.moveTo(currentLocation, azimuth, step)
		path<-appendToList(path, currentLocation)			
	}
	
	return (path)
}



frez.pstepLength<-function(x, minStepLength, fractalDimension) {
	p<-(minStepLength / x)^fractalDimension
	return (p)
}

#From Reynolds AM (2006)
frez.reynolds.pstepLengthList<-function(l, lstar, mu) {
	p<-c()
	for (i in 1:length(l)) {
		li<-l[i]
		pi<-reynolds.pstepLength(li, lstar, mu)
		p<-c(p, pi)
	}
	return (p)
}

#reynolds.pstepLength(3, 1, 1.1)
frez.reynolds.pstepLength<-function(l, lstar,mu) {
	p<-(mu-1)*reynolds.theta(l-lstar)*lstar^(mu-1)*l^(-mu)
	return (p)
}
#l.1<-1;lstar<-1;mu<-1.1
#mu-1

#2^(-1)

#reynolds.theta(l-lstar)
frez.reynolds.theta<-function(x) {
	ret=0
	if (x>0) {
		ret=1
	}
	return (ret)
}
