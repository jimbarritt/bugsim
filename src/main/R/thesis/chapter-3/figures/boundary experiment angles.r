#Some graphs of how to measure the boundary angles.
library(plotrix)
lib.loadLibrary("thesis/common/geometry/Geometry Library.r")
quartz(width=10, height=10)


numberOfPoints<-10000
randomStartPoints<-coord.generateRandomPointsInSquare(n=numberOfPoints, xlim=c(0, 100), ylim=c(0,100))
randomMoves<-coord.generateRandomMoves(randomStartPoints, stepLength=30)
randomEndPoints<-randomMoves$endPoints
randomAzimuths<-randomMoves$azimuths

boundaryCrossings<-getBoundaryCrossings(randomStartPoints, randomEndPoints, randomAzimuths, centre, radius)

hist(as.numeric(boundaryCrossings$azimuths))

B<-extractB(boundaryCrossings)
hist(B)
shapiro.test(B)

B1<-extractB1(boundaryCrossings)
hist(B1)
shapiro.test(B1)


drawLines(boundaryCrossings$startPoints, boundaryCrossings$endPoints)
drawCalculation()


extractB<-function(boundaryCrossings) {
	B<-c()
	for (intersection in boundaryCrossings$intersections) {
		B<-c(B, intersection$B)
	}
	return (B)
}

extractB1<-function(boundaryCrossings) {
	B1<-c()
	for (intersection in boundaryCrossings$intersections) {
		B1<-c(B1, intersection$B1)
	}
	return (B1)
}




drawCalculation<-function() {
	plot(y~x, pch=NA, xlim=c(0, 100), ylim=c(0,100))

	radius<-40
	centre<-c(50, 50)
	pStart<-c(5, 20)
	pEnd<-c(30, 60)
	
	draw.circle(centre[1], centre[2], radius, nv=1000, lty=1, lwd=4, col=NA, border="blue")
	arrows(x0=pStart[1], y0=pStart[2], x1=pEnd[1], y1=pEnd[2], code=2, col="grey", lwd=2)
	lines(x=c(pStart[1], 50), y=c(pStart[2], 50), col="grey", lwd = 1.5, lty=2)

	trigToCentre<-coord.calculateTrig(pStart, centre)
	trigToEnd<-coord.calculateTrig(pStart, pEnd)

	projectedEnd.A<-trigToCentre$A
	projectedEnd.theta<-trigToEnd$theta
	
	#tan(theta)=O/A
	#O=tan(theta)*A
	projectedEnd.O<-tan(toRadians(projectedEnd.theta)) * projectedEnd.A

	pProjected<-c(centre[1], pStart[2]+projectedEnd.O)
	
	trigToProjected<-coord.calculateTrig(pStart, pProjected)

	extension.H<-trigToProjected$H-trigToEnd$H
	
	#sin(theta)=O/H
	#O=sin(theta)*H
	extension.O=sin(toRadians(trigToProjected$theta))* extension.H
	#H^2=A^2 + O^2
	#A^2=H^2-O^2
	extension.A=sqrt(extension.H^2-extension.O^2)
	pExtension<-c(pProjected[1]-extension.A, pProjected[2]-extension.O)
	
	intersection<-calculateIntersectionAngles(pStart, pEnd, centre, radius)
	pIntersection<-intersection$pIntersection
			
	lines(x=c(pStart[1], centre[1]), y=c(pStart[2], pStart[2]), col="green")
	lines(x=c(centre[1], centre[1]), y=c(pStart[2], centre[2]), col="green")
	lines(x=c(pProjected[1], pProjected[1]), y=c(centre[2], pProjected[2]), col="red")
	lines(x=c(pExtension[1], pProjected[1]), y=c(pExtension[2], pProjected[2]), col="red")
	lines(x=c(pExtension[1], pProjected[1]), y=c(pExtension[2], pExtension[2]), col="red", lty=2)
	lines(x=c(pEnd[1], centre[1]), y=c(pEnd[2], centre[2]), col="green")
	
	lines(x=c(pIntersection[1], centre[1]), y=c(pIntersection[2], centre[2]), col="blue", lty=2)
	
	points(pStart[1], pStart[2], pch=19, col="red",cex=2)
	points(centre[1], centre[2], pch=19, col="blue",cex=2)
	points(pProjected[1], pProjected[2], pch=19, col="green", cex=2)
	points(pExtension[1], pExtension[2], pch=19, col="green", cex=2)
	points(pIntersection[1], pIntersection[2], pch=19, col="red", cex=2)
	text(x=pStart[1], y=pStart[2], "B", pos=2)
	text(x=centre[1], y=centre[2], "A", pos=4)
	text(x=pEnd[1], y=pEnd[2], "C", pos=3)
	text(x=pIntersection[1], y=pIntersection[2], "B1", pos=2)
	
}

