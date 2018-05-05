MEGAcmd for Java
================
This is an open source Java library for the [mega.co.nz][mega] API, working on top of the [MEGAcmd][megacmd] CLI. 
Current version supports:
* Login: Using user and password, or sessionID or an exported/public folder. See more using 
`mega-login --help`.
* Logout: Close the current session.
* Put: Upload content to the cloud. See more using `mega-put --help`.
* Session: Returns the id of the current session. See more using `mega-session --help`.
* WhoAmI: Returns the username of the current session. See more using `mega-whoami --help`.

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

* **Eliecer Hernandez** - [eliecerhdz@gmail.com](mailto:eliecerhdz@gmail.com)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

[mega]: https://mega.co.nz
[megacmd]: https://github.com/meganz/MEGAcmd
[megacmd-install]: https://mega.nz/cmd