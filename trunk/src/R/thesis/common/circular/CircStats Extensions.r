#CircStats works nice but doesnt return you any values!!

#So here we implement it so it does... just copied the source.
dvm.degrees<-function(xdegrees, mu, k, breakSize, N) {
	return (dvm(toRadians(xdegrees), mu, k))
}



watson.test<-function (x, alpha = 0, dist = "uniform") 
{
	results.mx<-matrix(NA, nrow=4, ncol=1)
    if (dist == "uniform") {
#        cat("\n", "      Watson's Test for Circular Uniformity", 
#            "\n", "\n")
        n <- length(x)
        u <- sort(x)/(2 * pi)
        u.bar <- mean(u)
        i <- seq(1:n)
        sum.terms <- (u - u.bar - (2 * i - 1)/(2 * n) + 0.5)^2
        u2 <- sum(sum.terms) + 1/(12 * n)
        u2 <- (u2 - 0.1/n + 0.1/(n^2)) * (1 + 0.8/n)
        crits <- c(99, 0.267, 0.221, 0.187, 0.152)
        if (n < 8) {
            cat("Total Sample Size < 8:  Results are not valid", 
                "\n", "\n")
        }
#        cat("Test Statistic:", round(u2, 4), "\n")
		results.mx[1, 1]<-round(u2, 4)
        if (sum(alpha == c(0, 0.01, 0.025, 0.05, 0.1)) == 0) 
            stop("Invalid input for alpha")

		results.mx[2, 1]<-alpha
        if (alpha == 0) {
            if (u2 > 0.267) {
 #               cat("P-value < 0.01", "\n", "\n")
				results.mx[2, 1]<-0.01
			} else if (u2 > 0.221) {
  #              cat("0.01 < P-value < 0.025", "\n", "\n")
				results.mx[2, 1]<-0.025
			} else if (u2 > 0.187) {
   #             cat("0.025 < P-value < 0.05", "\n", "\n")
				results.mx[2, 1]<-0.05
			} else if (u2 > 0.152) {
 #               cat("0.05 < P-value < 0.10", "\n", "\n")
				results.mx[2, 1]<-0.10
			} else {
#				cat("P-value > 0.10", "\n", "\n")
				results.mx[2, 1]<-">0.10"
			}
        }
        else {
            index <- (1:5)[alpha == c(0, 0.01, 0.025, 0.05, 0.1)]
            Critical <- crits[index]
            if (u2 > Critical) 
                Reject <- "Reject Null Hypothesis"
            else Reject <- "Do Not Reject Null Hypothesis"

#            cat("Level", alpha, "Critical Value:", round(Critical, 
#                4), "\n")

#            cat(Reject, "\n", "\n")
			results.mx[3, 1]<-round(Critical, 4)
			results.mx[4, 1]<-Reject
        }
    }
    else if (dist == "vm") {
#        cat("\n", "      Watson's Test for the von Mises Distribution", 
#            "\n", "\n")
        u2.crits <- cbind(c(0, 0.5, 1, 1.5, 2, 4, 100), c(0.052, 
            0.056, 0.066, 0.077, 0.084, 0.093, 0.096), c(0.061, 
            0.066, 0.079, 0.092, 0.101, 0.113, 0.117), c(0.081, 
            0.09, 0.11, 0.128, 0.142, 0.158, 0.164))
        n <- length(x)
        mu.hat <- circ.mean(x)
        kappa.hat <- est.kappa(x)
        x <- (x - mu.hat)%%(2 * pi)
        x <- matrix(x, ncol = 1)
        z <- apply(x, 1, pvm, 0, kappa.hat)
        z <- sort(z)
        z.bar <- mean(z)
        i <- c(1:n)
        sum.terms <- (z - (2 * i - 1)/(2 * n))^2
        Value <- sum(sum.terms) - n * (z.bar - 0.5)^2 + 1/(12 * 
            n)
        if (kappa.hat < 0.25) 
            row <- 1
        else if (kappa.hat < 0.75) 
            row <- 2
        else if (kappa.hat < 1.25) 
            row <- 3
        else if (kappa.hat < 1.75) 
            row <- 4
        else if (kappa.hat < 3) 
            row <- 5
        else if (kappa.hat < 5) 
            row <- 6
        else row <- 7
        if (alpha != 0) {
            if (alpha == 0.1) 
                col <- 2
            else if (alpha == 0.05) 
                col <- 3
            else if (alpha == 0.01) 
                col <- 4
            else {
                cat("Invalid input for alpha", "\n", "\n")
                break
            }
            Critical <- u2.crits[row, col]
            if (Value > Critical) 
                Reject <- "Reject Null Hypothesis"
            else Reject <- "Do Not Reject Null Hypothesis"
#            cat("Test Statistic:", round(Value, 4), "\n")
#            cat("Level", alpha, "Critical Value:", round(Critical, 
#                4), "\n")
#            cat(Reject, "\n", "\n")

			results.mx[1, 1]<-round(Value, 4)
			results.mx[2, 1]<-alpha
			results.mx[3, 1]<-round(Critical, 4)
			results.mx[4, 1]<-Reject
        }
        else {
#            cat("Test Statistic:", round(Value, 4), "\n")
			results.mx[1, 1]<-round(Value, 4)

            if (Value < u2.crits[row, 2]) {
#                cat("P-value > 0.10", "\n", "\n")
				results.mx[2, 1]<-">0.10"
			} else if ((Value >= u2.crits[row, 2]) && (Value < u2.crits[row, 3])) {
#                cat("0.05 < P-value > 0.10", "\n", "\n")
				results.mx[2, 1]<-0.10
            } else if ((Value >= u2.crits[row, 3]) && (Value < u2.crits[row, 4])) {
#                cat("0.01 < P-value > 0.05", "\n", "\n")
				results.mx[2, 1]<-0.05
            } else {
#				cat("P-value < 0.01", "\n", "\n")
				results.mx[2, 1]<-0.01
			}

        }
    }
    else stop("Distribution must be either uniform or von Mises")
	return (list(Test.Statistic=results.mx[1, 1], Sig.Level=results.mx[2, 1], Critical.Value=results.mx[3, 1], Summary=results.mx[4, 1]))
}


