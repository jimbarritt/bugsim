#Class definition for a Signal Surface class...


setClass("SignalSurface", representation(
	name="character",
	surfaceSD="numeric",
	surfaceMagnification="numeric",
	iterationNumber="numeric",
	surface.df="data.frame",
	surfaceMatrix="matrix",
	minX="numeric",
	maxX="numeric",
	countX="numeric",
	intervalX="numeric",
	minY="numeric",
	maxY="numeric",
	countY="numeric",
	intervalY="numeric"
))




#Add a constructor:
SignalSurface<-function(name, surface.df, experimentPlan, iterationNumber) {
	instance<-new("SignalSurface")
	
	instance@iterationNumber<-iterationNumber
	instance@name<-name
	
	instance@surface.df<-surface.df

	instance@surfaceSD<-surface.df$surfaceSD
	instance@surfaceMagnification<-surface.df$surfaceMagnification
		
	instance@minX<-surface.df$minX
	instance@maxX<-surface.df$maxX
	
	instance@minY<-surface.df$minY
	instance@maxY<-surface.df$maxY
	
	instance@countX<-surface.df$countX
	instance@countY<-surface.df$countY
	
	instance@intervalX<-surface.df$intervalX
	instance@intervalY<-surface.df$intervalY
	
	instance@surfaceMatrix<-SignalSurface.loadMatrix(instance, experimentPlan)
	
	return (instance)
}

SignalSurface.loadMatrix<-function(instance, experimentPlan, iterationNumber) {
	
	nRows<-instance@countX
	nCols<-instance@countY
	matrixFileName<-sprintf("%s/Iteration-%03.0f/Iteration-signal-survey-%s.mat", experimentPlan@experimentDir, instance@iterationNumber, instance@name)	
#	cat(sprintf("Reading Matrix: %s", matrixFileName), "\n")

	
	zm<-matrix(scan(matrixFileName, n=nRows*nCols), nrow=nRows, ncol=nCols,byrow = TRUE)
	
	return (zm)
}



#Add a "toString" which is a "show method"
setMethod("show", "SignalSurface", 
	function(object) {
		cat("SignalSurface (", object@name, ")\n")
		cat("x (", min(object@minX), " : ", max(object@maxX), ", countX=", object@countX, ", intervalX=", object@intervalX, ")\n")
		cat("y (", min(object@minY), " : ", max(object@maxY), ", countY=", object@countY, ", intervalY=", object@intervalY,  ")\n")
	}
)



#And a "summary" method
setMethod("summary", "SignalSurface", 
	function(object) {
#		cat("Experiment      : ", object@experimentName, " (", object@directoryName, ")\n")
#		cat("description     : ", object@description, "\n")
#		cat("iterationCount  : ", object@iterationCount, "\n")	
#		cat("replicateCount  : ", object@replicateCount, "\n")
	}
)

