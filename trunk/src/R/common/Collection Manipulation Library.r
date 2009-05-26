cat("Collection manipulation Library (v 1.0)\n")

setMethod("summary", "list",
	function(object) {
		for (item in object) {
			summary(item)
		}
	}
)

#c.in<-1:10
collection.reverse<-function(c.in) {

	out<-rep(0, length(c.in))
	countIn<-length(c.in)
	for (i in countIn:1 ) {
		out[(countIn+1)-i]<-c.in[i]
	}
	return (out)
}

collection.appendToList<-function(input.list, newValue) {
	tList<- list(newValue)
	new.list<-c(input.list, tList)
	return (new.list)
}

#this is a very inefficient function so dont use it for hard processing!
collection.contains<-function(in.list, search.string) {
	contains<-FALSE
	for (in.item in in.list) {
		if (in.item == search.string) {
			contains<-TRUE
		}
	}
	return (contains)
}

collection.arraysEqual<-function(expected, actual, delta) {
	for (i in 1:length(expected)) {
		e<-precisionEquals(expected[[i]], actual[[i]], delta)
		if (e==FALSE) {
			return (FALSE)
		}
	}
	return (TRUE)
}

collection.precisionEquals<-function(x1, x2, delta) {
	equals<-(abs(x1-x2) <= delta)
	return (equals)
}

collection.buildListString<-function(x, seperator) {
	s<-""
	for (i in 1:length(x)) {
		s<-paste(s, x[[i]])
		
		if (i!=length(x)) {
			s<-paste(s, seperator)
		}
		
	}
	return (s)
}

collection.max<-function(list) {
	maxs<-c()
		for (item in list) {
			maxs<-c(maxs, max(item))			
		}
		return (max(maxs))
}

