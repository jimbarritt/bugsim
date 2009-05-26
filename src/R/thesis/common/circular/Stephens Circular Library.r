# Functions_circular.R
# by Stephen Hartley, 10-Nov-06
# Functions to calculate circular stats and draw circular plots

# Notes
# There are four common measures of dispersion for circular data:
# 1. circular SD or circular dispersion (Zar), corresponds to the SD of a wrapped normal distribution? SD = (180/pi)*sqrt(-2log(rho)).  See also CircStats, dwrpnorm 
# 2. length of mean vector ('r' or 'rho') (i.e. displacement of the centre of gravity from the origin) (ranges from 0 to 1)
# 3. circular variance (a simple function of mean vector length and sample size, for point data on the unit circle)
# 4. the kappa parameter of a fitted von Mises distribution

# Other distributions can also be fitted such as the wrapped normal, wrapped Cauchy and sine-wave.
# Each of these has one parmeter relating to the angle of bias (mean angle) 
#   	and another parameter to describe the concentration or dispersion around the mean angle.

# Many of the functions below accept a vector of grouped values as input (e.g. counts.v)
# Counts.v is assumed to consist of counts or measures taken at equal intervals around a circle (e.g. at 12 monthly values)
# If the data consists of 'events' or 'point records' made continuosly around a unit circle then please refer to the 
#	many useful functions found in the CircStat library, 

# To convert from a grouped data structure (e.g. monthly values) to a set of point locations distributed 
#	around a unit circle, use the function 'groups2points' below. 


library(CircStats)
library(circular)		#includes functions for linear-circular regression and anova, Rayleigh test, plus plotting routines

# Convert degrees to radians or vice versa:

Deg2Rad = function(values.in.deg) {values.in.deg*pi/180}
Rad2Deg = function(values.in.rad) {values.in.rad*180/pi}


calc.sd = function(angles.v) {   					# only works if angles are in degrees
	anglesR.v <- Deg2Rad(angles.v)
	s.d. <- sqrt(2*(1-circ.summary(anglesR.v)[[3]]))		# Batschelet formula for angular s.d.
	mean <- circ.summary(anglesR.v)[[2]]
	paste("mean =", signif(Rad2Deg(mean), 4), "s.d.=", signif(Rad2Deg(s.d.), 4), "  mean +/- s.d.", signif(Rad2Deg(mean - s.d.), 4), "to", signif(Rad2Deg(mean + s.d.), 4)) 
	}

# calc.lags
# input two vectors of equal length  (in units of degrees (max range = 360))
# output is one vector of their lags (from -180 to +180 degrees)
# default method calculates lags for a positive correlation
# entering the second vector with a minus sign calculates the lags needed to measure negative correlations 

calc.lags = function(angles1.v, angles2.v, ...) {   # only works if angles are in degrees
	ns <- n1 <- length(angles1.v)
	n2 <- length(angles2.v)
	if(n1 != n2) print(paste("Warning: unequal sample sizes", n1, n2))
	lags.v <- angles2.v - angles1.v
	for (i in 1:ns) {
   		if(lags.v[i] < -180) lags.v[i] <- lags.v[i] + 360
     		if(lags.v[i] > 180) lags.v[i] <- lags.v[i] - 360
   		if(lags.v[i] < -180) lags.v[i] <- lags.v[i] + 360
     		if(lags.v[i] > 180) lags.v[i] <- lags.v[i] - 360 
   		}
	lags.v
	}

circ.corr = function(angles1.v, angles2.v, ...) {   	# calculates positive and negative correlations between two vectors of angles
	n <- length(angles1.v)				# refer to Batschelet p183 for table of critical values of the test statistic
	lags.1 <- calc.lags(angles1.v, angles2.v)
	lags.2 <- calc.lags(angles1.v, -angles2.v)
	r.plus <- est.rho(Deg2Rad(lags.1))
	r.neg  <- est.rho(Deg2Rad(lags.2))
	corr.coef <- max(r.plus, r.neg)
	angle.plus <- Rad2Deg(circ.mean(Deg2Rad(lags.1)))
	angle.neg <-  Rad2Deg(circ.mean(Deg2Rad(lags.2)))
	ifelse (r.plus > r.neg, corr.sign <- +1, corr.sign <- -1)
	output.v <- c(corr.coef, corr.sign, r.plus, r.neg, corr.coef*sqrt(n), n, angle.plus, angle.neg)
	dim(output.v) <- c(1,8)	
	colnames(output.v) <- c("max_corr", "corr.sign", "r.plus", "r.neg", "test.stat", "n", "angle.plus", "angle.neg")
	output.v
	}


