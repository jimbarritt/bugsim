#Scripts for processing output of the simulation

#-------------------------
# This script assumes you have just a summary file which includes each iteration changing the release boundary
# You could combine different experiments as long as they have the rigth structure.

source("~/Work/codemsc/bugsim/resource/R/Bugsim R Library.r", echo=FALSE)

experimentNumber<-1
trialId<-"TrF"
baseFilename<-"edge-exp01a"

rm(inp.df)
inp.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)
#Some interesting description of the experiment
#str(inp.df)
levels(inp.df$iteration)
levels(inp.df$B)
levels(inp.df$L)
levels(inp.df$A)

numReplicates<-length(inp.df$replicant)/length(levels(inp.df$iteration))
numReplicates


#t-test of means of centre ratios
attach(inp.df)
subset.df<-data.frame(iteration, replicant, B, CENTRE_RATIO, CORNER_RATIO, TOTAL_RATIO, CENTRE_EGGS, CORNER_EGGS, TOTAL_EGGS)
detach(inp.df)
subset.df

attach(subset.df)

B0.df<-subset(subset.df, subset.df$B==0)
B800.df<-subset(subset.df, subset.df$B==800)
plot(B0.df$replicant,B0.df$CENTRE_RATIO, type="l", ylim=c(0.36, 0.49), col="blue")
lines(B800.df$replicant,B800.df$CENTRE_RATIO, col="green")

plot(replicant,CENTRE_RATIO, type="l")

detach(subset.df)

attach(subset.df)

plotTwoFactors("CENTRE_RATIO", "Centre Ratio", inp.df, baseFilename, experimentNumber, trialId, numReplicates, newPlot=FALSE, plotReverse=FALSE)
plotTwoFactors("TOTAL_EGGS", "Total Eggs", inp.df, baseFilename, experimentNumber, trialId, numReplicates, newPlot=FALSE, plotReverse=FALSE)
plotTwoFactors("ESCAPED_BUTTERFLIES", "Escaped Butterflies", inp.df, baseFilename, experimentNumber, trialId, numReplicates, newPlot=FALSE, plotReverse=FALSE)	




#--------------------------------------------------------------------------------------------------------------------------------------------
#Old shit....








str(inp.df$B[[1]])

stats.A.df <- getSummaryStats(inp.df, 1, itrBlock, inp.df$iteration, inp.df$B, "B", "Release Distance", factorName)
stats.B.df <- getSummaryStats(inp.df, ((itrBlock*1)+1), (itrBlock*2), inp.df$iteration, inp.df$B, "B", "Release Distance", factorName)
stats.C.df <- getSummaryStats(inp.df, ((itrBlock*2)+1), (itrBlock*3), inp.df$iteration, inp.df$B, "B", "Release Distance", factorName)
stats.D.df <- getSummaryStats(inp.df, ((itrBlock*3)+1), (itrBlock*4),  inp.df$iteration, inp.df$B, "B", "Release Distance", factorName)
stats.E.df <- getSummaryStats(inp.df, ((itrBlock*4)+1), (itrBlock*5), inp.df$iteration, inp.df$B, "B", "Release Distance", factorName)
#stats.F.df <- getSummaryStats(inp.df, ((itrBlock*5)+1), (itrBlock*6), inp.df$iteration, inp.df$B, "B", "Release Distance", factorName)


#Put the list of the stats you want into the all.stats array ....
all.stats<-list(stats.A.df, stats.B.df, stats.C.df, stats.D.df, stats.E.df)


#This is just to do the first four
#all.stats<-list(stats.A.df, stats.B.df, stats.C.df, stats.D.df)
#all.stats<-list(stats.B.df)

#Writes out CSV files for all the stats so we dont have to process them again each time.
writeStats(all.stats, baseFilename, experimentNumber, trialId)

# This can be used to read them back in later.
all.stats<-readStats(baseFilename, experimentNumber, trialId)

















#Old stuff.....
directory <- createDirectoryName(baseFilename, experimentNumber, trialId)
filename<-sprintf("%s/TRIAL %s - N-1000 - reps-%d - %s.pdf", directory, trialId, numReplicates, factorTitle) 
print("Writing Graph to '%s'")
pdf(filename, width=8.75, height=8.25)

lineCols<-rainbow(length(levels(inp.df$B)))
iB<-1
eggStats.df$TOTAL_EGGS<-0
eggStats.df<-data.frame("L"=as.numeric(levels(inp.df$L)))
eggStats.df$TOTAL_EGGS[[1]]<-stats.A.df$MEAN[[iB]]
eggStats.df$TOTAL_EGGS[[2]]<-stats.B.df$MEAN[[iB]]
eggStats.df$TOTAL_EGGS[[3]]<-stats.C.df$MEAN[[iB]]
eggStats.df$TOTAL_EGGS[[4]]<-stats.D.df$MEAN[[iB]]
eggStats.df$TOTAL_EGGS[[5]]<-stats.E.df$MEAN[[iB]]

