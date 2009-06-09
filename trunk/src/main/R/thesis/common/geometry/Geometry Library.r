lib.loadLibrary("/common/Collection Manipulation Library.r")
cat("Geometry Library (v1.0)\n")

#180 = pi
#rad<-toRadians(365)
#deg<-toDegrees(rad)
#cat("rad=", rad, ", deg=", deg, "\n")
toRadians<-function(degrees){
	return ((pi/180)*degrees)
}


toDegrees<-function(radians){
	return (180/pi*radians)
}

#Converts 0-360 into +/- 180
toAngleOfTurn<-function(degrees) {
	aot<-degrees
	if (degrees>180) {
		aot<-(degrees-360)
	}
	return (aot)
}

Geometry.plotIntersectionAreaDiagram<-function() {
	par(mar=c(5, 5, 5, 5))
	x<-c(1)
	y<-c(1)
	plot(y~x, axes=F, ann=F, pch=NA, ylim=c(0, 600), xlim=c(0, 600))
	axis(1, cex.axis=1.5)
	axis(2, cex.axis=1.5)
	axis(3, cex.axis=1.5)
	axis(4, cex.axis=1.5)
	box()

	x<-c(195, 380)
	y<-c(300, 300)


	centre1<-c(195, 300)
	R1<-150
	centre2<-c(380, 300)
	R2<-100
	D<-coord.displacementDistance(centre1, centre2)
	d1<-(D^2-R2^2+R1^2)/(2*D)
	d2<-(D^2+R2^2-R1^2)/(2*D)
	
	#Simple pythagoras to work out half the height of the chord...
	#h^2 = a^2 + b^2
	# we know h^2 and a^2 so:
	# b^2 = h^2 - a^2 
	c2<-sqrt(R1^2-d1^2)	
	
	#Then can plot the chord....
	lines(x=c(195+d1, 195+d1), y=c(300, 300+c2), col="grey", lwd=2)
	lines(x=c(195+d1, 195+d1), y=c(300, 300-c2), col="grey", lwd=2)
	
	lines(x=c(380, 380-d2), y=c(300, 300), lty=2, lwd=2,col="red" )
	text(360, 280, expression(d[2]), cex=1.5, col="red")

	lines(x=c(195, 195+d1), y=c(300, 300), lty=2, lwd=2,col="green" )
	text(240, 280, expression(d[1]), cex=1.5, col="green")
	
	text(310, 340, "c", cex=1.5, col="grey")
	
	#Radii:	
	lines(x=c(380,380-d2), y=c(300, 300+c2), lty=2, lwd=1,col="blue" )
	lines(x=c(195, 195+d1), y=c(300, 300+c2), lty=2, lwd=1,col="blue" )

	text(230, 350, expression(R[1]), cex=1.5, col="blue")
	text(370, 350, expression(R[2]), cex=1.5, col="blue")

	symbols(195, 300, circles=c(150),fg="blue",inches=F, add=T, lwd=2)
	symbols(380, 300, circles=c(100),fg="blue",inches=F, add=T, lwd=2)

	points(y~x, pch=19, col="blue", cex=1.5)
}


#x<-layout.df$x;y<-layout.df$y;radii<-rep(50, 16)
Geometry.areaCovered<-function(x, y, radii) {
	if ((length(x) != length(y)) && (length(x) != length(radii))) {
		stop("x, y and radii must have the same number of elements.")
	}		

	totalArea<-0
	totalIxArea<-0
	for (i.1 in 1:length(x)) {
		for (i.2 in i.1:length(x)) {
			if (i.1 != i.2) {
				centre1<-c(x[i.1], y[i.1])
				centre2<-c(x[i.2], y[i.2])
				R1<-radii[i.1]
				R2<-radii[i.2]
				IxArea<-Geometry.intersectionArea(centre1, R1, centre2, R2)
				totalIxArea<-totalIxArea+IxArea
			}
		}
		Area<-pi*radii[i.1]^2
		totalArea<-totalArea+Area		
	}
	return (totalArea-totalIxArea)
}
600x600


