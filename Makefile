# Made by Eliux for MEGAcmd4J

.PHONY: release
release:
	mvn release:clean release:prepare -X

.PHONY: tag
tag:
	mvn help:evaluate -N -Dexpression=project.version
	export TAG=$(shell mvn help:evaluate -N -Dexpression=project.version|grep -v '\[')
	git tag -a v$TAG -m "MEGAcmd4J $TAG"
	git push --tags

.PHONY: deploy
deploy:
	mvn deploy --settings .travis/maven-settings.xml -DskipTests=true -P release
