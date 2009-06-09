# A Class that extends the LatexFileClass to provide the structure for a building a standard report...

#Represents an iteration - parameters and such ...




#Class definition for an IterationReplicate class

setClass("FieldDataReport", representation(
    kaitoke04Cabbages="FieldCabbages",
	levin06IICabbages="FieldCabbages",
	outputDir="character"
), contains="StandardReport")


#Add a constructor:
# ksMAXN - is the sample size to use for KS (kolmogrov-smirnoff) test - if you have too many smaples you get ties which produces a warning.
FieldDataReport<-function() {
	instance<-new("FieldDataReport")
	
	instance@kaitoke04Cabbages<-FieldCabbages("KAITOKE-04")
	instance@levin06IICabbages<-FieldCabbages("LEVIN-II")
	
	rootDir<-sprintf("%s/chapter-4/field-data", ExperimentData.getPublishedRootDir());		
	instance@outputDir<-sprintf("%s",rootDir)
	dir.create(instance@outputDir, showWarnings=FALSE)
	filename<-"field-data-report"
	instance@latexFile<-LatexFile(filename, rootDir)



	instance@latexFile<-addTitle(instance@latexFile, "Field Data Report")

		
	
	
	
	return (instance)
}


FieldDataReport.generateKaitokeSection<-function(instance) {
	starred<-FALSE
	instance@latexFile<-addSubSection(instance@latexFile, "Kaitoke 2004", starred=starred)

	instance@latexFile<-addSubSubSection(instance@latexFile, "Field Layout")

	name<-"Kaitoke-2004-Field-Layout"
	filename<-sprintf("%s/kaitoke-04/output/%s.pdf", instance@outputDir, name)
	cat("Writing field layout figure to: ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
		plot(instance@kaitoke04Cabbages, TRUE)		
	dev.off()
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Kaitoke 2004 Field Layout. Rectangles of each of the scales is shown. The four repeated 'blocks' are indicated by K1 - K4", sprintf("fig:%s", name))
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")

	name<-"Kaitoke-2004-Field-Layout-Block-BreakDown-K1"
	filename<-sprintf("%s/kaitoke-04/output/%s.pdf", instance@outputDir, name)
	cat("Writing field layout breakdown figure to: ", filename, "\n")
	pdf(file=filename, width=14, height=14, bg="white")
		plot(instance@kaitoke04Cabbages,"BLOCK1", title="Block K1")
	dev.off()
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Kaitoke 2004 Field Layout Block Breakdown (Block 1).", sprintf("fig:%s", name))
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")
	
	name<-"Kaitoke-2004-Field-Layout-Block-BreakDown-K234"
	filename<-sprintf("%s/kaitoke-04/output/%s.pdf", instance@outputDir, name)
	cat("Writing field layout breakdown figure to: ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
		par(mfrow=c(2, 2))
		plot(instance@kaitoke04Cabbages,"BLOCK2", title="a) Block K2")
		plot(instance@kaitoke04Cabbages,"BLOCK3", title="b) Block K3")
		plot(instance@kaitoke04Cabbages,"BLOCK4", title="c) Block K4")
	dev.off()
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Kaitoke 2004 Field Layout Block Breakdown.", sprintf("fig:%s", name))
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")

	
	
	instance@latexFile<-addSubSubSection(instance@latexFile, "Egg Distribution Graphs")

	
	dist<-instance@kaitoke04Cabbages@eggDistribution
		
	filename<-sprintf("%s/kaitoke-04/output/Kaitoke-2004-aggregated-egg-distribution.pdf", instance@outputDir)
	cat("Writing aggregated egg distribution figure to: ", filename, "\n")
	pdf(file=filename, width=8, height=8, bg="white")
		plot(dist, plotType, plotType="AGGREGATED")
	dev.off()		
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 5,"Kaitoke 2004 Aggregated Egg Distribution", "fig:field-kaitoke04-aggregated-egg-dist")
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
		
	instance@latexFile<-addBreakdownRegressionPlots(instance@latexFile, instance, dist, "Kaitoke 2004 Log-Log Regression analysis", "kaitoke04-breakdown-regression")
		
#	instance@latexFile<-addKaitokeAggregatedRegressionPlots(instance, dist)
	
	filename<-sprintf("%s/kaitoke-04/output/Kaitoke-2004-detailed-egg-distribution.pdf", instance@outputDir)
	cat("Writing detailed egg distribution figure to: ", filename, "\n")
	pdf(file=filename, width=8, height=9.5, bg="white")
	par(mfrow=c(3, 1))
	plot(dist, plotType="SCALE_CODE", sortScale="1M")
	plot(dist, plotType="SCALE_CODE", sortScale="6M")
	plot(dist, plotType="SCALE_CODE", sortScale="48M")
	dev.off()
	
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Kaitoke 2004 Detailed Egg Distribution", "fig:field-kaitoke04-detailed-egg-dist")
	

	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
	return (instance)
}

addKaitokeAggregatedRegressionPlots<-function(instance, dist) {
	name<-"Kaitoke-2004-II-aggregated-egg-distribution-loglog.pdf"
	filename<-sprintf("%s/levin-06-II/output/%s", instance@outputDir, name)
	cat("Writing KAITOKE aggregated LOGLOG egg distribution figure to: ", filename, "\n")	
	pdf(file=filename, width=8, height=8, bg="white")
		results.lm<-plot(dist, plotType, plotType="AGGREGATED-LOGLOG")
	dev.off()		
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 5,"Kaitoke 2004 Log-Log Plot of Aggregated Egg Distribution with regression lines", sprintf("fig:%s", name))


	instance@latexFile<-FieldDataReport.addLmStats(instance@latexFile, results.lm, "Kaitoke 2004 Log-Log Regression Analysis")
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
	
	

	name<-"Kaitoke-2004-II-aggregated-egg-distribution-sqsq.pdf"
	filename<-sprintf("%s/levin-06-II/output/%s", instance@outputDir, name)
	cat("Writing KAITOKE aggregated SQSQ egg distribution figure to: ", filename, "\n")	
	pdf(file=filename, width=8, height=8, bg="white")
		results.lm<-plot(dist, plotType, plotType="AGGREGATED-SQSQ")
	dev.off()		
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 5,"Kaitoke 2004 Sq-Sq Plot of Aggregated Egg Distribution with regression lines", sprintf("fig:%s", name))

	instance@latexFile<-FieldDataReport.addLmStats(instance@latexFile, results.lm, "Kaitoke 2004 Sq-Sq Regression Analysis")
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
	
}

