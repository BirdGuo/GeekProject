//
// Created by guoxw on 2018/1/5 0005.
//

#ifndef GEEKPROJECT_CALLBACKJNILIB_H
#define GEEKPROJECT_CALLBACKJNILIB_H


#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

typedef struct tick_context {
    JavaVM *javaVM;
    jclass jniHelperClz;
    jobject jniHelperObj;
    jclass mainActivityClz;
    jobject mainActivityObj;
    pthread_mutex_t lock;
    int done;
} TickContext;

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_startTicks(JNIEnv *, jobject);

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_stopTicks(JNIEnv *, jobject);

#ifdef __cplusplus
};
#endif


#endif //GEEKPROJECT_CALLBACKJNILIB_H
