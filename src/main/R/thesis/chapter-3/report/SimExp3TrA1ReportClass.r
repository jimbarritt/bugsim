# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3A1Report", representation(
	foragerSummary="ForagerSummary",
	breaks="vector",
	initialA.hist="ANY",
	expectedCounts="vector",
	actualCounts="vector",
	chisq.result="ANY",
	ks.result="ANY",
	ks.punif.result="ANY",
	MAX_N="numeric"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3A1Report<-function(experimentPlan, breakSize, ksMAXN=4000) {
	instance<-new("SimExp3A1Report")
	
	instance<-StandardReport.initialise(instance, experimentPlan)
	
	instance@MAX_N<-ksMAXN
	itr.1<-getIteration(experimentPlan, 1)
	rep.1<-getReplicate(itr.1, 1)

	instance@foragerSummary<-ForagerSummary(rep.1, itr.1, 1)
	instance@breaks<-seq(from=0, to=360, by=10)
	instance@initialA.hist<-hist(instance@foragerSummary@summary.df$InitialAzimuth, breaks=instance@breaks, plot=FALSE)
	
	instance<-SimExp3A1Report.initialiseChiSq(instance)
	instance<-SimExp3A1Report.initialiseKS(instance)
			
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)

	instance@latexFile<-addTitle(instance@latexFile, sprintf("Trial A1 Report (%s)", experimentPlan@experimentDirName))

	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	


	
	return (instance)
}

SimExp3A1Report.initialiseKS<-function(instance) {
	N<-length(instance@foragerSummary@summary.df$InitialAzimuth)
	uniform<-runif(N, min=0, max=360)
	test<-instance@foragerSummary@summary.df$InitialAzimuth
	
	#Do the KS test with a random 5000 of the samples - because any more than about 7000
	#results in lots of "ties" i.e. same value more than once which the ks test doesnt like.
	#  	

	NKS<-N
	if (NKS > instance@MAX_N) {
		NKS<-instance@MAX_N
	}

	test<-sample(test, NKS)
	uniform<-sample(uniform, NKS)
	cat("Sampling ", NKS, " observations for kolmogrov-smirnov test, length(test)=", length(test), "\n")
		
	instance@ks.result<-ks.test(test,uniform, exact=TRUE)	
	
	# specify the punif distribution with min=0 and max=360 - this is important otherwise it wont compare the right numbers!
	instance@ks.punif.result<-ks.test(test, "punif", 0, 360)
	return (instance)
}
SimExp3A1Report.initialiseChiSq<-function(instance) {
	N<-length(instance@foragerSummary@summary.df$InitialAzimuth)
	NCOUNTS<-length(instance@initialA.hist$counts)
	instance@actualCounts<-instance@initialA.hist$counts
	p<-N/NCOUNTS
	
	instance@expectedCounts<-rep(p,NCOUNTS)

	chisq.mx<-rbind(instance@actualCounts, instance@expectedCounts)
	instance@chisq.result<-chisq.test(chisq.mx)
	return (instance)
}

