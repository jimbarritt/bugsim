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

lib.loadLibrary("/thesis/chapter-3/model/SignalSurfaceClass.r")
lib.loadLibrary("/thesis/chapter-3/model/SignalSurveyClass.r")
lib.loadLibrary("/thesis/chapter-3/report/Signal Surface Library.r")

lib.loadLibrary("/thesis/common/output/StandardReportClass.r")

lib.loadLibrary("/thesis/chapter-3/model/Resource Layout Library.r")
lib.loadLibrary("/thesis/chapter-3/model/Calculated Layout Library.r")

lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrE1ReportClass.r")
lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrE1-OReportClass.r")
lib.loadLibrary("/thesis/chapter-3/report/SenseSummaryReportClass.r")

CalculatedLayout.plotSuccessVsLayoutSinuosity(ratio.df)
CalculatedLayout.plotRatioVsLayoutSinuosity(ratio.df, includeEdge=F)

CalculatedLayout.plotSuccessForPandRSummary(ratio.df)
quartz(width=10, height=10)

experimentPlan.E1B@ratio.df<-CalculatedLayout.aggregateRatioResults(experimentPlan.E1B)
ratio.df<-experimentPlan.E1B@ratio.df
experimentPlan.E1B@ratio.df$REL.S
experimentPlan.E1B@ratio.df<-ratio.df
debug(SimExp3E1Report.addSuccessVsLandASummarySection)
report<-report.E1B


#This one has no edges
experimentPlan.exp3.E1A<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-A", 1)
report.exp3.E1A<-SimExp3E1Report(experimentPlan.exp3.E1A, includeEdges=F)
report.exp3.E1A<-generateReport(report.exp3.E1A, TRUE, TRUE)
rm(experimentPlan.E1A)
rm(report.E1A)

#This one has edges
experimentPlan.exp3.E1B<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-B", 1)
report.exp3.E1B<-SimExp3E1Report(experimentPlan.exp3.E1B, includeEdges=T)
report.exp3.E1B<-generateReport(report.exp3.E1B, TRUE, TRUE)

#Olfaction (with edges)
experimentPlan.exp3.E1C<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-C", 1)
report.exp3.E1C<-SimExp3E1Report(experimentPlan.exp3.E1C, includeEdges=T)
report.exp3.E1C<-generateReport(report.exp3.E1C, TRUE, TRUE)
#10, 19
report.sense.exp3.E1C<-SenseSummaryReport(experimentPlan.exp3.E1C, z.rotation=c(0, 0, 0, 0), iteration=c(1, 10, 19), maxZ=0.019, byZ=0.019/10)
report.sense.exp3.E1C<-generateReport(report.sense.exp3.E1C, TRUE, TRUE)



#Vision (with edges)
experimentPlan.exp3.E1D<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-D", 1)
report.exp3.E1D<-SimExp3E1Report(experimentPlan.exp3.E1D, includeEdges=T)
report.exp3.E1D<-generateReport(report.exp3.E1D, TRUE, TRUE)

#Vision ANd Olfaction (with edges)
experimentPlan.exp3.E1E<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-E", 1)
report.exp3.E1E<-SimExp3E1Report(experimentPlan.exp3.E1E, includeEdges=T)
report.exp3.E1E<-generateReport(report.exp3.E1E, TRUE, TRUE)


#some different olfaction signal widths:
#A - SW=20
experimentPlan.exp3.E1C.SWA<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-C-SWA", 1)
report.sense.exp3.E1C.SWA<-SenseSummaryReport(experimentPlan.exp3.E1C.SWA, z.rotation=c(0, 0, 0, 0), iteration=c(1))
report.sense.exp3.E1C.SWA<-generateReport(report.sense.exp3.E1C.SWA, TRUE, TRUE)

#B - SW=90
experimentPlan.exp3.E1C.SWB<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-C-SWB", 1)
report.sense.exp3.E1C.SWB<-SenseSummaryReport(experimentPlan.exp3.E1C.SWB, z.rotation=c(0, 0, 0, 0), iteration=c(1))
report.sense.exp3.E1C.SWB<-generateReport(report.sense.exp3.E1C.SWB, TRUE, TRUE)

#B - SW=180
experimentPlan.exp3.E1C.SWC<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-C-SWC", 1)
report.sense.exp3.E1C.SWC<-SenseSummaryReport(experimentPlan.exp3.E1C.SWC, z.rotation=c(0, 0, 0, 0), iteration=c(1))
report.sense.exp3.E1C.SWC<-generateReport(report.sense.exp3.E1C.SWC, TRUE, TRUE)

