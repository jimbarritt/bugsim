input.mx<-rbind(runif(n=5, min=0.1, max=1.6),
				runif(n=5, min=0.1, max=1.6),
				runif(n=5, min=0.1, max=1.6),
				runif(n=5, min=0.1, max=1.6)
)

xValues<-c(97, 98, 99, 100, 101)

data.df<-matrixToDataFrame(input.mx, xValues)

matrixToDataFrame<-function(input.mx, xValues) {
	replicateCount<-length(input.mx[,1])
	observationCount<-length(input.mx[1,])
	rowCount<-replicateCount*observationCount

	aggregate.df<-NULL

	for (i.replicate in 1:replicateCount) {
	
		replicate.df<-data.frame("id"=1:observationCount)
		replicate.df$replicate<-i.replicate
		for (i.observation in 1:observationCount) {
			replicate.df$x[i.observation]<-xValues[i.observation]
			replicate.df$y[i.observation]<-input.mx[i.replicate, i.observation]		
		}
		replicate.df$id<-NULL
	

		if (is.null(aggregate.df)) {
			aggregate.df<-replicate.df
		} else {
			aggregate.df<-merge(aggregate.df, replicate.df, all=T)
		}
	}
	return (aggregate.df)
}