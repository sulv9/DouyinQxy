@file:Suppress("UnstableApiUsage")

package com.qxy.codeerror.convention.project.base

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

/**
 * Library模块基础类封装，主要实现以下内容：
 * 1. 应用Library插件, kotlin-android和kapt插件
 * 2. 调用父类方法配置闭包
 * 3. 配置targetSdk信息
 */
abstract class BaseLibProject(project: Project): BaseAndroidProject(project) {
    override fun initProjectInBase() {
        apply(plugin = "com.android.library")
        apply(plugin = "kotlin-android")
        apply(plugin = "kotlin-kapt")

        extensions.configure<LibraryExtension> {
            configCommonForAndroid()

            defaultConfig {
                targetSdk = BaseAndroidProject.targetSdk
            }
        }

        super.initProjectInBase()
    }
}