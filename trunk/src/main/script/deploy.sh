#! /bin/bash

SERVER=barretts.mcs.vuw.ac.nz
VERSION=@version@
DISTRIBUTION=@distribution@
BUGSIM_JAR=bugsim-application-$VERSION-$DISTRIBUTION.jar

echo Deploying version $VERSION to $SERVER ...
scp sense.sh barritjame@$SERVER:/home/rialto2/barritjame/bugsim/sense.sh
scp ../dist/$BUGSIM_JAR barritjame@$SERVER:/home/rialto2/barritjame/bugsim/$BUGSIM_JAR
scp -r ../../resource/experiments/chapter-03/*.xml barritjame@barretts.mcs.vuw.ac.nz:/home/rialto2/barritjame/bugsim/experiments/chapter-03
scp -r ../../resource/experiments/chapter-04/*.xml barritjame@barretts.mcs.vuw.ac.nz:/home/rialto2/barritjame/bugsim/experiments/chapter-04

