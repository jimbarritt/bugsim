source("~/Work/code/bugsim/src/R/archive/Exp1a Library.r", echo=FALSE)
cat("Exp 1c R Library Version 1.0\n")


loadCabbageFieldLayout<-function(fieldLayoutDirectory, fieldLayoutFilename) {
	inputFilename<-sprintf("%s/%s.csv", fieldLayoutDirectory, fieldLayoutFilename)
	cat("Reading input from : ", inputFilename, " ...\n")
	input.cabbage.layout.df<-read.csv(inputFilename)	
	
	input.cabbage.layout.df$X1m_dens<-factor(input.cabbage.layout.df$X1m_dens)
	input.cabbage.layout.df$X6m_dens<-factor(input.cabbage.layout.df$X6m_dens)
	
	return (input.cabbage.layout.df)
}

#df.list<-stats.list; title<-"Many";xaxisLabel<-"Number Of Plants"; yaxisLabel<-"Eggs Per Plant";legendFormatString<-"Density=%d";legendFactor<-factor(c(1, 6));plotErr<-TRUE;continuousX<-FALSE;lineColors<-c("red", "blue")
#	legendFactor<-factor(c("1x1m", "6x6m"))
#	par(mar=c(5, 5, 5, 2))
#	plotManyResults(stats.list, "Eggs/Plant at different scales", "Number Of Plants", "Eggs Per Plant", "%s", legendFactor, plotErr=FALSE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(2, 1),legendPosition="topright", yLim=c(0, 25))	
#iterationId<-1; field.df<-layout.df
outputComparisonBetweenSimAndField<-function(iterationId, field.df, baseFilename, experimentNumber, trialId) {
	stats.1m.df<-createUngroupedSummaryStatistics(input.df=field.df, xFactorName="X1m_dens", yFactorName="eggs_24.02.2006")	
	stats.6m.df<-createUngroupedSummaryStatistics(input.df=field.df, xFactorName="X6m_dens", yFactorName="eggs_24.02.2006")	
	stats.list<-list(stats.1m.df, stats.6m.df)

	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)

	replicantId<-1
	iterationDir<-sprintf("%s/Iteration-%03d-%03d", directory, iterationId, replicantId)
	cabbagesFilename<-sprintf("%s/%03d-%03d-%03d-cabbages.csv", iterationDir, experimentNumber, iterationId, replicantId)
	cat("Reading iteration ", iterationId, ", replicant " ,  replicantId, " : " ,cabbagesFilename, "...\n")
	cabbages.df<-read.csv(cabbagesFilename)

	merged.df<-merge(layout.df, cabbages.df, by.x="Plant.ID", by.y="Id")
	merged.df$Simulation.Egg.Count<-merged.df$Egg.Count
	merged.df$Egg.Count<-NULL

	simulation.stats.1m.df<-createUngroupedSummaryStatistics(input.df=merged.df, xFactorName="X1m_dens", yFactorName="Simulation.Egg.Count")	
	simulation.stats.6m.df<-createUngroupedSummaryStatistics(input.df=merged.df, xFactorName="X6m_dens", yFactorName="Simulation.Egg.Count")	

	simulation.stats.list<-list(simulation.stats.1m.df, simulation.stats.6m.df)
	legendFactor<-factor(c("1x1m", "6x6m"))
	par(mar=c(5, 5, 5, 2))

	outputDir<-createDirectoryName(baseFilename, experimentNumber, trialId)
	title<-sprintf("Field vs Simulated Field E Iteration %d", iterationId)
	outputFilename<-sprintf("%s/%s.pdf", outputDir, title)


	quartz(width=10, height=5)
	par(mfrow=c(1,2))
	plotManyResults(stats.list, "Field Data", "Number Of Plants", "Eggs Per Plant", "%s", legendFactor, plotErr=FALSE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(2, 1),legendPosition="topright", yLim=c(0, 25))	
	plotManyResults(simulation.stats.list, "Simulated", "Number Of Plants", "Eggs Per Plant", "%s", legendFactor, plotErr=FALSE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(2, 1),legendPosition="topright", yLim=c(0, 25))	

	cat("Writing to ", outputFilename, " ...\n")
	pdf(file=outputFilename, width=12, height=8, bg="white")
	par(mfrow=c(1,2))
	plotManyResults(stats.list, "Field Data", "Number Of Plants", "Eggs Per Plant", "%s", legendFactor, plotErr=FALSE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(2, 1),legendPosition="topright", yLim=c(0, 25))	
	plotManyResults(simulation.stats.list, "Simulated", "Number Of Plants", "Eggs Per Plant", "%s", legendFactor, plotErr=FALSE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(2, 1),legendPosition="topright", yLim=c(0, 25))	
	dev.off()
}






