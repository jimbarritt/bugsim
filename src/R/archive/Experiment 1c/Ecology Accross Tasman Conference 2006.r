source("~/Work/code/bugsim/resource/R/Experiment 1c/Exp 1c Library.r", echo=FALSE)

experimentNumber<-1
trialId<-"TrC"
baseFilename<-"edge-exp01c"
fieldLayoutDirectory<-"~/Documents/Projects/msc/Simulation Input"
fieldLayoutFilename<-"Field E Levin II 2006 layout"
fieldresultsFilename<-"LevinII-no-p.csv"
experimentDir<-createDirectoryName(basefilename, experimentNumber, trialId)


layout.df<-loadCabbageFieldLayout(fieldLayoutDirectory, fieldLayoutFilename)
outputCabbageFieldLayout(layout.df, fieldLayoutDirectory, fieldLayoutFilename)

fieldresults.df<-read.csv(sprintf("%s/%s", fieldLayoutDirectory, fieldresultsFilename))
fieldresults.df$X1m_dens<-factor(fieldresults.df$X1m_dens)
fieldresults.df$X6m_dens<-factor(fieldresults.df$X6m_dens)

count(fieldresults.df,fieldresults.df$Field)

field.stats.list<-outputFieldResults(fieldresults.df, fieldLayoutDirectory, "Levin II Fields E-H 2006")
writeDataFrameToDir(field.stats.list,  fieldLayoutDirectory, "Field Stats") 



rm(summary.df)
summary.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)
numReplicants<-describeExperiment1a(summary.df)

#Display graphs of the simulation results...
all.results.list<-readTrialBResults(layout.df, summary.df, summary.df$iteration, numReplicants, baseFilename, experimentNumber, trialId) 

outputTrialBResults(all.results.list, summary.df, baseFilename, experimentNumber, trialId, legendFactor=c(), only6m=TRUE)

#Calculate regression comparisons
t.stats<-outputRegressionComparisons(field.stats.list, all.stats.list, summary.df, basefilename, experimentNumber, trialId)


#Read a specific iteration in to "zoom into" for the presentation
iteration.stats.list<-readExp1cIterationStats(fieldResults.df, 12, 10, experimentDir, experimentNumber)

quartz(width=8, height=8)

# --------------------------------------------------------------------------------------------------------------------------
# Slide 5 - Field Results:
outputFilename<-sprintf("%s/Field Results Levin II 2006.pdf", experimentDir)
pdf(file=outputFilename, width=8, height=8, bg="white")
par(mar=c(5, 6, 5,3))
legendFactor<-c()
plotTitle<-sprintf("Field Levin II 2006", 200, 100)
i.stats.list<-list(field.stats.list[[2]])
par(mar=c(5, 6, 5,3))
plotManyResults(i.stats.list, plotTitle, "Plant density (6x6m)", "Eggs Per Plant (mean +/- stderr)", FALSE, legendFactor, plotErr=TRUE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(1, 2),legendPosition="topright", yLim=c(0, 45),  cex.lab=1.4)			
dev.off()

# --------------------------------------------------------------------------------------------------------------------------
# Slide 6 - Log field results:
quartz(width=6, height=6)
outputFilename<-sprintf("%s/Log Regression analysis Field II Levin 2006.pdf", experimentDir)
pdf(file=outputFilename, width=8, height=8, bg="white")
idf<-sum(fieldresults.df$eggs_24.02.2006)/length(fieldresults.df$X6m_dens)
simulated.stats.1m.df<-iteration.stats.list[[2]] #item 1 is the complete data set from which the stats were generated
simulated.stats.6m.df<-iteration.stats.list[[3]]

plotTitle<-sprintf("Log Regression Analysis Field Results")
par(mar=c(5, 6, 5, 3))
t.stats<-plotLogRegressionFieldVsSimulation(field.stats.list, simulated.stats.1m.df, simulated.stats.6m.df, plotTitle, only6m=TRUE, onlyField=TRUE, cex.lab=1.4, idf=idf)
lms<-t.stats[[2]]
lm.sim.6m<-summary(lms[[1]])
lm.fld.6m<-summary(lms[[2]])
legend.cex<-1
legendStrings.6m<-c("Field", sprintf("Regression (r=%3.3f)", lm.fld.6m$r.squared), "Ideal Free Distribution")
colors.6m<-c(rep("#000099", 2), "#CC66CC")
pchs.6m<-c(24, NA,  NA)
ltys.6m<-c(NA, 1, 1)
legend("bottomright", inset=c(.05, 0.05),x.intersp=1.2, y.intersp=1.5, pch=pchs.6m, col=colors.6m, legend=legendStrings.6m,lty=ltys.6m,  cex=1.1)
dev.off()

# --------------------------------------------------------------------------------------------------------------------------
# Slide 27 - All field results!!



