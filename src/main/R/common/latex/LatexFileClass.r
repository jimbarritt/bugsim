
#Class definition for an LatexFile class...


setClass("LatexFile", representation(
	baseFileName="character",
	parentDir="character",
	contents="list"

))


#Add a constructor:
LatexFile<-function(baseFileName, parentDir) {
	instance<-new("LatexFile")
	
	instance@baseFileName<-baseFileName
	instance@parentDir<-parentDir
	instance@contents<-list()
	return (instance)
}

LatexFile.buildFile<-function(latexFile, showPreview, clean=TRUE) {
	LatexFile.buildFileInternal(latexFile, showPreview, clean, asSection=FALSE)
	LatexFile.buildFileInternal(latexFile, showPreview, clean, asSection=TRUE)
}

LatexFile.buildFileInternal<-function(latexFile, showPreview, clean=TRUE, asSection=FALSE) {
	rootDir<-lib.getRootDir()
	
	outputDir<-sprintf("%s/%s", latexFile@parentDir, "output")
	buildDir<-sprintf("%s/%s", outputDir, "target")
	if (!file.exists(outputDir)) {
		dir.create(outputDir)
	}
	if (!file.exists(buildDir)){
		dir.create(buildDir)
	}
	
	#Should be passed in as parameters...
	templateHeader<-sprintf("%s/thesis/common/output/StandardReportHeader.tex", rootDir)
	templateFooter<-sprintf("%s/thesis/common/output/StandardReportFooter.tex", rootDir)
	
	if (asSection==TRUE) {
		buildTexFile<-sprintf("%s/%s-section.tex", buildDir, latexFile@baseFileName)
		outputTexFile<-sprintf("%s/%s-section.tex", outputDir, latexFile@baseFileName)
	} else {
		buildTexFile<-sprintf("%s/%s.tex", buildDir, latexFile@baseFileName)
		outputTexFile<-sprintf("%s/%s.tex", outputDir, latexFile@baseFileName)
	}

	if (clean) {
		if (file.exists(buildTexFile)) {
			file.remove(buildTexFile)
		}
		if (asSection == FALSE) {
			cat.cmd<-sprintf("cat '%s' > '%s'", templateHeader, buildTexFile)
			system(cat.cmd)
		}
	
		cat("\\begin{flushleft}", file=buildTexFile, append=TRUE)
		timestamp<-sprintf("\\textcolor{blue}{Generated @ %s on %s", format(Sys.time(), "%H:%M:%S "), format(Sys.time(), "%a %b %d %Y}\n\n\\vspace{10pt}"))
		cat(timestamp, file=buildTexFile, append=TRUE)
		cat("\\end{flushleft}", file=buildTexFile, append=TRUE)
		for (i.line in latexFile@contents) {
			cat("\n", file=buildTexFile, append=TRUE)
			cat(i.line, file=buildTexFile, append=TRUE)
			cat("\n", file=buildTexFile, append=TRUE)
		}
	
		if (asSection == FALSE) {
			cat.cmd<-sprintf("cat '%s' '%s' > '%s'",  buildTexFile , templateFooter, outputTexFile)
			system(cat.cmd)
		}
	}
	
	if (asSection == FALSE) {
		latex.build(outputTexFile, buildDir)	
		pdfBuildFile<-sprintf("%s/%s.pdf", buildDir, latexFile@baseFileName)
		pdfOutputFile<-sprintf("%s/%s.pdf", outputDir, latexFile@baseFileName)
		file.copy(pdfBuildFile, pdfOutputFile, overwrite=TRUE)

		if (showPreview) {
			latex.preview(pdfOutputFile)
		}		
	} else {
		file.copy(buildTexFile, outputTexFile, overwrite=TRUE)
	}
	
		
}


#Add a "toString" which is a "show method"
setMethod("show", "LatexFile", 
	function(object) {
		cat("LatexFile (", object@parentDir, "/", object@baseFileName, ")\n")
	}
)

setGeneric("latexBuild", 
	function(latexFile, showPreview, clean) {
		standardGeneric("latexBuild")
	}
)

setMethod("latexBuild", signature=c("LatexFile", "logical"),
	function(latexFile, showPreview, clean) {
		LatexFile.buildFile(latexFile, showPreview, clean)
	}
)
#And a "summary" method
setMethod("summary", "LatexFile", 
	function(object) {
	#	cat("Experiment      : ", object@experimentName, " (", object@directoryName, ")\n")
	
	}
)
setGeneric("addSection", 
	function(latexFile, title, starred) {
		standardGeneric("addSection")
	}
)

setMethod("addSection", signature=c("LatexFile", "character", "logical"),
	function(latexFile, title, starred) {
		star<-""
		if (starred==TRUE) {
			star<-"*"
		}
		
		item<-sprintf("\\section%s{%s}", star, title)
		tList<- list(item)
		new.list<-c(latexFile@contents, tList)
		
		latexFile@contents<-new.list
		return (latexFile)
	}
)
setGeneric("addSubSection", 
	function(latexFile, title, starred) {
		standardGeneric("addSubSection")
	}
)

setMethod("addSubSection", signature=c("LatexFile", "character", "logical"),
	function(latexFile, title, starred) {
		star<-""
		if (starred==TRUE) {
			star<-"*"
		}
		item<-sprintf("\\subsection%s{%s}", star, title)
		return (addContent(latexFile, item))
	}
)
setGeneric("addSubSubSection", 
	function(latexFile, title) {
		standardGeneric("addSubSubSection")
	}
)

setMethod("addSubSubSection", signature=c("LatexFile", "character"),
	function(latexFile, title) {
		item<-sprintf("\\subsubsection*{%s}", title)
		return (addContent(latexFile, item))
	}
)

