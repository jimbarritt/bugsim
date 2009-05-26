source("~/Work/code/bugsim/resource/R/Experiment 2/Experiment 2a Library.r", echo=FALSE)


outputGraphs<-function(outputDirectory, beta, title) {
	pdf(file=sprintf("%s/P of Actions beta=%d.pdf", outputDirectory, beta), width=15, height=5, bg="white")
	par(mfrow=c(1, 1))
	plotPA(beta, title)
	dev.off()
}

#betaV<-500
#title<-expression(beta==500)
plotPA<-function(betaV, title) {
	sumDensity<-0
	numerator<-rep(0, length(S.values.hist$density))
	for (i in 1:length(S.values.hist$density)) {
		sthetai<-S.values.hist$density[[i]]
		ecalc<-exp(betaV*sthetai)
		numerator[[i]]<-ecalc
		sumDensity<-sumDensity+ecalc 
	}

	pa<-rep(0, length(S.values.hist$density))
	for (i in 1:length(S.values.hist$density)) {
		pa[[i]]<-numerator[[i]]/sumDensity
	}



	xlabel<-expression(a[i])
	ylabel<-expression(p(a[i]))
	xnames<-rep("", length(pa))
	par(mar=c(5, 7, 2, 2))
	barplot(pa, main=title, xlab=xlabel, ylab=ylabel, cex.lab=1.5, cex.main=1.7, axes=FALSE, xlim=c(0, 19.5), ylim=c(0, 1))
	axis(side=1, at=c(0, 9.7, 19.5), labels=c("-180", "0", "180"), cex.axis=1.2)
	axis(side=2, at=c(0, 0.5, 1), labels=c("0", "0.5", 1))
}


outputBetaGraphs<-function(outputDirectory) {
	outputBetaGraph(0.05, 1000, expression(beta(gamma==0.05,beta[max]==1000)), outputDirectory)
	outputBetaGraph(0.1, 1000, expression(beta(gamma==0.1,beta[max]==1000)), outputDirectory)
	outputBetaGraph(0.25, 1000, expression(beta(gamma==0.25,beta[max]==1000)), outputDirectory)
	outputBetaGraph(0.5, 1000, expression(beta(gamma==0.5,beta[max]==1000)), outputDirectory)
	
}


outputBetaGraph<-function(gamma, betamax, title, outputDirectory) {	
	beta<-rep(0, 100)

	for (t in 2:100) {
		betaPrevious<-beta[[t-1]]
		deltaBeta<-gamma*(betamax-betaPrevious)
		beta[[t]]<-betaPrevious+deltaBeta
	}
	#quartz(width=10, height=10)
	filename<-sprintf("%s/Beta Graph gamma-%0.3f maxBeta-%0.0f.pdf", outputDirectory, gamma,betamax )
	cat("Writing Beta graph to :", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
	
	par(mar=c(5, 5, 5, 5))
	plot(beta, pch=NA, xlab="Time (t)", ylab=expression(beta), axes=FALSE, cex.lab=1.5, cex.main=1.7, cex.axis=1.2,main=title)
	lines(x=c(0, 100), y=c(betamax, betamax), col="grey")
	lines(beta, col="blue")
	axis(1, mgp=c(3,1 ,0))
	axis(2, at=c(0, betamax))
	dev.off()
}