FieldDataReport.addLmStats<-function(latexFile, results.lm, title) {
	latexFile<-addSubSubSection(latexFile, title)

	latexFile<-addContent(latexFile, "\\vspace{6pt}")	
	latexFile<-addContent(latexFile, "\\renewcommand{\\arraystretch}{1.5}")
	latexFile<-addContent(latexFile, "\\setlength{\\tabcolsep}{5mm}")
	
	
	latexFile<-beginTabular(latexFile, "lrrrrrrr")		
	latexFile<-addTableRow(latexFile, list(
			"\\textbf{Scale}", 
			"\\textbf{d.f.}", 
			"\\textbf{slope}", 
			"$\\mathbf{R^2}$", 
			"$\\mathbf{R^2}$ \\textbf{(Adj.)}", 
			"\\textbf{Std. Error}", 
			"\\textbf{t-value}", 
			"\\textbf{p-value}"
	))
	latexFile<-addContent(latexFile, "\\hline")	
	
	latexFile<-addLmRow(latexFile, "1x1m", results.lm$lm.1m)
	latexFile<-addLmRow(latexFile, "6x6m", results.lm$lm.6m)
	if (!is.null(results.lm$lm.48m)) {
		latexFile<-addLmRow(latexFile, "48x48m", results.lm$lm.48m)		
	}
	
	latexFile<-addContent(latexFile, "\\hline")	
	
	
	latexFile<-endTabular(latexFile)
	
	return (latexFile)
}

addLmRow<-function(latexFile,scale, lm) {
	slope<-lm$coef[[2]]
	summary.lm<-summary(lm)
	stderr.slope<-summary.lm$coefficients[[2,2]]
	r.sq<-summary.lm$r.squared
	r.sq.adj<-summary.lm$adj.r.squared

	t.value<-summary.lm$coefficients[[2,3]]	
	p.value<-summary.lm$coefficients[[2,4]]	
	d.f<-lm$df.residual
	
	latexFile<-addTableRow(latexFile, list(
			sprintf("\\textbf{%s}", scale), 
			sprintf("%0.3f", d.f),  
			sprintf("%0.3f", slope),  
			sprintf("%0.3f", r.sq), 
			sprintf("%0.3f", r.sq.adj), 
			sprintf("%0.3f", stderr.slope), 
			sprintf("%0.3f", t.value), 
			StandardReport.printPValue(p.value)
	))
	
	return (latexFile)
}
FieldDataReport.generateLevinSection<-function(instance) {
	starred = FALSE
	instance@latexFile<-addSubSection(instance@latexFile, "Levin 2006 - Experiment II", starred=starred)
	
	instance@latexFile<-addLevinFieldLayout(instance)
	
	dist<-instance@levin06IICabbages@eggDistribution
	
	instance@latexFile<-addAggregatedLevinEggDistribution(instance, dist)
	
	instance@latexFile<-addDetailedLevinEggDistribution(instance, dist)

	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	

	instance@latexFile<-FieldDataReport.addLevinFieldBreakdown(instance@latexFile, instance, "E")
	instance@latexFile<-FieldDataReport.addLevinFieldBreakdown(instance@latexFile, instance, "F")
	instance@latexFile<-FieldDataReport.addLevinFieldBreakdown(instance@latexFile, instance, "G")
	instance@latexFile<-FieldDataReport.addLevinFieldBreakdown(instance@latexFile, instance, "H")
	
	return (instance)
}

