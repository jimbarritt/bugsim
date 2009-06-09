#Test the uniformity of distribution for the initial angles.

butterflies.df <- read.csv("004-001-001-butterflies.csv")



bfheading.df <- data.frame("heading"=butterflies.df$InitialHeading)
str(bfheading.df)

par(mfrow=c(2,2))
breakSize<-20
breakCount<-18
h<-hist(bfheading.df$heading[1:10], main="n=10", xlab="Angle", ylab="Frequency", axes=FALSE,
freq=TRUE, br=breakSize*0:breakCount)
h
labelSeq <- seq(1, length(h$breaks), 3)
breaks.labels <- h$breaks[labelSeq]
length(breaks.labels)
axis(1, at=labelSeq, labels=breaks.labels)

hist(bfheading.df$heading[1:100])
hist(bfheading.df$heading[1:1000])
hist(bfheading.df$heading[1:100000])


bf10.hist <- createHistogram(bfheading.df$heading, 1, 10, 20, 18)
bf100.hist <- createHistogram(bfheading.df$heading, 1, 100, 20, 18)
bf1000.hist <- createHistogram(bfheading.df$heading, 1, 1000, 20, 18)
bf10000.hist <- createHistogram(bfheading.df$heading, 1, 10000, 20, 18)
bf100000.hist <- createHistogram(bfheading.df$heading, 1, 100000, 20, 18)
#bf1000000.hist <- createHistogram(bfheading.df$heading, 1, 1000000, 20, 18) #need to generate 1 million!!

quartz(width=11.75, height=8.25)
pdf(file="Initial Angle Of Turn Distribution.pdf", width=11.75, height=8.25)
par(mfrow=c(2,2))
plotManyHistograms(list(bf10.hist), "n=10", "Angle")
plotManyHistograms(list(bf100.hist), "n=100", "Angle")
plotManyHistograms(list(bf1000.hist), "n=100", "Angle")
#plotManyHistograms(list(bf10000.hist), "n=10000", "Angle")
plotManyHistograms(list(bf100000.hist), "n=100000", "Angle")
#plotManyHistograms(list(bf1000000.hist), "n=1000000", "Angle")
dev.off()




bf1000.B.hist <- createHistogram(bfheading.df$heading, 1001, 2000, 20, 18)
bf1000.C.hist <- createHistogram(bfheading.df$heading, 2001, 3000, 20, 18)
bf1000.D.hist <- createHistogram(bfheading.df$heading, 3001, 4000, 20, 18)


data.hist.list <- list(bf100000.hist, bf10000.hist, bf1000.hist)
data.hist.list <- list(bf1000.A.hist, bf1000.B.hist, bf1000.C.hist, bf1000.D.hist)
data.hist.list <- list(bf10.hist)


all.hist.df <- data.frame(Replicant=)
all.hist.df$

data.hist.list <- list(bf1000.C.hist)
plotManyHistograms(data.hist.list, "Frequency Of Intial turn", "Angle")



