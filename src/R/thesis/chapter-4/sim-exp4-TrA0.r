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

lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")
lib.loadLibrary("/thesis/common/circular/CircStats Extensions.r")
lib.loadLibrary("/thesis/common/circular/Circular Distributions Library.r")

lib.loadLibrary("/thesis/chapter-3/model/Resource Layout Library.r")
lib.loadLibrary("/thesis/chapter-3/model/Calculated Layout Library.r")



lib.loadLibrary("/common/latex/Latex Library.r")
lib.loadLibrary("/thesis/common/output/StandardReportClass.r")
lib.loadLibrary("/thesis/chapter-4/report/Chapter-04 Report Library.r")

lib.loadLibrary("thesis/chapter-4/report/LevinReplicateReportClass.r")
lib.loadLibrary("/thesis/chapter-4/report/OptimisationReportClass.r")



#########################################################################################################
#
# Trial A0: Diagnostics - Generally check that everything is working and results we really need can be got!:)
#
#

#Just with gaussian and A-100 same as previous results
experimentPlan.A0G1<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA0-G1", 1)
report.A0G1<-LevinReplicateReport(experimentPlan.A0G1)
report.A0G1<-generateReport(report.A0G1, TRUE, TRUE)

#With real wide angle of turn basically same as above...
experimentPlan.A0VM1<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA0-VM1", 1)
report.A0VM1<-LevinReplicateReport(experimentPlan.A0VM1)
report.A0VM1<-generateReport(report.A0VM1, TRUE, TRUE)

#With some vision
experimentPlan.A0VMV1<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA0-VMV1", 1)
report.A0VMV1<-LevinReplicateReport(experimentPlan.A0VMV1)
report.A0VMV1<-generateReport(report.A0VMV1, TRUE, TRUE)

#Vision enabled with search optimised to proove that it doesnt affect results
experimentPlan.A0VMV2<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA0-VMV2", 1,FieldCabbages.LEVINII)
report.A0VMV2<-LevinReplicateReport(experimentPlan.A0VMV2)
report.A0VMV2<-generateReport(report.A0VMV2, TRUE, TRUE)

report.opt.A0VMV2<-OptimisationReport(experimentPlan.A0VMV2,plotSummaryDistributionBreakdown=F)
report.opt.A0VMV2<-generateReport(report.opt.A0VMV2, TRUE, TRUE)


#Just random walk varying L and A
experimentPlan.A0VMLA<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA0-VM-LA", 1)
report.A0VMLA<-LevinReplicateReport(experimentPlan.A0VMLA)
report.A0VMLA<-generateReport(report.A0VMLA, TRUE, TRUE)

#Random walk L-150 A-0.5 (best fit to field) but with 250 eggs
experimentPlan.A0VMEG<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA0-VM-EG", 1)
report.A0VMEG<-LevinReplicateReport(experimentPlan.A0VMEG)
report.A0VMEG<-generateReport(report.A0VMEG, TRUE, TRUE)

#Random walk L-150 A-0.5 (best fit to field) Doubled radius (10)
experimentPlan.A0VMDR<-ExperimentData.loadPlan("4", "sim", "exp4", "TrA0-VM-DR", 1)
report.A0VMDR<-LevinReplicateReport(experimentPlan.A0VMDR)
report.A0VMDR<-generateReport(report.A0VMDR, TRUE, TRUE)


#
#########################################################################################################

debug(LevinReplicateReport.generateForagerSuccessSummarySection)

colnames(report.A0VMLA@simulation.cabbages.df)
debug(EggDistribution.plotAggregatedDistribution)

debug(EggDistribution.breakdownByFactor)

as.numeric(experimentPlan.A0VMLA@ratio.df$eggCount)

itr<-getIteration(experimentPlan.A0VMLA, 1)
rep<-getReplicate(itr, 1)

colnames(report.A0VMLA@eggDistribution@cabbages.df)

x<-seq(from=1, to=100, by=10)
y<-x^2

plot(y~x, pch=19, col="blue", log="", ylim=c(1, 10000))