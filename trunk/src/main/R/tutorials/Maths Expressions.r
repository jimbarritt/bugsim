id<-1
S<-2
R<-4
AREA<-300

partA<-sprintf("%s) S=%0.0f, ", id,S)
partB<-sprintf("=%0.1f, Area=%s",S/R, AREA)
title<-substitute(paste(a, I[R]^45*sum(theta[x]^i), b), list(a=partA, b=partB))

quartz(width=5, height=5)
par(mar=c(7, 7,7 ,7))
plot.new()
title(main=title, cex=2)

spacingTitle<-substitute(paste(a, I[R], b), list(a="\\\\textbf{", b="}"))
plot.new()
title(main=spacingTitle, cex=2)
