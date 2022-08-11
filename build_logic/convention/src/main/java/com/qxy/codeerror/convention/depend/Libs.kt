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

    // 日志打印 - Timber https://github.com/JakeWharton/timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Network - https://github.com/square/retrofit
    const val retrofit_version = "2.9.0"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val `converter-gson` = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val `adapter-rxjava3` = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val `logging-interceptor` = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    // Security - https://developer.android.com/topic/security/data?hl=zh-cn#kotlin
    const val `security-crypto` = "androidx.security:security-crypto:${Versions.securityCrypto}"
    // For Identity Credential APIs
    const val `security-identity-credential` = "androidx.security:security-identity-credential:1.0.0"
    // For App Authentication APIs
    const val `security-app-authenticator` = "androidx.security:security-app-authenticator:1.0.0"

    // Paging 3 - https://developer.android.com/topic/libraries/architecture/paging/v3-overview
    const val `paging-runtime` = "androidx.paging:paging-runtime:${Versions.paging}"
    // alternatively - without Android dependencies for tests
    const val `paging-common` = "androidx.paging:paging-common:${Versions.paging}"
    // optional - RxJava2 support
    const val `paging-rxjava2` = "androidx.paging:paging-rxjava2:${Versions.paging}"
    // optional - RxJava3 support
    const val `paging-rxjava3` = "androidx.paging:paging-rxjava3:${Versions.paging}"
    // optional - Guava ListenableFuture support
    const val `paging-giava` = "androidx.paging:paging-guava:${Versions.paging}"
    // optional - Jetpack Compose integration
    const val `paging-compose` = "androidx.paging:paging-compose:1.0.0-alpha15"

    // Glide - https://github.com/bumptech/glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val `glide-compiler` = "com.github.bumptech.glide:compiler:${Versions.glide}"
}