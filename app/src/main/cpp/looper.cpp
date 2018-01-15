//
// Created by guoxw on 2018/1/11 0011.
//

#include "looper.h"

typedef struct loopermessage loopermessage;

struct loopermessage {
    int what;
    void *obj;
    loopermessage *next;
    bool quit;
};

void *looper::trampoline(void *p) {
//    ((looper*p)p)->loop();
//    ((looper * p)
//    p)->looper();
    return NULL;
}

looper::looper() {

    sem_init(&headdataavailable, 0, 0);
    sem_init(&headwriteprotect, 0, 1);
    pthread_attr_t attr;
    pthread_attr_init(&attr);

    pthread_create(&worker, &attr, trampoline, this);
    running = true;

}

looper::~looper() {
    if (running) {
        LOGV("Looper deleted while still running. Some messages will not be processed");
        quit();
    }
}

void looper::post(int what, void *data, bool flush) {
    loopermessage *msg = new loopermessage();
    msg->what = what;
    msg->obj = data;
    msg->next = NULL;
    msg->quit = false;
    addmsg(msg, flush);
}

void looper::addmsg(loopermessage *msg, bool flush) {
    sem_wait(&headwriteprotect);
    loopermessage *h = head;
    if (flush) {
        while (h) {
            loopermessage *next = h->next;
            delete h;
            h = next;
        }
    }
    if (h) {
        while (h->next) {
            h = h->next;
        }
        h->next = msg;
    } else {
        head = msg;
    }
    LOGV("post msg %d", msg->what);
    sem_post(&headwriteprotect);
    sem_post(&headdataavailable);
}

void looper::loop() {
    while (true) {
        sem_wait(&headdataavailable);
        sem_wait(&headwriteprotect);
        loopermessage *msg = head;
        if (msg == NULL) {
            LOGV("no msg");
        }
        head = msg->next;
        sem_post(&headwriteprotect);
        if (msg->quit) {
            LOGV("quitting");
        }
        LOGV("processing msg %d", msg->what);
        handle(msg->what, msg->obj);
        delete msg;
    }
}

void looper::quit() {
    LOGV("quit");
    loopermessage *msg = new loopermessage();
    msg->what = 0;
    msg->obj = NULL;
    msg->next = NULL;
    msg->quit = true;
    addmsg(msg, false);
    void *retval;
    pthread_join(worker, &retval);
    sem_destroy(&headdataavailable);
    sem_destroy(&headwriteprotect);
    running = false;
}

void looper::handle(int what, void *data) {
    LOGV("dropping msg %d %p", what, data);
}