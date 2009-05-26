source("~/Work/code/bugsim/resource/R/Bugsim R Library.r", echo=FALSE)

#You CANNOT include a value of zero in the dataset because then the data is not normally distributed (normal distribution tend to infinitely small numbers, but never zero)
#which means you cannot calculate a linear model properly so you need to start somewhere away from zero.
#
x<-seq(from=0, to=50, by=5)
y.1<-(x)
y.2<-50-(x)
y.3<-c(30, 2, 23, 4, 35, 2, 23, 14, 15, 34, 12)
y.4<-rep(10, length(x))


lm.1<-lm(y.1~x)
lm.2<-lm(y.2~x)
lm.3<-lm(y.3~x)
lm.4<-lm(y.4~x)


plot(y.1~x, ylim=c(0, max(y.2)), main="Example of lm ", cex=NA, ylab="y")

abline(lm.1, col="grey")
abline(lm.2, col="grey")
abline(lm.3, col="grey", lty=2)

points(y.2~x, pch=19,col="blue", cex=.8)
points(y.1~x, pch=19,col="green", cex=.8)
points(y.3~x, pch=19,col="grey", cex=.8)
points(y.4~x)

ex.y.1 <- expression(y.1==.25*x +10)
ex.y.2 <- expression(y.2==.5*x +10)
ex.y.3 <- "y.3=Random()"
ex.y.4 <- "y.4=10"

legend<-c(ex.y.2, ex.y.1, ex.y.3, ex.y.4)
legend(x="topright", inset=c(.35, 0.05), pch=c(19, 19), col=c("blue", "green", "red"), legend=legend, cex=.9)

summary(lm.1)
summary(lm.2)
summary(lm.3)
summary(lm.4)

summary.aov(lm.2)


#In the formula context, + and - are not arithmetic operators but represent whether a variable should be included in the model or not
f<-formula(y.1~I(x*x)-1)

#See if there is a significant difference between the slopes of y.1 and y.2
slope.1.expected<-lm.1$coef[[2]]
slope.2.observed<-lm.2$coef[[2]]
summary.lm.2<-summary(lm.2)
stderr.slope.observed<-summary.lm.2$coefficients[[2,2]]
observed.degf<-lm.2$df.residual

abs.t<-abs((slope.1.expected-slope.2.observed)/stderr.slope.observed)
p.1.tail.t<-pt(abs.t, observed.degf, lower.tail=FALSE) # get the t value from the t distribution for 1 
p.2.tail.t<-p.1.tail.t*2 #calculate for two tail t distribution

#Null hypothesis is that the SLOPES are the same, therefore p.value of 1 supports the null hypothesis.
#low p-value significantly REJECT the null hypothesis
p.2.tail.t




#------------------------------------------------------------------------------------------------------
#CROSS CHECK: See if there is a significant difference between the slopes of y.1 and y.1
summary.lm.1<-summary(lm.1)
slope.1.expected<-lm.1$coef[[2]]
slope.2.observed<-lm.1$coef[[2]]
stderr.slope.observed<-summary.lm.1$coefficients[[2,2]]

abs.t<-abs((slope.1.expected-slope.2.observed)/stderr.slope.observed)

observed.degf<-lm.1$df.residual
p.1.tail.t<-pt(abs.t, observed.degf, lower.tail=FALSE) # get the t value from the t distribution for 1 
p.2.tail.t<-p.1.tail.t*2 #calculate for two tail t distribution

#Null hypothesis is that the SLOPES are the same, therefore p.value of 1 supports the null hypothesis.
#low p-value significantly REJECT the null hypothesis
p.2.tail.t





#Old example from Experiment 1c

slope.1.expected<-lm.sim.1m$coef[[1]]
slope.2.observed<-lm.fld.1m$coef[[1]]
observed.degf<-lm.fld.1m$df.residual
summary.lm<-summary(lm.fld.1m)
stderr.slope.observed<-summary.lm$coefficients[[2,1]]


abs.t<-abs((slope.1.expected-slope.2.observed)/stderr.slope.observed)


p.1.tail.t<-pt(abs.t, observed.degf, lower.tail=FALSE) # get the t value from the t distribution for 1 
p.2.tail.t<-p.1.tail.t*2 #calculate for two tail t distribution

#Null hypothesis is that the SLOPES are the same, therefore p.value of 1 supports the null hypothesis.
#low p-value significantly REJECT the null hypothesis
p.2.tail.t