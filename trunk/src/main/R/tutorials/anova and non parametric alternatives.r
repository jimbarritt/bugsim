## ONE-WAY ANOVA ##

#Load package lawstat to be able to test assumptions of normality and equal
#variances

#load data frame
data.df<-read.csv("data.csv")
#data = name of the data frame

data.df
#view data frame

#attach data frame
attach(data.df)

#view data frame
str(data.df)

#check assumption of normality = Shapiro-Wilk normality test
shapiro.test(response variable)
#response variable is the one you are measuring
#p < 0.05 indicates data does not conform to a normal distribution

#Check assumption of homogeneity of variances = Levene's test
levene.test(y, group,option=c("mean", "median", "trim.mean"),trim.alpha=1)
#where y = response variable and group = grouping variable
#p < 0.05 indicates data does not conform to this assumption

------------------------------------------------------------------
ANOVA and non-parametric equivalents

#If data does not meet both assumptions must use the non-parametric equivalent 
#to a one-way ANOVA = Kruskal-Wallis test
kruskal.test(response variable,grouping variable)

#If data does meet both assumptions you can use one-way ANOVA
label.aov <- aov(Response ~ Factor, data = Name.df)
# label.aov = Name of the ANOVA object. 
# <- = create a data file called label.aov and it 'gets' the following function...
# aov = ANOVA function
# Response ~ Factor1= This is the model being used in the ANOVA. The #response variable must be first and then the factor.
# ~ = modeled against.
# data = Name.df = identifies the data frame to be used in the ANOVA.
#To look at the results of the ANOVA
summary(label.aov)





