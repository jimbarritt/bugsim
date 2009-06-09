#Class definition for a Signal Surface class...


setClass("FieldCabbageLayout", representation(
	fieldName="character",
	cabbages.df="data.frame",
	totalEggs="numeric"

))




#Add a constructor:
FieldCabbageLayout<-function(fieldName, cabbages.df, startX, startY) {
	instance<-new("FieldCabbageLayout")
	
	instance@fieldName<-fieldName
	instance@cabbages.df<-cabbages.df
	instance@cabbages.df$realX<-instance@cabbages.df$x
	instance@cabbages.df$realY<-instance@cabbages.df$y
	
	instance@cabbages.df$x<-instance@cabbages.df$x-startX
	instance@cabbages.df$y<-instance@cabbages.df$y-startY
	
	instance@totalEggs<-sum(instance@cabbages.df$Field.EggCount)

	return (instance)
}




#Add a "toString" which is a "show method"
setMethod("show", "FieldCabbageLayout", 
	function(object) {
		cat("FieldCabbageLayout (Field ", object@fieldName, ", totalEggs=, ", object@totalEggs, ")\n")
	}
)



#And a "summary" method
setMethod("summary", "FieldCabbageLayout", 
	function(object) {
#		cat("Experiment      : ", object@experimentName, " (", object@directoryName, ")\n")
#		cat("description     : ", object@description, "\n")
#		cat("iterationCount  : ", object@iterationCount, "\n")	
#		cat("replicateCount  : ", object@replicateCount, "\n")
	}
)

setMethod("plot", "FieldCabbageLayout",
	function(x, y, ...) {
		fieldLayout<-x
		
		data.df<-fieldLayout@cabbages.df
		minId<-min(data.df$Plant.ID)
		maxId<-max(data.df$Plant.ID)
		
		title<-sprintf("Field %s : Plants %0.0f to %0.0f", fieldLayout@fieldName, minId, maxId)
		
		plot(data.df$y~data.df$x, xlim=c(0, 36), ylim=c(0, 36), axes=FALSE, main=title, ...)
		axis(side=1, at=seq(0, 36, 6), ...)
		axis(side=2, at=seq(0, 36, 6), ...)
		box()
	}
)

setMethod("as.data.frame",  "FieldCabbageLayout",
	function(x, row.names, optional) {
		return (x@cabbages.df)
	}
)
setMethod("as.data.frame.default",  "FieldCabbageLayout",
	function(x, row.names, optional) {
		return (x@cabbages.df)
	}
)


