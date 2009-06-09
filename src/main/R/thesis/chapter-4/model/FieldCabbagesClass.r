# Represents the way the cabbages are laid out in the field...
# The main reson for using this is that it gives us the Density groupings for the cabbages.
# We have to be sure to give the cabbages the same id's so we also use this file to initialise the simulation

#Class definition for a Signal Surface class...



setClass("FieldCabbages", representation(
	fieldExperimentName="character",
	cabbagesFileName="character",
	cabbages.df="data.frame",
	eggDistribution="EggDistribution",
	layouts="list",
	blocks="character",
	blockSymbols="numeric",
	scaleCodeBlockSymbols="numeric",
	scaleCodeSortOrder.1M="numeric",
	scaleCodeSortOrder.6M="numeric",
	scaleCodeSortOrder.48M="numeric",
	has48mScale="logical"
))




#Add a constructor:
FieldCabbages<-function(fieldExperimentName) {
	instance<-new("FieldCabbages")

	instance@fieldExperimentName<-fieldExperimentName
	rootDir<-ExperimentData.getPublishedRootDir()
	if (fieldExperimentName == "LEVIN-II") {		
		instance@cabbagesFileName<-sprintf("%s/chapter-4/field-data/levin-06-II/Levin-06-II-Field-Data.csv", rootDir)
		cat("Reading cabbages from : ", instance@cabbagesFileName, " ...\n")
		import.df<-read.csv(instance@cabbagesFileName)

		cabbages.df<-subset(import.df, import.df$Field!="P")
		fieldF<-factor(as.character(cabbages.df$Field))
		cabbages.df$Field<-fieldF
		cabbages.df$X3.scale.code<-factor(as.character(cabbages.df$X3.scale.code))
		
		instance@layouts<-FieldCabbages.createLayouts(cabbages.df)
		
		instance@cabbages.df<-cabbages.df
		
		instance@blocks<-c("L1", "L2", "L3", "L4")
		instance@blockSymbols<-c(2, 1, 19, 15)
		
		pch.B1<-instance@blockSymbols[[1]]
		pch.B2<-instance@blockSymbols[[2]]
		pch.B3<-instance@blockSymbols[[3]]
		pch.B4<-instance@blockSymbols[[4]]

		instance@scaleCodeBlockSymbols<-c(pch.B4, pch.B1, pch.B2, pch.B3, pch.B3, pch.B3)

		instance@scaleCodeSortOrder.1M<-c(1, 3, 2, 4, 6, 5)			
		instance@scaleCodeSortOrder.6M<-c(1, 3, 2, 4, 6, 5)
#		instance@scaleCodeSortOrder.48M<-c()
		instance@has48mScale<-F
		
	} else if (fieldExperimentName=="KAITOKE-04") {
		instance@cabbagesFileName<-sprintf("%s/chapter-4/field-data/kaitoke-04/Kaitoke-04-Field-Data.csv", rootDir)
		cat("Reading cabbages from : ", instance@cabbagesFileName, " ...\n")
		
		cabbages.df<-read.csv(instance@cabbagesFileName)	
		
		#Remove any columns we dont use so it doesnt get confusing...
		cabbages.df$X1m.density<-NULL
		cabbages.df$X6m.density<-NULL
		cabbages.df$X6m.dens<-NULL
		cabbages.df$X48m.density<-NULL
		Position..corner.middle.edge.<-NULL
		cabbages.df$eastings..localised.<-NULL         
		cabbages.df$northings..localised.<-NULL        
		cabbages.df$Width.of.largest.leaf<-NULL        
		cabbages.df$eaves.5cm<-NULL                    
		cabbages.df$cab.size.leaf.width.leaves.<-NULL  
		cabbages.df$Tot.Eggs<-NULL
		cabbages.df$Position..corner.middle.edge.<-NULL
		
		cabbages.df$X48m_dens<-factor(cabbages.df$X48m_dens)		
		
		minX<-min(cabbages.df$x)
		minY<-min(cabbages.df$y)

		cabbages.df$Field<-"KAITOKE-04"
		fieldLayout<-FieldCabbageLayout("KAITOKE-04", cabbages.df, minX-6.75, minY-6.75)
		instance@layouts<-list(fieldLayout)
		
		instance@cabbages.df<-cabbages.df		
		
		instance@blocks<-c("K1", "K2", "K3", "K4")
		instance@blockSymbols<-c(2, 1, 19, 15)
		pch.B1<-instance@blockSymbols[[1]]
		pch.B2<-instance@blockSymbols[[2]]
		pch.B3<-instance@blockSymbols[[3]]
		pch.B4<-instance@blockSymbols[[4]]

		instance@scaleCodeBlockSymbols<-c(pch.B3, pch.B1, pch.B4, pch.B2, pch.B1, pch.B4, pch.B1, pch.B4)

		instance@scaleCodeSortOrder.1M<-c(1, 2, 3, 7, 8, 4, 5, 6)			
		instance@scaleCodeSortOrder.6M<-c(1, 2, 7, 4, 5, 3, 8, 6)
		instance@scaleCodeSortOrder.48M<-c(1, 4, 2, 7, 5,3, 8, 6)
		instance@has48mScale<-T
		
    } else {
		stop(sprintf("Don't know how to load field experiment '%s'", fieldExperimentName, " options are KAITOKE-04, LEVIN-II"))
	}

	
	#Convert the scale densities into factors...
	instance@cabbages.df$X1m_dens<-factor(instance@cabbages.df$X1m_dens)
	instance@cabbages.df$X6m_dens<-factor(instance@cabbages.df$X6m_dens)		
	
	instance@eggDistribution<-EggDistribution(sprintf("%s Field Distribution", fieldExperimentName), instance@cabbages.df,  "Field.EggCount", instance)

		
	return (instance)
}



