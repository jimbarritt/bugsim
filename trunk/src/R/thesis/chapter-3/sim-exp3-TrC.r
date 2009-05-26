source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

library(CircStats)

lib.loadLibrary("/thesis/common/circular/CircStats Extensions.r")

lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/common/summarystats/Summary Statistics Library.r")


lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")
lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")
lib.loadLibrary("/thesis/common/movement/Mean Dispersal Library.r")
lib.loadLibrary("/thesis/common/circular/Circular Distributions Library.r")

lib.loadLibrary("/thesis/chapter-3/model/ForagerSummaryClass.r")
lib.loadLibrary("/thesis/chapter-3/model/Mean Dispersal Graph Library.r")
lib.loadLibrary("/thesis/chapter-3/model/DispersalStatisticsClass.r")

lib.loadLibrary("/common/latex/Latex Library.r")
lib.loadLibrary("/thesis/common/output/StandardReportClass.r")

lib.loadLibrary("/thesis/chapter-3/model/Calculated Layout Library.r")

lib.loadLibrary("/thesis/chapter-3/model/Resource Layout Library.r")

lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrC1ReportClass.r")


experimentPlan.ch3.C1<-ExperimentData.loadPlan("3", "sim", "exp3", "TrC1", 1)


#Shows us some summary responses for the L and A parameters
report.ch3.C1<-SimExp3C1Report(experimentPlan.ch3.C1)
report.ch3.C1<-generateReport(report.ch3.C1, TRUE, TRUE)

md.plotExpectedMSD(rho=.3, expectedL=10, "title", plot=T)


#####################################################################
# OLD
iterationNumbers<-c(1,2,3)
foragerIndexes<-c(16, 10, 10)


itr<-getIteration(experimentPlan, iterationNumber)
azimuths.1<-getAzimuthsForForager(itr, foragerIndex)

steps<-20
size<-120

azimuths.1<-azimuths.1
graphIds<-c("a", "b", "c")
par(mfrow=c(3, 3))

radius<-3
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=25", graphIds[1], radius), size, 1, patchSize=25, resourceRadius=radius, drawCentre=T,drawScale=T)
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=50", graphIds[2], radius), size, 1, patchSize=50, resourceRadius=radius, drawCentre=T,drawScale=F)
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=120", graphIds[3], radius), size, 1, patchSize=120, resourceRadius=radius, drawCentre=T,drawScale=F)

radius<-5
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=25", graphIds[1], radius), size, 1, patchSize=25, resourceRadius=radius, drawCentre=T,drawScale=F)
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=50", graphIds[2], radius), size, 1, patchSize=50, resourceRadius=radius, drawCentre=T,drawScale=F)
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=120", graphIds[3], radius), size, 1, patchSize=120, resourceRadius=radius, drawCentre=T,drawScale=F)

radius=15
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=25", graphIds[1], radius), size, 1, patchSize=25, resourceRadius=radius, drawCentre=T,drawScale=F)
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=50", graphIds[2], radius), size, 1, patchSize=50, resourceRadius=radius, drawCentre=T,drawScale=F)
plotLTrail(azimuths.1, steps, 5, sprintf("%s) R=%d, P=120", graphIds[3], radius), size, 1, patchSize=120, resourceRadius=radius, drawCentre=T,drawScale=F)


#addTrail(azimuths.2, steps, 5, col="green")
#addTrail(azimuths.3, steps, 5, col="red")


quartz(width=10, height=10)
quartz(width=9, height=2.9)

itr<-getIteration(experimentPlan, 2)
rep<-getReplicate(itr, 1)
foragerSummary<-ForagerSummary(rep, itr, 1)		
lh.df<-foragerSummary@lifehistory.df

foragerIndex<-10
id.factor<-as.factor(lh.df$Id)
foragerId<-levels(id.factor)[foragerIndex]
f.df<-subset(lh.df, lh.df$Id==foragerId)

azimuths<-f.df$Azimuth

resources.df<-Resourcelayout.createLayout(50, 4, midX-25, midY-25)

par(mfrow=c(1, 3))

steps<-20
size<-120
plotLTrail(azimuths, steps, 1, "a) L=1", size, 1,drawScale=T)
plotLTrail(azimuths, steps, 5, "b) L=5", size, 1, drawScale=T)
plotLTrail(azimuths, steps, 10, "c) L=10", size, 1, drawScale=T)







