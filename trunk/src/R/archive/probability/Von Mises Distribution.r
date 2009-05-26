source("~/Work/code/bugsim/src/R/archive/probability/Von Mises Distribution Library.r", echo=FALSE)
library("CircStats")


#Generate a random vmise walk...
N<-100
k<-10
mu<-0
steps<-100
stepLength<-10
startX<-100;startY<-100

result<-vmises.generateRandomPath(steps,stepLength, N, k, mu, startX, startY) 

path<-result[[1]]
azimuths<-result[[2]]
x<-coord.extractX(path)
y<-coord.extractY(path)

min<-min(min(x), min(y))
max<-max(max(x), max(y))
xlim<-c(min, max)
ylim<-c(min, max)

plot(y~x, xlim=xlim, ylim=ylim, type="l")
hist(as.array(azimuths))

azimuths.


#########

#Old stuff

quartz(width=8, height=8)

x<-seq(from=0, to=10, by=.1)
y<-vmises.besselI0(x)

par(mar=c(7, 7,4, 4))
title<-expression(paste("Plot of Bessel Function ", I[0](K)))
plot(y~x, log="y", type="l", main=title, xlab=expression(K), ylab=expression(paste("Log of ", I[0](K))))

vmises.plotDensityFunction(700, 20)

x<-seq(from=-180, to=180, by=.1)
vmisesd<-vmises.densityFXDegrees(x, k=700, m=0)
plot(vmisesd~x, type="l")

#From Batshclett table E - the value should be 
expected<-c(.13889)
x<-50
mu<-180
k<-0.4
vmises.densityFXDegrees(x, k, mu)
dvm(toRadians(x), mu, k) 
#The CircStats function uses "ExponentialScaling" to aviod overflow so returns exp(-x)*I(x, nu) 
kappa<-4
vms<-vmises.besselI0(kappa)
#From batshclett tables - value = 11.30192
expected<-11.30192
expected==vms
sqrt((expected-vms)^2)

#Test IBessel function
#FROM BATSCHSLETT - TABLE OF I0 Bessel Values (Appendix Table F - pp 332)
#as batschlett only gives accuracy to 5 decimals we compare with delta 1-e05
z<-c(0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0)
expected.besselI0<-c(1.00000, 1.00250, 1.01003, 1.02263, 1.04040, 1.06348, 1.09204, 1.12630, 1.16652, 1.21299, 1.26607, 1.64672, 2.27959, 3.28984, 4.88079, 7.37820, 11.30192, 17.48117, 27.23987)
actual.circStatsBesselI0<-besselI(x = z, nu = 0, expon.scaled = FALSE)
actual.bugsimStatsBesselI0<-vmises.besselI0(z)
delta<-1e-05

arraysEqual(expected.besselI0, actual.circStatsBesselI0, delta)
#If you want to know more detail...
#precisionEquals(expected.besselI0, actual.circStatsBesselI0, delta)


arraysEqual(expected.besselI0, actual.bugsimStatsBesselI0, delta)
#If you want to know more detail...
#precisionEquals(expected.besselI0, actual.circStatsBesselI0, delta)



t1<-c(1, 2.000009, 3.00001)
t2<-c(1, 2.000009, 3.0000)
t3<-c(1, 2, 3)
arraysEqual(t1, t2, delta)
arraysEqual(t1, t3, delta)
arraysEqual(t2, t3, delta)

precisionEquals(t1, t2, delta)
arraysEqual<-function(expected, actual, delta) {
	for (i in 1:length(expected)) {
		e<-precisionEquals(expected[[i]], actual[[i]], delta)
		if (e==FALSE) {
			return (FALSE)
		}
	}
	return (TRUE)
}

precisionEquals<-function(x1, x2, delta) {
	equal<- (sqrt((x1-x2)^2) <= delta)
		return (equal)
}

besselI(x = kappa, nu = 0, expon.scaled = FALSE)
besselI(x = kappa, nu = 0, expon.scaled = TRUE)
log(circBessel)^kappa
vmises.besselI0(kappa)
exp(-kappa)*vmises.besselI0(kappa)

#Batchslett calls VMise the Circular Normal Distribution (p281)

#Cant seem to get exactly the same numbers as in table E (p322) but at least ours aggrees with the CircStats one
#
k<-3.8
phi<-seq(from=0, to=360, by=5)
y<-dvm(toRadians(phi), pi, k=k)
y1<-vmises.densityFXDegrees(phi, toDegrees(pi), k=k)
plot(y~phi, type="l", col="blue")
points(y1~phi, type="p", pch=19, col="red", cex=.3)


