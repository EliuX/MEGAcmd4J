#!/bin/bash

echo $GPG_SECRET_KEYS | base64 --decode | $GPG_EXECUTABLE --import
echo $GPG_OWNERTRUST | base64 --decode | $GPG_EXECUTABLE --import-ownertrust

# Export the version to $PROJECT_VERSION
mvn help:evaluate -N -Dexpression=project.version|grep -v '\['
export PROJECT_VERSION=$(mvn help:evaluate -N -Dexpression=project.version|grep -v '\[')