source("~/Work/code/bugsim/resource/R/Experiment 2/Sensory Signal Model Library.r", echo=FALSE)


#Normal distribution:
x<-seq(from=-180, to=180,by=1)
y<-dnorm(x, 0,90)
plot(y~x, type="l")

y2<-calculatedDNorm(x, 0,90)
plot(y2~x, type="l")
points(y~x, cex=.1, col="blue")
calculatedDNorm<-function(x, mean, sd) {
	firstPart<- 1/(sd*sqrt(2*pi))
	exponent<-((x-mean)^2)/(2*sd^2)
	secondPart<-exp(-exponent)
	return (firstPart*secondPart)
}

#Contains some graphs used to demonstrate how our sensory model works

S.values.left<-rnorm(-90, 20, n=1000)
S.values.right<-rnorm(90, 20, n=1500)
S.values.all<-c(S.values.left, S.values.right)

quartz(width=8, height=5)
par(mar=c(7, 7, 2, 2))
stheta<-expression(S[theta*i])
S.values.hist<-hist(S.values.all, breaks=seq(from=-180, to=180, by=22.5),xlab=NA, xaxs="i", freq=FALSE,  xlim=c(-180 , 180), xaxt="n", yaxt="n", main="Input Signal", ylab="S value", cex.lab=1.5, cex.axis=1.2)
axis(side=1, at=seq(from=-180, to=180, by=180), cex.axis=1.5, mgp=c(3,1, 1))
mtext(side=1, line=4,stheta, cex=1.5)


outputDirectory<-"~/Documents/Projects/bugsim/documentation/Experiment Notes/Experiment 2/Figures"

pdf(file=sprintf("%s/S-input-histogram.pdf", outputDirectory, beta), width=8, height=5, bg="white")
par(mar=c(7, 7, 2, 2))
S.values.hist<-hist(S.values.all, breaks=seq(from=-180, to=180, by=22.5),xlab=NA, xaxs="i", freq=FALSE,  xlim=c(-180 , 180), xaxt="n", yaxt="n", main="Input Signal", ylab="S value", cex.lab=1.5, cex.axis=1.2)
axis(side=1, at=seq(from=-180, to=180, by=180), cex.axis=1.5, mgp=c(3,1, 1))
mtext(side=1, line=4, stheta, cex=1.5)
dev.off()

outputGraphs(outputDirectory, 0, expression(beta==0))
outputGraphs(outputDirectory, 100, expression(beta==100))
outputGraphs(outputDirectory, 500, expression(beta==500))
outputGraphs(outputDirectory, 1000, expression(beta==1000))

outputBetaGraphs(outputDirectory)

# this gives us a decreasing number from 1 to 0 over time which can be controlled by a constant gamma
# to determine the curve shape. Small gamma (0.05) produces shallower curve,i.e. takes longer to reach 0
betafunctionA<-function(t, gamma) {
	return (exp(-gamma*t))
}
time<-as.array(seq(from=0, to=500, by=1))
betaA<-apply(time,1, betafunctionA, gamma=.05)
plot(betaA~time, type="l")


# This simply inverts it so that it rises to 1 over time...
betafunctionB<-function(t, gamma) {
	return (1-exp(-gamma*t))
}
betaB<-apply(time,1, betafunctionB, gamma=.05)
plot(betaB~time, type="l")


#And this is the final function where we include MaxBeta
betafunctionC<-function(t, gamma, maxBeta) {
	return (maxBeta*(1-exp(-gamma*t)))
}
betaC<-apply(time,1, betafunctionC, gamma=.05, maxBeta=200)
plot(betaC~time, type="l")

#This one does it by iteration
betafunctionD<-function(timesteps, gamma, maxBeta) {
	beta<-rep(0, timesteps+1)
	for (t in 1:timesteps+1) {
		betaPrevious<-beta[[t-1]]
		deltaBeta<-gamma*(maxBeta-betaPrevious)
		beta[[t]]<-betaPrevious+deltaBeta
	}
	return (beta)
}
betaD<-betafunctionD(500, gamma=.05, maxBeta=200)
length(betaD)
plot(betaD~time, type="l")
points(betaC~time,pch=19 ,col="blue", cex=0.2)



startX<-.001 #Actually equal to N/K in terms of population size / carrying capacity.
gamma<-0.5
maxY<-1
t<-seq(from=1,to=30,by=.1)
y<-testExp(startX, t, gamma, maxY)
plot(y~t, type="l")
testExp<-function(startX, x, gamma, maxY) {
	y<- 1 / ( (1 + ( 1/startX ) * exp(-gamma*x)) )
	return (y*maxY)
}

gamma<-.1
maxAlpha<-10
L<-seq(from=0, to=10, by=.1)
alpha<-luminosityResponse(L, gamma, maxAlpha)
plot(alpha~L, type="l", ylim=c(0, maxAlpha))
luminosityResponse<-function(L, gamma, maxAlpha){
	y<-1+((maxAlpha-1)*exp(-gamma*L))
}


#Maths A Level...
#
(2*(6+9))-(3*(2-21))

(3*2)+(21*3)
(3*2)-(15*3)

testA<-function(a, b) {
	return ( (2 * ( (3*a)+(3*b) ) ) - ( 3 * ( a - (7 * b) ) ) )
}
testA(2, 3)

testB<-function(a, b) {
	return ( ((6*a)+(6*b)) - ((3*a)-(21*b)) )
}
testB(2, 3)

testC<-function(a, b) {
	return ( (3*a) + (27*b) )
}
testC(2, 3)

test3dA<-function(c) {
	return ( 16- ( 3 * ((4*c) - 3) ) )
}
test3dA(3)

test3dB<-function(c) {
	return ( (-12*c) +25 )
}
test3dB(3)

( (4 * - sqrt(82)) + (3*-sqrt(32)) ) / (4 * -sqrt(32)) 

( (4 *  sqrt(82)) + (3*sqrt(32)) ) / (4 * sqrt(32)) 

( (4 *  -sqrt(82)) + (3*sqrt(32)) ) / (4 * sqrt(32)) 

( (4 *  sqrt(82)) + (3*-sqrt(32)) ) / (4 * sqrt(32)) 

(3+sqrt(41))/4

(3-sqrt(41))/4

x<- (63-19)/11
y<- (2*x) -9
2*x-y
(3*x)-(7*y)
44/11

3*56


LHS<-function(x){
	exp1 <- (3*x)*( (5*x) - 7)
	exp2 <- -x*((4*x)-5)
	return (exp1 + exp2)
}
LHS2<-function(x){
	exp1 <- (3*x)*( (5*x) - 7)
	exp2 <- x*((4*x)-5)
	return (exp1 - exp2)
}

RHS<-function(x) {
	return ((11 * (x^2)) - (26*x))
}
LHS(4)
LHS2(4)
RHS(0)

-16+26

-sqrt(82/32) + (3/4)
sqrt(82/32) + (3/4)

-sqrt(56/32)+(6/4)
sqrt(56/32)+(6/4)

63-19

-21+5