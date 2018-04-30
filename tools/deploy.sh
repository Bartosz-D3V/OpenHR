#!/usr/bin/env bash

PATH=$PATH:$(npm bin)
set -x
cd ../frontend
npm run dist
cp -R dist/. ../backend/src/main/resources/static/
cd ../backend
mvn package -D skipTests
cd ..
