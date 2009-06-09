# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3A2Report", representation(
	foragerSummaries="list",
	MAX_KS_N="numeric",
	CIRC_SAMPLE_N="numeric",
	genLinear="logical",
	genCircular="logical",
	genGraphs="logical"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3A2Report<-function(experimentPlan, maxKSN, circSampleN, genLinear=TRUE, genCircular=TRUE, genGraphs=TRUE) {
	instance<-new("SimExp3A2Report")
	
	instance<-StandardReport.initialise(instance, experimentPlan)
	
	instance@MAX_KS_N<-maxKSN
	instance@CIRC_SAMPLE_N<-circSampleN
	instance@genLinear=genLinear
	instance@genCircular<-genCircular
	instance@genGraphs<-genGraphs
	
	foragerSummaries<-list()
	for (itr in experimentPlan@iterations) {
		rep<-getReplicate(itr, 1)
		foragerSummary<-ForagerSummary(rep, itr, 1)		
		foragerSummaries<-collection.appendToList(foragerSummaries, foragerSummary)
	}
	instance@foragerSummaries<-foragerSummaries

			
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)

	instance@latexFile<-addTitle(instance@latexFile, sprintf("Angle Of Turn Report (%s)", experimentPlan@experimentDirName))

	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-StandardReport.generateIterationSummarySection(instance@latexFile, experimentPlan)
	


	
	return (instance)
}

SimExp3A2Report.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile

	if (report@genLinear) {
		latexFile<-SimExp3A2Report.generateArithmeticStats(latexFile, report)	
	}
	
	if (report@genCircular) {
		latexFile<-SimExp3A2Report.generateCircularStats(latexFile, report)	
	}

	if (report@genGraphs) {
		latexFile<-generateGraphSection(latexFile, report)
	}
	

	report@latexFile<-latexFile
	return (report)	
}



SimExp3A2Report.ks.rvm<-function(foragerSummary, maxN) {
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn	
	aot.radians<-toRadians(aot)
	
	if (foragerSummary@iteration@azimuthGenerator=="vonMises") {
		k<-foragerSummary@iteration@angleOfTurn
	} else {
		k<-est.kappa(aot.radians)	
	}

	obs.N<-length(aot)
	vm.radians<-rvm(obs.N, mean=0, k=k)
	vm.radians<-sapply(vm.radians, Geometry.toAngleOfTurnRadians)
	
	
	#Do the KS test with a maxN of the samples - because any more than about 7000
	#results in lots of "ties" i.e. same value more than once which the ks test doesnt like.
	#  	
	if (obs.N>maxN) {
		obs.sample<-sample(aot.radians, maxN)
		exp.sample<-sample(vm.radians, maxN)
	} else {
		obs.sample<-aot.radians
		exp.sample<-vm.radians
	}

	
	cat("Sampling ", length(obs.sample), " observations from " , obs.N, "for kolmogrov-smirnov test\n")
		
	ks.result<-ks.test(obs.sample, exp.sample, exact=TRUE)	
	
	return (ks.result)
}

SimExp3A2Report.chiSqTest<-function(foragerSummary) {
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn	
	if (foragerSummary@iteration@azimuthGenerator=="vonMises") {
		k<-foragerSummary@iteration@angleOfTurn
	} else {
		k<-est.kappa(toRadians(aot))	
	}

	mu<-0

	breakSize<-10
	breaks<-seq(from=-180, to=180, by=breakSize)
	minAot<-min(aot)
	maxAot<-max(aot)
	if (minAot<(-180) || maxAot>180) {
		cat("WARNING: Having to increase range of breaks because: min=" ,  minAot, ", max=", maxAot, "\n")
		breaks<-seq(from=-190, to=190, by=breakSize)
	} 

	aot.hist<-hist(aot, breaks=breaks, plot=FALSE)

	N<-length(aot)
	NCOUNTS<-length(aot.hist$counts)
	actualCounts<-aot.hist$counts
	mids<-aot.hist$mids

	pdvm<-dvm.degrees(mids, mu, k, breakSize, N)
	expectedCounts<-prob.createDistribution(pdvm)*N

	chisq.mx<-rbind(actualCounts, expectedCounts)
	chisq.result<-chisq.test(chisq.mx)
	return (chisq.result)
}

