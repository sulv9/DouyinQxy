@file:Suppress("UnstableApiUsage")

package com.qxy.codeerror.convention.project.base

import com.android.build.api.dsl.*
import com.qxy.codeerror.convention.depend.*
import com.qxy.codeerror.convention.depend.dependARouter
import com.qxy.codeerror.convention.depend.dependAndroidBase
import com.qxy.codeerror.convention.depend.dependAndroidKtx
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

/**
 * Android所有工程的统一配置基类，主要实现以下几个功能：
 * 1. 自动依赖与工程同文件夹下的其他api或lib模块
 * 2. 添加所有工程都需要依赖的库(如ktx)
 * 3. 对Android闭包部分进行统一配置
 */
abstract class BaseAndroidProject(project: Project) : BaseProject(project) {

    companion object {
        const val minSdk = 21
        const val targetSdk = 32
        const val compileSdk = 32

        const val versionCode = 1
        const val versionName = "1.0"

        fun getApplicationId(projectName: String) = when(projectName) {
            "app" -> "com.qxy.codeerror"
            else -> "com.qxy.codeerror.${projectName}"
        }
    }

    // 是否自动依赖api或lib子模块
    protected open fun isDependChildModule(): Boolean = true

    override fun initProjectInBase() {
        // 自动依赖该模块同文件夹下的其他api或lib模块
        dependencies {
            if (isDependChildModule()) {
                val allProjectsName = rootProject.subprojects.map { it.name }

                projectDir.listFiles()?.filter {
                    it.isDirectory // 属于文件夹
                            && "(lib_.+)|(api_.+)".toRegex().matches(it.name) // 以lib或api开头
                            && allProjectsName.contains(it.name) // 属于Project
                }?.forEach {
                    "implementation"(project(":${name}:${it.name}"))
                }
            }
        }

        // 依赖Android基础库
        dependAndroidBase()
        // 依赖Android-Ktx
        dependAndroidKtx()
        // 依赖Hilt
        dependHilt()
        // 依赖ARouter路由
        dependARouter()
        // 依赖抖音SDK
        dependDouYin()

        super.initProjectInBase()

        // kapt扩展配置
        extensions.configure<KaptExtension> {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
                arg("room.schemaLocation", "${project.projectDir}/schemas")
            }
        }
    }

    /**
     * 统一配置Android闭包的公共部分
     */
    protected fun <A : BuildFeatures, B : BuildType, C : DefaultConfig, D : ProductFlavor>
            CommonExtension<A, B, C, D>.configCommonForAndroid() {

        compileSdk = BaseAndroidProject.compileSdk

        defaultConfig {
            minSdk = BaseAndroidProject.minSdk
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            release {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            debug {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        (this as ExtensionAware).extensions.configure<KotlinJvmOptions> {
            jvmTarget = "1.8"
        }

        lint {
            // 编译遇到错误不退出
            abortOnError = false
            // 错误输出文件
            baseline = project.file("lint-baseline.xml")
        }

        buildFeatures {
            viewBinding = true
        }

    }

}