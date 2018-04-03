#!/usr/bin/env bash
PATH=$PATH:$(npm bin)
set -x

# Production build
ng build --prod --build-optimizer --aot --no-sourcemap --extract-licenses=false

cd ./dist
http-server -p 4200
#http-server -p 4200 --proxy http://localhost:8080

trap cleanup EXIT
cd ..
