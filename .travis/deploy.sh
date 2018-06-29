#!/usr/bin/env bash
echo $GPG_SECRET_KEYS | base64 --decode | $GPG_EXECUTABLE --import
echo $GPG_OWNERTRUST | base64 --decode | $GPG_EXECUTABLE --import-ownertrust
mvn deploy --settings .travis/maven-settings.xml -DskipTests=true -P release