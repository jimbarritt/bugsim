cat("Ant Library (Version 1.0)\n")

library(lattice)

AntLibrary.loadBaitCabbages<-function(layoutFileName, experimentPlan, iterationNumber=1) {
	baitLayout.df<-read.csv(out.filename)
	baits.df<-experimentPlan@iterations[[iterationNumber]]@cabbages.df


	merged.df<-merge(baitLayout.df, baits.df, by.x="Plant.ID", by.y="Id")
	merged.df$Simulation.Egg.Count<-merged.df$Egg.Count
	merged.df$Egg.Count<-NULL
	return (merged.df)
}

AntLibrary.outputBaitMatrix<-function(merged.df) {
	rowCount<-max(merged.df$row)
	colCount<-max(merged.df$col)

	dimnames<-list(1:rowCount, 1:colCount)
	mx<-matrix(0, nrow=rowCount, ncol=colCount, dimnames=dimnames)
	for (irow in 1:rowCount) {
		row<-subset(merged.df, merged.df$row==irow)
		
		for (icol in 1:colCount) {
			cat(row$Simulation.Egg.Count[[icol]])
			if (icol < colCount) {
				cat("\t")
			}
			mx[irow, icol]<-row$Simulation.Egg.Count[[icol]]
		}
		cat ("\n")
	}
	return (mx)
}

AntLibrary.plotSurface<-function(mx, zmax=15, levels=1) {
	x<-1:length(mx[,1])
	y<-1:length(mx[1,])

	filled.contour(x, y,mx,zlim=c(0,zmax),col=topo.colors(zmax), nlevels=zmax/levels, 
		xlab="X",
		ylab="Y",
		plot.axes = {
			axis(1, c(1, 7, 14))
			axis(2, c(1, 7, 14))
		}
	)
	
}