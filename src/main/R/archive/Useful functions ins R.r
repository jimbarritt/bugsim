test.df<-read.csv("/Users/Jim/Work/code/bugsim/resource/R/Test Deterministic Birth.csv")

plot(test.df$x,test.df$y, xlim=c(0, 10), ylim=c(0,10))
class(x)

#tells you what class something is

#to get a row in a table:
bf.ALL.df[5000,]

#Accesing items in la list or vector
#for some reason you have to put [[]] around the index to get the
#real thing you are after

#Lists the names of columns
	names(expSummary.df)
	
#Gives detailed structure
	str(expSummary.df)

#Tells you if an object is a factor
	is.factor(expSummary.df$L)
	
#Tells you the lenght of anything (like .size() in java)
	length(expSummary.df$S)
	
#Make something the current object
	attach()
	detach()
	
#This show you what methods are available for a class!
	methods(class="data.frame")

# Show the attributes of something (properties):
	attributes(someObject)
	
# Column names of a dataframe
	colnames(x)
	
# Change a column name:
	names(T.df)[names(T.df)=="S"] <- "JOHN"
	
# Create a new graphics output window: with size in inches
quartz(width=11.75, height=8.25)

# Format some values in a string to be printed:
title <- sprintf("%s=%s, %s=%s", factor1Name, iLevel.F1, factor2Name, iLevel.F2)
print(title)

#Extract a subset of data based on some criteria...
plotFrame.df <- subset(F1.subset.df,F1.subset.df[factor2Name]==iLevel.F2)
			
#----------------------------------------------------------------------------------------------
simpleF <- function(x, y) {
	str(x)
	str(y)
}

simpleF(plot.df, S.levels)
	# Extract the factors into separate variables for processing later.
	L.levels <- levels(expSummary.df$L)
	A.levels <- levels(expSummary.df$A)
	S.levels <- levels(expSummary.df$S)


#Variance
 var(stats.df$CENTRE_RATIO)
 
#Standard deviation
sd(stats.df$CENTRE_RATIO)

#mean
mean(stats.df$CENTRE_RATIO)

#Standard Error :
stderr <- sd(stats.df$CENTRE_RATIO) / sqrt(n)

#where n is number of samples 


#The square root of the variance is the standard deviation

 few thoughts:
	1 	 Instead of fixing the y axis max value at 200, simply set ylim to c(0, max(math.bar * 1.2)) or a similar constant. In this case, you get an extra 20% above the max(y) value for the legend placement.
	2 	 In the legend call, use:

  legend("topleft", legend=(c("Label A", "Average")),

          fill = c("blue","orange"))

This will place the legend at the topleft of the plot region, rather than you having to calculate the x and y coords. If you want it moved in from the upper left hand corner, you can use the 'inset' argument as well, which moves the legend by a proportion of the plot region limits (0 - 1):

    legend("topleft", legend=(c("Label A", "Average")),

          fill = c("blue","orange"), inset = .1)

3. You can use barplot(..., yaxt = "n") to have the y axis not drawn and then use axis() to place the labels at locations of your choosing, which do not need to run the full length of the axis range:

 barplot(1:5, yaxt = "n", ylim = c(0, 10))  axis(2, at = 0:5)

4. You can place the legend outside the plot region, enabling you to keep the y axis range to <=100. This would need some tweaking, but the idea is the same:

 # Increase the size of the top margin 
  par(mar = c(5, 4, 8, 2) + 0.1)

 # Draw a barplot 
  barplot(1:5)

 # Disable clipping outside the plot region  par(xpd = TRUE)

 # Now draw the legend, but move it up by 30% from the top left  legend("topleft", legend = LETTERS[1:5], inset = c(0, -.3))

You could also place the legend to the right or left of the plot region if you prefer, adjusting the above accordingly.


#AGGREGATE
aggregate - does some useful aggregation by different factors


#APPLY :

returns a vecotr by applying a set of functions to a dataset


#WHICH :
#Gives you the index of a value in an array equal to some condition;
index<-which(VECTOR==X)


#Demonstration of apply....
apply(X=breaks, MARGIN=1, FUN=test, "a", "b") #MARGIN 1 = apply over rows

test<-function(X, a, b) {
	cat("x: ", X, "a: ", a, "b: ", b, "\n")
}


str(bf100.df)

categories <- c()
i=360;
while (i>0) {
	categories<-append(categories, c(i))
	i<-i-20	
}
freq.df <- data.frame("cat"=sort(categories))
freq.df$count <- 0
for (i in bf100.df) {
	allocated <- FALSE
	for (cat in freq.df$cat) {		
		if (i<cat && allocated==FALSE) {
			current <- freq.df$count[freq.df$cat==as.numeric(cat)]
			freq.df$count[freq.df$cat==as.numeric(cat)] <- current + 1 
			allocated <- TRUE
		}		
	}	
}
sum(freq.df$count)
