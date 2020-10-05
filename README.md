<a href="https://eliux.github.io/MEGAcmd4J/">
  <img src="https://eliux.github.io/assets/images/MEGAcmd4J.png" alt="MEGAcmd for Java"  title="MEGAcmd for Java" />
</a>

# MEGAcmd for Java

[![Build Status](https://travis-ci.com/EliuX/MEGAcmd4J.svg?branch=master)](https://travis-ci.com/EliuX/MEGAcmd4J)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.eliux/megacmd4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.eliux/megacmd4j)
[![Twitter URL](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/eliux_black)

This is an open source Java library for the [mega.nz][mega] API, working on top of the [MEGAcmd][megacmd] CLI. 
There is a [Quick Start][quick-start] available for developers who want to start using the library right away.

## Features

| Feature                   | MEGAcmd   | Description                                                           
| ---                       | ---       | ---                                                                   
| Start session             | `login`   | Allows to authenticate into the mega api to start running commands using user and password, or sessionID or an exported/public folder  |
| Close session             | `logout`  | Close the current session. 
| Put content in the Cloud  | `put`     | Upload content to the cloud.
| Session Id                | `session` | Returns the id of the current session.
| HTTPS                     | `https`   | Shows if HTTPS is used for transfers. Allows enabling and disabling this option.
| Idenfity current username | `whoami`  | Returns the username of the current session.
| Make directory            | `mkdir`   | Creates a directory or multiple based on a given remote path.
| List files/directories    | `ls`      | List files and directories in a remote path. 
| Get content               | `get`     | Get the content of files and directories in a remote path.
| Copy                      | `cp`      | Copy remote files and directories into a new location.
| Move                      | `mv`      | Move remote files and directories into a new location.
| Change password           | `passwd`  | Changes the password of the currently logged user.
| Shares folder             | `share`   | Shares/Unshares folder with user
| Export to the Internet    | `export`  | Create, delete or list existing public links
| Signup user               | `signup`  | Register as user with a given email
| Version                   | `version` | Prints MEGAcmd versioning and extra info
| Quit                      | `quit`    | Quits MEGAcmd. Stops the server without killing the session
| Import                    | `import`  | Imports the contents of a remote link into user's cloud
        
If you have any doubt about how each feature works, please run

```bash
    MegaClient <MEGAcmd> --help
```

## Additional Features
This features have no corresponding Megacmd command, but they are infered from the existing ones

### Count
Show the amount of elements in a folder or that match the query mask. E.g.

```java
    final long amountOfFilesAndDirectories = sessionMega.count("remote/path");
```

But what if you want to filter that counting and it a Predicate to, for instance,
just get the files and not the directories.

```java
    final long amountOfFiles = sessionMega.count("remote/path", FileInfo::isFile);
```

### Exists
This is pretty much the equivalent of `java.io.File.exists`. It checks if the remote path or mask
indicates any non null response. E.g.
 
```java
    sessionMega.exists("remote/path/filesprefix*.ext");
```

### Export with an expiration date
With a premium mega account, you can export and generate public folder links by setting the option `expire date`.
```java
    LocalDate expireDate = LocalDate.of(2020, 9, 2);
    final ExportInfo exportInfo = sessionMega.export(exportFolder)
            .setExpireDate(expireDate)
            .call();
```

or you can just specify a duration using `TimeDelay`.

```java  
    final ExportInfo exportInfo = sessionMega.export(exportFolder)
        .setExpirationTimeDelay(TimeDelay.of(
             Period.ofYears(3).plusMonths(11).plusDays(15)
         ))
        .call();
```

### MEGAcmdServer
Thanks to the class `io.github.eliux.mega.MegaServer` you can now `start` and `stop` the local MEGAcmdServer on command.

## System requirements
* Install [MEGAcmd][megacmd]. Available packages for MEGAcmd in all supported 
platforms should be found [here][megacmd-install].

### Environment variables to set

* `MEGA_EMAIL`: Email used as username (lowercase)
* `MEGA_PWD`: Corresponding password
* `CMD_TTL_ENV_VAR`: Maximum time to live of a running MEGAcmd command. By default, 20 seconds.

### Setup your credentials
1. The most common way to setup your credentials would be using the environment variables `MEGA_EMAIL` 
and `MEGA_PWD`.

2. Use an existing session can be a saver way. You can use it from your app as long 
as it don't be closed.

## Continuous Integrations
As most OSS projects in github, this one uses [Travis CI](https://travis-ci.com/EliuX/MEGAcmd4J). 
I you want a CLI for your project, its recommend to install the correspondent ruby gem:

```bash
    gem install travis
```

## For users
* Once installed [MEGAcmd][megacmd] in your system, execute `mega-help` to check all commands.
You will be able to notice those who are already implemented in this library and those who don't. Try them out.

## Contributing
Make sure your contact data is included in the **Contributors** section, and

* Follow the [Google Java Style Guide][google-java-style-guide] as much as possible: Its recommended that you install the 
[Google Java Style Guide settings in your IDE][google-java-style-settings-intellij]. 
* Respect as much as possible the format of the provided templates for issues and PRs.

# Contributors
Add your name, email and your account for Twitter, Github or LinkedIn:
- **Cenyo Medewou** - [medewou@gmail.com](mailto:medewou@gmail.com) | [Github](https://github.com/ccenyo) | [Linkedin](https://www.linkedin.com/in/cenyomedewou)



## Built With
* [Java SDK 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) -  Java™ Platform
* [Maven](https://maven.apache.org/) - Dependency Management
* [MEGAcmd](https://github.com/meganz/MEGAcmd) - Command Line Interactive and Scriptable Application

## Author
* **Eliecer Hernandez** - [eliecerhdz@gmail.com](mailto:eliecerhdz@gmail.com). 
For more information, please visit my [website](http://eliux.github.io).

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

[google-java-style-guide]: https://google.github.io/styleguide/javaguide.html 
[google-java-style-settings-intellij]: https://github.com/HPI-Information-Systems/Metanome/wiki/Installing-the-google-styleguide-settings-in-intellij-and-eclipse
[mega]: https://mega.co.nz
[megacmd]: https://github.com/meganz/MEGAcmd
[megacmd-install]: https://mega.nz/cmd
[quick-start]: https://github.com/EliuX/MEGAcmd4J/wiki/Quick-start
