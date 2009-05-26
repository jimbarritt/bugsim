source("~/Work/code/bugsim/resource/R/Exp1a Library.r", echo=FALSE)

experimentNumber<-6
trialId<-"TrJ1"
baseFilename<-"edge-exp01a"

rm(inp.df)
inp.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)
numReplicants<-describeExperiment1a(inp.df)



results<-readIterationsTrialI(inp.df$iteration,numReplicants, baseFilename, experimentNumber, trialId)
maps.list<-results[[1]]
M.list<-levels(inp.df$L)

title<-sprintf("Maps of Random walker cabbage intersections for I 0-90 a 0-180 L 1-120 Trial %s-%03d",  trialId, experimentNumber)
plotTitle<-"L=%0.0f"

outputTrialH(maps.list, M.list, baseFilename, experimentNumber, trialId, title, plotTitle)

ratios.list<-calculateRatiosFromMaps(maps.list)
outputRatios(ratios.list, M.list, baseFilename, experimentNumber, trialId, "L=%0.0f", "Centre-Corner ratios for varying L")


#this does an averaging over several replicants, resulting in an image which contains ratios from 0 to 1 of corner cabbages
#might need to do 2 1 for corner and 1 for cente...
results<-readIterationsTrialJ(levels(inp.df$iteration),numReplicants, baseFilename, experimentNumber, trialId)
maps.J.list<-results[[1]]
M.list<-levels(inp.df$L)




#purple<-"#9999FF"
colors.v<-generatePalette(11, "white", .2, .6, reverse=TRUE)

plotImageLegend(yData=c(0:10), yLabels=c(0:10)/10, yBreakSize<-0.1, colors.v)

test.mx<-maps.list[[6]]

plotMapTrialH(test.mx, 6, colors.v, "hello", c(0,10))
title<-sprintf("Maps of Random walker cabbage intersections (Replicants %d) for I 0-90 a 0-180 L 1-120 Trial %s-%03d",  numReplicants, trialId, experimentNumber)
outputTrialH(maps.J.list, M.list, baseFilename, experimentNumber, trialId, title, plotTitle, colors.v, c(0,10), drawLegend=FALSE)