watson.two.test<-function (x, y, alpha = 0, plot = FALSE) 
{
	results.mx<-matrix(NA, nrow=4, ncol=1)
	
    n1 <- length(x)
    n2 <- length(y)
    n <- n1 + n2
    if (n < 18) 
        cat("Total Sample Size < 18:  Consult tabulated critical values", 
            "\n", "\n")
    if (plot == TRUE) {
        x <- sort(x%%(2 * pi))
        y <- sort(y%%(2 * pi))
        plot.edf(x, main = "Comparison of Empirical CDFs", xlab = "", 
            ylab = "")
        par(new = TRUE)
        plot.edf(y, xlab = "", ylab = "", axes = FALSE, lty = 2)
    }
#    cat("\n", "      Watson's Two-Sample Test of Homogeneity", 
#        "\n", "\n")
    x <- cbind(sort(x%%(2 * pi)), rep(1, n1))
    y <- cbind(sort(y%%(2 * pi)), rep(2, n2))
    xx <- rbind(x, y)
    rank <- order(xx[, 1])
    xx <- cbind(xx[rank, ], seq(1:n))
    a <- c(1:n)
    b <- c(1:n)
    for (i in 1:n) {
        a[i] <- sum(xx[1:i, 2] == 1)
        b[i] <- sum(xx[1:i, 2] == 2)
    }
    d <- b/n2 - a/n1
    dbar <- mean(d)
    u2 <- (n1 * n2)/n^2 * sum((d - dbar)^2)
    crits <- c(99, 0.385, 0.268, 0.187, 0.152)
#    cat("Test Statistic:", round(u2, 4), "\n")
	results.mx[1, 1]<-round(u2, 4)
	results.mx[2, 1]<-alpha
    if (sum(alpha == c(0, 0.001, 0.01, 0.05, 0.1)) == 0) 
        stop("Invalid input for alpha")
    else if (alpha == 0) {
        if (u2 > 0.385) {
#            cat("P-value < 0.001", "\n", "\n")
			results.mx[2, 1]<-0.001
        } else if (u2 > 0.268) {
#            cat("0.001 < P-value < 0.01", "\n", "\n")
			results.mx[2, 1]<-0.01
        } else if (u2 > 0.187) {
 #           cat("0.01 < P-value < 0.05", "\n", "\n")
			results.mx[2, 1]<-0.05
        } else if (u2 > 0.152) {
#            cat("0.05 < P-value < 0.10", "\n", "\n")
			results.mx[2, 1]<-0.10
        } else {
#			cat("P-value > 0.10", "\n", "\n")
			results.mx[2, 1]<-">0.10"
		}
    }
    else {
        index <- (1:5)[alpha == c(0, 0.001, 0.01, 0.05, 0.1)]
        Critical <- crits[index]
        if (u2 > Critical) 
            Reject <- "Reject Null Hypothesis"
        else Reject <- "Do Not Reject Null Hypothesis"
#        cat("Level", alpha, "Critical Value:", round(Critical, 
#            4), "\n")
#        cat(Reject, "\n", "\n")
		results.mx[2, 1]<-alpha
		results.mx[3, 1]<-Critical
		results.mx[4, 1]<-Reject
    }
	return (list(Test.Statistic=results.mx[1, 1], Sig.Level=results.mx[2, 1], Critical.Value=results.mx[3, 1], Summary=results.mx[4, 1]))
}