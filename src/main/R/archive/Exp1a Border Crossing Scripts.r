
source("/Users/Jim/Work/code/bugsim/src/R/archive/Exp1a Library.r")

experimentNumber<-1
trialId<-"TrF"
baseFilename<-"edge-exp01a"

rm(inp.df)
setwd("/Users/Jim/Work/Projects/bugsim/experimental-data")
inp.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)
levels(inp.df$iteration)
levels(inp.df$L)
levels(inp.df$B)
levels(inp.df$replicant)
numReplicants<-length(inp.df$replicant)/length(levels(inp.df$iteration))
numReplicants

mean(inp.df$CENTRE_RATIO)
mean(inp.df$CORNER_RATIO)
mean(inp.df$TOTAL_RATIO)

crossings.df<-importBoundaryCrossings(baseFilename, experimentNumber, trialId)
colnames(crossings.df)

str(crossings.df$B)
plotBorderCrossingResults(crossings.df)

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

