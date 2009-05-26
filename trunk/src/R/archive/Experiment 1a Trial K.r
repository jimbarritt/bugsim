source("~/Work/code/bugsim/resource/R/Exp1a Library.r", echo=FALSE)

experimentNumber<-4
trialId<-"TrK4" # TrK4
baseFilename<-"edge-exp01a"

rm(inp.df)
rm(inp.1.df)
rm(inp.2.df)
inp.1.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId, fileNumber=1)
inp.2.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId, fileNumber=2)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)

inp.df<-merge(inp.1.df, inp.2.df, all=TRUE)
numReplicants<-summarise(inp.df)

plot0vs800Results(inp.df, plan.df, baseFilename, experimentNumber, trialId, numReplicants, B.1=0, B.2=800)

crossings.df<-readBoundaryCrossings(filename=baseFilename, experiment=experimentNumber, trial=trialId)
#colnames(crossings.df)

plotBorderCrossingResults(crossings.df, baseFilename, experimentNumber, trialId)


#Plot the starting points of the intersections....
plotSummaryOfBorderCrossings(crossings.df, "B" ,baseFilename, experimentNumber, trialId, inp.df, plan.df)
















#old stuff ....
crossings.all.df<-crossings.df
crossings.1.df<-subset(crossings.all.df, crossings.all.df$iteration==1)
crossings.2.df<-subset(crossings.all.df, crossings.all.df$iteration==2)

plotCrossingsSummary(crossings.1.df)

quartz(width=10, height=10)
crossings.1.hist<-hist(crossings.1.df, freq=TRUE, plot=FALSE)
crossings.2.hist<-hist(crossings.2.df)

plot(crossings.hist, main=sprintf("Histogram of d (N=%d)", N))

plotScatterPlotOfStartCoords(crossings.1.df)

plotCrossingSummary<-function() {
#Plot the starting points of the intersections....
	quartz(width=10, height=10)
	par(mfrow=c(2,2))
	plotScatterPlotOfStartCoords(crossings.df)
}

plotScatterPlotOfStartCoords<-function(crossings.df) {	
	N<-length(crossings.df$startY)
	
	plot(crossings.df$startY~crossings.df$startX, pch=NA, xlab="x", ylab="y", main=sprintf("Start locations N=%d", N))
	points(crossings.df$intersectionY~crossings.df$intersectionX, col="grey", cex=0.5)
	points(crossings.df$startY~crossings.df$startX, col="blue", cex=0.5)
	
	#symbols(x=c(centreXY), y=c(centreXY), circles=c(radiusOuter), add=TRUE, inches=FALSE)
	#lines(x=c(centreXY-radiusOuter, centreXY-radiusInner), y=c(centreXY, centreXY), col="grey", lwd=4)
}

#Histogram of distances to the boundary:
	all.hist<-hist(crossings.df$d.toBoundary, main="Distance to boundary", xlab="Distance",plot=FALSE)
	reverse.mids<-vector(length=length(all.hist$mids))
	reverse.breaks<-vector(length=length(all.hist$mids))
	reverse.counts<-vector(length=length(all.hist$counts))
	i<-0
	for (i in 0:(length(all.hist$mids)-1)) {
		iNew<-((length(all.hist$mids))-i) 
		reverse.counts[iNew]<-all.hist$counts[[i+1]]
		reverse.mids[iNew]<-all.hist$mids[[i+1]]
		reverse.breaks[iNew]<-all.hist$breaks[[i+1]]+5
	}
	all.hist$counts<-reverse.counts
	plot(all.hist, main="Distance from Boundary", axes=FALSE,xlim=c(0, 80),xlab="Distance")
	axis(2)
	axis(1, at=all.hist$mids, labels=reverse.breaks)
#Histogram of distances to the boundary:
	hist(crossings.df$d, main="Step Length Inside Boundary", xlab="Distance")
}