plot(eggStats.df, type="l", lty=2, ylim=c(0, 250),xlim=c(1, 85) ,col=lineCols[iB],ylab="Total Eggs")
title("Total Eggs Vs L And B, A=20, S=10, N=1000, REPS=100")
points(eggStats.df$L, eggStats.df$TOTAL_EGGS, pty=2, col=lineCols[iB])
for (iB in 2:5) {
eggStats.df$TOTAL_EGGS<-0
eggStats.df<-data.frame("L"=as.numeric(levels(inp.df$L)))
eggStats.df$TOTAL_EGGS[[1]]<-stats.A.df$MEAN[[iB]]
eggStats.df$TOTAL_EGGS[[2]]<-stats.B.df$MEAN[[iB]]
eggStats.df$TOTAL_EGGS[[3]]<-stats.C.df$MEAN[[iB]]
eggStats.df$TOTAL_EGGS[[4]]<-stats.D.df$MEAN[[iB]]
eggStats.df$TOTAL_EGGS[[5]]<-stats.E.df$MEAN[[iB]]
lines(eggStats.df$L, eggStats.df$TOTAL_EGGS, lty=2, col=lineCols[iB])
points(eggStats.df$L, eggStats.df$TOTAL_EGGS, pty=2, col=lineCols[iB])

}
legends2<-levels(inp.df$L)
legends2<-createFactorLegend("B=%s", inp.df$B)
legend("topleft",horiz=TRUE, legend=legends2, fill=lineCols, inset=0.05)
dev.off()
#===============================================================================================================================================================
#Experimental
help(errbar)

#From here is all old stuff with variations on the theme.

experimentId<-16
directory<-sprintf("edge-effect-exp01a-%03d", experimentId)
summaryFilename<-sprintf("%s/summary-edge-effect-exp01a-%03d-001.csv", directory, experimentId)
expSummary.df <- importIterationSummary(summaryFilename)
expSummary.df
is.factor(expSummary.df$iteration)
length(expSummary.df$iteration)


#Experiment 1a : plotting effect of release boundary distance against centre effect...
#quartz(width=11.75, height=8.25)
pdf("experiment 1a summary - L=5 S=10.pdf" , width=11.75, height=8.25)
par(mfrow=c(1, 2))
maxIt <- length(levels(expSummary.df$iteration))
plot2FactorsWithStdErr(expSummary.df, 1, maxIt, expSummary.df$iteration, expSummary.df$B, "B", "Release Distance", "CENTRE_RATIO")
plot2FactorsWithStdErr(expSummary.df, 1, maxIt, expSummary.df$iteration, expSummary.df$B, "B", "Release Distance", "timesteps")
dev.off()

