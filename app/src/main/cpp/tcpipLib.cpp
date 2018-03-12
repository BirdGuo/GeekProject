//
// Created by guoxw on 2018/2/27 0027.
//

#include "tcpipLib.h"
#include "exceptionLib.h"
#include "clientLib.h"
#include "serverLib.h"

//sockaddr_in,htons
#include <netinet/in.h>
//inet_ntop
#include <arpa/inet.h>

//offsetof
#include <stddef.h>

//close,unlink
#include <unistd.h>

//sockaddr_un
#include <sys/un.h>

static int newTcpSocket(JNIEnv *env, jobject obj, jobject mContext) {
    LOGI("------newTcpSocket 1-------");
    LogMessage(env, obj, mContext, "新建一个socket对象");
    int tcpSocket = socket(PF_INET, SOCK_STREAM, 0);
    LOGI("------newTcpSocket 2------- %d ",tcpSocket);
    //socket是否成功构建
    if (-1 == tcpSocket) {
        LOGI("------newTcpSocket 3-------");
        //抛出异常
        ThrowErrnoException(env, "java/io/IOException", errno);
    }
}

/**
 * 绑定端口
 *
 * @param env
 * @param obj
 * @param sd
 * @param port
 */
static void
bindSocketToPort(JNIEnv *env, jobject obj, jobject mContext, int sd, unsigned short port) {

    struct sockaddr_in address;
    //socket绑定地址
    memset(&address, 0, sizeof(address));
    address.sin_family = PF_INET;
    //绑定所有的地址
    address.sin_addr.s_addr = htonl(INADDR_ANY);

    //将端口转换为网络字节顺序
    address.sin_port = htons(port);

    LogMessage(env, obj, mContext, "绑定端口号 %hu", port);
    if (-1 == bind(sd, (struct sockaddr *) &address, sizeof(address))) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    }

}

/**
 * 获取当前绑定的端口号
 *
 * @param env
 * @param obj
 * @param sd
 * @return 端口号
 * @throws IOException
 */
static unsigned short getSocketPort(JNIEnv *env, jobject obj, jobject mContext, int sd) {

    unsigned short port = 0;
    struct sockaddr_in address;
    socklen_t addressLength = sizeof(address);

    //获取socket地址
    if (-1 == getsockname(sd, (struct sockaddr *) &address, &addressLength)) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    } else {
        //将端口转换为主机字节顺序
        port = ntohs(address.sin_port);
        LogMessage(env, obj, mContext, "绑定随机端口 %hu", port);
    }

    return port;
}

/**
 * 用给定的backlog监听给定的套接字。等待连接。当连接池已满时。新的连接将被拒绝
 *
 * @param env
 * @param obj
 * @param sd
 * @param backlog
 */
static void listenOnSocket(JNIEnv *env, jobject obj, jobject mContext, int sd, int backlog) {

    LogMessage(env, obj, mContext, "Listening on socket with a backlog of %d pending connections.",
               backlog);

    if (-1 == listen(sd, backlog)) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    }
}

/**
 *
 *
 * @param env
 * @param obj
 * @param message
 * @param address
 */
static void
logAddress(JNIEnv *env, jobject obj, jobject mContext, const char *message,
           const struct sockaddr_in *address) {

    char ip[INET_ADDRSTRLEN];
    // Convert the IP address to string
    if (NULL == inet_ntop(PF_INET, &(address->sin_addr), ip, INET_ADDRSTRLEN)) {
        // Throw an exception with error number
        ThrowErrnoException(env, "java/io/IOException", errno);
    } else {
        // Convert port to host byte order
        unsigned short port = ntohs(address->sin_port);
        LogMessage(env, obj, mContext, "%s %s:%hu.", message, ip, port);
    }
}

/**
 *
 *
 * @param env
 * @param obj
 * @param sd
 * @return
 */
static int acceptOnSocket(JNIEnv *env, jobject obj, jobject mContext, int sd) {

    struct sockaddr_in address;
    socklen_t addressLength = sizeof(address);

    LogMessage(env, obj, mContext, "等待客户端连接...");
    int clientSocket = accept(sd, (struct sockaddr *) &address, &addressLength);

    if (-1 == clientSocket) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    } else {
        logAddress(env, obj, mContext, "Client connection from ", &address);
    }

    return clientSocket;
}

/**
 *
 * @param env
 * @param obj
 * @param sd
 * @param buffer
 * @param bufferSize
 * @return
 */
