#source("~/Work/code/bugsim/resource/R/Experiment 2/Experiment 2a Library.r", echo=FALSE)
#source("~/Work/code/bugsim/resource/R/probability/Probability Library.r", echo=FALSE)

sense.plotRadiusOfAttractionDemo<-function() {
	par(mfrow=c(1,3))
	sense.plotDemoPatch(resourceRadius=2, title=expression("a) R=2"))
	sense.plotDemoPatch(resourceRadius=5, title=expression("b) R=5"))
	sense.plotDemoPatch(resourceRadius=15, title=expression("c) R=15"))
}

sense.plotDemoPatch<-function(resourceRadius, title) {
	patchSize<-100

	par(mar=c(2, 2, 5, 2))
	resources.df<-Resourcelayout.createLayout(patchSize, 4, 5, 5)
	radii<-rep(resourceRadius, length(resources.df$x))
	plot(resources.df$y~resources.df$x, pch=NA, axes=F, ann=F, xlim=c(0, 110), ylim=c(0, 110))
	box()
	symbols(resources.df$x, resources.df$y, circles=radii, bg="grey", fg=NA,inches=F, add=T)
	points(resources.df$x, resources.df$y, pch=16, col="black", cex=.5)		
	title(main=title, cex.main=3)
}


sense.plotCornerCentreEdgePatch<-function() {
	patchSize<-100

	resourceRadius<-10
	par(mar=c(2, 2, 2, 2))
	res.df<-Resourcelayout.createLayout(patchSize, 4, 5, 5)
	
	corner.df<-subset(res.df, (res.df$row==1 | res.df$row==4) 
							& (res.df$col==1 | res.df$col==4))
	centre.df<-subset(res.df, (res.df$row==2 | res.df$row==3) 
							& (res.df$col==2 | res.df$col==3))	
	edge.df<-subset(res.df, 	(res.df$row==2 | res.df$row==3) 
									& (res.df$col==1 | res.df$col==4)
							  | (res.df$row==1 | res.df$row==4) 
									& (res.df$col==2 | res.df$col==3))
	
	plot(resources.df$y~resources.df$x, pch=NA, axes=F, ann=F, xlim=c(0, 110), ylim=c(0, 120))
	box()

	col.corner=rgb(0.6, 0.6, 0.6)
	col.edge=rgb(0.9, 0.9, 0.9)
	col.centre=rgb(0.1, 0.1, 0.1)
	
	radii<-rep(resourceRadius, length(corner.df$x))	
	symbols(corner.df$x, corner.df$y, circles=radii, bg=col.corner, fg=NA,inches=F, add=T)
	
	radii<-rep(resourceRadius, length(edge.df$x))	
	symbols(edge.df$x, edge.df$y, circles=radii, bg=col.edge, fg=NA,inches=F, add=T)
	
	radii<-rep(resourceRadius, length(centre.df$x))	
	symbols(centre.df$x, centre.df$y, circles=radii, bg=col.centre, fg=NA,inches=F, add=T)
	
	points(res.df$x, res.df$y, pch=16, col="black", cex=.5)		
	points(centre.df$x, centre.df$y, pch=16, col="white", cex=.5)		
	
	legend("top", legend=c("Centre", "Corner", "Edge"),fill=c(col.centre, col.corner, col.edge), horiz=T, cex=2, inset=0.025)
}


