mvn clean package
clear
mvn exec:java -Dexec.mainClass="src.Runner" -Dexec.cleanupDaemonThreads=false
read