outputCabbageFieldLayout<-function(input.cabbage.layout.df, fieldLayoutDirectory, fieldLayoutFilename) {
	plotCabbageFieldLayout(input.cabbage.layout.df, fieldLayoutFilename)
	
	outputFilename<-sprintf("%s/Plot of %s.pdf", fieldLayoutDirectory, fieldLayoutFilename)
	pdf(file=outputFilename, width = 8, height=8, bg="white")

	cat("Writing output file to : ", outputFilename, " ... \n")
	plotCabbageFieldLayout(input.cabbage.layout.df, fieldLayoutFilename)
	dev.off()
	
	
}

#input.cabbage.layout.df<-layout.df
plotCabbageFieldLayout<-function(input.cabbage.layout.df, title) {
	attach(input.cabbage.layout.df)
	par(mar=c(5, 6 , 5, 3))
	plot(y~x, xlim=c(0,36), ylim=c(0,36), xaxt="n", yaxt="n", main=title, xlab="x (m)", ylab="y (m)", pch=NA, cex.lab=1.5)
	points(y~x, pch=19, col="#339933", cex=1.5)
	axis(side=1, at=seq(from=0, to=36, by=6))
	axis(side=2, at=seq(from=0, to=36, by=6),las=2)
}

#iterationFactor<-inp.df$iteration
#summary.df<-inp.df
readTrialBResults<-function(layout.df, summary.df, iterationFactor, numReplicants, baseFilename, experimentNumber, trialId) {
	field.df<-layout.df
	
	experimentDir<-createDirectoryName(baseFilename, experimentNumber, trialId)
	
	all.stats.list<-list()
	iterationId<-1
	while (iterationId<= max(as.numeric(levels(iterationFactor)))) {
		iteration.stats.list<-readExp1cIterationStats(field.df, iterationId, numReplicants, experimentDir, experimentNumber)
		all.stats.list<-appendToList(all.stats.list, iteration.stats.list)
		iterationId<-iterationId+1
	}
	return (all.stats.list)
}

outputTrialBResults<-function(all.stats.list, summary.df, baseFilename, experimentNumber, trialId, legendFactor=factor(c("1x1m", "6x6m")), only6m=FALSE) {


	outputDir<-createDirectoryName(baseFilename, experimentNumber, trialId)
	title<-sprintf("Simulated Field E Varying L and A")


	quartz(width=12, height=9)
	par(mfrow=c(3,4))
	
	plotAllTrialBResults(all.stats.list, summary.df, only6m, legendFactor)


	outputFilename<-sprintf("%s/output/%s.pdf", outputDir, title)
	cat("Writing to ", outputFilename, " ...\n")
	pdf(file=outputFilename, width=12, height=9, bg="white")
	par(mfrow=c(3,4))
	plotAllTrialBResults(all.stats.list, summary.df, only6m, legendFactor)
	
	dev.off()
	
}



