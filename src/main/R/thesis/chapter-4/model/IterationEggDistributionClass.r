#Class definition for a Signal Surface class...

lib.loadLibrary("/thesis/chapter-4/model/EggDistributionClass.r")

setClass("IterationEggDistribution", representation(
	iterationNumber="numeric",
	eggDistribution="EggDistribution"
))




#Add a constructor:
IterationEggDistribution<-function(fieldCabbages, iteration, iterationNumber) {
	instance<-new("IterationEggDistribution")
	
	instance@iterationNumber<-iterationNumber

	cabbages.df<-IterationEggDistribution.mergeCabbages(fieldCabbages, iteration@cabbages.df)
	
	instance@eggDistribution<-EggDistribution(sprintf("Iteration %03.0f Distribution", iterationNumber), cabbages.df, "Simulation.Egg.Count", fieldCabbages)
	
	return (instance)
}

IterationEggDistribution.mergeCabbages<-function(fieldCabbages, simulationCabbages.df) {
	merged.df<-merge(fieldCabbages@cabbages.df, simulationCabbages.df, by.x="Plant.ID", by.y="Plant.ID")
	merged.df$Simulation.Egg.Count<-merged.df$Egg.Count
	merged.df$Egg.Count<-NULL
	
	return (merged.df)
}




#Add a "toString" which is a "show method"
setMethod("show", "IterationEggDistribution", 
	function(object) {
		show(object@eggDistribution)
		
	}
)



#And a "summary" method
setMethod("summary", "IterationEggDistribution", 
	function(object) {
	}
)

setMethod("plot", "IterationEggDistribution",
	function(x, y) {
		plot(x@eggDistribution)
	}
)

setMethod("boxplot", "IterationEggDistribution", 
	function(x, y, ...) {
		boxplot(x@eggDistribution, ...)
	}
)


