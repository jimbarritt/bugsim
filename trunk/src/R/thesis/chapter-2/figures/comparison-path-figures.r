source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("/thesis/common/probability/Probability Library.r")
lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")
lib.loadLibrary("/thesis/common/fractals/Fractal Resource Distribution Library.r")
lib.loadLibrary("/thesis/common/circular/Circular Distributions Library.r")
lib.loadLibrary("/thesis/common/movement/Random Walk Library.r")



ch2.output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/02-review/resources/figures"

quartz(width=8, height=8)


D<-2
r0<-10

outputFile<-sprintf("%s/comparison-path-types.pdf",ch2.output.figures)
cat("Writing graph to ", outputFile, "\n")
pdf(file=outputFile, width=18, height=6, bg="white")
rwalk.plotComparisonWalks(100)
dev.off()



