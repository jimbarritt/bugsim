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



lib.loadLibrary("/thesis/chapter-3/model/Resource Layout Library.r")

lib.loadLibrary("/common/latex/Latex Library.r")
lib.loadLibrary("/thesis/common/output/StandardReportClass.r")

lib.loadLibrary("/thesis/chapter-3/model/SignalSurfaceClass.r")
lib.loadLibrary("/thesis/chapter-3/model/SignalSurveyClass.r")
lib.loadLibrary("/thesis/chapter-3/report/Signal Surface Library.r")
lib.loadLibrary("/thesis/chapter-3/report/SenseSummaryReportClass.r")


lib.loadLibrary("/thesis/chapter-3/model/Calculated Layout Library.r")
lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrD1ReportClass.r")
lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrE1-OReportClass.r")


#Varying eggs to see the effect it has on variance
experimentPlan<-ExperimentData.loadPlan("raw", "sim", "exp3", "TrE1-O", 1)
report<-SimExp3E1OReport(experimentPlan)
report<-generateReport(report, TRUE, TRUE)

report<-SenseSummaryReport(experimentPlan)
report<-generateReport(report, TRUE, TRUE)

Resourcelayout.calculateSeparationFromPatchSize(patchSize=100,radius=5, countX=4 )
Resourcelayout.calculateSeparationFromPatchSize(patchSize=300,radius=5, countX=4 )
Resourcelayout.calculateSeparationFromPatchSize(patchSize=600,radius=5, countX=4 )

Iteration.calculatePatchSize(separation=15, radius=5, countX=4, countY=4)
Iteration.calculatePatchSize(separation=65, radius=5, countX=4, countY=4)
Iteration.calculatePatchSize(separation=140, radius=5, countX=4, countY=4)
par(mfrow=c(3, 3))
R<-c(5, 15, 50)
S<-c(5, 40, 80)
P<-c(100, 300, 600)

scale<-600
for (r in R) {
	for (p in P) {
#		P.size<-Iteration.calculatePatchSize(separation=s, radius=r, countX=4, countY=4)

		CalculatedLayout.plotLayout(P=p, R=r, countX=4, scaleSize=scale, drawEdges=T)
	}
}