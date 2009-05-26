#Class definition for a Signal Surface class...


setClass("BrayCurtisIndex", representation(
	index="numeric"
))




#Add a constructor:
BrayCurtisIndex<-function(index) {
	instance<-new("BrayCurtisIndex")	

	instance@index<-index
	return (instance)
}





#Add a "toString" which is a "show method"
setMethod("show", "BrayCurtisIndex", 
	function(object) {
		cat("BrayCurtisIndex =", object@index, "\n")		
	}
)



#And a "summary" method
setMethod("summary", "BrayCurtisIndex", 
	function(object) {
	}
)

