#!/usr/bin/env bashTH=$PATH:$(npm bin)
set -x

PATH=$PATH:$(npm bin)

java -jar ./backend/target/*.jar

