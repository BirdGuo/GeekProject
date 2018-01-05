//
// Created by guoxw on 2018/1/5 0005.
//

#include <sys/types.h>
#include <pthread.h>
#include "callbackJniLib.h"

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_startTicks(JNIEnv *env, jobject) {

    pthread_t threadInfo_;
    pthread_attr_t threadAttr_;

    pthread_attr_init(&threadAttr_);
    pthread_attr_setdetachstate(&threadAttr_,PTHREAD_CREATE_DETACHED);

    pthread_attr_init(&g_c)

}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_stopTicks(JNIEnv *env, jobject) {

}
