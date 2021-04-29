#include <jni.h>
#include <string>
#include "art_method.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_learning_andfix_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_learning_andfix_DexManager_replaceMethod(JNIEnv *env, jobject thiz, jobject right_method,
                                                    jobject err_method) {
    // TODO: implement replaceMethod()
    art::mirror::ArtMethod *rightMethod = (art::mirror::ArtMethod *)(env->FromReflectedMethod(
            right_method));
    art::mirror::ArtMethod *errMethod = (art::mirror::ArtMethod *)(env->FromReflectedMethod(
            err_method));

    errMethod->declaring_class_ = rightMethod->declaring_class_;
    errMethod->dex_cache_resolved_methods_ = rightMethod->dex_cache_resolved_methods_;

    errMethod->access_flags_ = rightMethod->access_flags_;
    errMethod->dex_cache_resolved_types_ = rightMethod->dex_cache_resolved_types_;
    errMethod->dex_code_item_offset_ = rightMethod->dex_code_item_offset_;
    //这里   方法索引的替换
    errMethod->method_index_ = rightMethod->method_index_;
    errMethod->dex_method_index_ = rightMethod->dex_method_index_;


    //入口
    errMethod->ptr_sized_fields_.entry_point_from_jni_=rightMethod->ptr_sized_fields_.entry_point_from_jni_;
    //机器码模式
    errMethod->ptr_sized_fields_.entry_point_from_quick_compiled_code_=rightMethod->ptr_sized_fields_.entry_point_from_quick_compiled_code_;
}