sense.plotCalculatedLayoutParameters<-function() {
	title<-expression("Calculated Layout Parameters")
	ss<-"I"
	resourceRadius<-5
	diameter<-resourceRadius*2
	patchSize<-120

	s<-Resourcelayout.calculateSeparationFromPatchSize(patchSize, resourceRadius, 4)
	indent<-20
	rulerHeight<-6
	midRuler<-rulerHeight/2
	plotSize<-patchSize+(indent*2)
	topRight<-indent+patchSize
	mid<-indent+(patchSize/2)
	arrowLength<-0.15
	arrowCol<-"gray"

	par(mar=c(2, 2, 5, 2))
	resources.df<-Resourcelayout.createLayout(patchSize, 4, indent, indent)
	radii<-rep(resourceRadius, length(resources.df$x))

	plot(resources.df$y~resources.df$x, pch=NA, axes=F, ann=F, xlim=c(0, plotSize), ylim=c(0+5, plotSize+5))
	box()
	symbols(resources.df$x, resources.df$y, circles=radii, bg="grey", fg=NA,inches=F, add=T)
	points(resources.df$x, resources.df$y, pch=16, col="black", cex=.5)		
	title(main=title, cex.main=1.5)

	arrows(topRight+midRuler, indent , topRight+midRuler, topRight, code=3, length=arrowLength, col=arrowCol)
	arrows(indent, indent-midRuler , topRight, indent-midRuler, code=3, length=arrowLength, col=arrowCol)
	
	radj<-s+diameter+s+diameter

	arrows(indent, topRight+midRuler, indent+(s/2), topRight+midRuler, code=3, length=arrowLength, col=arrowCol)
	arrows(indent+(s/2)+radj, topRight+midRuler, indent+(s/2)+diameter+radj, topRight+midRuler, code=3, length=arrowLength, col=arrowCol)
	arrows(indent+(s/2)+diameter, topRight+midRuler, indent+(s/2)+diameter+s, topRight+midRuler, code=3, length=arrowLength, col=arrowCol)
	arrows(indent, topRight+(rulerHeight*2)+midRuler, topRight, topRight+(rulerHeight*2)+midRuler, code=3, length=arrowLength, col=arrowCol)

	lines(c(topRight, topRight+rulerHeight), c(indent, indent), lwd=2, col="black")
	lines(c(topRight, topRight+rulerHeight), c(topRight, topRight), lwd=2, col="black")

	lines(c(indent, indent), c(indent, indent-rulerHeight), lwd=2, col="black")
	lines(c(topRight, topRight), c(indent, indent-rulerHeight), lwd=2, col="black")


	text(topRight+rulerHeight, mid, "P", cex=2, pos=4, col="red")
	text(mid, indent-rulerHeight,  "P", cex=2, pos=1, col="red")

	lines(c(indent, indent), c(topRight, topRight+rulerHeight), lwd=2, col="black")
	lines(c(topRight, topRight+rulerHeight), c(topRight, topRight), lwd=2, col="black")



	lines(c(indent, indent), c(topRight, topRight+(rulerHeight*3)), lwd=2, col="black")
	lines(c(indent+(s/2), indent+(s/2)), c(topRight, topRight+rulerHeight), lwd=2, col="black")

	lines(c(radj+indent+(s/2), radj+indent+(s/2)), c(topRight, topRight+rulerHeight), lwd=2, col="black")
	lines(c(radj+indent+(s/2)+diameter, radj+indent+(s/2)+diameter), c(topRight, topRight+rulerHeight), lwd=2, col="black")


	lines(c(indent+(s/2)+diameter, indent+(s/2)+diameter), c(topRight, topRight+rulerHeight), lwd=2, col="black")
	lines(c(indent+(s/2)+diameter+s, indent+(s/2)+diameter+s), c(topRight, topRight+rulerHeight), lwd=2, col="black")


	text(indent+s/4, topRight+rulerHeight,  "I/2", cex=2, pos=3, col="red")
	text(radj+indent+(s/2)+resourceRadius, topRight+rulerHeight,  "2R", cex=2, pos=3, col="red")
	text(indent+diameter+s, topRight+rulerHeight,  "I", cex=2, pos=3, col="red")

	lines(c(topRight, topRight), c(topRight, topRight+(rulerHeight*3)), lwd=2, col="black")
	text(mid, topRight+(rulerHeight*3),  "4I + 8R", cex=2, pos=3, col="red")


	rect(indent, indent, topRight, topRight, lwd=2, border="blue")
}
sense.plot3olfactionSignals<-function() {
	par(mfrow=c(1, 3))

	layout.mx<-rbind(c(1, 2, 3, 4), 
					 c(5, 6, 6, 6))
	layout(layout.mx, heights=c(90, 10), widths=c(2, 33, 33, 33))
	par(mar=c(0, 0, 0, 0))
	plot.new()
	mtext(expression("S"), side=2, line=-4, cex=2)

	sense.plotOlfactionSignal(20,"a) SW=20")
	sense.plotOlfactionSignal(35, "b) SW=35")
	sense.plotOlfactionSignal(70, "c) SW=70")
	legend("topright", legend=c("Sum of Signal", "Signal"), inset=0.02, cex=2, lwd=c(5,3), ,lty=c(1, 1), col=c("darkgrey", "grey"))
	
	par(mar=c(0, 0, 0, 0))
	plot.new()#5
	plot.new()#6
	mtext("X", side=1, line=-4, cex=2)
}
sense.plotOlfactionSignal<-function(sd, title) {
	x<-0:400
	plant.1<-150
	plant.2<-250
	signal.1<-dnorm(x, mean=plant.1, sd=sd)
	signal.2<-dnorm(x, mean=plant.2, sd=sd)
	signal.surface<-signal.1+signal.2

	maxY<-0.025
	par(mar=c(5, 5, 5, 2))
	plot(signal.surface~x, xlim=c(0, 400), ylim=c(0, maxY),pch=NA, axes=F, ann=F)
	box()
	axis(1, at=c(0, 200, 400),cex.axis=3,mgp=c(3, 2.5, 0) )

	mtext(side=3, line=2, substitute(a, list(a=title)), cex=2)

	lines(c(plant.1, plant.1), c(0,max(signal.1)), col="darkgrey", lty=2,lwd=2)
	lines(c(plant.2, plant.2), c(0,max(signal.2)), col="darkgrey", lty=2, lwd=2)

	lines(signal.1~x, col="grey", lwd=2)
	lines(signal.2~x, col="grey", lwd=2)
	lines(signal.surface~x, col="darkgrey", lwd=4)
}

