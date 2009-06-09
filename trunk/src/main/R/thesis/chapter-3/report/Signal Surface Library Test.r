source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("/thesis/chapter-3/Signal Surface Library.r")

experimentPlan<-ex.loadPlan("raw", "sense", "exp2", "TrX1", 1)

#todo- could do with specifying a scale .... infact could do with a whole scale package for the graphs.
itr.1.signalSurvey<-SignalSurvey(experimentPlan, 1)

survey.df<-itr.1.signalSurvey@survey.df

#colnames(survey.df)


surface.1<-getSurface(itr.1.signalSurvey, 1)
survey.mx<-surface.1@surfaceMatrix
x<-1:surface.1@countX
y<-1:surface.1@countY
surfaceName<-"Test"
quartz(width=10, height=10)
image(x,y, z=survey.mx, main=sprintf("Image map of surface '%s'", surfaceName))

quartz(width=10, height=10)
contour(x,y, z=survey.mx, main=sprintf("Contour map of surface '%s'", surfaceName))

quartz(width=10, height=10)
trellis.par.set(theme = col.whitebg())


writePDF(survey.mx, surfaceName)
writePDF<-function(survey.mx, surfaceName) {
	pdf(file="~/Desktop/jim.pdf")
	print(wireframe(survey.mx, scales = list(arrows = FALSE),shade=TRUE, aspect = c(1, 1), screen=list(z=0, x=-45, y=30),main=sprintf("3D Surface of '%s'", surfaceName), xlab="X", ylab="Y", zlab="S"))
	dev.off()
}