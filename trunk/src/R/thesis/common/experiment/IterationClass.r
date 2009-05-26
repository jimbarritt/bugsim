#Represents an iteration - parameters and such ...


#Class definition for an ExperimentPlan class...

setClass("ExperimentIteration", representation(
	experimentPlan="ExperimentPlan",
	iterationNumber="numeric",
	elapsedTimeMS="ANY",
	includeLifeHistory="logical",
	ageLimit="numeric",
	eggLimit="numeric",
	foragersReleased="numeric",
	eggsPerForager="numeric",
	azimuthGenerator="character",
	angleOfTurn="numeric",
	azimuthResolution="numeric",
	moveLength="numeric",
	searchOptimised="logical",
	releaseDistance="numeric",
	releaseAzimuthOptimised="logical",
	releaseType="character",
	visionEnabled="logical",#boolean
	visualFieldDepth="numeric",
	visualFieldWidth="numeric",
	olfactionEnabled="logical",
	olfactionMinSensitivity="numeric",
	olfactionMaxSensitivity="numeric",
	iteration.df="data.frame",
	replicateStats.df="data.frame",
	replicateCount="numeric",
	replicates="list",
	cabbages.df="data.frame",
	parameters.df="data.frame",
	resourceLayoutName="character",
	resourceRadius="numeric",
	resourceSeparation="numeric",
	patchWidth="numeric",
	patchHeight="numeric",
	resourceLayoutWidth="numeric",
	resourceLayoutHeight="numeric",
	sinuosity="numeric",
	signalSurfaces="list",
	signalWidth="numeric"
	

))


#Add a constructor:
ExperimentIteration<-function(experimentPlan, iterationNumber) {
	instance<-new("ExperimentIteration")
	
	instance@experimentPlan<-experimentPlan
	instance@iterationNumber<-iterationNumber
	
	parameterSummaryFile<-sprintf("%s/Iteration-%03.0f/Iteration-params.csv", experimentPlan@experimentDir, iterationNumber)
	cat(sprintf("Loading Iteration [ %03.0f ]\n", iterationNumber))
	it.params.df<-read.csv(parameterSummaryFile, header=TRUE, as.is=TRUE)
	
	
	instance@iteration.df<-it.params.df
	instance@replicateCount<-it.params.df$replicateCount
	
	instance@replicateStats.df<-IterationReplicate.readReplicatesDataFrame(experimentPlan, iterationNumber)
	instance@replicates<-IterationReplicate.readReplicates(experimentPlan, instance@replicateStats.df, iterationNumber, instance@replicateCount)
	
	instance@elapsedTimeMS<-NA
	for (rep in instance@replicates) {
		if (!is.na(rep@elapsedTimeMS)) {
			if (is.na(instance@elapsedTimeMS)) {
				instance@elapsedTimeMS<-0
			}
			instance@elapsedTimeMS<-instance@elapsedTimeMS+rep@elapsedTimeMS
		}
	}
	
	cn<-colnames(it.params.df)
	instance@includeLifeHistory<-getIncludeLifeHistory(cn, it.params.df)
	instance@azimuthGenerator<-getAzimuthGenerator(cn, it.params.df)
	instance@angleOfTurn<-getAngleOfTurn(cn, it.params.df)
	instance@azimuthResolution<-getAzimuthResolution(cn, it.params.df)
	instance@moveLength<-getMoveLength(cn, it.params.df)
	instance@sinuosity<-circular.sinuosityFromKAndL(instance@angleOfTurn, instance@moveLength)
	instance@ageLimit<-getAgeLimit(cn, it.params.df)
	instance@eggLimit<-getEggLimit(cn, it.params.df)
	instance@foragersReleased<-getForagersReleased(cn, it.params.df)
	instance@releaseDistance<-getReleaseDistance(cn, it.params.df)
	instance@releaseAzimuthOptimised<-getReleaseAzimuthOptimised(cn, it.params.df)
	instance@releaseType<-getReleaseType(cn, it.params.df)
	instance@searchOptimised<-getSearchOptimised(cn, it.params.df)

	instance@eggsPerForager<-getEggsPerForager(cn, it.params.df)
	instance@visionEnabled<-getVisionEnabled(cn, it.params.df)
	instance@visualFieldDepth<-getVisualFieldDepth(cn, it.params.df)
	instance@visualFieldWidth<-getVisualFieldWidth(cn, it.params.df)
	instance@olfactionEnabled<-getOlfactionEnabled(cn, it.params.df)
#	instance@olfactionMinSensitivity
#	instance@olfactionMaxSensitivity


	if (instance@olfactionEnabled) {
		survey.df<-SignalSurvey.loadSurvey(experimentPlan, iterationNumber)
		if (length(survey.df$signalSurfaceName)>0) {
			instance@signalSurfaces<-SignalSurvey.loadSurfaces(survey.df, experimentPlan, iterationNumber)
			instance@signalWidth<-instance@signalSurfaces[[1]]@surfaceSD#This will stop working if we have more than one surface ever.
		} else {
			instance@signalSurfaces<-list()
			instance@signalWidth<-NaN
		}
	} else {
		instance@signalWidth<-NaN
	}

	instance@resourceLayoutName<-getResourceLayoutName(cn, it.params.df)
	
	instance@resourceRadius<-getResourceRadius(cn, it.params.df)
	instance@resourceSeparation<-getResourceSeparation(cn, it.params.df)
	
	if (!is.nan(instance@resourceSeparation) && !is.nan(instance@resourceRadius)) {
		size<-Iteration.calculatePatchSize(instance@resourceSeparation, instance@resourceRadius, 4, 4)
		instance@patchWidth<-size$w
		instance@patchHeight<-size$h		
	}else {
		instance@patchWidth<-NaN
		instance@patchHeight<-NaN
	}
	
	resourceLayoutSize<-getResourceLayoutSize(cn, it.params.df)
	if (length(resourceLayoutSize>0)) {
			instance@resourceLayoutWidth<-resourceLayoutSize$w
			instance@resourceLayoutHeight<-resourceLayoutSize$h	
		} else {
			instance@resourceLayoutWidth<-NaN
			instance@resourceLayoutHeight<-NaN
		}
	
	

	instance@cabbages.df<-ExperimentIteration.loadResourceResults(instance)
	instance@parameters.df<-it.params.df
	
	return (instance)
}


