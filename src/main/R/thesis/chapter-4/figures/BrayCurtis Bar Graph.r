output.figures<-"~/Documents/Projects/bugsim/documentation/masters-thesis/04-sense-scale/resources/figures"

#Dont forget to execute all the functions below...

filename<-sprintf("%s/bray-curtis-summary.pdf", output.figures)
cat("Writing graph to ", filename, "\n")
pdf(file=filename, width=10, height=6, bg="white")
	plotBrayCurtisSummary()
dev.off()


plotBrayCurtisSummary<-function() {
	BC<-c(0.1133, 0.0829, 0.1590, 0.1353, 0.1389, 0.1418, 0.1774, 0.1370, 0.0534)
	A<-c(0.5, 0.5, 10, 0.5, 0.5, 10, 0.5, 0.5, 3.0)
	RFD<-c(5, 5, 10, 10, 100, 100, 10, 600, 600)
	L<-c(150, 250, 350, 150, 350, 350, 350, 250, 250)
	TRIAL<-c("C1", "C2", "C3", "D1", "D2", "D3", "D4", "D5", "D6")




	bcsummary.df<-data.frame(TRIAL)
	bcsummary.df$BC<-BC
	bcsummary.df$RFD<-RFD
	bcsummary.df$L<-L
	bcsummary.df$A<-A

	sorted.df<-bcsummary.df[ order(BC) ,]
	names<-levels(sorted.df$TRIAL)[sorted.df$TRIAL]

	par(mar=c(7, 7, 2, 2))
	barplot(sorted.df$BC, col="blue", ylim=c(0, 0.2), names.arg=names,cex.names=1.5 ,axes=F)
	box()
	axis(2, at=c(0, 0.1, 0.2), labels=c("0", "0.1", "0.2"), cex.axis=1.5)
	mtext(side=1, line=4, expression("Trial"), cex=2)
	mtext(side=2, line=4, expression("B-C"), cex=2)
}