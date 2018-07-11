find -name "*.java" > sources.txt
javac @sources.txt
start java -cp ./src/ "architecture.DbServer"
start java -cp ./src/ "architecture.ClientWithoutTax"
start java -cp ./src/ "architecture.ClientWithTax"
start java -cp ./src/ "architecture.ClientWithoutTax" 
read
find . -name "*.class" -exec rm -r "{}" \;
rm sources.txt