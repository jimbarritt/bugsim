cat("Mean Dispersal Library (version 1.0)\n")

#EJones and ESkellam are identical
ESkellamJones<-function(n , rho, s) {
	a<-(1+rho)/(1-rho)
	b<-(2*rho*(1-rho^n)/(1-rho^2))
	return (s^2*a*(n-b))
}


EJohnson<-function(n, c, s) {
	a<-2*(c/(1-c))
	b<-n-((1-c^n)/(1-c))
	return (n+(a*b))
}

#z is the mean of cos(theta) where theta is the angle of turn.
#trouble is you need a path first. this is one taken from a path with k=2 so quite wide...
EKareiva<-function(n, c, s) {
	EL<-s
	ELSq<-s^2
	a<-n*ELSq
	b<-(2*EL)^2
	c<-c/(1-c)
	d<- n - ( (1-(c^n) ) / (1-c) )
	E<-a+( (b*c)*d )
	return (E)
}

md.calculateMSDForKL<-function(K, L, timesteps) {
	rho<-circular.rhoFromK(K)
	msd<-md.E(timesteps, rho, L, "skellam-jones")
	return (msd)	
}


md.Erho<-function(meanAot, nSteps , stepLength, type) {
	return (md.E(nSteps, meanAot, stepLength, type))
}

md.E<-function(nSteps, meanAot, stepLength, type) {
	result<-NA
	if (type=="skellam-jones") {
		result<-ESkellamJones(nSteps, meanAot, stepLength)
	} else if (type=="kareiva") {
		result<-EKareiva(nSteps, meanAot, stepLength)
	} else if (type=="johnson") {
		result<-EJohnson(nSteps, meanAot, stepLength)
	} else {
		stop(sprintf("Unknown method, %s", type))
	}
	
	return (result)
}




