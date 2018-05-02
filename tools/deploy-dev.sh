#!/usr/bin/env bash

PATH=$PATH:$(npm bin)
set -x
cd ./frontend
npm run dist || exit 1

static='../backend/src/main/resources/static/'

if [ -f $static ] ; then
  find $static -type f -not -name '.git*' -print0 | xargs -0 rm -r $static || exit 1
fi

cp -R dist/. ../backend/src/main/resources/static || exit 1
cd ../backend
mvn clean || exit 1
mvn package -DskipTests || exit 1
cd ..

