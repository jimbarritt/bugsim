#Calculate the mean squared dispersal distance for the butterflies over time.

#This just shows you the summary of the experiment
source("~/Work/codemsc/bugsim/resource/R/Bugsim R Library.r", echo=FALSE)
experimentNumber<-2
trialId<-"TrB"
baseFilename<-"mdd-exp01b"


inp.df <- importIteration1bDispersal(baseFilename, experimentNumber, trialId)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)
levels(inp.df$iteration)
as.numeric(levels(inp.df$X))
length(levels(inp.df$X))
itrBlock<-length(levels(inp.df$X))
numReplicants<-inp.df$N[1]
numReplicants

title<-sprintf("L=1 A=20 n=%d", numReplicants)
directory<-createDirectoryName(basefilename, experimentNumber, trialId)
filename<-sprintf("%s/MDD Variation %s.pdf", directory, title)
resultsA <- plotResults1B(inp.df,"iteration", title, filename)





#Old stuff




#this loads data from the summary file where stats are automatically calculated
stats.A1.df <- importButterflyStats(baseFilename, experimentId, trialId, 1, itrBlock)
stats.B1.df <- importButterflyStats(baseFilename, experimentId, trialId, (itrBlock+1), (itrBlock*2))
stats.C1.df <- importButterflyStats(baseFilename, experimentId, trialId, ((itrBlock*2)+1), (itrBlock*3))
stats.D1.df <- importButterflyStats(baseFilename, experimentId, trialId, ((itrBlock*3)+1), (itrBlock*4))



quartz(width=8.75, height=8.25)
pdf(sprintf("Test Of Summary Statistics.pdf", n), width=8.25, height=8.25)
par(mfrow=c(2,2))
n<-numReplicants
resultsA <- plotResults1B(stats.A1.df, sprintf("L=1, A=20, n=%s", n))
resultsB <- plotResults1B(stats.B1.df, sprintf("L=1, A=20, n=%s", n))
resultsC <- plotResults1B(stats.C1.df, sprintf("L=1, A=20, n=%s", n))
resultsD <- plotResults1B(stats.D1.df, sprintf("L=1, A=20, n=%s", n))
dev.off()















#-------------------------------------------------------------------------------------------------------------------------------------
#Old shit......

#These load data directly from the butterflies individual output
stats.A.df <- processExperimentData(baseFilename, experimentId, trialId, 1, itrBlock)
stats.B.df <- processExperimentData(baseFilename, experimentId, trialId, (itrBlock+1), (itrBlock*2))
stats.C.df <- processExperimentData(baseFilename, experimentId, trialId, ((itrBlock*2)+1), (itrBlock*3))
stats.D.df <- processExperimentData(baseFilename, experimentId, trialId, ((itrBlock*3)+1), (itrBlock*4))




summary(resultsA.lm)

