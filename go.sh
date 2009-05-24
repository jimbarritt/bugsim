#!/bin/bash
# Type chmod +x go.sh in this folder to make it executable

echo -----------------------------------------------------------------------------
echo Building bugsim using ant in directory [$PWD] ...
echo -----------------------------------------------------------------------------


ant -f src/build/build.xml -Dbasedir=$PWD

echo -----------------------------------------------------------------------------
echo Build Complete.
echo -----------------------------------------------------------------------------
