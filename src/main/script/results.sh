#! /bin/bash

echo "Please tell me which directory to download: "
read -e DIRECTORY
echo Downloading results from directory $DIRECTORY  ...
scp -r barritjame@barretts.mcs.vuw.ac.nz:/home/rialto2/barritjame/bugsim/results/$DIRECTORY/ /Users/jim/Documents/Projects/bugsim/experimental-data/raw-download/


