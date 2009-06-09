source("~/Work/code/bugsim/resource/R/Experiment 1d/Experiment 1d Library.r", echo=FALSE)
#Requires nlme package


experimentNumber<-16
trialId<-"TrDEBUG"
baseFilename<-"match-exp01d"
experimentDir<-createDirectoryName(basefilename, experimentNumber, trialId)

matchstickIntersectionFilename<-sprintf("%s/%s.csv", experimentDir, sprintf("%03d-matchstick-intersections", experimentNumber))

mis.df<-read.csv(file=matchstickIntersectionFilename)




#Try fitting quadratic equation:
yx.quad.lm<-lm(y~x+I(x^2))
summary(yx.quad.lm)
AIC(yx.quad.lm)
y.quad.fitted<-fitted(yx.quad.lm)


title<-sprintf("h vs I (Circular D=%d, L=%d, R=%d)", 5, 40, 100)
outputFilename<-sprintf("%s/%s-%03d.pdf", experimentDir, title, experimentNumber)

cat("Writing file to '", outputFilename, "'\n")
pdf(file=outputFilename, width=8, height=8, bg="white")
plotXY(x,y, title, ylim=c(0, 20))
lines(y.quad.fitted~x, col="grey")
dev.off()


#To work out the probability density we take each of the fitted values and divide by the max
y.prob<-y.quad.fitted/max(y.quad.fitted)
plot(y.prob~x)

#Can then work out the cumulative probability 
y.prob.cumulative<-cumsum(y.prob)/sum(y.prob)
plot(y.prob.cumulative~x)

#this can then be used to generate random numbers- simply generate a uniform RN between 0-1 and see which value of X
#it corresponds to ...
