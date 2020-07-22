# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Before 1.5.0 - 2020-07-21
### Added
- Add support for command `import`
- Added `option` password to `export`
## Before 1.4.0 - 2019-05-24
### Update
- Updated implementation of `passwd`.
### Changed
- Increase MEGA_CMD_TTL to 1 minute in Travis CI: Allow hardworking tasks to pass.
- MegaWrongArgumentsException will replace MegaCmdInvalidArgumentException (Different codes for Win and Unix)

### Fixed
- Fixed parsing of commands, which made that the server could not start in Windows.
- Fixed parsing of the version, i.e. New prefix (MEGAcmd version). Now it is not fixed.

## Before 1.3.0 - 2019-01-20
### Added 
- Added CODEOWNERS
- Added github templates for issues and PRs
- Added command to start and stop Mega local server 
- Added support for command "version"
- Added support for MegaCmdInvalidArgumentException (different MegaWrongArgumentsException)
### Changed
- All exit codes are converted always to positive to comply with POSIX standard.
- Commands instead of being passed as a String to the process builder are now 
passed as an array: It avoids problems related to the use of spaces on paths and parameters. 
It also makes more sense to get the parameters as a list.
### Fixed 
- Fixed #13 so local and dest paths can support whitespaces. 
- Fixed #11, so remotePath was changed in code.
- Fixed #8, so when Mega.init is called the MEGAcmdServer is started.
- Fixed #10, uploading issue: Related to the use of whitespaces.
- Fixed #9, When the first call never ended because the server had not started.

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
