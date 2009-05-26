source("~/Work/code/bugsim/resource/R/Exp1a Library.r", echo=FALSE)

experimentNumber<-5
trialId<-"TrI"
baseFilename<-"edge-exp01a"

rm(inp.df)
inp.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)
numReplicants<-describeExperiment1a(inp.df)



results<-readIterationsTrialI(inp.df$iteration, baseFilename, experimentNumber, trialId)
maps.list<-results[[1]]
M.list<-levels(inp.df$L)

title<-sprintf("Maps of Random walker cabbage intersections for I 0-90 a 0-180 L 1-120 Trial %s-%03d",  trialId, experimentNumber)
plotTitle<-"L=%0.0f"

outputTrialH(maps.list, M.list, baseFilename, experimentNumber, trialId, title, plotTitle)

ratios.list<-calculateRatiosFromMaps(maps.list)
outputRatios(ratios.list, M.list, baseFilename, experimentNumber, trialId, "L=%0.0f", "Centre-Corner ratios for varying L")


#iterations<-inp.df$iteration
#itr<-iterations[[1]]
readIterationsTrialI<-function(iterations, numReplicants, baseFilename, experimentNumber, trialId) {
	matrices.list<-list()
	M.list<-list()
	for (itr in iterations) {
		itrContents.list<-readIterationTrialIJ(itr, replicant, baseFilename, experimentNumber, trialId)

		matrices.list<-appendToList(matrices.list, itrContents.list[[1]])		
		M.list<-appendToList(M.list, itrContents.list[[2]])
	}
	results<-list(matrices.list, M.list)
	return (results)
}



#itr<-1