sense.plotControlOfDirection<-function(mean, sd, meanThetaMin, meanThetaMax, col.light="black") {
	x<-seq(from=-180, to=180,by=1)
	y<-dnorm(x, mean,sd)
	y2<-dnorm(x, mean+meanThetaMin,sd)
	y3<-dnorm(x, mean+meanThetaMax,sd)
	maxY<-max(y)
	topPos<-maxY+maxY*.05
	par(mar=c(7, 7, 5, 5))
	plot(y~x,axes=FALSE,pch=NA, ylim=c(0,maxY+ maxY*.1) , main="", yaxs="r", yaxp=c(0, maxY, 3),type="p", xaxt="n", col="blue", cex.axis=1.5,cex.lab=1.5, xlab=NA, ylab=expression(P(theta)))
	axis(2, cex.axis=1.5, cex.lab=2)
	axis(1, at=seq(from=-180, to=180, by=45), cex.axis=1.5, cex.lab=2)
	lines(y2~x, lty=4, col=col.light)
	lines(y3~x, lty=4, col=col.light)
	lines(y~x, lty=1, col="blue")
	lines(x=c(0, 0), y=c(-0.3,topPos), lty=2)
	mtext(side=1, line=3, expression(theta), cex=2)


	#Draw the arrows indicating possible changes to the mean....
	#lines(x=c(-90, 90), y=c(topPos, topPos))
	arrows(0,  topPos, meanThetaMin, topPos)
	arrows(0,  topPos, meanThetaMax, topPos)
	lines(x=c(meanThetaMin, meanThetaMin), y=c(topPos+maxY*0.025, topPos-maxY*0.025))
	lines(x=c(meanThetaMax, meanThetaMax), y=c(topPos+maxY*0.025, topPos-maxY*0.025))

	lines(x=c(meanThetaMin, meanThetaMin), y=c(-0.3, topPos-maxY*0.025), lty=3)
	lines(x=c(meanThetaMax, meanThetaMax), y=c(-0.3, topPos-maxY*0.025), lty=3)

	points(x=0,y=topPos, pch=21, cex=2.4, lwd=1.5)
	points(x=0,y=topPos, pch=19, col="red",cex=1.8)

	thetaMin<-expression(mu[theta*min])
	text(x=meanThetaMin, y=topPos,thetaMin, pos=2, cex = 2)
	#points(x=meanThetaMin-24, y=topPos+maxY*0.01, pch=2,cex=2)
	thetaMax<-expression(mu[theta*max])
	text(x=meanThetaMax, y=topPos,thetaMax, pos=4, cex=2)
	#text(x=meanThetaMax+7, y=topPos,thetaMax, pos=4, cex=2)
	#points(x=meanThetaMax+5, y=topPos+maxY*0.01, pch=2,cex=2)

	text(x=0, y=topPos+maxY*0.06, expression(mu[theta]), cex=2)


	#Draw the arrows indicating the s.d. of the distribution...
	i<-util.indexOfArrayValue(x, sd)
	sdPos<-y[i]

	rect(-5, sdPos , 5, sdPos+maxY*0.075, col="white", border="white")#Draw a white rectangle to blot out the dotted line
	arrows(0, sdPos, sd, sdPos)
	arrows(0, sdPos, -sd, sdPos)
	text(0, sdPos+ maxY*.01, expression(sigma[theta]), cex=2, pos=3)
}

