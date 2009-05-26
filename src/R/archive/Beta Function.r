#Draws nice graphs of the beta probability and distribution

quartz(width=8.25, height=4.125)
par(mfrow=c(1,2))

#Probabilities
x <- seq(0, 1, length=40)

alpha.1<-1.2
beta.1<-1.2
alpha.2<-1.25
beta.2<-1.25
alpha.3<-1.3
beta.3<-1.3

y <- pbeta(x, alpha.1, beta.1)
y2 <- pbeta(x, alpha.2, beta.2)
y3 <- pbeta(x, alpha.3, beta.3)

plot (80*x, y3, xlab="d", type="l",ylab="count",main="beta probability")
lines(80*x, y, col="blue")
lines(80*x, y2, col="green")

y <- dbeta(x, alpha.1, beta.1)
y2 <- dbeta(x, alpha.2, beta.2)
y3 <- dbeta(x, alpha.3, beta.3)

plot (80*x, y3, xlab="d", type="l",ylab="count",main="beta distribution")
lines(80*x, y, col="blue")
lines(80*x, y2, col="green")
