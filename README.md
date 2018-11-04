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


## System requirements
* Install [MEGAcmd][megacmd]. Available packages for MEGAcmd in all supported 
platforms should be found [here][megacmd-install].

### Setup your credentials
1. The most common way to setup your credentials would be using environment variables:
* `MEGA_EMAIL`: Email used as username (lowercase)
* `MEGA_PWD`: Corresponding password

2. Use an existing session can be a saver way. You can use it from your app as long 
as it don't be closed.

## Continuous Integrations
As most OSS projects in github this one uses [Travis CI](https://travis-ci.com/EliuX/MEGAcmd4J). 
I you want a CLI for your project, its recommend to install the correspondent ruby gem:

```bash
    gem install travis
```


## Code style

* Follow the [Google Java Style Guide][google-java-style-guide] as much as possible.
* Install the [Google Java Style Guide settings in your IDE][google-java-style-settings-intellij].

## Known Bugs
There were tests that moved a file to a folder where another file with the same name was present.
In Windows boths files were identified as one and when I ran a move instruction for that file
name it operated over the last moved file and left the oldest one in that folder. In Linux
both files are keep in the folder but identified as different ones, even if the properties where the
same; when a move operation was run over such filename it operated over both.  

Also when I tested this on Windows I got more detailed error code, which also gives more detailed exceptions.

I suppose these issues has to do with the libraries used for making calls to the Mega API, because in the 
Linux version I had more features enabled that in the Windows one, whilst the Mega Cmd and SDK version 
were the same. It has already been reported as an [issue](https://github.com/meganz/MEGAcmd/issues/52) in the MEGAcmd project.

## More information
* Once installed [MEGAcmd][megacmd] in your system execute `mega-help` to check all commands.
You will be able to notice those who are used in this library and others which don't, for practical
reasons, but that will provide you additional capabilities. Try them out.

## Status
Pre-release or Beta: The project has gone through multiple rounds of active development with a goal of reaching 
a stable release version, but is not there yet.

Path of Development: Active (July 2nd 2018)

## Author

* **Eliecer Hernandez** - [eliecerhdz@gmail.com](mailto:eliecerhdz@gmail.com). 
To know more of me please visit my [website](http://eliux.github.io).

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

[google-java-style-guide]: https://google.github.io/styleguide/javaguide.html 
[google-java-style-settings-intellij]: https://github.com/HPI-Information-Systems/Metanome/wiki/Installing-the-google-styleguide-settings-in-intellij-and-eclipse
[mega]: https://mega.co.nz
[megacmd]: https://github.com/meganz/MEGAcmd
[megacmd-install]: https://mega.nz/cmd
[quick-start]: https://github.com/EliuX/MEGAcmd4J/wiki/Quick-start