source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

# Taken from Cain (1985) These functions demonstrate how to generate random numbers
# From any distribution function in a monte carlo style manner (like roulette)
lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")

j<-seq(from=-180, to=180, by=1)
xj<-toDegrees(mcc.interval(360, j))



mcc.interval<-function(N, j) {
	return((2*pi/N)*(j-.5))
}