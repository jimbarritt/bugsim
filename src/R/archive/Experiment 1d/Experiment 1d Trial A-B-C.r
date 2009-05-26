source("~/Work/code/bugsim/resource/R/Experiment 1d/Experiment 1d Library.r", echo=FALSE)

experimentNumber<-3
trialId<-"TrC"
baseFilename<-"match-exp01d"
experimentDir<-createDirectoryName(basefilename, experimentNumber, trialId)

matchstickIntersectionFilename<-sprintf("%s/%s.csv", experimentDir, sprintf("%03d-matchstick-intersections", experimentNumber))

mis.df<-read.csv(file=matchstickIntersectionFilename)
str(mis.df)
#Plot 3D wireframe to get overview:
y<-mis.df$IntersectionD
x<-mis.df$Heading
iF<-factor(mis.df$Iteration)
xF<-factor(mis.df$Heading)
levels(xF)
plot.mx<-matrix(nrow=length(levels(iF)), ncol=length(levels(xF)), dimnames=list(levels(iF), levels(xF)))
str(plot.mx)
iLevel<-1
for (iLevel in levels(iF)) {
	mis.itr.df<-subset(mis.df, mis.df$Iteration==iLevel)
	h<-mis.itr.df$Heading
	d<-mis.itr.df$IntersectionD
	for (i in 1:length(h)) {
		mx.i<-indexOfFactorValue(xF, h[[i]])
		plot.mx[as.numeric(iLevel),mx.i]<-d[[i]]
	}
}

quartz(width=10, height=10)
trellis.par.set(theme = col.whitebg())
wireframe(plot.mx, scales = list(arrows = FALSE),shade=TRUE, aspect = c(1, 1), screen=list(z=0, x=-90, y=30),main="L vs D and h", xlab="Drop Distance", ylab="h", zlab="Length I")

#This isnt as good as wireframe
persp(plot.mx, ticktype="detailed", scale=TRUE)


#line plot of intersection lengths with a fitted curve against heading
quartz(width=16, height=8)
par(mfrow=c(1, 2))

focalItr<-11
iteration.df<-subset(mis.df, mis.df$Iteration==focalItr)
#iteration.df<-mis.df

y<-iteration.df$IntersectionD
x<-iteration.df$Heading
yx.quad.lm<-lm(y~x+I(x^2))
summary(yx.quad.lm)
AIC(yx.quad.lm)
y.quad.fitted<-fitted(yx.quad.lm)

plot(y~x)
#lines(y.quad.fitted~x, col="grey")
#lines(y~x)
#lines(x=c(0, max(x)), y=c(0.5, 0.5))


focalItr<-1
iteration.df<-subset(mis.df, mis.df$Iteration==focalItr)

#Frequency of intersection lengths:
mis.total.hist<-hist(mis.df$IntersectionD,freq=TRUE, br=20:20,plot=TRUE, main="Deterministic Step Lengths")

prop.count<-mis.total.hist$counts/(180*21)
plot(mis.total.hist$counts~mis.total.hist$mids, ylim=c(0, 160))
lines(mis.total.hist$counts~mis.total.hist$mids)

result.hist<-hist(iteration.df$IntersectionD,freq=TRUE, br=40:40, plot=TRUE)
crossings.hist<-result.hist

x<-crossings.hist$mids
y<-crossings.hist$counts
yx.quad.lm<-lm(y~x+I(exp(x)))
summary(yx.quad.lm)
AIC(yx.quad.lm)
y.quad.fitted<-fitted(yx.quad.lm)

quartz(width=10, height=10)
plot(y~x, pch=NA, ylim=c(0, max(crossings.hist$counts)), xlab="Intersection D", ylab="Frequency")
lines(y.quad.fitted~x, col="grey")
lines(y~x, pch=19, cex=.75, col="blue", lty=2)
points(y~x, pch=19, cex=.75, col="blue")





