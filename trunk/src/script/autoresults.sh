#! /bin/bash


echo Downloading results from directory $3 on $1@$2 ...
scp -r $1@$2:~/bugsim/results/$3/ /Users/jim/Documents/Projects/bugsim/experimental-data/raw-download/
echo Complete.

