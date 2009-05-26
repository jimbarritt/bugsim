#Various tests for normality:
N<-5000
xNorm<-rnorm(N)
xUnif<-runif(N)

#qqplot
hist(xNorm)
qqnorm(xNorm, cex=.4, col="darkgrey")
qqline(xNorm, col="black")


hist(xUnif)
qqnorm(xUnif, cex=.4, col="darkgrey")
qqline(xUnif, col="black")


#Shapiro-Wilk normality test (limit of betwen 3 and 5000 samples) - gives you a p-value - LOW UNLIKELY THAT ARE NORMAL
shapiro.test(xNorm)
shapiro.test(xUnif)


#Testing skew (Crawley 2005 p 69):
skew<-function(x) {
	m3<-sum((x-mean(x))^3)/length(x)
	s3<-sqrt(var(x))^3
	return (m3/s3)
}

skew(xNorm)
skew(xUnif)

#Do a t-test - divide the skew value by its standard error to produce T statistic
#6 is the approximate variance of the skew - it doesnt say where this comes from but probably because
#there are two estimated parameters each to the power 3.
t.norm<-skew(xNorm)/sqrt(6/length(xNorm))
t.unif<-skew(xNorm)/sqrt(6/length(xNorm))

#what is the probabiltiy of getting a t-value by chance alone:
#degrees of freedom = (number of samples - 1) + (number of variables -1)
#Number of variables in this case is 2 because the skew function estimates 2 parameters, m3 and s3
t.pvalue.norm<-1-pt(t.norm, df=N-1 + 2-1)
t.pvalue.unif<-1-pt(t.unif, df=N-1 + 2-1)

#So there is no significant skew for either distribution

#Kolmogrov-Smirnov test:
# We used the default rnorm which has a mean of 0 and s.d. of 1 (standard normal)
#So test ks agains a pnorm distribution with a mean of 0 and s.d. of 1.. 
#to do this we pass the name of the distribution function in and any parameters it requires
#Gives a p-value of 0.554 for the normal but real low probability for the unif
ks.test(xNorm, "pnorm", 0, 1) 
ks.test(xUnif, "pnorm", 0, 1) 

#and against the uniform the opposite : 
ks.test(xNorm, "punif") 
ks.test(xUnif, "punif") 






