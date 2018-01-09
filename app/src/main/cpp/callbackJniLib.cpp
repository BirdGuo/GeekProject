//
// Created by guoxw on 2018/1/5 0005.
//

#include <pthread.h>
#include "callbackJniLib.h"
#include <assert.h>
#include <string.h>
#include <inttypes.h>

/*
 *  A helper function to show how to call
 *     java static functions JniHelper::getBuildVersion()
 *     java non-static function JniHelper::getRuntimeMemorySize()
 *  The trivial implementation for these functions are inside file
 *     JniHelper.java
 */
void queryRuntimeInfo(JNIEnv *env, jobject instance) {
    // Find out which OS we are running on. It does not matter for this app
    // just to demo how to call static functions.
    // Our java JniHelper class id and instance are initialized when this
    // shared lib got loaded, we just directly use them
    //    static function does not need instance, so we just need to feed
    //    class and method id to JNI
    jmethodID versionFunc = env->GetStaticMethodID(
            g_ctx.jniHelperClz,
            "getBuildVersion", "()Ljava/lang/String;");
    if (!versionFunc) {
        LOGE("Failed to retrieve getBuildVersion() methodID @ line %d",
             __LINE__);
        return;
    }

    jstring buildVersion = (jstring) env->CallStaticObjectMethod(g_ctx.jniHelperClz, versionFunc);
    const char *version = env->GetStringUTFChars(buildVersion, NULL);
    if (!version) {
        LOGE("Unable to get version string @ line %d", __LINE__);
        return;
    }
    LOGI("Android Version - %s", version);
    env->ReleaseStringUTFChars(buildVersion, version);

    // we are called from JNI_OnLoad, so got to release LocalRef to avoid leaking
    env->DeleteLocalRef(buildVersion);

    // Query available memory size from a non-static public function
    // we need use an instance of JniHelper class to call JNI
    jmethodID memFunc = env->GetMethodID(g_ctx.jniHelperClz,
                                         "getRuntimeMemorySize", "()J");
    if (!memFunc) {
        LOGE("Failed to retrieve getRuntimeMemorySize() methodID @ line %d",
             __LINE__);
        return;
    }
    jlong result = env->CallLongMethod(instance, memFunc);
    LOGI("Runtime free memory size: %"PRId64, result);
    (void) result;  // silence the compiler warning
}

/*
 * processing one time initialization:
 *     Cache the javaVM into our context
 *     Find class ID for JniHelper
 *     Create an instance of JniHelper
 *     Make global reference since we are using them from a native thread
 * Note:
 *     All resources allocated here are never released by application
 *     we rely on system to free all global refs when it goes away;
 *     the pairing function JNI_OnUnload() never gets called at all.
 */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    memset(&g_ctx, 0, sizeof(g_ctx));

    g_ctx.javaVM = vm;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR; // JNI version not supported.
    }

    jclass clz = env->FindClass(
            "com/guoxw/geekproject/jniui/JniHandler");
    g_ctx.jniHelperClz = (jclass) env->NewGlobalRef(clz);

    jmethodID jniHelperCtor = env->GetMethodID(g_ctx.jniHelperClz,
                                               "<init>", "()V");
    jobject handler = env->NewObject(g_ctx.jniHelperClz,
                                     jniHelperCtor);
    g_ctx.jniHelperObj = env->NewGlobalRef(handler);
    queryRuntimeInfo(env, g_ctx.jniHelperObj);

    g_ctx.done = 0;
    g_ctx.mainActivityObj = NULL;
    return JNI_VERSION_1_6;
}

/**
 *
 * @param env
 * @param instance
 * @param func
 * @param msg
 */
void sendJavaMsg(JNIEnv *env, jobject instance,
                 jmethodID func, const char *msg) {
    //创建一个新的字符串
    jstring javaMsg = env->NewStringUTF(msg);
    /**
     * 注意 GetXXXMethodID  和 CallXXXMethod 。
     * 第一个XXX 表示的是映射方法的类型，如： 静态 跟非静态
     * 第二个XXX 表示 调用方法的返回值 ，如：Void,Object,等等。（调用静态方法的时候Call后面要加Static）
     */
    env->CallVoidMethod(instance, func, javaMsg);
    /**
     * native method 调用 DeleteLocalRef() 释放某个 JNI Local Reference 时，
     * 首先通过指针 p 定位相应的 Local Reference 在 Local Ref 表中的位置，
     * 然后从 Local Ref 表中删除该 Local Reference，也就取消了对相应 Java 对象的引用（Ref count 减 1）。
     */
    env->DeleteLocalRef(javaMsg);

}

/**
 *
 * @param context
 * @return
 */
