//
// Created by guoxw on 2018/1/3 0003.
//

#include "hexJniLib.h"

JNIEXPORT jstring JNICALL
Java_com_guoxw_geekproject_jniutil_HexUtil_hexEncode(JNIEnv *env, jobject, jbyteArray array) {

    jstring ret = NULL;
    if (array != NULL) {
        //得到数组长度
        jsize length = env->GetArrayLength(array);
        if (length > 0) {
            //存储编码后的字符 有可能为0
            char chs[length * 2 + 1];
            //得到数据的原始数据
            jbyte *data = env->GetByteArrayElements(array, JNI_FALSE);
            for (int i = 0; i < length; i++) {
                jbyte bc = data[i];
                //构造成高位
                jbyte h = ((bc >> 4) & 0x0f);
                //构造成低位
                jbyte l = (bc & 0x0f);
                //把高位和地位转换成字符
                jchar ch;
                jchar cl;
                if (h > 9) {
                    ch = 'A' + (h - 10);
                } else {
                    ch = '0' + h;
                }
                if (l > 9) {
                    cl = 'A' + (l - 10);
                } else {
                    cl = '0' + l;
                }
                chs[i * 2] = ch;
                chs[i * 2 + 1] = cl;
            }
            chs[length * 2] = 0;
            //释放数组
            env->ReleaseByteArrayElements(array, data, JNI_ABORT);
            ret = env->NewStringUTF(chs);
        }
    }
    return ret;
}

JNIEXPORT jbyteArray JNICALL
Java_com_guoxw_geekproject_jniutil_HexUtil_hexDecode(JNIEnv *env, jobject, jstring str) {
    jbyteArray ret = NULL;
    if (str != NULL) {
        jsize length = env->GetStringLength(str);
        //判断只有在长度为偶数的情况下才继续
        if (length % 2 == 0) {
            jsize dLen = length >> 1;
            jbyte data[dLen];
            const jchar *chs = env->GetStringChars(str, JNI_FALSE);
            for (int i = 0; i < dLen; i++) {
                //获得单个字符
                jchar ch = chs[i * 2];
                jchar cl = chs[i * 2 + 1];
                jint h = 0;
                jint l = 0;
                //得到高位和低位的ascii
                if (ch >= 'A') {
                    h = ch - 'A' + 10;
                } else if (ch >= 'a') {
                    h = ch - 'a' + 10;
                } else if (ch >= '0') {
                    h = ch - '0';
                }
                if (cl >= 'A') {
                    l = cl - 'A' + 10;
                } else if (ch >= 'a') {
                    l = cl - 'a' + 10;
                } else if (ch >= '0') {
                    l = cl - '0';
                }
                data[i] = ((h << 4) | l);

            }
            //释放字符串
            env->ReleaseStringChars(str, chs);
            //创建新的字节数组
            ret = env->NewByteArray(dLen);
            //给新创建的数组设置数值
            env->SetByteArrayRegion(ret, 0, dLen, data);
        }
    }
    return ret;
}