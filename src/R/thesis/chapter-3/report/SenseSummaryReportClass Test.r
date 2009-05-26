source("/Users/Jim/Work/code/bugsim/src/R/common/Library Management.r")


lib.loadLibrary("/thesis/chapter-3/SenseSummaryReportClass.r")



experimentPlan<-ex.loadPlan("raw", "sense", "exp2", "TrX1", 1)

report<-SenseSummaryReport(experimentPlan)

report<-generateReport(report, TRUE, TRUE)

report@signalSurvey