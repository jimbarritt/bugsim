#! /bin/bash
# To make a script executable do chmod +x
#"/usr/local/teTeX/bin/powerpc-apple-darwin-current/pdflatex" -output-directory ./output -interaction=nonstopmode %.tex|open -a texniscope ./output/%.pdf
#"/usr/local/teTeX/bin/powerpc-apple-darwin-current/pdflatex" 

#-output-directory ./output -interaction=nonstopmode %.tex|open -a texniscope ./output/%.pdf

LATEX_CMD="/usr/local/teTeX/bin/powerpc-apple-darwin-current/pdflatex -interaction=nonstopmode"


#BIBTEX_CMD="/usr/local/teTeX/bin/powerpc-apple-darwin-current/bibtex"

echo Building LaTeX document ...
echo texFile   : [$1] 
echo outputDir : [$2]
#if [ ! -d "output" ]
#then
#mkdir output
#mkdir output/chapter-1
#mkdir output/chapter-2
#fi

$LATEX_CMD  -output-directory $2 $1 

#if $LATEX_CMD seperate-bibs.tex > /dev/stderr
#then
#	echo Merging chapter bib files... > /dev/stderr
#	if [ -f "./output/combined.bib"]
#	then
#		rm ./output/combined.bib
#	fi
#	
#	cat ./chapter-1/chapter-1.bib ./chapter-2/chapter-2.bib > ./output/complete.bib
#	
#	echo Creating BibTex files ... > /dev/stderr
#	$BIBTEX_CMD ./output/seperate-bibs  > /dev/stderr
#	$BIBTEX_CMD ./output/chapter-1/chapter-1  > /dev/stderr
#	$BIBTEX_CMD ./output/chapter-2/chapter-2  > /dev/stderr
#
#
#	echo Rebuilding Latex File Twice To update references... >/dev/stderr
#	$LATEX_CMD seperate-bibs.tex > /dev/null #/dev/null stops the output from appearing on the screen. 
#	$LATEX_CMD seperate-bibs.tex > /dev/null
#	echo Complete, launching preview... > /dev/stderr
#
#	open -a texniscope ./output/seperate-bibs.pdf
	#osascript "reloadpreview.scpt" 
#else
#	return -1
#fi