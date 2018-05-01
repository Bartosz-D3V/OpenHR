#!/usr/bin/env bash

PATH=$PATH:$(npm bin)
set -x
cd ./frontend
npm run dist || exit 1
cp -R dist/. ../backend/src/main/resources/static/ || exit 1
cd ../backend
mvn clean || exit 1
mvn package -DskipTests || exit 1
cd ..