FieldCabbages.createLayouts<-function(cabbages.df) {
	layouts<-list()
	for (fieldName in levels(cabbages.df$Field)) {
		field.df<-subset(cabbages.df, cabbages.df$Field==fieldName)

		minX<-min(field.df$x)
		minY<-min(field.df$y)

		fieldLayout<-FieldCabbageLayout(fieldName, field.df, minX-2.5, minY-2.5)
	
		layouts<-collection.appendToList(layouts, fieldLayout)
	}
	return (layouts)	
}


#Add a "toString" which is a "show method"
setMethod("show", "FieldCabbages", 
	function(object) {
		cat("FieldCabbages \n")	
		cat("cabbagesFileName" ,object@cabbagesFileName, "\n")
		show(object@eggDistribution)
	}
)



#And a "summary" method
setMethod("summary", "FieldCabbages", 
	function(object) {
#		cat("Experiment      : ", object@experimentName, " (", object@directoryName, ")\n")
#		cat("description     : ", object@description, "\n")
#		cat("iterationCount  : ", object@iterationCount, "\n")	
#		cat("replicateCount  : ", object@replicateCount, "\n")
	}
)

setMethod("plot", "FieldCabbages",
	function(x, y, ...) {
		FieldCabbages.genericPlot(x, y, ...)
	}
)

FieldCabbages.genericPlot<-function(x, y, ...) {
	fieldCabbages<-x
	
	if (y=="BLOCK1") {
		FieldCabbages.plotBlockBreakdown(fieldCabbages, block=1, ...)
	} else if (y=="BLOCK2") {
		FieldCabbages.plotBlockBreakdown(fieldCabbages, block=2, ...)
	} else if (y=="BLOCK3") {
		FieldCabbages.plotBlockBreakdown(fieldCabbages, block=3, ...)		
	} else if (y=="BLOCK4") {
		FieldCabbages.plotBlockBreakdown(fieldCabbages, block=4, ...)		
	} else {
		FieldCabbages.plotSummaryLayout(fieldCabbages, ...)		
	}
}

FieldCabbages.plotSummaryLayout<-function(fieldCabbages, ...) {
	if (fieldCabbages@fieldExperimentName=="KAITOKE-04") {
		par(mfrow=c(1, 1))
		FieldCabbages.plotCabbages("KAITOKE-04", fieldCabbages@eggDistribution@cabbages.df, 6.75, axis.at=c(0, 50, 100, 150, 200), addTitle=F, xLim=c(0, 200), yLim=c(0, 200), ...)
		FieldCabbages.plotKaitokeGrids(fieldCabbages)
	} else {
		par(mfrow=c(2, 2))
		FieldCabbages.plotCabbages("E", fieldCabbages@eggDistribution@cabbages.df, 2.75,axis.at=c(0, 18, 36), xLim=c(0, 36), yLim=c(0,36), graphId="a",cex.labels=1,...)
		FieldCabbages.plotLevinGrids(fieldCabbages)
		FieldCabbages.plotCabbages("F", fieldCabbages@eggDistribution@cabbages.df, 2.75,axis.at=c(0, 18, 36), xLim=c(0, 36), yLim=c(0,36), graphId="b", cex.labels=1, ...)
		FieldCabbages.plotCabbages("G", fieldCabbages@eggDistribution@cabbages.df, 2.75,axis.at=c(0, 18, 36), xLim=c(0, 36), yLim=c(0,36), graphId="c", cex.labels=1,...)
		FieldCabbages.plotCabbages("H", fieldCabbages@eggDistribution@cabbages.df, 2.75,axis.at=c(0, 18, 36), xLim=c(0, 36), yLim=c(0,36), graphId="d", cex.labels=1,...)
	}
}

