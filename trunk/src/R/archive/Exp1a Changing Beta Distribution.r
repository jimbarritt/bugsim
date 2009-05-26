source("~/Work/codemsc/bugsim/resource/R/Exp1a Library.r", echo=FALSE)

	experimentNumber<-11
	trialId<-"TrDEBUG"
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

	numReplicants<-length(inp.df$replicant)/length(levels(inp.df$iteration))
	numReplicants

plotVaryingBetaDist(inp.df,baseFilename, experimentNumber, trialId, numReplicants)


mean(inp.df$CENTRE_RATIO[inp.df$iteration==1])

plot0vs800Results(inp.df,baseFilename, experimentNumber, trialId, numReplicants)


directory<-createDirectoryName(basefilename, experimentNumber, trialId)
filename<-sprintf("%s/test.jpg", directory)
jpeg(filename=filename, width=480, height=480)
plot(inp.df$CENTRE_RATIO~inp.df$L)
dev.off()