Geometry.intersectionArea<-function(centre1, R1, centre2, R2) {
	D<-coord.displacementDistance(centre1, centre2)
	
	if (D<(R1+R2)) {#They intersect...		
		A<-Geometry.areaOfSegment(D, R1, R2)
	} else {
		A<-0
	}
	return(A)	
}




#R - radius, d - distance from centre to chord..
#R<-R2
#From http://mathworld.wolfram.com/Circle-CircleIntersection.html
#http://mathworld.wolfram.com/Circle-CircleIntersection.html
Geometry.areaOfSegment<-function(D, R1, R2) {
	term1<-(-D+R1+R2)
	term2<-(D+R1-R2)
	term3<-(D-R1+R2)
	term4<-(D+R1+R2)
	sqr<-sqrt(term1*term2*term3*term4)
	return (0.5*sqr)
}


#Can solve a system of simulataneous equations using Cramers' Rule:
# As long as matrices of AX = B
#Need to re-arrange our formulae so 
# Calculates the intersection of two regression lines
#
# y = slope*x + intercept
#
# can be re-arranged to:
#
# slope1*x -y = -intercept1
# slope2*x -y = -intercept2
#
# the second column is always going to be -1 then.
#
#ALL lines intersect unless they are absolutely parallel, the question is, WHERE do they intersect...
# replace entries in each column by those in B (the values -intercept1, -intercept2)
#then apply cramers rule which is:
#  x1=det(A1) / det(A),  x2=det(A2) / det(A)
Geometry.calculateIntersection<-function(slope1, intercept1, slope2, intercept2) {
	result<-NA
	
	#Then put the values into matrices....
	A<-matrix(c( c(slope1, -1), c(slope2, -1 )), nrow=2, ncol=2, byrow=T)
	detA<-det(A)
	
	if (detA!=0) {
		A1<-matrix(c( c(-intercept1, -1), c(-intercept2, -1 )), nrow=2, ncol=2, byrow=T)
		A2<-matrix(c( c(slope1, -intercept1), c(slope2, -intercept2)), nrow=2, ncol=2, byrow=T)

		x1<-det(A1)/detA
		x2<-det(A2)/detA

		#and our coordinates should be
		xI<-x1
		yI<-x2
	
		result<-list("x"=xI, "y"=yI)
	} 
	return (result)
}

Geometry.toAngleOfTurnRadians<-function(radians) {
	aot<-radians
	if (radians>pi) {
		aot<-(radians-(2*pi))
	}
	return (aot)
}


coord.moveTo<-function(startCoordinate, azimuth, radius) {
	polarCoord<-coord.polarFromAzimuth(c(azimuth, radius))
	incr<-coord.rectangularFromPolar(polarCoord)
	x<-startCoordinate[1]
	y<-startCoordinate[2]
	return (c(x+incr[1], y+incr[2]))
}


coord.polarFromRectangularCoordinates<-function(coordinateList) {
	results<-list()
	for (coordinate in coordinateList) {
		results<-appendToList(results, coord.polarFromRectangular(coordinate))
	}
	return (results)
}

coord.polarFromRectangular<-function(coordinate) {

	phi<-coord.polarAngle(coordinate)
	r<-coord.polarDistance(coordinate)
	
	return (c(phi, r))	
}

coord.azimuthFromPolarCoordinates<-function(coordinateList) {
	results<-list()
	for (coordinate in coordinateList) {
		results<-appendToList(results, coord.azimuthFromPolar(coordinate))
	}
	return (results)
}

coord.azimuthFromPolar<-function(coordinate) {
	theta<-coord.azimuthFromPolarAngle(coordinate[1])
	r<-coordinate[2]
	
	return (c(theta, r))	
}

coord.polarFromAzimuthCoordinates<-function(coordinateList) {
	results<-list()
	for (coordinate in coordinateList) {
		results<-appendToList(results, coord.polarFromAzimuth(coordinate))
	}
	return (results)
}

coord.polarFromAzimuth<-function(coordinate) {
	phi<-coord.polarFromAzimuthAngle(coordinate[1])
	r<-coordinate[2]
	
	return (c(phi, r))	
}

