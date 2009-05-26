#Class definition for an ExperimentPlan class...


setClass("ExperimentPlan", representation(
	experimentNumber="numeric",
	experimentDir="character",
	experimentName="character",
	experimentDirName="character",
	trialName="character",
	description="character",
	elapsedTimeMS="ANY",
	iterationCount="numeric",
	replicateCount="numeric",
	iterations="list",
	iterationSummary.df="data.frame",
	plan.df="data.frame",
	ratio.df="data.frame",
	fieldCabbages="FieldCabbages",
	fieldComparisonStats.df="data.frame",
	groupingFactorName="character",
	groupingFactorRawName="character"	
	
))


#Add a constructor:
ExperimentPlan<-function(experimentParentDir, experimentDirName, experimentNumber, fieldCabbages=NULL, groupingFactorName, groupingFactorRawName) {
	instance<-new("ExperimentPlan")
	
	experimentDir<-sprintf("%s/%s", experimentParentDir, experimentDirName)
	planFilename<-sprintf("%s/Experiment-plan.csv", experimentDir)	
	cat(sprintf("Reading plan: %s", planFilename), "\n")
	plan.df <- read.csv(planFilename, as.is=TRUE)

	cat("Initialising ExperimentPlan in : ",experimentDir, "\n")

	
	instance@experimentDir<-experimentDir
	instance@experimentDirName<-experimentDirName	
	instance@experimentNumber<-experimentNumber
	instance@experimentName<-plan.df$experiment.name
	instance@trialName<-as.character(plan.df$plan.trial.name)
	instance@description<-plan.df$plan.description
	instance@iterationCount<-plan.df$experiment.iterations
	instance@replicateCount<-plan.df$experiment.replicates
	instance@groupingFactorName<-groupingFactorName
	instance@groupingFactorRawName<-groupingFactorRawName
	
	instance@plan.df<-plan.df
	
	instance@iterations<-ExperimentPlanClass.readIterations(instance)
	
	instance@elapsedTimeMS<-NA
	for (itr in instance@iterations) {
		if (!is.na(itr@elapsedTimeMS)) {
			if (is.na(instance@elapsedTimeMS)) {
				instance@elapsedTimeMS<-0
			}
			instance@elapsedTimeMS<-instance@elapsedTimeMS+itr@elapsedTimeMS
		}
	}
	
	instance@iterationSummary.df<-ExperimentPlanClass.createIterationDF(instance)
	
	instance@ratio.df<-CalculatedLayout.aggregateRatioResults(instance)

	if (!is.null(fieldCabbages)) {
		cat("Creating Field Comparison Stats\n")
		instance@fieldComparisonStats.df<-ExperimentPlan.createComparisonStatisticsRows(instance, fieldCabbages)
		instance@fieldCabbages<-fieldCabbages
		
	}
	
	return (instance)
}

ExperimentPlan.createComparisonStatisticsRows<-function(experimentPlan, fieldCabbages) {
	groupingFactorName<-experimentPlan@groupingFactorName
	groupingFactorRawName<-experimentPlan@groupingFactorRawName
	
	ratio.df<-experimentPlan@ratio.df
	if(is.null(ratio.df[[groupingFactorName]])) {
		stop(sprintf("Cannot find factor %s in ratio.df", groupingFactorName))
	}
	
	results<-list()
	for (filter.GROUP in levels(ratio.df[[groupingFactorName]])) {
		for (filter.L in levels(ratio.df$LF)) {
			for (filter.A in levels(ratio.df$AF)) {	
				cat(sprintf("Processing results for %s=%0.0f, L=%0.0f, A=%0.2f ...\n", groupingFactorRawName, filter.GROUP, filter.L, filter.A))		
				resultRow<-calculateResultRow(ratio.df,experimentPlan,fieldCabbages, filter.GROUP, filter.L, filter.A, groupingFactorName, groupingFactorRawName)
				results<-collection.appendToList(results, resultRow)
			}
		}
	}
	
	results.df<-convertComparisonStatsToDataFrame(results, groupingFactorName, groupingFactorRawName)
	
	return (results.df)
}

