# Some functions for dealing with mean dispersal and estimating it!

source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")
lib.loadLibrary("/thesis/common/movement/Mean Dispersal Library.r")


quartz(width=10, height=10)

md.calculateMSDForKL(K=10, L=80, timesteps=42)

z<-.9
s<-10
x<-1:14
ySkellamJones<-sapply(x, md.E, z, s, "skellam-jones")

#THese two dont work yet as we don;t know how to get E(l) and E(l^2) and E(cos(theta))
yKareiva<-sapply(x, md.E, z, s, "kareiva")
yJohnson<-sapply(x, md.E, z, s, "johnson")
plot(ySkellamJones~x, type="p", xlab="n", ylab="E(R2)")

lines(yKareiva~x, col="blue")
lines(yJohnson~x, lty=2, col="green")



#Just prooves that it doesnt matter to mean(cos(theta)) wether they are 0 to 360 or -180 to 180
theta360<-toRadians(c(250, 350, 330, 20, 30, 40))
theta180<-toRadians(c(250-360, 350-360, 330-360, 20, 30, 40))

mean(cos(theta360))
mean(cos(theta180))