# --------------------------------------------------------------------------------------------------------------------------
#Zoomed into a single comparison with the field
outputFilename<-sprintf("%s/Iteration 012 L-200 A-100 Field vs Simulation Eggs Per Plant.pdf", experimentDir)
pdf(file=outputFilename, width=12, height=6, bg="white")
par(mfrow=c(1, 2))

legendFactor<-c()
plotTitle<-sprintf("Field Levin II 2006", 200, 100)
i.stats.list<-list(field.stats.list[[2]])
par(mar=c(5, 6, 5,3))
plotManyResults(i.stats.list, plotTitle, "Plant density (6x6m)", "Eggs Per Plant (mean +/- stderr)", "%s", legendFactor, plotErr=TRUE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(1, 2),legendPosition="topright", yLim=c(0, 45), , cex.lab=1.4)			

plotTitle<-sprintf("Simulation L=%s, A=%s", 200, 100)
#item 1 is the complete data set from which the stats were generated
i.stats.list<-list(iteration.stats.list[[3]])
par(mar=c(5, 6, 5,3))
plotManyResults(i.stats.list, plotTitle, "Plant density (6x6m)", "Eggs Per Plant (mean +/- stderr)", "%s", legendFactor, plotErr=TRUE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(1, 2),legendPosition="topright", yLim=c(0, 45),, cex.lab=1.4)			
dev.off()

# --------------------------------------------------------------------------------------------------------------------------
#Plotted on the same graph...
outputFilename<-sprintf("%s/Iteration 012 L-200 A-100 Field vs Simulation Eggs Per Plant Same Graph.pdf", experimentDir)
pdf(file=outputFilename, width=8, height=8, bg="white")
par(mar=c(5, 6, 5,3))
legendFactor<-factor(c(1,2), labels=c("Field", "Simulation"))

plotTitle<-sprintf("Field vs Simulation", 200, 100)
i.stats.list<-list(field.stats.list[[2]], iteration.stats.list[[3]])
plotManyResults(i.stats.list, plotTitle, "Plant density (6x6m)", "Eggs Per Plant (mean +/- stderr)", "%s", legendFactor, plotErr=TRUE, continuousX=FALSE, lineColors=c("#3333CC", "#000099"), lineTypes=c(1, 2),legendPosition="topright", yLim=c(0, 45), cex.lab=1.4)			

chsq<-expression(chi^2==3.21919880013278E-11)
legend("topright", inset=c(0.05, 0.15), legend=c(chsq), x.intersp=1.5, y.intersp=1.4)
dev.off()




#Regression graphs for simulation vs field
quartz(width=8, height=8)
outputFilename<-sprintf("%s/Iteration 012 L-200 A-100 Log Regression analysis.pdf", experimentDir)
pdf(file=outputFilename, width=8, height=8, bg="white")


idf<-sum(fieldresults.df$eggs_24.02.2006)/length(fieldresults.df$X6m_dens)

#item 1 is the complete data set from which the stats were generated
simulated.stats.1m.df<-iteration.stats.list[[2]]
simulated.stats.6m.df<-iteration.stats.list[[3]]
plotTitle<-sprintf("Log Regression Analysis L=%s, A=%s", 200, 100)

par(mar=c(5, 6, 5, 3))
t.stats<-plotLogRegressionFieldVsSimulation(field.stats.list, simulated.stats.1m.df, simulated.stats.6m.df, plotTitle, only6m=TRUE, cex.lab=1.4, idf=idf)

lms<-t.stats[[2]]


lm.sim.6m<-summary(lms[[1]])
lm.fld.6m<-summary(lms[[2]])


legend.cex<-1.2
legendStrings.6m<-c("Field", "Simulated", sprintf("Field (r=%3.3f)", lm.fld.6m$r.squared), sprintf("Simulated (r=%3.3f)", lm.sim.6m$r.squared), "Ideal Free Distribution")
colors.6m<-c(rep("#000099", 4), "#CC66CC")
pchs.6m<-c(24, 19,  NA, NA, NA)
ltys.6m<-c(NA, NA,  1, 2, 1)

legendStrings.1m<-c("1x1m ", "field", "sim", "regression ", "field", "sim")

pchs.1m<-c(NA, 24, 19, NA, NA, NA)
ltys.1m<-c(NA, NA, NA,NA,  1, 2)
colors.1m<-rep("#660066", 6)
legend("bottomright", inset=c(.05, 0.01),x.intersp=1.2, y.intersp=1.5, pch=pchs.6m, col=colors.6m, legend=legendStrings.6m,lty=ltys.6m,  cex=1.1)
#legend("bottomright", ncol=6,inset=c(.01, 0.05), pch=pchs.1m, col=colors.1m, legend=legendStrings.1m,lty=ltys.1m,  cex=1.1)	


dev.off()


