#Generates random egg distributions simply by plantID, so no account of space is taken.
cat("Random Egg Distribution Library (v 1.0)\n")


LEVIN06II.eggCounts<-list(
				"E"=list(label="E", egg.count=613),
				"F"= list(label="F", egg.count=476),
				"G"= list(label="G", egg.count=590),
				"H"= list(label="H", egg.count=438)
				)

#dist.randomPlant<-dist.random
REGG.plotEqualAndRandomPlantDistributions<-function(dist.equal, dist.randomPlant) {
	layout.mx<-rbind(c( 1, 1, 1, 1, 1),
					 c( 1, 2, 3, 4, 1),
					 c( 1, 1, 5, 5, 1))
	layout(layout.mx, widths=c(15, 3, 30, 30, 16), heights=c(5, 90, 5))
	cex.labels<-1.8
	cex.axis<-2
	mgp<-c(3, 1.5, 0)
	par(mar=c(0,0,0,0))
	plot.new() #1
#	plot.new() #2
#	plot.new() #3 
	plot.new() #2 
	mtext(side=2, line=-3, EggDistribution.eggsPerPlantErrCaption(), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.equal, plotAxes=F,plotLegend=F, cex.axis=cex.axis, mgp=mgp)
	mtext(side=3, line=2, expression("a) Equal Eggs"), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.randomPlant, plotAxes=F,plotLegend=T, cex.axis=cex.axis, mgp=mgp)
	mtext(side=3, line=2, expression("b) Random Plant"), cex=cex.labels)

	par(mar=c(0,0,0,0))
	plot.new()#5
	mtext(side=1, line=-2, "Plant Density", cex=cex.labels)
	
}

REGG.plotSingleFromSkyDistribution<-function(dist.fromSky) {
	layout.mx<-rbind(c( 1, 1, 1,  1),
					 c( 1, 2, 3,  1),
					 c( 1, 1, 4,  1))
	layout(layout.mx, widths=c(30, 3, 30, 31), heights=c(5, 90, 5))
	cex.labels<-1.8
	cex.axis<-2
	mgp<-c(3, 1.5, 0)
	par(mar=c(0,0,0,0))
	plot.new() #1
#	plot.new() #2
#	plot.new() #3 
	plot.new() #2 
	mtext(side=2, line=-3, EggDistribution.eggsPerPlantErrCaption(), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.fromSky, plotAxes=F,plotLegend=T, cex.axis=cex.axis, mgp=mgp)
#	mtext(side=3, line=2, expression("a) Equal Eggs"), cex=cex.labels)

	par(mar=c(0,0,0,0))
	plot.new()#4
	mtext(side=1, line=-2, "Plant Density", cex=cex.labels)
	
}

REGG.plotFromSkyDistributions<-function(dist.R5.fromSky, dist.R10.fromSky, dist.R20.fromSky) {
	layout.mx<-rbind(c(1, 2, 2, 2, 1),
					 c(3, 4, 5, 6, 7),
					 c(8, 9, 9, 9,10))
	layout(layout.mx, widths=c(3, 30, 30, 30, 1), heights=c(5, 90, 5))

	cex.labels<-1.8
	cex.axis<-2
	mgp<-c(3, 1.5, 0)
	par(mar=c(0,0,0,0))
	plot.new() #1
	plot.new() #2
	plot.new() #3 
	mtext(side=2, line=-3, EggDistribution.eggsPerPlantErrCaption(), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.R5.fromSky, plotAxes=F,plotLegend=F, cex.axis=cex.axis, mgp=mgp)
	mtext(side=3, line=2, expression("a) R=5"), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.R10.fromSky, plotAxes=F,plotLegend=F, cex.axis=cex.axis, mgp=mgp)
	mtext(side=3, line=2, expression("b) R=10"), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.R20.fromSky, plotAxes=F,plotLegend=T, cex.axis=cex.axis, mgp=mgp)
	mtext(side=3, line=2, expression("c) R=20"), cex=cex.labels)

	par(mar=c(0,0,0,0))
	plot.new()#7
	plot.new()#8
	plot.new()#9
	mtext(side=1, line=-2, "Plant Density", cex=cex.labels)

}

