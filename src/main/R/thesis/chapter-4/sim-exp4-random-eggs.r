# Tests the layout with a simple random egg distribution...

source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")

lib.loadLibrary("/common/Graphing Utilities Library.r")
lib.loadLibrary("/common/Collection Manipulation Library.r")
lib.loadLibrary("/common/summarystats/Summary Statistics Library.r")
lib.loadLibrary("/thesis/common/geometry/Geometry Library.r")

lib.loadLibrary("/thesis/common/experiment/Experiment Data Library.r")
lib.loadLibrary("/thesis/chapter-4/model/EggDistributionClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbagesClass.r")
lib.loadLibrary("/thesis/chapter-4/model/FieldCabbageLayoutClass.r")
lib.loadLibrary("/thesis/chapter-4/model/IterationEggDistributionClass.r")
lib.loadLibrary("/thesis/chapter-4/model/RandomEggDistribution.r")

sense.output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/04-sense-scale/resources/figures"

cabbages.ALL.df<-REGG.createUniformEggDistribution(fieldCabbages@eggDistribution@cabbages.df, replicates=20)
dist.random<-EggDistribution("Random", cabbages.ALL.df, "Random.EggCount", fieldCabbages=FieldCabbages.LEVINII)

fieldCabbages@eggDistribution@cabbages.df$Equal.EggCount<-15
dist.equal<-EggDistribution("FromSky", fieldCabbages@eggDistribution@cabbages.df, "Equal.EggCount", fieldCabbages=FieldCabbages.LEVINII)


sense.filename<-sprintf("%s/%s.pdf", sense.output.figures, "random-plant-dist")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=17, height=6)
	REGG.plotEqualAndRandomPlantDistributions(dist.equal, dist.random)
dev.off()

fieldCabbages<-FieldCabbages.LEVINII

fromSkyOutputDir<-"~/Documents/Projects/bugsim/experimental-data/published/chapter-4/simulation-data/sim-exp4-TrB3-001"

if (!file.exists(fromSkyOutputDir)) {
	dir.create(fromSkyOutputDir)
}
sense.sky.figures<-sprintf("%s/output", fromSkyOutputDir)
if (!file.exists(sense.sky.figures)) {
	dir.create(sense.sky.figures)
}


R5.filename<-sprintf("%s/R5-cabbages.csv", fromSkyOutputDir)
R10.filename<-sprintf("%s/R10-cabbages.csv", fromSkyOutputDir)
R20.filename<-sprintf("%s/R20-cabbages.csv", fromSkyOutputDir)


#quartz(width=10, height=10)
replicates<-4
cabbages.fromSky.R5.df<-REGG.createFRromSkyDistribution(fieldCabbages@eggDistribution@cabbages.df, replicates=replicates, radius=0.05)
write.csv(cabbages.fromSky.R5.df, file<-R5.filename, row.names=F)


cabbages.fromSky.R10.df<-REGG.createFRromSkyDistribution(fieldCabbages@eggDistribution@cabbages.df, replicates=replicates, radius=0.1)
write.csv(cabbages.fromSky.R10.df, file<-R10.filename, row.names=F)

cabbages.fromSky.R20.df<-REGG.createFRromSkyDistribution(fieldCabbages@eggDistribution@cabbages.df, replicates=replicates, radius=0.2)
write.csv(cabbages.fromSky.R20.df, file<-R20.filename, row.names=F)



cabbages.fromSky.R5.df<-REGG.readCabbages(R5.filename)
cabbages.fromSky.R10.df<-REGG.readCabbages(R10.filename)
cabbages.fromSky.R20.df<-REGG.readCabbages(R20.filename)

dist.R5.fromSky<-EggDistribution("FromSky", cabbages.fromSky.R5.df, "FromSky.EggCount", fieldCabbages=FieldCabbages.LEVINII)
dist.R10.fromSky<-EggDistribution("FromSky", cabbages.fromSky.R10.df, "FromSky.EggCount", fieldCabbages=FieldCabbages.LEVINII)
dist.R20.fromSky<-EggDistribution("FromSky", cabbages.fromSky.R20.df, "FromSky.EggCount", fieldCabbages=FieldCabbages.LEVINII)


sense.filename<-sprintf("%s/%s.pdf", sense.sky.figures, "random-fromsky-dist")
cat("Writing pdf to ", sense.filename)
pdf(file=sense.filename, bg="white", width=17, height=6)
	REGG.plotCalibrationDistributions(dist.equal, dist.random, dist.R20.fromSky)
dev.off()


par(mfrow=c(1, 1))


REGG.readCabbages<-function(filename) {
	cabbages.df<-read.csv(file=filename)
	
	cabbages.df$X1m_dens<-as.factor(cabbages.df$X1m_dens)
	cabbages.df$X6m_dens<-as.factor(cabbages.df$X6m_dens)
	cabbages.df$X36m_dens<-as.factor(cabbages.df$X36m_dens)
	return (cabbages.df)
}

###############################################################################################
#####
##### Old stuf 
#####
dist.field<-EggDistribution("Field", cabbages.ALL.df, "Field.EggCount", fieldCabbages=FieldCabbages.LEVINII)


boxplot(dist.field)


cabbages.ALL.df$Equal.EggCount<-rep(20, length(cabbages.ALL.df$Plant.ID))
dist<-EggDistribution("Random", cabbages.ALL.df, "Equal.EggCount")

#quartz(width=10, height=10)
plot(dist)
eggCountFieldName<-"Equal.EggCount"
colnames(dist@cabbages.df[eggCountFieldName])

boxplot(eggDistribution@cabbages.df[["Random.EggCount"]]~eggDistribution@cabbages.df$X1m_dens)


hitCabbage<-function(x, y, cabbages.df) {
	
	for (i in 1:length(cabbages.df$Plant.ID)) {
		Geometry.isPointInCircle(c(x, y), , ))
	
}