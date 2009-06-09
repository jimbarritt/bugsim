source("~/Work/code/bugsim/src/R/archive/Summary Statistics Library.r", echo=FALSE)
cat("Geometry Library (v1.0)\n")

#180 = pi
#rad<-toRadians(365)
#deg<-toDegrees(rad)
#cat("rad=", rad, ", deg=", deg, "\n")
toRadians<-function(degrees){
	return (pi/180*degrees)
}


toDegrees<-function(radians){
	return (180/pi*radians)
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

coord.displacementDistance<-function(start, end) {
	x1<-start[1]
	y1<-start[2]
	
	x2<-end[1]
	y2<-end[2]
	
	polarCoord<-c(x2-x1, y2-y1)
	return (coord.polarDistance(polarCoord))
	
}
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
		coords<-appendToList(coords, coord)
	}
	return (coords)
}