FieldCabbages.plotBlockBreakdown<-function(fieldCabbages,  ...) {
	if (fieldCabbages@fieldExperimentName=="KAITOKE-04") {
		FieldCabbages.plotKaitokeBlockBreakdown(fieldCabbages@eggDistribution@cabbages.df,...)
	} else {
		FieldCabbages.plotLevinBlockBreakdown(fieldCabbages@eggDistribution@cabbages.df, ...)
	}	
}


FieldCabbages.plotKaitokeBlockBreakdown<-function(cabbages.df, block, title="") {
	if (block==1) {
		blockIds<-seq(from=100, to=100+39, by=1)
		cex.pch<-.7
		blockSize<-48
	} else if (block==2) {
		blockIds<-seq(from=140, to=140+15, by=1)
		cex.pch<-.8
		blockSize<-6
	} else if (block==3) {		
		blockIds<-seq(from=156, to=156+3, by=1)		
		cex.pch<-.8
		blockSize<-48
	} else if (block==4) {
		blockIds<-seq(from=160, to=160+39, by=1)		
		cex.pch<-.8
		blockSize<-6
	} else {
		stop(sprintf("I dont know about block %d!", block))
	}

	FieldCabbages.plotBlock(cabbages.df, blockIds, blockSize=blockSize, title, cex.pch=cex.pch)

	
}


FieldCabbages.plotLevinBlockBreakdown<-function(cabbages.df, block, title) {


	if (block==1) {
		blockIds<-seq(from=226, to=226+15, by=1)
	} else if (block==2) {
		blockIds<-seq(from=222, to=222+3, by=1)
	} else if (block==3) {		
		blockIds<-seq(from=182, to=182+39, by=1)
	} else if (block==4) {
		blockIds<-c(181)
	} else {
		stop(sprintf("I dont know about block %d!", block))
	}

	FieldCabbages.plotBlock(cabbages.df, blockIds,blockSize=6, title, cex.pch=.8)
}

FieldCabbages.plotBlock<-function(cabbages.df, blockIds, blockSize, title="", cex.pch) {
	blockCabbages.df<-subset(cabbages.df, cabbages.df$Plant.ID %in% blockIds)


	xlim<-range(blockCabbages.df$x)
	ylim<-range(blockCabbages.df$y)
	
	margin<-(blockSize-(ylim[2]-ylim[1]))/2
	y<-(blockCabbages.df$y-ylim[1])+margin
	x<-(blockCabbages.df$x-xlim[1])+margin
	par(mar=c(7, 7, 4, 4))
	plot(y~x, pch=19, col="#666666", cex=cex.pch, ann=F, axes=F, ylim=c(0, blockSize), xlim=c(0, blockSize))
	axis(1, at=c(0, blockSize/2,blockSize), cex.axis=1.5)
	axis(2, at=c(0, blockSize/2,blockSize), cex.axis=1.5)
	box()
	mtext(side=1, line=4, cex=1.2, "X (m)")
	mtext(side=2, line=4, cex=1.2, "Y (m)")
	
	title(cex=1.5, title)
	
}





FieldCabbages.plotCabbages<-function(name, cabbages.df, margin, axis.at, graphId="", 
	addTitle=T, cex.labels=1.5, xLim, yLim,cex.cabbage=0.5, ...) {
	field.df<-subset(cabbages.df, cabbages.df$Field==name)
	minId<-min(field.df$Plant.ID)
	maxId<-max(field.df$Plant.ID)
	title<-sprintf("%s) Field %s", graphId, name, minId, maxId)
	
	if (addTitle) {
		par(mar=c(7, 7, 4, 2))
	} else {
		par(mar=c(7, 7, 2, 2))
	}
	
	plot(field.df$y~field.df$x, main=title, pch=NA, ann=F,axes=F, xlim=xLim, ylim=yLim)	
	points(field.df$y~field.df$x, cex=cex.cabbage, pch=19, col="#666666", ...)
	
	axis(1, at=axis.at, cex.axis=1.5)
	axis(2, at=axis.at, cex.axis=1.5)
	
	mtext(side=1, line=4, "X (m)", cex=cex.labels)
	mtext(side=2, line=4, "Y (m)", cex=cex.labels)
	
	if (addTitle) {
		title(main=title, cex=1.5)
	}
	box()
#	for (id in minId:maxId) {
#	FieldCabbages.printId(id, field.df)
#	}

}

