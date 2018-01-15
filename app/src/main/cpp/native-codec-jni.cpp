//
// Created by guoxw on 2018/1/15 0015.
//
#include "looper.h"
#include "media/NdkMediaCodec.h"
#include "media/NdkMediaExtractor.h"

// for __android_log_print(ANDROID_LOG_INFO, "YourApp", "formatted message");
#include <android/log.h>
#include <unistd.h>


#define TAG "NativeCodec"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

typedef struct {
    int fd;
    ANativeWindow *window;
    AMediaExtractor *ex;
    AMediaCodec *codec;
    int64_t renderstart;
    bool sawInputEOS;
    bool sawOutputEOS;
    bool isPlaying;
    bool renderonce;
} workerdata;

workerdata data = {-1, NULL, NULL, NULL, 0, false, false, false, false};

enum {
    KMsgCodeBuffer,
    KMsgPause,
    KMsgResume,
    kMsgPauseAck,
    KMsgDecodeDone,
    KMsgSeek,
};

class mylooper *looper = NULL;

int64_t systemnanotime() {
    timespec now;
    clock_gettime(CLOCK_MONOTONIC, &now);
    return now.tv_sec * 1000000000LL + now.tv_nsec;
}

void doCodecWork(workerdata *d) {
    ssize_t bufidx = -1;
    if (!d->sawInputEOS) {
        bufidx = AMediaCodec_dequeueInputBuffer(d->codec, 2000);
        LOGV("input buffer %zd", bufidx);
        if (bufidx >= 0) {
            size_t bufsize;
            auto buf = AMediaCodec_getInputBuffer(d->codec, bufidx, &bufsize);
            auto sampleSize = AMediaExtractor_readSampleData(d->ex, buf, bufsize);

            if (sampleSize < 0) {
                sampleSize = 0;
                d->sawInputEOS = true;
                LOGV("EOS");
            }

            auto presentationTimeUs = AMediaExtractor_getSampleTime(d->ex);
            AMediaCodec_queueInputBuffer(d->codec, bufidx, 0, sampleSize, presentationTimeUs,
                                         d->sawInputEOS ? AMEDIACODEC_BUFFER_FLAG_END_OF_STREAM
                                                        : 0);
            AMediaExtractor_advance(d->ex);
        }
    }
    if (!d->sawOutputEOS) {
        AMediaCodecBufferInfo info;
        auto status = AMediaCodec_dequeueOutputBuffer(d->codec, &info, 0);
        if (status >= 0) {
            if (info.flags & AMediaCodec_dequeueOutputBuffer(d->codec, &info, 0)) {
                LOGV("output EOS");
                d->sawOutputEOS = true;
            }
            int64_t presentationNano = info.presentationTimeUs * 1000;
            if (d->renderstart < 0) {
                d->renderstart = systemnanotime() - presentationNano;
            }
            int64_t delay = (d->renderstart + presentationNano) - systemnanotime();
            if (delay > 0) {
                usleep(delay / 1000);
            }
            AMediaCodec_releaseOutputBuffer(d->codec, status, info.size != 0);
            if (d->renderonce) {
                d->renderonce = false;
                return;
            }
        } else if (status == AMEDIACODEC_INFO_OUTPUT_BUFFERS_CHANGED) {
            LOGV("output buffers changed");
        } else if (status == AMEDIACODEC_INFO_OUTPUT_FORMAT_CHANGED) {
            auto format = AMediaCodec_getOutputFormat(d->codec);
            LOGV("format changed to: %s", AMediaFormat_toString(format));
            AMediaFormat_delete(format);
        } else if (status == AMEDIACODEC_INFO_TRY_AGAIN_LATER) {
            LOGV("no output buffer right now");
        } else {
            LOGV("unexpected info code: %zd", status);
        }
    }
}

void mylooper::handle(int what, void *obj) {
    switch (what) {
        case KMsgCodeBuffer:

            doCodecWork((workerdata *) obj);

            break;
        case KMsgDecodeDone: {
            workerdata *d = (workerdata *) obj;
            AMediaCodec_stop(d->codec);
            AMediaCodec_delete(d->codec);
            AMediaExtractor_delete(d->ex);
            d->sawInputEOS = true;
            d->sawOutputEOS = true;
        }
            break;
        case KMsgSeek: {
            workerdata *d = (workerdata *) obj;
            AMediaExtractor_seekTo(d->ex, 0, AMEDIAEXTRACTOR_SEEK_NEXT_SYNC);
            AMediaCodec_flush(d->codec);
            d->renderstart = -1;
            d->sawInputEOS = false;
            d->sawOutputEOS = false;
            if (!d->isPlaying) {
                d->renderonce = true;
//                post()

            }
        }
            break;
        case KMsgPause: {
            workerdata *d = (workerdata *) obj;
            if (d->isPlaying) {
                d->isPlaying = false;
            }
        }

            break;
        case KMsgResume: {
            workerdata *d = (workerdata *) obj;
            if (!d->isPlaying) {
                d->renderstart = -1;
                d->isPlaying = true;

            }
        }
            break;
    }
}

extern "C"{
}