circ.stats = function(counts.v, ...) {  			# Calculate the angular mean (Zar Chap 24.4)
	bins    <- length(counts.v)				# number of equal sized segments
	lambda  <- 2*pi/bins					# size of one segment in radians
	angles  <- 360/(2*bins) + (0:(bins-1))*360/bins	# angles at sector mid-points
	angles  <- Deg2Rad(angles)
	n <- sum(counts.v)    				  	# n = Number of observation events
	average <- n/bins
	ifelse (n==0, X <- 0, X <- sum(counts.v*cos(angles))/n)
	ifelse (n==0, Y <- 0, Y <- sum(counts.v*sin(angles))/n)
   	r.length  <- sqrt(X^2 + Y^2)					  # mean vector length for point data
	rc.length <- r.length*((lambda/2)/sin(lambda/2))	  # mean vector length corrected for grouped data
	if (X>0)  r.angle <- atan(Y/X)
	if (X<0)  r.angle <- atan(Y/X) + pi 
	if (X==0) r.angle <- NA 
	ifelse (r.angle<0, r.angle <- r.angle + (2*pi), NA)		  # convert negative angles to positive
   	circSD <- 180/pi*sqrt(-2*log(r.length))	  		  # calculate the angular dispersion (Zar section 24.5)
	circSDc <- 180/pi*sqrt(-2*log(rc.length))
	output.v <- c(r.length, rc.length, r.angle, Rad2Deg(r.angle), circSD, circSDc, bins, average)
	dim(output.v) <- c(1,8)
	colnames(output.v) <- c("r.length", "rc.length", "r.angle.rad", "r.angle.deg", "circSD", "circSDc", "bins", "average")
	output.v
	}



draw.circ = function(x = 0, y = 0, r = 1, theta1 = 0, theta2 = 2*pi, sides = 360, ...) {
	incrmt <- (2*pi)/sides
	steps <- ((theta2 - theta1)/incrmt)+1
	x.coords <- c()
	y.coords <- c()
	theta <- theta1
	for(i in 1:steps) {
		x.coords[i] <- (r*sin(theta)) + x
		y.coords[i] <- (r*cos(theta)) + y
		theta <- theta + incrmt
		}
	lines(x.coords, y.coords, ...)
	}


# draw.seg description
#           	(x = x of origin, y = y of origin, r = radius,
#           	theta1 = start angle in radians, theta2 = end angle in radians, 
#		resln = resolution of the arc, i.e. number of straight line segments in a full circle
#		col= color to shade polygon segments, e.g. "red", default is no fill
#		draw.seg.2 does not draw lines around segments - called by draw.rainbow.hist4

draw.seg = function(x = 0, y = 0, r = 1, theta1 = 0, theta2 = pi/2, resln = 360, ...) {
	incrmt <- (2*pi)/resln
	steps <- ((theta2 - theta1)/incrmt) + 1
	x.coords <- c()
	y.coords <- c()
	theta <- theta1
	for(i in 1:steps) {
		x.coords[i] <- (r*sin(theta)) + x
		y.coords[i] <- (r*cos(theta)) + y
		theta <- theta + incrmt
		}
	polygon(c(x, x.coords), c(y, y.coords), ...)
	}

draw.seg2 = function(x = 0, y = 0, r = 1, theta1 = 0, theta2 = pi/2, resln = 360, ...) {
	incrmt <- (2*pi)/resln
	steps <- ((theta2 - theta1)/incrmt) + 1
	x.coords <- c()
	y.coords <- c()
	theta <- theta1
	for(i in 1:steps) {
		x.coords[i] <- (r*sin(theta)) + x
		y.coords[i] <- (r*cos(theta)) + y
		theta <- theta + incrmt
		}
	polygon(c(x, x.coords), c(y, y.coords), lty = 0, ...)
	}


			# input an x, y and angle in radians
draw.polar.arrow = function(x = 0, y = 0, r = 1, angle, ...) {
	theta <- angle
	x1 <- (r*sin(theta)) + x
	y1 <- (r*cos(theta)) + y
	arrows(x, y, x1, y1, ...)		# length of arrow head specified by "length ="
	}   

# draw.vector
# input is a column of counts in bins distributed around a circle
# draws the mean angle vector of specified length (r) and returns the x,y coords of the end point
# the end point is the centre of gravity, calculated as if the counts were applied as "weights" on
# the circle of radius = mean of counts, theta is returned in radians
	
