//
// Created by guoxw on 2018/1/4 0004.
//

#ifndef GEEKPROJECT_UNINSTALLLISTENERJNILIB_H
#define GEEKPROJECT_UNINSTALLLISTENERJNILIB_H

#include <android/log.h>
#include <jni.h>
#include <unistd.h>
#include <malloc.h>
#include <sys/inotify.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include<sys/file.h>
#include <sys/stat.h>

#define LOGTAG "GXWPP"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOGTAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOGTAG, __VA_ARGS__)


#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     Java_com_guoxw_geekproject_jniutil_UninstallUtil
 * Method:    callUnInstallListener
 * Signature: 卸载APP监听
 */
JNIEXPORT jint JNICALL
Java_com_guoxw_geekproject_jniutil_UninstallUtil_callUnInstallListener(JNIEnv *, jobject, jint);

#ifdef __cplusplus
}
#endif


#endif //GEEKPROJECT_UNINSTALLLISTENERJNILIB_H
