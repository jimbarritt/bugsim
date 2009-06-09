#This library generates layouts for you that can then be imported into bugsim.

cat("Resource Layout Library v(1.0)\n")





#Patch size is the number of resources on each side...
#Patch size is the size of the square
ResourceLayout.generateLayout<-function(fileName, patchSize, squareSize) {
	output.df<-ResourceLayout.createLayout(patchSize, squareSize, 0, 0)
	write.csv(output.df, file=out.filename, row.names=FALSE)
}

Resourcelayout.calculateSeparationFromPatchSize<-function(patchSize, radius, countX) {
	totalS<-patchSize/countX
	S<-totalS-(radius*2)
	return (S)
}
#patchSize<-80;squareCount<-4;originX<-0;originY<-0
Resourcelayout.createLayout<-function(patchSize, squareCount, originX, originY, edges=T) {
	separation<-patchSize/squareCount



	startX<-(separation/2)+ originX
	startY<-(separation/2) + originY
	currX<-startX
	currY<-startY	
	numResources<-squareCount*squareCount
	id<-1:(numResources)
	row<-c()
	col<-c()
	x<-c()
	y<-c()
	for (iX in 1:squareCount) {
		currX<-startX + separation*(iX-1)
		for (iY in 1:squareCount) {
			currY<-startY + separation * (iY-1)
			x<-c(x, currX)
			y<-c(y, currY)
			row<-c(row, iX)
			col<-c(col, iY)
		}	
	}
	output.df<-data.frame("Plant.ID"=id)
	output.df$x<-x
	output.df$y<-y
	output.df$row<-row
	output.df$col<-col
	
	if (!edges) {
		ss.df<-subset(output.df, output.df$Plant.ID%in%c(1, 4, 6, 7, 10, 11, 13, 16))
		output.df<-ss.df
	}
	return (output.df)
}