###############################################################################################
## OLD STUFF
##
itr<-getIteration(experimentPlan, 1)
rep<-getReplicate(itr, 1)
foragerSummary<-ForagerSummary(rep, itr, 1)		
lh.1.df<-foragerSummary@lifehistory.df
itr<-getIteration(experimentPlan, 2)
rep<-getReplicate(itr, 1)
foragerSummary<-ForagerSummary(rep, itr, 1)		
lh.2.df<-foragerSummary@lifehistory.df
itr<-getIteration(experimentPlan, 3)
rep<-getReplicate(itr, 1)
foragerSummary<-ForagerSummary(rep, itr, 1)		
lh.3.df<-foragerSummary@lifehistory.df


quartz(width=10, height=10)

quartz(width=9, height=6)

par(mfrow=c(2, 3))

circular.vm.plotDensityFunctionRadians(10, "a)")
circular.vm.plotDensityFunctionRadians(3, "b)")
circular.vm.plotDensityFunctionRadians(0.5, "c)")

ForagerSummary.plotForagerTrail(lh.1.df,1,  120,"topright", itr@moveLength, 5, drawScale=T, drawBox=F)
ForagerSummary.plotForagerTrail(lh.2.df,5,  120,"topright", itr@moveLength, 5, drawScale=F, drawBox=F)
ForagerSummary.plotForagerTrail(lh.3.df,12,  120,"topright", itr@moveLength, 5, drawScale=F, drawBox=F)





par(mfrow=c(4, 4))

for(forager in 1:16) {
	ForagerSummary.plotForagerTrail(lh.1.df,forager,  220,"topright", itr@moveLength, 5, drawScale=(forager==1))
}

for(forager in 1:16) {
	ForagerSummary.plotForagerTrail(lh.2.df,forager,  220,"topright", itr@moveLength, 5, drawScale=(forager==1))
}

for(forager in 1:16) {
	ForagerSummary.plotForagerTrail(lh.3.df,forager,  220,"topright", itr@moveLength, 5, drawScale=(forager==1))
}

par(mfrow=c(1, 1))






################################################################################################
#PRACTICE
plotWidth<-200

	id.factor<-as.factor(lh.df$Id)
	
	foragerId<-levels(id.factor)[foragerIndex]
	f.df<-subset(lh.df, lh.df$Id==foragerId)
	
	xlim<-range(f.df$X)
	ylim<-range(f.df$Y)
	
	lines(xlim, c(min(ylim), min(ylim)))
	lines(xlim, c(max(ylim), max(ylim)))
	
	# Try to make all plots the same width
	midX<-as.integer(min(xlim)+ ((max(xlim)-min(xlim)) /2) )
	midY<-as.integer(min(ylim)+ ((max(ylim)-min(ylim)) /2) )
		
	xlim<-c(as.integer(midX-plotWidth/2), as.integer(midX+plotWidth/2))
	ylim<-c(as.integer(midY-plotWidth/2), as.integer(midY+plotWidth/2))

	par(mar=c(2, 2, 2, 2))
	plot(f.df$Y~f.df$X, type="l", ann=F, axes=F, lwd=1.5, col="blue",xlim=xlim, ylim=ylim)
#	points(f.df$Y~f.df$X, pch=16, cex=.5, col="blue")


	
	if (scalePos=="topright" && drawScale) {
		xs<-xlim[2]-(xlim[2]*.1)
		ys<-ylim[2]-(ylim[2]*.1)
		halfL<-((L*scaleL)/2)
		d<-halfL*2
	#	rect(xs-d, ys-d, xs+d, ys+d, col="white", border=NA)
		text(xs, ys, pos=1,sprintf("%0.0fL", scaleL),cex=1.5)

		lines(c(xs-halfL,xs+halfL), c(ys, ys), lwd=2, col="blue")
#		points(c(xs-halfL,xs+halfL), c(ys, ys), pch=16, cex=.5, col="blue")
	}
	box()
}











######################################################################################################
#
# OLD STUFF

quartz(width=10, height=10)

par(mfrow=c(1,1))
circular.plotAngularDeviationAsK()


Ls<-c(1, 10, 100, 250)
md.plotMSDWithKMultipleL(Ls, 10)
md.plotMSDWithKMultipleL(Ls, 100)
md.plotMSDWithKMultipleL(Ls, 1000)
md.plotMSDWithKMultipleL(Ls, 5000)



i<-2
expectedK<-circular.kappaTable.df$k[i]
rho<-circular.kappaTable.df$rho[i]
s<-circular.kappaTable.df$s[i]

sCain.rad<-sqrt(2*(1-rho))
sCain.deg<-toDegrees(sCain.rad)



