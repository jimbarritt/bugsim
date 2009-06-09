cat("Bray-Curtis Library (v 1.0)\n")

lib.loadLibrary("thesis/common/dissimilarity/BrayCurtisIndexClass.r")


brayCurtis.test<-function(y1, y2) {
	input.mx<-rbind(y1, y2)

	mins<-apply(input.mx, 2,min)
	fracTop<-2*sum(mins)
	fracBottom<-sum(y1) + sum(y2)
	index<- 1-(fracTop/fracBottom)
	return (BrayCurtisIndex(index))
}