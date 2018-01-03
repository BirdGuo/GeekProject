//
// Created by guoxw on 2018/1/3 0003.
//

#ifndef GEEKPROJECT_HEXJNILIB_H
#define GEEKPROJECT_HEXJNILIB_H

#include <jni.h>
#include <android/log.h>

#ifdef __cplusplus
extern "C" {
#endif

/**
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    hexEncode
 * Signature: String转hex
 * @return  编码后字符串
 */
JNIEXPORT jstring JNICALL
Java_com_guoxw_geekproject_jniutil_HexUtil_hexEncode(JNIEnv *, jobject, jbyteArray);

/**
 * Class:     Java_com_guoxw_geekproject_jniutil_JNIUtil
 * Method:    hexDecode
 * Signature: hex转String
 * @return  解码后字符串
 */
JNIEXPORT jbyteArray JNICALL
Java_com_guoxw_geekproject_jniutil_HexUtil_hexDecode(JNIEnv *, jobject, jstring);

#ifdef __cplusplus
}
#endif


#endif //GEEKPROJECT_HEXJNILIB_H
