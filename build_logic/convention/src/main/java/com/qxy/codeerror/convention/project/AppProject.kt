package com.qxy.codeerror.convention.project

import com.qxy.codeerror.convention.project.base.BaseAppProject
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * app模块实现类
 * 实现app模块依赖其他所有的module和lib模块配置
 */
class AppProject(project: Project): BaseAppProject(project) {
    override fun initProjectInImpl() {
        // app模块不需要依赖的模块
        val excludeModuleList = listOf<String>()
        // app需要依赖的所有子模块
        val includeModuleList =rootProject.allprojects.map { it.name }

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
    }

}