coord.rectangularFromPolarCoordinates<-function(coordinateList) {
	results<-list()
	for (coordinate in coordinateList) {
		results<-appendToList(results, coord.rectangularFromPolar(coordinate))
	}
	return (results)
}

coord.rectangularFromPolar<-function(coordinate) {
	polarAngle<-coordinate[1]
	polarDistance<-coordinate[2]
	phiRadians <- toRadians(polarAngle)
  	x <- cos(phiRadians) * polarDistance
	y <- sin(phiRadians) * polarDistance
	
	if (is.na(polarAngle) || is.na(polarDistance) || polarDistance==0) {
		x<-0
		y<-0
	}
	return (c(x, y))
}

coord.circleFromPolarCoordinates<-function(coordinateList, radius) {
	results<-list()
	for (coordinate in coordinateList) {
		results<-appendToList(results, coord.circleFromPolar(coordinate, radius))
	}
	return (results)
}

# Simply returns the polarAngle but with a specific radius...
coord.circleFromPolar<-function(coordinate, radius) {
	return (c(coordinate[1], radius))
}


#From Batschelet, E.  1981.  Circular Statistics In Biology. Academic Press, San Fransisco.
#P.240
#{Batschelet, 1981 #8052}
# Except it doesnt seem to produce the right angle when you go into fourth quadrant (x>0 && y<0) so i 
# added 360
coord.polarAngle<-function(coordinate){		
	x<-coordinate[1]
	y<-coordinate[2]
	
	if (x > 0 && y<0) {
		phi<-360 + toDegrees(atan(y/x))
	} else if (x > 0) {
    	phi<-toDegrees(atan(y/x))
	} else if (x < 0) {
		phi<-180 + toDegrees(atan(y/x))
	} else if (x==0 && y >0) {
		phi = 90
	} else if (x==0 && y<0) {
		phi = 270
	} else if (x==0 && y==0) {
		phi = NA # it is undetermined - the point is on the origin so can have no angle.
	}
	
	return (phi)
}

#Calculates the linear distance between two points.
#Pass in the points as two arrays of size 2, e.g.:
#
#             x   y              x   y
#  point1<-c(10, 10); point2<-c(10, 20)
#
# It works by treating the first point as the origin and then calculating the polar distance 
# to the second point - the polar coordinate of the point would be the angle from the origin 
# and the straight line distance
#
coord.displacementDistance<-function(start, end) {
	x1<-start[1]
	y1<-start[2]
	
	x2<-end[1]
	y2<-end[2]
	
	rectCoord<-c(x2-x1, y2-y1)
	return (coord.polarDistance(rectCoord))
	
}

# Given a rectangular coordinate calculate the polar distance from the origin.
# Simply use pythagoras' theorum - you know the adjacent and opposite sides of the triangle
# because they are x and y.
#
# a^2 = b^2 + c^
#
# so 
#
# a = sqrt(b^2 + c^)
#
# where a is the hypotenuse, b + c adjacent + opposite (doesnt matter in this instance which one is which)
coord.polarDistance<-function(coordinate) {
	x<-coordinate[1]
	y<-coordinate[2]
	
	r <- sqrt(x^2 +y^2)
	return (r)
}

#coord.headingFromOrigin(c(10, 10))
#Because polar coordinates go anticlockwise from the x axis - we have to firs invert the degrees(360-polarAngle)
#Then add 90 as 0 is the x axis
#
# theta = (360 - phi) + 90
#
coord.azimuthFromPolarAngle<-function(polarAngle) {
	return(coord.restrictAngle360((360-polarAngle)+90))
}

#
# phi=(360+90)-theta
#
coord.polarFromAzimuthAngle<-function(azimuth) {
	return(coord.restrictAngle360((360+90)-azimuth))
}

# see {Batschelet, 1981 #8052}
coord.restrictAngle360<-function(angle) {
	restricted<-angle %% 360
	if (!is.na(restricted)) {		
		if (restricted == 0) {
			restricted <- abs(restricted)
		}
	}
	return (restricted)
}






