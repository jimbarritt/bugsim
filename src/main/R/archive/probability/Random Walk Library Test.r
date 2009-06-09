source("~/Work/code/bugsim/src/R/archive/probability/Von Mises Distribution Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/probability/Random Walk Library.r", echo=FALSE)


steps<-1000
randAzimuth<-runif(steps)

path<-rwalk.uniformRandomWalk(n=steps,  randAzimuth=randAzimuth, stepLength=2,startX=0, startY=0)

expectedDisplacement<-(2*steps)^.5
actualDisplacement<-coord.displacementDistance(path[[1]], path[[steps]])

expectedDisplacement
actualDisplacement



quartz(width=10, height=10)


scale<-80
rwalk.plotPath(path, scale, "Uniform Random Walk")


