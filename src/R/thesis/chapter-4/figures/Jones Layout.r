output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/02-review/resources/figures"

#Dont forget to execute all the functions below...

filename<-sprintf("%s/jones-1977-layouts.pdf", output.figures)
cat("Writing graph to ", filename, "\n")
pdf(file=filename, width=15, height=10, bg="white")
	plotJonesFieldLayouts()
dev.off()

filename<-sprintf("%s/jones-1977-simulation-DIR.pdf", output.figures)
cat("Writing graph to ", filename, "\n")
pdf(file=filename, width=10, height=10, bg="white")
	plotJonesSimulationDIR()
dev.off()

quartz(width=15, height=10)
plotJonesFieldLayouts()

quartz(width=10, height=10)
plotJonesSimulationDIR()


#Demonstration of DIR parameter ....
plotJonesSimulationDIR<-function() {
	par(mar=c(2, 2, 2, 2))
	layout<-plotGrid(patchSizeX=6, start=1, endX=5, by=1)
	
	plotPotentialmoves()
	plotCabageLayout(layout)
	
	points(3,3, col="black", pch=19, cex=3.5)
	points(3,3, col="grey", pch=19, cex=3)
	
}

#quartz(width=10, height=10)
#plotJonesFieldLayouts()
plotJonesFieldLayouts<-function() {
	layout.mx<-rbind(c(1, 2, 3, 4, 5),
					c(6, 7,7, 7, 8))

	layout(layout.mx,widths=c(5, 42.5, 5, 42.5, 5), heights=c(85, 25) )

	par(mar=c(0, 0, 0,0))
	plot.new() #1


	# Uniform grid - 2
	par(mar=c(5, 2, 7, 2))
	layout<-plotGrid(patchSizeX=7, start=1, endX=6, by=1)
	plotCabageLayout(layout)
	title(main=expression("a) Uniform Grid Layout"), line=2, cex.main=2.5)
	mtext(side=1, line=2, expression("1, 2, 4 or 10m"), cex=1.5)

	par(mar=c(0, 0, 0,0))
	plot.new() #3

	#Clumped Grid - 4 
	par(mar=c(5, 2, 7, 2))
	layout<-plotGrid(patchSizeX=7, start=1, endX=6, by=1)
	layout<-createClumpedLayout(startCoord=c(1, 1), groupSpacing=1, interGroupSpacing=3, patchSize=7)
	plotCabageLayout(layout)
	title(main=expression("b) Clumped Grid Layout"), line=2, cex.main=2.5)

	par(mar=c(0, 0, 0,0))
	plot.new() #5
	plot.new() #6

	par(mar=c(3, 2, 7, 2))
	plot20mLayout()
	title(main=expression("c) Linear Layout"), line=2, cex.main=2.5)
}

plot20mLayout<-function() {
	layout<-plotGrid(patchSizeX=51, patchSizeY=5,start=1, endX=50, endY=4, by=1)

	coords.x<-c(1, 2, 3, 23+1, 23+2, 23+3, 23+23+1, 23+23+2, 23+23+3)
	coords.x<-c(coords.x, coords.x)
	coords.y<-c(rep(1, length(coords.x)/2), rep(4, length(coords.x)/2))

	layout<-list("coords.x"=coords.x, "coords.y"=coords.y)
	plotCabageLayout(layout, cex.cabbage=1.5)
	arrows(3, 2, 24,2, lwd=3, col=rgb(0.3, 0.3, 0.3), code=3)
	text(13, 3, expression("20m"), cex=2.5)
}

createClumpedLayout<-function(startCoord, groupSpacing, interGroupSpacing, patchSize) {
	curr.x<-startCoord[1]
	curr.y<-startCoord[2]
	
	coords.x<-c()
	coords.y<-c()
	
	while (curr.x<=patchSize) {
		
		curr.y<-startCoord[2]
		while  (curr.y<=patchSize) {
			coords.x<-c(coords.x, curr.x)
			coords.y<-c(coords.y, curr.y)
			
			coords.x<-c(coords.x, curr.x+groupSpacing)
			coords.y<-c(coords.y, curr.y)

			coords.x<-c(coords.x, curr.x)
			coords.y<-c(coords.y, curr.y+groupSpacing)

			coords.x<-c(coords.x, curr.x+groupSpacing)
			coords.y<-c(coords.y, curr.y+groupSpacing)
			
			curr.y<-curr.y+groupSpacing+interGroupSpacing
		}
		curr.x<-curr.x+groupSpacing+interGroupSpacing
	}
	
	return (list("coords.x"=coords.x, "coords.y"=coords.y))
}

