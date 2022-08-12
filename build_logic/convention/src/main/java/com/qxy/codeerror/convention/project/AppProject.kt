@file:Suppress("UnstableApiUsage")

package com.qxy.codeerror.convention.project

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.qxy.codeerror.convention.project.base.BaseAppProject
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import java.io.File

/**
 * app模块实现类
 * 实现app模块依赖其他所有的module和lib模块配置
 * 配置debug和release的签名及BuildConfig
 */
class AppProject(project: Project) : BaseAppProject(project) {
    override fun initProjectInImpl() {
        // app模块不需要依赖的模块
        val excludeModuleList = listOf<String>()
        // app需要依赖的所有子模块
        val includeModuleList = rootProject.allprojects.map { it.name }

        // 依赖所有其他模块
        dependencies {
            rootDir.listFiles()?.filter {
                it.isDirectory // 属于文件夹
                        && it.name != "app" // 排除app模块本身
                        && (it.name.startsWith("lib_") || it.name.startsWith("module_"))
                        && it.name !in excludeModuleList
                        && it.name in includeModuleList
            }?.forEach {
                "implementation"(project(":${it.name}"))
            }
        }
        configSigning()
    }

    private fun configSigning() {
        apply(from = "$rootDir/build_logic/secret/secret.gradle")
        extensions.configure<BaseAppModuleExtension> {
            val ext = extensions["ext"] as ExtraPropertiesExtension
            operator fun Any?.get(key: String): Any? {
                check(this is Map<*, *>) { "key = $key，当前 key 值对应的不为 Map 类型" }
                return this.getOrDefault(key, null)
            }

            signingConfigs {
                create("config") {
                    keyAlias = ext["secret"]["sign"]["RELEASE_KEY_ALIAS"] as String
                    keyPassword = ext["secret"]["sign"]["RELEASE_KEY_PASSWORD"] as String
                    storePassword = ext["secret"]["sign"]["RELEASE_STORE_PASSWORD"] as String
                    storeFile = file("$rootDir/build_logic/secret/key-qxy.jks")
                }
            }

            applicationVariants.all {
                outputs.map { it as BaseVariantOutputImpl }
                    .forEach { output ->
                        output.outputFileName =
                            "DouyinQxy-${versionName}-${versionCode}-${baseName}.apk"
                    }
            }

            buildTypes {
                release {
                    signingConfig = signingConfigs.getByName("config")
                }
                debug {
                    signingConfig = signingConfigs.getByName("config")
                }
            }
        }
    }

}