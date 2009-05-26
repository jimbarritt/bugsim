# Simple script for showing the transformation of visual signal to Noise....

signalNoise<-function(signal, psi, maxNoise) {
#	return (psi*maxNoise - (psi*signal*maxNoise))
	return (maxNoise*exp(-psi*signal))
}

x=seq(from=0, to=100, by=0.01)

#for vonmises its the other way round...
maxK=10
currK=6
y=maxK-signalNoise(x, psi=(maxK-currK)/maxK, maxNoise=maxK)

y=maxK-signalNoise(x, psi=.6, maxNoise=maxK)


plot(y~x, type="l", xlab="Signal", ylab="Noise")
