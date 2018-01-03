//
// Created by guoxw on 2018/1/3 0003.
//

#include <jni.h>
#include <android/log.h>

#ifndef GEEKPROJECT_MYJNILIB_H
#define GEEKPROJECT_MYJNILIB_H
#define LOG_TAG "CPPGXW"

#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_guoxw_geekproject_jniutil_JNIUtil_getCLanguageString
        (JNIEnv *, jobject);

/*
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    calAAndB
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_guoxw_geekproject_jniutil_JNIUtil_calAAndB
        (JNIEnv *, jobject, jint, jint);

/*
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    printLogInfo
 * Signature: 输出info类型的log
 */
JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUtil_printLogInfo(JNIEnv *, jobject, jstring);

/*
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    printLogDebug
 * Signature: 输出debug类型的log
 */
JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUtil_printLogDebug(JNIEnv *, jobject, jstring);

/*
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    printLogWarning
 * Signature: 输出warn类型的log
 */
JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUtil_printLogWarning(JNIEnv *, jobject, jstring);

/*
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    printLogError
 * Signature: 输出error类型的log
 */
JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUtil_printLogError(JNIEnv *, jobject, jstring);

#ifdef __cplusplus
}
#endif


#endif //GEEKPROJECT_MYJNILIB_H
