svn up ../svn/compilers/a4
cp ../svn/compilers/a4/src/main/java/*.java .
make cgen
./pa4-grading.pl
