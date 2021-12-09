javac -d CODE/bin -cp CODE/src CODE/src/*.java
java -cp CODE/bin Main ./DB


##Windows
javac -d CODE/bin -cp CODE/src CODE/src/*.java
javac -d CODE/bin -cp CODE/src CODE/src/kernel/*.java
javac -d CODE/bin -cp CODE/src CODE/src/kernel/exceptions/*.java

java -cp CODE/bin **** ./DB
