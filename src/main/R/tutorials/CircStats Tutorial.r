library(CircStats)

quartz(width=10, height=10)

x.radians<-runif(2000, min=0, max=2*pi)

circ.plot(sample(x.radians, 2000), stack=TRUE, bins=360, shrink=3, pch=16, cex=.9)

stats<-circ.summary(x.radians)

theta<-stats$mean.dir
rho<-stats$rho

rect(-.1, -.1, .1, .1, col="white", border=NA)
rect(-.92, -.1, -.6, .1, col="white", border=NA)
rect(.9, -.1, .6, .1, col="white", border=NA)
rect(-.15, .9, .15, .6, col="white", border=NA)
rect(-.15, -.91, .15, -.6, col="white", border=NA)

if (rho<.8) {
	text(0,.8, "0")
}
text(-.8, 0, "-90")
text(.8, 0, "+90")
text(0,-.8 , "180")

lines(c(-.1, .1),c(0, 0), lwd=1.5, col="black")
lines(c(0, 0),c(-.1, .1), lwd=1.5, col="black")
arrows(0, 0, cos(theta)*rho, sin(theta)*rho, col="blue", lwd=2)

#x.radians<-rvm(1000, mean=0, k=3)

#Rayleigh test for uniformity...
v0.test(x.radians)

#Watson test for uniformity and against von mises:
watson(x.radians, alpha=0.01, dist="uniform")


unif.radians<-runif(1000, min=0, max=2*pi)
vm.radians<-rvm(1000, mean=0, k=6)

watson(unif.radians, alpha=0.01, dist="uniform")

watson(vm.radians, alpha=0.01, dist="uniform")

watson(unif.radians, alpha=0.01, dist="vm")

watson(vm.radians, alpha=0.01, dist="vm")

watson.two(unif.radians, vm.radians, alpha=0.01, plot=TRUE)


watson.two(vm.radians, vm.radians, alpha=0.01, plot=TRUE)


#What happens when -pi to +pi?

unif.radians.2<-c(rep((-pi*.5)+.00001, 100), rep((pi*.25)+.00001, 100))

unif.radians.2<-unif.radians.2+(pi*.5)
circ.plot(unif.radians.2, stack=TRUE, bins=360, shrink=3, pch=16, cex=.9)

quartz(width=10, height=10)
circ.plot(vm.radians, stack=TRUE, bins=360, shrink=3, pch=16, cex=.9)