REGG.plotCalibrationDistributions<-function(dist.R5.fromSky, dist.R10.fromSky, dist.R20.fromSky) {
	layout.mx<-rbind(c(1, 2, 2, 2, 1),
					 c(3, 4, 5, 6, 7),
					 c(8, 9, 9, 9,10))
	layout(layout.mx, widths=c(3, 30, 30, 30, 1), heights=c(5, 90, 5))

	cex.labels<-1.8
	cex.axis<-2
	mgp<-c(3, 1.5, 0)
	par(mar=c(0,0,0,0))
	plot.new() #1
	plot.new() #2
	plot.new() #3 
	mtext(side=2, line=-3, EggDistribution.eggsPerPlantErrCaption(), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.R5.fromSky, plotAxes=F,plotLegend=F, cex.axis=cex.axis, mgp=mgp)
	mtext(side=3, line=2, expression("a) Equal Eggs"), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.R10.fromSky, plotAxes=F,plotLegend=F, cex.axis=cex.axis, mgp=mgp)
	mtext(side=3, line=2, expression("b) Random Plant"), cex=cex.labels)
	EggDistribution.plotAggregatedDistribution(dist.R20.fromSky, plotAxes=F,plotLegend=T, cex.axis=cex.axis, mgp=mgp)
	mtext(side=3, line=2, expression("c) Random Location"), cex=cex.labels)

	par(mar=c(0,0,0,0))
	plot.new()#7
	plot.new()#8
	plot.new()#9
	mtext(side=1, line=-2, "Plant Density", cex=cex.labels)

}

#replicates<-1;i.replicate<-1;cabbages.layout.df<-fieldCabbages@eggDistribution@cabbages.df
#radius<-0.20
REGG.createFRromSkyDistribution<-function(cabbages.layout.df, replicates, radius, patchSize=51, plot=T) {

	cabbages.layout.df$FromSky.EggCount<-0
	totalEggs<-sum(cabbages.layout.df$Field.EggCount)
	replication.df<-NULL

	if(plot) {
		quartz(width=10, height=10)
	}

	for (i.replicate in 1:replicates) {
		cat("Replicate ", i.replicate, "\n")

		cabbages.replicate.df<-data.frame(cabbages.layout.df)
		cabbages.replicate.df$Replicate<-i.replicate		

		cabbages.replicate.df<-REGG.layEggsInEachFieldFromSky(cabbages.replicate.df, radius, i.replicate=i.replicate)

		if (is.null(replication.df)) {
			replication.df<-cabbages.replicate.df
		} else {
			replication.df<-merge(replication.df, cabbages.replicate.df, all=T)
		}
	}
	return (replication.df)
}

#cabbages.df<-cabbages.replicate.df
#colnames(cabbages.df)
#radius<-0.20
#cabbages.df$FromSky.EggCount<-0
#cabbages.df$Plant.ID[cabbages.df$FromSky.EggCount>0]
#cabbages.df<-fieldCabbages@eggDistribution@cabbages.df
#plot(cabb.y~cabb.x, xlim=c(0,36), ylim=c(0,36), pch=19, col="grey")
#field.df<-subset(cabbages.df, cabbages.df$Plant.ID==plantIDs)
#colnames(field.df)
#yvalues<-as.numeric(field.df$FromSky.EggCount)
#xvalues<-as.numeric(field.df$X6m_dens)
#stats.df<-SummaryStatistics.createUngroupedSummaryStatistics(field.df, "X6m_dens", "FromSky.EggCount")
#xv<-as.numeric(levels(stats.df$X))
#plot(stats.df$MEAN~xv, type="l", log="x", axes=F)
#axis(1, at=xv)
#points(stats.df$MEAN~xv, col="blue", pch=19, cex=1.5)
REGG.layEggsInEachFieldFromSky<-function(cabbages.df, radius, eggCounts=LEVIN06II.eggCounts, patchSize=36, plot=T, i.replicate=1) {


	for (count in eggCounts) {
		eggsLaid<-0		

		plantIDs<-cabbages.df$Plant.ID[cabbages.df$Field==count$label]

		cabb.x<-cabbages.df$x[cabbages.df$Plant.ID==plantIDs]
		cabb.y<-cabbages.df$y[cabbages.df$Plant.ID==plantIDs]
		centres<-coord.combineXY(cabb.x, cabb.y)

		if (plot) {
			plot(cabb.y~cabb.x, xlim=c(0,36), ylim=c(0,36), pch=19, col="grey", xlab="X(m)", ylab="Y(m)", main=sprintf("Field %s", count$label))
		}
#		generatedPoints<-list()
		eggsLaid<-0 #
		while(eggsLaid<count$egg.count) {			
			result<-dropEggFromSky(patchSize, centres, radius, cabbages.df, plot)
			cabbages.df<-result$cabbages
			if (result$eggLaid) {
				eggsLaid<-eggsLaid+1
				if (plot) {
					mid<-patchSize/2
					rect(mid-6, mid-6, mid+6, mid+6, col="white", border=NA)
					text(mid, mid, sprintf("Replicate : %0.0f, Total Eggs: %0.0f", i.replicate, eggsLaid))			
				}
			}
		}
	}
	return (cabbages.df)
}