FieldCabbages.plotLevinGrids<-function(instance) {
	margin<-1.85
	rect(margin,36-margin-.2-6 , margin+6, 36-margin-.2, border="blue")
	text(5, 33.5, "6x6m",pos=3, col="blue")

	margin<-2.4
	adj<-.125
	rect((36-margin-1)+adj,(36-margin-1)+adj , (36-margin)+adj, (36-margin)+adj, border="blue")
	text(33, 33.5, "1x1m",pos=3, col="blue")


	lines(x=c(18, 18), y=c(0, 36), col="gray")
	lines(x=c(0, 36), y=c(18, 18), col="gray")

	text(9.5, 24, "L1", cex=1.5, col="red")
	text(26, 24, "L2", cex=1.5, col="red")
	text(9.5, 11, "L3", cex=1.5, col="red")
	text(26, 11, "L4", cex=1.5, col="red")
	
}
FieldCabbages.plotKaitokeGrids<-function(instance) {
	lines(x=c(50, 50), y=c(0, 200), col="grey")
	lines(x=c(100, 100), y=c(0, 200), col="grey")
	lines(x=c(150, 150), y=c(0, 200), col="grey")
#	lines(x=c(200, 200), y=c(0, 200), col="grey")

	lines(x=c(0, 200), y=c(50, 50), col="grey")
	lines(x=c(0, 200), y=c(100, 100), col="grey")
	lines(x=c(0, 200), y=c(150, 150), col="grey")
#	lines(x=c(0, 200), y=c(200, 200), col="grey")

	margin<-(50-48)/2
	x1<-0
	y1<-0
	scale<-48

	rect(x1+margin, y1+margin, x1+margin+scale, y1+margin+scale, border="blue")
	text(15, 45, "48x48m",pos=1, col="blue")

	margin<-(48-6)/2
	x1<-1
	y1<-50+1
	scale<-6

	rect(x1+margin, y1+margin, x1+margin+scale, y1+margin+scale, border="blue")
	text(25, 90, "6x6m",pos=1, col="blue")


	margin<-3+.25
	x1<-50+1+.5+1+.5
	y1<-1+.25+1+.5+.5
	scale<-1

	rect(x1+margin, y1+margin, x1+margin+scale, y1+margin+scale, border="blue")
	text(58, 17, "1x1m",pos=1, col="blue")
	
	text(25, 180, instance@blocks[[1]], pos=1, col="red", cex=1.5)
	text(65, 180, instance@blocks[[2]], pos=1, col="red", cex=1.5)
	text(25, 130, instance@blocks[[3]], pos=1, col="red", cex=1.5)
	text(65, 130, instance@blocks[[4]], pos=1, col="red", cex=1.5)
	
}


FieldCabbages.printId<-function(id, field.df) {
	x<-field.df$x[field.df$Plant.ID==id]
	y<-field.df$y[field.df$Plant.ID==id]
	if ((field.df$X1m_dens[field.df$Plant.ID==id] == 1) && !(field.df$X6m_dens[field.df$Plant.ID==id] == 40)){
		text(x, y, id,pos=3, col="blue")
	}
}

FieldCabbages.createSimulationLayoutFiles<-function(fieldCabbages) {
	rootDir<-ExperimentData.getPublishedRootDir()
	
	for (layout in fieldCabbages@layouts) {
		fileName<-sprintf("%s/chapter-4/resource-layouts/Field %s - Resource Layout.csv", rootDir, layout@fieldName)
		cat("Writing layout to '", fileName, "' ...\n")
		output.df<-data.frame("Plant.ID"=layout@cabbages.df$Plant.ID)
		output.df$x<-layout@cabbages.df$x
		output.df$y<-layout@cabbages.df$y
		write.csv(output.df, fileName, row.names=FALSE)
	}
}

