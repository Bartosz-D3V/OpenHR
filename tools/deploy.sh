#!/usr/bin/env bash

PATH=$PATH:$(npm bin)
set -x
cd ./frontend
#npm run dist || exit 1

static='../backend/src/main/resources/static/'

if [ "$(ls -A $static)" ] ; then
  find $static -type f -not -name '.git*' -print0 | xargs --null rm -r || exit 1
fi

cp -R dist/. ../backend/src/main/resources/static || exit 1
cd ../backend
mvn clean || exit 1
mvn package -DskipTests -Pprod || exit 1
cd ..

