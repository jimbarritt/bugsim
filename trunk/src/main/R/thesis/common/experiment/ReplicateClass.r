#Represents an iteration - parameters and such ...



#Class definition for an IterationReplicate class

setClass("IterationReplicate", representation(
	experimentPlan="ExperimentPlan",
	iterationNumber="numeric",
	replicateNumber="numeric",
	replicateDir="character",
	executedTimesteps="numeric",
	elapsedTime="character",
	elapsedTimeMS="numeric",
	outputDir="character",
	foragersAlive="numeric",
	foragersRemaining="numeric",
	foragersDead="numeric",
	foragersEscaped="numeric", 
	eggCount="numeric"

))


#Add a constructor:
IterationReplicate<-function() {
	instance<-new("IterationReplicate")				
	return (instance)
}

IterationReplicate.readReplicatesDataFrame<-function(experimentPlan, iterationNumber) {
	replicateStatsFile<-sprintf("%s/Iteration-%03.0f/Iteration-stats.csv", experimentPlan@experimentDir, iterationNumber)
#	cat("Reading Stats Summary [", iterationNumber, "] from ", replicateStatsFile, " \n")
	rep.stats.df<-read.csv(replicateStatsFile, header=TRUE, as.is=TRUE)#as.is stops it creating factors
	return (rep.stats.df)
	
}

IterationReplicate.readReplicates<-function(experimentPlan, replicateStats.df, iterationNumber, replicateCount) {
	
	replicates<-list()
	if (replicateCount>0) {
		for (replicateNumber in 1:replicateCount) {
			instance<-IterationReplicate()
			instance@experimentPlan<-experimentPlan
			#TODO: need to update this to deal with the multiple generation stuff...
			subset.replicate.df<-subset(replicateStats.df, replicateStats.df$replicate==replicateNumber)
			
			subset.df<-subset(subset.replicate.df, subset.replicate.df$type=="IMMIGRATION")
			if (length(subset.df$replicate)==0) {
				stop(sprintf("No details in the replicate summary file for replicate number %d, iteration %03.0f", replicateNumber, iterationNumber))
			}
			instance@replicateDir<-sprintf("%s/Iteration-%03.0f/Replicate-%03.0f", experimentPlan@experimentDir, iterationNumber, replicateNumber)
			instance@replicateNumber<-subset.df$replicate
			instance@iterationNumber<-iterationNumber
			instance@executedTimesteps<-subset.df$executedTimesteps
			instance@elapsedTime<-subset.df$elapsedTime
			if (!is.null(subset.df$elapsedTimeMS)) {
				instance@elapsedTimeMS<-subset.df$elapsedTimeMS
			} else {
				instance@elapsedTimeMS<-NaN
			}
			if (length(instance@elapsedTimeMS)==0) {
				stop("Elapsed time is zero length!")
			}
			instance@outputDir<-subset.df$outputDir
			instance@foragersAlive<-subset.df$foragersAlive
			instance@foragersDead<-subset.df$foragersDead
			instance@foragersEscaped<-subset.df$foragersEscaped
			instance@foragersRemaining<-subset.df$foragersRemaining
			instance@eggCount<-subset.df$eggCount
 
			replicates<-collection.appendToList(replicates, instance)
		}				
	}

	return (replicates)
}




#Add a "toString" which is a "show method"
setMethod("show", "IterationReplicate", 
	function(object) {

		cat("replicateNumber    : ",object@replicateNumber, "\n")
		cat("replicateDir       : ",object@replicateDir, "\n")
		cat("executedTimesteps  : ",object@executedTimesteps, "\n")
		if (!is.na(object@elapsedTimeMS)) {
			cat("elapsedTimeMS      : ",Time.formatMS(object@elapsedTimeMS), "\n")
		} else {
			cat("elapsedTime        : ",object@elapsedTime, "\n")
		}
		cat("outputDir          : ",object@outputDir, "\n")
		cat("foragersAlive      : ",object@foragersAlive, "\n")
		cat("foragersDead       : ",object@foragersDead, "\n")
		cat("foragersEscaped    : ",object@foragersEscaped, "\n")
		cat("foragersRemaining  : ",object@foragersRemaining, "\n")		
		cat("eggCount  : ",object@eggCount, "\n")		

	}
)


#And a "summary" method
setMethod("summary", "IterationReplicate", 
	function(object) {
	}
)



