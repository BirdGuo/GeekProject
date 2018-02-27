//
// Created by guoxw on 2018/2/27 0027.
//

#include "tcpipLib.h"

static char recv_php_buf[1024] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

struct test {
    char a;
    int b;
    int c;
};

void startTouchServer() {
    int type;
    int code;
    int value;
    long long when;
    struct sockaddr_un srv_addr;

    int connect_fd = socket(AF_UNIX, SOCK_STREAM, 0);

    if (connect_fd < 0) {
        perror("cannot create communication socket");
        LOGD("connect_fd %d\n", connect_fd);
    } else {

    }

}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_TcpIPUtil_startConnect(JNIEnv *env, jobject) {


}