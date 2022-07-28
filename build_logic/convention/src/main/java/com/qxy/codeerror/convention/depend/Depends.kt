package com.qxy.codeerror.convention.depend

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * 依赖Versions最基础的库
 */
internal fun Project.dependAndroidBase() {
    dependencies {
        "implementation"(Versions.appcompat)
        "implementation"(Versions.constraintlayout)
    }
}

/**
 * 默认所有module依赖的官方组件
 */
internal fun Project.dependAndroidUI() {
    dependencies {
        "implementation"(Versions.recyclerview)
        "implementation"(Versions.viewpager2)
        "implementation"(Versions.material)
    }
}

/**
 * 默认所有module依赖ktx
 */
internal fun Project.dependAndroidKtx() {
    dependencies {
        "implementation"(Versions.`core-ktx`)
        "implementation"(Versions.`collection-ktx`)
        "implementation"(Versions.`fragment-ktx`)
        "implementation"(Versions.`activity-ktx`)
    }
}

/**
 * 所有模块都默认依赖ARouter
 */
internal fun Project.dependARouter() {
    dependencies {
        "implementation"(Versions.`arouter-api`)
        "kapt"(Versions.`arouter-compiler`)
    }
}

/**
 * room基础依赖库
 */
fun Project.dependRoom() {
    dependencies {
        "implementation"(Versions.`room-runtime`)
        "implementation"(Versions.`room-ktx`)
        "kapt"(Versions.`room-compiler`)
    }
}

/*
* 所有 module 模块默认依赖
*/
internal fun Project.dependLifecycleKtx() {
    dependencies {
        "implementation"(Versions.`viewmodel-ktx`)
        "implementation"(Versions.`livedata-ktx`)
        "implementation"(Versions.`runtime-ktx`)
        "kapt"(Versions.`lifecycle-compiler`)
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