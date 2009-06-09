#Compilation(dont have this package):
library(compiler)
f<-function(x, mu=0, sigma=1) {
	(1/sqrt(2*pi)) * exp(-0.5 * ((x-mu)/sigma)^2)/sigma
}
fc<-cmpfun(f)
x<-seq(0, 3, len=5)
r<-system.time(for (i in 1:1e+05) f(x))
?system.time