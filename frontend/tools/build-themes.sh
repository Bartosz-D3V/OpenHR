#!/bin/bash

DEST_PATH=./src/assets
INPUT_PATH=$DEST_PATH/themes/


echo Building custom themes from scss files.

FILES=$(find ./src/assets/themes -name "*.scss")

for FILE in $FILES
do
  FILENAME=${FILE#$INPUT_PATH}
  BASENAME=${FILENAME%.scss}
  $(npm bin)/node-sass $FILE > $DEST_PATH/themes/$BASENAME.css
done

echo Finished building CSS.
