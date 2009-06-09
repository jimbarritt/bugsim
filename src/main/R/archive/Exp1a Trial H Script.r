source("~/Work/code/bugsim/resource/R/Exp1a Library.r", echo=FALSE)

experimentNumber<-10
trialId<-"TrH"
baseFilename<-"edge-exp01a"

rm(inp.df)
inp.df <- importExperiment1aSummary(baseFilename, experimentNumber, trialId)
plan.df<-importExperimentPlan(baseFilename, experimentNumber, trialId)
numReplicants<-describeExperiment1a(inp.df)



results<-readIterationsTrialH(inp.df$iteration, baseFilename, experimentNumber, trialId)
maps.list<-results[[1]]
M.list<-results[[2]]

title<-"Maps of cabbage intersections for I 0-90 h 0-180 M 1-120"
plotTitle<-"M=%0.0f"
outputTrialH(maps.list, M.list, baseFilename, experimentNumber, trialId, title, plotTitle)


ratios.list<-calculateRatiosFromMaps(maps.list)

outputRatios(ratios.list, M.list, baseFilename, experimentNumber, trialId, "M=%0.0f", "Centre-Corner ratios for varying M")






#Rubbish....

help(par)

I.0.df<-subset(butterflies.df, butterflies.df$I==0)


length(I.group.df$lastCabbage.analysis.code)

length(I.0.df$I)


length(butterflies.df$lastCabbage.analysis.code)


x <- y <- seq(-4*pi, 4*pi, len=27)
r <- sqrt(outer(x^2, y^2, "+"))
image(z = z <- cos(r^2)*exp(-r/6), col=gray((0:32)/32))
image(z, axes = FALSE, main = "Math can be beautiful ...",
      xlab = expression(cos(r^2) * e^{-r/6}))
contour(z, add = TRUE, drawlabels = FALSE)



str(volcano)
x <- 10*(1:nrow(volcano))
y <- 10*(1:ncol(volcano))
image(x, y, volcano, col = terrain.colors(100), axes = FALSE)
contour(x, y, volcano, levels = seq(90, 200, by = 5),
        add = TRUE, col = "peru")
axis(1, at = seq(100, 800, by = 100))
axis(2, at = seq(100, 600, by = 100))
box()
title(main = "Maunga Whau Volcano", font.main = 4)

