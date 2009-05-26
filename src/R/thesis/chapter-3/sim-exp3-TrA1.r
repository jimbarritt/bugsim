
###########################################################################################
#
# Trial   : A1
#
# Purpose : To test the initial Azimuth generator to make sure it is uniform random
#
source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")
lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")
lib.loadLibrary("/thesis/chapter-3/model/ForagerSummaryClass.r")
lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")

library(CircStats)
lib.loadLibrary("/thesis/common/circular/CircStats Extensions.r")

lib.loadLibrary("/common/latex/Latex Library.r")
lib.loadLibrary("/thesis/common/output/StandardReportClass.r")
lib.loadLibrary("/thesis/chapter-3/report/Circular Stats Report Library.r")
lib.loadLibrary("/thesis/chapter-3/report/SimExp3TrA1ReportClass.r")

#chapter="raw" or "raw-download" or the number of the chapter...
experimentPlan<-ExperimentData.loadPlan("3", "sim", "exp3", "TrA1", 1)

report<-SimExp3A1Report(experimentPlan, breakSize=10, ksMAXN<-3000)
report<-generateReport(report, TRUE, TRUE)






















########################################################################################################
#
# Workspace: how we work out stuff for the report...

test.N<-1000
knownMu<-NA
knownK<-NA

azimuths<-foragerSummary@summary.df$InitialAzimuth
x.radians<-toRadians(azimuths)
if (length(x.radians)>test.N) {
	x.radians.test<-sample(x.radians, test.N)
} else {
	x.radians.test<-azimuths
}

summary<-circ.summary(x.radians)
sprintf("%0.4f", toDegrees(summary$mean.dir))
sprintf("%0.4f", summary$rho)	


#Estimated k value from von mises distribution
est.k<-vm.ml(x.radians)

est.k$mu
est.k$kappa

#Generate a test distribution from this...
vm.radians<-rvm(n=test.N, mean=est.k$mu, k=est.k$kappa)	

#Watson test for uniformity:
result.unif.test<-watson.test(x.radians, alpha=0.01, dist="uniform")

sprintf("%s",result.unif.test$Sig.Level)
sprintf("%0.4f",result.unif.test$Critical.Value)
sprintf("%0.4f",result.unif.test$Test.Statistic)
sprintf("%s",result.unif.test$Summary)
	
#Rayleigh test for uniformity...
result.ray.test<-v0.test(x.radians)

sprintf("%0.4f", result.ray.test$r0.bar)
sprintf("%0.4f", result.ray.test$p.value)

#Watson test for von mises...
result.vm.test<-watson.test(x.radians.test, alpha=0.01, dist="vm")

sprintf("%s",result.vm.test$Sig.Level)
sprintf("%0.4f",result.vm.test$Critical.Value)
sprintf("%0.4f",result.vm.test$Test.Statistic)
sprintf("%s",result.vm.test$Summary)


#Compare it to estimated distribution...
result.estimated.vm.test<-watson.two.test(x.radians.test, vm.radians, alpha=0.01, plot=FALSE)
sprintf("%s",result.estimated.vm.test$Sig.Level)
sprintf("%0.4f",result.estimated.vm.test$Critical.Value)
sprintf("%0.4f",result.estimated.vm.test$Test.Statistic)
sprintf("%s",result.estimated.vm.test$Summary)

#Compare it to known distribution...
if (!is.na(knownMu)) {
	result.known.vm.test<-watson.two.test(x.radians.test, vm.radians, alpha=0.01, plot=FALSE)	
	sprintf("%s",result.known.vm.test$Sig.Level)
	sprintf("%0.4f",result.known.vm.test$Critical.Value)
	sprintf("%0.4f",result.known.vm.test$Test.Statistic)
	sprintf("%s",result.known.vm.test$Summary)
} else {
	result.known.vm.test<-NA
}




########################################################################################################
#
# OLD STUFF
#
itr.1<-getIteration(experimentPlan, 1)
rep.1<-getReplicate(itr.1, 1)

foragerSummary<-ForagerSummary(rep.1, itr.1, 1)


x.radians<-toRadians(foragerSummary@summary.df$InitialAzimuth)

quartz(width=10, height=10)
stats<-CircularReport.plot.distribution(x.radians, maxPlotN=2000)


