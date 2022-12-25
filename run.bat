@echo off
if not "%1" == "max" start /MAX cmd /c %0 max & exit/b
dir src\*.java /s /b > sources.txt
rmdir /s /q bin
mkdir bin
javac -sourcepath src -cp bin;lib/sqlite-jdbc-3.40.0.0.jar -d bin @sources.txt
java -cp bin;lib/sqlite-jdbc-3.40.0.0.jar App Windows
pause