#i
#length(all.stats.list)
#i.A<-"test"
#i.L<-"test"
plotAllTrialBResults<-function(all.stats.list, summary.df, only6m=FALSE, legendFactor) {

	par(mar=c(4, 4, 2, 2))
	
	i.stats<-1
	i.Row<-1
	for (i.A in levels(summary.df$A)) {
		i.Col<-1
		for (i.L in levels(summary.df$L)){
			
			i.all.stats.list<-all.stats.list[[i.stats]]
			
				plotTitle<-NULL
			
			#item 1 is the complete data set from which the stats were generated
			if (only6m==FALSE) {
				i.stats.list<-list(i.all.stats.list[[2]], i.all.stats.list[[3]])
				linetypes<-c(2, 1)
			} else {
				i.stats.list<-list(i.all.stats.list[[3]])
				linetypes<-c(1)
			}
			plotManyResults(i.stats.list, plotTitle, NULL, NULL, "%s", legendFactor, plotErr=TRUE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=linetypes,legendPosition="topright", yLim=c(0, 25), cex.axis=1.4)			
			i.stats<-i.stats+1
			i.Col<-i.Col+1
		}
		i.Row<-i.Row+1
	}	
	
}

#test.df<-data.frame("x"=c(1,2,3))
#test.2.df<-data.frame("x"=c(4,5,6))
#test.3.df<-merge(test.df, test.2.df, all=TRUE)

#iterationId<-1
readExp1cIterationStats<-function(layout.df, iterationId, numReplicants, experimentDir, experimentNumber, only6m=FALSE) {	
	completeData.df<-readExp1cCabbages(layout.df, iterationId, numReplicants, experimentDir, experimentNumber)
	simulation.stats.1m.df<-createUngroupedSummaryStatistics(input.df=completeData.df, xFactorName="X1m_dens", yFactorName="Simulation.Egg.Count")	
	simulation.stats.6m.df<-createUngroupedSummaryStatistics(input.df=completeData.df, xFactorName="X6m_dens", yFactorName="Simulation.Egg.Count")	

	if (only6m == FALSE) {
		simulation.stats.list<-list(completeData.df,simulation.stats.1m.df, simulation.stats.6m.df)
	} else {
		simulation.stats.list<-list(completeData.df, simulation.stats.6m.df)
	}

	
	return (simulation.stats.list)
}

readExp1cCabbages<-function(layout.df, iterationId, numReplicants, experimentDir, experimentNumber) {
	i.replicant<-1
	completeData.df<-NULL
	while (i.replicant <= numReplicants) {
		cat("Reading replicant ", i.replicant, "\n")
		replicant.df<-readExp1cIterationCabbageFile(experimentDir, iterationId, i.replicant)
		replicant.df$replicant<-i.replicant
		if (is.null(completeData.df)) {
			completeData.df<-replicant.df
		} else {
			completeData.df<-merge(completeData.df, replicant.df, all=TRUE)
		}


		i.replicant<-i.replicant+1
	}
	completeData.df$replicant<-factor(completeData.df$replicant)
#	count(completeData.df, completeData.df$replicant)
	return (completeData.df)
}

readExp1cIterationCabbageFile<-function(experimentDir, iterationId, replicantId) {
	iterationDir<-sprintf("%s/Iteration-%03d-%03d", experimentDir, iterationId, replicantId)
	cabbagesFilename<-sprintf("%s/%03d-%03d-%03d-cabbages.csv", iterationDir, experimentNumber, iterationId, replicantId)
	cat("Reading iteration ", iterationId, ", replicant " ,  replicantId, " : " ,cabbagesFilename, "...\n")
	cabbages.df<-read.csv(cabbagesFilename)

	merged.df<-merge(layout.df, cabbages.df, by.x="Plant.ID", by.y="Id")
	merged.df$Simulation.Egg.Count<-merged.df$Egg.Count
	merged.df$Egg.Count<-NULL
	return (merged.df)
	
}