FieldDataReport.addLevinFieldBreakdown<-function(latexFile, report, field) {
	latexFile<-addSubSection(latexFile, sprintf("Field %s Layout", field), starred=F)
	
	fieldCabbages<-FieldCabbages.LEVINII
	
	name<-sprintf("Levin-06-II-Field-%s", field)
	filename<-sprintf("%s/levin-06-II/output/%s.pdf", report@outputDir, name)
	cat("Writing field layout breakdown figure to: ", filename, "\n")
	pdf(file=filename, width=8, height=8, bg="white")
		FieldCabbages.plotCabbages(field, fieldCabbages@eggDistribution@cabbages.df, margin=2.75,axis.at=c(0, 18, 36), xLim=c(0, 36), yLim=c(0,36), graphId="a",cex.labels=1, cex.cabbage=0.7)	
	dev.off()
	latexFile<-addFigure(latexFile, filename, "h", 7,"Levin 06 II Field Layout Block Breakdown.", sprintf("fig:%s", name))
	latexFile<-addContent(latexFile, "\\clearpage")	
	
	return (latexFile)
}

addLevinFieldLayout<-function(instance) {
	instance@latexFile<-addSubSubSection(instance@latexFile, "Field Layout")

	fieldLayoutFileName<-sprintf("%s/levin-06-II/output/Levin-06-II-Field-Layout.pdf", instance@outputDir)
	cat("Writing field LEVIN-II layout figure to: ", fieldLayoutFileName, "\n")
	pdf(file=fieldLayoutFileName, width=10, height=10, bg="white")
		plot(instance@levin06IICabbages, FALSE)		
	dev.off()
	instance@latexFile<-addFigure(instance@latexFile, fieldLayoutFileName, "h", 7,"Levin 06 II Field Layout", "fig:field-levin-06-II-field-layout")	
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")
	
	name<-"Levin-06-II-Field-Layout-Block-BreakDown"
	filename<-sprintf("%s/levin-06-II/output/%s.pdf", instance@outputDir, name)
	cat("Writing field layout breakdown figure to: ", filename, "\n")
	pdf(file=filename, width=10, height=10, bg="white")
		par(mfrow=c(2,2))
		
		plot(instance@levin06IICabbages,"BLOCK1", title="a) Block L1")
		plot(instance@levin06IICabbages,"BLOCK2", title="b) Block L2")
		plot(instance@levin06IICabbages,"BLOCK3", title="c) Block L3")
		plot(instance@levin06IICabbages,"BLOCK4", title="d) Block L4")
	dev.off()
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Levin 06 II Field Layout Block Breakdown.", sprintf("fig:%s", name))
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")
	
	return (instance@latexFile)
}

addAggregatedLevinEggDistribution<-function(instance, dist) {
	instance@latexFile<-addSubSubSection(instance@latexFile, "Egg Distribution Graphs")

	filename<-sprintf("%s/levin-06-II/output/Levin-2006-II-aggregated-egg-distribution.pdf", instance@outputDir)
	cat("Writing LEVIN aggregated egg distribution figure to: ", filename, "\n")	
	pdf(file=filename, width=8, height=8, bg="white")
		plot(dist, plotType, plotType="AGGREGATED")
	dev.off()		
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 5,"Levin 2006 Aggregated Egg Distribution", "fig:field-levin-06-II-aggregated-egg-dist")

	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	

	#instance@latextFile<-addLevinAggregatedRegressionPlots(instance, dist)

	instance@latexFile<-addBreakdownRegressionPlots(instance@latexFile, instance, dist, "Levin 2006 Log-Log Regression analysis", "Levin-2006-II-breakdown-regression")
	
	name<-"Levin-2006-II-field-breakdown-egg-distribution"
	filename<-sprintf("%s/levin-06-II/output/%s.pdf", instance@outputDir, name)	
	cat("Writing LEVIN Field Breakdown egg distribution figure to: ", filename, "\n")
	par(mfrow=c(1, 1))
	dist.breakdown.list<-EggDistribution.breakdownByFactor(dist, "Field", "Field.EggCount")
	pdf(file=filename, width=12, height=12, bg="white")
		par(mfrow=c(2, 2))
		for (dist.break in dist.breakdown.list) {
			plot(dist.break, plotType="AGGREGATED")
		}
	dev.off()	
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Levin 2006 Field breakdown of  Egg Distribution", sprintf("fig:%s", name))




	return (instance@latexFile)
}