convertComparisonStatsToDataFrame<-function(results.list, groupingFactorName, groupingFactorRawName) {
	cat("Converting to data.frame ...\n")
	result.count<-length(results.list)
	
	results.df<-data.frame("result.ID"=1:result.count)

	results.df$X48m_dens.bc<-rep(NA, result.count)
	results.df$X48m_dens.chisq.statistic<-rep(NA, result.count)
	results.df$X48m_dens.chisq.p.value<-rep(NA, result.count)

	totalBC<-0
	
	for (i in 1:length(results.list)) {
		i.row<-results.list[[i]]
		
		results.df[[groupingFactorRawName]][[i]]<-i.row$GROUP
		results.df$L[[i]]<-i.row$L
		results.df$A[[i]]<-i.row$A
		
		results.df$X1m_dens.bc[[i]]<-i.row$s1x1m.bc@index
		results.df$X1m_dens.chisq.statistic[[i]]<-i.row$s1x1m$statistic
		results.df$X1m_dens.chisq.p.value[[i]]<-i.row$s1x1m$p.value

		results.df$X6m_dens.bc[[i]]<-i.row$s6x6m.bc@index
		results.df$X6m_dens.chisq.statistic[[i]]<-i.row$s6x6m$statistic
		results.df$X6m_dens.chisq.p.value[[i]]<-i.row$s6x6m$p.value

		totalBC<-i.row$scaleCode.bc@index +i.row$s1x1m.bc@index+ i.row$s6x6m.bc@index

		if (!is.null(i.row$s48x48m)) {
			results.df$X48m_dens.bc[[i]]<-i.row$s48x48m.bc@index
			results.df$X48m_dens.chisq.statistic[[i]]<-i.row$s48x48m$statistic
			results.df$X48m_dens.chisq.p.value[[i]]<-i.row$s48x48m$p.value

			totalBC<-totalBC+i.row$s48x48m.bc@index
			meanBC<-totalBC/4
		} else {
			meanBC<-totalBC/3
		}
		
		results.df$X3.scale.code.bc[[i]]<-i.row$scaleCode.bc@index
		results.df$X3.scale.code.chisq.statistic[[i]]<-i.row$scale.code$statistic
		results.df$X3.scale.code.chisq.p.value[[i]]<-i.row$scale.code$p.value				
		
		results.df$mean.bc[[i]]<-meanBC
		
		results.df$dist.field[[i]]<-i.row$dist.field
		results.df$dist.sim[[i]]<-i.row$dist.sim
		
	}
	

	results.df[[groupingFactorName]]<-as.factor(results.df[[groupingFactorRawName]])
	results.df$LF<-as.factor(results.df$L)
	results.df$AF<-as.factor(results.df$A)

	return (results.df)
}

#experimentPlan<-experimentPlan.D6;colnames(ratio.df)
calculateResultRow<-function(ratio.df, experimentPlan, fieldCabbages, filter.GROUP, filter.L, filter.A, groupingFactorName, groupingFactorRawName) {
	ratio.df<-experimentPlan@ratio.df
	ss.df<-subset(ratio.df, ratio.df$A==filter.A)
	ss.df<-subset(ss.df, ss.df$L==filter.L)
	ss.df<-subset(ss.df, ss.df[[groupingFactorRawName]]==filter.GROUP)		
	ss.df$iterationF<-as.factor(ss.df$iteration)


	cabbages.df<-getAggregatedCabbagesByIteration(experimentPlan, as.numeric(levels(ss.df$iterationF)))			
	merged.cabbages.df<-IterationEggDistribution.mergeCabbages(fieldCabbages, cabbages.df)			
	dist.sim<-EggDistribution(experimentPlan@experimentName, merged.cabbages.df, "Simulation.Egg.Count", fieldCabbages)
	
	dist.field<-fieldCabbages@eggDistribution
				
	chisq.1m.result<-calculateChiSqComparison(dist.field, dist.sim, xFactorName="X1m_dens")
	chisq.6m.result<-calculateChiSqComparison(dist.field, dist.sim, xFactorName="X6m_dens")

	

	bc.1m.result<-calculateBrayCurtisComparison(dist.field, dist.sim, xFactorName="X1m_dens")
	bc.6m.result<-calculateBrayCurtisComparison(dist.field, dist.sim, xFactorName="X6m_dens")
	
	chisq.scaleCode.result<-calculateChiSqComparison(dist.field, dist.sim, xFactorName="X3.scale.code")
	bc.scaleCode.result<-calculateBrayCurtisComparison(dist.field, dist.sim, xFactorName="X3.scale.code")
	
	
	list.1m<-createChSqList(chisq.1m.result)
	list.6m<-createChSqList(chisq.6m.result)
	list.scaleCode<-createChSqList(chisq.scaleCode.result)
				
	resultRow<-list("dist.field"=dist.field, "dist.sim"=dist.sim,"GROUP"=filter.GROUP, "L"=filter.L,"A"=filter.A,"s1x1m"=list.1m, "s6x6m"=list.6m, 
				"scale.code"=list.scaleCode, "s1x1m.bc"=bc.1m.result,  "s6x6m.bc"=bc.6m.result, "scaleCode.bc"=bc.scaleCode.result)


	if (fieldCabbages@has48mScale) {
		chisq.48m.result<-calculateChiSqComparison(dist.field, dist.sim, xFactorName="X48m_dens")				
		list.48m<-createChSqList(chisq.48m.result)
		bc.48m.result<-calculateBrayCurtisComparison(dist.field, dist.sim, xFactorName="X48m_dens")
		
		resultRow<-list("dist.field"=dist.field, "dist.sim"=dist.sim, "GROUP"=filter.GROUP, "L"=filter.L,"A"=filter.A,"s1x1m"=list.1m, "s6x6m"=list.6m, 
					"s48x48m"=list.48m, "scale.code"=list.scaleCode, "s1x1m.bc"=bc.1m.result,  "s6x6m.bc"=bc.6m.result,  "s48x48m.bc"=bc.48m.result,  "scaleCode.bc"=bc.scaleCode.result)				
	}
	
	return (resultRow)
}

	
createChSqList<-function(chisq.result) {
		
	current<-list(  "p.value"=chisq.result$p.value,
	 				"statistic"=chisq.result$statistic, 
					"d.f"=as.numeric(chisq.result$parameter)
				)
	return (current)
}