draw.vector = function(counts.v, r = circ.stats(counts.v)[8] * circ.stats(counts.v)[2], x = 0, y = 0, mag = 1, draw.arrow = TRUE, ...) {
	theta <- circ.stats(counts.v)[3]
	x1 <- (r*sin(theta)* mag) + x
	y1 <- (r*cos(theta)* mag) + y
	if (draw.arrow == TRUE) arrows(x, y, x1, y1, length = (r/20)+ 0.05, ...)		# length of arrow head proportional to arrow length
	c(r, theta, x1, y1)									# print results (# or not)
	}   

draw.vector1 = function(counts.v, r = circ.stats(counts.v)[8] * circ.stats(counts.v)[2], x = 0, y = 0, ...) {
	theta <- circ.stats(counts.v)[3]
	x1 <- (r*sin(theta)) + x
	y1 <- (r*cos(theta)) + y
	arrows(x, y, x1, y1, ...)		# length of arrowhead can be set when calling the function
	c(r, theta, x1, y1)				# print results
	}

calc.theta = function(counts.v, ...) { # returns theta in degrees
	circ.stats(counts.v)[4]
	} 


circular.hist1 = function(counts.v, plot.size = (max(counts.v)*1.05), max.tic = max(counts.v), min.tic = 0, tics = 2, draw.mean = FALSE, ...) {  # looks like spokes on a wheel
	bins <- length(counts.v)
	angles <- 360/(2*bins) + (0:(bins-1))*360/bins
	angles <- Deg2Rad(angles)
	plot.limits <- c(-plot.size, plot.size)
	plot(0, 0, xlim = plot.limits, ylim = plot.limits, xaxp = c(min.tic, max.tic, tics), yaxp = c(min.tic, max.tic, tics), ...)
	if (draw.mean == TRUE) draw.circ(r = sum(counts.v)/bins)
	for (i in 1:bins) lines(c(0,counts.v[i]*sin(angles[i])), c(0,counts.v[i]*cos(angles[i])), ...)
	}


circular.hist2 = function(counts.v) {   # exploded pie segments, flat ended
	bins <- length(counts.v)
	angles <- (2*pi)/(4*bins) + (0:((2*bins)-1))*(2*pi)/(2*bins)
	plot.limits <- c(-max(counts.v), max(counts.v))
	plot(0, 0, xlim = plot.limits, ylim = plot.limits)
	for(i in 1:bins) {	
			x.coords <- c(0, counts.v[i]*sin(angles[(i*2)-1]), counts.v[i]*sin(angles[i*2]), 0)
			y.coords <- c(0, counts.v[i]*cos(angles[(i*2)-1]), counts.v[i]*cos(angles[i*2]), 0)
			lines(x.coords, y.coords) 
			}
	}

circular.hist3 = function(counts.v) {     		# flat-ended pie segments, touching
	bins <- length(counts.v)				# number of bins or sectors in the grouped data
	angles <- (0:bins)*(2*pi/bins)			# angles of sector edges (start = 0 = end = 360)
	plot.limits <- c(-max(counts.v), max(counts.v))
	plot(0, 0, xlim = plot.limits, ylim = plot.limits)
	for(i in 1:bins) {
			x.coords <- c(0, counts.v[i]*sin(angles[i]), counts.v[i]*sin(angles[i+1]), 0)
			y.coords <- c(0, counts.v[i]*cos(angles[i]), counts.v[i]*cos(angles[i+1]), 0)
			lines(x.coords, y.coords) 
			}
	}

circular.hist4 = function(counts.v, resln = 360, limit = max(counts.v), type = linear, ...) {  # high-level plot of a rose-diagram
	bins <- length(counts.v)
	angles <- (0:bins)*(2*pi/bins)
	plot.new()
	plot.window(xlim = c(-limit, limit), ylim = c(-limit, limit), asp = 1, ...)
	#plot(0, 0, xlim = plot.limits, ylim = plot.limits, xaxt = "n", yaxt="n", ...)
      # plot(0, 0, xaxt = "n", yaxt="n", ...)
	#axis(1, at = 0); axis(2, at = 0)
	for(i in 1:bins) {
		draw.seg(r = counts.v[i], theta1 = angles[i], theta2 = angles[i+1], resln = resln, ...)
			}
	}

