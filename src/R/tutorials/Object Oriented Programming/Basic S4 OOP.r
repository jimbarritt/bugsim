# How to do S4 programming in R

#Create a class:
setClass("TestClass", representation(
	x="numeric",
	y="numeric",
	label="character",
	z="numeric"
))

#Instantiate an object:
testObject<-new("TestClass", x=30, y=40, label="Some label")

#Access the member variables:
testObject@label
testObject@z
testObject@z<-19
testObject@z

#Mutate:
testObject@label<-"New Label"
testObject@label

#Add a constructor:
TestClass<-function(x=0, y=0, label="") {
	x<-as(x, "numeric")
	y<-as(y, "numeric")
	label<-as(label, "character")
	instance<-new("TestClass", x=x, y=y, label=label)
	return(instance)
}
testObject<-TestClass(label="Hello Marc!")

#Add a "toString" which is a "show method"
#The function signature has to have "object" in it - cant call it something else...
setMethod("show", "TestClass", 
	function(object) {
		 cat((paste("Hello!! x=", object@x, " : y=", object@y, " : label=", object@label)))
	}
)

testObject<-TestClass(30, 40, "Hello World")
testObject

str(testObject)

#And a "summary" method
setMethod("summary", "TestClass", 
	function(object) {
		summary<-paste("x=", object@x, "\ny=", object@y, "\nlabel=", object@label)
		 cat(summary)
	}
)
summary(testObject)


#Inheritance:
setClass("TestDerivedClass", representation(
	newX="numeric"
), contains="TestClass")


testDerivedObject<-new("TestDerivedClass")