#SOHCAHTOA
coord.calculateTrig<-function(pStart, pEnd) {
	A<-abs(pEnd[1]-pStart[1])
	O<-abs(pEnd[2]-pStart[2])
	H<-sqrt(A^2+O^2)
	theta<-toDegrees(atan(O/A))
	return (list("A"=A, "O"=O, "H"=H, "theta"=theta))
}

drawLines<-function(startPoints, endPoints) {
	plot(y~x, pch=NA, xlim=c(0, 100), ylim=c(0,100))

	radius<-40
	centre<-c(50, 50)
	draw.circle(centre[1], centre[2], radius, nv=100, lty=1, lwd=4, col=NA, border="blue")
	points(centre[1],centre[2], pch=19, col="blue",cex=2)

	numberOfPoints<-length(startPoints)
	for (iPoint in 1:numberOfPoints) {
		pStart<-startPoints[[iPoint]]
		pEnd<-endPoints[[iPoint]]
		coord.drawLineBetweenTwoPoints(pStart, pEnd, col="grey", lwd = 1.5, lty=1)	
		points(pStart[1], pStart[2], pch=19, col="red",cex=1)		
	}
}

getBoundaryCrossings<-function(randomStartPoints, randomEndPoints, randomAzimuths, centre, radius) {
	numberOfPoints<-length(randomStartPoints)
	crossing.startPoints<-list()
	crossing.endPoints<-list()
	crossing.azimuths<-list()	
	crossing.intersections<-list()
	
	for (iPoint in 1:numberOfPoints) {
		pStart<-randomStartPoints[[iPoint]]
		pEnd<-randomEndPoints[[iPoint]]
	
	
	    if (!Geometry.isPointInCircle(pStart, centre, radius) && Geometry.isPointInCircle(pEnd, centre, radius)) {
			crossing.startPoints<-collection.appendToList(crossing.startPoints, pStart)
			crossing.endPoints<-collection.appendToList(crossing.endPoints, pEnd)
			crossing.azimuths<-collection.appendToList(crossing.azimuths, randomAzimuths[iPoint])
			intersection<-calculateIntersectionAngles(pStart, pEnd, centre, radius)
			crossing.intersections<-collection.appendToList(crossing.intersections, intersection)
		}
	
	}
	return (list("startPoints"=crossing.startPoints, "endPoints"=crossing.endPoints, "azimuths"=crossing.azimuths, "intersections"=crossing.intersections))
	
}

calculateIntersectionAngles<-function(pStart, pEnd, centre, radius) {
	trigToCentre<-coord.calculateTrig(pStart, centre)
	trigToEnd<-coord.calculateTrig(pStart, pEnd)
	trigEndCentre<-coord.calculateTrig(pEnd, centre)

	a<-trigToEnd$H
	b<-trigEndCentre$H
	c<-trigToCentre$H
	
	B<-trigToEnd$theta-trigToCentre$theta		
	t2=b/sin(toRadians(B))	
	A = toDegrees(asin(a / t2))	
	C<-180-(A+B)

	c1<-radius	
	t3<-c1/sin(toRadians(C))	
	B1 <- toDegrees(sin(b / t3))	
	A<-180-(B1+C)		
	a1<-t3*sin(toRadians(A))

	intersection.H<-trigToEnd$H-a1

	#SOH
	intersection.O<- sin(toRadians(trigToEnd$theta)) * intersection.H  
	intersection.A<-sqrt(intersection.H^2-intersection.O^2)
	pIntersection<-c(pStart[1]+intersection.A, pStart[2]+intersection.O)

	return (list("B"=B, "B1"=B1, "pIntersection"=pIntersection))
}




coord.drawLineBetweenTwoPoints<-function(startPoint, endPoint, ...) {
	arrows(x0=startPoint[1],  y0=startPoint[2], x1=endPoint[1], y1=endPoint[2], ...)	
}

coord.generateRandomMoves<-function(startPoints, stepLength) {
	endPoints<-list()
	azimuths<-runif(n=length(startPoints), min=0, max=360)
	for (iPoint in 1:length(startPoints)) {
		pStart<-startPoints[[iPoint]]
		pEnd<-coord.moveTo(pStart, azimuths[iPoint], stepLength)
		endPoints<-collection.appendToList(endPoints, pEnd)
	}
	return(list("endPoints"=endPoints, "azimuths"=azimuths))
}

coord.generateRandomPointsInSquare<-function(n, xlim, ylim) {
	x<-runif(n, min=xlim[1], max=xlim[2])	
	y<-runif(n, min=ylim[1], max=ylim[2])	
	return (coord.combineXY(x, y))
}

