//
// Created by guoxw on 2018/1/11 0011.
//

#ifndef GEEKPROJECT_SIMPLE_MODEL_H
#define GEEKPROJECT_SIMPLE_MODEL_H

#include <vector>
#include <android/NeuralNetworks.h>
#include <cwchar>
#include <jni.h>

#define FLOAT_EISILON (1e-6)
#define TENSOR_SIZE 200
#define LOG_TAG "NNAPI_DEMO"

#ifdef __cplusplus
extern "C" {
#endif

class SimpleModel {

public:
    explicit SimpleModel(size_t size, int protect, int fd, size_t offset);

    /**
     * “~”按位取反
     */
    ~SimpleModel();

    bool CreateComileModel();

    /**
     *
     * “*”取内容 “&”取地址
     *
     * @param inputValue1
     * @param inputValue2
     * @param result
     * @return
     */
    bool Compute(float inputValue1, float inputValue2, float *result);

private:
    ANeuralNetworksModel *model_;
    ANeuralNetworksCompilation *compilation_;
    ANeuralNetworksMemory *memoryModel_;
    ANeuralNetworksMemory *memoryInput2_;
    ANeuralNetworksMemory *memoryOutput_;

    uint32_t dimLength_;
    uint32_t tensorSize;
    size_t offset_;

    std::vector<float> inputTensor1_;
    int modelDataFd_;
    int inputTensor2Fd_;
    int outputTensorFd_;
};

/**
 * Class:     Java_com_guoxw_geekproject_jniutil_SimpleModelUtil
 * Method:    initModel
 * Signature: ()Ljava.lang.Long 初始化model
 *
 */
JNIEXPORT jlong
JNICALL
Java_com_guoxw_geekproject_jniutil_SimpleModelUtil_initModel(
        JNIEnv *, jobject, jobject, jstring);

/**
 * Class:     Java_com_guoxw_geekproject_jniutil_SimpleModelUtil
 * Method:    startCompute
 * Signature: ()Ljava.lang.float 初始化model
 *
 */
JNIEXPORT jfloat
JNICALL
Java_com_guoxw_geekproject_jniutil_SimpleModelUtil_startCompute(
        JNIEnv *, jobject, jlong, jfloat, jfloat);

/**
 * Class:     Java_com_guoxw_geekproject_jniutil_SimpleModelUtil
 * Method:    destoryModel
 * Signature: ()Ljava.lang.float 初始化model
 *
 */
JNIEXPORT jfloat
JNICALL
Java_com_guoxw_geekproject_jniutil_SimpleModelUtil_destoryModel(
        JNIEnv *, jobject, jlong);


#ifdef __cplusplus
};
#endif

#endif //GEEKPROJECT_SIMPLE_MODEL_H