outputFieldResults<-function(fieldResults.df, outputDir, title) {
	stats.list<-readFieldResultStats(fieldResults.df)

	quartz(width=8, height=8)
	
	legendFactor<-factor(c("1x1m", "6x6m"))
	par(mar=c(5, 5, 5, 2))
	plotManyResults(stats.list, title, "Number Of Plants", "Eggs Per Plant", "%s", legendFactor, plotErr=TRUE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(2, 1),legendPosition="topright", errCol="#333333")			
	

	outputFilename<-sprintf("%s/%s.pdf", outputDir, title)
	cat("Writing to ", outputFilename, " ...\n")
	pdf(file=outputFilename, width=8, height=8, bg="white")
	par(mar=c(5, 5, 5, 2))
	plotManyResults(stats.list, title, "Number Of Plants", "Eggs Per Plant", "%s", legendFactor, plotErr=TRUE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(2, 1),legendPosition="topright", errCol="#333333")			
	
	dev.off()
	return (stats.list)
}

readFieldResultStats<-function(fieldResults.df) {
	field.stats.1m.df<-createUngroupedSummaryStatistics(input.df=fieldResults.df, xFactorName="X1m_dens", yFactorName="eggs_24.02.2006")	
	field.stats.6m.df<-createUngroupedSummaryStatistics(input.df=fieldResults.df, xFactorName="X6m_dens", yFactorName="eggs_24.02.2006")		
	return (list(field.stats.1m.df, field.stats.6m.df))
}

data.frame.ChisqResults<-function(chisq.results) {
	results.df<-data.frame("L"=rep(0, length(chisq.results)))
	results.df$A<-0
	results.df$p.value.1m<-0
	results.df$p.value.6m<-0
	
	i.result<-1
	while (i.result<=length(chisq.results)) {
		result.list<-chisq.results[[i.result]]
		results.df$L[i.result]<-result.list[[1]]
		results.df$A[i.result]<-result.list[[2]]
		chsq.1m<-result.list[[3]]
		chsq.6m<-result.list[[4]]
		results.df$p.value.1m[i.result]<-chsq.1m$p.value
		results.df$p.value.6m[i.result]<-chsq.6m$p.value
		i.result<-i.result+1
	}
	
	return (results.df)
}


#summary.df<-inp.df
calculateChiSquaredGoodnessOfFits<-function(layout.df, fieldData.df, summary.df, iterationFactor, numReplicants, experimentDir, experimentNumber) {	
	iterationId<-1
	chiSquaredresults<-list()
	while (iterationId<= max(as.numeric(levels(iterationFactor)))) {
		cat("Calculating chi squared comparison with iteration ", iterationId, "\n")
		cabbages.df<-readExp1cCabbages(layout.df, iterationId, numReplicants, experimentDir, experimentNumber)
		
		iteration.summary.df<-subset(summary.df, summary.df$iteration==iterationId)
		L<-as.numeric(as.character(iteration.summary.df$L[1]))
		A<-as.numeric(as.character(iteration.summary.df$A[1]))
		
		chiSquaredresult<-calculateChiSquaredGoodnessOfFit(fieldData.df, cabbages.df, L, A)
		chiSquaredresults<-appendToList(chiSquaredresults, chiSquaredresult)
		
		iterationId<-iterationId+1		
	}
	
	return (chiSquaredresults)
}

