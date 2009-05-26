# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("SimExp3B1Report", representation(
	genJohnson="logical",
	genAOT="logical"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
SimExp3B1Report<-function(experimentPlan, genJohnson=T, genAOT=T) {
	instance<-new("SimExp3B1Report")
	
	instance<-StandardReport.initialise(instance, experimentPlan)	
	
	instance@genJohnson<-genJohnson
	instance@genAOT<-genAOT
			
	filename<-sprintf("%s-report", experimentPlan@experimentDirName)
	instance@latexFile<-LatexFile(filename, experimentPlan@experimentDir)

	instance@latexFile<-addTitle(instance@latexFile, sprintf("MSD Over Time Report (%s)", experimentPlan@experimentDirName))

	instance@latexFile<-StandardReport.generatePlanSummarySection(instance@latexFile, experimentPlan)
	
	instance@latexFile<-StandardReport.generateIterationSummarySection(instance@latexFile, experimentPlan)
	

	
	return (instance)
}

SimExp3B1Report.generateDescriptiveSection<-function(report) {
	latexFile<-report@latexFile

	latexFile<-SimExp3B1Report.generateSummaryGraphs(latexFile, report)

	report@latexFile<-latexFile
	return (report)	
}

SimExp3B1Report.generateSummaryGraphs<-function(latexFile, report) {
	
	latexFile<-addContent(latexFile, "\\clearpage")

	
	if (report@genJohnson) {
		latexFile<-generateJohnsonGraph(latexFile, report)
	}
	
	if (report@genAOT) {
		latexFile<-generateAOTGraphs(latexFile, report)
	}
	
	
	lengthIterations_1<-c(1, 2, 3, 4)
	lengthIterations_2<-c(5, 6, 7, 8)
	lengthIterations_3<-c(9, 10, 11, 12)
	lengthIterations_4<-c(13, 14, 15, 16)
	lengthIterations_5<-c(17, 18, 19, 20)
	
	latexFile<-SimExp3B1Report.generateLengthGraphs(latexFile, report, lengthIterations_1, 1)
	latexFile<-SimExp3B1Report.generateLengthGraphs(latexFile, report, lengthIterations_2, 2)
	latexFile<-SimExp3B1Report.generateLengthGraphs(latexFile, report, lengthIterations_3, 3)
	latexFile<-SimExp3B1Report.generateLengthGraphs(latexFile, report, lengthIterations_4, 4)
	latexFile<-SimExp3B1Report.generateLengthGraphs(latexFile, report, lengthIterations_5, 5)

	return (latexFile)
}

generateAOTGraphs<-function(latexFile, report) {
	aotIterations<-c(1, 5, 9, 13)

	filename<-sprintf("%s/%s-summarey-L-1.pdf", report@figOutputDir, report@experimentPlan@experimentDirName)
	cat("Writing graph to: ", filename, "\n")
	
	graphIds<-c("a", "b", "c", "d")
	result.list<-list()
	i<-1
	pdf(file=filename, width=10, height=10, bg="white")

		par(mar=c(5, 5, 5, 5))

		par(mfrow=c(2,2))
		for (itr.num in aotIterations) {
			itr<-getIteration(experimentPlan, itr.num)
			dispersalStats<-DispersalStatistics(experimentPlan, itr.num)
			title<-sprintf("%s) L=%0.1f, k=%0.1f", graphIds[i],itr@moveLength, itr@angleOfTurn)
			results<-md.simplePlot(dispersalStats,iteration=itr.num, title , ageLimit=itr@ageLimit, ageSplit=7, ageSplitUpper=399, stats=F, expectedMSD=T, expectedK=itr@angleOfTurn, expectedL=itr@moveLength)
			result.list<-collection.appendToList(result.list, results)
			i<-i+1
		}
	dev.off()
	

	latexFile<-addSubSection(latexFile, "MSD Over Time, L=1", FALSE)
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Summary of MSD over time", "fig:msd-time-L-1")
	

	latexFile<-SimExp3TrB1Report.addResultTable(latexFile, result.list, aotIterations)
	
}
generateJohnsonGraph<-function(latexFile, report) {
	filename<-sprintf("%s/%s-summarey-L-1-A-3.pdf", report@figOutputDir, report@experimentPlan@experimentDirName)
	cat("Writing graph to: ", filename, "\n")
	
	pdf(file=filename, width=10, height=10, bg="white")
		par(mar=c(5, 5, 5, 5))
		itr.num<-9
		itr<-getIteration(experimentPlan, itr.num)
		dispersalStats<-DispersalStatistics(experimentPlan, itr.num)
		#title<-sprintf("(L=%0.1f, k=%0.1f)",itr@moveLength, itr@angleOfTurn)
		title<-""
		results<-md.simplePlot(dispersalStats,iteration=itr.num, title , ageLimit=itr@ageLimit, ageSplit=7, ageSplitUpper=399, stats=F,expectedK=itr@angleOfTurn, expectedL=itr@moveLength)
	dev.off()
	
	latexFile<-addSubSection(latexFile, "MSD Over Time, L=1, A=3 (after Johnson)", FALSE)
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Summary of MSD over time", "fig:msd-time-L-1-A-3")	
	
	latexFile<-addContent(latexFile, "\\vspace{6pt}")
	latexFile<-addContent(latexFile, "\\begin{center}")
	latexFile<-addContent(latexFile, "\\noindent\\textit{The Green line represents the expected displacement $E(R^2)$ from the formular in Skellam (1951) and Jones(1977)}")
	latexFile<-addContent(latexFile, "\\end{center}")
	latexFile<-addContent(latexFile, "\\clearpage")
}

