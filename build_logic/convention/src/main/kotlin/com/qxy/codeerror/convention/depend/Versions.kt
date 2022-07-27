package com.qxy.codeerror.convention.depend

@Suppress("ObjectPropertyName", "SpellCheckingInspection")
object Versions {
    // 基础库
    const val appcompat = "androidx.appcompat:appcompat:1.4.2"

    // 官方控件库
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
    const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
    const val material = "com.google.android.material:material:1.6.1"

    // 官方扩展库KTX
    // https://developer.android.google.cn/kotlin/ktx?hl=zh_cn#core
    const val `core-ktx` = "androidx.core:core-ktx:1.8.0"
    // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxcollection
    const val `collection-ktx` = "androidx.collection:collection-ktx:1.2.0"
    // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxfragmentapp
    const val `fragment-ktx` = "androidx.fragment:fragment-ktx:1.5.0"
    // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxactivity
    const val `activity-ktx` = "androidx.activity:activity-ktx:1.5.0"

    // ARouter - https://github.com/alibaba/ARouter
    private const val arouter_version = "1.5.2"
    const val `arouter-api` = "com.alibaba:arouter-api:$arouter_version"
    const val `arouter-compiler` = "com.alibaba:arouter-compiler:$arouter_version"

    // room - https://developer.android.com/training/data-storage/room
    private const val room_version = "2.4.2"
    const val `room-runtime` = "androidx.room:room-runtime:$room_version"
    const val `room-compiler` = "androidx.room:room-compiler:$room_version"
    // room扩展 - https://developer.android.google.cn/kotlin/ktx?hl=zh_cn#room
    const val `room-ktx` = "androidx.room:room-ktx:$room_version"
    const val `room-rxjava3` = "androidx.room:room-rxjava3:$room_version"
    const val `room-paging` = "androidx.room:room-paging:$room_version"
    // room optional - Test helpers
    const val `room-testing` = "androidx.room:room-testing:$room_version"

    // 官方 lifecycle 扩展
    // https://developer.android.google.cn/jetpack/androidx/releases/lifecycle
    // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxlifecycle
    private const val lifecycle_version = "2.5.0"
    const val `viewmodel-ktx` = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    const val `livedata-ktx` = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    // Lifecycles only (without ViewModel or LiveData)
    const val `runtime-ktx` = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
    // Annotation processor
    const val `lifecycle-compiler` = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"
    // 下面这几个是可选的，默认不主动依赖
    // optional - helpers for implementing LifecycleOwner in a Service
    const val `lifecycle-service` = "androidx.lifecycle:lifecycle-service:$lifecycle_version"
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    const val `lifecycle-process` = "androidx.lifecycle:lifecycle-process:$lifecycle_version"
    // optional - ReactiveStreams support for LiveData
    // LiveData 与 Rxjava、Flow 的转换
    const val `lifecycle-reactivestreams-ktx` = "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version"
    // optional - Test helpers for LiveData
    private const val arch_version = "2.1.0"
    const val `core-testing` = "androidx.arch.core:core-testing:$arch_version"
}