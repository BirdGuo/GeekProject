//
// Created by guoxw on 2018/2/28 0028.
//

#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>

#ifndef GEEKPROJECT_EXCEPTIONLIB_H
#define GEEKPROJECT_EXCEPTIONLIB_H

// Max log message length
#define MAX_LOG_MESSAGE_LENGTH 256

// Max data buffer size
#define MAX_BUFFER_SIZE 80

#endif //GEEKPROJECT_EXCEPTIONLIB_H

/**
 * 打印日志
 * @param env
 * @param obj
 * @param format
 * @param ...
 */
static void LogMessage(JNIEnv *env, jobject obj, const char *format, ...) {

    static jmethodID methodId = NULL;
    if (NULL == methodId) {
        jclass clazz = env->GetObjectClass(obj);
        //找到java中的方法
        methodId = env->GetMethodID(clazz, "logMessage", "(Ljava/lang/String;)V");
        env->DeleteLocalRef(clazz);
    }

    if (NULL != methodId) {
        char buffer[MAX_LOG_MESSAGE_LENGTH];

        va_list ap;
        va_start(ap, format);
        vsnprintf(buffer, MAX_LOG_MESSAGE_LENGTH, format, ap);

        va_end(ap);

        jstring message = env->NewStringUTF(buffer);
        if (NULL != message) {
            //调用java 方法
            env->CallVoidMethod(obj, methodId, message);
            env->DeleteLocalRef(message);
        }
    }

}

/**
 * 抛出一个异常
 * @param env JNI接口
 * @param className 类名
 * @param message 异常信息
 */
static void ThrowException(JNIEnv *env, const char *className, const char *message) {

    jclass clazz = env->FindClass(className);
    if (NULL != clazz) {
        //抛出异常
        env->ThrowNew(clazz, message);
        //释放本地资源
        env->DeleteLocalRef(clazz);
    }
}

/**
 * 抛出一个异常
 * @param env
 * @param className
 * @param errnum
 */
static void ThrowErrnoException(JNIEnv *env, const char *className, int errnum) {
    char buffer[MAX_LOG_MESSAGE_LENGTH];
    //获得错误编号
    if (-1 == strerror_r(errnum, buffer, MAX_LOG_MESSAGE_LENGTH)) {
        strerror_r(errno, buffer, MAX_LOG_MESSAGE_LENGTH);
    }
    ThrowException(env, className, buffer);
}