setGeneric("addTitle", 
	function(latexFile, title) {
		standardGeneric("addTitle")
	}
)

setMethod("addTitle", signature=c("LatexFile", "character"),
	function(latexFile, title) {
		item<-sprintf("\\begin{flushleft} \\huge \\textbf{%s} \\end{flushleft} \\normalsize ", title)
		tList<- list(item)
		new.list<-c(latexFile@contents, tList)
		
		latexFile@contents<-new.list
		return (latexFile)
	}
)
setGeneric("addFigure", 
	function(latexFile, filename, options, width, caption, label) {
		standardGeneric("addFigure")
	}
)

#Figure position options...
# h --- here (where the figure command is located)
# t --- at the top of the page
# b --- at the bottom of the page
# p --- on a separate page which contains only floats
#
setMethod("addFigure", signature=c("LatexFile", "character", "character", "numeric", "character", "character"),
	function(latexFile, filename, options, width, caption, label) {
		latexFile<-addContent(latexFile, sprintf("\\begin{figure}[%s]", options))
		latexFile<-addContent(latexFile, "\\begin{center}")
		latexFile<-addContent(latexFile, sprintf("\\includegraphics[width=%0.0fin]{%s}", width, filename)) 
		latexFile<-addContent(latexFile, "\\end{center}")
		latexFile<-addContent(latexFile, sprintf("\\caption{\\textit{%s}}", caption))
		latexFile<-addContent(latexFile, sprintf("\\label{%s}", label))
		latexFile<-addContent(latexFile, "\\end{figure}")
		
		
		return (latexFile)
	}
)



setGeneric("addContent", 
	function(latexFile, content) {
		standardGeneric("addContent")
	}
)

setMethod("addContent", signature=c("LatexFile", "character"),
	function(latexFile, content) {		
		tList<- list(content)
		new.list<-c(latexFile@contents, tList)
		
		latexFile@contents<-new.list
		return (latexFile)
	}
)

setGeneric("beginLongTabular", 
	function(latexFile, definition, headers, caption, label) {
		standardGeneric("beginLongTabular")
	}
)

setMethod("beginLongTabular", signature=c("LatexFile", "character", "list", "character", "character"),
	function(latexFile, definition, headers, caption, label) {		
				

		latexFile<-addContent(latexFile, sprintf("\\noindent\\begin{longtable}[l]{%s}\\center", definition))
		#%Here is the caption, the stuff in [] is the table of contents entry,
		#%the stuff in {} is the title that will appear on the first page of the
		#%table.
		
		if (caption != "NULL") {
			latexFile<-addContent(latexFile, sprintf("\\caption[%s]{%s} \\label{%s} \\\\", caption, caption, label))
		}

#		%This is the header for the first page of the table...
		header<-""
		for (i.col in 1:length(headers)) {
			i.header<-headers[[i.col]]			
			header<-paste(header, i.header)
			if (i.col == length(headers)) {
				header<-paste(header, " \\\\")
			} else {
				header<-paste(header, " & ")
			}
		}

		latexFile<-addContent(latexFile, header)
		latexFile<-addContent(latexFile, "\\hline")
		latexFile<-addContent(latexFile, "\\endfirsthead")

		#%This is the header for the remaining page(s) of the table...
		latexFile<-addContent(latexFile, header)
		latexFile<-addContent(latexFile, "\\hline")
		latexFile<-addContent(latexFile, "\\endhead")

		#%This is the footer for all pages except the last page of the table...
		latexFile<-addContent(latexFile, sprintf("\\multicolumn{%d}{l}{{Continued on Next Page\\ldots}} \\\\", length(headers)))
		latexFile<-addContent(latexFile, "\\endfoot")

		#%This is the footer for the last page of the table...

		latexFile<-addContent(latexFile, "\\\\[-1.8ex]")
		latexFile<-addContent(latexFile, "\\hline")
		latexFile<-addContent(latexFile, "\\endlastfoot")

		
		
		return (latexFile)
	}
)

setGeneric("endLongTabular", 
	function(latexFile) {
		standardGeneric("endLongTabular")
	}
)

setMethod("endLongTabular", signature=c("LatexFile"),
	function(latexFile) {		
		latexFile<-addContent(latexFile, "\\end{longtable}")
		return (latexFile)
	}
)



setGeneric("beginTabular", 
	function(latexFile, definition) {
		standardGeneric("beginTabular")
	}
)

setMethod("beginTabular", signature=c("LatexFile", "character"),
	function(latexFile, definition) {		
		cmd<-sprintf("\\noindent \\begin{tabular}{%s}", definition)
		latexFile<-addContent(latexFile, cmd)
		return (latexFile)
	}
)

setGeneric("addTableRow", 
	function(latexFile, data) {
		standardGeneric("addTableRow")
	}
)

setMethod("addTableRow", signature=c("LatexFile", "list"),
	function(latexFile, data) {		
		row<-""
		for (i.col in 1:length(data)) {
			i.data<-data[[i.col]]			
			row<-paste(row, i.data)
			if (i.col == length(data)) {
				row<-paste(row, " \\\\")
			} else {
				row<-paste(row, " & ")
			}
		}
		cmd<-sprintf("%s\n", row)
		latexFile<-addContent(latexFile, cmd)
		return (latexFile)
	}
)

setGeneric("endTabular", 
	function(latexFile) {
		standardGeneric("endTabular")
	}
)

setMethod("endTabular", signature=c("LatexFile"),
	function(latexFile) {		
		cmd<-sprintf("\\end{tabular}")
		latexFile<-addContent(latexFile, cmd)
		return (latexFile)
	}
)