pdist<-prob.createDistribution(y1)
cumulative<-prob.createCumulativeDistribution(pdist)
plot(cumulative~phi, type="l")



#Conversion between angular deviation and concentration parameter...
x<-seq(from=-pi, to=pi, by=0.01)
sd<-toRadians(10)
kappa<-35.1
y1<-c()
for (i in x) {
	e<-dwrpnorm(i, mu=0, sd=sd)
	y1<-c(y1, e)
}
y2<-dvm(x, 0, k=kappa)#From table D p 320
#y2<-dwrpcauchy(x, 0, .94)
plot(y1~x, type="l")
points(y2~x, pch=19, cex=.3, col="blue")





dvm(0, pi, k=.4)
vmises.densityFXDegrees(5, .4, 180)

x<-seq(from=-pi, to=pi, by=0.01)
y<-dvm(x, 0, 700) #From Circstats library
plot(y~x, type="l")
y1<-dwrpcauchy(x, 0, 0.9)
y2<-dwrpcauchy(x, 0, 0.8)
y3<-dwrpcauchy(x, 0, 0.5)
y4<-dwrpcauchy(x, 0, 0)
plot(y1~x, type="l", ylim=c(0, 4))
lines(y2~x)
lines(y3~x)
lines(y4~x)

test(56)
test<-function(x) {
	jim<-x
	jim
}
(-2)^2/2

#By returning values modulo 360 you always get an azimuth...
0 %% 360
361 %% 360
720 %% 360
y<-c()
for (i in x) {
	e<-dwrpnorm(i, mu=0, sd=pi/100)
	y<-c(y, e)
}
plot(y~x, type="l")



#Rose diagram from circ stats...
data <- runif(500, 0, 2*pi) 
rose.diag(data, bins = 18, main = 'Uniform Data') 
rose.diag(data, bins = 18, main = 'Stacked Points', pts=TRUE) 
# Generate von Mises data and create several rose diagrams. 
data <- rvm(25, 0, 5) 
rose.diag(data, bins=18, pts=TRUE) # Points fall out of bounds. 
rose.diag(data, bins=36, prop=1.5, pts=TRUE, shrink=1.5) 


#From Bugsim:
y1<-c(0.03241403640986161,0.03228636511256681,0.031906857461700945,0.03128585420222639,0.030440010373188768,0.02939147258376875,0.028166815319249822,0.026795813334277524,0.02531013508088807,0.023742042044359927,0.022123171430311988,0.02048346626483679,0.01885029957524951,0.017247820080873593,0.015696527836874093,0.01421307131162043,0.01281024371974771,0.01149714679397687,0.010279484723948473,0.009159949420400427,0.00813865994272319,0.0072136230304447645,0.006381187322085865,0.005636470200676883,0.004973742578229086,0.004386762798370492,0.003869055856127374,0.003414138133269352,0.003015690791988376,0.00266768692624361,0.0023644786715008503,0.0021008508853017304,0.0018720479057023774,0.0016737794336285864,0.0015022109079394093,0.001353942960479902,0.0012259837366044917,0.001115717102737347,0.0010208690727883352,9.394741886143679E-4,8.698430923335269E-4,8.105321274897995E-4,7.603154936813598E-4,7.181602442554176E-4,6.832042469499832E-4,6.54737111055624E-4,6.321840108870977E-4,6.150922945807856E-4,6.031207514643615E-4,5.960314139443692E-4,5.93683785809307E-4,5.960314139443696E-4,6.031207514643623E-4,6.150922945807867E-4,6.321840108870991E-4,6.54737111055626E-4,6.832042469499859E-4,7.181602442554207E-4,7.603154936813637E-4,8.10532127489804E-4,8.698430923335322E-4,9.394741886143739E-4,0.0010208690727883424,0.0011157171027373555,0.0012259837366045013,0.001353942960479912,0.0015022109079394197,0.0016737794336285972,0.0018720479057023885,0.002100850885301743,0.002364478671500862,0.0026676869262436216,0.0030156907919883865,0.003414138133269361,0.00386905585612738,0.004386762798370495,0.004973742578229086,0.005636470200676879,0.006381187322085854,0.007213623030444745,0.008138659942723163,0.00915994942040039,0.010279484723948422,0.011497146793976804,0.012810243719747634,0.014213071311620334,0.015696527836873985,0.01724782008087347,0.01885029957524938,0.020483466264836648,0.022123171430311842,0.023742042044359774,0.025310135080887922,0.026795813334277368,0.028166815319249673,0.02939147258376862,0.030440010373188657,0.03128585420222629,0.031906857461700876,0.03228636511256677)
plot(y1)

