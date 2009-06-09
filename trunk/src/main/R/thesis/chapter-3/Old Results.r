#This is for producing some graphs from our archive of simulations


sourceDir<-sprintf("%s/chapter-3/previous-data/edge-exp01a-TrB-002", ExperimentData.getPublishedRootDir())
outputDir<-sprintf("%s/output/figures")

plotSummaryOfCentreRatioVsB<-function(){
	basefilename<-"/stats/r-stats-Centre Ratio for L vs B (A=20, S=10, REPS=1000)"
	nFiles<-5
	stats.list<-list()
	for (i.file in 1:nFiles) {
		filename<-sprintf("%s/%s-%03.0f.csv", sourceDir, basefilename, i.file)
		stats.df<-read.csv(filename, header=T)
		stats.df$X<-as.factor(stats.df$X.1)
		stats.df$X.1<-NULL
		stats.list<-collection.appendToList(stats.list, stats.df)
	}

	quartz(width=10, height=10)

	legends=c(1, 5, 10, 40, 80)
	par(mar=c(7, 7,5,5))
	SummaryStatistics.plotManyResults(stats.list, plotErr=T, legendFormatString="L=%0.0f",
	 legendList=legends, noDecoration=T, yLim=c(0.3, 0.5), minXZero=F)
	mtext(side=1, "Release Distance(B)", cex=1.5, line=3)
	mtext(side=2, "CENTRE:CORNER (Eggs)", cex=1.5, line=4)
	lines(c(0, 100), c(0.5, 0.5), col="grey", lwd=2)
}
paramFilename<-sprintf("%s/summary-edge-exp01a-TrB-002-001.csv", sourceDir)
parameters.df<-read.csv(paramFilename)

colnames(parameters.df)

#

parameters.df$simulation.populationSize