static ssize_t
receiveFromSocket(JNIEnv *env, jobject obj, jobject mContext, int sd, char *buffer,
                  size_t bufferSize) {
    LogMessage(env, obj, mContext, "收到报文...");

    ssize_t recvSize = recv(sd, buffer, bufferSize - 1, 0);
    if (-1 == recvSize) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    } else {
        buffer[recvSize] = NULL;
        if (recvSize > 0) {
            LogMessage(env, obj, mContext, "Received %d bytes: %s", recvSize, buffer);
        } else {
            LogMessage(env, obj, mContext, "Client disconnected");
        }
    }
}

/**
 *
 * @param env
 * @param obj
 * @param sd
 * @param buffer
 * @param bufferSize
 * @return
 */
static ssize_t
sendToSocket(JNIEnv *env, jobject obj, jobject mContext, int sd, const char *buffer,
             size_t bufferSize) {

    LogMessage(env, obj, mContext, "给服务器发消息");
    ssize_t sendSize = send(sd, buffer, bufferSize, 0);
    if (-1 == sendSize) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    } else {
        if (sendSize > 0) {
            LogMessage(env, obj, mContext, "Sent %d bytes: %s", sendSize);
        } else {
            LogMessage(env, obj, mContext, "Client disconnected");
        }
    }
}

/**
 *
 * @param env
 * @param obj
 * @param sd
 * @param ip
 * @param port
 */
static void
connectToAddress(JNIEnv *env, jobject obj, jobject mContext, int sd, const char *ip,
                 unsigned short port) {
    LogMessage(env, obj, mContext, "Connecting to %s;%uh...", ip, port);

    struct sockaddr_in address;
    memset(&address, 0, sizeof(address));
    address.sin_family = PF_INET;

    if (0 == inet_aton(ip, &(address.sin_addr))) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    } else {
        address.sin_port = htons(port);
        if (-1 == connect(sd, (const sockaddr *) &address, sizeof(address))) {
            ThrowErrnoException(env, "java/io/IOException", errno);
        } else {
            LogMessage(env, obj, mContext, "已连接！");
        }
    }

}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_ClientUtil_nativeStartTcpClient(JNIEnv *env, jobject obj,
                                                                   jobject mContext,
                                                                   jstring ip, jint port,
                                                                   jstring message) {

    int clientSocket = newTcpSocket(env, obj, mContext);


    if (NULL == env->ExceptionOccurred()) {
        // Get IP address as C string
        const char *ipAddress = env->GetStringUTFChars(ip, NULL);
        if (NULL == ipAddress) {
            goto exit;
        }

        connectToAddress(env, obj, mContext, clientSocket, ipAddress, (unsigned short) port);

        env->ReleaseStringUTFChars(ip, ipAddress);
        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }

        const char *messageText = env->GetStringUTFChars(message, NULL);
        if (NULL == messageText) {
            goto exit;
        }

        jsize messageSize = env->GetStringUTFLength(message);
        sendToSocket(env, obj, mContext, clientSocket, messageText, messageSize);

        env->ReleaseStringUTFChars(message, messageText);

        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }

        char buffer[MAX_BUFFER_SIZE];

        receiveFromSocket(env, obj, mContext, clientSocket, buffer, MAX_BUFFER_SIZE);
    }

    exit:
    if (clientSocket > 0) {
        close(clientSocket);
    }
}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_ServerUtil_nativeStartTcpServer(JNIEnv *env, jobject obj,
                                                                   jobject mContext,
                                                                   jint port) {
    LOGI("----------1---------");
    int serverSocket = newTcpSocket(env, obj, mContext);
    if (NULL == env->ExceptionOccurred()) {
        LOGI("----------2---------");
        bindSocketToPort(env, obj, mContext, serverSocket, (unsigned short) port);
        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }
        LOGI("----------3---------");
        if (0 == port) {
            getSocketPort(env, obj, mContext, serverSocket);
            if (NULL != env->ExceptionOccurred()) {
                goto exit;
            }
        }
        LOGI("----------4---------");
        listenOnSocket(env, obj, mContext, serverSocket, 4);

        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }
        LOGI("----------5---------");
        int clientSocket = acceptOnSocket(env, obj, mContext, serverSocket);

        char buffer[MAX_BUFFER_SIZE];
        ssize_t recvSize;
        ssize_t sendSize;

        LOGI("----------6---------");
        while (1) {
            recvSize = receiveFromSocket(env, obj, mContext, clientSocket, buffer, MAX_BUFFER_SIZE);
            if ((0 == recvSize) || (NULL != env->ExceptionOccurred())) {
                break;
            }
            sendSize = sendToSocket(env, obj, mContext, clientSocket, buffer, (size_t) recvSize);

            if ((0 == sendSize) || (NULL != env->ExceptionOccurred())) {
                break;
            }
        }

        close(clientSocket);

    }

    exit:
    if (serverSocket > 0) {
        LOGI("-------exit-------");
        close(serverSocket);
    }
}