SimExp3TrB1Report.addResultTable<-function(latexFile, result.list, iterationNumbers, title=T) {
	
	graphIds<-c("a", "b", "c","d")
	
	if (title) {
		latexFile<-addSubSubSection(latexFile, "Summary Stats")	
	}
	latexFile<-addContent(latexFile, "\\vspace{6pt}")		
	
	latexFile<-addContent(latexFile, "\\begin{center}")		
	
	latexFile<-beginTabular(latexFile, "llrrrr")

		latexFile<-addTableRow(latexFile, list("\\textbf{Graph}", "\\textbf{Iteration}", "\\textbf{r1}", "\\textbf{r2}", "\\textbf{xAge}", "\\textbf{xMSD}"))
		latexFile<-addContent(latexFile, "\\hline")		
		i<-1

		for (result in result.list) {	
			latexFile<-SimExp3TrB1Report.addResultRow(latexFile, result, graphIds[i], iterationNumbers[i])
			i<-i+1
		}
		
	latexFile<-endTabular(latexFile)
	
	latexFile<-addContent(latexFile, "\\end{center}")		

	return (latexFile)
}



SimExp3TrB1Report.addResultRow<-function(latexFile, result, graphId, iterationNumber) {
	latexFile<-addTableRow(latexFile, list(						
							sprintf("\\textbf{%s)}", graphId),	
							sprintf("%03.0f", iterationNumber),						
							sprintf("%0.3f", result$slope1),
							sprintf("%0.3f", result$slope2),
							sprintf("%0.0f", result$intersectionAge),
							sprintf("%0.0f", result$intersectionMSD),							
	))
	
	return (latexFile)
	
}

SimExp3B1Report.generateLengthGraphs<-function(latexFile, report, lengthIterations, id) {
	latexFile<-addContent(latexFile, "\\clearpage")
	
	filename<-sprintf("%s/%s-summary-A-%03.0f.pdf", report@figOutputDir, report@experimentPlan@experimentDirName, id)
	cat("Writing graph to: ", filename, "\n")
	
	graphIds<-c("a", "b", "c", "d")
	result.list<-list()
	i<-1
	pdf(file=filename, width=10, height=10, bg="white")
	
		par(mfrow=c(2,2))
		for (itr.num in lengthIterations) {
			itr<-getIteration(experimentPlan, itr.num)
			dispersalStats<-DispersalStatistics(experimentPlan, itr.num)
			title<-sprintf("%s) L=%0.1f, k=%0.1f",graphIds[i], itr@moveLength, itr@angleOfTurn)		
			results<-md.simplePlot(dispersalStats,iteration=itr.num, title , ageLimit=itr@ageLimit, ageSplit=7, ageSplitUpper=10, stats=F, expectedK=itr@angleOfTurn, expectedL=itr@moveLength)
			i<-i+1
			A<-itr@angleOfTurn
			result.list<-collection.appendToList(result.list, results)
		}
	
	dev.off()
	
	latexFile<-addSubSection(latexFile, sprintf("MSD Over Time, k=%0.2f", itr@angleOfTurn), FALSE)
	
	latexFile<-addFigure(latexFile, filename, "ht", 7,"Summary of MSD over time", sprintf("fig:msd-time-A-%0.0f", A))
	
	latexFile<-SimExp3TrB1Report.addResultTable(latexFile, result.list, lengthIterations)
	return (latexFile)
}



setMethod("generateReport", signature=c("SimExp3B1Report", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-SimExp3B1Report.generateDescriptiveSection(report)	
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)



#Add a "toString" which is a "show method"
setMethod("show", "SimExp3B1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)


#And a "summary" method
setMethod("summary", "SimExp3B1Report", 
	function(object) {
		StandardReport.showSummary(object)
	}
)
setMethod("plot", "SimExp3B1Report",
	function(x, y, ...) {
		
	}
)


