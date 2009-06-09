## volcano  ## 87 x 61 matrix
wireframe(volcano, shade = TRUE,aspect = c(61/87, 0.4),light.source = c(10,0,10))


mx.test<-matrix(nrow=10, ncol=10)

mx.test[1,]=rep(10, 10)
mx.test[2,]=rep(9, 10)
mx.test[3,]=rep(8, 10)
mx.test[4,]=rep(7, 10)
mx.test[5,]=rep(6, 10)
mx.test[6,]=rep(5, 10)
mx.test[7,]=rep(4, 10)
mx.test[8,]=rep(3, 10)
mx.test[9,]=rep(2, 10)
mx.test[10,]=rep(1, 10)

wireframe(mx.test, shade=TRUE, aspect = c(61/87, 0.4))