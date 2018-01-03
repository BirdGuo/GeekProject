//
// Created by guoxw on 2018/1/3 0003.
//

#include "myJniLib.h"

JNIEXPORT jstring JNICALL Java_com_guoxw_geekproject_jniutil_JNIUtil_getCLanguageString
        (JNIEnv *env, jobject obj) {
    return env->NewStringUTF("Hello World C");
}

JNIEXPORT jint JNICALL Java_com_guoxw_geekproject_jniutil_JNIUtil_calAAndB
        (JNIEnv *env, jobject obj, jint a, jint b) {
    return (a + b);
}

JNIEXPORT void
Java_com_guoxw_geekproject_jniutil_JNIUtil_printLogInfo(JNIEnv *env, jobject, jstring str_) {
    const char *str = env->GetStringUTFChars(str_, NULL);
    //发送字符串到Log队列
    __android_log_write(ANDROID_LOG_INFO, LOG_TAG, str);
    //释放string
    env->ReleaseStringUTFChars(str_, str);
}

JNIEXPORT void
Java_com_guoxw_geekproject_jniutil_JNIUtil_printLogDebug(JNIEnv *env, jobject, jstring str_) {
    const char *str = env->GetStringUTFChars(str_, NULL);
    //发送字符串到Log队列
    __android_log_write(ANDROID_LOG_DEBUG, LOG_TAG, str);
    //释放string
    env->ReleaseStringUTFChars(str_, str);
}

JNIEXPORT void
Java_com_guoxw_geekproject_jniutil_JNIUtil_printLogWarning(JNIEnv *env, jobject, jstring str_) {
    const char *str = env->GetStringUTFChars(str_, NULL);
    //发送字符串到Log队列
    __android_log_write(ANDROID_LOG_WARN, LOG_TAG, str);
    //释放string
    env->ReleaseStringUTFChars(str_, str);
}

JNIEXPORT void
Java_com_guoxw_geekproject_jniutil_JNIUtil_printLogError(JNIEnv *env, jobject, jstring str_) {
    const char *str = env->GetStringUTFChars(str_, NULL);
    //发送字符串到Log队列
    __android_log_write(ANDROID_LOG_ERROR, LOG_TAG, str);
    //释放string
    env->ReleaseStringUTFChars(str_, str);
}