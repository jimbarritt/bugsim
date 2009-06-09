source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("thesis/common/dissimilarity/bray-curtis library.r")

#Should be quite different
y1<-c(0, 4, 6, 7, 2, 3, 0, 2, 1, 9)
y2<-c(2, 0, 1, 2, 8, 9, 7, 9, 9, 0)

brayCurtis.test(y1, y2)


#Should be 0 - exactly the same
y1<-c(0, 4, 6, 7, 2, 3, 0, 2, 1, 9)
y2<-c(0, 4, 6, 7, 2, 3, 0, 2, 1, 9)

brayCurtis.test(y1, y2)

#Should be 1 - can't be any more disimilar
y1<-c(0, 4, 6, 7, 0, 3, 0, 2, 0, 9)
y2<-c(2, 0, 0, 0, 8, 0, 7, 0, 9, 0)

brayCurtis.test(y1, y2)



