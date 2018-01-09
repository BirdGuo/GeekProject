//
// Created by guoxw on 2018/1/5 0005.
//

#ifndef GEEKPROJECT_CALLBACKJNILIB_H
#define GEEKPROJECT_CALLBACKJNILIB_H


#include <jni.h>
#include <android/log.h>

// Android log function wrappers
static const char *kTAG = "GXW";
#define LOGI(...) \
  ((void)__android_log_print(ANDROID_LOG_INFO, kTAG, __VA_ARGS__))
#define LOGW(...) \
  ((void)__android_log_print(ANDROID_LOG_WARN, kTAG, __VA_ARGS__))
#define LOGE(...) \
  ((void)__android_log_print(ANDROID_LOG_ERROR, kTAG, __VA_ARGS__))


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

TickContext g_ctx;

/**
 * Class:     Java_com_guoxw_geekproject_jniutil_JNICallbackUtil
 * Method:    stringFromJNI
 * Signature: Java中回调的接口，发送开始计时指令，在onResume中回调
 * @return
 */
JNIEXPORT jstring JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_stringFromJNI(JNIEnv *, jobject);


/**
 * Class:     Java_com_guoxw_geekproject_jniutil_JNICallbackUtil
 * Method:    startTicks
 * Signature: Java中回调的接口，发送开始计时指令，在onResume中回调
 */
JNIEXPORT
void JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_startTicks(JNIEnv *, jobject);

/**
 * Class:     Java_com_guoxw_geekproject_jniutil_JNICallbackUtil
 * Method:    stopTicks
 * Signature: Interface to Java side to stop ticks:
 *            we need to hold and make sure our native thread has finished before return
 *            for a clean shutdown. The caller is from onPause
 */
JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_stopTicks(JNIEnv *, jobject);

#ifdef __cplusplus
};
#endif


#endif //GEEKPROJECT_CALLBACKJNILIB_H