createSquareLayout<-function(start, endX,endY=endX,  by) {
	cabbages.x<-seq(from=start, to=endX, by=by)
	cabbages.y<-seq(from=start, to=endY, by=by)

	coords.x<-c()
	coords.y<-c()
	for (i.x in cabbages.x) {
		for (i.y in cabbages.y) {
			coords.x<-c(coords.x, i.x)
			coords.y<-c(coords.y, i.y)	
		}
	
	}
	return (list("coords.x"=coords.x, "coords.y"=coords.y))
}

plotGrid<-function(patchSizeX,patchSizeY=patchSizeX, ...) {
	layout<-createSquareLayout(...)
	plot(layout$coords.y~layout$coords.x, type="p", pch=NA, xlim=c(0, patchSizeX), ylim=c(0, patchSizeY), ann=F, axes=F)

	x.factor<-as.factor(layout$coords.x)
	y.factor<-as.factor(layout$coords.y)
	for (i.x in as.numeric(levels(x.factor))) {
		lines(c(i.x, i.x), c(0, patchSizeY), col="grey", lwd=1.5)		
	}
	for (i.y in as.numeric(levels(y.factor))) {
		lines(c(0, patchSizeX), c(i.y, i.y), col="grey", lwd=1.5)			
	}

	box()
	return (layout)
}

plotCabageLayout<-function(layout, cex.cabbage=3,nodeCol="#333333"){		
	points(layout$coords.y~layout$coords.x, pch=19, col=nodeCol, cex=cex.cabbage)	
}

plotPotentialmoves<-function(coord.centre=c(3, 3)) {
	#Start at North and work around, 8 points...
	prob.dist<-c(0.9, 0.8, 0.6, 0.5, 0.5, 0.5, 0.6, 0.8)

	
	coord.n<-coord.rectangularFromPolar(c(90, prob.dist[1]))
	coord.nw<-coord.rectangularFromPolar(c(45+90, prob.dist[2]))
	coord.w<-coord.rectangularFromPolar(c(180, prob.dist[3]))
	coord.sw<-coord.rectangularFromPolar(c(45+180, prob.dist[4]))
	coord.s<-coord.rectangularFromPolar(c(270, prob.dist[5]))
	coord.se<-coord.rectangularFromPolar(c(270+45, prob.dist[6]))
	coord.e<-coord.rectangularFromPolar(c(0, prob.dist[7]))
	coord.ne<-coord.rectangularFromPolar(c(45, prob.dist[8]))

	potential.nw<-c(coord.centre[1]-1, coord.centre[2]+1)
	potential.ne<-c(coord.centre[1]+1, coord.centre[2]+1)
	potential.sw<-c(coord.centre[1]-1, coord.centre[2]-1)
	potential.se<-c(coord.centre[1]+1, coord.centre[2]-1)

	plotLine(coord.centre, potential.nw)
	plotLine(coord.centre, potential.ne)
	plotLine(coord.centre, potential.sw)
	plotLine(coord.centre, potential.se)

	plotProbVector(coord.centre, coord.n)
	plotProbVector(coord.centre, coord.nw)
	plotProbVector(coord.centre, coord.w)
	plotProbVector(coord.centre, coord.sw)
	plotProbVector(coord.centre, coord.s)
	plotProbVector(coord.centre, coord.se)
	plotProbVector(coord.centre, coord.e)
	plotProbVector(coord.centre, coord.ne)

}

plotLine<-function(startCoord, endCoord) {
	lines(c(startCoord[1], endCoord[1]), c(startCoord[2], endCoord[2]), lwd=1.5, col="grey")
}
plotProbVector<-function(startCoord, endCoord) {
	arrows(startCoord[1],startCoord[1], endCoord[1]+startCoord[1], endCoord[2]+startCoord[2], lwd=4, col="darkgrey")	
}

