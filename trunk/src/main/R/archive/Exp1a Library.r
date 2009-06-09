#All the functions that are specific to analysing Experiment 1a - which is all to do with the Release boundary distance

source("~/Work/code/bugsim/src/R/archive/Bugsim R Library.r", echo=FALSE)
source("~/Work/code/bugsim/src/R/archive/Experiment 1a Boundary Crossing Library.r", echo=FALSE)
cat("Experiment 1a Library Version 1.0")


describeExperiment1a<-function(inp.df){
	numReplicants<-length(inp.df$replicant)/length(levels(inp.df$iteration))	
	cat("Iterations : ", levels(inp.df$iteration), "\n")	
	cat("numReplicants : ", numReplicants, "\n")
	cat("L : ", levels(inp.df$L), "\n")
	cat("A : ", levels(inp.df$A), "\n")
	cat("B : ", levels(inp.df$B), "\n")



	return (numReplicants)
}

outputRatios<-function(ratios.list, M.list, baseFilename, experimentNumber, trialId, title, plotTitle) {
	formattedTitle<-sprintf("%s Trial %s-%s", plotTitle, trialId, experimentNumber)
	
	quartz(width=12, height=8)
	par(mfrow=c(2, 3))
	plotRatios(ratios.list, M.list, title, formattedTitle)
	
	
	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)
	outputFilename<-sprintf("%s/%s.pdf", directory, formattedTitle)
	cat("Writing output file: ", outputFilename,"\n")
	pdf(file=outputFilename, height=8, width=12)
	par(mfrow=c(2, 3))
	plotRatios(ratios.list, M.list, title, formattedTitle)
	dev.off()
	
}


plotRatios<-function(ratios.list, M.list, title, mainTitle) {
	layout.mx<-matrix(c(1,1,1,2, 3, 4, 5, 6, 7), 3, 3, byrow = TRUE)
	layout(layout.mx, heights=c(20, 40, 40))
	
	par(mar=c(1, 1, 1, 1))
	plot.new()
	text(x=.5, y=.9, mainTitle, cex=1.5,font=2)
	clrs<-c("green", "blue")
	legend(x=.4, y=.6,legend=(c("Centre", "Corner")), fill=clrs, ncol=3, cex=1.2)
	
	i<-1
	while (i<=length(ratios.list)){
		input.ratios.df<-ratios.list[[i]]
		M<-M.list[[i]]
		par(mar=c(5, 5, 3, 3), cex=1)
		plotRatio(input.ratios.df, M, title)
		i<-i+1
	}
}

plotRatio<-function(ratios.df, M, title) {
	
	title<-sprintf(title, M)
	par(mar=c(5, 5, 5, 3))
	plot(ratios.df$I, ratios.df$CORNER, type="l", xlim=c(0, 90), ylim=c(0, 1),xlab="I", ylab="ratio", col="blue", xaxt="n", main=title)
	lines( ratios.df$CENTRE, col="green")
	axis(side=1, at=c(0,90))
}


calculateRatiosFromMaps<-function(maps.list) {
	ratios.list<-list()
	for (input.mx in maps.list) {
#	test.mx<-maps.list[[6]]
		ratios.df<-calculateRatiosForMap(input.mx)
		ratios.list<-appendToList(ratios.list, ratios.df)
	}
	return (ratios.list)	
}





#Loops over the columns in a matrix and calculates the ratios of centre:corner cabbages as I varies
calculateRatiosForMap<-function(input.mx) {	
	ratios.df<-data.frame("I"=c(0:90))
	ratios.df$MISSED<-rep(0, 91)
	ratios.df$CENTRE<-rep(0, 91)
	ratios.df$CORNER<-rep(0, 91)

	i.I<-1
	while(i.I<=length(ratios.df$I)) {
		col<-input.mx[,i.I]

		cabbageTypes<-c(0, 1, 2)
		counts.vector<-rep(0, length(cabbageTypes))

		i.row<-1
		while (i.row <= length(col)) {
			value<-col[i.row]
			i.cabbageType<-1
			while (i.cabbageType <= length(cabbageTypes)) {
				if (cabbageTypes[[i.cabbageType]]==value) {
					count<-counts.vector[[i.cabbageType]]
					counts.vector[[i.cabbageType]]<-count+1
				}
				i.cabbageType<-i.cabbageType+1
			}
			i.row<-i.row+1

		}
		total<-sum(c(counts.vector[2], counts.vector[3]))
		ratios.vector<-rep(0, length(counts.vector))
		i.count<-1
		while (i.count <= length(counts.vector)) {
			ratios.vector[[i.count]]<-counts.vector[[i.count]]/total
			i.count<-i.count+1
		}

		ratios.df$MISSED[[i.I]]<-ratios.vector[1]
		ratios.df$CENTRE[[i.I]]<-ratios.vector[2]
		ratios.df$CORNER[[i.I]]<-ratios.vector[3]
		i.I<-i.I+1
	}
	return (ratios.df)
}




