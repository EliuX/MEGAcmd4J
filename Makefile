# Made by Eliux for MEGAcmd4J
#To update version
# Update to SNAPSHOT version with mvn versions:set -DnewVersion=<version>-SNAPSHOT if necessary
# make release (Increase the version)
# source tag.sh (This will create a tag which in travis will call deploy)

.PHONY: release
release:
	mvn release:clean release:prepare -X

.PHONY: deploy
deploy:
	mvn deploy --settings .travis/maven-settings.xml -DskipTests=true -P release
