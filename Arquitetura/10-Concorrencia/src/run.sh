find -name "*.java" > sources.txt
javac @sources.txt
start java "architecture.DbServer"
start java "architecture.ClientWithoutTax"
start java "architecture.ClientWithTax"
start java "architecture.ClientWithoutTax" 
read
find . -name "*.class" -exec rm -r "{}" \;
rm sources.txt