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