draw.hist4 = function(counts.v, resln = 360, limit = max(counts.v), type = linear, ...) {  # adds a rose-diagram to an existing plot
	bins <- length(counts.v)
	angles <- (0:bins)*(2*pi/bins)
	for(i in 1:bins) {
		draw.seg(r = counts.v[i], theta1 = angles[i], theta2 = angles[i+1], resln = resln, ...)
			}
	}

# draw.rainbow.hist4: draws a colour wheel if all values in counts.v are equal
		# saturation and value of hsv colour can be set with s = ... h = ...

draw.rainbow.hist4 = function(counts.v, resln = 360, limit = max(counts.v), type = linear, ...) {  # adds a rose-diagram to an existing plot
	bins <- length(counts.v)
	angles <- (0:bins)*(2*pi/bins)
	for(i in 1:bins) {
		draw.seg2(r = counts.v[i], theta1 = angles[i], theta2 = angles[i+1], resln = resln, col = rainbow(bins, ...)[i], ...)
			}
	}


circular.labels = function(labels.v, r=0.5, cex = 1, txtcol = NA, ...) {  # add a circle of labels, e.g. use with circular.hist4
	bins <- length(labels.v)
	angles <- 360/(2*bins) + (0:(bins-1))*360/bins
	angles <- Deg2Rad(angles)
	for (i in 1:bins) text(r*sin(angles[i]), r*cos(angles[i]), labels.v[i], cex=cex, col=txtcol, ...)
	}

# color.wheel see 'pie' in the graphics library
# E.g. n <- 180, pie(rep(1,n), labels="", col=rainbow(n), border=NA, clockwise = TRUE)

draw.crosshairs = function(x = 0, y = 0, xmax = 1, xmin = -xmax, ymax = xmax, ymin = xmin, 
					tic.interval = floor(xmax-xmin)/4, tic.size = tic.interval/20, 
					tic.labels = TRUE, spacer = 2.5, axis.labels = c("0", "90", "180", "270"), ...) {
	lines(c(x, x), c(ymin, ymax), ...)
	lines(c(xmin, xmax), c(y, y), ...)
	if(tic.interval == 0) { tic.interval <- signif((xmax - xmin)/4, 1) }
	tic.pos <- x
	while(tic.pos < ymax) {
		tic.pos <- tic.pos + tic.interval
		lines(c(x-tic.size, x+tic.size), c(tic.pos, tic.pos), ...)
		if(tic.labels == TRUE) text(x-(spacer*tic.size), tic.pos, tic.pos, adj = c(1,0.5), ...)
		}
	tic.pos <- x
	while(tic.pos > ymin) {
		tic.pos <- tic.pos - tic.interval
		lines(c(x-tic.size, x+tic.size), c(tic.pos, tic.pos), ...)
		if(tic.labels == TRUE) text(x-(spacer*tic.size), tic.pos, tic.pos, adj = c(1,0.5), ...)
		}
 	tic.pos <- y
	while(tic.pos < xmax) {
		tic.pos <- tic.pos + tic.interval
		lines(c(tic.pos, tic.pos), c(y-tic.size, y+tic.size), ...)
		if(tic.labels == TRUE) text(tic.pos, y-(spacer*tic.size), tic.pos, adj = c(0.5,1),...)
		}
 	tic.pos <- y
	while(tic.pos > xmin) {
		tic.pos <- tic.pos - tic.interval
		lines(c(tic.pos, tic.pos), c(y-tic.size, y+tic.size), ...)
		if(tic.labels == TRUE) text(tic.pos, y-(spacer*tic.size), tic.pos, adj = c(0.5,1),...)
		}
	}


groups2points = function(counts.v, factor = 1, pointplot = TRUE, ...) {
	bins <- length(counts.v)				# number of bins or sectors in the grouped data
	angles <- (0:bins)*(2*pi/bins)			# angles of sector edges (start = 0 = end = 360)
	points.on.circ <- c()
	pos <- 1
	for (i in 1:bins) {
		theta1 <- angles[i]
		theta2 <- angles[i+1]
		if ((counts.v[i] * factor) > 0) {
			incrmt <- (2*pi) / (bins * round(counts.v[i] * factor))
			points.on.circ <- c(points.on.circ, seq(theta1+(incrmt/2), to = theta2-(incrmt/2), by = incrmt)) 
			theta3 <- points.on.circ[pos]
			pos <- pos + round(counts.v[i] * factor)
			}
		}
	if (pointplot == TRUE) { point.plot(points.on.circ, ...) }
	points.on.circ
	}

imageflip = function(input.matrix, ...) {  #transposes and flips an image so that row one prints across the top
	image(t(input.matrix)[1:ncol(input.matrix),nrow(input.matrix):1], ...)
	}


