//
// Created by guoxw on 2018/1/11 0011.
//

#ifndef GEEKPROJECT_LOOPER_H
#define GEEKPROJECT_LOOPER_H

#include <jni.h>
#include <pthread.h>
#include <semaphore.h>

#include <android/log.h>

#define TAG "NativeCodec-looper"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)


struct loopermessage;

class looper {
public:
    looper();

    looper &operator=(const looper) = delete;

    looper(looper &) = delete;

    virtual ~looper();

    void post(int what, void *data, bool flush = false);

    void quit();

    virtual void handle(int what, void *data);

private :
    void addmsg(loopermessage *msg, bool flush);

    static void *trampoline(void *p);

    void loop();

    loopermessage *head;
    pthread_t worker;
    sem_t headwriteprotect;
    sem_t headdataavailable;
    bool running;
};


