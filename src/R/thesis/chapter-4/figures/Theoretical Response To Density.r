# this file contains the code to generate the theoretical response graphs we use.

output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/04-sense-scale/resources/figures"


x<-seq(from=0, to=100, by=1)
y.dilution<-(100-x)
y.ideal<-rep(50, length(x))
y.concentration<-(x)


quartz(width=15, height=5)


filename<-sprintf("%s/theoretical-response-to-density.pdf", output.figures)
cat("Writing graph to ", filename, "\n")
pdf(file=filename, width=15, height=5, bg="white")
	par(mfrow=c(1, 3))
	plotLine(x, y.concentration, expression("a) Resource Concentration"), xlab=F, ylab=T)
	plotLine(x, y.ideal, expression("b) Ideal Free Distribution"), xlab=T, ylab=F)
	plotLine(x, y.dilution, expression("c) Resource Dilution"), xlab=F, ylab=F)
dev.off()

plotLine<-function(x, y, title, xlab=T, ylab=T) {
	par(mar=c(5, 5, 5, 5))	
	plot(y~x, ann=F, pch=NA, axes=F)
	box()
	lines(y~x, lwd=4, col="darkgrey")
	title(main=substitute(a, list(a=title)), cex.main=2.5)
	if (xlab) {
		mtext(side=1, line=3, "Resource Density", cex=2)
	}
	if (ylab) {
		mtext(side=2, line=3, "Eggs Per Plant", cex=2)
	}
}