//
// Created by guoxw on 2018/2/27 0027.
//
#include <jni.h>
#include <stdio.h>
#include <sys/socket.h>
#include <android/log.h>

#ifndef GEEKPROJECT_TCPIPLIB_H
#define GEEKPROJECT_TCPIPLIB_H

#define LOGTAG "GXW"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOGTAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOGTAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOGTAG, __VA_ARGS__)


#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_TcpIPUtil_startConnect(JNIEnv *, jobject);


#ifdef __cplusplus
};
#endif

#endif //GEEKPROJECT_TCPIPLIB_H
