#!/bin/bash

#ndk-build && ant.bat install

ant clean
android update project -p .
ant release
