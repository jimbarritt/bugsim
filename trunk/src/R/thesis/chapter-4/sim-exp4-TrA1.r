source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("/thesis/common/circular/Circular Distributions Library.r")

lib.loadLibrary("/common/Graphing Utilities Library.r")
lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/common/summarystats/Summary Statistics Library.r")
lib.loadLibrary("/thesis/chapter-4/model/EggDistributionClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbagesClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbageLayoutClass.r")
lib.loadLibrary("/thesis/chapter-4/model/IterationEggDistributionClass.r")

lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")

lib.loadLibrary("/thesis/chapter-3/model/SignalSurfaceClass.r")
lib.loadLibrary("/thesis/chapter-3/model/SignalSurveyClass.r")
lib.loadLibrary("/thesis/chapter-3/report/Signal Surface Library.r")
lib.loadLibrary("/thesis/chapter-3/report/SenseSummaryReportClass.r")

lib.loadLibrary("thesis/common/dissimilarity/bray-curtis library.r")

lib.loadLibrary("/common/latex/Latex Library.r")
lib.loadLibrary("/thesis/common/output/StandardReportClass.r")
lib.loadLibrary("/thesis/chapter-4/report/Chapter-04 Report Library.r")

lib.loadLibrary("/thesis/chapter-3/model/SignalSurfaceClass.r")
lib.loadLibrary("/thesis/chapter-3/model/SignalSurveyClass.r")
lib.loadLibrary("/thesis/chapter-3/report/Signal Surface Library.r")
lib.loadLibrary("/thesis/chapter-3/report/SenseSummaryReportClass.r")


lib.loadLibrary("/thesis/chapter-3/model/Calculated Layout Library.r")

lib.loadLibrary("thesis/chapter-4/report/LevinReplicateReportClass.r")



options(warn=2) 


#########################################################################################################
#
# Trial A1: Replicate Calibration - run different parameters for 10 replicates to explore variation.
#
#
experimentPlan.A1G1<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA1-G1", 1)
report.A1G1<-LevinReplicateReport(experimentPlan.A1G1)
report.A1G1<-generateReport(report.A1G1, TRUE, TRUE)

experimentPlan.A1VM1<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA1-VM1", 1)
report.A1VM1<-LevinReplicateReport(experimentPlan.A1VM1)
report.A1VM1<-generateReport(report.A1VM1, TRUE, TRUE)



experimentPlan.A1VM2<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA1-VM2", 1)
report.A1VM2<-LevinReplicateReport(experimentPlan.A1VM2)
report.A1VM2<-generateReport(report.A1VM2, TRUE, TRUE)





#
#########################################################################################################

report<-report.D2
filter.A<-1.735
filter.L<-256

cabbages.df<-report.A1VM1@simulation.cabbages.df

colnames(cabbages.df)
ss.df<-subset(cabbages.df, cabbages.df$A==filter.A)
ss.df<-subset(ss.df, ss.df$L==filter.L)
#ss.df<-subset(ss.df, ss.df$Replicate==2)


dist.sim<-EggDistribution(report.A1VM1@experimentPlan@experimentName, ss.df, "Simulation.Egg.Count", report.A1VM1@levin06IICabbages)


r<-EggDistribution.plotLogLogBreakdownMultiplePlot(dist.sim)
r<-EggDistribution.plotLogLogBreakdownMultiplePlot(dist.levin)

dist.levin<-report.field@levin06IICabbages@eggDistribution


sim.xlevels.1m<-as.numeric(levels(dist.sim@cabbages.df$X1m_dens))				
sim.x.1m<-as.numeric(levels(dist.sim@cabbages.df$X1m_dens)[dist.sim@cabbages.df$X1m_dens])
sim.y.1m<-(dist.sim@cabbages.df$Simulation.Egg.Count/sim.x.1m)+1	

sim.xlevels.6m<-as.numeric(levels(dist.sim@cabbages.df$X6m_dens))				
sim.x.6m<-as.numeric(levels(dist.sim@cabbages.df$X6m_dens)[dist.sim@cabbages.df$X6m_dens])
sim.y.6m<-(dist.sim@cabbages.df$Simulation.Egg.Count/sim.x.6m)+1



xlevels.1m<-as.numeric(levels(dist.levin@cabbages.df$X1m_dens))				
x.1m<-as.numeric(levels(dist.levin@cabbages.df$X1m_dens)[dist.levin@cabbages.df$X1m_dens])
y.1m<-(dist.levin@cabbages.df$Field.EggCount/x.1m)+1	