setMethod("generateReport", signature=c("SimExp3A2Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3A2Report.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)





generateGraphSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")
	latexFile<-addSubSection(latexFile, "Linear Summary Graphs", TRUE)	
	
	for (foragerSummary in report@foragerSummaries) {
		latexFile<-SimExp3A2Report.generateLinearGraphs( latexFile, report,foragerSummary)
		latexFile<-addContent(latexFile, "\\clearpage")		
	}
	
	latexFile<-addContent(latexFile, "\\clearpage")
	latexFile<-addSubSection(latexFile, "Circular Summary Graphs", TRUE)	
	for (foragerSummary in report@foragerSummaries) {
		latexFile<-SimExp3A2Report.generateCircularGraphs( latexFile, report,foragerSummary)
		latexFile<-addContent(latexFile, "\\clearpage")		
	}
	return (latexFile)
	
}


SimExp3A2Report.generateLinearGraphs<-function(latexFile, report, foragerSummary) {		
	iterationNumber<-foragerSummary@iteration@iterationNumber
	latexFile<-addSubSubSection(latexFile, sprintf("Iteration %03.0f", iterationNumber))
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn	
	filename<-sprintf("%s/%s-%03.0f-linear-graph.pdf", report@figOutputDir, report@experimentPlan@experimentDirName, iterationNumber)
	cat("Writing summary graph to: ", filename, "\n")
	
	pdf(file=filename, width=10, height=10, bg="white")

		par(mar=c(5, 5, 5, 5))
	
		mu<-0
		if (foragerSummary@iteration@azimuthGenerator=="vonMises") {
			k<-foragerSummary@iteration@angleOfTurn
		} else {
			k<-est.kappa(toRadians(aot)) #Estimate it!
		}
		breakSize<-10
		x<-seq(from=-180, to=180, by=breakSize)

		minAot<-min(aot)
		maxAot<-max(aot)
		if (minAot<(-180) || maxAot>180) {
			cat("WARNING: Having to increase range of breaks because: min=" ,  minAot, ", max=", maxAot, "\n")
			x<-seq(from=-190, to=190, by=breakSize)
		} 
		
		hist<-hist(aot,breaks=x, main=sprintf("k=%0.3f", k), freq=TRUE,xlim=c(-180, 180), axes=FALSE)
		axis(1, at=seq(-180, 180, 45))
		axis(2)
		N<-sum(hist$counts)
		pdvm<-dvm.degrees(hist$mids, mu, k, breakSize, N)
		pdvm.dist<-prob.createDistribution(pdvm)*N
		lines(pdvm.dist~hist$mids, col="blue")			
	
	dev.off()
	
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Summary of angles of turn", sprintf("fig:linear-%03.0f", iterationNumber))
		
	return (latexFile)
}

#quartz(width=5.9, height=5.9)
#foragerSummary<-report@foragerSummaries[[1]]
SimExp3A2Report.generateCircularGraphs<-function(latexFile, report, foragerSummary) {		
	iterationNumber<-foragerSummary@iteration@iterationNumber
	latexFile<-addSubSubSection(latexFile, sprintf("Iteration %03.0f", iterationNumber))
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn	
	filename<-sprintf("%s/%s-%03.0f-circular-graph.pdf", report@figOutputDir, report@experimentPlan@experimentDirName, iterationNumber)
	cat("Writing summary graph to: ", filename, "\n")
	
	pdf(file=filename, width=10, height=10, bg="white")

		par(mar=c(5, 5, 5, 5))
	
		mu<-0
		k<-foragerSummary@iteration@angleOfTurn

		x.radians<-toRadians(aot)
		stats<-CircularReport.plot.distribution(x.radians, maxPlotN=2000)
	
	dev.off()
	
	latexFile<-addFigure(latexFile, filename, "ht", 7,sprintf("Summary of angles of turn (k=%0.3f), n=%0.0f", k, length(aot)), sprintf("fig:circular-%03.0f", iterationNumber))
		
	return (latexFile)
}



	


SimExp3A2Report.generateArithmeticStats<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")		
	latexFile<-addSection(latexFile, "Arithmetic Statistics", FALSE)	
	latexFile<-addSubSubSection(latexFile, "Angle Of Turn Summary")	
	
	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-beginTabular(latexFile, "lrrrrrrr")

		latexFile<-addTableRow(latexFile, list("\\textbf{Iteration}", "\\textbf{N}", "\\textbf{Max}", "\\textbf{Min}", "\\textbf{Mean}", "\\textbf{s.d.}", "\\textbf{median}"))
		latexFile<-addContent(latexFile, "\\hline")		
		for (foragerSummary in report@foragerSummaries) {	
			latexFile<-addArithmeticStatsRow(latexFile, foragerSummary)
		}
		
	latexFile<-endTabular(latexFile)
	
	latexFile<-addSubSubSection(latexFile, "Statistical Tests")	
	
	latexFile<-addContent(latexFile, "\\vspace{6pt}")			
	latexFile<-beginTabular(latexFile, "lrrrr|rrr")
		latexFile<-addTableRow(latexFile, list("\\textbf{Iteration}", 
		"\\textbf{$\\chi^2$}", "\\textbf{(N)}", "\\textbf{d.f}", 
		"\\textbf{p-value}", "\\textbf{k-s}", "\\textbf{(N)}", "\\textbf{p-value}"))
		latexFile<-addContent(latexFile, "\\hline")
		for (foragerSummary in report@foragerSummaries) {		
			chisq<-SimExp3A2Report.chiSqTest(foragerSummary)
			nchisq<-length(foragerSummary@anglesOfTurn.df$LastAngleOfTurn)
			
			ks.results<-SimExp3A2Report.ks.rvm(foragerSummary, report@MAX_KS_N)
			nks<-nchisq
			if (nks > report@MAX_KS_N) {
				nks<-report@MAX_KS_N
			}
			
			latexFile<-addArithmeticTestRow(latexFile, chisq, foragerSummary@iteration@iterationNumber, nchisq, ks.results, nks)	
		}
		
	latexFile<-endTabular(latexFile)

	latexFile<-addContent(latexFile, "\\vspace{6pt}")		

	latexFile<-addContent(latexFile, "\\noindent\\textit{p-values indicate the probability that the 2 samples come from the same population. So if $p<0.05$ they are significantly different. Can only say wether they are significantly different, not significantly the same!}")

	
	return (latexFile)
}