coord.polarAngles<-function(coordinateList){
	results<-c();
	for (coordinate in coordinateList) {
		angle<-coord.polarAngle(coordinate)
		results<-c(results, angle)
	}
	return (results)
}


coord.printResults<-function(results) {
	for (polarAngle in results) {
		cat(sprintf("%0.2f", polarAngle), ", ")
	}
	cat("\n")
}

coord.printRectangularCoordinates<-function(coordinateList){
	for (coordinate in coordinateList) {
		coord.printRectangularCoordinate(coordinate)
		cat("| ")
	}
}

coord.printRectangularCoordinate<-function(coordinate) {
	cat(sprintf("(x=%0.2f, y=%0.2f)", coordinate[1], coordinate[2]))
}

coord.printPolarCoordinates<-function(coordinateList){
	for (coordinate in coordinateList) {
		coord.printPolarCoordinate(coordinate)
		cat("| ")
	}
}

coord.printPolarCoordinate<-function(coordinate) {
	cat(sprintf("(phi=%0.2f, r=%0.2f)", coordinate[1], coordinate[2]))
}

coord.printAzimuthCoordinates<-function(coordinateList){
	for (coordinate in coordinateList) {
		coord.printAzimuthCoordinate(coordinate)
		cat("| ")
	}
}

coord.printAzimuthCoordinate<-function(coordinate) {
	cat(sprintf("(theta=%0.2f, r=%0.2f)", coordinate[1], coordinate[2]))
}

coord.uniformRandomAzimuth<-function() {
	rnd<-runif(1)
	return(coord.uniformRandomAzimuthX(rnd))
}
coord.uniformRandomAzimuthX<-function(x) {
	return (360*x)
}

coord.extractX<-function(coordinateList) {
	x<-c()
	for (coordinate in coordinateList) {
		x<-c(x, coordinate[1])
	}
	return (x)
}

coord.extractY<-function(coordinateList) {
	y<-c()
	for (coordinate in coordinateList) {
		y<-c(y, coordinate[2])
	}
	return (y)
}

coord.combineXY<-function(x, y) {
	
	coords<-list()
	for (i in 1:length(x)) {
		coord<-c(x[i], y[i])
		coords<-collection.appendToList(coords, coord)
	}
	return (coords)
}

#Could extend this to take a list of radii but we dont need it just now...
Geomtery.estimateAreaOfCircles<-function(cx, cy, radius, n=25) {
	
	
	minx<-min(cx)-radius
	miny<-min(cy)-radius
	maxx<-max(cx)+radius
	maxy<-max(cy)+radius

	centres<-coord.combineXY(cx, cy)	
	
	radii<-rep(radius, length(cx))
	

	ratios<-c()
	for (i in 1:40) {
		x<-runif(n, min=minx, max=maxx)
		y<-runif(n, min=miny, max=maxy)
		points<-coord.combineXY(x,y)
		ratio<-Geomtery.proportionPointsInCircles(points, centres, radii) 
		ratios<-c(ratios, ratio)
	}

	meanRatio<-round(mean(ratios), 1)

	sampleArea<-(maxx-minx)*(maxy-miny)
	areaEstimate<-sampleArea*meanRatio
	
	return (areaEstimate)
}

#Real hard work but can estimate the area by randomly sampling the space.
#point<-points[[1]];centre<-centres[[1]];radii<-radii[[1]]
Geomtery.proportionPointsInCircles<-function(points, centres, radii) {
	countIn<-0
	countOut<-0
	for (point in points) {
		is.in<-FALSE
		for (i in 1:length(centres)) {
			centre<-centres[[i]]
			radius<-radii[[i]]
			if (Geometry.isPointInCircle(point, centre, radius)) {
				is.in<-TRUE
				break
			} 
		}
		if (is.in) {
			countIn<-countIn+1
		} else {
			countOut<-countOut+1
		}
	}
	return (countIn/(countIn+countOut))	
}

Geometry.isPointInCircle<-function(point, centre, radius) {
	D<-coord.displacementDistance(point, centre)
	return (D<=radius)
}

#coord.displacementDistance(c(50, -50),c(0, -50))
