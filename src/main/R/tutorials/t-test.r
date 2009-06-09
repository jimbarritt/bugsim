t.test(lm.1$coef[[1]], lm.2$coef[[1]])
summary(t.stats)

a<-c(1:30)
b<-c(1:30)
t.stats.equal<-t.test(a, b)
a<-c(1:30)
b<-c(31:60)
t.stats.notequal<-t.test(a, b)


printResults(t.stats.equal,t.stats.notequal)


printResults<-function(t.stats.1, t.stats.2) {
	cat("With identical values:\n")
	cat("p-value=",t.stats.1$p.value,"\n")
	cat("With completely different values:\n")
	cat("p-value=",t.stats.2$p.value,"\n")
}
