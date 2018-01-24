//
// Created by guoxw on 2018/1/22 0022.
//

#include "jni_ui.h"

jclass native_findById;

jstring getPackageName(JNIEnv *env, jobject mContext) {

    jclass native_class = env->GetObjectClass(mContext);
    //获取包名
    jmethodID methodID_packagename = env->GetMethodID(native_class, "getPackageName",
                                                      "()Ljava/lang/String;");
    jstring name_str = static_cast<jstring>(env->CallObjectMethod(mContext, methodID_packagename));

    return name_str;
}

jint findbyId(JNIEnv *env, jobject mContext, char *id) {

//    const char *p = env->GetStringUTFChars(name_str, 0);
//
//    LOGI("package name1 : %d", *p);
//    LOGI("package name2 : %d", p);
//    LOGI("package name3 : %d", *&p);
//
//    char *s = "aa";
//    LOGI("content s :%d",s);
//    LOGI("content s :%d",*s);


    native_findById = env->FindClass("com/guoxw/geekproject/R$id");
    jfieldID tvJNITitle_id = env->GetStaticFieldID(native_findById, id, "I");
    jint id_title = env->GetStaticIntField(native_findById, tvJNITitle_id);
    LOGI("title str is %d", id_title);

    return id_title;
}

JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUIUtil_nativec(JNIEnv *env, jobject, jobject mContext) {
    //获取布局layout集合 包名+R$layout
    jclass native_layout = env->FindClass("com/guoxw/geekproject/R$layout");
    //在内存中找到layout
    jfieldID layout_id = env->GetStaticFieldID(native_layout, "activity_jni_ui", "I");

    /**
     * 必须加上这儿
     */
    //找到layout文件
    jint main = env->GetStaticIntField(native_layout, layout_id);
    LOGI("main is %d", main);

    //找到Activity类
    jclass class_activity = env->FindClass("android/app/Activity");
    if (class_activity == 0) {
        LOGI("FindClass native_activity error");
        return;
    }
    LOGI("native activity: %d", class_activity);

    //调用Activity类中setContentView方法
    jmethodID metthod_setContextView = env->GetMethodID(class_activity, "setContentView", "(I)V");
    if (metthod_setContextView == 0) {
        LOGI("FindClass jmethodID_setContentView error");
        return;
    }

    /**
     * 必须加上这儿
     */
    //设置activity布局
    env->CallVoidMethod(mContext, metthod_setContextView, main);

    /**
     * 在内存中找到id
     */
//    jclass native_findById = env->FindClass("com/guoxw/geekproject/R$id");
//    jfieldID etJNIFirst_id = env->GetStaticFieldID(native_findById, "et_jni_first", "I");


    //这应该是获得内容，但怎么会是jint呢
//    jint id_first = env->GetStaticIntField(native_findById, etJNIFirst_id);
    jint id_first = findbyId(env, mContext, "et_jni_first");

    LOGI("first str is %d", id_first);


    /**
     * 这段代码有意义吗
     * 这个输出和上一个输出的值不一样
     * ------------------------------------------------------------------------------
     */
    jclass class_activity_1 = env->FindClass("android/app/Activity");
    if (class_activity_1 == 0) {
        LOGI("FindClass class_activity_1 error");
        return;
    }
    LOGI("class_activity_1: %d", class_activity_1);
    /*--------------------------------------------------------------------------------*/

    //调用findViewById方法
    //(I)Landroid/view/View;必须加上分号，不然会找不到该方法，签名不对
    jmethodID method_findViewById = env->GetMethodID(class_activity_1, "findViewById",
                                                     "(I)Landroid/view/View;");
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

    jstring content1 = env->NewStringUTF("hello jni 222");

    //具体调用方法
    env->CallVoidMethod(first_id, method_setText, content1);

    LOGI("to here");


}


JNIEXPORT jstring JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUIUtil_getPackageName(JNIEnv *env, jobject,
                                                            jobject mContext) {

    return getPackageName(env, mContext);

}


JNIEXPORT void JNICALL
Java_com_guoxw_geekproject_jniutil_JNIUIUtil_sureClick(JNIEnv *env, jobject) {


}