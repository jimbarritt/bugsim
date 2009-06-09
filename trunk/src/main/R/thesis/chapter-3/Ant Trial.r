#This trial is for stephens ant data... 
# a 14x14 resource grid is created and different parameters are tried.
lib.loadLibrary("/common/Graphing Utilities Library.r")
lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/common/summarystats/Summary Statistics Library.r")
lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")
lib.loadLibrary("thesis/common/dissimilarity/bray-curtis library.r")
lib.loadLibrary("/thesis/common/circular/Circular Distributions Library.r")
lib.loadLibrary("/thesis/chapter-3/model/Ant Library.r")


rootDir<-ExperimentData.getPublishedRootDir()
layoutDir<-sprintf("%s/chapter-3/resource-layouts", rootDir)
out.filename<-sprintf("%s/ants-patch-A.csv", layoutDir)
ResourceLayout.generateLayout(out.filename, 1400, 14)


quartz(width=9, height=8)
experimentPlan<-ExperimentData.loadPlan("03", "ant", "expANT", "TrA1", 1)
ant.A.df<-AntLibrary.loadBaitCabbages(out.filename, experimentPlan, 1)
mx<-AntLibrary.outputBaitMatrix(ant.A.df)
AntLibrary.plotSurface(mx)

ant.A.df<-AntLibrary.loadBaitCabbages(out.filename, experimentPlan, 2)
mx<-AntLibrary.outputBaitMatrix(ant.A.df)
AntLibrary.plotSurface(mx)

experimentPlan<-ExperimentData.loadPlan("03", "ant", "expANT", "TrA2", 1)
ant.A.df<-AntLibrary.loadBaitCabbages(out.filename, experimentPlan, 1)
mx<-AntLibrary.outputBaitMatrix(ant.A.df)
AntLibrary.plotSurface(mx)

ant.A.df<-AntLibrary.loadBaitCabbages(out.filename, experimentPlan, 2)
mx<-AntLibrary.outputBaitMatrix(ant.A.df)
AntLibrary.plotSurface(mx)









########################################################################################
#old stuff:



experimentPlan<-ExperimentData.loadPlan("raw", "ant", "expANT", "TrA", 6)
ant.A.df<-AntLibrary.loadBaitCabbages(out.filename, experimentPlan)
AntLibrary.outputBaitMatrix(ant.A.df)

out.filename<-sprintf("%s/ants-patch-B.csv", layoutDir)
ResourceLayout.generateLayout(out.filename, 2800, 14)

experimentPlan<-ExperimentData.loadPlan("raw", "ant", "expANT", "TrB", 2)
ant.B.df<-AntLibrary.loadBaitCabbages(out.filename, experimentPlan)
AntLibrary.outputBaitMatrix(ant.B.df)

out.filename<-sprintf("%s/ants-patch-C.csv", layoutDir)
ResourceLayout.generateLayout(out.filename, 5600, 14)

experimentPlan<-ExperimentData.loadPlan("raw", "ant", "expANT", "TrC", 1)
ant.B.df<-AntLibrary.loadBaitCabbages(out.filename, experimentPlan)
AntLibrary.outputBaitMatrix(ant.B.df)

out.filename<-sprintf("%s/ants-patch-D.csv", layoutDir)
ResourceLayout.generateLayout(out.filename, 11200, 14)

experimentPlan<-ExperimentData.loadPlan("raw", "ant", "expANT", "TrD", 1)
ant.B.df<-AntLibrary.loadBaitCabbages(out.filename, experimentPlan)
AntLibrary.outputBaitMatrix(ant.B.df)

experimentPlan@iterations[[1]]@replicates[[1]]