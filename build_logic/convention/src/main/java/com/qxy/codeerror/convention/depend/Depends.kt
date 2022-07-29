package com.qxy.codeerror.convention.depend

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * 依赖Android最基础的库
 */
internal fun Project.dependAndroidBase() {
    dependencies {
        "implementation"(Libs.appcompat)
        "implementation"(Libs.constraintLayout)
    }
}

/**
 * 默认所有module依赖的官方组件
 */
internal fun Project.dependAndroidUI() {
    dependencies {
        "implementation"(Libs.recyclerview)
        "implementation"(Libs.viewpager2)
        "implementation"(Libs.material)
    }
}

/**
 * 默认所有module依赖ktx
 */
internal fun Project.dependAndroidKtx() {
    dependencies {
        "implementation"(Libs.`core-ktx`)
        "implementation"(Libs.`collection-ktx`)
        "implementation"(Libs.`fragment-ktx`)
        "implementation"(Libs.`activity-ktx`)
    }
}

/**
 * 所有模块都默认依赖ARouter
 */
internal fun Project.dependARouter() {
    dependencies {
        "implementation"(Libs.`arouter-api`)
        "kapt"(Libs.`arouter-compiler`)
    }
}

/**
 * room基础依赖库
 */
fun Project.dependRoom() {
    dependencies {
        "implementation"(Libs.`room-runtime`)
        "implementation"(Libs.`room-ktx`)
        "kapt"(Libs.`room-compiler`)
    }
}

/*
* 所有 module 模块默认依赖
*/
internal fun Project.dependLifecycleKtx() {
    dependencies {
        "implementation"(Libs.`viewmodel-ktx`)
        "implementation"(Libs.`livedata-ktx`)
        "implementation"(Libs.`runtime-ktx`)
        "kapt"(Libs.`lifecycle-compiler`)
    }
}

/**
 * 依赖lib_common库
 */
internal fun Project.dependLibCommon() {
    dependencies {
        "implementation"(project(":lib_common"))
    }
}

internal fun Project.dependLibBase() {
    dependencies {
        "implementation"(project(":lib_base"))
    }
}

fun Project.dependApiAccount() {
    dependApi(ApiPath.api_account)
}

@Suppress("UnstableApiUsage")
val Project.hilt_version get() =
    extensions.getByType<VersionCatalogsExtension>()
        .named("libs")
        .findVersion("hilt")
        .get().toString()

fun Project.dependHilt() {
    apply(plugin = "dagger.hilt.android.plugin")
    dependencies {
        "implementation"("com.google.dagger:hilt-android:${hilt_version}")
        "kapt"("com.google.dagger:hilt-android-compiler:${hilt_version}")
    }
}

fun Project.dependDouYin() {
    dependencies {
        "implementation"(Libs.`bytedance-opensdk-china-external`)
        "implementation"(Libs.`bytedance-opensdk-common`)
    }
}