FieldCabbages.importKaitoke04FieldData<-function() {
	rootDir<-ExperimentData.getPublishedRootDir()
	filename<-sprintf("%s/chapter-4/field-data/kaitoke-04/import/Kaitoke 04 Field Data - Cabbages.csv", rootDir)
	cat("Reading KAITOKE-04 Field Data From '" , filename, "'\n")
	kaitDensities.df<-read.csv(filename)

	filename<-sprintf("%s/chapter-4/field-data/kaitoke-04/import/Kaitoke 04 Field Data - Locations.csv", rootDir)
	cat("Reading KAITOKE-04 Locations from '", filename, "'\n")
	kaitLocations.df<-read.csv(filename)

	#str(kaitDensities.df)
	#str(kaitLocations.df)
	#min(kaitLocations.df$PlantID)
	#max(kaitLocations.df$PlantID)
	#min(kaitDensities.df$Plant.ID)
	#max(kaitDensities.df$Plant.ID)
	#min(kaitLocations.df$Eastings)-6.75
	#max(kaitLocations.df$Northings)+6.75

	kaitDensities.df$X1m_dens<-factor(kaitDensities.df$X1m.density)
	kaitDensities.df$X6m_dens<-factor(kaitDensities.df$X6m.dens)
	kaitDensities.df$X48m_dens<-factor(kaitDensities.df$X48m.density)
	kaitDensities.df$Field.EggCount<-kaitDensities.df$Tot.Eggs
	kaitDensities.df$x<-kaitLocations.df$Eastings
	kaitDensities.df$y<-kaitLocations.df$Northings


	kaitDensities.df$X3.scale.code
	filename.out<-sprintf("%s/chapter-4/field-data/kaitoke-04/Kaitoke-04-Field-Data.csv", rootDir)
	cat("Writing Kaitoke 04 Field Data to '", filename.out, "'\n")
	write.csv(kaitDensities.df, filename.out, row.names=FALSE)
	
}
FieldCabbages.importLevinII06FieldData<-function() {
	rootDir<-sprintf("%s/chapter-4/field-data/levin-06-II", ExperimentData.getPublishedRootDir())	
	importDir<-sprintf("%s/import", rootDir)
	
	filename<-sprintf("%s/Levin-06-II-Egg-Count.csv", importDir)
	levinII.df<-read.csv(filename)

	levinII.df$x<-NA
	levinII.df$y<-NA

	#Load up the local xy coordinates from each of the files
	filename<-sprintf("%s/Levin-06-II-Field-E.csv", importDir)
	localLocations.df<-read.csv(filename)

	filename<-sprintf("%s/Levin-06-II-Field-F.csv", importDir)
	field.df<-read.csv(filename)
	localLocations.df<-merge(localLocations.df, field.df, all=TRUE)

	filename<-sprintf("%s/Levin-06-II-Field-G.csv", importDir)
	field.df<-read.csv(filename)
	localLocations.df<-merge(localLocations.df, field.df, all=TRUE)

	filename<-sprintf("%s/Levin-06-II-Field-H.csv", importDir)
	field.df<-read.csv(filename)
	localLocations.df<-merge(localLocations.df, field.df, all=TRUE)

	merged.df<-merge(levinII.df, localLocations.df, all.x=TRUE, by.x="Plant.ID", by.y="Plant_ID")

	merged.df$x<-merged.df$X_local
	merged.df$y<-merged.df$Y_local

	merged.df$X_local<-NULL
	merged.df$Y_local<-NULL
	merged.df$Box_X_local<-NULL
	merged.df$Box_Y_local<-NULL
	merged.df$X_global<-NULL
	merged.df$Y_global<-NULL
	merged.df$Box_X_global<-NULL
	merged.df$Box_Y_global<-NULL
	merged.df$Dens_1m<-NULL
	merged.df$Dens_6m<-NULL
	merged.df$X<-NULL
	merged.df$X3.scale.code<-merged.df$scale_code
	merged.df$scale_code<-NULL
	merged.df$Field.EggCount<-merged.df$eggs_24.02.2006
	merged.df$eggs_24.02.2006<-NULL
	
	filename.out<-sprintf("%s/Levin-06-II-Field-Data.csv", rootDir)
	cat("Writing Levin II 06 Field Data to '", filename.out, "'\n")
	write.csv(merged.df, filename.out, row.names=FALSE)
	
}

FieldCabbages.LEVINII<-FieldCabbages("LEVIN-II")
FieldCabbages.KAITOKE04<-FieldCabbages("KAITOKE-04")

