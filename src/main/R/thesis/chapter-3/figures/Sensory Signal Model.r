source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")
library(CircStats)

lib.loadLibrary("/thesis/common/circular/CircStats Extensions.r")

lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")
lib.loadLibrary("/thesis/chapter-3/figures/Sensory Signal Model Library.r")
lib.loadLibrary("/thesis/chapter-3/model/Resource Layout Library.r")


sense.output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/03-simulation-framework/resources/figures"

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "gaussian-olfaction-signal-surface")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=15, height=6)
sense.plot3olfactionSignals()
dev.off()

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "radius-of-attraction-demo")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=15, height=5.5)
sense.plotRadiusOfAttractionDemo()
dev.off()


quartz(width=15, height=5.5)
sense.plotRadiusOfAttractionDemo()


quartz(width=10, height=10)
sense.plotCalculatedLayoutParameters()

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "calculated-layout-parameters")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=10, height=10)
sense.plotCalculatedLayoutParameters()
dev.off()


sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "corner-centre-edge-patch")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=10, height=11)
sense.plotCornerCentreEdgePatch()
dev.off()




sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "Potential-Response-L-80-vs-B")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=7, height=7)
sense.plotPotentialResponseL80ForAvsB()
dev.off()

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "Potential-Response-L-80-vs-B-slope")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=7, height=7)
sense.plotPotentialResponseOfSlopeToAForB()
dev.off()


quartz(width=10, height=10)
sense.plotVisualSignalNoise(currentK=2, maxK=10)


sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "Visual-Signal-Noise-VonMises")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=7, height=7)
sense.plotVisualSignalNoise(currentK=2, maxK=10)
dev.off()


#These are the figures used to describe the sensory signal based carrelated random walk.
quartz(width=15 , height=10)
#First a normal distribution:

sense.plotControlOfDirection(mean=0, sd=10, meanThetaMin=-90, meanThetaMax=90, col.light="grey")

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "Control-Of-Direction-SD-30-MINMAX-90")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=15, height=10)
sense.plotControlOfDirection(mean=0, sd=30, meanThetaMin=-90, meanThetaMax=90)
dev.off()

sense.plotControlOfDirectionVM(mean=0, k=10, meanThetaMin=-90, meanThetaMax=90, col.light="grey")

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "Control-Of-Direction-K-10-MINMAX-90")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=13, height=8)
sense.plotControlOfDirectionVM(mean=0, k=10, meanThetaMin=-90, meanThetaMax=90)
dev.off()


quartz(width=10 , height=10)
sense.plotSignalDeltas()

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "Example-Signal-Deltas-2-Sensors")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=10, height=12)
sense.plotSignalDeltas()
dev.off()


quartz(width=10, height=10)
sense.plotSignalDeltaVsDirectionDelta(minMax=90)

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "SignalDeltaVsDirectionDelta-MAX-90")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=7, height=7)
sense.plotSignalDeltaVsDirectionDelta(minMax=90)
dev.off()


quartz(width=10, height=10)

sense.plotLuminosityResponseToDistance(gamma=0, maxD=100)


sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "LuminosityResponseWithDistance-gamma-0-05")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=8, height=8)
sense.plotLuminosityResponseToDistance(gamma=0.05, maxD=100)
dev.off()

#Draw signal input for example visual agent...
azimuthCoords<-list(
	c(347.47, 92.20),
	c(0.00, 90.00),
	c(352.87, 80.62),
	c(344.05, 72.80),
	c(350.54, 60.83),
	c(7.13, 80.62),
	c(9.46, 60.83),
	c(45.00, 28.28),
	c(333.43, 89.44)
)

gamma<-0.05
quartz(width=10, height=5)
signals<-sense.plotInputSignal(azimuthCoords, gamma)

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "VisualInputSignals-gamma-0-05")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=10, height=6)
signals<-sense.plotInputSignal(azimuthCoords, gamma)
dev.off()


#graph of pdistribution:
sense.plotVisualPDistribution(signals)

sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "VisualPDistribution-gamma-0-05")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=10, height=6)
sense.plotVisualPDistribution(signals)
dev.off()


#graph of pdistribution including beta:
exp(0.1) + exp(0.1) + exp(0.1)

exp(0.1+0.1+0.1)
exp(1)

