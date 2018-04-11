#!/usr/bin/env bash

PATH=$PATH:$(npm bin)
set -x

cd ./backend
./mvnw com.coveo:fmt-maven-plugin:format
cd ..