#Add a "toString" which is a "show method"
setMethod("show", "ExperimentIteration", 
	function(object) {
		cat("iterationNumber    : ", object@iterationNumber, "\n")
		cat("elapsedTimeMS      : ")
		if (!is.na(object@elapsedTimeMS)) {
			cat(" ", Time.formatMS(object@elapsedTimeMS), "\n")
		} else {
			cat("NA\n")
		}

		cat("resourceLayoutName      : ", object@resourceLayoutName, "\n")
		cat("resourceRadius          : ", object@resourceRadius, "\n")
		cat("resourceSeparation      : ", object@resourceSeparation, "\n")
		cat("patchSize               : ", sprintf("(w=%0.0f, h=%0.0f)",object@patchWidth, object@patchHeight), "\n")
		cat("layoutSize              : ", sprintf("(w=%0.0f, h=%0.0f)",object@resourceLayoutWidth, object@resourceLayoutHeight), "\n")
		cat("eggLimit                : ", object@eggLimit, "\n")
		
		cat("foragersReleased        : ", object@foragersReleased, "\n")
		cat("releaseDistance         : ", object@releaseDistance, "\n")
		cat("releaseType             : ", object@releaseType, "\n")
		cat("releaseAzimuthOptimised : ", object@releaseAzimuthOptimised, "\n")
		cat("eggsPerForager          : ", object@eggsPerForager, "\n")
		
		cat("ageLimit                : ", object@ageLimit, "\n")

		cat("includeLifeHistory      : ", object@includeLifeHistory, "\n")
		cat("azimuthGenerator        : ", object@azimuthGenerator, "\n")
		cat("angleOfTurn             : ", object@angleOfTurn, "\n")
		cat("azimuthResolution       : ", object@azimuthResolution, "\n")
		cat("moveLength              : ", object@moveLength, "\n")
		cat("sinuosity               : ", object@sinuosity, "\n")
		cat("searchOptimised         : ", object@searchOptimised, "\n")
		
		cat("visionEnabled           : ", object@visionEnabled, "\n")
		cat("visualFieldDepth        : ", object@visualFieldDepth, "\n")
		cat("visualFieldWidth        : ", object@visualFieldWidth, "\n")
		cat("olfactionEnabled        : ", object@olfactionEnabled, "\n")
		cat("signalWidth             : ", object@signalWidth, "\n")
		
		
	}
)

