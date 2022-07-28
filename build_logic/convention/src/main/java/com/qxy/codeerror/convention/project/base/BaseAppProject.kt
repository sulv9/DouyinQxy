@file:Suppress("UnstableApiUsage")

package com.qxy.codeerror.convention.project.base

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

/**
 * app模块基础类的封装，统一配置主模块gradle内容，主要包括以下内容：
 * 1. 应用App插件, kotlin-android和kapt插件
 * 2. 调用父类方法配置闭包
 * 3. 配置applicationId、版本号和sdk等信息
 * (集成调试时app为主模块，单模块调试时单个指定模块为主模块)
 */
abstract class BaseAppProject(project: Project): BaseAndroidProject(project) {
    override fun initProjectInBase() {
        apply(plugin = "com.android.application")
        apply(plugin = "kotlin-android")
        apply(plugin = "kotlin-kapt")

        extensions.configure<BaseAppModuleExtension> {
            configCommonForAndroid()

            defaultConfig {
                applicationId = getApplicationId(project.name)
                versionCode = BaseAndroidProject.versionCode
                versionName = BaseAndroidProject.versionName
                targetSdk = BaseAndroidProject.targetSdk
            }
        }

        super.initProjectInBase()
    }
}