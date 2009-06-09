source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")
lib.loadLibrary("/thesis/common/time/Time Library.r")


# should produce hrs=145, mins=36, secs=56, ms=95
ms.test<-95+(56*1000)+(36*60*1000)+(145*60*60*1000)

split<-Time.hoursMinsSecs(ms.test)

Time.formatMS(ms.test)