point.plot = function(angles.v, r=0.5, ...) {  		# points plotted on a circle, accepts angles in radians
	limit <- r*1.2
	plot.new()
	plot.window(xlim = c(-limit, limit), ylim = c(-limit, limit), asp = 1, ...)
	points(r*sin(angles.v), r*cos(angles.v), ...)
	}

# polar.plot: 	plots a set of points in the unit circle at angle theta and distance r, 
	#######	centre of gravity in red, angles projected onto circumfrence

polar.plot = function(angles.v, lengths.v, r = 1.0, crosshairs = TRUE, circle = TRUE, c.o.g = TRUE, shadows = TRUE, ...) {  	# a polar plot of points placed within a circle
	limit <- r*1.2													# input angles in radians
	plot.new()
	plot.window(xlim = c(-limit, limit), ylim = c(-limit, limit), asp = 1, ...)
	if(crosshairs == TRUE) draw.crosshairs(, ...)
	if(circle == TRUE) draw.circ(r = r)
	points(lengths.v*sin(angles.v), lengths.v*cos(angles.v), ...)
	if(c.o.g == TRUE) points(mean(lengths.v*sin(angles.v)), mean(lengths.v*cos(angles.v)), pch = 18, col = "red")
	if(shadows == TRUE) arrows(r*sin(angles.v), r*cos(angles.v), r*1.05*sin(angles.v), r*1.05*cos(angles.v), length = 0)
	}



readascii = function (input.file, na.strings = "-9999", nrow = 360, ncol = 720, ...) {		# read ArcInfo ascii grid file into a matrix
	output.matrix <- matrix(data = scan(input.file, nmax = (nrow * ncol), skip = 6), byrow = TRUE, ...) 
	}



######################################################################
########   Examples ##################################################

test.counts <- c(4.2, 3.2, 2.2, 2.4, 1.7, 2.4, 2.8, 3.8, 4.0, 5.5, 5.1, 5.0)  # from Aguilar 2002, Geogr. Environ. Modelling 6:1, 5-25
labels.v 	<- c("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D") 


par(mfrow = c(2,2), pty = "s", mar = c(4.5,4,3,2))     # square plotting region

#Example 1
circ.stats(test.counts)
circular.hist4(test.counts, col = "grey")
circular.labels(labels.v, r = max(test.counts)*0.2)
draw.crosshairs(xmax = max(test.counts))

#Example 2
circular.hist1(test.counts, col = "gray", lwd = 5)
draw.circ(r = mean(test.counts), col = "blue")		# draws blue circle of radius equal to mean of counts (=uniform expectation)				
draw.vector(test.counts)					# returns centre of gravity as an x and y value, used in the next function

#Example 3
test.points <- groups2points(test.counts, factor = 2, pointplot = FALSE)
point.plot(test.points)

#Example 4 :  a colour wheel where angle = hue and radius = saturation
plot.new()
plot.window(xlim = c(-1.2, 1.2), ylim = c(-1.2, 1.2), asp = 1)
for (z in 20:1) {								# z = number of saturation intervals
	draw.rainbow.hist4(rep(z/20,360), s = (z-0.8)/20)		# draw colour wheel with 360 hue gradations, saturation decreases with radius
}
circular.labels(labels.v, r = 1.2) 
lines(c(0,1), c(0,0)) 							# scale bar
lines(c(0,0), c(0.03, -0.03))   						# 1st tic
lines(c(0.5,0.5), c(0.03, -0.03))   					# 2nd tic
lines(c(1,1), c(0.03, -0.03))   						# 3rd tic
text(c(0, 0.5, 1), c(0.12, 0.12, 0.12), c("0", "0.5", "1.0"), cex = 0.8)
text(0, -0.12, expression(infinity), cex = 1.1)			# infinity symbol, scaled up
text(c(0.5, 1), c(-0.12, -0.12), c("67.5", "0"), cex = 0.8)		# numeric labels for lower scale-bar, scaled down
text(c(0.5, 0.5), c(0.27, -0.27), c("r", "s"), font = 3)		# text labels for both scale bars

#	r.angle <- asin(X/r.length)
  # 	    if (Y<0) r.angle <-  sign(X) * (pi - abs(r.angle))  # Want angle clockwise from North
   #	    if (r.angle<0) r.angle <- r.angle + (2*pi)  


# useful debugging commands
# traceback()
# debug(function_name)
# undebug(...)
