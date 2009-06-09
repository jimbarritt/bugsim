# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3B1E1Report", representation(
	experimentDir="character"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3B1E1Report<-function() {
	instance<-new("SimExp3B1E1Report")
				
	rootDir<-ExperimentData.getPublishedRootDir()
	experimentDir<-sprintf("%s/chapter-3/simulation-data/sim-exp3-TrB1-E1-001", rootDir)
	if (file.exists(experimentDir)==FALSE) {
		dir.create(experimentDir)
	}
	outputDir<-sprintf("%s/output", experimentDir)
	if (file.exists(outputDir)==FALSE) {
		dir.create(outputDir)
	}
	instance@experimentDir<-experimentDir
	instance@outputDir<-outputDir
	instance@figOutputDir<-sprintf("%s/figures", outputDir)
	if (file.exists(instance@figOutputDir)==FALSE) {
		dir.create(instance@figOutputDir)
	}
	
	
	filename<-sprintf("sim-exp3-TrB1-E1-001-report")
	instance@latexFile<-LatexFile(filename, experimentDir)

	instance@latexFile<-addTitle(instance@latexFile, "Analytical $\\mathbf{E(R^2)}$ Report (sim-exp3-TrB1-E1-001)")

	return (instance)
}

SimExp3B1E1Report.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile

	latexFile<-addEstimatedDisplacementSection(latexFile)

	latexFile<-addGraphSection(latexFile, report)

	latexFile<-addxAGEWithKSection(latexFile)


	report@latexFile<-latexFile
	return (report)	
}


addEstimatedDisplacementSection<-function(latexFile) {
	title<-sprintf("Estimated Displacement")
	latexFile<-addSection(latexFile, title, TRUE)
	latexFile<-addContent(latexFile, "\\vspace{12pt}\\noindent\\textit{ED is estimated distance (the side of the area) and ES is the distance to to the edge from the centre (radius of enclosed circle),ER is calculated from $Area=\pi r^2$}\\vspace{12pt}")

	latexFile<-addEstimatedDisplacementTable(latexFile, 10)
	latexFile<-addEstimatedDisplacementTable(latexFile, 3)
	latexFile<-addEstimatedDisplacementTable(latexFile, 0.5)
		
	latexFile<-addContent(latexFile, "\\clearpage")	
	return (latexFile)
}

addEstimatedDisplacementTable<-function(latexFile, k) {
	rho<-circular.rhoFromK(k)
	results<-md.plotExpectedMSD(rho, 1, "", plot=F)
	xAge<-round(results$intersectionAge, 0)
	
	
	title<-sprintf("($\\mathbf{k}=%0.2f$, timesteps=%0.0f)", k, xAge)
	latexFile<-addSubSection(latexFile, title, TRUE)

	
	
	latexFile<-beginTabular(latexFile, "rrrrrr")
		latexFile<-addTableRow(latexFile, list(
			"\\textbf{L}", 			
			"\\textbf{Steps}", 
			"\\textbf{MSD}", 
			"\\textbf{ED}", 
			"\\textbf{ES}",
			"\\textbf{ER}"
		))
		latexFile<-addContent(latexFile, "\\hline")	
		Ls<-c(1, 5, 10, 20, 50, 80, 100, 150, 200, 250)
		
		for (i.L in Ls) {
			latexFile<-addEstimatedDispersalRow(latexFile, k, i.L, xAge)
		}
	
	latexFile<-endTabular(latexFile)
	
	
	
	return (latexFile)
}

addEstimatedDispersalRow<-function(latexFile, k, L, timesteps) {
	msd<-md.calculateMSDForKL(K=k, L=L, timesteps=timesteps)
	
	latexFile<-addTableRow(latexFile, list(	
		sprintf("%0.0f", L), 
		sprintf("%0.0f", timesteps), 
		sprintf("%0.0f", msd), 
		sprintf("%0.0f", sqrt(msd)),
		sprintf("%0.0f", sqrt(msd)/2), 
		sprintf("%0.0f", sqrt(msd/pi))
	))
	return (latexFile)
	
}