xlevels.6m<-as.numeric(levels(dist.levin@cabbages.df$X6m_dens))				
x.6m<-as.numeric(levels(dist.levin@cabbages.df$X6m_dens)[dist.levin@cabbages.df$X6m_dens])
y.6m<-(dist.levin@cabbages.df$Field.EggCount/x.6m)+1


length(x.1m[x.1m==1])
length(sim.x.1m[sim.x.1m==1])

length(x.6m[x.6m==1])
length(sim.x.6m[sim.x.6m==1])

sim.y.6m[which(sim.x.6m==1)]
y.6m[which(x.6m==1)]

par(mar=c(4, 4, 4, 4))
par(mfrow=c(1,3))
plot(y.6m~x.6m)
plot(sim.y.6m~sim.x.6m)

?which







dist<-report@eggDistributions.list[[1]]
dists<-ExperimentPlan.breakDownEggDistributionByReplicate(dist)

quartz(width=12, height=12)
par(mfrow=c(1,1))
plot(dists[[1]], plotType="AGGREGATED", noDecoration=TRUE)

plot(dists[[7]])


debug(calculatePlotYLim)





























####################################################################################################
#OLD STUFF:!!!!!


fieldCabbages.levin<-FieldCabbages("LEVIN-II")
fieldCabbages.levin@eggDistribution@totalEggs
fieldCabbages.levin@layouts
colnames(fieldCabbages.levin@layout.df)
attach(fieldCabbages.levin@originalCabbages.df)
pdf("~/Levin Layout.pdf", width=11.7, height=16.5, bg="white")
plot(y~x)
dev.off()

detach(fieldCabbages.levin@originalCabbages.df)
plot(fieldCabbages.levin@layouts)
quartz(width=10, height=10)
par(mfrow=c(2, 2))

plot(fieldCabbages.levin, FALSE)
plot(fieldCabbages.levin, TRUE)


fieldCabbages<-FieldCabbages("KAITOKE")

quartz()
plot(fieldCabbages, TRUE)
plot(fieldCabbages, TRUE,xlim=c(20, 32), ylim=c(70,82))
plot(fieldCabbages, FALSE)
plot(fieldCabbages@eggDistribution)

fieldCabbages@eggDistribution@totalEggs
cabbages<-fieldCabbages@eggDistribution@totalCabbages

plot(fieldCabbages@eggDistribution)
densityLevin<-60/(36*36)
denistyKaitoke<-400/(200*200)

fieldCabbages@cabbagesFilename


# OR fieldCabbages<-FieldCabbages("LEVIN")
#undebug(SummaryStatistics.createUngroupedSummaryStatistics)
#plot(fieldCabbages@eggDistribution)
#quartz(width=15, height=15)
#plot(fieldCabbages, FALSE)
#plot(fieldCabbages, TRUE)

experimentPlan<-ExperimentData.loadPlan("raw-download", "sim", "exp4", "TrA1", 4)
experimentPlan<-ExperimentData.loadPlan("raw", "sim", "exp4", "TrA1", 1)


iterationNumber<-1
iteration<-getIteration(experimentPlan, iterationNumber)
replicate<-getReplicate(iteration, 1)
eggDist<-IterationEggDistribution(fieldCabbages, iteration, iterationNumber)

eggDist@eggDistribution@totalCabbages
eggDist@eggDistribution@totalEggs


quartz(width=10, height=10)
plot(eggDist)
boxplot(eggDist)


FieldCabbages.createSimulationLayoutFiles(fieldCabbages)


#BrayCurtis Stuff
fieldE<-fieldCabbages.levin@layouts[[1]]

y1<-fieldE@cabbages.df$Field.EggCount





#Try to plot with the 16 split up:
colnames(fieldCabbages@originalCabbages.df)
cabbage.df<-fieldCabbages@originalCabbages.df
cabbage.df<-kaitDensities.df
colnames(cabbage.df)
str(cabbage.df)
cabbage.df$X3.scale.code 
str(stats.6m.ScaleCode.df$X)

stats.ScaleCode.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=cabbage.df, xFactorName="X3.scale.code", yFactorName="Field.EggCount")	



str(stats.6m.ScaleCode.df$X)

is.numeric(levels(stats.6m.ScaleCode.df$X))
max(cabbage.df$Field.EggCount)