#	outputFilename<-sprintf("%s/%s.jpg", directory, title)
#	cat("Writing output to : ", outputFilename, "\n")
#	jpeg(filename=outputFilename, width=2400, height=2000, quality=100)
#	plotMapsTrialH(maps.list, title)
#	dev.off()

outputTrialH<-function(maps.list, M.list, baseFilename, experimentNumber, trialId, title, plotTitle, colors=c("#9999FF", "#33CC33", "blue"), zlim=c(0,2), drawLegend=TRUE) {
	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)
		
	quartz(width=10, height=8)
	plotMapsTrialH(maps.list,M.list, title, plotTitle, colors, zlim, drawLegend)
	outputFilename<-sprintf("%s/%s.pdf", directory, title)
	cat("Writing output to : ", outputFilename, "\n")
	pdf(file=outputFilename, width=12, height=10, bg="white")
	plotMapsTrialH(maps.list, M.list, title, plotTitle, colors, zlim, drawLegend)
	dev.off()	
	
}



plotMapsTrialH<-function(maps.list,M.list, title, plotTitle, colors=c("#9999FF", "#33CC33", "blue"), zlim=c(0,2), drawLegend=TRUE) {
	layout.mx<-matrix(c(1,1,1,2, 3, 4, 5, 6, 7), 3, 3, byrow = TRUE)
	layout(layout.mx, heights=c(20, 40, 40))
	
	par(mar=c(1, 1, 1, 1))
	plot.new()
	text(x=.5, y=.9, title, cex=1.5,font=2)

	if (drawLegend==TRUE) {
		legend(x=.4, y=.6,legend=(c("Missed", "Centre", "Corner")), fill=colors, ncol=3, cex=1.2)
	}
	
	for (i in 1:length(maps.list)) {		
		map.mx<-maps.list[[i]]
		M<-M.list[[i]]
		plotMapTrialH(map.mx, M, colors, plotTitle, zlim)		
	}
	
}

#map.mx<-maps.list[[6]]
plotMapTrialH<-function(map.mx, M, colors, plotTitle, zlim=c(0,2)) {
	x<-0:180
	y<-0:90
	par(mar=c(5, 5, 3, 3), cex=1)
	imageTitle<-sprintf(plotTitle, M)
	image(x, y,z=map.mx, col=colors, zlim=zlim,xlim=c(0,180), ylim=c(0,90), xlab="a (degrees)", ylab="I (degrees)", cex=2, main=imageTitle,xaxt="n", yaxt="n")	
	axis(side=1, at=seq(from=0 ,to=180, by=45 ))
	axis(side=2, at=seq(from=0 ,to=90, by=30 ))
}


readIterationsTrialH<-function(iterations, baseFilename, experimentNumber, trialId) {
	matrices.list<-list()
	M.list<-list()
	for (itr in iterations) {
		itrContents.list<-readIterationTrialH(itr, baseFilename, experimentNumber, trialId)

		matrices.list<-appendToList(matrices.list, itrContents.list[[1]])		
		M.list<-appendToList(M.list, itrContents.list[[2]])
	}
	results<-list(matrices.list, M.list)
	return (results)
}

#itr<-1
readIterationTrialH<-function(itr, baseFilename, experimentNumber, trialId) {

	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)
	iterationId<-itr
	replicantId<-1
	iterationDir<-sprintf("%s/Iteration-%03d-%03d", directory, iterationId, replicantId)
	butterfliesFilename<-sprintf("%s/%03d-%03d-%03d-butterflies.csv", iterationDir, experimentNumber, iterationId, replicantId)
	print(sprintf("Reading iteration %d : %s ...", itr, butterfliesFilename))
	butterflies.df<-read.csv(butterfliesFilename)
	I.rounded<-as.numeric(sprintf("%0.0f", butterflies.df$I))
	butterflies.df$I<-factor(butterflies.df$I)
	butterflies.df$h<-factor(butterflies.df$InitialHeading)

	butterflies.df <- butterflies.df[order(butterflies.df$I, butterflies.df$h) , ]


	zm<-matrix(0, nrow=181, ncol=91)
	for (I.level in levels(butterflies.df$I)) {
		I.group.df<-subset(butterflies.df, butterflies.df$I==I.level)
		i<-as.numeric(I.level)
#		print(sprintf("Processing i=%d ...", i))
		zm[,(i+1)]<-I.group.df$lastCabbage.analysis.code
	}
	M<-max(butterflies.df$Displacement)
	results<-list(zm, M)
	return (results)
}
#iterations<-inp.df$iteration
#itr<-iterations[[1]]
readIterationsTrialJ<-function(iterations, numReplicants, baseFilename, experimentNumber, trialId) {
	matrices.list<-list()
	M.list<-list()
	for (itr in iterations) {
		itrContents.list<-processReplicantsTrialJ(as.numeric(itr), numReplicants, baseFilename, experimentNumber, trialId)

		matrices.list<-appendToList(matrices.list, itrContents.list[[1]])		
		M.list<-appendToList(M.list, itrContents.list[[2]])
	}
	results<-list(matrices.list, M.list)
	return (results)
}

