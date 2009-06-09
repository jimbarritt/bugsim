#this file demonstrates using the AIC (Akaike's An Information Criterion) to compare models to see how they fit the data
#


#FIrst a simple linear model....
x<-seq(from=0, to=180, by=5)
y<-(0.5*x) + 10

yx.lm<-lm(y~x)
yx.vs.yx.cubic.2.lm<-lm(y~I(x^2))
yx.vs.yx.cubic.3.lm<-lm(y~I(x^3))
yx.vs.yx.cubic.4.lm<-lm(y~I(x^4))
yx.vs.yx.cubic.5.lm<-lm(y~I(x^5))

summary(yx.lm)
summary(yx.vs.yx.cubic.2.lm)
summary(yx.vs.yx.cubic.3.lm)
summary(yx.vs.yx.cubic.4.lm)
summary(yx.vs.yx.cubic.5.lm)


AIC(yx.lm, yx.vs.yx.cubic.2.lm, yx.vs.yx.cubic.3.lm, yx.vs.yx.cubic.4.lm, yx.vs.yx.cubic.5.lm)


plot(y~x, pch=NA, col="blue")
abline(yx.lm, col="grey")
points(y~x, pch=19, col="blue")

AIC(yx.lm)



#exponential line: x^5
y.exp<-x^5
yx.exp.lm<-lm(y.exp~I(x^5))
summary(yx.exp.lm)
plot(y.exp~x, pch=NA, col="blue")
y.exp.fitted<-fitted(yx.exp.lm)
lines(y.exp.fitted~x, col="grey")
points(y.exp~x, pch=19, col="blue")

#Quadratic
y.quad<-(180*x)-0.5*(x^2)
yx.quad.lm<-lm(y.quad~x+I(x^2))
summary(yx.quad.lm)
plot(y.quad~x, pch=NA, col="blue", axes=FALSE)
axis(1, at=seq(from=0, to=max(x), by=max(x)/4))
axis(2)
y.quad.fitted<-fitted(yx.quad.lm)
lines(y.quad.fitted~x, col="grey")
points(y.quad~x, pch=19, col="blue")






anova(yx.lm, yx.vs.yx.cubic.5.lm)

