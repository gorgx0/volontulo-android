#!/bin/bash

if [[ ! -z "$1" ]]; then
    adb -s $1 shell pm clear com.stxnext.volontulo
    adb -s $1 uninstall com.stxnext.volontulo
    ADB_DEVICE_ARG=$1 calabash-android run app-debug.apk
else
    echo "Please specify target Android device"
fi