#Plot 3D wireframe of frequencies
y<-mis.df$IntersectionD
x<-mis.total.hist$mids
iF<-factor(mis.df$Iteration)
xF<-factor(mis.total.hist$mids)
levels(xF)
plot.mx<-matrix(nrow=length(levels(iF)), ncol=length(levels(xF)), dimnames=list(levels(iF), levels(xF)))
str(plot.mx)
iLevel<-2
for (iLevel in levels(iF)) {
	mis.itr.df<-subset(mis.df, mis.df$Iteration==iLevel)
	mis.itr.hist<-hist(mis.itr.df$IntersectionD,freq=TRUE, breaks=mis.total.hist$breaks, plot=FALSE)
	mids<-mis.itr.hist$mids
	counts<-mis.itr.hist$counts
	for (i in 1:length(mids)) {
		mx.i<-indexOfFactorValue(xF, mids[[i]])
		plot.mx[as.numeric(iLevel),mx.i]<-counts[[i]]
	}
}


#par(mfrow=c(1, 2))
trellis.par.set(theme = col.whitebg())
tickMarksX<-c(1, 6, 11, 16, 21)
tickMarksX<-as.numeric(levels(iF))
plot.scales<-list(arrows=FALSE,x = list(at=tickMarksX, labels=21-tickMarksX), y=list(draw=FALSE))

#Nice side on view showing drop distance
quartz(width=10, height=10)
viewLocation<-list(z=0, x=-90, y=-0)
wireframe(plot.mx,drape=TRUE, aspect = c(1, 1), screen=viewLocation,scales=plot.scales,main="L vs D and h", xlab="Drop Distance", ylab="", zlab="Freq")

#BEST VIEW : Shows lower parts well
quartz(width=10, height=10)
viewLocation<-list(z=0, x=-90, y=-90)
tickMarksY<-seq(from=0, to=max(as.numeric(levels(xF))*10), by=20)
plot.scales<-list(arrows=FALSE,x = list(draw=FALSE,at=tickMarksX, labels=21-tickMarksX), y=list(at=tickMarksY, labels=tickMarksY/5))
wireframe(plot.mx,drape=TRUE, aspect = c(1, 1), screen=viewLocation,scales=plot.scales,main="L vs D and h", xlab="", ylab="Length", zlab="Freq")

plot.mx[21,]

test.mx<-matrix(nrow=2, ncol=length(plot.mx[1,]))
test.mx[1,]<-plot.mx[15,]
test.mx[2,]<-plot.mx[16,]
#test.mx[3,]<-plot.mx[17,]
viewLocation<-list(z=0, x=-90, y=-70)
wireframe(test.mx,drape=TRUE, aspect = c(1, 1), screen=viewLocation,scales=plot.scales,main="L vs D and h", xlab="Drop Distance", ylab="Length", zlab="Freq")


#Deterministic calculation of L for a straight line:

theta=seq(from=90,to=269, by=1) #90 is same as 270 so don't need to include 270
length(theta) # should be 180
dropD<-20 #how far away do we drop the match
M<-40 #length of matchstick
L<-(M/2)-(dropD/sin(toRadians(theta-90)))
plot(L~theta)

O<-20
theta<-45
H<-O/sin(toRadians(theta))
A<-H*cos(toRadians(theta))

Htest<-sqrt(A*A+O*O)
cat("Hypotenuse should =", H, ", recalc=", Htest)

theta.x=seq(from=90,to=269, by=1) #90 is same as 270 so don't need to include 270

dropD<-5
M<-40
i<-1
L<-vector(length=length(theta.x))
for (theta in theta.x) {
	L[i]<-calculateL(theta-90, dropD, M/2)
	i<-i+1
}

plot(L~theta.x)

calculateL<-function(theta, dropD, M) {
	H<-dropD/sin(toRadians(theta))
	if (H>M) {
		return (NA)
	} else {
		return (M-H)
	}
}