addArithmeticTestRow<-function(latexFile, chisq, iterationNumber, nchi, ks, nks) {
	latexFile<-addTableRow(latexFile, list(
							sprintf("\\textbf{%03.0f}", iterationNumber),							
							sprintf("%0.0f", chisq$statistic),
							sprintf("%0.0f", nchi),
							sprintf("%0.0f", chisq$parameter),
							printPValue(chisq$p.value),
							sprintf("|%0.4f", ks$statistic),
							sprintf("%d", nks),							
							printPValue(ks$p.value)
							
	))
	
	return (latexFile)
}

printPValue<-function(pvalue) {
	if (pvalue<0.0001) {
		out<-"\\textbf{$<$ 0.0001}"
	} else {	
		if (pvalue<0.05) {
			sprintf("\\textbf{%0.4f}", pvalue)
		}else {
			sprintf("%0.4f", pvalue)
		}
	}
}
addArithmeticStatsRow<-function(latexFile, foragerSummary) {
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn		
	maxAz<-max(aot)
	minAz<-min(aot)
	meanAz<-mean(aot)
	medianAz<-median(aot)
	sdAz<-sd(aot)
	
	latexFile<-addTableRow(latexFile, list(
							sprintf("\\textbf{%03.0f}", foragerSummary@iteration@iterationNumber),
							sprintf("%0.0f", length(aot)),
							sprintf("%0.4f", maxAz),
							sprintf("%0.4f", minAz),
							sprintf("%0.4f", meanAz),
							sprintf("%0.4f", sdAz),
							sprintf("%0.4f", medianAz)
	))
	
	return (latexFile)
}

