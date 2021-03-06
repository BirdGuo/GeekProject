//
// Created by guoxw on 2018/1/22 0022.
//

#ifndef GEEKPROJECT_JNI_UI_H
#define GEEKPROJECT_JNI_UI_H


#include <jni.h>
#include <android/log.h>

#define LOGTAG "GXW"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOGTAG, __VA_ARGS__)


#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUIUtil_nativec(JNIEnv *, jobject, jobject);

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUIUtil_sureClick(JNIEnv *, jobject);

/**
 * 获取包名
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 * @return 包名
 */
JNIEXPORT jstring JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUIUtil_getPackageName(JNIEnv *, jobject, jobject);

#ifdef __cplusplus
};
#endif

#endif //GEEKPROJECT_JNI_UI_H