#mean=0; k=10; meanThetaMin=-90; meanThetaMax=+90;col.light="black"
sense.plotControlOfDirectionVM<-function(mean, k, meanThetaMin, meanThetaMax, col.light="black") {
	x<-seq(from=-pi, to=pi,by=pi*0.01)
	y<-toDegrees(sapply(x, dvm, mean,k))
	y2<-toDegrees(sapply(x, dvm, mean+toRadians(meanThetaMin),k))
	y3<-toDegrees(sapply(x, dvm, mean-toRadians(meanThetaMin),k))
	maxY<-max(y)
	topPos<-maxY+maxY*.05
	par(mar=c(7, 7, 5, 5))
	xDegrees<-toDegrees(x)
	
	
	plot(y~xDegrees,axes=FALSE,pch=NA, ylim=c(0,maxY+ maxY*.1) , main="", yaxs="r", yaxp=c(0, maxY, 3),type="p", xaxt="n", col="blue", cex.axis=1.5,cex.lab=1.5, xlab=NA, ylab=expression(P(Delta*theta)))
#	axis(2, cex.axis=1.5, cex.lab=2)
	axis(1, at=seq(from=-180, to=180, by=90), cex.axis=1.5, cex.lab=2)
	lines(x=c(0, 0), y=c(-0.3,topPos), lty=2, col="grey", lwd=2)

	lines(x=c(meanThetaMin, meanThetaMin), y=c(-0.3, topPos-maxY*0.025), lty=2,col="grey",lwd=2)
	lines(x=c(meanThetaMax, meanThetaMax), y=c(-0.3, topPos-maxY*0.025), lty=2,col="grey",lwd=2)

	

	lines(y2~xDegrees, lty=4, col=col.light, lwd=1)
	lines(y3~xDegrees, lty=4, col=col.light, lwd=1)
	lines(y~xDegrees, lty=1, col="blue", lwd=2)

	
	mtext(side=1, line=3, expression(Delta*theta), cex=2)


	#Draw the arrows indicating possible changes to the mean....
	#lines(x=c(-90, 90), y=c(topPos, topPos))
	arrows(0,  topPos, meanThetaMin, topPos)
	arrows(0,  topPos, meanThetaMax, topPos)
	lines(x=c(meanThetaMin, meanThetaMin), y=c(topPos+maxY*0.025, topPos-maxY*0.025))
	lines(x=c(meanThetaMax, meanThetaMax), y=c(topPos+maxY*0.025, topPos-maxY*0.025))



	points(x=0,y=topPos, pch=21, cex=2.4, lwd=1.5)
	points(x=0,y=topPos, pch=19, col="red",cex=1.8)

	thetaMin<-expression(Delta*theta[min])
	text(x=meanThetaMin, y=topPos,thetaMin, pos=2, cex = 2)
	#points(x=meanThetaMin-24, y=topPos+maxY*0.01, pch=2,cex=2)
	thetaMax<-expression(Delta*theta[max])
	text(x=meanThetaMax, y=topPos,thetaMax, pos=4, cex=2)
	#text(x=meanThetaMax+7, y=topPos,thetaMax, pos=4, cex=2)
	#points(x=meanThetaMax+5, y=topPos+maxY*0.01, pch=2,cex=2)

	text(x=0, y=topPos+maxY*0.06, expression(theta), cex=2)
	box()

}