calculateChiSqComparison<-function(dist.field, dist.sim, xFactorName) {
	field.eggCountFieldName<-dist.field@eggCountFieldName
	sim.eggCountFieldName<-dist.sim@eggCountFieldName
	
	dist.sim@cabbages.df$Replicate
	
	stats.field<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=dist.field@cabbages.df, xFactorName=xFactorName, yFactorName=field.eggCountFieldName)	
	stats.sim<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=dist.sim@cabbages.df, xFactorName=xFactorName, yFactorName=sim.eggCountFieldName)	

	#Calculate the proportion of eggs in each category so we adjust for the fact that there are more eggs (x number of replicates) in the simulation
	#Work out the proportion in the simulation and then multiply by the number in the field to 
	#provide our observed distribution.	
	field.expected<-stats.field$TOTAL
	simulation.observed<-(stats.sim$TOTAL/sum(stats.sim$TOTAL))*sum(stats.field$TOTAL)

	chi.sq.input<-rbind(field.expected,
						simulation.observed)

	chisq.result.raw<-chisq.test(chi.sq.input)
	return (chisq.result.raw)
}


calculateBrayCurtisComparison<-function(dist.field, dist.sim, xFactorName) {
	field.eggCountFieldName<-dist.field@eggCountFieldName
	sim.eggCountFieldName<-dist.sim@eggCountFieldName
	
	dist.sim@cabbages.df$Replicate
	
	stats.field<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=dist.field@cabbages.df, xFactorName=xFactorName, yFactorName=field.eggCountFieldName)	
	stats.sim<-SummaryStatistics.createUngroupedSummaryStatistics(input.df=dist.sim@cabbages.df, xFactorName=xFactorName, yFactorName=sim.eggCountFieldName)	

	#Calculate the proportion of eggs in each category so we adjust for the fact that there are more eggs (x number of replicates) in the simulation
	#Work out the proportion in the simulation and then multiply by the number in the field to 
	#provide our observed distribution.	
#	field.expected<-stats.field$TOTAL
#	simulation.observed<-(stats.sim$TOTAL/sum(stats.sim$TOTAL))*sum(stats.field$TOTAL)

	field.expected<-stats.field$MEAN
	simulation.observed<-stats.sim$MEAN
	result<-brayCurtis.test(field.expected, simulation.observed)

	return (result)
}