addBreakdownRegressionPlots<-function(latexFile, instance, dist, caption, name) {
	latexFile<-addSubSection(latexFile, caption, F)
	
	name<-name
	filename<-sprintf("%s/levin-06-II/output/%s.pdf", instance@outputDir, name)	
	cat("Writing Regression Breakdown figure to: ", filename, "\n")
	pdf(file=filename, width=16, height=6, bg="white")		
		results.lm<-EggDistribution.plotLogLogBreakdownMultiplePlot(dist)
	dev.off()	
	latexFile<-addFigure(latexFile, filename, "h", 8,caption, sprintf("fig:%s", name))
	
	latexFile<-FieldDataReport.addLmStats(latexFile, results.lm, "Regression Statistics")
	
	latexFile<-addContent(latexFile, "\\clearpage")	
	return (latexFile)
}

addLevinAggregatedRegressionPlots<-function(instance, dist) {
	name<-"Levin-2006-II-aggregated-egg-distribution-loglog.pdf"
	filename<-sprintf("%s/levin-06-II/output/%s", instance@outputDir, name)
	cat("Writing LEVIN aggregated LOGLOG egg distribution figure to: ", filename, "\n")	
	pdf(file=filename, width=8, height=8, bg="white")
		results.lm<-plot(dist, plotType, plotType="AGGREGATED-LOGLOG")
	dev.off()		
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 5,"Levin 2006 Log-Log Plot of Aggregated Egg Distribution with regression lines", sprintf("fig:%s", name))

	instance@latexFile<-FieldDataReport.addLmStats(instance@latexFile, results.lm, "Levin 2006 Log-Log Regression Analysis")
	
	results.lm<-EggDistribution.calculateLogLogRegressionAccurate(dist, dist@eggCountFieldName)
	instance@latexFile<-FieldDataReport.addLmStats(instance@latexFile, results.lm, "Levin 2006 Log-Log Regression Analysis")
	
	
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	



	name<-"Levin-2006-II-aggregated-egg-distribution-sqsq.pdf"
	filename<-sprintf("%s/levin-06-II/output/%s", instance@outputDir, name)
	cat("Writing LEVIN aggregated SQSQ egg distribution figure to: ", filename, "\n")	
	pdf(file=filename, width=8, height=8, bg="white")
		results.lm<-plot(dist, plotType, plotType="AGGREGATED-SQSQ")
	dev.off()		
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 5,"Levin 2006 Sq-Sq Plot of Aggregated Egg Distribution with regression lines", sprintf("fig:%s", name))

	instance@latexFile<-FieldDataReport.addLmStats(instance@latexFile, results.lm, "Levin 2006 Sq-Sq Regression Analysis")
	instance@latexFile<-addContent(instance@latexFile, "\\clearpage")	
	
	return (instance@latexFile)
}

addDetailedLevinEggDistribution<-function(instance, dist) {
	filename<-sprintf("%s/levin-06-II/output/Levin-2006-II-detailed-egg-distribution.pdf", instance@outputDir)
	cat("Writing LEVIN detailed egg distribution figure to: ", filename, "\n")
	pdf(file=filename, width=8, height=9.5, bg="white")
	par(mfrow=c(3, 1))
		plot(dist, plotType="SCALE_CODE", sortScale="1M")
		plot(dist, plotType="SCALE_CODE", sortScale="6M")
	dev.off()
	
	instance@latexFile<-addFigure(instance@latexFile, filename, "h", 7,"Levin 2006 Detailed Egg Distribution", "fig:field-levin-06-II-detailed-egg-dist")
	return (instance@latexFile)
}

setMethod("generateReport", signature=c("FieldDataReport", "logical", "logical"),
	function(report, showPreview, clean) {		
		report<-FieldDataReport.generateKaitokeSection(report)



		report<-FieldDataReport.generateLevinSection(report)
		
		latexBuild(report@latexFile, showPreview, clean)		
		return (report)
	}
)




#Add a "toString" which is a "show method"
setMethod("show", "FieldDataReport", 
	function(object) {
		show(object@latexFile)

	}
)


#And a "summary" method
setMethod("summary", "FieldDataReport", 
	function(object) {
	}
)
setMethod("plot", "FieldDataReport",
	function(x, y, ...) {
	}
)


