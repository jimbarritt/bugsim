
#plots a nice demonstration of the normal distribution density function for sd = 40

sd<-100
density<-density(rnorm(2000000,0,sd))

#Use this instead!!
x<-seq(from=-180, to=180,by=1)
y<-dnorm(x, 0,90)
plot(y~x, type="l")

quartz(width=6, height=5)
outputDir<-"/Users/Jim/Work/Projects/bugsim/documentation/presentations/Ecology Accross The Tasman 2006/images"
outputFilename<-sprintf("%s/Normal Distribution sd-%d - no annotation.pdf", outputDir, sd)
pdf(file=outputFilename, width=4, height=4, bg="white")
par(mar=c(5, 6, 3, 3))
par(mar=c(2, 2, 2, 2))
xlab<-expression(theta * " (degrees)")
title<-sprintf("Angle of turn probability density", sd)
plot(density, xlim=c(-180, 180), xaxt="n", yaxt="n", main=title, ann=FALSE,ylab="Probability", xlab=xlab, cex=1.2, axes=FALSE,cex.main = 1, cex.axis=2, cex.lab=1.5, lwd=2, cex.main=1)
axis(side=1, at=c(-180, -90, 0, 90, 180), labels=c("-180", "-90", "0", "+90", "+180"), cex.axis=1.2)

maxY<-max(density$y)
lty<-2
col="#333333"
baseY<-maxY*.2 #.004
incrY<-maxY*.05
lines(c(-sd, -sd), c(0, maxY), lty=lty, col=col)
lines(c(sd, sd), c(0, maxY), lty=lty, col=col)
lines(c(-sd, sd), c(baseY, baseY))
lines(c(-sd, -sd), c(baseY-incrY, baseY+incrY))
lines(c(sd, sd), c(baseY-incrY, baseY+incrY))
text(x=0, y=baseY-incrY, sprintf("sd (%d)", sd), adj=c(0.5, 0.5))
text(x=0, y=baseY+incrY, "A", adj=c(0.5, 0.5), font=2, cex=1.4)
dev.off()

### R-code about qqplot and density()


##########
# QQplot #
##########

# This library is needed to draw 1-dimensional scatterplots on the 
# qq-plot. You first need to install it, by going to the 'Packages', 
# then choose 'Install package(s) from CRAN...', and then choose the 
# package Hmisc
library(Hmisc)

# Draw random sample of size 50 from standard normal distribution
sample <- rnorm(50,0,1)

# Open postscript environment, so that plot is automatically saved
# as postscript file
pdf("QQplot.ps",horizontal=FALSE)

# QQplot looks like a line with intercept 0 and slope 1
qqnorm(sample,ylim=c(-5,5),xlim=c(-4,4))
qqline(sample)
scat1d(sample,side=2,lwd=2)
scat1d(qnorm(seq(from=.5/50,to=49.5/50,by=1/50)),side=1,lwd=2)
abline(v=0,lty=2)
abline(h=0,lty=2)

# Shift sample to the right by 1 -> qqplot shifts up by 1
qqnorm(sample+1,ylim=c(-5,5),xlim=c(-4,4))
qqline(sample+1)
scat1d(sample,side=2,lwd=2)
scat1d(sample+1,side=4,lwd=2,col="red")
scat1d(qnorm(seq(from=.5/50,to=49.5/50,by=1/50)),side=1,lwd=2)
abline(v=0,lty=2)
abline(h=0,lty=2)

# Change scale of sample (multiply all values by 2) -> slope of qqplot is 2
qqnorm(2*sample,ylim=c(-5,5),xlim=c(-4,4))
qqline(2*sample)
scat1d(sample,side=2,lwd=2)
scat1d(2*sample,side=4,lwd=2,col="red")
scat1d(qnorm(seq(from=.5/50,to=49.5/50,by=1/50)),side=1,lwd=2)
abline(v=0,lty=2)
abline(h=0,lty=2)

# Change smallest value of sample to -5
sample.adj <- sort(sample)
sample.adj[1]<- -5
qqnorm(sample.adj,ylim=c(-5,5),xlim=c(-4,4))
qqline(sample.adj)
scat1d(sample,side=2,lwd=2)
scat1d(sample.adj,side=4,lwd=2,col="red")
scat1d(qnorm(seq(from=.5/50,to=49.5/50,by=1/50)),side=1,lwd=2)
abline(v=0,lty=2)
abline(h=0,lty=2)

# Close postscript environment, and automatically save the plot
dev.off()

#############
# density() #
#############

# You can use the command 'density()' to plot a density estimate of 
# your sampple. The density estimate is a smoothed out version of the 
# histogram. 

# Draw random sample of size 500 from a t-distribution with 5 degrees
# of freedom
x <- rt(500,5)

# Open postscript environment
pdf("Density.pdf",horizontal=FALSE)

# Draw histogram of x
# ylim=c(a,b) means that the y-axis ranges from a to b
hist(x,probability=TRUE,ylim=c(0,0.4))

# Add density estimate of x
lines(density(x))

# Draw density estimate of x in a new plot (use 'plot' instead of 'lines')
plot(density(x),ylim=c(0,0.4))

# Compare with density of a standard normal distribution:
# create grid
grid <- seq(from=-4, to=4, by=.1)
lines(grid, dnorm(grid), lty=2)
legend(-6,.4,c("sample","normal"),lty=c(1,2))

# Some explanation: 'dnorm' stands for 'density normal'. 
# dnorm(a) gives the value of the normal density in the point a.
# 'lty' stands for 'line type', lty=2 gives a dashed line.

# Close postscript environment
dev.off()