ExperimentPlanClass.createIterationDF<-function(instance) {
	colNames<-c("iterationNumber","elapsedTimeMS", "includeLifeHistory","foragersReleased","ageLimit","eggLimit","azimuthGenerator","angleOfTurn","azimuthResolution","moveLength","searchOptimised", "releaseDistance","visionEnabled","visualFieldDepth","visualFieldWidth","olfactionEnabled","olfactionMin","olfactionMax","resourceLayoutName", "releaseType", "releaseAzimuthOptimised", "resourceRadius", "resourceSeparation", "resourcePatchSize", "eggsPerForager", "resourceLayoutSize", "signalWidth")
	rowNames<-1:instance@iterationCount
	names<-list( rowNames, colNames)
	summaryMatrix<-matrix(NA, nrow=instance@iterationCount, ncol=length(colNames),byrow=TRUE,dimnames=names)


	cat("Populating summary for: ", instance@iterationCount, " Iterations...\n")
	for (itr in instance@iterations) {
		summaryMatrix[itr@iterationNumber, 1]<-itr@iterationNumber	
		summaryMatrix[itr@iterationNumber, 2]<-itr@elapsedTimeMS	
		summaryMatrix[itr@iterationNumber, 3]<-as.character(itr@includeLifeHistory)
		summaryMatrix[itr@iterationNumber, 4]<-itr@foragersReleased
		summaryMatrix[itr@iterationNumber, 5]<-itr@ageLimit
		summaryMatrix[itr@iterationNumber, 6]<-itr@eggLimit
		summaryMatrix[itr@iterationNumber, 7]<-itr@azimuthGenerator
		summaryMatrix[itr@iterationNumber, 8]<-itr@angleOfTurn
		summaryMatrix[itr@iterationNumber, 9]<-itr@azimuthResolution
		summaryMatrix[itr@iterationNumber, 10]<-itr@moveLength
		summaryMatrix[itr@iterationNumber, 11]<-itr@searchOptimised
		summaryMatrix[itr@iterationNumber, 12]<-itr@releaseDistance
		summaryMatrix[itr@iterationNumber, 13]<-itr@visionEnabled
		summaryMatrix[itr@iterationNumber, 14]<-itr@visualFieldDepth
		summaryMatrix[itr@iterationNumber, 15]<-itr@visualFieldWidth
		summaryMatrix[itr@iterationNumber, 16]<-itr@olfactionEnabled
	#	summaryMatrix[itr@iterationNumber, 17]<-itr@olfactionMinSensitivity
	#	summaryMatrix[itr@iterationNumber, 18]<-itr@olfactionMaxSensitivity
		summaryMatrix[itr@iterationNumber, 19]<-itr@resourceLayoutName		
		summaryMatrix[itr@iterationNumber, 20]<-itr@releaseType		
		summaryMatrix[itr@iterationNumber, 21]<-itr@releaseAzimuthOptimised		
		summaryMatrix[itr@iterationNumber, 22]<-itr@resourceRadius
		summaryMatrix[itr@iterationNumber, 23]<-itr@resourceSeparation		
		summaryMatrix[itr@iterationNumber, 24]<-itr@patchWidth				
		summaryMatrix[itr@iterationNumber, 25]<-itr@eggsPerForager				
		summaryMatrix[itr@iterationNumber, 26]<-itr@resourceLayoutWidth
		summaryMatrix[itr@iterationNumber, 27]<-itr@signalWidth
	}

	summary.df<-as.data.frame(summaryMatrix)

	return (summary.df)
}

ExperimentPlanClass.readIterations<-function(instance) {
	iterations<-list()
	if (instance@iterationCount>0) {
		for (iterationNumber in 1:instance@iterationCount) {
			iteration<-ExperimentIteration(instance, iterationNumber)
			iterations<-collection.appendToList(iterations, iteration)
		}				
	}
	return (iterations)
}


#Add a "toString" which is a "show method"
setMethod("show", "ExperimentPlan", 
	function(object) {
		summary(object)
	
		cat("Azimuth Generator:\n")
		show(levels(object@iterationSummary.df$azimuthGenerator))
		cat("Move Length:\n")
		show(levels(object@iterationSummary.df$moveLength))
		cat("Angle Of Turn:\n")
		show(levels(object@iterationSummary.df$angleOfTurn))
		cat("Search Optimised:\n")
		show(levels(object@iterationSummary.df$searchOptimised))
		cat("Resource Layouts:\n")
		show(levels(object@iterationSummary.df$resourceLayoutName))
		cat("Release Distance:\n")
		show(levels(object@iterationSummary.df$releaseDistance))
		cat("Eggs Per Forager:\n")
		show(levels(object@iterationSummary.df$eggsPerForager))
	
	}
)

setGeneric("getIteration", 
	function(plan, index) {
		standardGeneric("getIteration")
	}
)

setMethod("getIteration", signature=c("ExperimentPlan", "numeric"),
	function(plan, index) {
		return (plan@iterations[[index]])
	}
)


