#Contains some demonstrations of cartesian calculations with coordinates
source("~/Work/code/bugsim/resource/R/Geometry Library.r", echo=FALSE)

P1<-c(10, 10)
P2<-coord.moveTo(P1, 45, 10)
P3<-coord.moveTo(P2, 180, 10)
P4<-coord.moveTo(P3, 230, 10)
P5<-coord.moveTo(P4, 330, 10)


coords<-list(P1, P2, P3, P4, P5)
x<-coord.extractX(coords)
y<-coord.extractY(coords)

plot(y~x, xlim=c(-20, 20), ylim=c(-20, 20))
lines(y~x)

azimuths<-c()
for (i in 1:1000) {
	azimuth<-coord.uniformRandomAzimuth()
	azimuths<-c(azimuths, azimuth)
}
hist(azimuths)
min(azimuths)
max(azimuths)

#Test conversion of rectangular coords to polar angles
rectangularCoordinates<-list(
	c(0, 0),
	c(10, 0),
	c(10, 1),
	c(10, 10),
	c(1, 10),
	c(0, 10),
	c(-1, 10),
	c(-10, 10),
	c(-10, 1),
	c(-10, 0),
	c(-10, -1),
	c(-10, -10),
	c(-1, -10),
	c(0, -10),
	c(1, -10),
	c(10, -10),
	c(10, -1)
)

coord.printRectangularCoordinates(rectangularCoordinates)

polarCoordinates<-coord.polarFromRectangularCoordinates(rectangularCoordinates)
coord.printPolarCoordinates(polarCoordinates)

#Expresses the polar coordinates but in terms of the azimuth (i.e. North is 0degrees)
azimuthCoordinates<-coord.azimuthFromPolarCoordinates(polarCoordinates)
coord.printAzimuthCoordinates(azimuthCoordinates)

polarCoordinatesRecalc<-coord.polarFromAzimuthCoordinates(azimuthCoordinates)
coord.printPolarCoordinates(polarCoordinatesRecalc)

rectangularCoordinatesRecalc<-coord.rectangularFromPolarCoordinates(polarCoordinatesRecalc)
coord.printRectangularCoordinates(rectangularCoordinatesRecalc)



circle<-coord.circleFromPolarCoordinates(polarCoordinatesRecalc, 5)
rectangularCircle<-coord.rectangularFromPolarCoordinates(circle)

x<-coord.extractX(rectangularCoordinates)
y<-coord.extractY(rectangularCoordinates)

x2<-coord.extractX(rectangularCoordinatesRecalc)
y2<-coord.extractY(rectangularCoordinatesRecalc)

x3<-coord.extractX(rectangularCircle)
y3<-coord.extractY(rectangularCircle)

colors<-rainbow(length(x))
quartz(width=12, height=12)
plot(y~x, pch=NA)
points(y~x, pch=1, cex=2, col=colors)
points(y2~x2, pch=19, cex=1, col=colors)
points(y3~x3, pch=19, cex=1, col=colors)



#Old stugff :

atan(1/ -10)
atan(1/ 10)






r<-1
headingDegrees<-10
polarDegrees<-(360-(headingDegrees-90))
thetaRad<-toRadians(polarDegrees)
x<-cos(thetaRad)*r
y<-sin(thetaRad)*r
plot(y~x, main=expression(theta==thetaDeg), xlim=c(-1, 1), ylim=c(-1,1))
points(x=c(0, x), y=c(0, y))
lines(x=c(0,x), y=c(0,y))

rCalc<-sqrt(x^2 + y^2)



P1<-c(0,0)
P2<-moveTo(P1, 45, 10)
P3<-moveTo(P2, 90, 5)
P4<-moveTo(P3, 270, 5)
P5<-moveTo(P4, 320, 15)
P6<-moveTo(P5, 170, 5)
x<-c(P1[1], P2[1], P3[1], P4[1], P5[1], P6[1])
y<-c(P1[2], P2[2], P3[2], P4[2], P5[2], P6[2])
plot(y~x, type="l")
points(y~x)


testPoint1<-moveTo(c(0,0), 0, 10)
d1<-calculateDisplacement(c(0,0), testPoint1)

testPoint2<-moveTo(c(0,0), 90, 10)
d2<-calculateDisplacement(c(0,0), testPoint2)

testPoint3<-moveTo(c(0,0), 180, 10)
d3<-calculateDisplacement(c(0,0), testPoint3)

testPoint4<-moveTo(c(0,0), 270, 10)
d4<-calculateDisplacement(c(0,0), testPoint4)

x<-c(testPoint1[1], testPoint2[1], testPoint3[1], testPoint4[1])
y<-c(testPoint1[2], testPoint2[2], testPoint3[2], testPoint4[2])

sprintf("%0.0f, %0.0f, %0.0f, %0.0f", d1[1], d2[1], d3[1], d4[1])
plot(y~x, type="p")

getQuadrant(10,0)
getQuadrant(10,-1)
getQuadrant(0,-1)
getQuadrant(-1,-1)
getQuadrant(-1,0)
toDegrees(atan(10/10))
toDegrees(atan(10/5))
toDegrees(atan(10/0))

calculateHeadingFromOrigin(testPoint4)
calculateHeadingFromOrigin(c(0,-10))
calculateHeadingFromOrigin(c(10,10))
calculateHeadingFromOrigin(c(10,0))
calculateHeadingFromOrigin(c(10,-1))
90+90+toDegrees(atan(10/-1))
90+90+toDegrees(atan(10/-10))
90+90+toDegrees(atan(0/-10))
90+90+toDegrees(atan(-1/-10))
90+90+toDegrees(atan(-10/-10))
90+90+toDegrees(atan(-10/-1))
90+90+90+90+toDegrees(atan(-10/0))
90+90+90+90+toDegrees(atan(-10/1))
90+90+90+90+toDegrees(atan(-10/10))

toDegrees(atan(-7.81/2.35))
315-270
toDegrees(atan(-10/10))



cos(toRadians(90))
sin(toRadians(90))

-cos(toRadians(110-180))