dropEggFromSky<-function(patchSize, centres, radius, cabbages.df, plot) {
	point<-runif(n=2, min=0,max=patchSize)
	lookup<-sapply(centres, FUN=isPointInCentreX, point, radius)
	indexes<-which(lookup, T)
	if (length(indexes)>0) { #If more than one cabbage intersects, choose one at random.
		index.chosen<-indexes[[round(runif(n=1, min=1, max=length(indexes)))]]
	}

	eggLaid<-F
	if (length(indexes==1)) {
		plantID.chosen<-plantIDs[[index.chosen]]
		cabbages.df$FromSky.EggCount[cabbages.df$Plant.ID==plantID.chosen]<-cabbages.df$FromSky.EggCount[cabbages.df$Plant.ID==plantID.chosen]+1					
		eggLaid<-T
		count<-cabbages.df$FromSky.EggCount[cabbages.df$Plant.ID==plantID.chosen]
		if (plot) {
			points(point[2]~point[1], col="green", pch=19, cex=1+(count/10))	

		}
	} else {

		#points(point[2]~point[1], col="red", pch=19, cex=0.5)				
	}
	
	return (list("eggLaid"=eggLaid, "cabbages"=cabbages.df))

}

isPointInCentreX<-function(centre, point, radius) {
	return (Geometry.isPointInCircle(point, centre, radius))
}


#cabbages.df<-fieldCabbages@eggDistribution@cabbages.df;cabbages.replicate.df$Random.EggCount;replicates<-7;replication.df$Random.EggCount;replication.merged.df$Random.
#undebug(REGG.createUniformEggDistribution);i.replicate<-3
REGG.createUniformEggDistribution<-function(cabbages.df, replicates, eggCounts=LEVIN06II.eggCounts) {
	
	replication.df<-NULL
	
	cabbages.df$Random.EggCount<-0

	for (i.replicate in 1:replicates) {	
		cabbages.replicate.df<-as.data.frame(cabbages.df)
		cabbages.replicate.df$Replicate<-i.replicate

	 	cat("Replicate ", i.replicate, "\n")
		for (count in eggCounts) {
			actual<-sum(cabbages.replicate.df$Field.EggCount[cabbages.replicate.df$Field==count$label])		
			cabbages.replicate.df<-REGG.createFieldEggDistribution(cabbages.replicate.df, count)		
		}

		if (is.null(replication.df)) {
			replication.df<-cabbages.replicate.df
			
		} else {
			replication.merged.df<-merge(replication.df, cabbages.replicate.df, all=T)
			replication.df<-replication.merged.df
		}
		
	}	
	return (replication.df)
}


a.df<-data.frame("a"=1:10)
a.df$iteration<-1
a2.df<-data.frame("a"=1:10)
a2.df$iteration<-2
merged.df<-merge(a.df, a2.df, all=T)
REGG.createFieldEggDistribution<-function(cabbages.df, count) {
	cabbageCount<-length(cabbages.df$Field.EggCount[cabbages.df$Field==count$label])

	field.plantIDS<-cabbages.df$Plant.ID[cabbages.df$Field==count$label]

	minPlantID<-min(field.plantIDS)
	maxPlantID<-max(field.plantIDS)

	cabbages.df<-REGG.createRandomEggDistribution(count, cabbages.df, minPlantID, maxPlantID)
	return (cabbages.df)
}

REGG.createRandomEggDistribution<-function(count, cabbages.df, minPlantID, maxPlantID) {
	x<-round(runif(n=count$egg.count, minPlantID, maxPlantID))
	for (R in x) {	
		cabbages.df<-REGG.layEgg(R, cabbages.df)
	}
	return (cabbages.df)
}
#cabbages.df<-cabbages.ALL.df
#plantID<-181
REGG.layEgg<-function(plantID, cabbages.df, countColumnName="Random.EggCount") {
	currentCount<-cabbages.df[[countColumnName]][cabbages.df$Plant.ID==plantID]
	cabbages.df[[countColumnName]][cabbages.df$Plant.ID==plantID]<-currentCount+1
	return (cabbages.df)
}
