
cauchypdf<-function(x, x0, gamma) {
	sqrdTerm<-1+((x-x0)/gamma)^2
	return (1 / ((pi*gamma)*sqrdTerm))
	
}
x<-seq(from=-pi, to=pi, by=0.1)
y<-cauchypdf(x, 0, 0.9)
y<-vmises.densityFXDegrees(x, 6, 0)
plot(y~x, type="l")