/**
 * 新建一个udp连接
 * @param env
 * @param obj
 * @return
 */
static int newUdpSocket(JNIEnv *env, jobject obj, jobject mContext) {

    LogMessage(env, obj, mContext, "尝试开启一个udp连接...");
    int udpSocket = socket(PF_INET, SOCK_DGRAM, 0);
    if (-1 == udpSocket) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    }
    return udpSocket;
}

/**
 *
 * @param env
 * @param obj
 * @param sd
 * @param address
 * @param buffer
 * @param bufferSize
 * @return
 */
static ssize_t
receiveDatagramFromSocket(JNIEnv *env, jobject obj, jobject mContext, int sd,
                          struct sockaddr_in *address,
                          char *buffer, size_t bufferSize) {

    socklen_t addressLength = sizeof(struct sockaddr_in);
    LogMessage(env, obj, mContext, "Receiving from the socket...");

    ssize_t recvSize = recvfrom(sd, buffer, bufferSize, 0, (struct sockaddr *) address,
                                &addressLength);

    if (-1 == recvSize) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    } else {
        LogMessage(env, obj, mContext, "Received from", address);
        buffer[recvSize] = NULL;

        if (recvSize > 0) {
            LogMessage(env, obj, mContext, "Received %d bytes: %s", recvSize);
        }
    }

    return recvSize;
}

static ssize_t
sendDatagramToSocket(JNIEnv *env, jobject obj, jobject mContext, int sd,
                     const struct sockaddr_in *address,
                     const char *buffer, size_t bufferSize) {

    LogMessage(env, obj, mContext, "发送到", address);

    ssize_t sendSize = sendto(sd, buffer, bufferSize, 0, (const sockaddr *) address,
                              sizeof(struct sockaddr_in));

    if (-1 == sendSize) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    } else if (sendSize > 0) {
        LogMessage(env, obj, mContext, "Send %d bytes: %s", sendSize, buffer);
    }

    return sendSize;

}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_ClientUtil_nativeStartUdpClient(JNIEnv *env, jobject obj,
                                                                   jobject mContext,
                                                                   jstring ip, jint port,
                                                                   jstring message) {

    int clientSocket = newUdpSocket(env, obj, mContext);
    if (NULL == env->ExceptionOccurred()) {
        struct sockaddr_in address;

        memset(&address, 0, sizeof(address));
        address.sin_family = PF_INET;

        const char *ipAddress = env->GetStringUTFChars(ip, NULL);
        if (NULL == ipAddress) {
            goto exit;
        }

        int result = inet_aton(ipAddress, &(address.sin_addr));
        env->ReleaseStringUTFChars(ip, ipAddress);
        if (0 == result) {
            ThrowErrnoException(env, "java/io/IOException", errno);
            goto exit;
        }

        address.sin_port = htons(port);
        const char *messageText = env->GetStringUTFChars(message, NULL);
        if (NULL == messageText) {
            goto exit;
        }

        jsize messageSize = env->GetStringUTFLength(message);

        sendDatagramToSocket(env, obj, mContext, clientSocket, &address, messageText, messageSize);

        env->ReleaseStringUTFChars(message, messageText);

        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }

        char buffer[MAX_BUFFER_SIZE];

        memset(&address, 0, sizeof(address));

        receiveDatagramFromSocket(env, obj, mContext, clientSocket, &address, buffer,
                                  MAX_BUFFER_SIZE);

    }

    exit:
    if (clientSocket > 0) {
        close(clientSocket);
    }

}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_ServerUtil_nativeStartUdpServer(JNIEnv *env, jobject obj,
                                                                   jobject mContext,
                                                                   jint port) {

    int serverSocket = newUdpSocket(env, obj, mContext);
    if (NULL == env->ExceptionOccurred()) {
        bindSocketToPort(env, obj, mContext, serverSocket, (unsigned short) port);
        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }

        if (0 == port) {
            getSocketPort(env, obj, mContext, serverSocket);
            if (NULL != env->ExceptionOccurred()) {
                goto exit;
            }
        }

        struct sockaddr_in address;
        memset(&address, 0, sizeof(address));

        char buffer[MAX_BUFFER_SIZE];
        ssize_t recvSize;
        ssize_t sendSize;

        recvSize = receiveDatagramFromSocket(env, obj, mContext, serverSocket, &address, buffer,
                                             MAX_BUFFER_SIZE);

        if ((0 == recvSize) || (NULL != env->ExceptionOccurred())) {
            goto exit;
        }

        sendSize = sendDatagramToSocket(env, obj, mContext, serverSocket, &address, buffer,
                                        (size_t) recvSize);

    }
    exit:
    if (serverSocket > 0) {
        close(serverSocket);
    }

}

