#!/usr/bin/env bash

javac -d CODE/bin CODE/src/*.java
java -cp CODE/bin "$1Tests" ./DB
