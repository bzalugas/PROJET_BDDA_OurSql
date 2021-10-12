#!/usr/bin/env bash

javac -d CODE/bin -cp CODE/src CODE/src/*.java
java -cp CODE/bin "$1Tests" ./DB