static int newLocalSocket(JNIEnv *env, jobject obj) {
    int localSocket = socket(PF_LOCAL, SOCK_STREAM, 0);
    if (-1 == localSocket) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    }

    return localSocket;
}

static void
bindLocalSocketToName(JNIEnv *env, jobject obj, jobject mContext, int sd, const char *name) {
    struct sockaddr_un address;
    const size_t nameLength = strlen(name);
    size_t pathLength = nameLength;

    // If name is not starting with a slash it is
    // in the abstract namespace
    bool abstractNamespace = ('/' != name[0]);

    if (abstractNamespace) {
        pathLength++;
    }

    if (pathLength > sizeof(address.sun_path)) {
        ThrowException(env, "java/io/IOException", "Name is too big.");
    } else {
        memset(&address, 0, sizeof(address));
        address.sun_family = PF_LOCAL;
        char *sunPath = address.sun_path;

        if (abstractNamespace) {
            *sunPath++ = NULL;
        }

        strcpy(sunPath, name);
        //为什么这儿会有sun_path
        socklen_t addressLength = (offsetof(struct sockaddr_un, sun_path)) + pathLength;

        unlink(address.sun_path);

        LogMessage(env, obj, mContext, "Binding to local name %s%s.",
                   (abstractNamespace) ? "(null)" : "",
                   name);

        if (-1 == bind(sd, (struct sockaddr *) &address, addressLength)) {
            ThrowErrnoException(env, "java/io/IOException", errno);
        }
    }
}

static int acceptOnLocalSocket(JNIEnv *env, jobject obj, jobject mContext, int sd) {

    LogMessage(env, obj, mContext, "Waiting for a client connection...");
    int clientSocket = accept(sd, NULL, NULL);

    if (-1 == clientSocket) {
        ThrowErrnoException(env, "java/io/IOException", errno);
    }

    return clientSocket;
}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_ServerUtil_nativeStartLocalServer(JNIEnv *env, jobject obj,
                                                                     jobject montext,
                                                                     jstring name) {

    int serverSocket = newLocalSocket(env, obj);
    if (NULL == env->ExceptionOccurred()) {
        const char *nameText = env->GetStringUTFChars(name, NULL);
        if (NULL == nameText) {
            goto exit;
        }

        bindLocalSocketToName(env, obj, montext, serverSocket, nameText);

        env->ReleaseStringUTFChars(name, nameText);

        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }

        listenOnSocket(env, obj, montext, serverSocket, 4);

        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }

        int clientSocket = acceptOnSocket(env, obj, montext, serverSocket);
        if (NULL != env->ExceptionOccurred()) {
            goto exit;
        }

        char buffer[MAX_BUFFER_SIZE];
        ssize_t recvSize;
        ssize_t sendSize;

        while (1) {
            recvSize = receiveFromSocket(env, obj, montext, clientSocket, buffer, MAX_BUFFER_SIZE);

            if ((0 == recvSize) || (NULL != env->ExceptionOccurred())) {
                break;
            }

            sendSize = sendToSocket(env, obj, montext, clientSocket, buffer, (size_t) recvSize);

            if ((0 == sendSize) || (NULL != env->ExceptionOccurred())) {
                break;
            }
        }
        close(clientSocket);
    }

    exit:
    if (serverSocket > 0) {
        close(serverSocket);
    }
}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_TcpIPUtil_startConnect(JNIEnv *env, jobject) {


}