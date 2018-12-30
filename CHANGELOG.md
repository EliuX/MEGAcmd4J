# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Before 1.2.3 
### Added 
- Added CODEOWNERS
- Added templates for github issues and PRs
- Added command to start and stop Mega local server 
- Added support for command "version"
### Changed
- All exit codes are converted always to positive to comply with POSIX standard.
### Fixed 
- Fixed #13 so local and dest paths can support whitespaces  

## Before 1.2.2 - 2018-12-16
### Changed
- Use new Unix/POSIX compliant error codes to create exceptions.
- Publish jar artifact as release in Github.

## Before 1.0 - 2018-11-04
### Added
- Add support for command `signup`.
- Add Makefile

## Before 1.0.0-beta - 2018-07-04
### Added
- Add support for command `https`.
- Add javadocs to functions and classes difficult to understand.
- Add the Google Java Style Guide references to README
### Update
- Format the the java codebase with the Google Java Style Guide settings.

## Before release 1.2.2-alpha - 2018-06-28
### Added
- Add support for commands: `login`, `logout`, `put`, `session`
   `whoami`, `mkdir`, `ls`, `get`, `cp`, `mv`, `passwd`, `share`, `export`.
- Add README with a link to the Quickstart session of the wiki of the project.
- Add stable maven pom.xml and Travis CI configuration to create releases.
