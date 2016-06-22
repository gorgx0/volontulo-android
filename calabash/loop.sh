#!/bin/bash

if [[ ! -z "$1" ]]; then
	while true; do
		echo "===== RUNNING TEST SUITE ====================="
    adb -s $1 shell pm clear com.stxnext.volontulo
    adb -s $1 uninstall com.stxnext.volontulo
    ADB_DEVICE_ARG=$1 calabash-android run app-debug.apk
		echo "===== TEST SUITE FINISHED ===================="
	done
else
    echo "Please specify target Android device"
fi
