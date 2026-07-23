#!/bin/sh
chmod +x gradlew
./gradlew --parallel --build-cache generateJavascript
