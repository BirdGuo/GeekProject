#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUtil_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

/**
 * 内部调用方法需要写在export方法之上
 * @return num
 */
jint testOne() {
    jint num = 2;
    return num;
}

extern "C"
JNIEXPORT jint

JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUtil_countFromJNI(JNIEnv *env, jobject) {
    jint num = 1;
    num = testOne();
    return num;
}

extern "C"
JNIEXPORT jint

JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUtil_logInCPPI(JNIEnv *env, jobject){

}