#D - SW=360
experimentPlan.exp3.E1C.SWD<-ExperimentData.loadPlan("3", "sim", "exp3", "TrE1-C-SWD", 1)
report.sense.exp3.E1C.SWD<-SenseSummaryReport(experimentPlan.exp3.E1C.SWD, z.rotation=c(0, 0, 0, 0), iteration=c(1))
report.sense.exp3.E1C.SWD<-generateReport(report.sense.exp3.E1C.SWD, TRUE, TRUE)








################################################################################################
#
# TEST

itr<-getIteration(experimentPlan.E1D, 1)
colnames(itr@parameters.df)




quartz(width=10, height=10)

mtext(side=3, "HELLO")

debug(SimExp3E1Report.addSignalSurface)


summary.df<-experimentPlan.E1C@iterationSummary.df
itr.19<-getIteration(experimentPlan.E1C, 19)
itr.1<-getIteration(experimentPlan.E1C, 1)

par(mfrow=c(1, 1))
print(wireframe(itr.19@signalSurfaces[[1]]@surfaceMatrix), drape=T)
print(wireframe(itr.1@signalSurfaces[[1]]@surfaceMatrix))






#Test run with no edges
experimentPlan<-ExperimentData.loadPlan("raw", "sim", "exp3", "TrE1-A-X", 2)
report<-SimExp3E1Report(experimentPlan, includeEdges=F)
report<-generateReport(report, TRUE, TRUE)

#Test run with edges
experimentPlan<-ExperimentData.loadPlan("raw", "sim", "exp3", "TrE1-A-X", 3)
report<-SimExp3E1Report(experimentPlan, includeEdges=T)
report<-generateReport(report, TRUE, TRUE)

#Test run with 1 iteration
experimentPlan<-ExperimentData.loadPlan("raw", "sim", "exp3", "TrE1-X1", 4)
experimentPlan@iterations[[1]]
report<-SimExp3E1Report(experimentPlan, includeEdges=T)
report<-generateReport(report, TRUE, TRUE)



################################################################################################
#
# OLD STUFF
colnames(itr@parameters.df)


R<-50
layout.df<-Resourcelayout.createLayout(600, 4, 0, 0, edges=T)
area<-Geometry.areaCovered(layout.df$x, layout.df$y, radii=rep(R, 16))
prop<-area/(600*600)

colnames(ratio.df)

ratio.df<-experimentPlan.E1B@ratio.df

CalculatedLayout.plotSuccessVsLandA(ratio.df)
ratio.df$ratio.centre
ratio.df$foragers.success
ratio.df$foragers.dead+ratio.df$foragers.escaped

ratio.df$SF
ss.df<-subset(ratio.df, ratio.df$RF=="5")

quartz(width=16, height=8)

itr<-getIteration(experimentPlan, 1)


rep<-getReplicate(itr, 1)

experimentPlan.E1C@ratio.df<-CalculatedLayout.aggregateRatioResults(experimentPlan.E1C)
experimentPlan@ratio.df<-CalculatedLayout.aggregateRatioResults(experimentPlan)
ratio.df<-experimentPlan.E1D@ratio.df
ratio.df<-subset(ratio.df, ratio.df$G==1)

colnames(experimentPlan.E1C@ratio.df)
experimentPlan.E1C@ratio.df$SWF
quartz(width=16, height=8)

quartz(width=10, height=10)
ratio.df$S
ratio.df$AREAF

experimentPlan.E1A@ratio.df$SF
experimentPlan.E1A@ratio.df$RF
experimentPlan.E1A@ratio.df$AREAF

filter.
ss.df<-subset(ratio.df, ratio.df$P==filter.P)
ss.df<-subset(ss.df, ss.df$R==filter.R)

stats.list<-SummaryStatistics.createSummaryStatistics(ss.df,groupingFactorName="AF", xFactorName="LF" ,yFactorName=response)
par(mar=c(3, 3, 5, 1))

if (legend) {
	legends<-levels(ratio.df$AF)
} else {
	legends<-c()
}
linesX<-c(min(ss.df$L), max(ss.df$L))


	axis(1, cex.axis=1.5)




quartz(width=15, height=5)
colnames(itr@parameters.df)

sort(as.numeric(levels(ratio.df$AREAF)))
colnames(ratio.df)

ratio.df$AREAF
ratio.df$SIN
CalculatedLayout.plotRatioVsLayout(ratio.df)

CalculatedLayout.plotRatioVsLayoutAndSinuosity(ratio.df)

CalculatedLayout.plotSuccessVsLayout(ratio.df)