addxAGEWithKSection<-function(latexFile) {
	latexFile<-addSection(latexFile, "xAGE Varies With $\\mathbf{k}$ (L=1)", TRUE)
	
	filename<-sprintf("%s/msd-xAGE-with-K.pdf", report@figOutputDir)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		results.df<-md.plotXAGEWithK()
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"xAGE Varies With  $\\mathbf{\\kappa}$", "fig:xAGE-k")
	
	latexFile<-beginTabular(latexFile, "lr")

		latexFile<-addTableRow(latexFile, list("\\textbf{min=}", sprintf("%0.0f", min(results.df$xAge))))
		latexFile<-addTableRow(latexFile, list("\\textbf{max=}", sprintf("%0.0f", max(results.df$xAge))))
	
	latexFile<-endTabular(latexFile)
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
# Doesnt work until we work out how to format the table so it goes over multiple columns
	
#	latexFile<-addSubSection(latexFile, "Table Of Results", TRUE)
#	latexFile<-addContent(latexFile, "\\vspace{6pt}")			
#	latexFile<-beginTabular(latexFile, "rr")
#
#
#		latexFile<-addTableRow(latexFile, list("$\\mathbf{k}$", "\\textbf{xAGE}"))
#		latexFile<-addContent(latexFile, "\\hline")		
#		
#		for (i in 1:length(results.df$k)) {
#			latexFile<-addTableRow(latexFile, list(
#				sprintf("%0.4f", results.df$k[i]), 
#				sprintf("%0.2f", results.df$xAge[i])
#				))			
#		}
#	
#	latexFile<-endTabular(latexFile)
	
		

	
	
	return (latexFile)
}

addxAGEWithLSection<-function(latexFile, k) {
	title<-sprintf("xAGE Varies With L ($\\mathbf{k}=%0.2f$)", k)
	latexFile<-addSection(latexFile, title, TRUE)
	
	name<-"msd-xAGE-with-K"
	filename<-sprintf("%s/%s.pdf", report@figOutputDir, name)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		results.df<-md.plotXAGEWithL(k)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,title, sprintf("fig:%s", name))
	
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	
	
	return (latexFile)
}



addGraphSection<-function(latexFile, report) {
	latexFile<-addContent(latexFile, "\\clearpage")
	
	latexFile<-addSection(latexFile, "Effect Of Angle Of Turn", TRUE)
	rowNums<-c(6, 31, 101, 111)
	graphIds<-c("a", "b", "c", "d")
	expectedLs<-c(1, 1, 1, 1)

	filename<-sprintf("%s/msd-effect-of-A.pdf", report@figOutputDir)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=10, height=10, bg="white")
		results.aot<-md.generateSelectedEMSD(rowNums, graphIds, expectedLs)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Effect of Angle Of Turn", "fig:effect-aot")
	
	latexFile<-addSubSection(latexFile, "Angle Of Turn", T)	
	latexFile<-SimExp3TrB1Report.addResultTable(latexFile, results.aot, c(1, 2, 3, 4), title=F)

	latexFile<-addContent(latexFile, "\\clearpage")

	latexFile<-addSection(latexFile, "Effect Of Step Length", TRUE)
	rowNums<-c(2, 2, 2, 2)
	graphIds<-c("a", "b", "c", "d")
	expectedLs<-c(1, 5, 10, 20)

	filename<-sprintf("%s/msd-effect-of-L.pdf", report@figOutputDir)
	cat(sprintf("Writing graph to %s...\n", filename))
	pdf(filename, width=12, height=12, bg="white")
		results.L<-md.generateSelectedEMSD(rowNums, graphIds, expectedLs)
	dev.off()
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Effect of Step Length", "fig:effect-L")

	
	latexFile<-addSubSection(latexFile, "Step Length", T)	
	latexFile<-SimExp3TrB1Report.addResultTable(latexFile, results.L, c(1, 2, 3, 4), title=F)
	latexFile<-addContent(latexFile, "\\clearpage")	
}



setMethod("generateReport", signature=c("SimExp3B1E1Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3B1E1Report.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)



#Add a "toString" which is a "show method"
setMethod("show", "SimExp3B1E1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3B1E1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3B1E1Report",
	function(x, y, ...) {
		
	}
)


