#!/usr/bin/env bash
PATH=$PATH:$(npm bin)
set -x

# Production build
ng build --prod

cd ./dist
http-server -p 4200
