//
// Created by guoxw on 2018/3/1 0001.
//

#include <jni.h>

#ifndef GEEKPROJECT_SERVERLIB_H
#define GEEKPROJECT_SERVERLIB_H

#endif //GEEKPROJECT_SERVERLIB_H

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     Java_com_guoxw_geekproject_jniutil
 * Method:    nativeStartTcpServer
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_guoxw_geekproject_jniutil_ServerUtil_nativeStartTcpServer
        (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     Java_com_guoxw_geekproject_jniutil
 * Method:    nativeStartUdpServer
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_com_guoxw_geekproject_jniutil_ServerUtil_nativeStartUdpServer
        (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     Java_com_guoxw_geekproject_jniutil
 * Method:    nativeStartLocalServer
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_guoxw_geekproject_jniutil_ServerUtil_nativeStartLocalServer
        (JNIEnv *, jobject, jobject, jstring);
#ifdef __cplusplus
};
#endif