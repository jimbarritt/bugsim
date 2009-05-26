# Stuff to do with working out intersections:
#Page 83 of Elementary Linear Algebra by Howard Anton (4th Ed)

f1<-function(x) {
	return (3*x+1)
}

f2<-function(x) {
	return (-3*x+30)
}


x<-1:10
y1<-f1(x)
y2<-f2(x)

par(mar=c(5, 5, 2, 2))
plot(y1~x, type="l", xlab="X", ylab="Y",lwd=2, col="darkgrey", xlim=c(0, 10), ylim=c(0,30))
lines(y2~x, col="lightblue", lwd=2)

#We want to know where the intersection is!

#Can solve a system of simulataneous equations using Cramers' Rule:
# As long as matrices of AX = B
#Need to re-arrange our formulae so 

# 3*x-y = -1
# -3*x-y = -30

f2(2)
-3*2-24

#Then put the values into matrices....
A<-matrix(c( c(3, -1), c(-3, -1 )), nrow=2, ncol=2, byrow=T)
detA<-det(A)
if (detA==0) {
	cat("Lines Do Not Intersect\n")
} else {
	cat("Lines intersect somehwere.")
}

#ALL lines intersect unless they are absolutely parallel, the question is, WHERE do they intersect...
#Now replace entries in each column by those in B (the values -1, -30)
A1<-matrix(c( c(-1, -1), c(-30, -1 )), nrow=2, ncol=2, byrow=T)
A2<-matrix(c( c(3, -1), c(-3, -30 )), nrow=2, ncol=2, byrow=T)

#Now apply cramers rule which is:
#  x1=det(A1) / det(A),  x2=det(A2) / det(A)

x1<-det(A1)/detA
x2<-det(A2)/detA

#and our coordinates should be
xI<-x1
yI<-x2

points(xI, yI, cex=2, col="red")

#Now what we want this for is to draw a nice line to X axis, so:
lines(x=c(xI, xI), y=c(-1, yI),lty=2 )