dataframes<- createSummaryDataFrame(baseFilename, experimentNumber, trialId, 1, 1, fileNumber=1)
summary.df<-dataframes[[1]]

str(crossings.df$B)
plotBorderCrossingResults(crossings.df)

x<-crossings.hist$mids
y<-crossings.hist$counts
yx.quad.lm<-lm(y~x+I(x^2))
summary(yx.quad.lm)
AIC(yx.quad.lm)
y.quad.fitted<-fitted(yx.quad.lm)
y.quad.jimfit<-90.429569+(3.275*x)+(-0.0408*(x^2))

plot(y~x, pch=NA, ylim=c(0, max(crossings.hist$counts)))
lines(y.quad.fitted~x, col="grey")
lines(y.quad.jimfit~x, col="green")
points(y~x, pch=19, cex=.75, col="blue")



inputFilename<-"/Users/Jim/Work/Documents/msc/Simulation Input/Birth Locations And Headings For Import - B-800 n-500.csv"
inputCrossings.df<-importBoundaryCrossings(inputFilename)
plotComparisonWithInput("Comparison Of Border Crossings input vs B0-test",crossings.df, inputCrossings.df,  basefilename, experimentNumber, trialId)

simulatedFilename<-"/Users/Jim/Work/Documents/msc/Simulation Input/Simulated Boundary Crossings - B-0 L-80 A-20 s-10 N-500.csv"
simulatedCrossings.df<-importBoundaryCrossings(simulatedFilename)
plotComparisonWithInput("Comparison Of Border Crossings input vs B0-test RFML-FALSE",crossings.df, simulatedCrossings.df,  basefilename, experimentNumber, trialId)


simulatedRFMLFilename<-"/Users/Jim/Work/Documents/msc/Simulation Input/Simulated Boundary Crossings - B-0 L-80 A-20 s-10 N-500 RFML-TRUE ALPHA-1.2 BETA-1.2.csv"
simulatedRFMLCrossings.df<-importBoundaryCrossings(simulatedRFMLFilename)
plotComparisonWithInput("Comparison Of Border Crossings input vs B0-test RFML-TRUE A-1.2 B-12",crossings.df, simulatedRFMLCrossings.df,  basefilename, experimentNumber, trialId)



#Old stuff:


#This bit creates us a file to us as an input file to the simulation at border 0
B800.crossings.df<-subset(crossings.df, crossings.df$B==800)
str(B800.crossings.df)
directory<-createDirectoryName(baseFilename, experimentNumber, trialId)
filename<-sprintf("%s/Birth Locations And Headings For Import - B-800 n-2151.csv", directory)
write.csv(B800.crossings.df, filename)


min(B800.crossings.df$d)









#Old stuff
colnames(crossings.df)
length(crossings.df$iteration)

B0.df<-subset(crossings.df, crossings.df$B==0)
B800.df<-subset(crossings.df, crossings.df$B==800)
B800.df.MASSIVE<-B800.df

str(B800.df)
B800.df$B<-factor(B800.df$B)
B800.df$L<-factor(B800.df$L)
B800.df$A<-factor(B800.df$A)


B800.df<-B800.df.MASSIVE
writeDataFrame(list(B800.df), baseFilename, experimentNumber, trialId, "B800-crossings")
df.list<-readDataFrame(baseFilename, experimentNumber, trialId, "B800-crossings")
B800.df<-df.list[[1]]

	directory<-createDirectoryName(basefilename, experimentNumber, trialId)
	filename<-sprintf("%s/L-80 B-800 Frequency Of distances Vs Beta Dist Model.pdf", directory)
	
	quartz(width=12, height=4)
	pdf(width=12, height=4, file=filename)
	par(mfrow=c(1, 3))	
	result.hist<-plotHistogram(B800.df, "d", 5, "L=80 B=800 Frequency of d")
	
	str(result.hist)
	plot(result.hist$mids, result.hist$counts,ylim=c(0, 450), xlab="d", ylab="count",main="Scatterplot of frequencies")


