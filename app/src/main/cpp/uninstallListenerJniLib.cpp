//
// Created by guoxw on 2018/1/4 0004.
//

#include <string.h>
#include "uninstallListenerJniLib.h"

static const char APP_DIR[] = "/data/data/com.guoxw.geekproject";
static const char APP_FILES_DIR[] = "/data/data/com.guoxw.geekproject/files";
static const char APP_OBSERVED_FILE[] = "/data/data/com.guoxw.geekproject/files/observedFile";
static const char APP_LOCK_FILE[] = "/data/data/com.guoxw.geekproject/files/lockFile";

/**
 * 返回值 char* 这个代表char数组的首地址
 * Jstring2CStr 把java中的jstring的类型转化成一个c语言中的char 字符串
 */
char *Jstring2CStr(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String"); //String
    jstring strencode = env->NewStringUTF("GB2312"); // 得到一个java字符串 "GB2312"
    jmethodID mid = env->GetMethodID(clsstring, "getBytes",
                                     "(Ljava/lang/String;)[B"); //[ String.getBytes("gb2312");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid,
                                                         strencode); // String .getByte("GB2312");
    jsize alen = env->GetArrayLength(barr); // byte数组的长度
    jbyte *ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1); //"\0"
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0); //
    return rtn;
}

//-std=c++14
char *get_watch_file(const char *package) {
    int len = strlen(package) + strlen("watch.tmp") + 1;
    char *watchPath = (char *) malloc(sizeof(char) * len);
    sprintf(watchPath, "%s%s", package, "watch.tmp");
    FILE *file = fopen(watchPath, "r");
    if (file == NULL) {
        file = fopen(watchPath, "w+");
//        chmod(watchPath,0755);
    }
    fclose(file);
    return watchPath;

}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_UninstallUtil_callUnInstallListener(JNIEnv *env, jobject,
                                                                       jint versionSDK,
                                                                       jstring path) {
    LOGD("-----------");
    // 1，将传递过来的java的包名转为c的字符串
    const char *path_str = env->GetStringUTFChars(path, JNI_FALSE);
//    char *path_str = Jstring2CStr(env, path);
    // 2，创建当前进程的克隆进程
    pid_t pid = fork();
    // 3，根据返回值的不同做不同的操作,<0,>0,=0
    if (pid < 0) {
        // 说明克隆进程失败
        LOGD("克隆失败");

    } else if (pid > 0) {
        // 说明克隆进程成功，而且该代码运行在父进程中
        LOGD("父进程");
    } else if (pid == 0) {
        // 说明克隆进程成功，而且代码运行在子进程中
        LOGD("子进程");
        // 4，在子进程中监视/data/data/包名这个目录

        //监听文件夹
        FILE *p_filesDir = fopen(APP_FILES_DIR, "r");
        if (p_filesDir == NULL) {//如果监听文件所在文件夹不存在，创建
            //创建文件夹
            int filesDirRet = mkdir(APP_FILES_DIR, S_IRWXU | S_IRWXG | S_IXOTH);
            if (filesDirRet == -1) {
                LOGD("mkdir failed!!!");
                //退出APP
                exit(1);
            }
        }

        //被监听文件
        FILE *p_observedFile = fopen(APP_OBSERVED_FILE, "r");
        if (p_observedFile == NULL) {//判断被监听文件是否存在
            //创建文件
            p_observedFile = fopen(APP_OBSERVED_FILE, "w");
        }
        //
        fclose(p_observedFile);

        //创建文件锁
        int lockFileDescriptor = open(APP_LOCK_FILE, O_CREAT);
        if (lockFileDescriptor == -1) {
            lockFileDescriptor = open(APP_LOCK_FILE, O_CREAT);
        }
        //需要添加头文件#include<sys/file.h>
        int lockRet = flock(lockFileDescriptor, LOCK_EX | LOCK_NB);

        if (lockRet == -1) {
            LOGD("observed by another process");
            exit(0);
        }

        LOGD("observed by another process");

        //分配空间，以便读取event
        void *p_buf = malloc(sizeof(struct inotify_event));
        if (p_buf == NULL) {
            LOGD("malloc failed !!!");
            exit(1);
        }

        //分配空间以便打印mask
        size_t maskStrLength = 7 + 10 + 1;// mask=0x占7字节，32位整形数最大为10位，转换为字符串占10字节，'\0'占1字节
        void *p_maskStr = malloc(maskStrLength);
        if (p_maskStr == NULL) {
            free(p_maskStr);
            LOGD("malloc failed !!! 222");
            exit(1);
        }

        LOGD("------到这里没 2--开始监听----");

        int fileDescriptor = inotify_init();
        if (fileDescriptor < 0) {
            free(p_buf);
            free(p_maskStr);
            LOGD("inotify_init failed !!!");
            exit(1);
        }

        //添加被监听文件到监听列表
        int watchDescriptor = inotify_add_watch(fileDescriptor, APP_OBSERVED_FILE, IN_ALL_EVENTS);
        if (watchDescriptor < 0) {
            free(p_buf);
            free(p_maskStr);
            LOGD("inotify_add_watch failed !!!");
            exit(1);
        }

        while (1) {
            //read会阻塞进程
            size_t readBytes = read(fileDescriptor, p_buf, sizeof(struct inotify_event));
            // 若文件被删除，可能是已卸载，还需进一步判断app文件夹是否存在
            if (IN_DELETE_SELF == ((struct inotify_event *) p_buf)->mask) {
                FILE *p_appDir = fopen(APP_DIR, "r");
                //确认已卸载
                if (p_appDir == NULL) {
                    inotify_rm_watch(fileDescriptor, watchDescriptor);
                    break;
                } else {// 未卸载，可能用户执行了"清除数据"
                    fclose(p_appDir);
                    //重新创建被监听文件
                    FILE *p_observedFile = fopen(APP_OBSERVED_FILE, "w");
                    fclose(p_observedFile);

                    int watchDescriptor = inotify_add_watch(fileDescriptor, APP_OBSERVED_FILE,
                                                            IN_ALL_EVENTS);
                    if (watchDescriptor < 0) {
                        free(p_buf);
                        free(p_maskStr);
                        LOGD("inotify_add_watch failed !!! 333");
                        exit(1);
                    }
                }
            }
        }
        free(p_buf);
        free(p_maskStr);

        LOGD("停止监听");
        if (versionSDK < 17) {
            LOGD("------小于17-----");
            execlp("am", "am", "start", "-a", "android.intent.action.VIEW", "-d",
                   "http://blog.csdn.net/onepiece2/article/details/77559739", NULL);
        } else {
            LOGD("------大于17-----");
            execlp("am", "am", "start", "--user", "0", "-a",
                   "android.intent.action.VIEW", "-d",
                   "http://blog.csdn.net/onepiece2/article/details/71439431", NULL);
        }
    } else {

    }
    //释放资源
    env->ReleaseStringUTFChars(path, path_str);

}
