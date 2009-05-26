source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("/common/Graphing Utilities Library.r")
lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/common/summarystats/Summary Statistics Library.r")
lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")
lib.loadLibrary("/thesis/chapter-4/model/EggDistributionClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbagesClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbageLayoutClass.r")
lib.loadLibrary("/thesis/chapter-4/model/IterationEggDistributionClass.r")
lib.loadLibrary("thesis/common/dissimilarity/bray-curtis library.r")

lib.loadLibrary("/thesis/chapter-4/report/SimExp4TrB2ReportClass.r")

experimentPlan.B2<-ExperimentData.loadPlan("4", "sim", "exp4", "TrB2", 1)

report.B2<-SimExp4TrB2Report(experimentPlan.B2)
report.B2<-generateReport(report.B2, TRUE, TRUE)










############################################################################################
# TEST STUFF

fieldCabbages.kaitoke<-FieldCabbages("KAITOKE-04")

fieldCabbages.kaitoke@eggDistribution@totalEggs
fieldCabbages.kaitoke@layouts




iterationNumber<-1
iteration<-getIteration(experimentPlan, iterationNumber)
replicate<-getReplicate(iteration, 1)
eggDist<-IterationEggDistribution(fieldCabbages.kaitoke, iteration, iterationNumber)

dist<-eggDist@eggDistribution
eggDist@eggDistribution@totalCabbages
eggDist@eggDistribution@totalEggs


quartz(width=10, height=10)
plot(eggDist)
boxplot(eggDist)

quartz(height=15, width=10)
par(mfrow=c(3, 1))
plot(dist, plotType="SCALE_CODE", sortScale="1M")
plot(dist, plotType="SCALE_CODE", sortScale="6M")
plot(dist, plotType="SCALE_CODE", sortScale="48M")
