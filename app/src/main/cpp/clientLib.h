//
// Created by guoxw on 2018/3/1 0001.
//

#include <jni.h>

#ifndef GEEKPROJECT_CLIENTLIB_H
#define GEEKPROJECT_CLIENTLIB_H

#endif //GEEKPROJECT_CLIENTLIB_H

#ifdef __cplusplus
extern "C" {
#endif

/**
 * Class
 * @param ip
 * @param port
 * @param message
 */
JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_ClientUtil_nativeStartTcpClient(JNIEnv *, jobject, jstring ip,
                                                                   jint port, jstring message);

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_ClientUtil_nativeStartUdpClient(JNIEnv *, jobject, jstring ip,
                                                                   jint port, jstring message);

#ifdef __cplusplus
};
#endif