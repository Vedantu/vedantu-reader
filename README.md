vedantu-reader
==============

Lightweight and runtime customizable PDF Reader.


## About this app

This customizable app is intended mainly for app developers. However, without any customization it works as a normal PDF Viewer with all capabilities provided by the base platform, which is [MuPDF](http://www.mupdf.com/).

## For Developers

If you want to launch a PDF from your Android app in a secure PDF Viewer you can use this application. While launching through an [Intent](http://developer.android.com/reference/android/content/Intent.html) it allows you to control various buttons of the PDF Viewer. Following are the customizable buttons:
- Link
- Reflow
- Outline
- Search
- More
- Highlight
- Underline
- Strike Out
- Ink
- Print
- Copy Text
- Edit Annotation

You need to pass a [String array as Extra in the Intent](http://developer.android.com/reference/android/content/Intent.html#putExtra(java.lang.String, java.lang.String[]). The name will be *VEDANTU_READER_HIDE_BUTTONS*. The values that you specify in the String array will be the buttons that will be hidden from the _vedantu-reader_ when its launched.

**Refer:** [Constants](https://github.com/Vedantu/vedantu-reader/blob/master/android/src/com/vedantu/android/reader/customize/Constants.java) class for the keys to use for hiding buttons.

**Note:** At present the customizable properties of this application are available only on Android. iOS and Windows 8 applications might follow soon.


## License
Vedantu Reader is free software: you can redistribute it and/or modify it under the terms of the Affero GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This project is Copyright 2013-2014 Vedantu Innovations Pvt Ltd, except included libraries:
- Copyright 2006-2013 Artifex Software, Inc for the MuPDF library

The use of the Google Analytics library is governed by the following terms: http://www.google.com/analytics/tos.html


## Source code of libraries used
- MuPDF: git://git.ghostscript.com/mupdf.git

