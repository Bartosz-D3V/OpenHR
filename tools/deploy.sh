#!/usr/bin/env bash

PATH=$PATH:$(npm bin)
set -x
cd ./frontend
npm run dist || exit 1
cp -R dist/. ../backend/src/main/resources/static/ || exit 1
cd ../backend
mvn package -D skipTests || exit 1
cd ..

