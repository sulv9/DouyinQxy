package com.qxy.codeerror.convention.project

import com.android.build.api.dsl.LibraryExtension
import com.qxy.codeerror.convention.depend.dependAndroidUI
import com.qxy.codeerror.convention.depend.dependGlide
import com.qxy.codeerror.convention.depend.dependNetwork
import com.qxy.codeerror.convention.depend.dependRoom
import com.qxy.codeerror.convention.project.base.BaseLibProject
import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get

/**
 * lib_base模块，指各个项目都会公用的部分，如数据存储、网络请求和base类的封装等
 * 统一按需添加依赖
 */
class LibBaseProject(project: Project): BaseLibProject(project) {
    override fun initProjectInImpl() {
        dependNetwork()
        dependRoom()
        dependAndroidUI()
        dependGlide()
        configBuild()
    }

    private fun configBuild() {
        apply(from = "$rootDir/build_logic/secret/secret.gradle")
        extensions.configure<LibraryExtension> {
            val ext = extensions["ext"] as ExtraPropertiesExtension
            operator fun Any?.get(key: String): Any? {
                check(this is Map<*, *>) { "key = $key，当前 key 值对应的不为 Map 类型" }
                return this.getOrDefault(key, null)
            }

            defaultConfig {
                (ext["secret"]["buildConfig"] as Map<String, String>).forEach { (k, v) ->
                    buildConfigField("String", k, v)
                }
            }
        }
    }
}