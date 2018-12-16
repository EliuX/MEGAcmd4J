#!/usr/bin/env bash
mvn help:evaluate -N -Dexpression=project.version|grep -v '\['
export TAG=$(mvn help:evaluate -N -Dexpression=project.version|grep -v '\[')
git tag -d v$TAG
git tag -a v$TAG -m "MEGAcmd4J $TAG"
git push --tags -f