#fieldData.df<-fieldResults.df
calculateChiSquaredGoodnessOfFit<-function(fieldData.df, cabbages.df, L, A) {
	
	chi.1m.mx<-matrix(0,nrow=2, ncol=3)
	
	sumEggs.field<-sum(fieldData.df$eggs_24.02.2006)
	sumEggs.simulation<-sum(cabbages.df$Simulation.Egg.Count)
	

	#Observed
	chi.1m.mx[1,1]<-sum(fieldData.df$eggs_24.02.2006[fieldData.df$X1m_dens=="1"])
	chi.1m.mx[1,2]<-sum(fieldData.df$eggs_24.02.2006[fieldData.df$X1m_dens=="4"])
	chi.1m.mx[1,3]<-sum(fieldData.df$eggs_24.02.2006[fieldData.df$X1m_dens=="16"]) 
	
	#Expected
	chi.1m.mx[2,1]<-sum(cabbages.df$Simulation.Egg.Count [cabbages.df$X1m_dens=="1"]/sumEggs.simulation) * sumEggs.field
	chi.1m.mx[2,2]<-sum(cabbages.df$Simulation.Egg.Count [cabbages.df$X1m_dens=="4"]/sumEggs.simulation) * sumEggs.field
	chi.1m.mx[2,3]<-sum(cabbages.df$Simulation.Egg.Count [cabbages.df$X1m_dens=="16"]/sumEggs.simulation) * sumEggs.field

	chisq.1m.result<-chisq.test(chi.1m.mx)
		
	chi.6m.mx<-matrix(0,nrow=2, ncol=4)
	
	chi.6m.mx[1,1]<-sum(fieldData.df$eggs_24.02.2006 [fieldData.df$X6m_dens=="1"])
	chi.6m.mx[1,2]<-sum(fieldData.df$eggs_24.02.2006 [fieldData.df$X6m_dens=="4"])
	chi.6m.mx[1,3]<-sum(fieldData.df$eggs_24.02.2006 [fieldData.df$X6m_dens=="16"])
	chi.6m.mx[1,4]<-sum(fieldData.df$eggs_24.02.2006 [fieldData.df$X6m_dens=="40"]) 
	
	#These calculate the actual number of eggs in the simulation by first creating 
	#a proportional value for each one and then multiplying by the number of eggs in the field...
	#this way it doesnt matter how many eggs there are in the sim and the field - it just goes on the 
	#relative proportions of each density..
	chi.6m.mx[2,1]<-sum(cabbages.df$Simulation.Egg.Count [cabbages.df$X6m_dens=="1"]/sumEggs.simulation) * sumEggs.field
	chi.6m.mx[2,2]<-sum(cabbages.df$Simulation.Egg.Count [cabbages.df$X6m_dens=="4"]/sumEggs.simulation) *  sumEggs.field
	chi.6m.mx[2,3]<-sum(cabbages.df$Simulation.Egg.Count [cabbages.df$X6m_dens=="16"]/sumEggs.simulation) *  sumEggs.field
	chi.6m.mx[2,4]<-sum(cabbages.df$Simulation.Egg.Count [cabbages.df$X6m_dens=="40"]/sumEggs.simulation) *  sumEggs.field
				
	chisq.6m.result<-chisq.test(chi.6m.mx)	

	result.list<-list(L, A, chisq.1m.result,chisq.6m.result)
	return (result.list)
}

outputRegressionComparisons<-function(field.stats.list, all.stats.list, summary.df, basefilename, experimentNumber, trialId) {
	quartz(width=14, height=10)
	title<-"Field vs Simulation Regression tests"
	t.stats<-layoutRegressionComparisons(field.stats.list, all.stats.list, summary.df, title)


	directory<-createDirectoryName(basefilename, experimentNumber, trialId)
	outputFilename<-sprintf("%s/Log Regressions 1x1m 6x6m Field vs Simulation.pdf", directory)
	cat("Writing graph to file : ", outputFilename, "\n")
	pdf(file=outputFilename, width=14, height=10, bg="white")
	layoutRegressionComparisons(field.stats.list, all.stats.list, summary.df, title)
	dev.off()
	
	return (t.stats)
}
#title<-"test comparison"
layoutRegressionComparisons<-function(field.stats.list, all.stats.list, summary.df, title) {
	
	layout.mx<-matrix(c(1,1,1,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,12,13, 14, 15, 16, 17), 4, 4, byrow = TRUE)
	layout(layout.mx, heights=c(16, 28, 28, 28))

	plotRegressionLegends(title)
	
	t.stats<-plotAllRegressionComparisons(field.stats.list, all.results.list, summary.df)
	return (t.stats)
}