ExperimentIteration.loadResourceResults<-function(instance) {
	completeData.df<-NULL
	for (i.replicate in instance@replicates) {
		cabbageFile<-sprintf("Replicate-cabbages.csv", instance@experimentPlan@experimentNumber, instance@iterationNumber, i.replicate@replicateNumber)
		inputFileName<-sprintf("%s/Iteration-%03.0f/%s/%s", instance@experimentPlan@experimentDir, instance@iterationNumber, i.replicate@outputDir, cabbageFile)
#		cat("Reading Resource Replicate: ", inputFileName, "\n")
		replicate.df<-read.csv(inputFileName)
		if (is.null(completeData.df)) {
			completeData.df<-replicate.df
		} else {
			completeData.df<-merge(completeData.df, replicate.df, all=TRUE)
		}
		
		
	}
	if (length(completeData.df$Replicate) > 0) {
		completeData.df$Replicate<-factor(completeData.df$Replicate)
		completeData.df$resourceLayoutName<-instance@resourceLayoutName		
		
		completeData.df$A<-instance@angleOfTurn
		completeData.df$AF<-as.factor(instance@angleOfTurn)
		completeData.df$L<-instance@moveLength
		completeData.df$LF<-as.factor(instance@moveLength)
		
	}
	
	
	return (completeData.df)	
}

getParameter<-function(cn , it.params.df, paramName) {
	result<-"NOT FOUND"         
	if (collection.contains(cn, paramName)) {
		result<-it.params.df[[paramName]]
		if (is.factor(result)) {
			result<-as.character(result)
		}
	}
	return (result)
}



getVisualFieldDepth<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.sensor.visual.fieldOfViewVision.fieldDepth")	
	if (result=="NOT FOUND") {
		result<-NA
	}
	return (result)
}

getVisualFieldWidth<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.sensor.visual.fieldOfViewVision.fieldWidth")	
	if (result=="NOT FOUND") {
		result<-NA
	}
	return (result)
}

getVisionEnabled<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.visionEnabled")	
	if (result=="NOT FOUND") {
		result<-NA
	} else {
		result<-as.logical(result)
	}
	return (result)
}

getOlfactionEnabled<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.olfactionEnabled")	
	if (result=="NOT FOUND") {
		result<-NA
	}	else {
			result<-as.logical(result)
		}
	return (result)
}

getEggsPerForager<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.behaviour.foraging.eggLaying.numberOfEggs")	
	if (result=="NOT FOUND") {
		result<-NaN
	}
	return (result)
}

getAgeLimit<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.population.adultMortality.limitedAge.maxAge")	
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.population.adultMortality.limitedAgeAndEggs.maxAge")			
	}
	if (result=="NOT FOUND") {
		result<-NaN
	}
	return (result)
}

getReleaseAzimuthOptimised<-function(cn, it.params.df) {
	result<-getParameter(cn , it.params.df, "forager.population.immigration.initial.immigrationPattern.randomRelease.optimiseReleaseAzimuth")	
	
	if (result=="NOT FOUND") {
		result<-F # not all experiments have this parameter.
#		stop("Could not find Release Azimuth Optimised Released!")
	}
	result<-as.logical(result)
	return (result)
	
	
}


getReleaseType<-function(cn, it.params.df) {
	result<-getParameter(cn , it.params.df, "forager.population.immigration.initial.immigrationPattern")	

	if (result=="randomRelease") {
		inZone<-getParameter(cn , it.params.df, "forager.population.immigration.initial.immigrationPattern.randomRelease.releaseInZone")			
		if(as.logical(inZone)==T) {
			result<-sprintf("%s (IN ZONE)", result)
		}
	}
	
	if (result=="NOT FOUND") {
		stop("Could not find Foragers Released!")
	}
	return (result)
	
	
}
getForagersReleased<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.population.immigration.initial.immigrationPattern.fixedLocationRelease.numberToRelease")	
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.population.immigration.initial.immigrationPattern.randomRelease.numberToRelease")	
	}
	
	if (result=="NOT FOUND") {
		stop("Could not find Foragers Released!")
	}
	return (result)
}

getEggLimit<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.population.adultMortality.limitedAgeAndEggs.maxEggs")		
	if (result=="NOT FOUND") {	
		result<-getParameter(cn , it.params.df, "forager.population.immigration.initial.immigrationPattern.randomRelease.eggLimit")			
	}	
	if (result=="NOT FOUND") {	
		cat("warning!:Could not find egg Limit!\n")
		result<-0
	}
	return (result)
}

getReleaseDistance<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "environment.landscape.releaseBoundary.circularBoundaryStrategy.releaseDistance")	
 
	if (result=="NOT FOUND") {
		stop("Could not find Release Distance Parameter!")
	}
	return (result)
}


