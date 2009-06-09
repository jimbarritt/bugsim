source("~/Work/code/bugsim/src/R/archive/Bugsim R Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/Experiment 1a Boundary Crossing Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/Experiment 1c/Exp 1c Library.r", echo=FALSE)

cat("Experiment 2a Library Version 1.0")

readInformationSurvey<-function(baseFilename, experimentNumber, trialId) {
	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)
	surveyFilename<-sprintf("%s/summary-information-surface-%s-%s-%03d.csv", directory, baseFilename, trialId, experimentNumber)
	cat("Reading information survey from :", surveyFilename, "\n")
	survey.df<-read.csv(surveyFilename)
	return (survey.df)
}


createSurveyMatrix<-function(informationSurvey.df, functionType) {
	sub.df<-subset(informationSurvey.df, informationSurvey.df$sensoryFunctionName==functionType)
	
	nRows<-(max(sub.df$surveyY)-min(sub.df$surveyY) + 1)
	nCols<-(max(sub.df$surveyX)-min(sub.df$surveyX)	+ 1)
	
	zm<-matrix(0, nrow=nRows, ncol=nCols)
	
	cat("Calculating surface matrix (", nRows , ", ", nCols, ")...\n")
	for (i.y in 0:max(sub.df$surveyY)) {
		for (i.x in 0:max(sub.df$surveyX)) {
			surveyValue<-sub.df$surveyValue[(sub.df$surveyX==i.x) &  (sub.df$surveyY==i.y)]
			zm[(i.x+1),(i.y+1)]<-surveyValue			
		}

	}
	cat("complete.\n")
	return (list(zm, sub.df))
}

#surfaceName<-'cabbageInformation'
plotSurveyGraphs<-function(results.vector, surfaceName, baseFilename, experimentNumber, trialId) {

	survey.mx<-results.vector[[1]]
	sub.df<-results.vector[[2]]

	
	x<-0:max(sub.df$surveyX)
	y<-0:max(sub.df$surveyY)
	
	quartz(width=10, height=10)
	image(x,y, z=survey.mx, main=sprintf("Image map of surface '%s'", surfaceName))
	
	quartz(width=10, height=10)
	contour(x,y, z=survey.mx, main=sprintf("Contour map of surface '%s'", surfaceName))
	
	quartz(width=10, height=10)
	trellis.par.set(theme = col.whitebg())
	wireframe(survey.mx, scales = list(arrows = FALSE),shade=TRUE, aspect = c(1, 1), screen=list(z=0, x=-45, y=30),main=sprintf("3D Surface of '%s'", surfaceName), xlab="X", ylab="Y", zlab="I")
	

	
}

outputGraphs<-function(results.vector, surfaceName, baseFilename, experimentNumber, trialId) {

	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)

	survey.mx<-results.vector[[1]]
	sub.df<-results.vector[[2]]

	
	x<-0:max(sub.df$surveyX)
	y<-0:max(sub.df$surveyY)

	filename<-sprintf("%s/output/%s-%03d Image map of %s.pdf", directory, trialId, experimentNumber, surfaceName)
	cat("Writing image map to : ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
	image(x,y, z=survey.mx, main=sprintf("Image map of surface '%s'", surfaceName))
	dev.off()

	filename<-sprintf("%s/output/%s-%03d Contour map of %s.pdf", directory, trialId, experimentNumber, surfaceName)
	cat("Writing countour map to : ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
	contour(x,y, z=survey.mx, main=sprintf("Contour map of surface '%s'", surfaceName))
	dev.off()
	
	filename<-sprintf("%s/output/%s-%03d 3D plot of %s.pdf", directory, trialId, experimentNumber, surfaceName)
	cat("Writing 3D surface to : ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
	trellis.par.set(theme = col.whitebg())
	wireframe(survey.mx, scales = list(arrows=FALSE),shade=TRUE, aspect = c(1, 1), screen=list(z=0, x=-45, y=30),main=sprintf("3D Surface of '%s'", surfaceName), xlab="X", ylab="Y", zlab="I")
	

}