plot(stats.A1.df$X, stats.A1.df$SE, type="l", lty=1)
lines(sqrt(stats.A1.df$VARIANCE)



#This is so we can break a massive dataset down into bits - we have 100,000 butterflies want to do 4 lots of 25,000 and 2 lots of 50,0000
stats.A.df <- processPartialExperimentData(dir, expId, 1, itrBlock, 1, 30000)
stats.B.df <- processPartialExperimentData(dir, expId, 1, itrBlock, 30001, 60000)
stats.C.df <- processPartialExperimentData(dir, expId, 1, itrBlock, 60001, 90000)
#stats.D.df <- processPartialExperimentData(dir, expId, 1, itrBlock, 75001, 100000)

n="30000"
quartz(width=11.75, height=8.25)
filename<-sprintf("Mean Dispersal Distance Graphs L-1 A-20 MaxAge-1000 n-%s.pdf", n)
pdf(filename, width=11.75, height=8.25)
par(mfrow=c(1,3))
plotResults(stats.A.df, sprintf("L=1, A=20, n=%s", n))
plotResults(stats.B.df, sprintf("L=1, A=20, n=%s", n))
plotResults(stats.C.df, sprintf("L=1, A=20, n=%s", n))
#plotResults(stats.D.df, sprintf("L=1, A=20, n=%s", n))
dev.off()

#Write the stats out so we don't have to calculate them again!
filename<-sprintf("Mean Dispersal Distance Graphs L-1 A-20 MaxAge-1000 n-%s stats.A.df.csv", n)
write.csv(stats.A.df, filename)
filename<-sprintf("Mean Dispersal Distance Graphs L-1 A-20 MaxAge-1000 n-%s stats.B.df.csv", n)
write.csv(stats.B.df, filename)
filename<-sprintf("Mean Dispersal Distance Graphs L-1 A-20 MaxAge-1000 n-%s stats.C.df.csv", n)
write.csv(stats.C.df, filename)
filename<-sprintf("Mean Dispersal Distance Graphs L-1 A-20 MaxAge-1000 n-%s stats.D.df.csv", n)
write.csv(stats.D.df, filename)



#This is for the first run when we ran it as seperate experiments with varying angle of turn
stats.L1.A10.df <- processExperimentData("L-1-A-10","007", 1, 16)
stats.L1.A20.df <- processExperimentData("L-1-A-20","006", 1, 16)
stats.L1.A40.df <- processExperimentData("L-1-A-40","009", 1, 16)
stats.L1.A80.df <- processExperimentData("L-1-A-80","008", 1, 16)

#quartz(width=8.25, height=8.25)
pdf("Mean Dispersal Distance Graphs L-1 A-10 to A-80.pdf", width=8.25, height=8.25)
par(mfrow=c(2,2))
plotResults(stats.L1.A10.df, "L=1, A=10")
plotResults(stats.L1.A20.df, "L=1, A=20")
plotResults(stats.L1.A40.df, "L=1, A=40")
plotResults(stats.L1.A80.df, "L=1, A=80")
dev.off()

#regressionLowerEnd, regressionUpperStart are the indexes into the stats dataframe which you want regression to occur over

readIterationReport <- function(basefilename, experimentId, trialId, iterationId, replicantId, iterationFilename) {
	directory<-createDirectoryName(basefilename, experimentId, trialId)
	filename <- sprintf("%s/Iteration-%s-%s/%03d-%s-%s-%s.csv", directory, iterationId, replicantId, experimentId, iterationId, replicantId, iterationFilename)
	print(sprintf("Reading from '%s'", filename))
	input.df <- read.csv(filename)
	return (input.df)
}


square <- function(input) {
	return (input ^ 2)
}

#iterationCount<-16
#experimentId<-"007"
#startIteration<-1
#endIteration<-2
#stats.A.df
#colnames(s.df)

processExperimentData <-function(baseFilename, experimentId, trialId, startIteration, endIteration) {
	it.summary.df<-importIterationSummary(baseFilename, experimentId, trialId, startIteration, endIteration)
		
	iterations<-c(startIteration:endIteration)
	is.first<-TRUE
	for (itr in iterations) {  
		itrName<-sprintf("%03d", as.numeric(itr))
		t.df <- readIterationReport(baseFilename, experimentId, trialId, itrName, "001", "butterflies") 
		if (is.first) {
			bf.ALL.df <- data.frame(t.df)
			is.first<-FALSE
		} else {
			bf.ALL.df <- merge(bf.ALL.df, t.df, all=TRUE)
		}
	}
	bf.ALL.df$DisplacementSQ<-square(bf.ALL.df$Displacement)
	
	ages<-as.numeric(levels(it.summary.df$age))
	
	stats.df<-createStatsDataFrame(ages)

	for (age in ages) {
		t.df<-subset(bf.ALL.df, bf.ALL.df$Age==age)				
		factor<-t.df$DisplacementSQ
		obs.N <- length(factor)
		obs.mean <-mean(factor)
		obs.sd <- sd(factor)
		obs.stdErr <- obs.sd / sqrt(obs.N)
		obs.variance <- var(factor)
		
		stats.df$MEAN[stats.df$X==age]<-obs.mean
		stats.df$MINUS_ERR[stats.df$X==age] <- (obs.mean - obs.stdErr)
		stats.df$MEAN[stats.df$X==age] <- obs.mean
		stats.df$PLUS_ERR[stats.df$X==age] <- (obs.mean + obs.stdErr)
		stats.df$N[stats.df$X==age] <- obs.N
		stats.df$SD[stats.df$X==age]<-obs.sd
		stats.df$SE[stats.df$X==age]<-obs.stdErr
		stats.df$VARIANCE[stats.df$X==age] <- obs.variance	
	}
	return (stats.df)
}




processPartialExperimentData <-function(directory, experimentId, startIteration, endIteration, startButterfly, endButterfly) {
	iterations<-c(startIteration:endIteration)
	is.first<-TRUE
	for (itr in iterations) {  
		itrName<-sprintf("%03d", as.numeric(itr))
#		print(sprintf("Reading %s", itrName))
		t.df <- readIterationReport(directory, experimentId, itrName, "001", "butterflies") 
		if (is.first) {
			bf.ALL.df <- data.frame(t.df)
			is.first<-FALSE
		} else {
			bf.ALL.df <- merge(bf.ALL.df, t.df, all=TRUE)
		}
	}
#str(bf.ALL.df)


	bf.ALL.df$DisplacementSQ<-square(bf.ALL.df$Displacement)

#experimentId<-"004"
#directory<-"mdd-exp01b-004"
#startIteration<-1
#endIteration<-19
	filename<-sprintf("%s/summary-mdd-exp01b-%s-001.csv", directory, experimentId)
	print(sprintf("Reading summary: %s", filename))
	it.summary.df<-importIterationSummary(filename, startIteration, endIteration)
	
	ages<-as.numeric(levels(it.summary.df$age))

	stats.df <- data.frame("age"=ages)
	stats.df$MEAN <- 0
	stats.df$MINUS_ERR <- 0
	stats.df$PLUS_ERR<-0
	stats.df$N<-0

	for (age in ages) {
		thisAge.df<-subset(bf.ALL.df, bf.ALL.df$Age==age)		
		t.df <- thisAge.df[startButterfly:endButterfly,]
		
		factor<-t.df$DisplacementSQ
		obs.mean <-mean(factor)
		obs.sd <- sd(factor)
		obs.stdErr <- obs.sd / sqrt(length(factor))
		
		stats.df$MEAN[stats.df$age==age]<-obs.mean
		stats.df$MINUS_ERR[stats.df$age==age] <- (obs.mean - obs.stdErr)
		stats.df$MEAN[stats.df$age==age] <- obs.mean
		stats.df$PLUS_ERR[stats.df$age==age] <- (obs.mean + obs.stdErr)
		stats.df$N <- length(factor)
	}
	return (stats.df)
}





#stats.df

#help(log)

#pdf("double-log-plot-msd-vs-timesteps.pdf", width=11.75, height=8.25)
#plotFactorWithStdError(stats.df, "log(Moves)", "log(MSD) L=1, A=40", stats.df$age, stats.df$MINUS_ERR, stats.df$MEAN, stats.df$PLUS_ERR, logAxes="xy")

#plotLogFactorsWithRegression(stats.df, "log(Moves)", "log(MSD)", stats.df$age, stats.df$MINUS_ERR, stats.df$MEAN, stats.df$PLUS_ERR, 5, 13)
#dev.off()

plotFactorWithStdError <- function(src.df, xaxisFactorName, yaxisName, xaxisFactor, minusStdError, yaxisFactor, plusStdError, logAxes=NULL, c1Upper, c2Lower, titleString) {

		pointType <- 19
		lineColor <- "blue"
		errColor <- "grey"

		title <- sprintf("%s vs %s (%s)", xaxisFactorName, yaxisName, titleString)
		plot(xaxisFactor, yaxisFactor, type="l", lty=2, col=lineColor, ann=FALSE, log=logAxes) #xaxt="n"
		title(main=title, ylab=yaxisName, xlab=xaxisFactorName)
		points(xaxisFactor, yaxisFactor, pch=pointType, col=lineColor)

	
#		lines(xaxisFactor, minusStdError, type="l", lty=2, col=errColor)
#		lines(xaxisFactor, plusStdError, type="l", lty=2, col=errColor)
}


#pdf("double-log-plot-msd-vs-timesteps.pdf", width=11.75, height=8.25)

#plotLogFactorsWithRegression(stats.df, "log(Moves)", "log(MSD)", stats.df$age, stats.df$MINUS_ERR, stats.df$MEAN, stats.df$PLUS_ERR, 5, 13, "L=1, A=20")
#dev.off()

#help(abline)
#abline(a=1.6, b=1.164, col="grey")
#src.df<-stats.df
#xaxisFactor<-stats.df$age
#yaxisFactor<-stats.df$MEAN
#c2Lower<-13
#c1Upper<-5
#plot(stats.L1.A20.df$age, stats.L1.A20.df$MEAN)
#text(80,max(stats.L1.A20.df$MEAN), "hello")
