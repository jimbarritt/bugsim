# A test script for the Experiment Data Library...
#Always put this first this way this is the only one that needs the full path in it...
source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")
#Then you can use:
lib.loadLibrary("/thesis/common/Experiment Data Library.r")


experimentPlan<-ExperimentData.loadPlan("raw", "sense", "exp2", "TrA2", 1)
experimentPlan
summary(experimentPlan)

dir(experimentPlan@experimentDir)



itr.1<-getIteration(experimentPlan, 1)
rep.1<-getReplicate(itr.1, 1)
rep.1
itr.1@replicates