sense.plotBayesianVsModelAverage<-function(names, signalDistA, signalDistB) {
	probDistA<-prob.createDistribution(signalDistA)
	probDistB<-prob.createDistribution(signalDistB)
	bayesianProductDist<-prob.createDistribution(prob.bayesianProduct(probDistA, probDistB))
	modelAverageDist<-prob.createDistribution(prob.modelAverage(probDistA, probDistB))

	cexAxis<-1.3
	layout.mx<-matrix(c(1,2,3,4, 6, 5, 7, 8, 9), 3, 3, byrow = TRUE)
	layout(layout.mx, heights=c(30, 30, 30))

	par(mar=c(7, 7, 5, 5))
	maxS<-sense.calculateMax(signalDistA, signalDistB)	
	maxP<-sense.calculateMax(probDistA, probDistB)
		
	barplot(signalDistA, names=names,cex=cexAxis,ylim=c(0,maxS), xlab=expression(theta), ylab="S",cex.axis=cexAxis ,cex.lab=2, main="(a) Signal Input (A)")
	barplot(probDistA, names=names, cex=cexAxis,ylim=c(0,maxP), xlab="a", ylab="P(a)" ,cex.lab=2, cex.axis=cexAxis,main="(b) Probability (A)")
	barplot(probDistA, names=names, cex=cexAxis,ylim=c(0,maxP), xlab="a", ylab="P(a)" ,cex.lab=2,cex.axis=cexAxis, main="(c) Probability (A)")
	barplot(signalDistB, names=names, cex=cexAxis,ylim=c(0,maxS),xlab=expression(theta), ylab="S" ,cex.lab=2,cex.axis=cexAxis, main="(d) Signal Input (B)")
	barplot(probDistB, names=names, cex=cexAxis,ylim=c(0,maxP),xlab="a", ylab="P(a)" ,cex.lab=2,cex.axis=cexAxis, main="(f) Probability (B)")
	barplot(probDistB, names=names, cex=cexAxis,ylim=c(0,maxP), xlab="a", ylab="P(a)" ,cex.lab=2,cex.axis=cexAxis, main="(e) Probability (B)")
	plot.new()
	maxP<-sense.calculateMax(bayesianProductDist, modelAverageDist)
	barplot(modelAverageDist, names=names, cex=1.4,xlab="a", ylim=c(0,maxP), ylab="P(a)" , cex.axis=1.5, cex.lab=2, main="(g) Model Average")
	barplot(bayesianProductDist, names=names, cex=1.4,xlab="a", ylim=c(0,maxP),ylab="P(a)",cex.axis=1.5, cex.lab=2, main="(h) Bayesian Product")
}


sense.plotInputSignal<-function(azimuthCoords,gamma) {
	x<-seq(from=-180, to=180, by=1)
	y<-rep(0, length(x))

	for (coord in azimuthCoords) {
		theta<-coord[1] 
		d<-coord[2] 
		thetaRounded<-as.numeric(sprintf("%0.0f", theta))
		lambda<-sense.luminosityResponse(d, gamma)
		if (thetaRounded>180) {
			thetaRounded<-thetaRounded-360
		}
		
		i<-which(x==thetaRounded )
		y[i]<-y[i]+lambda
		cat("Setting y[", i, "] to be " ,lambda, "\n")	
	}

	ylabels<-c("0.0", "0.1", "0.2", "0.3")
	
	par(mar=c(6, 6, 2, 2))
	barplot(y,ann=F,axes=F, ylim=c(0, 0.3),cex.axis=1.5)
	axis(1, at=c(0,216.75, 432), labels=c("-180", "0", "+180"), cex.axis=1.5)
	axis(2, at=c(0,0.1, 0.2, 0.3), labels=ylabels,cex.axis=1.5)
	box()
	lines(x=c(216.75,216.75), y=c(-10, max(y)+ max(y)*.1), lty=3)
	mtext(expression(theta), 1, 4, cex=2)
	mtext(expression(S["in"]), 2, 4, cex=2)
	return (y)
}

