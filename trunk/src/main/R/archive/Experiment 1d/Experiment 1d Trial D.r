source("~/Work/code/bugsim/resource/R/Experiment 1d/Experiment 1d Library.r", echo=FALSE)

experimentNumber<-6
trialId<-"TrD"
baseFilename<-"match-exp01d"
experimentDir<-createDirectoryName(basefilename, experimentNumber, trialId)

matchstickIntersectionFilename<-sprintf("%s/%s.csv", experimentDir, sprintf("%03d-matchstick-intersections", experimentNumber))

mis.df<-read.csv(file=matchstickIntersectionFilename)

quartz(width=10, height=10)
mis.total.hist<-hist(mis.df$IntersectionD[1:80],freq=TRUE, br=20:20,plot=FALSE)
N<-sum(mis.total.hist$counts)
plot(mis.total.hist,main=sprintf("Random Matchsticks, N=%d", N))

plot(mis.df$StartY[1:3200]~mis.df$StartX[1:3200])
str(mis.df)