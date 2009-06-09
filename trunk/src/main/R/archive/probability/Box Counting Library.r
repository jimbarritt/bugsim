cat("Fractal Box Counting Library (v1.0)\n")

bcount.drawBox<-function(x, y, width, height, col="blue"){
	lines(c(x, x), c(y, y+height), col=col)
	lines(c(x+width, x+width), c(y, y+height), col=col)
	lines(c(x, x+ width), c(y+height, y+height), col=col)
	lines(c(x, x+ width), c(y, y), col=col)
}

#Calculate Fractal Box Counting Dimension:
bcount.calculateD<-function(coords, plotGraphs=TRUE, minK, maxK=NA) {
	x<-coord.extractX(coords)
	y<-coord.extractY(coords)
	return (bcount.calculateDXY(x, y, plotGraphs,minK, maxK))
}
bcount.calculateDXY<-function(x, y, plotGraphs=TRUE, minK, maxK=NA) {
	
	width<-max(x)-min(x)
	height<-max(y)-min(y)
	size<-max(width,height)
	return (bcount.calculateDXYScaled(x, y, plotGraphs, size, minK, maxK))
}

bcount.calculateDXYScaled<-function(x, y, plotGraphs=TRUE, size, minK, maxK=NA) {

	if (is.na(maxK)) {
		kmax<-1
		sTest<-1
		while (sTest<size) {
			sTest<-2^kmax
			kmax<-kmax+1	
		}
		kmax<-kmax-1
	} else {
		kmax<-maxK
	}

	
	sMax<-2^kmax
	sMin<-2

	xt<-x-min(x)
	yt<-y-min(y)

	if (plotGraphs) {
		quartz(width=8, height=8)
		plot(yt~xt, pch=NA,xlim=c(0, sMax), ylim=c(0, sMax))
		points(yt~xt, cex=.8)
		bcount.drawBox(0, 0, sMax, sMax)		
	}

	path<-coord.combineXY(xt, yt)
	NValues<-c()
	SValues<-c()
	for (k in minK:kmax) {		
		s<-2^k #Size of the box!
		cat("Counting k=", k, "\n")
		Nk<-bcount.countOccupiedBoxes(path, s, sMax, plotGraphs)

		NValues<-c(NValues, Nk)
		SValues<-c(SValues, s)
	}

	lnSValues<-log(1/SValues)
	lnNValues<-log(NValues)
	lmResult<-lm(lnNValues~lnSValues)
	
	if (plotGraphs==TRUE) {
		quartz(width=8, height=8)
		plot(lnNValues~lnSValues,type="l")
		points(lnNValues~lnSValues)
		points(lmResult$residuals, col="green")
	}

	return (lmResult$coefficients[[2]])
}
bcount.countOccupiedBoxes<-function(path, s, sMax, drawBoxes=FALSE) {
	boxCount<-sMax/s

	N<-0
	for (ix in 1:boxCount) {
		for (iy in 1:boxCount) {
			xb<-s*(ix-1)
			yb<-s*(iy-1)
			box<-c(xb, yb, s, s)
			#bcount.drawBox(xb, yb, s, s, "grey")
			#This is going to be the killer - how do we decide efficiently the number of points in the box?
			if (bcount.isBoxOccupied(path, box)==TRUE) {
				N<-N+1
				if (drawBoxes==TRUE) {
					bcount.drawBox(xb, yb, s, s, "red")
				}
			}
		}
	}
	return(N)
}

#This one is real stupid....
#"In" the box means inside or on the left, bottom edges. if its on the right or top edge it is not in the box.
#this means that points on the top right edge of the square will get missed out so maybe we always need to 
#make the box bigger than the area under scrutiny? trouble is that it doubles in size each time...
#Just add a rule in that if its on the top or left edge, include these - we just care how many
#are occupied, not which ones.
#debug(bcount.isBoxOccupied)
bcount.isBoxOccupied<-function(path, box) {
	isBoxOccupied<-FALSE
	for (coord in path) {
		if (bcount.inBox(coord, box)==TRUE) {
			isBoxOccupied<-TRUE
			break
		}
	}
	return (isBoxOccupied)
}

bcount.inBox<-function(coord, box)  {
	px<-coord[1]
	py<-coord[2]
	bx<-box[1]
	by<-box[2]
	bw<-box[3]
	bh<-box[4]
	
	inBox<-FALSE
	if (px>=bx && px<bx+bw && py>=by && py<by+bh) {
		inBox<-TRUE
	}
	return (inBox)
}