void *UpdateTicks(void *context) {
    LOGI("-------3-------");
    TickContext *pctx = (TickContext *) context;
    LOGI("-------4-------");
    JavaVM *javaVM = pctx->javaVM;
    //这行出现了空指针111
    JNIEnv *env;
    LOGI("-------5-------");
    if (env == NULL) {
        LOGI("-------7-------");
    }
    //这行出现了空指针
    jint res = javaVM->GetEnv((void **) &env, JNI_VERSION_1_6);
    LOGI("-------6-------");
    if (res != JNI_OK) {
        res = javaVM->AttachCurrentThread(&env, NULL);
        if (JNI_OK != res) {
            LOGE("Failed to AttachCurrentThread, ErrorCode = %d", res);
            return NULL;
        }
    }

    jmethodID statusId = env->GetMethodID(pctx->jniHelperClz, "updateStatus",
                                          "(Ljava/lang/String;)V");
    sendJavaMsg(env, pctx->jniHelperObj, statusId, "TickerThread status: initializing...");

    jmethodID timerId = env->GetMethodID(pctx->mainActivityClz, "updateTimer", "()V");
    struct timeval beginTime, curTime, usedTime, leftTime;
    const struct timeval kOneSecond = {
            (__kernel_time_t) 1, (__kernel_suseconds_t) 0
    };

    sendJavaMsg(env, pctx->jniHelperObj, statusId, "TickerThread status: start ticking ...");

    while (1) {
        gettimeofday(&beginTime, NULL);
        pthread_mutex_lock(&pctx->lock);
        int done = pctx->done;
        //为什么这儿要这么写，而下面直接用done
        if (pctx->done) {
            pctx->done = 0;
        }

        pthread_mutex_unlock(&pctx->lock);
        if (done) {
            break;
        }
        //pctx.mainActivityObj和pctx->mainActivityObj有什么区别
        env->CallVoidMethod(pctx->mainActivityObj, timerId);
        gettimeofday(&curTime, NULL);
        timersub(&curTime, &beginTime, &usedTime);
        timersub(&kOneSecond, &usedTime, &leftTime);

        struct timespec sleepTime;
        sleepTime.tv_sec = leftTime.tv_sec;
        sleepTime.tv_nsec = leftTime.tv_sec * 1000;
        if (sleepTime.tv_sec <= 1) {
            nanosleep(&sleepTime, NULL);
        } else {
            sendJavaMsg(env, pctx->jniHelperObj, statusId,
                        "TickerThread error: processing too long!");
        }
    }

    sendJavaMsg(env, pctx->jniHelperObj, statusId, "TickerThread status: ticking stopped");
    javaVM->DetachCurrentThread();
    return context;

}

JNIEXPORT jstring JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_stringFromJNI(JNIEnv *env, jobject thiz) {
#if defined(__arm__)
#if defined(__ARM_ARCH_7A__)
#if defined(__ARM_NEON__)
#if defined(__ARM_PCS_VFP)
#define ABI "armeabi-v7a/NEON (hard-float)"
#else
#define ABI "armeabi-v7a/NEON"
#endif
#else
#if defined(__ARM_PCS_VFP)
#define ABI "armeabi-v7a (hard-float)"
#else
#define ABI "armeabi-v7a"
#endif
#endif
#else
#define ABI "armeabi"
#endif
#elif defined(__i386__)
#define ABI "x86"
#elif defined(__x86_64__)
#define ABI "x86_64"
#elif defined(__mips64)  /* mips64el-* toolchain defines __mips__ too */
#define ABI "mips64"
#elif defined(__mips__)
#define ABI "mips"
#elif defined(__aarch64__)
#define ABI "arm64-v8a"
#else
#define ABI "unknown"
#endif
    return env->NewStringUTF("Hello from JNI !  Compiled with ABI " ABI ".");
}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_startTicks(JNIEnv *env, jobject instance) {

    pthread_t threadInfo_;
    pthread_attr_t threadAttr_;

    pthread_attr_init(&threadAttr_);
    pthread_attr_setdetachstate(&threadAttr_, PTHREAD_CREATE_DETACHED);

    pthread_mutex_init(&g_ctx.lock, NULL);

    jclass clz = env->GetObjectClass(instance);
    g_ctx.mainActivityClz = (jclass) env->NewGlobalRef(clz);
    g_ctx.mainActivityObj = env->NewGlobalRef(instance);
    LOGI("-------1-------");
    int result = pthread_create(&threadInfo_, &threadAttr_, UpdateTicks, &g_ctx);
    LOGI("-------2-------");
    assert(result == 0);
    pthread_attr_destroy(&threadAttr_);
    (void) result;

}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNICallbackUtil_stopTicks(JNIEnv *env, jobject) {

    pthread_mutex_lock(&g_ctx.lock);
    g_ctx.done = 1;
    pthread_mutex_unlock(&g_ctx.lock);
    struct timespec sleepTime;
    memset(&sleepTime, 0, sizeof(sleepTime));
    sleepTime.tv_nsec = 100000000;
    while (g_ctx.done) {
        nanosleep(&sleepTime, NULL);
    }

    env->DeleteGlobalRef(g_ctx.mainActivityClz);
    env->DeleteGlobalRef(g_ctx.mainActivityObj);
    g_ctx.mainActivityObj = NULL;
    g_ctx.mainActivityClz = NULL;

    pthread_mutex_destroy(&g_ctx.lock);


}
