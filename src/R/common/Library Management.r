# Some code for managing the loading of libraries...
cat("Library management Library (v 1.0)")

# Gets a frame dump when an error occurs...
#options(error=recover)
options(error=NULL)

#Turn Warnings into Errors...
options(warn=1) 

lib.libraryRootDir<-"/Users/Jim/Work/code/bugsim/src/R"

lib.getRootDir<-function() {
	return (lib.libraryRootDir)
}

# So we dont have to explicitly refer to all our libraries full paths...
#Just pass "/thesis/common/someLibrary" for example...
lib.loadLibrary<-function(libraryRelativePath) {
	fullName<-sprintf("%s/%s", lib.libraryRootDir, libraryRelativePath)
	source(fullName)
}
