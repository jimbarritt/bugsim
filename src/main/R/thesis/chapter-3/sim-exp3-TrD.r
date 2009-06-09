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


lib.loadLibrary("/thesis/chapter-3/model/Calculated Layout Library.r")
lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrD1ReportClass.r")
lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrD1-AReportClass.r")


#Varying eggs for 20 replicants to see the effect it has on variance
experimentPlan<-ExperimentData.loadPlan("3", "sim", "exp3", "TrD1-A", 1)
report<-SimExp3D1AReport(experimentPlan)
report<-generateReport(report, TRUE, TRUE)

#Varying L and A and B - 5 Reps
experimentPlan.D1B<-ExperimentData.loadPlan("3", "sim", "exp3", "TrD1-B", 1)
report.D1B<-SimExp3D1Report(experimentPlan.D1B)
report.D1B<-generateReport(report.D1B, TRUE, TRUE)

# 0 vs 800 high reps
experimentPlan.D1C<-ExperimentData.loadPlan("3", "sim", "exp3", "TrD1-C", 1)
report.D1C<-SimExp3D1Report(experimentPlan.D1C,zoom=T, zoomYLim=c(0.40, 0.46))

# 80 high reps
experimentPlan.D1C2<-ExperimentData.loadPlan("3", "sim", "exp3", "TrD1-C2", 1)
report.D1C2<-SimExp3D1Report(experimentPlan.D1C2,zoom=T, zoomYLim=c(0.40, 0.46))

report.D1C@ratio.df<-merge(report.D1C@ratio.df, report.D1C2@ratio.df, all=T)
report.D1C@ratio.df$BF<-as.factor(report.D1C@ratio.df$B)
report.D1C<-generateReport(report.D1C, TRUE, TRUE)



# 80 (inzone) vs 800 (not in zone) high reps
experimentPlan.D1D<-ExperimentData.loadPlan("3", "sim", "exp3", "TrD1-D", 1)
report.D1D<-SimExp3D1Report(experimentPlan.D1D, zoom=T, zoomYLim=c(0.40, 0.46))
report.D1D<-generateReport(report.D1D, TRUE, TRUE)

# 0 vs 800 high reps k=8.8 (sd=20 equivalent)
experimentPlan.D1E<-ExperimentData.loadPlan("3", "sim", "exp3", "TrD1-E", 1)
report.D1E<-SimExp3D1Report(experimentPlan.D1E)
report.D1E<-generateReport(report.D1E, TRUE, TRUE)


# 0 to 800 high reps L-80 K-8.8
experimentPlan.D1F<-ExperimentData.loadPlan("3", "sim", "exp3", "TrD1-F", 1)
report.D1F<-SimExp3D1Report(experimentPlan.D1F, zoom=T)
report.D1F<-generateReport(report.D1F, TRUE, TRUE)


extra.output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/03-simulation-framework/resources/figures"
outputFile<-sprintf("%s/comparison-trial-c-trial-d.pdf",extra.output.figures)
cat("Writing graph to ", outputFile, "\n")
pdf(file=outputFile, width=10, height=8, bg="white")
	SimExp3D1Report.createCombinationGraph(report.D1C,report.D1C2, report.D1D)
dev.off()




	
########################################################################################
# Draw some nice layouts...

CalculatedLayout.plotLvsBForA(report.D1B@ratio.df, 10)



CalculatedLayout.plotLayout(80, 5, 4, 120, drawEdges=F) 
	
ratio.df<-report@ratio.df
levels.P<-levels(ratio.df$PF)
levels.R<-levels(ratio.df$RF)
levels.S<-levels(ratio.df$SF)


for (i in 1:length(levels.P)) {
	for (i.R in as.numeric(levels.R)) {
		i.P<-as.numeric(levels.P[[i]])
		i.S<-as.numeric(levels.S[[i]])

		cat(sprintf("Graph of P=%0.0f, R=%0.0f, S=%0.0f", i.P, i.R, i.S))
		CalculatedLayout.plotLayout(i.P, i.R, 4, 120)
	}
}

P<-60
R<-5
countX<-4
scaleSize<-120




########################################################################################
# Old stuff...

ratio.df<-CalculatedLayout.aggregateRatioResults(experimentPlan)

stats.list<-SummaryStatistics.createSummaryStatistics(ratio.df,groupingFactorName="LF", xFactorName="BF" ,yFactorName="ratio.centre")


av<-aov(ratio.centre~BF, data=ratio.df)
plot(av)
s<-summary(av)
s$Residuals
av.1<-s[1]
colnames(av.1)
summary.lm.1<-summary.lm(av)
av
summary.lm.1$df
methods(s)
str(summary.lm.1)

str(av)
str(s)
str(av$model)
resid<-av$residuals
shapiro.test(resid)

plot(av$residuals)

#Strange incantation to extract the p-value!
p.value<-s[[1]]$'Pr(>F)'[1]
df<-s[[1]]$'Df'[1]


p.value<-lm.1$coef[[2, 4]]

s$coef[[2]]

#AOV - the p value indicates the probability of getting our result if the means are the same. so high P means they are not sig different
x<-c(80, 160)
y1<-ratio.df$ratio.centre[ratio.df$B==80]
y2<-ratio.df$ratio.centre[ratio.df$B==160]
#The confidence interval is the 95% CI of the difference between them
t.test(y1, y2)

lm.y1<-lm(y1~x)
lm.y1<-lm(y2~x)

stats.list[[1]]



k<-circular.kfromLinearSD(toRadians(20))

itr.1<-getIteration(experimentPlan, 1)
itr.2<-getIteration(experimentPlan, 2)
rep1.<-getReplicate(itr.1, 1)


ratio.df<-CalculatedLayout.aggregateRatioResults(experimentPlan)

filter.A<-10

ss.df<-subset(ratio.df, A==filter.A)
undebug(SummaryStatistics.createSummaryStatistics)
stats.list<-SummaryStatistics.createSummaryStatistics(ss.df,groupingFactorName="LF", xFactorName="BF" ,yFactorName="ratio.centre")
legends<-levels(as.factor(ratio.df$L))
par(mar=c(7, 7, 5, 5))

SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendFormatString="L=%0.0f", legendList=legends, noDecoration=T, yLim=c(0, 1), minXZero=F)
mtext(side=1, "Release Distance(B)", cex=1.5, line=3)
mtext(side=2, "Centre:Corner (Eggs)", cex=1.5, line=4)

quartz(width=10, height=10)
nReplicates<-experimentPlan@replicateCount

plot(ratio.centre~BF, data=ratio.df, ylab="Centre Ratio", ylim=c(0,1), main="", cex.axis=1.2, cex.lab=1.2)

plotReplicateBreakdown(ratio.df)