levels.R<-levels(experimentPlan.E1A@ratio.df$RF)
levels.P<-levels(experimentPlan.E1A@ratio.df$PF)
levels.L<-levels(experimentPlan.E1A@ratio.df$LF)
levels.A<-levels(experimentPlan.E1A@ratio.df$AF)

CalculatedLayout.plotSuccessVsLayout(experimentPlan.E1A@ratio.df)

CalculatedLayout.plotRatioVsSeparationByA(ratio.df)

CalculatedLayout.plotRatioVsSeparationByL(ratio.df)

CalculatedLayout.plotRatioForPandRSummary(ratio.df)

CalculatedLayout.plotSuccessForPandRSummary(ratio.df)

CalculatedLayout.plotSuccessVsLayoutSinuosity(ratio.df)


CalculatedLayout.plotSuccessVsAreaAndPatchSize(ratio.df)



ratio.df$ratio.edge
quartz(width=15, height=8)
par(mfrow=c(1,2))
par(mar=c(1, 1, 1, 1))
CalculatedLayout.plotCornerCentreEdgeRatio(ratio.df)
par(mar=c(1, 1, 1, 1))
CalculatedLayout.plotCornerCentreEdgeRatio(ratio.df, perPlant=T)

scaleSize<-600

ss.df


# Problem with intersecting radii!!
scaleSize<-600
x<-c(486.76406871192853, 511.76406871192853 , 536.7640687119285)
y<-c(561.7640687119285, 536.7640687119285, 536.7640687119285)


plot(y~x ,pch=NA,  axes=F, ann=F, xlim=c(300, scaleSize+300), ylim=c(300, scaleSize+300))

symbols(y~x, circles=c(50, 50, 50), bg="grey", fg="darkgrey",inches=F, add=T, lwd=2)
points(y~x, pch=16, col="black", cex=.5)


lines(x=c(643.8595488356066,500.44762466512395), y=c(772.5046645005192, 567.729128838371), col="blue", lwd=2)

lines(x=c(643.8595488356066,x[1]), y=c(772.5046645005192, y[1]), col="green",lty=1, lwd=1)
lines(x=c(643.8595488356066,x[2]), y=c(772.5046645005192, y[2]), col="green",lty=1, lwd=1)
lines(x=c(643.8595488356066,x[3]), y=c(772.5046645005192, y[3]), col="green",lty=1, lwd=1)




par(mfrow=c(1,1))

CalculatedLayout.plotRatioVsLForA(ratio.df, i.P, i.R, legendPosition="topright", title=title, xaxisLabel="", yaxisLabel="", legend=legend)

ratio.df<-experimentPlan.E1B@ratio.df
ratio.df<-subset(ratio.df, ratio.df$G==5)
stats.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="AF", xFactorName="LF" ,yFactorName="ratio.centre")

par(mar=c(7, 7, 5, 5))
legends=levels(ratio.df$AF)
SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendHoriz=T, 
	legendFormatString="A=%0.1f", legendList=legends, noDecoration=F,plotAxes=F, 
	yLim=c(0, 0.3), minXZero=F, xaxisLabel="", yaxisLabel="")
axis(1, cex.axis=1.5)
axis(2, at=c( 0.125, 0.3), cex.axis=1.5)

mtext(side=1, line=4, "L", cex=1.5)
mtext(side=2, line=4, "CENTRE:CORNER (Eggs)", cex=1.5)

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

cat(sprintf("\\\\textbf{P} & \\\\textbf{R} & \\\\textbf{S} \\\\ \n"))
scale<-600
for (r in R) {
	for (p in P) {
#		P.size<-Iteration.calculatePatchSize(separation=s, radius=r, countX=4, countY=4)

		s<-Resourcelayout.calculateSeparationFromPatchSize(patchSize=p,radius=r, countX=4 )
 		cat(sprintf("%0.0f, ",   s))		

		CalculatedLayout.plotLayout(P=p, R=r, main="hello" ,countX=4, scaleSize=scale, drawEdges=T, drawAxes=F)
	}	
}
cat("\n")


par(mfrow=c(1, 1))
CalculatedLayout.plotLayout(P=100, R=50, main="" ,countX=4, scaleSize=600, drawEdges=T, drawAxes=F)

quartz(width=10, height=10)

plotIntersectionAreaDiagram()

centre1<-c(500, 500)
R1<-50
centre2<-c(475, 500)
R2<-50

intersectionArea(centre1, R1, centre2, R2)
centre1<-c(195, 300)
R1<-150
centre2<-c(380, 300)
R2<-100
intersectionArea(centre1, R1, centre2, R2)



