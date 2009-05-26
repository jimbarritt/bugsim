#this is a specific set of scripts that draw a comparison between butterflies released at B=0 and B=800

source("~/Work/codemsc/bugsim/resource/R/Exp1a Library.r", echo=FALSE)

	experimentNumber<-2
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

	numReplicants<-length(inp.df$replicant)/length(levels(inp.df$iteration))
	numReplicants

#Plot the data comparing B=0 to B=800 Summary ...
	plot0vs800Results(inp.df,baseFilename, experimentNumber, trialId, numReplicants)