#DRAW BOTH TOGETHER:
baseFilename<-"summary-edge-effect-exp01a-015"
filenameA<-sprintf("%s-001.csv", baseFilename)
filenameB<-sprintf("%s-002.csv", baseFilename)
filenameA<-summaryFilename
inp.A.df <- importIterationSummary(filenameA)
inp.B.df <- importIterationSummary(filenameB)
str(inp.A.df)
levels(inp.A.df$iteration)
levels(inp.A.df$B)
stats.L01.A20.df <- getSummaryStats(inp.A.df, 1, 5, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
stats.L05.A20.df <- getSummaryStats(inp.A.df, 6, 10, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
stats.L10.A20.df <- getSummaryStats(inp.A.df, 11, 15, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
stats.L20.A20.df <- getSummaryStats(inp.A.df, 16, 20, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
stats.L40.A20.df <- getSummaryStats(inp.A.df, 21, 25, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
stats.L80.A20.df <- getSummaryStats(inp.A.df, 26, 30, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
all.stats<-list(stats.L01.A20.df, stats.L05.A20.df, stats.L10.A20.df, stats.L20.A20.df, stats.L40.A20.df, stats.L80.A20.df)
legends<-c("L=1", "L=5","L=10","L=20","L=40","L=80")

all.stats<-list(stats.L20.A20.df, stats.L40.A20.df, stats.L80.A20.df)
legends<-c("L=20","L=40","L=80")

stats.L01.A20.df <- getSummaryStats(inp.A.df, 1, 2, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
stats.L05.A20.df <- getSummaryStats(inp.A.df, 3, 4, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
stats.L80.A20.df <- getSummaryStats(inp.A.df, 5, 6, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "CENTRE_RATIO")
all.stats<-list(stats.L01.A20.df, stats.L05.A20.df, stats.L80.A20.df)
legends<-c("L=1", "L=5","L=80")

all.stats<-list(stats.L01.A20.df)
legends<-c("L=1")

df.list<-all.stats
title<-"Release boundary vs L (A=20, S=10)"
xaxisLabel<-"B - Release Distance"
yaxisLabel<-"Centre Ratio"

pdf("Varying L and B S=10 A=20.pdf" , width=11.75, height=8.25)
#levels(expSummary.df$L)

#levels(expSummary.df$iteration)



stats.A1.df <- getSummaryStats(inp.A.df, 1, 5, inp.A.df$iteration, inp.A.df$B, "B", "Release Distance", "L")

stats.B.df <- getSummaryStats(inp.B.df, 10, 18, inp.B.df$iteration, inp.B.df$B, "B", "Release Distance", "CENTRE_RATIO")
stats.B1.df <- getSummaryStats(inp.B.df, 10, 18, inp.B.df$iteration, inp.B.df$B, "B", "Release Distance", "timesteps")
plot(stats.A.df$X, stats.A.df$MEAN)

quartz(width=11.75, height=8.25)
outputFilename <-  "experiment 1a summary - L=1 S=10 vs L=60 S=10 (A=20)"
pdf(sprintf("%s.pdf", outputFilename) , width=11.75, height=8.25)
#png(file=sprintf("%s.png", outputFilename))
par(mfrow=c(1, 2))
plot2Results1A(stats.A.df, stats.B.df, "Release Distance", "Centre Ratio", "L=1, S=10, A=20", "L=60, S=10, A=20")
plot2Results1A(stats.A1.df, stats.B1.df, "Release Distance", "Timesteps", "L=1, S=10, A=20", "L=60, S=10, A=20")
dev.off()



# Plot summary of border distance effects
quartz(width=10, height=7)
par(mfrow=c(1, 2))
plot2Factors(expSummary.df, "Border Distance", "Timesteps for 100 eggs", expSummary.df$B, expSummary.df$timesteps) 
plot2Factors(expSummary.df, "Border Distance", "Centre Ratio", expSummary.df$B, expSummary.df$CENTRE_RATIO) 

# Plot Interedge separation for varying values of L + A
outputGraphs(expSummary.df, "S", "L", "A", expSummary.df$S, expSummary.df$L, expSummary.df$A)

# Plot MoveLength vs Angle of turn and S
outputGraphs(expSummary.df,  "L",  "S","A", expSummary.df$L,   expSummary.df$S, expSummary.df$A)

#This is the most useful view .... Plotting angle of turn on each graph with Step length accross the top and separation down.
outputGraphs(expSummary.df,  "A",  "S","L", expSummary.df$A,   expSummary.df$S, expSummary.df$L)


radiusSubset.df <- subset(expSummary.df, expSummary.df$R=="5")
outputGraphs(radiusSubset.df,  "A",  "S","L", radiusSubset.df$A,   radiusSubset.df$S, radiusSubset.df$L, makePDF=TRUE, pdfFilename="Summary Graphs-exp01-009-R5.pdf")

radiusSubset.df <- subset(expSummary.df, expSummary.df$R=="10")
outputGraphs(radiusSubset.df,  "A",  "S","L", radiusSubset.df$A,   radiusSubset.df$S, radiusSubset.df$L, makePDF=TRUE, pdfFilename="Summary Graphs-exp01-009-R10.pdf")

#Calculations for proportional Border where the last S doesnt work.
IS <- c(0, 1, 2, 3)
IS
S.subset <- factor(IS, levels=sort(unique.default(IS)), labels=c("0", "5", "10", "20"))
S.subset
radiusSubset.df <- subset(expSummary.df, expSummary.df$R=="5")
outputGraphs(radiusSubset.df,  "A",  "S","L", radiusSubset.df$A,   S.subset, radiusSubset.df$L, makePDF=TRUE, pdfFilename="Summary Graphs-exp01-011-PROP-R5.pdf")

radiusSubset.df <- subset(expSummary.df, expSummary.df$R=="10")
outputGraphs(radiusSubset.df,  "A",  "S","L", radiusSubset.df$A,   S.subset, radiusSubset.df$L, makePDF=TRUE, pdfFilename="Summary Graphs-exp01-011-PROP-R10.pdf")


#Plot Radius vs A and L for S=10
T1.df <- subset(expSummary.df, expSummary.df$S=="20")
outputGraphs(T1.df,  "A",  "R","L", T1.df$A,   T1.df$R, T1.df$L)

zeroS.df <- subset(expSummary.df, expSummary.df$S=="0")

zeroS.df
str(expSummary$R)
radiusSubset.df
