//
// Created by guoxw on 2018/1/15 0015.
//

#include <jni.h>

#ifndef GEEKPROJECT_NATIVE_CODEC_JNI_H
#define GEEKPROJECT_NATIVE_CODEC_JNI_H

#endif //GEEKPROJECT_NATIVE_CODEC_JNI_H

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jboolean JNICALL
Java_com_guoxw_geekproject_jniutil_NativeCodecUtil_createStreamingMediaPlayer(JNIEnv *, jobject,
                                                                              jbyteArray);


#ifdef __cplusplus
};
#endif
