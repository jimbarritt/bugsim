source("~/Work/code/bugsim/resource/R/probability/Probability Library.r", echo=FALSE)

#Print a normal distribution on the screen...
prob.plotNormalDistribution(0, 90)



rnd<-runif(1000)
max<-100
min<-10
xTest<-prob.uniformInRangeX(rnd, min, max)
min(xTest)
max(xTest)