source("~/Work/code/bugsim/src/R/thesis/common/fractals/Fractal Resource Distribution Library.r", echo=FALSE)

source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("/thesis/common/probability/Probability Library.r")
lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")
lib.loadLibrary("/thesis/common/fractals/Fractal Resource Distribution Library.r")
lib.loadLibrary("/thesis/common/circular/Circular Distributions Library.r")
lib.loadLibrary("/thesis/common/movement/Random Walk Library.r")

#frez.output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/chapter-3/Fractal Distributions/resources/figures"
frez.output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/08-appendix/resources/figures"

quartz(width=8, height=8)
par(mfrow=c(1,1))

D<-2
r0<-10

outputFile<-sprintf("%s/upper_vs_lower_tailed_u.pdf",frez.output.figures)
pdf(file=outputFile, width=8, height=8, bg="white")
frez.plotUpperVsLowerTailed(D, r0)
dev.off()

frez.plotUpperVsLowerTailedDivision()

outputFile<-sprintf("%s/upper_vs_lower_tailed_division.pdf",frez.output.figures)
pdf(file=outputFile, width=8, height=8, bg="white")
frez.plotUpperVsLowerTailedDivision()
dev.off()


path.levy.d1<-rwalk.levyRandomWalk(n=1000, minStepLength=2, maxStepLength=1000,fractalDimension=1, startX=0, startY=0)
outputFile<-sprintf("%s/levy-dust-n-500-d-1.pdf",frez.output.figures)
pdf(file=outputFile, width=8, height=8, bg="white")
	rwalk.plotPath(path.levy.d1, scale=400, "", points=T, axesLab=T)
dev.off()

path.levy.d15<-rwalk.levyRandomWalk(n=1000, minStepLength=2, maxStepLength=1000,fractalDimension=1.5, startX=0, startY=0)
outputFile<-sprintf("%s/levy-dust-n-500-d-1-5.pdf",frez.output.figures)
pdf(file=outputFile, width=8, height=8, bg="white")
	rwalk.plotPath(path.levy.d15, scale=120, "", points=T, axesLab=T)
dev.off()

path.levy.d2<-rwalk.levyRandomWalk(n=1000, minStepLength=2, maxStepLength=1000,fractalDimension=2, startX=0, startY=0)
outputFile<-sprintf("%s/levy-dust-n-500-d-2.pdf",frez.output.figures)
cat("Writing graph to ", outputFile, "\n")
pdf(file=outputFile, width=8, height=8, bg="white")
	rwalk.plotPath(path.levy.d2, scale=120, "", points=T, axesLab=T)
dev.off()