#Distributions
	x <- seq(0, 1, length=20)
	y <- 250+(100*dbeta(x, 2, 2))
	plot (80*x, y, ylim=c(0, 450), xlab="d", ylab="count",main="beta distribution model")

	dev.off()

	
	test.beta.df<-read.csv(sprintf("%s/test-beta.csv", directory))
	colnames(test.beta.df)
	test.hist<-plotHistogram(test.beta.df, "y", 5, "Test beta distribution")

max(test.beta.df$y)

max(result.hist$count)
	
	result.sd<-sd(B800.df$d)
	result.mean<-mean(B800.df$d)
	result.n<-length(B800.df$B)
	
	help(lm)
	result.df<-data.frame("mids"=result.hist$mids, "counts"=result.hist$counts)

	
	result.lm<-lm(mids~counts, result.df)
	summ
	
	norm.vals4 <- rnorm(mean=result.mean, sd=120, n=result.n)
	hist(norm.vals4, main=sprintf("Generated Values n=%d", result.n))
	





B.800.df<-subset(crossings.df, merged.df$B=="800")


directory<-createDirectoryName(basefilename, experimentNumber, trialId)
filename<-sprintf("%s/A vs B Frequency Of distances - variable breaksize.pdf", directory)
results.list<-plotFrequencyOfFactor(merged.df, "A", "B", "distance", 5, filename)

directory<-createDirectoryName(basefilename, experimentNumber, trialId)
filename<-sprintf("%s/A vs B Frequency Of Headings - variable breaksize.pdf", directory)
results.list<-plotFrequencyOfFactor(merged.df, "A", "B", "heading", 5, filename)



all.stats<-createSummaryStatistics(merged.df, "B", "L", "distance")

lenL<-length(levels(merged.df$L))
lenB<-length(levels(merged.df$B))
allLineColors<-rainbow(lenL + lenB)

lineColsL <- allLineColors[1:lenL]
lineColsB <- allLineColors[(lenL+1):(lenL+lenB)]

plotSummaryStatistics(all.stats, "title", "L - Step Length", "distance", "B=%s", merged.df$B, lineColors=lineColsB) 

colnames(merged.df)


L80.B200.df<-subset(L80.df, L80.df$B=="200")
plotHistogram(L80.B200.df, "distance", 5, "L=80, B=200")


L80.B100.df<-subset(L80.df, L80.df$B=="100")
plotHistogram(L80.B100.df, "distance", 5, "L=80, B=100")


itr1.df<-subset(merged.df, iteration==1)
itr2.df<-subset(merged.df, iteration==2)
itr3.df<-subset(merged.df, iteration==3)

mean(itr1.df$distance)

quartz(width=10.25, height=4.125)
par(mfrow=c(1,3))


length(itr1.df$B)
length(itr2.df$B)
length(itr3.df$B)
length(itr1.df$B)+length(itr2.df$B)+length(itr3.df$B)


itr2.hist <- createHistogram(itr2.df$distance, 1, length(itr2.df$distance), breakSize, breakCount)
n<-sprintf("n=%d", length(itr2.df$distance))
plotManyHistograms(list(itr2.hist), n, "Distance")

itr3.hist <- createHistogram(itr3.df$distance, 1, length(itr3.df$distance), breakSize, breakCount)
n<-sprintf("n=%d", length(itr3.df$distance))
plotManyHistograms(list(itr3.hist), n, "Distance")










heading.hist <- createHistogram(merged.df$heading, 1, length(merged.df$heading), 20, 18)
n<-sprintf("n=%d", length(merged.df$heading))
plotManyHistograms(list(heading.hist), n, "Angle")



colnames(inp.df)
colnames(crossings.df)


crossings.df$B<-NULL


colnames(merged.df)

small<-subset(merged.df, merged.df$replicant==1)

length(merged.df$B)
length(crossings.df$iteration)
length(inp.df$iteration)



plot(merged.df$heading)

