source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("/common/Graphing Utilities Library.r")
lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")

lib.loadLibrary("/common/summarystats/Summary Statistics Library.r")
lib.loadLibrary("/thesis/chapter-4/model/EggDistributionClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbagesClass.r")

lib.loadLibrary("/thesis/chapter-4/model/FieldCabbageLayoutClass.r")
lib.loadLibrary("/thesis/chapter-4/model/IterationEggDistributionClass.r")
lib.loadLibrary("thesis/common/dissimilarity/bray-curtis library.r")
lib.loadLibrary("/thesis/common/circular/Circular Distributions Library.r")

lib.loadLibrary("/common/latex/Latex Library.r")
lib.loadLibrary("/thesis/common/output/StandardReportClass.r")

lib.loadLibrary("/thesis/chapter-4/report/SimExp4TrB1ReportClass.r")
lib.loadLibrary("/thesis/chapter-4/report/FieldDataReportClass.r")
lib.loadLibrary("/thesis/chapter-4/report/OptimisationReportClass.r")
options(warn=2) 


#See sim-exp4-field-data for functions that load the field data and print the report...


experimentPlan<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA1", 1)
report<-SimExp4TrB1Report(experimentPlan)
report<-generateReport(report, TRUE, TRUE)




######################################################################################################
######
###### Old stuff
######

kaitoke.cabbages.df<-read.csv("/Users/Jim/Work/Projects/bugsim/experimental-data/published/chapter-4/resource-layouts/Field KAITOKE-04 - Resource Layout.csv")
kaitoke.cabbages.df[1:10,]

layout.1<-report.field@kaitoke04Cabbages@layouts[[1]]
layout.1@cabbages.df[1:10,]

colnames(report.field@kaitoke04Cabbages@cabbages.df)
length(report.field@kaitoke04Cabbages@cabbages.df$Plant.ID)
sum(report.field@kaitoke04Cabbages@cabbages.df$Field.EggCount)

plot(report.field@kaitoke04Cabbages, TRUE)

chisq.mx<-rbind(c(10, 20, 30, 40), c(10, 21, 30, 40))
chisq.result<-chisq.test(chisq.mx)


quartz(width=10, height=10)

plot(report.field@kaitoke04Cabbages, "SUMMARY")	
plot(report.field@levin06IICabbages, "SUMMARY")

par(mfrow=c(2,2))
plot(report.field@levin06IICabbages,"BLOCK1", title="Block 1")
plot(report.field@levin06IICabbages,"BLOCK2", title="Block 2")
plot(report.field@levin06IICabbages,"BLOCK3", title="Block 3")
plot(report.field@levin06IICabbages,"BLOCK4", title="Block 4")


plot(report.field@kaitoke04Cabbages,"BLOCK1", title="Block 2")


cabbages.df<-report.field@kaitoke04Cabbages@eggDistribution@cabbages.df

cabbages.df[1:10,]

dist<-report.field@kaitoke04Cabbages@eggDistribution
dist<-report.field@levin06IICabbages@eggDistribution

EggDistribution.plotLogLogBreakdownMultiplePlot(dist)


par(mfrow=c(1, 1))
result<-plot(dist, plotType="AGGREGATED-LOGLOG")

result<-plot(dist, plotType="BREAKDOWN-LOGLOG")


plot(dist, plotType="AGGREGATED-SQSQ")
plot(dist, plotType="AGGREGATED")



summary(result$lm.1m)

slope<-result$lm.1m

#Plot un-aggregated SQ-SQ 
x.1m<-as.numeric(levels(dist@cabbages.df$X1m_dens)[dist@cabbages.df$X1m_dens])
y.1m<-(dist@cabbages.df$SQRT_EGG_COUNT/x.1m)
x.1m<-sqrt(x.1m)

plot(y.1m~x.1m,pch=NA, xlim=c(0, sqrt(40)), ylim=c(0, sqrt(60)), ann=F, axes=F)

points(y.1m~x.1m, pch=2, col="blue", cex=1)