setMethod("generateReport", signature=c("SimExp3A1Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3A1Report.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)




SimExp3A1Report.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile
	
	latexFile<-SimExp3A1Report.generateArithmeticStats(latexFile, report, report@foragerSummary@summary.df$InitialAzimuth)
	latexFile<-SimExp3A1Report.generateCircularStats(latexFile, report, report@foragerSummary@summary.df$InitialAzimuth,3000)
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	latexFile<-addSection(latexFile, "Summary Graphs", TRUE)	

	latexFile<-addSubSection(latexFile, "Linear", starred=FALSE)
	filename<-sprintf("%s/%s-summary-graph.pdf", report@figOutputDir, report@experimentPlan@experimentDirName)
	cat("Writing summary graph to: ", filename, "\n")
	
	pdf(file=filename, width=4, height=4, bg="white")
	par(mar=c(5, 5, 0, 0))
	plot(report)
	dev.off()
	
	latexFile<-addFigure(latexFile, filename, "h", 4,"Summary of initial azimuth generation", "fig:summary")
	
	latexFile<-addContent(latexFile, "\\clearpage")
	
	latexFile<-addSubSection(latexFile, "Circular", starred=FALSE)
	x.radians<-toRadians(report@foragerSummary@summary.df$InitialAzimuth)

	filename<-sprintf("%s/%s-circular-graph.pdf", report@figOutputDir, report@experimentPlan@experimentDirName)
	cat("Writing Circular Graph to: ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
	stats<-CircularReport.plot.distribution(x.radians, maxPlotN=2000)
	dev.off()
	
	latexFile<-addFigure(latexFile, filename, "h", 7,"Summary of initial azimuth generation", "fig:summary-circular")

	report@latexFile<-latexFile
	return (report)	
}


SimExp3A1Report.generateArithmeticStats<-function(latexFile, report, azimuths) {
	latexFile<-addSection(latexFile, "Arithmetic Statistics", TRUE)	
		
	maxAz<-max(azimuths)
	minAz<-min(azimuths)
	meanAz<-mean(azimuths)
	medianAz<-median(azimuths)
	sdAz<-sd(azimuths)
	

	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-beginTabular(latexFile, "lrl")
	latexFile<-addTableRow(latexFile, list("No. Observations(N)", sprintf("%0.0f", length(azimuths))))
	latexFile<-addTableRow(latexFile, list("Max Azimuth", sprintf("%0.4f", maxAz)))
	latexFile<-addTableRow(latexFile, list("Min Azimuth", sprintf("%0.4f", minAz)))
	latexFile<-addTableRow(latexFile, list("Mean Azimuth", sprintf("%0.4f", meanAz)))
	latexFile<-addTableRow(latexFile, list("SD Azimuth", sprintf("%0.4f", sdAz)))
	latexFile<-addTableRow(latexFile, list("Median Azimuth", sprintf("%0.4f", medianAz)))

	latexFile<-addTableRow(latexFile, list("$\\mathbf{\\chi^2}$ (vs calculated)", sprintf("%0.4f", report@chisq.result$statistic), sprintf("(p=%0.4f)", report@chisq.result$p.value)))
	latexFile<-addTableRow(latexFile, list(sprintf("ks (vs runif, N=%0.0f)", report@MAX_N), sprintf("%0.4f", report@ks.result$statistic), sprintf("(p=%0.4f)", report@ks.result$p.value)))
	latexFile<-addTableRow(latexFile, list(sprintf("ks (vs punif, N=%0.0f)", report@MAX_N), sprintf("%0.4f", report@ks.punif.result$statistic), sprintf("(p=%0.4f)", report@ks.punif.result$p.value)))


	latexFile<-endTabular(latexFile)

	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	latexFile<-addContent(latexFile, "p-values indicate the probability that the 2 samples come from the same population. So if $p<0.05$ they are significantly different. Can only say wether they are significantly different, not significantly the same!")

	
	return (latexFile)
}

SimExp3A1Report.generateCircularStats<-function(latexFile, report, azimuths, 
	test.N=2000, knownMu=NA, knownK=NA) {
		
	latexFile<-addContent(latexFile, "\\noindent")			
	latexFile<-addSection(latexFile, "Circular Statistics", TRUE)	
		
	
	N<-length(azimuths)	
	x.radians<-toRadians(azimuths)
	if (length(x.radians)>test.N) {
		x.radians.test<-sample(x.radians, test.N)
	} else {
		x.radians.test<-azimuths
	}

	#Mean vector...
	summary<-circ.summary(x.radians)

	#Estimated k value from von mises distribution
	est.k<-vm.ml(x.radians)

	#Generate a test distribution from this...
	vm.radians<-rvm(n=test.N, mean=est.k$mu, k=est.k$kappa)	

	#Watson test for uniformity:
	result.unif.test<-watson.test(x.radians, alpha=0.01, dist="uniform")


	#Rayleigh test for uniformity...
	result.ray.test<-v0.test(x.radians)


	#Watson test for von mises...
	result.vm.test<-watson.test(x.radians.test, alpha=0.01, dist="vm")


	#Compare it to estimated distribution...
	result.estimated.vm.test<-watson.two.test(x.radians.test, vm.radians, alpha=0.01, plot=FALSE)
		
	#Compare it to known distribution...
	if (!is.na(knownMu)) {
		result.known.vm.test<-watson.two.test(x.radians.test, vm.radians, alpha=0.01, plot=FALSE)		
	} else {
		result.known.vm.test<-NA
	}
	

	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-beginTabular(latexFile, "lr")
	
		latexFile<-addTableRow(latexFile, list("No. Observations(N)", sprintf("%0.0f", N)))
		latexFile<-addTableRow(latexFile, list("Sample Size", sprintf("%0.0f", test.N)))
		latexFile<-addTableRow(latexFile, list("Mean Vector $\\theta$", sprintf("%0.4f", toDegrees(summary$mean.dir))))
		latexFile<-addTableRow(latexFile, list("Mean Vector $\\rho$", sprintf("%0.4f", summary$rho)))
		latexFile<-addTableRow(latexFile, list("Estimated vm $\\mu$", sprintf("%0.4f", toDegrees(est.k$mu))))
		latexFile<-addTableRow(latexFile, list("Estimated vm $\\kappa$", sprintf("%0.4f", est.k$kappa)))

	latexFile<-endTabular(latexFile)


	latexFile<-addContent(latexFile, "\\vspace{6pt}\\\\")		
	latexFile<-addContent(latexFile, "\\noindent")	
	latexFile<-beginTabular(latexFile, "lrr")
		latexFile<-addTableRow(latexFile, list("", "\\textbf{r0.bar}", "\\textbf{p.value}")) 	
		latexFile<-addTableRow(latexFile, list("Rayleigh Test", 
									sprintf("%0.4f", result.ray.test$r0.bar),
									sprintf("%0.4f", result.ray.test$p.value)
		))
	latexFile<-endTabular(latexFile)


	latexFile<-addContent(latexFile, "\\vspace{6pt}\\\\")		
	latexFile<-addContent(latexFile, " ")		
	latexFile<-addContent(latexFile, "\\noindent")	
	latexFile<-beginTabular(latexFile, "lrrrl")
		latexFile<-addTableRow(latexFile, list("", "\\textbf{Sig. Level}", "\\textbf{Critical}", "\\textbf{Test}", "\\textbf{Summary}")) 	
		latexFile<-addWatsonResult(latexFile, result.unif.test, "Watson's test (Uniform)")
		latexFile<-addWatsonResult(latexFile, result.vm.test, "Watson's test (Von Mises)")
		latexFile<-addWatsonResult(latexFile, result.estimated.vm.test, "Watson's test (vs. Est.Von Mises)")
	
		if (!is.na(result.known.vm.test)) {
			latexFile<-addWatsonResult(latexFile, result.estimated.vm.test, "Watson's test (vs. Known Von Mises)")		
		}
	latexFile<-endTabular(latexFile)


	latexFile<-addContent(latexFile, "\\vspace{6pt}\\\\")		
	latexFile<-addContent(latexFile, "\\noindent")	
	latexFile<-addContent(latexFile, "The $H_0$ for the watson test is that the sample comes from the specified distribution, or that the two distributions are the same.")	
	
	return (latexFile)
}

addWatsonResult<-function(latexFile, result, title) {
	latexFile<-addTableRow(latexFile, list(title, 
									sprintf("%s",result$Sig.Level),
									sprintf("%0.4f",result$Critical.Value),
									sprintf("%0.4f",result$Test.Statistic),
									sprintf("%s",result$Summary)))
	
	return (latexFile)
}


#Add a "toString" which is a "show method"
setMethod("show", "SimExp3A1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3A1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3A1Report",
	function(x, y, ...) {
		plot(x@initialA.hist, main="", xlab=expression(theta))
		lines(x@expectedCounts~x@initialA.hist$mids, col="blue", lw=2)		
	}
)


