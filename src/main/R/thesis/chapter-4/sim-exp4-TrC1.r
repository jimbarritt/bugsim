source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")
library(lattice)
lib.loadLibrary("/common/Graphing Utilities Library.r")
lib.loadLibrary("/common/Collection Manipulation Library.r")

lib.loadLibrary("/thesis/chapter-3/model/Calculated Layout Library.r")

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

lib.loadLibrary("thesis/common/dissimilarity/bray-curtis library.r")

lib.loadLibrary("/thesis/chapter-4/report/Chapter-04 Report Library.r")
lib.loadLibrary("thesis/chapter-4/report/FieldDataReportClass.r")
lib.loadLibrary("thesis/chapter-4/report/LevinReplicateReportClass.r")
lib.loadLibrary("/thesis/chapter-4/report/OptimisationReportClass.r")

#See sim-exp4-field-data.r for functions to generate and test out the field data report

#Random Walk - Levin
experimentPlan.exp4.C1<-ExperimentData.loadPlan("4", "sim", "exp4", "TrC1", 1, FieldCabbages.LEVINII)
report.exp4.C1<-OptimisationReport(experimentPlan.exp4.C1)
report.exp4.C1<-generateReport(report.exp4.C1, TRUE, TRUE)
report.exp4.C1.rep<-LevinReplicateReport(experimentPlan.exp4.C1)
report.exp4.C1.rep<-generateReport(report.exp4.C1.rep, TRUE, TRUE)


#Random Walk - Kaitoke
experimentPlan.exp4.C2<-ExperimentData.loadPlan("4", "sim", "exp4", "TrC2", 1, FieldCabbages.KAITOKE04)
report.exp4.C2<-OptimisationReport(experimentPlan.exp4.C2)
report.exp4.C2<-generateReport(report.exp4.C2, TRUE, TRUE)


# Levin with multiple eggs
experimentPlan.exp4.C3<-ExperimentData.loadPlan("4", "sim", "exp4", "TrC3", 1, FieldCabbages.LEVINII)
report.exp4.C3<-OptimisationReport(experimentPlan.exp4.C3)
report.exp4.C3<-generateReport(report.exp4.C3, TRUE, TRUE)

################################################################################
#OLD STUFF

stats.df<-report.C1@experimentPlan@fieldComparisonStats.df

colnames(stats.df)


OptimisationReport.plotBrayCurtisWireframe(stats.df, groupingFactorName="RF", groupingFactorLabel="R", groupingFactorValue=10)



#help(cloud)
#quartz(width=10, height=10)


######################################################################################################
######
###### Old stuff
######

debug(ExperimentPlan.createComparisonStatisticsRows)


experimentPlan.C1@fieldComparisonStats.df<-ExperimentPlan.createComparisonStatisticsRows(experimentPlan.C1, FieldCabbages.LEVINII)

experimentPlan.D3@fieldComparisonStats.df

report<-report.C1
ratio.df<-report@experimentPlan@ratio.df
ratio.df$replicate


eggs.1<-c(11, 20, 30, 10)
eggs.2<-c(2, 45, 34, 10)
eggs.per.plant.1<-eggs.1/4
eggs.per.plant.2<-eggs.2/4

(sum(eggs.1)+sum(eggs.2))/4

mean(eggs.1)+mean(eggs.2)

stats.df<-experimentPlan.C1@fieldComparisonStats.df
class(stats.df[1,]$dist.sim)

colnames(stats.df)
quartz(width=10, height=10)
par(mfrow=c(1, 1))
for (filter.R in levels(stats.df$RF)) {
}
filter.R<-20

OptimisationReport.plotSummaryDistributionsByLandAForR(stats.df, filter.R=20)


quartz(width=15, height=5)

ratio.df<-experimentPlan.C1@ratio.df
OptimisationReport.plotForagerSuccessForRvsAandL(ratio.df)
