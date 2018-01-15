//
// Created by guoxw on 2018/1/11 0011.
//

#include "simple_model.h"
#include <android/asset_manager_jni.h>
#include <android/log.h>
#include <sys/mman.h>

JNIEXPORT jlong
JNICALL
Java_com_guoxw_geekproject_jniutil_SimpleModelUtil_initModel(
        JNIEnv *env, jobject, jobject _assetManager, jstring _assetName) {

    AAssetManager *assetManager = AAssetManager_fromJava(env, _assetManager);
    const char *assetName = env->GetStringUTFChars(_assetName, NULL);
    AAsset *asset = AAssetManager_open(assetManager, assetName, AASSET_MODE_BUFFER);

    /**
     * 在C99里面，NULL可以被定义为0或者0L（32位和64位的区别），
     * 或者直接就是由0或者0L转成的成void*。
     *
     * nullptr可以转换成void *，然后再隐式转换成char *
     */
    if (asset == nullptr) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "Failed to open the asset.");
        return 0;
    }

    env->ReleaseStringUTFChars(_assetName, assetName);
    off_t offset, length;
    int fd = AAsset_openFileDescriptor(asset, &offset, &length);
    AAsset_close(asset);
    if (fd < 0) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG,
                            "Failed to open the model_data file descriptor.");
        return 0;
    }

    SimpleModel *nn_model = new SimpleModel(length, PROT_READ, fd, offset);
    if (!nn_model->CreateComileModel()) {
        __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, "Failed to prepare the model.");
        return 0;
    }

    return (jlong) (uintptr_t) nn_model;

}

JNIEXPORT jfloat
JNICALL
Java_com_guoxw_geekproject_jniutil_SimpleModelUtil_startCompute(
        JNIEnv *env, jobject, jlong _nnModel, jfloat inputValue1, jfloat inputValue2) {

    SimpleModel *nn_model = (SimpleModel *) _nnModel;
    float result = 0.0f;
    nn_model->Compute(inputValue1, inputValue2, &result);
    return result;
}

JNIEXPORT jfloat
JNICALL
Java_com_guoxw_geekproject_jniutil_SimpleModelUtil_destoryModel(
        JNIEnv *env, jobject, jlong _nnModel) {

    SimpleModel *nn_model = (SimpleModel *) _nnModel;
    delete (nn_model);

}