plotRegressionLegends<-function(title, only6m=FALSE) {
	par(mar=c(1, 1, 1, 1))
	plot.new()
	text(x=.5, y=.9, title, cex=1.5,font=2)

	legend.cex<-1.2
	legendStrings.6m<-c("6x6m ", "field", "sim", "regression ", "  field", "sim")
	colors.6m<-rep("#000099", 6)
	pchs.6m<-c(NA, 24, 19, NA, NA, NA)
	ltys.6m<-c(NA, NA, NA,NA,  1, 2)

	legendStrings.1m<-c("1x1m ", "field", "sim", "regression ", "  field", "sim")
	colors.1m<-rep("#666699", 6)
	pchs.1m<-c(NA, 24, 19, NA, NA, NA)
	ltys.1m<-c(NA, NA, NA,NA,  1, 2)

	legend(x=.25, y=.7, ncol=6,inset=c(.05, 0.05), pch=pchs.6m, col=colors.6m, legend=legendStrings.6m,lty=ltys.6m,  cex=legend.cex)
	if (only6m==FALSE) {
		legend(x=.25, y=.3, ncol=6,inset=c(.05, 0.05), pch=pchs.1m, col=colors.1m, legend=legendStrings.1m,lty=ltys.1m,  cex=legend.cex)	
	}
}

plotAllRegressionComparisons<-function(field.stats.list, all.stats.list, summary.df) {
	par(mar=c(5, 5, 5, 2))
	
	all.t.stats<-list()
	i.stats<-1
	for (i.A in levels(summary.df$A)) {

		for (i.L in levels(summary.df$L)){

			
			i.all.stats.list<-all.stats.list[[i.stats]]
			plotTitle<-sprintf("L=%s, A=%s", i.L, i.A)
			
			#item 1 is the complete data set from which the stats were generated
			simulated.stats.1m.df<-i.all.stats.list[[2]]
			simulated.stats.6m.df<-i.all.stats.list[[3]]
			
			t.stats<-plotLogRegressionFieldVsSimulation(field.stats.list, simulated.stats.1m.df, simulated.stats.6m.df, plotTitle)

			all.t.stats<-appendToList(all.t.stats, t.stats)
			i.stats<-i.stats+1
		}
	}	
	
	return (all.t.stats)
	
}



