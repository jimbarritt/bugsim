###########################################################################################
#
# Trial   : A2
#
# Purpose : To test the Von mises Azimuth generator - going to sample the angles of turn and
#           See that they conform to the Von Mises Distribution
#
library(CircStats)

source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("/thesis/common/circular/CircStats Extensions.r")

lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")
lib.loadLibrary("/thesis/chapter-3/model/ForagerSummaryClass.r")
lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")

lib.loadLibrary("/common/latex/Latex Library.r")
lib.loadLibrary("/thesis/common/output/StandardReportClass.r")
lib.loadLibrary("/thesis/common/probability/Probability Library.r")
lib.loadLibrary("/thesis/chapter-3/report/Circular Stats Report Library.r")
lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrA2ReportClass.r")

experimentPlan<-ExperimentData.loadPlan("3", "sim", "exp3", "TrA2-G", 1)

report<-SimExp3A2Report(experimentPlan, maxKSN=2000, circSampleN=2000, genCircular=T, genLinear=T, genGraphs=T)#3000, 2000
report<-generateReport(report, TRUE, TRUE)


experimentPlan<-ExperimentData.loadPlan("3", "sim", "exp3", "TrA2-VM1", 1)

report<-SimExp3A2Report(experimentPlan, maxKSN=2000, circSampleN=2000, genCircular=T, genLinear=T, genGraphs=T)#3000, 2000
report<-generateReport(report, TRUE, TRUE)


experimentPlan<-ExperimentData.loadPlan("3", "sim", "exp3", "TrA2-VM2", 1)

report<-SimExp3A2Report(experimentPlan, maxKSN=2000, circSampleN=2000, genCircular=F, genLinear=F, genGraphs=T)#3000, 2000
report<-generateReport(report, TRUE, TRUE)











################################################################################################################
# Messing about ....
length(report@foragerSummaries)


experimentPlan@iterations[[4]]@replicates[[1]]
report@foragerSummaries[[4]]

summary.df<-IterationReplicate.readReplicatesDataFrame(experimentPlan, 1)



result<-SimExp3A2Report.ks.rvm(report@foragerSummaries[[1]], 1000)


# Perform a two-sample test of homogeneity on two
 # simulated data sets. 
data1 <- rvm(1000, 0, 3) 
data2 <- rvm(100, pi, 2) 
data3 <- rvm(100, 0, 3) 
watson.two(data1, data2, alpha=0.01, plot=TRUE) 
watson.two(data1, data3, alpha=0.01, plot=TRUE) 


################################################################################################################
#KS Test:
maxN<-1000
foragerSummary<-report@foragerSummaries[[4]]
aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn	
aot.radians<-toRadians(aot)

mean(cos(aot.radians))

if (foragerSummary@iteration@azimuthGenerator=="vonMises") {
	k<-foragerSummary@iteration@angleOfTurn
} else {
	k<-est.kappa(aot.radians)	
}

obs.N<-length(aot)
vm.radians<-rvm(obs.N, mean=0, k=k)
vm.radians<-sapply(vm.radians, Geometry.toAngleOfTurnRadians)


#Do the KS test with a maxN of the samples - because any more than about 7000
#results in lots of "ties" i.e. same value more than once which the ks test doesnt like.
#  	
if (obs.N>maxN) {
	obs.sample<-sample(aot.radians, maxN)
	exp.sample<-sample(vm.radians, maxN)
} else {
	obs.sample<-aot.radians
	exp.sample<-vm.radians
}


cat("Sampling ", length(obs.sample), " observations from " , obs.N, "for kolmogrov-smirnov test\n")
	
ks.result<-ks.test(obs.sample, exp.sample, exact=TRUE)	

str(ks.result)

#Converts 0-360 into +/- 180
convertToAngleOfTurn<-function(degrees) {
	aot<-degrees
	if (degrees>180) {
		aot<-(degrees-360)
	}
	return (aot)
}

350-360

























################################################################################################################
# CHISQ TEST:

foragerSummary<-report@foragerSummaries[[1]]
aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn	
if (foragerSummary@iteration@azimuthGenerator=="vonMises") {
	k<-foragerSummary@iteration@angleOfTurn
} else {
	k<-est.kappa(toRadians(aot))	
}

mu<-0

breaks<-seq(from=-180, to=180, by=10)
minAot<-min(aot)
maxAot<-max(aot)
if (minAot<(-180) || maxAot>180) {
	cat("WARNING: Having to increase range of breaks because: min=" ,  minAot, ", max=", maxAot, "\n")
	breaks<-seq(from=-190, to=190, by=breakSize)
} 
	
