cat("Probability Library Version 1.0\n")

#plots a nice demonstration of the normal distribution density function for sd = 40
prob.plotNormalDistribution<-function(mean, sd) {
	x<-seq(from=-180, to=180,by=1)
	y<-dnorm(x, mean,sd)
	plot(y~x, type="l", xaxt="n", col="blue", xlab="x", ylab="P(x)")
	axis(1, at=seq(from=-180, to=180, by=45))
}

#Cant include this because the newline character messes up the input (think textmate sends it as a real newline or something)
#		cat("x[", i, "]=", x[i], "\n")
prob.createDistribution<-function(x) {
	result<-c()
	
	sumX<-sum(x)
	i<-1
	while (i<=length(x)) {
		
		p<-(x[i]/sumX)
		if (is.nan(p)) p = 0
		result<-c(result, p)
				
		i<-i+1
	}
	
	return (result)
}

prob.createCumulativeDistribution<-function(x) {
	result<-c()
	
	sum<-0
	i<-1
	while (i<=length(x)) {
		
		p<-(x[i])
		sum<-sum+p
		if (is.nan(sum)) p = 0
		result<-c(result, sum)
				
		i<-i+1
	}
	
	return (result)
}


prob.chooseRandomFromDist<-function(x, pDist) {
	rnd<-runif(1)
	return(prob.chooseRandomFromDistX(x, pDist, rnd))
}	

prob.uniformInRangeX<-function(x, min, max) {
		return (x*(maxStep-minStep)+minStep)
}




prob.chooseRandomFromDistX<-function(x, pDist, rnd) {
	i<-1
	found<-FALSE
	runOffEnd<-FALSE
	while (found == FALSE) {
		if (pDist[i] >= rnd) {
			found <- TRUE
		}
		
		
		if (i>=length(pDist)) {
			found<-TRUE
		}
		i<-i+1
	}
	
	if (i>length(pDist)) {
		i<-length(pDist)
	}
	

	return (x[i])
	

}


prob.bayesianProduct<-function(x1, x2) {
	
	if (length(x1) != length(x2)) {
		return (FALSE)
	}
	
	result<-c()
	
	sumX<-sum(x1) + sum(x2)
	i<-1
	while (i<=length(x1)) {
		
		result<-c(result, ((x1[i]*x2[i])/sumX))

				
		i<-i+1
	}
	return (result)	
	
}
prob.modelAverage<-function(x1, x2) {
	
	if (length(x1) != length(x2)) {
		return (FALSE)
	}
	
	result<-c()
	
	sumX<-sum(x1) + sum(x2)
	i<-1
	while (i<=length(x1)) {
		
		result<-c(result, ((x1[i]+x2[i])/sumX))
				
		i<-i+1
	}
	return (result)	
	
}