plotLogRegressionFieldVsSimulation<-function(field.stats.list, simulated.stats.1m.df, simulated.stats.6m.df, plotTitle, only6m=FALSE, onlyField=FALSE, idf, ...) {
	field.stats.1m.df<-field.stats.list[[1]]
	field.stats.6m.df<-field.stats.list[[2]]

	x.1m<-as.numeric(levels(simulated.stats.1m.df$X))
	x.6m<-as.numeric(levels(simulated.stats.6m.df$X))

	y.sim.1m<-simulated.stats.1m.df$MEAN
	y.fld.1m<-field.stats.1m.df$MEAN

	y.sim.6m<-simulated.stats.6m.df$MEAN
	y.fld.6m<-field.stats.6m.df$MEAN

	lm.sim.1m<-lm(log(y.sim.1m)~log(x.1m))
	lm.fld.1m<-lm(log(y.fld.1m)~log(x.1m))
	

	lm.sim.6m<-lm(log(y.sim.6m)~log(x.6m))
	lm.fld.6m<-lm(log(y.fld.6m)~log(x.6m))

	xlimit=c(1, max(c(x.1m, x.6m)+10))
	ylimit=c(1, 60)#max(c(y.sim.1m, y.fld.1m, y.sim.6m, y.fld.6m)))
	plot(y.sim.6m~x.6m, ylab="log(Eggs per plant)", xlab="log(plant density)", log="xy", xlim=xlimit, ylim=ylimit, cex=NA, main=plotTitle, ...)

	idf.y<-rep(idf,length(x.6m))
	lines(idf.y~x.6m, lty=1, col="#CC66CC")

	col.1m<-"#666699"
	col.6m<-"blue"#"#666699"
	pch.sim<-19
	pch.fld<-24
	lty=lty.fld<-1
	lty=lty.sim<-2
	regressionCol<-"#333333"
	point.cex<-.9
	if (only6m==FALSE) {
		plotLogRegressionLine(x.1m, lm.sim.1m, col=col.1m, lty=lty.sim)
		points(y.sim.1m~x.1m, pch=pch.sim,col=col.1m, cex=point.cex)


		plotLogRegressionLine(x.1m, lm.fld.1m, col=col.1m, lty=lty.fld)
		points(y.fld.1m~x.1m, pch=pch.fld,col=col.1m, cex=point.cex)
	}

	if (onlyField==FALSE) {
		plotLogRegressionLine(x.6m, lm.sim.6m, col=col.6m, lty=lty.sim)
		points(y.sim.6m~x.6m, pch=pch.sim,col=col.6m, cex=point.cex, bg=col.6m)
	}

	plotLogRegressionLine(x.6m, lm.fld.6m, col=col.6m, lty=lty.fld)
	points(y.fld.6m~x.6m, pch=pch.fld, col=col.6m, cex=point.cex)
	
	
	
	# legend(x="topright", inset=c(.05, 0.05), pch=c(19, 19, NA, NA), col=c("blue", "green", "grey", "grey"), legend=c("simulation", "field", "sim.reg", "fld.reg"),lty=c(NA, NA, 1, 2),  cex=.9)

	results.mx<-t.test.sim.fld(lm.sim.1m, lm.sim.6m, lm.fld.1m, lm.fld.6m)
	
	lms<-list(lm.sim.6m, lm.fld.6m, lm.sim.1m, lm.fld.1m)
	
	return (list(results.mx, lms))
}

t.test.sim.fld<-function(lm.sim.1m, lm.sim.6m, lm.fld.1m, lm.fld.6m) {
	
	names<-list(c("Sim vs Fld", "Fld vs Fld", "Sim vs Sim"), c("1 vs 1m ","6 vs 6m ", "1 vs 6m "))
	results.mx<-matrix(0,nrow=3, ncol=3, dimnames=names, byrow=TRUE)

	results.mx[1,1]<-t.test.lm(lm.expected=lm.sim.1m, lm.observed=lm.fld.1m, showDetails=FALSE)
	results.mx[1,2]<-t.test.lm(lm.expected=lm.sim.6m, lm.observed=lm.fld.6m, showDetails=FALSE)
	results.mx[1,3]<-t.test.lm(lm.expected=lm.sim.1m, lm.observed=lm.fld.6m, showDetails=FALSE)

	results.mx[2,1]<-t.test.lm(lm.expected=lm.fld.1m, lm.observed=lm.fld.1m, showDetails=FALSE)
	results.mx[2,2]<-t.test.lm(lm.expected=lm.fld.6m, lm.observed=lm.fld.6m, showDetails=FALSE)
	results.mx[2,3]<-t.test.lm(lm.expected=lm.fld.1m, lm.observed=lm.fld.6m, showDetails=FALSE)

	results.mx[3,1]<-t.test.lm(lm.expected=lm.sim.1m, lm.observed=lm.sim.1m, showDetails=FALSE)
	results.mx[3,2]<-t.test.lm(lm.expected=lm.sim.6m, lm.observed=lm.sim.6m, showDetails=FALSE)
	results.mx[3,3]<-t.test.lm(lm.expected=lm.sim.1m, lm.observed=lm.sim.6m, showDetails=FALSE)

	return (results.mx)
	
}




















