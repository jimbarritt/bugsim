source("~/Documents/Projects/bugsim/documentation/masters-thesis/Chapter-2/perception/resources/R/Sensory Signal Model Library.r", echo=FALSE)

outputDir<-"~/Documents/Projects/bugsim/documentation/masters-thesis/Chapter-2/perception/resources/figures"
namesA<-c("-180", "", "", "-90", "","","0", "","","90", "","","180"  )

signalDistA1<-c(10,0, 4 ,0, 8, 2, 3, 4, 5, 6, 8, 0, 4)
signalDistA2<-c(0, 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 0)

signalDistB1<-c(1, 2, 6, 5, 0, 5, 6, 3, 4, 10, 2, 7, 0)
signalDistB2<-c(0, 0, 0, 5,0,  0, 0, 0, 0, 5, 0, 0, 0)
signalDistB3<-rep(2, 13)
signalDistB4<-rep(0, 13)


quartz(width=15, height=10)
sense.plotBayesianVsModelAverage(namesA, signalDistA1, signalDistB1)
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB2)
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB3)
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB4)



pdf(file=sprintf("%s/%s",outputDir, "Bayesian-vs-Model-Average-1.pdf"), width=15, height=10, bg="white")
sense.plotBayesianVsModelAverage(namesA, signalDistA1, signalDistB1)
dev.off()

pdf(file=sprintf("%s/%s",outputDir, "Bayesian-vs-Model-Average-2.pdf"), width=15, height=10, bg="white")
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB2)
dev.off()

pdf(file=sprintf("%s/%s",outputDir, "Bayesian-vs-Model-Average-3.pdf"), width=15, height=10, bg="white")
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB3)
dev.off()

pdf(file=sprintf("%s/%s",outputDir, "Bayesian-vs-Model-Average-4.pdf"), width=15, height=10, bg="white")
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB4)
dev.off()


#All in one pdf:
pdf(file=sprintf("%s/%s",outputDir, "Bayesian vs Model Average Summary.pdf"), width=15, height=10, bg="white")
sense.plotBayesianVsModelAverage(namesA, signalDistA1, signalDistB1)
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB2)
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB3)
sense.plotBayesianVsModelAverage(namesA, signalDistA2, signalDistB4)
dev.off()