sense.plotVisualPDistribution<-function(signals) {
	x<-seq(from=-180, to=180, by=1)
	y<-prob.createDistribution(signals)

	ylabels<-c("0.0", "0.25", "0.5")

	par(mar=c(6, 6, 2, 2))
	barplot(y,ann=F,axes=F, ylim=c(0, max(y)+ max(y)*.2),cex.axis=1.5)
	axis(1, at=c(0,216.75, 432), labels=c("-180", "0", "+180"), cex.axis=1.5)
	axis(2, at=c(0, 0.25, 0.5), labels=ylabels,cex.axis=1.5)
	box()
	lines(x=c(216.75,216.75), y=c(-10, max(y)+ max(y)*.1), lty=3)
	mtext(expression(a[theta]), 1, 4, cex=2)
	mtext(expression(P(a[theta])), 2, 4, cex=2)
	
}



sense.plotSignalDeltaVsDirectionDelta<-function(minMax) {
	x<-seq(from=-1, to=+1, by=.01)
	y<-minMax*(tan(x)/tan(1))# divide by tan(1) to get a proportion between 0 and 1.
	ylabel<-expression(theta)	
	par(mar=c(7, 7, 2, 2))
	plot(ann=F,axes=F,y~x, ylim=c(-90, 90),type="p", pch=NA, ylab=ylabel, cex.lab=2)
	box()
	axis(1, c(-1, 0, 1), labels=c("-1", "0", "+1"), cex.axis=1.5)
	axis(2, c(-90, 0, 90), labels=c("-90", "0","+90"), cex.axis=1.5)
	mtext(expression(Delta*S), side=1,line=4, cex=2)
	mtext(expression(paste(Delta, theta)), side=2,line=4, cex=2)
	lines(x=c(-1, 1), y=c(0, 0), lty=2)
	lines(x=c(0,0), y=c(-minMax, minMax), lty=2)
	lines(y~x, col="blue", lwd=2)
}

#Plots examples of kinds of signal inputs given 2 sensors.
sense.plotSignalDeltas<-function() {
	signalDistA<-c(0, 0, 0,  0,  30, 0, 0, 0, 20, 0, 0, 0, 0)
	signalDistB<-c(0, 0, 0,  0,  30, 0, 0, 0, 30, 0, 0, 0, 0)
	signalDistC<-c(0, 0, 0,  0,  15, 0, 0, 0, 5,  0, 0, 0, 0)
	signalDistD<-c(0, 0, 0,  0,  5,  0, 0, 0, 5,  0, 0, 0, 0)
	signalDistE<-c(0, 0, 0,  0,  30, 0, 0, 0, 5,  0, 0, 0, 0)

	
	layout.mx<-matrix(c(1,2,3,4, 5, 6), 3, 2, byrow = TRUE)
	layout(layout.mx, heights=c(30, 30, 30))

	axes<-T
	sense.plotSignalDelta(signalDistA, "a)", axes=axes)
	sense.plotSignalDelta(signalDistB, "b)", axes=axes)
	sense.plotSignalDelta(signalDistC, "c)", axes=axes)
	sense.plotSignalDelta(signalDistD, "d)", axes=axes)
	sense.plotSignalDelta(signalDistE, "e)", axes=axes)

}

