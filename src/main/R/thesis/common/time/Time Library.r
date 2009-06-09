cat("Time Library (Version 1.0)\n")


# Works out hours, mins and seconds and then formats them nicely... you pass in milliseconds

Time.formatMS<-function(ms, showMS=FALSE) {
	result<-"NA"
	if (!is.na(ms)){
		split<-Time.hoursMinsSecs(ms)	
		if (showMS) {
			result<-sprintf("%02d:%02d:%02d.%03d", split$hours, split$minutes, split$seconds, split$ms)
		} else {
			result<-sprintf("%02d:%02d:%02d", split$hours, split$minutes, split$seconds)
		}
	}
	return (result)
}

#ms.in<-950+(56*1000)+(36*60*1000)+(145*60*60*1000)
# should produce hrs=145, mins=36, secs=56, ms=95
Time.hoursMinsSecs<-function(ms.in) {	
	secs<-floor(ms.in/1000)
	ms<-ms.in-(secs*1000)	
	mins<-floor(secs/60)	
	hours<-floor(mins/60)	
	mins<-mins-(hours*60)	
	secs<-secs-(mins*60 + hours*60*60)
	
	result<-list("hours"=hours, "minutes"=mins, "seconds"=secs, "ms"=ms)	
	return (result)
}