aot.hist<-hist(aot, breaks=breaks, plot=FALSE)
	
N<-length(aot)
NCOUNTS<-length(aot.hist$counts)
actualCounts<-aot.hist$counts
mids<-aot.hist$mids

pdvm<-dvm.degrees(mids, mu, k, breakSize, N)
expectedCounts<-prob.createDistribution(pdvm)*N

chisq.mx<-rbind(actualCounts, expectedCounts)
chisq.result<-chisq.test(chisq.mx)
str(chisq.result)
sum(chisq.result$expected)




###########################################################################################
#
# WORKSPACE: 
#

itr.1<-getIteration(experimentPlan, 6)
rep.1<-getReplicate(itr.1, 1)

foragerSummary<-ForagerSummary(rep.1, itr.1, 1)


foragerSummary@summary.df

lifeHistory.df<-subset(foragerSummary@lifehistory.df, foragerSummary@lifehistory.df$Age>0)
str(lifeHistory.df)
min(lifeHistory.df$LastAngleOfTurn)
max(lifeHistory.df$LastAngleOfTurn)



aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn
min(aot)
max(aot)
boxplot(aot)
mean(aot)
hist(aot, xlim=c(-180, 180))


stats<-CircularReport.plot.distribution(toRadians(aot), 2000)

theta<-stats$mean.dir
rho<-stats$rho
x<-cos(theta)*rho
y<-sin(theta)*rho
lines(c(0, 0), c(x, y), col="blue", lwd=2)
points(round(x,0),y, pch=16,col="red", cex=2)


#Need to get this into a report somehow - maybe draw both graphs next to each other... but in the 
#summary we will want a list of  the Statistics for each iteration...
#Would be nice to be able to draw the histogram with the density line over the top but we dont know
#how to scale it.
#Draw a histogram of the angles of turn and then overlay the von mises distribution as a line...
quartz(width=10, height=10)
mu<-0
k<-itr.1@angleOfTurn
breakSize<-10
x<-seq(from=-180, to=180, by=breakSize)

hist<-hist(aot,breaks=x, freq=TRUE,xlim=c(-180, 180), axes=FALSE)
axis(1, at=seq(-180, 180, 45))
axis(2)
N<-sum(hist$counts)
pdvm<-dvm.degrees(hist$mids, mu, k, breakSize, N)
pdvm.dist<-prob.createDistribution(pdvm)*N
lines(pdvm.dist~hist$mids, col="blue")

nm<-pnorm(x, 1, 0)


ks.test(aot, "dvm.degrees", mu, k, breakSize)
ks.test(aot, "punif", 0, 360)


x1<-seq(from=-180, to=180, by=1)
vonmiseDensity<-dvm(toRadians(x1), mu, k) 
vonmisePDist<-prob.createDistribution(vonmiseDensity)
#see Crawley(2005) Statistics - an introduction using R p57 - multiply sample size by the width of the breaks
calculatedX<-vonmisePDist*10000*10 #adjust so that it fits the height of the histogram 

lines(calculatedX~x1, type="l", col="blue")

randomVM<-rvm(N, 0, k)


aotTheta<-(360+aot)%%360
xTheta<-seq(from=0, to=360, by=10)
hist<-hist(aotTheta,breaks=xTheta, freq=TRUE,xlim=c(0, 360))
hist(toDegrees(randomVM), breaks=xTheta,freq=TRUE,xlim=c(0, 360))

#kolmogrov-smirnov test of the two distributions (should be not significantly different)
ks.test(aotTheta, toDegrees(randomVM))

hist.aot<-hist(aotTheta, breaks=xTheta, freq=TRUE,xlim=c(0, 360))
hist.randomVM<-hist(toDegrees(randomVM), breaks=xTheta, freq=TRUE,xlim=c(0, 360))
str(hist.aot)

#Have to add 1 to it because of the 0's - not sure how Cain did this = maybe only took the ones that didnt have 0's ...
mx<-rbind(hist.aot$counts+1, hist.randomVM$counts+1)
chs<-chisq.test(mx)
summary(chs)

#Could also do the Bray curtis dissimilarity index on it as this allows zeros...



testChi<-rbind(c(1, 3, 4), c(6, 1, 1))
chisq.test(testChi)
testChi<-rbind(c(1, 3, 4), c(1, 3, 4))
chisq.test(testChi)


min(aot)
max(aot)
mean(aot)