#itr<-6
processReplicantsTrialJ<-function(itr, numReplicants, baseFilename, experimentNumber, trialId) {
	
	matrices.list<-list()
	replicant<-1
	while (replicant <= numReplicants) {
		itrContents.list<-readIterationTrialI(itr, replicant, baseFilename, experimentNumber, trialId)
		M<-itrContents.list[[2]]
				
		matrices.list<-appendToList(matrices.list, itrContents.list[[1]])		
						
	    replicant<-replicant+1
	}
	
	matrix.out<-amalgamateReplicants(matrices.list)
	
	return (list(matrix.out, M))
	
}

readIterationTrialI<-function(itr, replicant, baseFilename, experimentNumber, trialId) {
	directory<-createDirectoryName(baseFilename, experimentNumber, trialId)
	iterationId<-itr
	replicantId<-replicant
	iterationDir<-sprintf("%s/Iteration-%03d-%03d", directory, iterationId, replicantId)
	butterfliesFilename<-sprintf("%s/%03d-%03d-%03d-butterflies.csv", iterationDir, experimentNumber, iterationId, replicantId)
	print(sprintf("Reading iteration %d replicant %d : %s ...", itr, replicant, butterfliesFilename))
	butterflies.df<-read.csv(butterfliesFilename)

	butterflies.df$I<-butterflies.df$I
	butterflies.df$h<-butterflies.df$InitialHeading

	butterflies.df$I.rounded<-round(butterflies.df$I, digits=0)
	butterflies.df$h.rounded<-round((butterflies.df$h-(90+butterflies.df$I)), digits=0)
	
	
	butterflies.df <- butterflies.df[order(butterflies.df$I.rounded, butterflies.df$h.rounded) , ]

	butterflies.90.df<-subset(butterflies.df,  butterflies.df$I.rounded <= 90)
	butterflies.90.df$I.rounded<-factor(butterflies.90.df$I.rounded)
	butterflies.90.df$h.rounded<-factor(butterflies.90.df$h.rounded)
	
	zm<-matrix(0, nrow=181, ncol=91)
	for (I.level in levels(butterflies.90.df$I.rounded)) {
		I.group.df<-subset(butterflies.90.df, butterflies.90.df$I.rounded==I.level)
		i<-as.numeric(I.level)
	#	print(sprintf("Processing i=%d ...", i))
		
		iDataRow<-1
		while (iDataRow<=length(I.group.df$h.rounded)) {
			iRow<-I.group.df$h.rounded[[iDataRow]]
			zm[iRow,(i+1)]<-I.group.df$lastCabbage.analysis.code[[iDataRow]]
			iDataRow<-iDataRow+1
		}

	}
	M<-max(butterflies.90.df$Displacement)
	results<-list(zm, M)
	return (results)
}




#out.mx<-amalgamateReplicants(matrices.list)
amalgamateReplicants<-function(matrices.list) {
	
	def.mx<-matrices.list[[1]]
	rowCount<-length(def.mx[,1])
	colCount<-length(def.mx[1,])
	
	output.mx<-matrix(0, nrow=rowCount, ncol=colCount)

	iRow<-1	
	while(iRow <= rowCount) {		
		iCol<-1
		while(iCol<= colCount) {
			cornerRatio<-calculateCornerRatio(matrices.list, iRow, iCol)			
			output.mx[iRow, iCol]<-cornerRatio*10
			iCol<-iCol+1
		}
				
		iRow<-iRow+1
	}	
	return (output.mx)	
}

#i.matrix<-matrices.list[[1]]
#iRow<-2
#iCol<-2
calculateCornerRatio<-function(matrices.list, iRow, iCol) {
	
	counts<-c(0,0,0)
	for (i.matrix in matrices.list) {
		value<-i.matrix[iRow, iCol]
		counts[value+1]<-counts[value+1]+1		
	}
	total<-sum(counts[2], counts[3])
	cornerRatio<-0
	if (total>0) {
		cornerRatio<-counts[3]/total
	}
	return (cornerRatio)
}


createTestMatrices<-function() {
	a.mx<-matrix(0, 2, 2)
	a.mx[1, 1]<-1
	a.mx[1, 2]<-1
	a.mx[2, 1]<-0
	a.mx[2, 2]<-2

	b.mx<-matrix(0, 2, 2)
	b.mx[1, 1]<-1
	b.mx[1, 2]<-2
	b.mx[2, 1]<-0
	b.mx[2, 2]<-2

	c.mx<-matrix(0, 2, 2)
	c.mx[1, 1]<-2
	c.mx[1, 2]<-2
	c.mx[2, 1]<-0
	c.mx[2, 2]<-2
	matrices.list<-list()
	matrices.list<-appendToList(matrices.list, a.mx)
	matrices.list<-appendToList(matrices.list, b.mx)
	matrices.list<-appendToList(matrices.list, c.mx)
	return (matrices)
}