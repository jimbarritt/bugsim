
expected<-c(23, 45, 56, 78, 12, 34)
observed<-c(23, 45, 56, 78, 12, 34)

chisq.mx<-rbind(expected, observed)


result<-chisq.test(chisq.mx)


result$p.value

print(sprintf("the p-value is %0.000f", result$p.value))

#If the values are too low, chisq doesnt like it and will produce a warning....
low.mx<-rbind(c(2, 3, 4), c(2, 3, 4))
chisq.test(low.mx)

fisher.test(low.mx)

low.mx<-rbind()
fisher.test(c(3,2, 1), c(1, 2, 3))

test.mx<-matrix(c(3, 1, 1, 3, 4, 5),
       nr = 2)
fisher.test(test.mx, alternative = "greater")


chisq.test(rbind(c(10, 3, 14), c(10, 5, 14)), correct=F)

x<-as.matrix(c(1.1, 1.4,1.4,  60, 2, 45000))
dim(x)<-c(3,2)
fisher.test(x)