x.vm.radians<-rvm(10000, mean=0, k=6)
stats<-CircularReport.plot.distribution(x.vm.radians, maxPlotN=2000)

est<-vm.ml(x.vm.radians)

est$mu

theta<-seq(from=0, to=2*pi, by=(2*pi)/360)
r<-sapply(theta, pvm, est$mu, est$kappa)
r<-(r)+1
lines(cos(r), sin(r), col="green")

rose.diag(x.vm.radians, bins=360/20, prop=1.6)







#x.radians<-rvm(1000, mean=0, k=3)

#Rayleigh test for uniformity...
x<-v0.test(x.radians)

str(x)

#Watson test for uniformity and against von mises:
result<-watson.test(x.radians, alpha=0.01, dist="uniform")


unif.radians<-runif(1000, min=0, max=2*pi)
vm.radians<-rvm(1000, mean=0, k=6)

result<-watson.test(unif.radians, alpha=0.01, dist="uniform")

result<-watson.test(vm.radians, alpha=0.01, dist="uniform")

result<-watson.test(unif.radians, alpha=0.01, dist="vm")

result<-watson.test(vm.radians, alpha=0.01, dist="vm")

result<-watson.two.test(unif.radians, vm.radians, alpha=0.01, plot=TRUE)


result<-watson.two.test(vm.radians, vm.radians, alpha=0.01, plot=TRUE)


#What happens when -pi to +pi?

unif.radians.2<-c(rep((-pi*.5)+.00001, 100), rep((pi*.25)+.00001, 100))

unif.radians.2<-unif.radians.2+(pi*.5)
circ.plot(unif.radians.2, stack=TRUE, bins=360, shrink=3, pch=16, cex=.9)
















###########################################################################################
# OLD STUFF
#This is how we worked out how to make the report....
itr.1<-getIteration(experimentPlan, 1)
rep.1<-getReplicate(itr.1, 1)

foragerSummary<-ForagerSummary(rep.1, itr.1, 1)


#foragerSummary@summary.df

breaks<-seq(from=0, to=360, by=10)

par(mfrow=c(2, 1))
initialA.hist<-hist(foragerSummary@summary.df$InitialAzimuth, breaks=breaks, main="", xlab=expression(theta))

#Build expected from uniform distribution
N<-length(foragerSummary@summary.df$InitialAzimuth)
NCOUNTS<-length(initialA.hist$counts)
actual<-initialA.hist$counts
p<-N/NCOUNTS
expected<-rep(p,NCOUNTS)

str(initialA.hist)

bpmx<-rbind(initialA.hist$counts, expected)
barplot(bpmx, beside=TRUE ,col=c("white", "grey"))


plot(initialA.hist, )
max(foragerSummary@summary.df$InitialAzimuth)
min(foragerSummary@summary.df$InitialAzimuth)
boxplot(foragerSummary@summary.df$InitialAzimuth)


uniform.1<-runif(N, min=0, max=360)
uniform.2<-runif(N, min=0, max=360)
normal.1<-rnorm(N, mean=0, sd=20)
normal.2<-rnorm(N, mean=0, sd=20)
min(uniform.1)
max(uniform.1)
runif(N)
test<-as.numeric(foragerSummary@summary.df$InitialAzimuth)
result<-ks.test(test,uniform.1, exact=TRUE)
str(result)
plot(result)


test<-as.numeric(foragerSummary@summary.df$InitialAzimuth[1:10000])
length(test)
NKS<-N
if (NKS>5000) {
	NKS<-5000
}
test<-sample(test, NKS)
ks.test(test,"punif",  0, 360,exact=TRUE)
ks.test(uniform.1,"punif",  0, 360,exact=TRUE)
length(test)
length(uniform.1)

x <- rnorm(50)
y <- runif(30)
# KS Test Do x and y come from the same distribution?
ks.test(x, y)


#ChiSq

#First calculate expected:
sum(initialA.hist$counts)#should be 10,000

lines(expected~initialA.hist$mids, col="blue", lw=2)

chisq.mx<-rbind(actual, expected)
chi.result<-chisq.test(chisq.mx)
class(chi.result)
#Bray Curtis ? well, maybe...
str(chi.result)
