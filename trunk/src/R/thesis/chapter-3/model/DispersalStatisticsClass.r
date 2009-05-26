

setClass("DispersalStatistics", representation(
	experimentPlan="ExperimentPlan",
	summary.df="data.frame",
	logTimeIntervals="vector"

))


#Add a constructor:
DispersalStatistics<-function(experimentPlan, iterationNumber) {
	instance<-new("DispersalStatistics")
		
	instance@experimentPlan<-experimentPlan
	instance@summary.df<-DispersalStatistics.readStatisticsDataFrame(experimentPlan, iterationNumber)	

	instance@logTimeIntervals<-DispersalStatistics.generateLogTimeIntervals()
	
	
	return (instance)
}

#Works out what time intervals to use in order to get an even sampling of the points on the 
#log graphs...
DispersalStatistics.generateLogTimeIntervals<-function() {
	x<-seq(from=10 ,to=100, by=10)
	max<-log(1500)
	min<-log(10)
	interval<-(max-min)/20

	x<-seq(from=min, to=max, by=interval)
	x<-c(log(1:9), x)
	return (round(exp(x)))
}
DispersalStatistics.readStatisticsDataFrame<-function(experimentPlan, iterationNumber) {
	dispersalStatsFile<-sprintf("%s/Iteration-%03.0f/Iteration-dispersal.csv", experimentPlan@experimentDir, iterationNumber)
	cat("Reading Dispersal Statistics from ", dispersalStatsFile, " \n")
	dispersal.df<-read.csv(dispersalStatsFile, header=TRUE, as.is=TRUE)#as.is stops it creating factors
	
	dispersal.copy.df<-data.frame(dispersal.df$iteration)
	dispersal.copy.df$iteration <- as.factor(dispersal.df$iteration)
	dispersal.copy.df$replicant <- dispersal.df$replicant
	dispersal.copy.df$AGE <- dispersal.df$age
	dispersal.copy.df$X <- as.factor(dispersal.df$age)
	dispersal.copy.df$N<-dispersal.df$dispsq.N
	dispersal.copy.df$MEAN<-dispersal.df$dispsq.MEAN
	dispersal.copy.df$SE<-dispersal.df$dispq.STD_ERR
	dispersal.copy.df$MEAN_MINUS_ERR<-dispersal.df$dispsq.MEAN_MINUS_ERR
	dispersal.copy.df$MEAN_PLUS_ERR<-dispersal.df$dispsq.MEAN_PLUS_ERR
	dispersal.copy.df$VARIANCE<-dispersal.df$dispsq.VARIANCE
	
	return (dispersal.copy.df)
	
}





#Add a "toString" which is a "show method"
setMethod("show", "DispersalStatistics", 
	function(object) {

		cat("Dispersal Statistics   : ",object@experimentPlan@experimentDirName, "\n")

	}
)


#And a "summary" method
setMethod("summary", "ForagerSummary", 
	function(object) {
	}
)


setGeneric("formatTimeIntervals", 
	function(dispersalStatistics) {
		standardGeneric("formatTimeIntervals")
	}
)
#This method is so its easy to import / paste them into the bugsim code...
setMethod("formatTimeIntervals", signature=c("DispersalStatistics"),
	function(dispersalStatistics) {
		intervals<-dispersalStatistics@logTimeIntervals
		output<-""
		for (i in 1:length(intervals)) {
			output<-paste(output, intervals[[i]])
			if (i != length(intervals)) {
				output<-paste(output, ", ")
			}
		}
		return (output)
	}
)


setMethod("plot", "DispersalStatistics",
	function(x, y, ...) {
		result<-md.simplePlot(dispersalStats=x,iteration=iteration,  titleString=titleString, ageLimit=ageLimit)
		return(result)
	}
)







