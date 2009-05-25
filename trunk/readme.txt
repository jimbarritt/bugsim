Welcome to the bugsim source code!

This directory is kept very clean, so there is only one command you need to know about, "go".

The 'go' shell script
=====================

This script is a wrapper for our build process. 

There are several commands you might want to pass it. 

go test             -->  compiles and executes unit tests.
go build-osx-app    -->  compiles, tests and ends up building you a nice .app package in the /target/app directory