#signalDist<-signalDistA;title="a"
sense.plotSignalDelta<-function(signalDist, title, axes=T) {
	cexAxis<-2
	maxS<-30
	
	if (axes) {	
		par(mar=c(8, 5, 6, 5))
	} else {
		par(mar=c(2, 2, 5, 2))
	}
		
	barplot(signalDist, ylim=c(0,maxS+5), ann=F, axes=F, axisnames=F, offset=2)
	box()
	
	
	if (axes) {
		mtext(side=1,line=2,at=0.5, "-180", cex=1.2)
		mtext(side=1,line=2,at=5.35, "-40", cex=1.2)
		mtext(side=1,line=2,at=7.9, "0", cex=1.2)	
		mtext(side=1,line=2,at=10.25, "+40", cex=1.2)
		mtext(side=1,line=2,at=15, "+180", cex=1.2)
		mtext(side=1, line=5, cex=1.5, expression(theta))
		mtext(side=2, line=2, cex=1.3, expression(S))
	}
	mtext(side=3, line=2,cex=1.5, title)
}

sense.calculateMax<-function(x1, x2) {
	max1<-max(x1)
	max2<-max(x2)
	
	if (is.nan(max1)) max1 = 0
	if (is.nan(max2)) max2 = 0
	return (max(max1, max2))	
}


outputGraphs<-function(outputDirectory, beta, title) {
	pdf(file=sprintf("%s/P of Actions beta=%d.pdf", outputDirectory, beta), width=15, height=5, bg="white")
	par(mfrow=c(1, 1))
	plotPA(beta, title)
	dev.off()
}

#betaV<-500
#title<-expression(beta==500)
plotPA<-function(betaV, title) {
	sumDensity<-0
	numerator<-rep(0, length(S.values.hist$density))
	for (i in 1:length(S.values.hist$density)) {
		sthetai<-S.values.hist$density[[i]]
		ecalc<-exp(betaV*sthetai)
		numerator[[i]]<-ecalc
		sumDensity<-sumDensity+ecalc 
	}

	pa<-rep(0, length(S.values.hist$density))
	for (i in 1:length(S.values.hist$density)) {
		pa[[i]]<-numerator[[i]]/sumDensity
	}



	xlabel<-expression(a[i])
	ylabel<-expression(p(a[i]))
	xnames<-rep("", length(pa))
	par(mar=c(5, 7, 2, 2))
	barplot(pa, main=title, xlab=xlabel, ylab=ylabel, cex.lab=1.5, cex.main=1.7, axes=FALSE, xlim=c(0, 19.5), ylim=c(0, 1))
	axis(side=1, at=c(0, 9.7, 19.5), labels=c("-180", "0", "180"), cex.axis=1.2)
	axis(side=2, at=c(0, 0.5, 1), labels=c("0", "0.5", 1))
}


outputBetaGraphs<-function(outputDirectory) {
	outputBetaGraph(0.05, 1000, expression(beta(gamma==0.05,beta[max]==1000)), outputDirectory)
	outputBetaGraph(0.1, 1000, expression(beta(gamma==0.1,beta[max]==1000)), outputDirectory)
	outputBetaGraph(0.25, 1000, expression(beta(gamma==0.25,beta[max]==1000)), outputDirectory)
	outputBetaGraph(0.5, 1000, expression(beta(gamma==0.5,beta[max]==1000)), outputDirectory)
	
}


