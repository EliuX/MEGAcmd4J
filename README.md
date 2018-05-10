MEGAcmd for Java
================
This is an open source Java library for the [mega.co.nz][mega] API, working on top of the [MEGAcmd][megacmd] CLI. 

## Features

| Feature                   | MEGAcmd   | Description                                                           
| ---                       | ---       | ---                                                                   
| Start session             | `login`   | Allows to authenticate into the mega api to start running commands using user and password, or sessionID or an exported/public folder  |
| Close session             | `logout`  | Close the current session. 
| Put content in the Cloud  | `put`     | Upload content to the cloud.
| Session Id                | `session` | Returns the id of the current session.
| Idenfity current username | `whoami`  | Returns the username of the current session.
| Make directory            | `mkdir`   | Creates a directory or multiple based on a given remote path.
| List files/directories    | `ls`      | List files and directories in a remote path. 
| Get content               | `get`     | Get the content of files and directories in a remote path.
| Copy                      | `cp`      | Copy remote files and directories into a new location.
| Move                      | `mv`      | Move remote files and directories into a new location.
| Change password           | `passwd`  | Changes the password of the currently logged user.
| Shares folder             | `share`   | Shares/Unshares folder with user
        
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

## Code conventions

*Java*
* Camelcase as usual for Java code
* Line length is tried (no code checkers) to be kept less than *80* characters. Specially for Java code.


## More information
* Once installed [MEGAcmd][megacmd] in your system execute `mega-help` to check all commands.
You will be able to notice those who are used in this library and others which don't, for practical
reasons, but that will provide you additional capabilities. Try them out.

## Author

* **Eliecer Hernandez** - [eliecerhdz@gmail.com](mailto:eliecerhdz@gmail.com). 
To know more of me please visit my [website](eliux.github.io).

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

[mega]: https://mega.co.nz
[megacmd]: https://github.com/meganz/MEGAcmd
[megacmd-install]: https://mega.nz/cmd