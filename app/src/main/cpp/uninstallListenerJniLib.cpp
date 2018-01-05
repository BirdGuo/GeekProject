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
    if (pid < 0) {
        LOGD("克隆失败");
    } else if (pid > 0) {
        LOGD("父进程");
    } else {
        LOGD("子进程");
        /**
         * 在内核中创建 inotify 子系统的一个实例，成功的话将返回一个文件描述符，失败则返回 -1。
         * 就像其他系统调用一样，如果 inotify_init() 失败，请检查 errno 以获得诊断信息。
         */
        int fuileDescript = inotify_init();
        /**
         * 用于添加监视器。
         * 每个监视器必须提供一个路径名和相关事件的列表（每个事件由一个常量指定，比如 IN_MODIFY）。
         * 要监控多个事件，只需在事件之间使用逻辑操作符或 — C 语言中的管道线（|）操作符。
         * 如果 inotify_add_watch() 成功，该调用会为已注册的监视器返回一个惟一的标识符；
         * 否则，返回 -1。使用这个标识符更改或删除相关的监视器。
         */
        int watch = inotify_add_watch(fuileDescript, path_str, IN_DELETE_SELF);
        /**
         * 说明：malloc 向系统申请分配指定size个字节的内存空间。返回类型是 void* 类型。
         * void* 表示未确定类型的指针。C,C++规定，void* 类型可以强制转换为任何其它类型的指针。
         * 这个在MSDN上可以找到相关的解释，具体内容如下：
         * malloc returns a void pointer to the allocated space, or NULL if there is insufficient memory available.
         * To return a pointer to a type other than void, use a type cast on the return value.
         * The storage space pointed to by the return value is guaranteed to be suitably aligned for storage of any type of object.
         * If size is 0, malloc allocates a zero-length item in the heap and returns a valid pointer to that item. Always check the return from malloc,
         * even if the amount of memory requested is small.
         */
        void *p = malloc(sizeof(struct inotify_event));

        read(fuileDescript, p, sizeof(struct inotify_event));
        /**
         * 删除一个监视器
         */
        inotify_rm_watch(fuileDescript, watch);
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
