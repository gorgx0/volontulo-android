#!/bin/bash
adb shell pm clear com.stxnext.volontulo
adb uninstall com.stxnext.volontulo
calabash-android run app-debug.apk
