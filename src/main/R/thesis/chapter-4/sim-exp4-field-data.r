source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")


lib.loadLibrary("/common/Graphing Utilities Library.r")
lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/common/summarystats/Summary Statistics Library.r")
lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")
lib.loadLibrary("/thesis/chapter-4/model/EggDistributionClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbagesClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbageLayoutClass.r")
lib.loadLibrary("/thesis/chapter-4/model/IterationEggDistributionClass.r")
lib.loadLibrary("/common/latex/Latex Library.r")
lib.loadLibrary("/thesis/chapter-4/report/FieldDataReportClass.r")


##############################################################################################
#
# GENERATE REPORT:
#
report.field<-FieldDataReport()
report.field<-generateReport(report.field, TRUE, TRUE)



##############################################################################################
#
#  CREATE KAITOKE - 04 FIELD DATA 
#
FieldCabbages.importKaitoke04FieldData()

##############################################################################################
#
#  LEVIN - 06 - II 
#
FieldCabbages.importLevinII06FieldData()

##############################################################################################
#
#  Generate Field Layouts: 
#
FieldCabbages.createSimulationLayoutFiles(FieldCabbages.LEVINII)
FieldCabbages.createSimulationLayoutFiles(FieldCabbages.KAITOKE04)



##############################################################################################
#
# TEST:
#

kaitokeCabbages<-report@kaitoke04Cabbages
str(kaitokeCabbages@cabbages.df)
quartz(width=10, height=10)
plot(kaitokeCabbages, TRUE)

dist<-kaitokeCabbages@eggDistribution
quartz(width=10, height=10)
plot(dist, plotType, plotType="AGGREGATED")

quartz(height=15, width=10)
par(mfrow=c(3, 1))
plot(dist, plotType="SCALE_CODE", sortScale="1M")
plot(dist, plotType="SCALE_CODE", sortScale="6M")
plot(dist, plotType="SCALE_CODE", sortScale="48M")

levinCabbages<-NULL
levinCabbages<-FieldCabbages("LEVIN-II")
dist<-levinCabbages@eggDistribution

quartz(height=10, width=10)
par(mfrow=c(2,2))
plot(levinCabbages, TRUE)
plot(FieldCabbages.LEVINII, "FIELD-E")

fieldCabbages<-FieldCabbages.LEVINII
par(mfrow=c(1,1))
FieldCabbages.plotCabbages("E", fieldCabbages@eggDistribution@cabbages.df, margin=2.75,axis.at=c(0, 18, 36), xLim=c(0, 36), yLim=c(0,36), graphId="a",cex.labels=1, cex.cabbage=0.8)



plot(dist, plotType, plotType="AGGREGATED")
par(mfrow=c(2, 1))
plot(dist, plotType="SCALE_CODE", sortScale="1M")
plot(dist, plotType="SCALE_CODE", sortScale="6M")