SimExp3A2Report.generateCircularStats<-function(latexFile, report) {
		
	latexFile<-addSection(latexFile, "Circular Statistics", FALSE)	
	
	latexFile<-addSubSubSection(latexFile, "Angle Of Turn Summary")	
	
	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-addContent(latexFile, "\\setlength{\\tabcolsep}{5mm}")
	latexFile<-beginTabular(latexFile, "lrrrrrrr")

		latexFile<-addTableRow(latexFile, list("\\textbf{Iteration}", 
			"\\textbf{N}", "\\textbf{$\\theta$}", "\\textbf{$\\rho$}", "\\textbf{s}",
			"\\textbf{est. $\\mu$}", "\\textbf{est. $\\kappa$}", "\\textbf{mean($\\cos{\\theta}$)}"))
		latexFile<-addContent(latexFile, "\\hline")		
		for (foragerSummary in report@foragerSummaries) {	
			latexFile<-addCircularStatsRow(latexFile, foragerSummary)
		}
		
	latexFile<-endTabular(latexFile)


	latexFile<-addSubSubSection(latexFile, "Rayleigh Test For Uniformity")	
	
	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-beginTabular(latexFile, "lrrr")

		latexFile<-addTableRow(latexFile, list("\\textbf{Iteration}", 
			"\\textbf{N}", "\\textbf{r0.bar}", "\\textbf{p.value}"))
		latexFile<-addContent(latexFile, "\\hline")
				
		for (foragerSummary in report@foragerSummaries) {	
			latexFile<-addRayleighTestRow(latexFile, foragerSummary)
		}
		
	latexFile<-endTabular(latexFile)

	latexFile<-addSubSubSection(latexFile, "Watson's Test for uniformity")	
	
	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-beginTabular(latexFile, "lrrrrrl")

		latexFile<-addTableRow(latexFile, list("\\textbf{Iteration}", 
			"\\textbf{N}","\\textbf{$\\kappa$}", "\\textbf{p-level}", "\\textbf{Critical}",
			 "\\textbf{Test}", "\\textbf{Summary}"))
		latexFile<-addContent(latexFile, "\\hline")
				
		for (foragerSummary in report@foragerSummaries) {	
			latexFile<-addWatsonUniformTestRow(latexFile, foragerSummary)
		}
		
	latexFile<-endTabular(latexFile)


	latexFile<-addSubSubSection(latexFile, "Watson's Test for von Mises Distribution")	
	
	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-beginTabular(latexFile, "lrrrrrl")

		latexFile<-addTableRow(latexFile, list("\\textbf{Iteration}", 
			"\\textbf{N}","\\textbf{$\\kappa$}", "\\textbf{p-level}", "\\textbf{Critical}",
			 "\\textbf{Test}", "\\textbf{Summary}"))
		latexFile<-addContent(latexFile, "\\hline")
				
		for (foragerSummary in report@foragerSummaries) {	
			latexFile<-addWatsonVonMisesTestRow(latexFile, foragerSummary, report@CIRC_SAMPLE_N)
		}
		
	latexFile<-endTabular(latexFile)
		
	latexFile<-addSubSubSection(latexFile, "Watson's Two Sample Test against von Mises Parameters")	

	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-beginTabular(latexFile, "lrrrrrl")

		latexFile<-addTableRow(latexFile, list("\\textbf{Iteration}", 
			"\\textbf{N}","\\textbf{$\\kappa$}", "\\textbf{p-level}", "\\textbf{Critical}",
			 "\\textbf{Test}", "\\textbf{Summary}"))
		latexFile<-addContent(latexFile, "\\hline")
				
		for (foragerSummary in report@foragerSummaries) {	
			latexFile<-addWatsonEstimatedVonMisesComparisonRow(latexFile, foragerSummary, report@CIRC_SAMPLE_N)
		}
		
	latexFile<-endTabular(latexFile)

	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-addContent(latexFile, "\\noindent\\textit{The $H_0$ for the watson test is that the sample comes from the specified distribution, or that the two distributions are the same.}")	
	
	latexFile<-addContent(latexFile, "\\noindent \\textit{If the azimuth generater is not von-mises, the k parameter will be estimated.}")								
	
	return (latexFile)
}