# Returns a data frame containing the cabbages from all iterations and replicates.
setGeneric("getAggregatedCabbages", 
	function(plan) {
		standardGeneric("getAggregatedCabbages")
	}
)

setMethod("getAggregatedCabbages", signature=c("ExperimentPlan"),
	function(plan) {
		return (ExperimentPlan.getAggregatedCabbages(plan))
	}
)

setGeneric("getAggregatedCabbagesByIteration", 
	function(plan, iterationNumbers) {
		standardGeneric("getAggregatedCabbagesByIteration")
	}
)

setMethod("getAggregatedCabbagesByIteration", signature=c("ExperimentPlan", "numeric"),
	function(plan, iterationNumbers) {
		return (ExperimentPlan.getAggregatedCabbages(plan, iterationNumbers))
	}
)



ExperimentPlan.getAggregatedCabbages<-function(experimentPlan, iterationNumbers=c()) {
	cabbages.df<-NULL
	

	if (length(iterationNumbers) > 0) {
		indexes<-iterationNumbers
	} else {
		indexes<-1:experimentPlan@iterationCount
	}
		
	for (i in 1:length(indexes) ) {
		itr<-getIteration(experimentPlan,indexes[i])
		if (is.null(cabbages.df)) {
			cabbages.df<-itr@cabbages.df
		} else {
			cabbages.df<-merge(cabbages.df, itr@cabbages.df, all=TRUE)
		}
	}
	cabbages.df$Plant.ID<-cabbages.df$Id
	return (cabbages.df)
}
 

# Returns a list of egg distributions grouped by a factor from the summary, e.g. step length, field name, etc.
setGeneric("getEggDistributionsByFactor", 
	function(experimentPlan, inputSummary.df, fieldCabbages, factorName, eggCountFieldName) {
		standardGeneric("getEggDistributionsByFactor")
	}
)

setMethod("getEggDistributionsByFactor", signature=c("ExperimentPlan", "data.frame", "FieldCabbages", "character", "character"),
	function(experimentPlan, inputSummary.df, fieldCabbages, factorName, eggCountFieldName) {
		return (ExperimentPlan.getEggDistributionsByFactor(experimentPlan, inputSummary.df, fieldCabbages, factorName, eggCountFieldName))
	}
)

ExperimentPlan.getEggDistributionsByFactor<-function(experimentPlan, inputSummary.df, fieldCabbages, factorName, eggCountFieldName){
	eggDistribution.list<-list()
	for (x in levels(inputSummary.df[[factorName]])) {
		subset.df<-subset(inputSummary.df, inputSummary.df[[factorName]]==x)
		iterationNumbers<-as.numeric(subset.df$iterationNumber)
		cabbages.df<-getAggregatedCabbagesByIteration(experimentPlan, iterationNumbers)	
		cabbages.merged.df<-IterationEggDistribution.mergeCabbages(fieldCabbages, cabbages.df)
		name<-sprintf("%s-%s", factorName, x)
		eggDistribution<-EggDistribution(name, cabbages.merged.df, eggCountFieldName, fieldCabbages)
		eggDistribution.list<-collection.appendToList(eggDistribution.list, eggDistribution)
	}
	return (eggDistribution.list)
}

ExperimentPlan.breakDownEggDistributionByReplicate<-function(dist) {
	all.cabbages.df<-dist@cabbages.df
	dists<-list()
	for (rep in levels(all.cabbages.df$Replicate)) {
		subset.df<-subset(all.cabbages.df, all.cabbages.df$Replicate==rep)
		subDist<-EggDistribution(sprintf("%s-rep-%s", dist@name, rep), subset.df, dist@eggCountFieldName, dist@fieldCabbages)
		dists<-collection.appendToList(dists, subDist)
	}
	return(dists)
}

#And a "summary" method
setMethod("summary", "ExperimentPlan", 
	function(object) {
		cat("Experiment      : ", object@experimentName, " (", object@experimentDirName, ", number=", sprintf("%03.0f", object@experimentNumber), ")\n")
		cat("trialName       : ", object@trialName,"\n")
		cat("description     : ", object@description, "\n")
		cat("iterationCount  : ", object@iterationCount, "\n")	
		cat("replicateCount  : ", object@replicateCount, "\n")
		cat("elapsedTime     : ")
		if (!is.na(object@elapsedTimeMS)) {
			cat("", Time.formatMS(object@elapsedTimeMS), "\n")
		}

	}
)

