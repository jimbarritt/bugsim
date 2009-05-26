# Stuff to build latex reports
cat("Latex Library (v 1.0)\n")

lib.loadLibrary("/common/latex/LatexFileClass.r")

latex.build<-function(texFile, outputDir) {
	root.dir<-lib.getRootDir()
	latex.cmd.build<-sprintf("%s/common/latex/latex-build.sh '%s' '%s' ", root.dir, texFile, outputDir)
	system(latex.cmd.build)	
}


latex.preview<-function(pdfOutputFile) {
	latex.cmd.preview<-sprintf("open -a Preview '%s'", pdfOutputFile)
	system(latex.cmd.preview)
}




