#!/bin/bash
# Type chmod +x go.sh in this folder to make it executable

projectName=bugsim

antPath=`which ant`

echo -----------------------------------------------------------------------------
echo Building [$projectName] ...
echo -----------------------------------------------------------------------------
echo Using ant from [$antPath] in directory [$PWD] with command [$1]

ant -f ./build.xml -Dbasedir=$PWD -Dproject.name=$projectName $1 $2 $3 $4 $5

#type man strftime to see full list of date formatting options.
curdate=`date "+%a %d %b %Y"`
curtime=`date "+%H:%M:%S"`
echo -----------------------------------------------------------------------------
echo Build Complete at $curtime on $curdate.
echo -----------------------------------------------------------------------------
echo
echo