outputBetaGraph<-function(gamma, betamax, title, outputDirectory) {	
	beta<-rep(0, 100)

	for (t in 2:100) {
		betaPrevious<-beta[[t-1]]
		deltaBeta<-gamma*(betamax-betaPrevious)
		beta[[t]]<-betaPrevious+deltaBeta
	}
	#quartz(width=10, height=10)
	filename<-sprintf("%s/Beta Graph gamma-%0.3f maxBeta-%0.0f.pdf", outputDirectory, gamma,betamax )
	cat("Writing Beta graph to :", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
	
	par(mar=c(5, 5, 5, 5))
	plot(beta, pch=NA, xlab="Time (t)", ylab=expression(beta), axes=FALSE, cex.lab=1.5, cex.main=1.7, cex.axis=1.2,main=title)
	lines(x=c(0, 100), y=c(betamax, betamax), col="grey")
	lines(beta, col="blue")
	axis(1, mgp=c(3,1 ,0))
	axis(2, at=c(0, betamax))
	dev.off()
}

sense.luminosityResponse<-function(d, gamma){
	y<-c()
	for (i in 1:length(d)) {
		alpha<-exp(-gamma*d[i])
		y<-c(y, alpha)
	}
	return (y)
}
sense.plotLuminosityResponseToDistance<-function(gamma, maxD) {
	d<-seq(from=0, to=maxD, by=.1)
	alpha<-sense.luminosityResponse(d, gamma)
	par(mar=c(7, 7, 2, 2))
	plot(alpha~d, type="l", ylim=c(0, 1), ann=F, axes=F, lwd=2, col="blue")
	axis(1, cex.axis=1.5)
	mtext("d", 1, 4, cex=2)
	axis(2, cex.axis=1.5)
	mtext(expression(lambda), 2, 4, cex=2)
	box()
}

#maxK<-10
#currentK<-2
sense.plotVisualSignalNoise<-function(currentK, maxK) {
	D<-seq(from=0, to=1, by=0.01)

	y<-sapply(D, sense.modifyKForSignalNoise, currentK, maxK)

	par(mar=c(7, 7, 2, 2))
	plot(y~D, pch=NA,ylim=c(0, maxK), ann=F, cex.axis=1.5, axes=F)
	box()
	axis(1, at=c(0, 0.25, 0.5, 0.75, 1),cex.axis=1.5)
	axis(2, at=c(0, 2, 4, 6, 8, 10),cex.axis=1.5)
	lines(c(0, 1), c(maxK, maxK), col="grey", lwd=2, lty=2)
	lines(c(0, 1), c(currentK, currentK), col="grey", lwd=2, lty=2)
	lines(y~D, col="blue", lwd=2)
	mtext(side=1, line=4, cex=2, expression(D))
	mtext(side=2, line=4, cex=2, expression(k))
}

sense.modifyKForSignalNoise<-function(D, currentK, maxK) {
	psi<-(maxK-currentK)/maxK
	signalNoise<-currentK+sense.visualSignalNoise(D, psi, maxK)
	
}

sense.visualSignalNoise<-function(D, psi, maxK) {
	noise<-(psi*maxK) - (psi*D*maxK)
	return (noise)
}


sense.plotPotentialResponseL80ForAvsB<-function() {
	x<-seq(from=0, to=800, by=10)
	a.0.5<-(exp(-x*0.008)+.4)/(1.4)
	a.10<-(1-((exp(-x*0.01)+6.2)/7.2))+.15
	a.8.8<-(1-((exp(-x*0.01)+(2.5))/3.5))

	par(mar=c(5, 6, 3, 2))
	plot(a.0.5~x, type="l", ylim=c(0, 1), col="blue", lwd=2, ann=F, axes=F)
	lines(a.10~x, col="blue", lwd=2)
	lines(a.8.8~x, col="blue", lwd=2)
	axis(1, cex.axis=1.5)
	axis(2, at=c(0,0.5, 1), cex.axis=1.5)
	
	text(50, 1, "k=0.5", cex=1.2)
	text(50, .27, "k=10", cex=1.2)
	text(70, .05, "k=8.8", cex=1.2)
	mtext(side=1, line=3, "B", cex=1.5)
	mtext(side=2, line=4, "CENTRE:CORNER (Eggs)", cex=1.5)
	title(main="a) Response vs B", cex.main=1.5)
	box()
	
}
sense.plotPotentialResponseOfSlopeToAForB<-function() {
	x<-c(0.5, 3, 10)
	y<-c(-1, +.8, +.2)

	par(mar=c(5, 6, 3, 2))
	plot(y~x, type="l", col="blue", lty=2, lwd=2, ylim=c(-1, 1), xlim=c(0, 10), axes=F, ann=F)
	axis(1, at=c(0.5, 3, 10), cex.axis=1.5)
	axis(2, at=c(-1, 0, 1), cex.axis=1.5)
	mtext(side=1, line=3, "k", cex=1.5)
	mtext(side=2, line=4, "Slope", cex=1.5)
	box()
	title(main="b) Slope of respsonse vs A", cex.main=1.5)
	points(y~x, pch=19, cex=2, col="blue")
}



