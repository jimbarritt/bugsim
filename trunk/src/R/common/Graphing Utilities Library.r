cat("Graphing Utilities Library (v 1.0)\n")


setMethod("plot", "list",
	function(x, y) {
		for (object in x) {
			plot(object)
		}
	}
)



#Draws a regression line for you given a vector of x values and a linear model
#lm<-lm.sim.1m
#x<-x.1m
#expects the linear model to be a model based on log(values) 
GraphUtil.plotLogRegressionLine<-function(x, lm, col="grey", interceptOverride=NULL,...) {
	
	if (!is.null(interceptOverride)) {
		intercept<-interceptOverride
	} else {
		intercept<-lm$coef[[1]]
	}
	slope<-lm$coef[[2]]
	
	xCount<-length(x)
	
	x1<-x[[1]]*.2
	x2<-x[[xCount]]*1.2
	
	y1<-exp((log(x1)*slope)+intercept)
	y2<-exp((log(x2)*slope)+intercept)
	
	lines(x=c(x1, x2), y=c(y1, y2), col=col, ...)

}

#Does the same thing except assumes you have sqrt'd y values and both axes are sqrted
#x<-x.1m;lm<-lm.1m
GraphUtil.plotSqrtRegressionLine<-function(x, lm, col="grey", ...) {

	intercept<-lm$coef[[1]]
	slope<-lm$coef[[2]]
	
	xCount<-length(x)
	
	x1<-0
	x2<-x[[xCount]]*1.2
	
	y1<-(x1*slope)+intercept
	y2<-(x2*slope)+intercept
	

	lines(x=c(x1, x2), y=c(y1, y2), col=col, ...)

}
