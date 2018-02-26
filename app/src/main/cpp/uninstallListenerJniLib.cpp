//
// Created by guoxw on 2018/1/4 0004.
//

#include <string.h>
#include "uninstallListenerJniLib.h"

static const char APP_DIR[] = "/data/data/com.guoxw.geekproject";
static const char APP_FILES_DIR[] = "/data/data/com.guoxw.geekproject/files";
static const char APP_OBSERVED_FILE[] = "/data/data/com.guoxw.geekproject/files/observedFile";
static const char APP_LOCK_FILE[] = "/data/data/com.guoxw.geekproject/files/lockFile";


JNIEXPORT jint JNICALL
Java_com_guoxw_geekproject_jniutil_UninstallUtil_callUnInstallListener(JNIEnv *env, jobject,
                                                                       jint versionSDK) {
//    char *path_str = Jstring2CStr(env, path);
    // 2，创建当前进程的克隆进程
    pid_t pid = fork();
    // 3，根据返回值的不同做不同的操作,<0,>0,=0
    if (pid < 0) {
        // 说明克隆进程失败
        LOGE("克隆失败");

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
                LOGE("创建监听文件夹失败");
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
            LOGE("被监听文件锁已被占用");
            exit(0);
        }

        //分配空间，以便读取event
        void *p_buf = malloc(sizeof(struct inotify_event));
        if (p_buf == NULL) {
            LOGE("分配空间失败");
            exit(1);
        }

        // 初始化
        int fileDescriptor = inotify_init();
        if (fileDescriptor < 0) {
            free(p_buf);
            LOGE("初始化失败");
            exit(1);
        }

        //添加被监听文件到监听列表
        int watchDescriptor = inotify_add_watch(fileDescriptor, APP_OBSERVED_FILE, IN_ALL_EVENTS);
        if (watchDescriptor < 0) {
            free(p_buf);
            LOGE("添加监听失败");
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
                    //移除监听
                    inotify_rm_watch(fileDescriptor, watchDescriptor);
                    break;
                } else {// 未卸载，可能用户执行了"清除数据"
                    fclose(p_appDir);
                    //重新创建被监听文件
                    FILE *p_observedFile = fopen(APP_OBSERVED_FILE, "w");
                    fclose(p_observedFile);
                    //重新添加监听
                    int watchDescriptor = inotify_add_watch(fileDescriptor, APP_OBSERVED_FILE,
                                                            IN_ALL_EVENTS);
                    if (watchDescriptor < 0) {
                        free(p_buf);
                        LOGD("重新添加监听失败");
                        exit(1);
                    }
                }
            }
        }
        free(p_buf);

        if (versionSDK < 17) {
            execlp("am", "am", "start", "-a", "android.intent.action.VIEW", "-d",
                   "http://blog.csdn.net/onepiece2/article/details/77559739", NULL);
        } else {
            execlp("am", "am", "start", "--user", "0", "-a",
                   "android.intent.action.VIEW", "-d",
                   "http://blog.csdn.net/onepiece2/article/details/71439431", NULL);
        }
    } else {
        // 父进程直接退出，使子进程被init进程领养，以避免子进程僵死，同时返回子进程pid
    }
    return pid;
}
