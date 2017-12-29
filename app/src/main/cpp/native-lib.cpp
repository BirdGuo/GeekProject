#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring

JNICALL
Java_com_guoxw_geekproject_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

jobject getInstance(JNIEnv *evn, jclass obj_class) {
    jmethodID construction_id = evn->GetMethodID(obj_class, "<init>", "()V");
    jobject obj = evn->NewObject(obj_class, construction_id);
    return obj;
}

jint countNum(JNIEnv *env,jobject){
    jint num = 1;
    return num;
}