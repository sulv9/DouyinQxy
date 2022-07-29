package com.qxy.codeerror.convention.depend

@Suppress("ObjectPropertyName", "SpellCheckingInspection")
object Libs {
    // 基础库
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

    // 官方控件库
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val material = "com.google.android.material:material:${Versions.material}"

    // 官方扩展库KTX
    // https://developer.android.google.cn/kotlin/ktx?hl=zh_cn#core
    const val `core-ktx` = "androidx.core:core-ktx:${Versions.`core-ktx`}"
    // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxcollection
    const val `collection-ktx` = "androidx.collection:collection-ktx:${Versions.`collection-ktx`}"
    // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxfragmentapp
    const val `fragment-ktx` = "androidx.fragment:fragment-ktx:${Versions.`fragment-ktx`}"
    // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxactivity
    const val `activity-ktx` = "androidx.activity:activity-ktx:${Versions.`activity-ktx`}"

    // ARouter - https://github.com/alibaba/ARouter
    const val `arouter-api` = "com.alibaba:arouter-api:${Versions.arouter}"
    const val `arouter-compiler` = "com.alibaba:arouter-compiler:${Versions.arouter}"

    // room - https://developer.android.com/training/data-storage/room
    const val `room-runtime` = "androidx.room:room-runtime:${Versions.room}"
    const val `room-compiler` = "androidx.room:room-compiler:${Versions.room}"
    // room扩展 - https://developer.android.google.cn/kotlin/ktx?hl=zh_cn#room
    const val `room-ktx` = "androidx.room:room-ktx:${Versions.room}"
    const val `room-rxjava3` = "androidx.room:room-rxjava3:${Versions.room}"
    const val `room-paging` = "androidx.room:room-paging:${Versions.room}"
    // room optional - Test helpers
    const val `room-testing` = "androidx.room:room-testing:${Versions.room}"

    // 官方 lifecycle 扩展
    // https://developer.android.google.cn/jetpack/androidx/releases/lifecycle
    // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxlifecycle
    const val `viewmodel-ktx` = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val `livedata-ktx` = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    // Lifecycles only (without ViewModel or LiveData)
    const val `runtime-ktx` = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    // Annotation processor
    const val `lifecycle-compiler` = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    // 下面这几个是可选的，默认不主动依赖
    // optional - helpers for implementing LifecycleOwner in a Service
    const val `lifecycle-service` = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"
    // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
    const val `lifecycle-process` = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
    // optional - ReactiveStreams support for LiveData
    // LiveData 与 Rxjava、Flow 的转换
    const val `lifecycle-reactivestreams-ktx` = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${Versions.lifecycle}"
    // optional - Test helpers for LiveData
    const val `core-testing` = "androidx.arch.core:core-testing:${Versions.archTest}"

    // palette
    const val palette = "androidx.palette:palette:${Versions.palette}"

    // 抖音SDK
    const val `bytedance-opensdk-china-external` = "com.bytedance.ies.ugc.aweme:opensdk-china-external:${Versions.douYinSdk}"
    const val `bytedance-opensdk-common` = "com.bytedance.ies.ugc.aweme:opensdk-common:${Versions.douYinSdk}"
}