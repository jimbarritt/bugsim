source("~/Work/code/bugsim/resource/R/Bugsim R Library.r", echo=FALSE)
library(lattice)

cat("Experiment 1d Library v(1.0)")



plotXY<-function(x, y, title, ylim=c(0,20)) {
	thetaStart<-90
	thetaEnd<-270
	plot(y~x, xlim=c(thetaStart, thetaEnd), ylim=ylim, axes=FALSE,pch=NA, lty=1, main=title, xlab="heading", ylab="point of intersection")
	axis(1, at=seq(from=thetaStart, to=thetaEnd, by=45))
	axis(2, at=seq(from=0, to=20, by=5))
	lines(y~x, lty=2,col="blue")
	points(y~x, col="blue", pch=19, cex=0.75)
}
