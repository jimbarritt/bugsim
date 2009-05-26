source("~/Work/code/bugsim/resource/R/Experiment 2/Experiment 2a Library.r", echo=FALSE)

experimentNumber<-1
trialId<-"TrB1" #TrA
baseFilename<-"sense-exp02a"

rm(inp.df)
rm(informationSurvey.df)
rm(results.vector)
inp.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId, fileNumber=1)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)

numReplicants<-summarise(inp.df, plan.df)


informationSurvey.df<-readInformationSurvey(baseFilename, experimentNumber, trialId)
max(informationSurvey.df$surveyValue)
surfaceName<-"cabbageInformation"
#debug(createSurveyMatrix)
#undebug(createSurveyMatrix)
results.vector<-createSurveyMatrix(informationSurvey.df, surfaceName)
#plotSurveyGraphs(results.vector, surfaceName, baseFilename, experimentNumber, trialId)
outputGraphs(results.vector, surfaceName, baseFilename, experimentNumber, trialId)
dev.off()#need to find a way of waiting until the wireframe is completed before turning off the device.


#Display graphs of the simulation results...
fieldLayoutDirectory<-"~/Documents/Projects/msc/Simulation Input"
fieldLayoutFilename<-"Field E Levin II 2006 layout"
fieldresultsFilename<-"LevinII-no-p.csv"
experimentDir<-createDirectoryName(basefilename, experimentNumber, trialId)

#undebug(plotManyResults)
layout.df<-loadCabbageFieldLayout(fieldLayoutDirectory, fieldLayoutFilename)
all.results.list<-readTrialBResults(layout.df, inp.df, inp.df$iteration, numReplicants, baseFilename, experimentNumber, trialId)


i.all.stats.list<-all.results.list[[1]]
plotTitle<-NULL
i.stats.list<-list(i.all.stats.list[[2]], i.all.stats.list[[3]])
linetypes<-c(2, 1)

plotManyResults(i.stats.list, plotTitle, NULL, NULL, "%s", plotErr=TRUE, legendFactor=factor(c("1x1m", "6x6m")), continuousX=FALSE, lineColors=c("#3333CC", "#000099"),  lineTypes=linetypes,legendPosition="topright", yLim=c(0, 25), cex.axis=1.4)			



outputTrialBResults(all.results.list, inp.df, baseFilename, experimentNumber, trialId) 
