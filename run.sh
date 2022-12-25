export PS1=""
echo -ne '\e[9;1t'
find ./src/ -type f -name "*.java" > sources.txt
rm -rf bin
mkdir bin
javac -cp bin -sourcepath src -d bin @sources.txt
java -cp "lib/sqlite-jdbc-3.40.0.0.jar;bin" App Bash
read
exit