Welcome to the bugsim source code!

This directory is kept very clean, so there is only one command you need to know about, "go".

The 'go' shell script
=====================
The first time you checkout the code, you will need to make the script executable.

To do this, run "chmod +x go" at the shell.

To make things easier, add the following line to your bash profile:

export PATH=.:$PATH

This script is a wrapper for our build process. 

There are several commands you might want to pass it. 

go test             -->  compiles and executes unit tests.
go build-osx-app    -->  compiles, tests and ends up building you a nice .app package in the /target/app directory

