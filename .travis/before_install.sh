#!/bin/bash
if [[ $TRAVIS_OS_NAME == 'osx' ]]; then
 # Install some custom requirements on macOS
 curl -O https://mega.nz/MEGAcmdSetup.dmg
 sudo hdiutil attach MEGAcmdSetup.dmg
 sudo cp -R /Volumes/MEGAcmd/MEGAcmd.app /Applications
 export PATH=/Applications/MEGAcmd.app/Contents/MacOS:$PATH
else
 # It must be linux
 # Megacmd required ubuntu packages
 sudo apt-get -y update
 sudo apt-get -y install libc-ares2 libcrypto++9 libmediainfo0 libzen0

 # Install Megacmd
 wget https://mega.nz/linux/MEGAsync/xUbuntu_14.04/amd64/megacmd-xUbuntu_14.04_amd64.deb
 sudo dpkg -i ./megacmd-xUbuntu_14.04_amd64.deb
fi