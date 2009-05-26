#Demos a pretty complex graph!

#mgp=c(3, 1, 0) - the second parameter controls the position of the numbers relative to the tick marks!

quartz(width=10, height=10)

mean<-0
sd<-40

x<-seq(from=-180, to=180,by=1)
y<-dnorm(x, mean,sd)
y2<-dnorm(x, mean+meanThetaMin,sd)
y3<-dnorm(x, mean+meanThetaMax,sd)
maxY<-max(y)
topPos<-maxY+maxY*.05
par(mar=c(7, 7, 5, 5))
plot(y~x,axes=FALSE,pch=NA, ylim=c(0,maxY+ maxY*.1) , main="", yaxs="r", yaxp=c(0, maxY, 3),type="p", xaxt="n", col="blue", cex.axis=1.5,cex.lab=1.5, xlab=NA, ylab=expression(P(theta)))
axis(2, cex.axis=1.5, cex.lab=2)
axis(1, at=seq(from=-180, to=180, by=45), cex.axis=1.5, cex.lab=2)
lines(y2~x, lty=4, col=col.light)
lines(y3~x, lty=4, col=col.light)
lines(y~x, lty=1, col="blue")
lines(x=c(0, 0), y=c(-0.3,topPos), lty=2)
mtext(side=1, line=3, expression(theta), cex=2)
mtext(side=3, line=2, "Complex Graph Demonstration", cex=2)


#Draw the arrows indicating possible changes to the mean....
#lines(x=c(-90, 90), y=c(topPos, topPos))
arrows(0,  topPos, meanThetaMin, topPos)
arrows(0,  topPos, meanThetaMax, topPos)
lines(x=c(meanThetaMin, meanThetaMin), y=c(topPos+maxY*0.025, topPos-maxY*0.025))
lines(x=c(meanThetaMax, meanThetaMax), y=c(topPos+maxY*0.025, topPos-maxY*0.025))

lines(x=c(meanThetaMin, meanThetaMin), y=c(-0.3, topPos-maxY*0.025), lty=3)
lines(x=c(meanThetaMax, meanThetaMax), y=c(-0.3, topPos-maxY*0.025), lty=3)

points(x=0,y=topPos, pch=21, cex=2.4, lwd=1.5)
points(x=0,y=topPos, pch=19, col="red",cex=1.8)

thetaMin<-expression(mu[theta*min])
text(x=meanThetaMin, y=topPos,thetaMin, pos=2, cex = 2)
#points(x=meanThetaMin-24, y=topPos+maxY*0.01, pch=2,cex=2)
thetaMax<-expression(mu[theta*max])
text(x=meanThetaMax, y=topPos,thetaMax, pos=4, cex=2)
#text(x=meanThetaMax+7, y=topPos,thetaMax, pos=4, cex=2)
#points(x=meanThetaMax+5, y=topPos+maxY*0.01, pch=2,cex=2)

text(x=0, y=topPos+maxY*0.06, expression(mu[theta]), cex=2)


#Draw the arrows indicating the s.d. of the distribution...
i<-which(x==sd)
sdPos<-y[i]

rect(-5, sdPos , 5, sdPos+maxY*0.075, col="white", border="white")#Draw a white rectangle to blot out the dotted line
arrows(0, sdPos, sd, sdPos)
arrows(0, sdPos, -sd, sdPos)
text(0, sdPos+ maxY*.01, expression(sigma[theta]), cex=2, pos=3)
