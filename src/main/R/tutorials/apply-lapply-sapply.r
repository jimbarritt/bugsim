#Explore posibility of doing loops using a list /  matrix as input value...

#Some stupid function to use...
writeOut<-function(x) {
	return (sprintf("%003.0f", x))
}


#Create a matrix
x.mx<-rbind(1, 2, 3, 4, 5)
x.list<-as.list(x.mx[,1])

apply(x.mx, MARGIN=1, FUN=writeOut)
lapply(x.list, writeOut)
sapply(x.list, writeOut)
sapply(x.mx, writeOut)

sapply(x.mx, writeOut)