addCircularStatsRow<-function(latexFile, foragerSummary) {
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn		
	N<-length(aot)	
	x.radians<-toRadians(aot)

	#Mean vector...
	summary<-circ.summary(x.radians)

	#Estimated k value from von mises distribution
	est.k<-vm.ml(x.radians)
	
	angularDeviation<-sqrt( 2*( 1-summary$rho) )

	mean.cos<-mean(cos(x.radians))
	
	latexFile<-addTableRow(latexFile, list(
							sprintf("\\textbf{%03.0f}", foragerSummary@iteration@iterationNumber),
							sprintf("%0.0f", N),
							sprintf("%0.4f", toDegrees(summary$mean.dir)),
							sprintf("%0.4f", summary$rho),
							sprintf("%0.4f", toDegrees(angularDeviation)),
							sprintf("%0.4f", toDegrees(est.k$mu)),
							sprintf("%0.4f", est.k$kappa),
							sprintf("%0.4f", mean.cos)
	))
	
	return (latexFile)
}
addRayleighTestRow<-function(latexFile, foragerSummary) {
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn		
	N<-length(aot)	
	x.radians<-toRadians(aot)

	result.ray.test<-v0.test(x.radians)

	
	latexFile<-addTableRow(latexFile, list(
							sprintf("\\textbf{%03.0f}", foragerSummary@iteration@iterationNumber),
							sprintf("%0.0f", N),
							sprintf("%0.4f", result.ray.test$r0.bar),
							printPValue(result.ray.test$p.value)
	))
	
	return (latexFile)
}
addWatsonUniformTestRow<-function(latexFile, foragerSummary) {
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn		
	N<-length(aot)	
	x.radians<-toRadians(aot)

	cat("Testing ", length(x.radians), " observations for watson uniformity test\n")
	result.unif.test<-watson.test(x.radians, alpha=0.01, dist="uniform")

	latexFile<-addWatsonResult(latexFile, result.unif.test, foragerSummary@iteration@iterationNumber, N, NA)
	
	return (latexFile)
}
addWatsonVonMisesTestRow<-function(latexFile, foragerSummary, test.N) {
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn		
	N<-length(aot)	
	x.radians<-toRadians(aot)
	if (length(x.radians)>test.N) {
		x.radians.test<-sample(x.radians, test.N)
	} else {
		x.radians.test<-x.radians
	}

	cat("Testing ", length(x.radians.test), " observations for watson von mises test\n")
	kappa<-est.kappa(x.radians)
	result.vm.test<-watson.test(x.radians.test, alpha=0.01, dist="vm")
	

	latexFile<-addWatsonResult(latexFile, result.vm.test, foragerSummary@iteration@iterationNumber, length(x.radians.test), kappa)
	
	return (latexFile)
}

addWatsonEstimatedVonMisesComparisonRow<-function(latexFile, foragerSummary, test.N) {
	aot<-foragerSummary@anglesOfTurn.df$LastAngleOfTurn		
	N<-length(aot)	
	x.radians<-toRadians(aot)
	if (length(x.radians)>test.N) {
		x.radians.test<-sample(x.radians, test.N)
	} else {
		x.radians.test<-x.radians
	}

	mu<-0	
	if (foragerSummary@iteration@azimuthGenerator=="vonMises") {		
		kappa<-foragerSummary@iteration@angleOfTurn
	} else {
		kappa<-est.kappa(x.radians)
	}
	
	vm.radians<-rvm(length(x.radians.test), mean=mu, k=kappa)	
	cat("Testing ", length(x.radians.test), " observations against " , length(vm.radians), "for watson two sample test\n")
	
	result.vm.test<-watson.two.test(x.radians.test, vm.radians, alpha=0.01, plot=FALSE)	
	
	
	latexFile<-addWatsonResult(latexFile, result.vm.test, foragerSummary@iteration@iterationNumber, length(x.radians.test), kappa)
		
	return (latexFile)
}


addWatsonResult<-function(latexFile, result, iterationNumber, N, kappa) {
	if (is.na(kappa)) {
		kappaFmt<-"-"
	} else {
		kappaFmt<-sprintf("%0.3f",kappa)
	}
	latexFile<-addTableRow(latexFile, list(
									sprintf("\\textbf{%03.0f}", iterationNumber),
									sprintf("%d",N),
									kappaFmt,
									sprintf("%s",result$Sig.Level),
									sprintf("%0.4f",result$Critical.Value),
									sprintf("%0.4f",result$Test.Statistic),
									sprintf("%s",result$Summary)
	))
	
	return (latexFile)
}



oldCircularStuff<-function() {
	
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
	
}



#Add a "toString" which is a "show method"
setMethod("show", "SimExp3A2Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3A2Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3A2Report",
	function(x, y, ...) {
		plot(x@initialA.hist, main="", xlab=expression(theta))
		lines(x@expectedCounts~x@initialA.hist$mids, col="blue", lw=2)		
	}
)