y2<-c(0.03241403640986161,0.06470040152242842,0.09660725898412936,0.12789311318635577,0.15833312355954454,0.1877245961433133,0.21589141146256313,0.24268722479684066,0.2679973598777287,0.29173940192208864,0.31386257335240064,0.3343460396172374,0.35319633919248694,0.37044415927336055,0.38614068711023464,0.40035375842185505,0.41316400214160276,0.4246611489355796,0.43494063365952806,0.4441005830799285,0.45223924302265167,0.45945286605309643,0.4658340533751823,0.47147052357585917,0.47644426615408825,0.4808310289524587,0.4847000848085861,0.48811422294185547,0.49112991373384385,0.49379760066008743,0.4961620793315883,0.49826293021689,0.5001349781225923,0.5018087575562209,0.5033109684641603,0.5046649114246402,0.5058908951612447,0.5070066122639821,0.5080274813367704,0.5089669555253848,0.5098367986177184,0.5106473307452082,0.5114076462388896,0.512125806483145,0.512809010730095,0.5134637478411506,0.5140959318520376,0.5147110241466184,0.5153141448980828,0.5159101763120272,0.5165038600978364,0.5170998915117808,0.5177030122632452,0.518318104557826,0.5189502885687131,0.5196050256797687,0.5202882299267187,0.5210063901709742,0.5217667056646556,0.5225772377921454,0.5234470808844789,0.5243865550730933,0.5254074241458816,0.526523141248619,0.5277491249852235,0.5291030679457034,0.5306052788536428,0.5322790582872714,0.5341511061929738,0.5362519570782756,0.5386164357497765,0.5412841226760201,0.5442998134680085,0.5477139516012779,0.5515830074574053,0.5559697702557758,0.5609435128340049,0.5665799830346818,0.5729611703567676,0.5801747933872123,0.5883134533299355,0.5974734027503359,0.6077528874742844,0.6192500342682612,0.6320602779880088,0.6462733492996291,0.6619698771365031,0.6792176972173766,0.698067996792626,0.7185514630574626,0.7406746344877745,0.7644166765321343,0.7897268116130223,0.8165226249472997,0.8446894402665494,0.874080912850318,0.9045209232235066,0.9358067774257329,0.9677136348874338,1.0000000000000004)
plot(y2)
y2[100]

y3<-c(338.39999999999964,327.5999999999997,21.6,14.400000000000002,28.799999999999997,7.200000000000001,349.1999999999996,338.39999999999964,313.19999999999976,287.9999999999999,3.6000000000000005,53.999999999999986,7.200000000000001,331.19999999999965,309.59999999999974,115.20000000000006,331.19999999999965,10.8,50.399999999999984,356.3999999999995,35.99999999999999,0.0,21.6,338.39999999999964,345.59999999999957,3.6000000000000005,295.1999999999999,331.19999999999965,46.79999999999999,39.599999999999994,25.2,338.39999999999964,298.79999999999984,43.19999999999999,305.9999999999998,226.8000000000002,331.19999999999965,68.4,338.39999999999964,320.39999999999975,14.400000000000002,28.799999999999997,35.99999999999999,237.6000000000002,75.60000000000002,25.2,53.999999999999986,341.99999999999955,295.1999999999999,21.6,356.3999999999995,21.6,331.19999999999965,241.20000000000016,57.599999999999994,334.7999999999996,316.7999999999997,79.20000000000002,291.5999999999999,345.59999999999957,345.59999999999957,334.7999999999996,323.9999999999997,331.19999999999965,352.7999999999995,356.3999999999995,32.39999999999999,7.200000000000001,21.6,28.799999999999997,341.99999999999955,334.7999999999996,349.1999999999996,57.599999999999994,305.9999999999998,93.60000000000004,10.8,25.2,14.400000000000002,53.999999999999986,21.6,338.39999999999964,320.39999999999975,334.7999999999996,0.0,14.400000000000002,338.39999999999964,35.99999999999999,331.19999999999965,356.3999999999995,39.599999999999994,61.199999999999996,273.59999999999997,356.3999999999995,316.7999999999997,14.400000000000002,43.19999999999999,320.39999999999975,18.0,320.39999999999975)
hist(y3)