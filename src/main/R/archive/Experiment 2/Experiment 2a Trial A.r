source("~/Work/code/bugsim/resource/R/Experiment 2/Experiment 2a Library.r", echo=FALSE)

experimentNumber<-3
trialId<-"TrA6"
baseFilename<-"sense-exp02a"




rm(inp.df)
inp.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId, fileNumber=1)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)


numReplicants<-summarise(inp.df, plan.df)

colnames(inp.df)


informationSurvey.df<-readInformationSurvey(baseFilename, experimentNumber, trialId)

surfaceName<-"cabbageInformation"
#debug(createSurveyMatrix)
#undebug(createSurveyMatrix)
results.vector<-createSurveyMatrix(informationSurvey.df, surfaceName)
plotSurveyGraphs(results.vector, surfaceName, baseFilename, experimentNumber, trialId)
outputGraphs(results.vector, surfaceName, baseFilename, experimentNumber, trialId)
dev.off()#need to find a way of waiting until the wireframe is completed before turning off the device.