labels.x<-c(0,1, 4, 16, 40)
at.x<-sqrt(labels.x)
labels.y<-c(0,1, 5, 15, 30, 60)
at.y<-sqrt(labels.y)

axis(1, at=at.x, labels=labels.x, cex.axis=1.5)
axis(2, at=at.y, labels=labels.y, cex.axis=1.5)
box()
lm.1m<-lm(y.1m~x.1m)
summary(lm.1m)

xlevels.1m<-as.numeric(levels(dist@cabbages.df$X1m_dens))
GraphUtil.plotSqrtRegressionLine(xlevels.1m, lm.1m, col="blue", lty=2, lwd=1.5)

x.1m<-as.numeric(levels(dist@cabbages.df$X1m_dens)[dist@cabbages.df$X1m_dens])
y.1m<-(dist@cabbages.df$SQRT_EGG_COUNT/x.1m)
x.1m<-sqrt(x.1m)

results.kaitoke<-EggDistribution.plotLogLogUnaggregated(report.field@kaitoke04Cabbages@eggDistribution)
results.levin<-EggDistribution.plotLogLogUnaggregated(report.field@levin06IICabbages@eggDistribution)





colnames(dist@cabbages.df)
stats.1m.df<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=dist@cabbages.df, xFactorName="X1m_dens", yFactorName=eggCountColumnName)	

points(dist@cabbages.df, col="blue", pch=2, cex=2,lwd=2)

xlevels.6m<-as.numeric(levels(stats.6m.df$X))+.5
points(stats.6m.df$MEAN~xlevels.6m, col="blue", pch=2, cex=2,lwd=2)



colnames(dist@cabbages.df)
dist.breakdown.list<-EggDistribution.breakdownByFactor(dist, "Field", "Field.EggCount")
par(mfrow=c(2, 2))

for (dist in dist.breakdown.list) {
	plot(dist, plotType="AGGREGATED")
}

results.levin<-EggDistribution.plotLogLogAggregated(dist=report.field@levin06IICabbages@eggDistribution,"Field.EggCount")
summary(results.levin$lm.1m)
summary(results.levin$lm.6m)

plot(results.levin$lm.6m)

results.kaitoke<-EggDistribution.plotLogLogAggregated(dist=report.field@kaitoke04Cabbages@eggDistribution,"Field.EggCount")
summary(results.kaitoke$lm.1m)
summary(results.kaitoke$lm.6m)
summary(results.kaitoke$lm.48m)

results.kaitoke<-EggDistribution.plotSqSqAggregated(report.field@kaitoke04Cabbages@eggDistribution,"Field.EggCount")
summary(results.kaitoke$lm.1m)
summary(results.kaitoke$lm.6m)
summary(results.kaitoke$lm.48m)






#########################################################################################################
####
#### Old Stuff
####

par(mfrow=c(2, 2))

field.df<-subset(report@simulation.cabbages.df, report@simulation.cabbages.df$Field=="E")
dist<-EggDistribution("FieldE", field.df, "Simulation.Egg.Count", report@levin06IICabbages)
plot(dist)

field.df<-subset(report@simulation.cabbages.df, report@simulation.cabbages.df$Field=="F")
dist<-EggDistribution("FieldE", field.df, "Simulation.Egg.Count", report@levin06IICabbages)
plot(dist)

field.df<-subset(report@simulation.cabbages.df, report@simulation.cabbages.df$Field=="G")
dist<-EggDistribution("FieldE", field.df, "Simulation.Egg.Count", report@levin06IICabbages)
plot(dist)

field.df<-subset(report@simulation.cabbages.df, report@simulation.cabbages.df$Field=="H")
dist<-EggDistribution("FieldE", field.df, "Simulation.Egg.Count", report@levin06IICabbages)
plot(dist)

fieldE.df$Plant.ID
# To get the final time and timesteps...
experimentPlan@iterations[[4]]@replicates[[1]]

sdToK.df<-circular.readKappaToKappaConversionTable()
mx<-cbind(sdToK.df$sd, sdToK.df$k)