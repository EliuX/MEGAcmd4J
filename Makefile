# Made by Eliux for MEGAcmd4J

.PHONY: release
release:
	mvn release:clean release:prepare -X

.PHONY: deploy
deploy:
	mvn deploy --settings .travis/maven-settings.xml -DskipTests=true -P release -e