getResourceLayoutName<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "environment.resource.resourceLayout.predefinedLayout.layoutName")	
 
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "environment.resource.resourceLayout.calculatedLayout.layoutName")	
	}

	if (result=="NOT FOUND") {
		stop("Could not find layout name!")
	}
	if (is.logical(result)) {
		if (result) {
			result<-"T"
		} else {
			result<-"F"
		}
	}
	return (result)
}


#result<-"w=600.00000 : h=600.00000"
getResourceLayoutSize<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "environment.resource.resourceLayout.calculatedLayout.layoutBoundary.rectangularBoundaryStrategy.dimensions")	
 	
	
	if (result=="NOT FOUND") {
		result<-list()
#		stop("Could not find layout name!")
	} else {
		params<-unlist(strsplit(result, ":", fixed = TRUE))
		wparam<-unlist(strsplit(params[1], "="))
		hparam<-unlist(strsplit(params[2], "="))
		w<-as.numeric(wparam[2])
		h<-as.numeric(hparam[2])
		result<-list("w"=w, "h"=h)
	}
	return (result)
}




getResourceRadius<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "environment.resource.resourceLayout.calculatedLayout.resourceRadius")	
 
	if (result=="NOT FOUND") {
		result<-getParameter(cn, it.params.df, "environment.resource.resourceLayout.predefinedLayout.fixedResourceRadius")
	}
	
	if (result=="NOT FOUND") {
		result<-NaN
#		stop("Could not find layout name!")
	}
	return (result)
}

getResourceSeparation<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "environment.resource.resourceLayout.calculatedLayout.interEdgeSeparation")	
 

	if (result=="NOT FOUND") {
		result<-NaN
#		stop("Could not find layout name!")
	}
	return (result)
}

Iteration.calculatePatchSize<-function(separation, radius, countX, countY) {
	diam<-radius*2
	xdim<-(countX*separation) + (countX*diam)
	ydim<-(countY*separation) + (countY*diam)
	return (list("w"=xdim, "h"=ydim))
		
}


getSearchOptimised<-function(cn, it.params.df) {	
	result=getParameter(cn , it.params.df, "forager.behaviour.foraging.eggLaying.optimiseSearch")
 
	if (result=="NOT FOUND") {
	#	stop("Could not find searchOptimised Parameter")
		result=F
	} 
	
	result<-as.logical(result)

	return (result)
}


getIncludeLifeHistory<-function(cn, it.params.df) {	
	result<-as.logical(getParameter(cn , it.params.df, "forager.behaviour.recordLifeHistory"))	
	return (result)
}

getAzimuthGenerator<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.azimuthGenerator")	
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.behaviour.movement.randomWalk.azimuthGenerator")	
	}
	
	if (result=="vonMises") {
		cain<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.azimuthGenerator.vonMises.useCainMethod")	
		if (cain !="NOT FOUND") {
			bCain<-as.logical(cain)
			if (bCain==T) {
				result<-paste(result, " (Cain)")
			} else {
				result<-paste(result, " (CircStats)")
			}
		}
	}
	return (result)
}

getAngleOfTurn<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.azimuthGenerator.vonMises.angleOfTurnK")	
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.behaviour.movement.randomWalk.azimuthGenerator.vonMises.angleOfTurnK")	
	}
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.azimuthGenerator.gaussian.standardDeviation")	
	}
	if (result=="NOT FOUND") {
		stop("Could not find angle of turn parameter")
	}
	
	return (result)
}
getAzimuthResolution<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.azimuthGenerator.vonMises.resolutionN")	
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.azimuthGenerator.wrappedCauchy.resolutionN")	
	}
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.azimuthGenerator.wrappedNormal.resolutionN")	
	}
	
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.azimuthGenerator.gaussian.resolutionN")	
	}
	if (result=="NOT FOUND") {
		result<-0
	}
	
	return (result)
}

getMoveLength<-function(cn, it.params.df) {	
	result<-getParameter(cn , it.params.df, "forager.behaviour.movement.sensoryRandomWalk.moveLengthGenerator.fixedMoveLength.moveLength")	
	if (result=="NOT FOUND") {
		result<-getParameter(cn , it.params.df, "forager.behaviour.movement.randomWalk.moveLengthGenerator.fixedMoveLength.moveLength")	
	}
	
	return (result)
}






#And a "summary" method
setMethod("summary", "ExperimentIteration", 
	function(object) {
	}
)

setGeneric("getReplicate", 
	function(iteration, index) {
		standardGeneric("getReplicate")
	}
)

setMethod("getReplicate", signature=c("ExperimentIteration", "numeric"),
	function(iteration, index) {
		return (iteration@replicates[[index]])
	}
)




