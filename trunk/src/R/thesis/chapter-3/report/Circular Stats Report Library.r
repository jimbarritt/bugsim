cat("Circular Statistics Report Library (Version 1.0)\n")

CircularReport.plot.distribution<-function(x.radians, maxPlotN) {
	x.radians<-x.radians+(0.5*pi)# Rotate everything by 90 degrees to get 0 at the top
	stats<-circ.summary(x.radians)
	
	
	par(mar=c(0, 0, 0,0))
	if (length(x.radians)>maxPlotN) {
		x<-sample(x.radians, maxPlotN)
	} else {
		x<-x.radians
	}
	circ.plot(x, stack=TRUE, bins=360, shrink=3, pch=16, cex=.9)

	theta<-stats$mean.dir
	rho<-stats$rho

	#Center
	rect(-.1, -.1, .1, .1, col="white", border=NA)
	
	#left
	rect(-.92, -.1, -.6, .1, col="white", border=NA)
	
	#Right
	rect(.9, -.1, .6, .1, col="white", border=NA)
	
	#top
	rect(-.15, .92, .15, .6, col="white", border=NA)
	
	#Bottom
	rect(-.15, -.91, .15, -.6, col="white", border=NA)

	indent<-0.75
	if (rho<.8) {
		text(0,indent, "0", cex=1.5)
	}
	
	text(-indent, 0, "-90", cex=1.5)
	text(indent, 0, "+90", cex=1.5)
	text(0,-indent , "180",cex=1.5)

	x<-cos(theta)*rho
	y<-sin(theta)*rho
	lines(c(-.1, .1),c(0, 0), lwd=1.5, col="black")
	lines(c(0, 0),c(-.1, .1), lwd=1.5, col="black")
	
	lines(c(0, 0), c(x, y), col="darkgrey", lwd=4)
	
	points(round(x,0),y, pch=16,col="darkgrey", cex=3) #round this because it seems to jump about a bit!
	points(round(x,0),y, pch=16,col="lightgrey", cex=2) #round this because it seems to jump about a bit!
	
	return (stats)

}


