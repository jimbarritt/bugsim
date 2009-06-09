#This file contains everything you need to produce the results for Trial B of chapter 03.
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

lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrB1ReportClass.r")
lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrB1-E1ReportClass.r")


#Performs an analytical set of estimating MSD so can see what k and L do to it...
report<-SimExp3B1E1Report()
report<-generateReport(report, TRUE, TRUE)


# Gives us a comparison of various parameters plotted, johnson-like
# Small move lengths
experimentPlan<-ExperimentData.loadPlan("3", "sim", "exp3", "TrB1-S1", 1)
report<-SimExp3B1Report(experimentPlan, genJohnson=T, genAOT=T)
report<-generateReport(report, TRUE, TRUE)


#Large move lengths
experimentPlan<-ExperimentData.loadPlan("3", "sim", "exp3", "TrB1-S2", 1)
report<-SimExp3B1Report(experimentPlan, genJohnson=F, genAOT=F)
report<-generateReport(report, TRUE, TRUE)




################################################################################
#
#
# Analytical report:
#
#
quartz(width=10, height=10)

str(circular.kappaTable.df)

rowNums<-c(2, 15, 40, 115)
graphIds<-c("a", "b", "c", "d")

results<-md.generateSelectedEMSD(rowNums, graphIds, c(1, 1, 1, 1))



#####################################################################################
#
#
# OLD STUFF:
#

colnames(itr@parameters.df)

itr.num<-10
itr<-getIteration(experimentPlan, itr.num)

dispersalStats<-DispersalStatistics(experimentPlan, itr.num)

dispersalStats<-DispersalStatistics(experimentPlan, itr.num)
d.df<-dispersalStats@summary.df

rho<-.24250 #k=.5
rho<-.37108 #k=.8
plot(log(d.df$MEAN)~log(d.df$AGE))
x<-1:max(df$AGE)
ySkellamJones<-sapply(x, md.E, rho, 50, "skellam-jones")
lines(log(ySkellamJones)~log(x), col="green")




############################
# OLD STUFF


#Tries out some parameters to find ones that look like johnsons results
experimentPlan<-ExperimentData.loadPlan("raw-download", "sim", "exp3", "TrB1-J", 1)


experimentPlan@iterationSummary.df



# To get the comparison to johnson 1992...
itr.num<-5
itr<-getIteration(experimentPlan, itr.num)
par(mfrow=c(1,1))
dispersalStats<-DispersalStatistics(experimentPlan, itr.num)
results<-md.simplePlot(dispersalStats,iteration=itr.num, sprintf("(L=%0.1f, A=%0.1f)",itr@moveLength, itr@angleOfTurn) , ageLimit=itr@ageLimit, ageSplit=7, ageSplitUpper=399)


aotIterations<-c(1, 5, 9, 13)
aotIterations<-c(1, 2, 3)
lengthIterations<-c(13, 14, 15, 16)
lengthIterations<-c(9,10,11,12)
lengthIterations<-c(5, 6, 7, 8)
lengthIterations<-c(1, 2, 3, 4)

quartz(width =10, height=10)
par(mfrow=c(2,2))
for (itr.num in aotIterations) {
	itr<-getIteration(experimentPlan, itr.num)
	dispersalStats<-DispersalStatistics(experimentPlan, itr.num)
	results<-md.simplePlot(dispersalStats,iteration=itr.num, sprintf("(L=%0.1f, A=%0.1f)",itr@moveLength, itr@angleOfTurn) , ageLimit=itr@ageLimit, ageSplit=7, ageSplitUpper=399)
}

quartz(width =10, height=10)
#ageSplits<-c(10, 10, 50, 7)
ageSplits<-rep(7, 4)
i<-1
par(mfrow=c(2,2))
for (itr.num in lengthIterations) {
	itr<-getIteration(experimentPlan, itr.num)
	dispersalStats<-DispersalStatistics(experimentPlan, itr.num)
	model<-md.simplePlot(dispersalStats,iteration=itr.num, sprintf("(L=%0.1f, A=%0.1f)",itr@moveLength, itr@angleOfTurn) , ageLimit=itr@ageLimit, stats=F, ageSplit=ageSplits[i])
	i<-i+1
}


# This is for export to bugsim...
#formatTimeIntervals(dispersalStats)

#undebug(md.plotLogFactorsWithRegression)
#undebug(md.simplePlot)
#traceback()
#quartz(width =10, height=10)

