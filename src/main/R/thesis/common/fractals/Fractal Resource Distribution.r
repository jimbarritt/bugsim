# Provides the old Levy Walk to create a cabbage layout...
source("~/Work/code/bugsim/src/R/archive/probability/Fractal Resource Distribution Library.r", echo=FALSE)

#frez.output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/chapter-3/Fractal Distributions/resources/figures"
frez.output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/08-appendix/resources/figures"

dir(frez.output.figures)
quartz(width=8, height=8)

steps<-2000
minStep<-1
maxStep<-100
stepBy<-1
randAzimuth<-runif(steps)
randStep<-runif(steps)


#Generate the paths and display....
coordsLevy<-frez.generatePointPattern(type="LEVY", steps=steps, randAzimuth=randAzimuth, randStep=randStep, D=2, scale=100, minStep=minStep, maxStep=maxStep, stepBy=stepBy)
coordsBrownian<-frez.generatePointPattern(type="BROWNIAN", steps=steps, randAzimuth=randAzimuth, randStep=randStep, D=2, scale=50, minStep=minStep, maxStep=maxStep, stepBy=stepBy)
coordsUniform<-frez.generatePointPattern(type="UNIFORM", steps=steps, randAzimuth=randAzimuth, randStep=randStep, D=2, scale=50, minStep=minStep, maxStep=maxStep, stepBy=stepBy)
coordsOgataLevy<-frez.generatePointPattern(type="OGATA", steps=steps, randAzimuth=randAzimuth, randStep=randStep, D=2, scale=250, minStep=minStep, maxStep=maxStep, stepBy=stepBy)

#Generate a uniform random distribution

path<-list(c(10, 10))

2^6
D<-0
#Calculate Fractal Box Counting Dimension:
D<-bcount.calculateD(coordsOgataLevy, minK=1, maxK=6)
D

D<-2
r0<-10
frez.plotUpperVsLowerTailed(D, r0)

outputFile<-sprintf("%s/upper_vs_lower_tailed_u.pdf",frez.output.figures)
pdf(file=outputFile, width=8, height=8, bg="white")
frez.plotUpperVsLowerTailed(D, r0)
dev.off()

frez.plotUpperVsLowerTailedDivision()

outputFile<-sprintf("%s/upper_vs_lower_tailed_division.pdf",frez.output.figures)
pdf(file=outputFile, width=8, height=8, bg="white")
frez.plotUpperVsLowerTailedDivision()
dev.off()




#Test the box counting algorithm
#Area is 64x64.
#First 1000 uniform random points 
x<-runif(n=5000, min=0, max=64)
y<-runif(n=5000, min=0, max=64)
	
plot(y~x)

bcount.calculateDXY(x, y, TRUE)

#D is about 1.9 - not bad

#Now 1 point:
x<-c(10)
y<-c(10)
bcount.calculateDXYScaled(x, y, TRUE, size=64)
#D=0

#Now a line:

x<-rep(10, 65)
y<-0:64

plot(y~x)
bcount.calculateDXYScaled(x, y, TRUE, size=64)
#D=1


x<-seq(from=1, to=100, by=1)
yR<-reynolds.pstepLengthList(x, .9, 1.4)

plot(yR~x, pch=NA)
lines(yR~x)

x<--10:10
y<-dnorm(x)
plot(y~x)

#Some basic Levy Distribution stuff...
x<-seq(from=1, to=10, by=.1)
y<-pstepLength(x, 1, .5)
#plot(y~x, type="l", ylim=c(0, max(y)))

y2<-prob.createDistribution(y)
#plot(y2~x, type="l")
sum(y2)


pDist<-prob.createCumulativeDistribution(y2)
plot(pDist~x,type="l", ylim=c(0,1))

steps<-c()
for (n in 1:1000) {
	step<-prob.chooseRandomFromDist(x, pDist)
	steps<-c(steps, step)
}

hist(steps)


