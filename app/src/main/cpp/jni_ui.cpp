//
// Created by guoxw on 2018/1/22 0022.
//

#include "jni_ui.h"

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUIUtil_createc(JNIEnv *env, jobject, jobject mContext) {
    //获取布局layout集合 包名+R$layout
    jclass native_layout = env->FindClass("com/guoxw/geekproject/R$layout");
    //在内存中找到layout
    jfieldID layout_id = env->GetStaticFieldID(native_layout, "activity_jni_ui", "I");

    LOGI("main is %d", layout_id);

    //找到Activity类
    jclass class_activity = env->FindClass("android/app/Activity");
    if (class_activity == 0) {
        LOGI("FindClass native_activity error");
        return;
    }
    LOGI("native activity: %d", class_activity);

    //调用Activity类中setContentView方法
    jmethodID metthod_setContextView = env->GetMethodID(class_activity, "setContentView",
                                                        "(I)V");
    if (metthod_setContextView == 0) {
        LOGI("FindClass jmethodID_setContentView error");
        return;
    }

    /**
     * 在内存中找到id
     */
    jclass native_findById = env->FindClass("com/guoxw/geekproject/R$id");
    jfieldID etJNIFirst_id = env->GetStaticFieldID(native_findById, "et_jni_first", "I");
    jfieldID tvJNITitle_id = env->GetStaticFieldID(native_findById, "tv_jni_title", "I");

    //这应该是获得内容，但怎么会是jint呢
    jint id_first = env->GetStaticIntField(native_findById, etJNIFirst_id);
    jint id_title = env->GetStaticIntField(native_findById,tvJNITitle_id);

    LOGI("first str is %d", id_first);
    LOGI("title str is %d", id_title);

    /**
     * 这段代码有意义吗
     * ------------------------------------------------------------------------------
     */
    jclass class_activity_1 = env->FindClass("android/app/Activity");
    if (class_activity_1 == 0) {
        LOGI("FindClass class_activity_1 error");
        return;
    }
    LOGI("native_str1_1: %d", class_activity_1);
    /*--------------------------------------------------------------------------------*/

    //调用findViewById方法
    jmethodID method_findViewById = env->GetMethodID(class_activity_1, "findViewById",
                                                     "(I)Landroid/view/View");
    if (method_findViewById == 0) {
        LOGI("GetMethodID methodID_func error");
        return;
    }


    jobject first_id = env->CallObjectMethod(mContext, method_findViewById, id_first);

    //找到TextView类
    jclass class_TextView = env->FindClass("android/widget/TextView");

    if (class_TextView == 0) {
        LOGI("FindClass native_TextView error");
        return;
    }

    //调用setText方法
    jmethodID method_setText = env->GetMethodID(class_TextView, "setText",
                                                "(Ljava/lang/CharSequence;)V");

    if (method_setText == 0) {
        LOGI("GetMethodID methodID_func error");
        return;
    }

    jstring content = env->NewStringUTF("hello jni 222");

    //具体调用方法
    env->CallVoidMethod(first_id, method_setText, content);
    LOGI("to here");



}
