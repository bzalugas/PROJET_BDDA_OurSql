find ./CODE/src/kernel -name "*.java" > sources.txt
# find ./CODE/src/ker -name "*.java" >> sources.txt
echo ./CODE/src/Main.java >> sources.txt
# CODE/src/kernel/*.java CODE/src/kernel/exceptions/*.java
javac -d CODE/bin -cp CODE/src @sources.txt
java -cp CODE/bin Main ./DB
rm sources.txt


##Windows
# javac -d CODE/bin -cp CODE/src CODE/src/*.java
# javac -d CODE/bin -cp CODE/src CODE/src/kernel/*.java
# javac -d CODE/bin -cp CODE/src CODE/src/kernel/exceptions/*.java

# java -cp CODE/bin **** ./DB
