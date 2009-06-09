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

lib.loadLibrary("/thesis/chapter-3/model/SignalSurfaceClass.r")
lib.loadLibrary("/thesis/chapter-3/model/SignalSurveyClass.r")
lib.loadLibrary("/thesis/chapter-3/report/Signal Surface Library.r")
lib.loadLibrary("/thesis/chapter-3/report/SenseSummaryReportClass.r")


lib.loadLibrary("/thesis/chapter-4/report/Chapter-04 Report Library.r")
lib.loadLibrary("thesis/chapter-4/report/FieldDataReportClass.r")
lib.loadLibrary("thesis/chapter-4/report/LevinReplicateReportClass.r")
lib.loadLibrary("/thesis/chapter-4/report/OptimisationReportClass.r")

#See sim-exp4-field-data.r for functions to generate and test out the field data report

# Olfaction
# This one has the same stuff but with varyinf L and A
experimentPlan.D1<-ExperimentData.loadPlan("4", "sim", "exp4", "TrD1", 1, FieldCabbages.LEVINII)
report.D1<-OptimisationReport(experimentPlan.D1, plotSummaryDistributionBreakdown=T)
report.D1<-generateReport(report.D1, TRUE, TRUE)

#Olfaction and Vision
experimentPlan.D2<-ExperimentData.loadPlan("4", "sim", "exp4", "TrD2", 1, FieldCabbages.LEVINII, groupingFactorName="FDF", groupingFactorRawName="FD")
report.D2<-OptimisationReport(experimentPlan.D2, plotSummaryDistributionBreakdown=T)
report.D2<-generateReport(report.D2, TRUE, TRUE)


#Vision
experimentPlan.D3<-ExperimentData.loadPlan("4", "sim", "exp4", "TrD3", 1, FieldCabbages.LEVINII, groupingFactorName="FDF", groupingFactorRawName="FD")
report.D3<-OptimisationReport(experimentPlan.D3, plotSummaryDistributionBreakdown=T)
report.D3<-generateReport(report.D3, TRUE, TRUE)


# Olfaction and Multiple Eggs
experimentPlan.D4<-ExperimentData.loadPlan("4", "sim", "exp4", "TrD4", 1, FieldCabbages.LEVINII)
report.D4<-OptimisationReport(experimentPlan.D4, plotSummaryDistributionBreakdown=T)
report.D4<-generateReport(report.D4, TRUE, TRUE)
#report.sense.D4<-SenseSummaryReport(experimentPlan.D4, z.rotation=c(0, 0, 0, 0), iteration=c(1, 40, 60, 100))
#report.sense.D4<-generateReport(report.sense.D4, TRUE, TRUE)






#Vision multiple eggs
experimentPlan.D5<-ExperimentData.loadPlan("4", "sim", "exp4", "TrD5", 1, FieldCabbages.LEVINII, groupingFactorName="FDF", groupingFactorRawName="FD")
report.D5<-OptimisationReport(experimentPlan.D5, plotSummaryDistributionBreakdown=T)
report.D5<-generateReport(report.D5, TRUE, TRUE)


#Vision and olfaction and multiple eggs
experimentPlan.D6<-ExperimentData.loadPlan("4", "sim", "exp4", "TrD6", 1, FieldCabbages.LEVINII, groupingFactorName="FDF", groupingFactorRawName="FD")
report.D6<-OptimisationReport(experimentPlan.D6, plotSummaryDistributionBreakdown=T,zoomIteration=list(FD=100, L=250, A=10))
report.D6<-generateReport(report.D6, TRUE, TRUE)




#To generate signal surface graphs...
experimentPlan.D1X<-ExperimentData.loadPlan("raw", "sim", "exp4", "TrD1-X", 1, FieldCabbages.LEVINII)
report.sense.D1X<-SenseSummaryReport(experimentPlan.D1X, z.rotation=100, iteration=c(1, 80))
report.sense.D1X<-generateReport(report.sense.D1X, TRUE, TRUE)


######################################################################################################
######
###### Old stuff
######

experimentPlan.B2@fieldComparisonStats.df<-ExperimentPlan.createComparisonStatisticsRows(experimentPlan.B2, FieldCabbages.KAITOKE04)

stats.df<-experimentPlan.B2@fieldComparisonStats.df

row.df<-subset(stats.df, stats.df$result.ID==1)
dist<-row.df$dist.sim[[1]]


plot(dist, plotType="AGGREGATED")

signalSurvey<-SignalSurvey(experimentPlan.D1, 1)

surface<-getSurface(signalSurvey, 1)
survey.mx<-surface@surfaceMatrix

x<-1:surface@countX
y<-1:surface@countY

trellis.par.set(theme = col.whitebg())

print(wireframe(survey.mx,
	shade=TRUE, 
	zoom=0.9,
aspect=c(1, 1), screen=list(z=100, x=-45, y=30),
 xlab="X", ylab="Y", zlab="S",
xlim=c(0, x), ylim=c(0, y)))


print(wireframe(survey.mx, scales = list(arrows = TRUE),shade=TRUE, aspect = c(1, 1), screen=list(z=100, x=-45, y=30),main="", xlab="X", ylab="Y", zlab="S"))

maxX.m<-(surface@maxX/100)

layout.mx<-rbind(c(1, 1, 1),
				 c(2, 3, 4),
				 c(5, 6, 7))
layout(layout.mx, widths=c(5, 95, 0.5), heights=c(0.5, 95,5))
par(mar=c(0,0, 0, 0))
plot.new() #1

par(mar=c(0,0, 0, 0))
plot.new() #2

mtext(side=2, line=-4, "Y (m)", cex=1.5)

par(mar=c(5, 5, 5, 5))
at.indexes<-c(0.5, max(x)/2,max(x)+0.5)
labels<-c(0, maxX.m/2, maxX.m)
image(x,y, z=survey.mx, axes=F, ann=F)
axis(1, at=at.indexes, labels=labels, cex.axis=1.5)
axis(2, at=at.indexes, labels=labels, cex.axis=1.5)

par(mar=c(0,0, 0, 0))
plot.new() #4
plot.new() #5


par(mar=c(0,0, 0, 0))
plot.new() #6
mtext(side=1, line=-4, "X (m)", cex=1.5)


quartz(width=10, height=10)
quartz(width=15, height=5)

ratio.df<-experimentPlan.C1@ratio.df
OptimisationReport.plotForagerSuccessForRvsAandL(ratio.df)
