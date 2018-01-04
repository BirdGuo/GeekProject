//
// Created by guoxw on 2018/1/4 0004.
//

#include "uninstallListenerJniLib.h"


JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_UninstallUtil_callUnInstallListener(JNIEnv *env, jobject,
                                                                       jint versionSDK,
                                                                       jstring path) {
    LOGD("-----------");
    const char *path_str = env->GetStringUTFChars(path, JNI_FALSE);
    pid_t pid = fork();

//    char *pidc = (char *) pid;
//
//    LOGD(pidc);

    if (pid < 0) {
        LOGD("克隆失败");
    } else if (pid > 0) {
        LOGD("父进程");
    } else {
        LOGD("子进程");
        int fuileDescript = inotify_init();
        LOGD("子进程1");
        int watch = inotify_add_watch(fuileDescript, path_str, IN_DELETE_SELF);
        LOGD("子进程2");
        void *p = malloc(sizeof(struct inotify_event));
        LOGD("子进程3");
        read(fuileDescript, p, sizeof(struct inotify_event));
        LOGD("子进程4");
        inotify_rm_watch(fuileDescript, watch);
        LOGD("子进程5");
//        if (versionSDK < 17) {
//            LOGD("------小于17-----");
//            execlp("am", "am", "start", "-a", "android.intent.action.VIEW", "-d",
//                   "http://blog.csdn.net/onepiece2/article/details/77559739", NULL);
//        } else {
//            LOGD("------大于17-----");
//            execlp("am", "am", "start", "--user", "0", "-a", "android.intent.action.VIEW", "-d",
//                   "http://blog.csdn.net/onepiece2/article/details/71439431", NULL);
//        }
        if (versionSDK < 17) {
            LOGD("子进程7");
            //am start -a android.intent.action.VIEW -d  http://gityuan.com
            execlp("am", "am", "start", "-a", "android.intent.action.VIEW", "-d",
                   "https://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=2247484366&idx=2&sn=a015497277d2a6380a80fdc9031ca51c&chksm=eb476f50dc30e64620fbb8a7ce0aebc445638c5f1097763e0da36fc40beb85fb256d980af440&scene=18#wechat_redirect",
                   NULL);
        } else {
            LOGD("子进程8");
            execlp("am", "am", "start", "--user", "0", "-a", "android.intent.action.VIEW", "-d",
                   "https://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=2247484366&idx=2&sn=a015497277d2a6380a80fdc9031ca51c&chksm=eb476f50dc30e64620fbb8a7ce0aebc445638c5f1097763e0da36fc40beb85fb256d980af440&scene=18#wechat_redirect",
                   NULL);
        }
//        execlp("am", "am", "start", "-n", "com.android.browser/com.android.browser.BrowserActivity",
//               "http://blog.csdn.net/onepiece2", NULL);
        LOGD("子进程6");
    }
    //释放资源
    env->ReleaseStringUTFChars(path, path_str);

}
