package com.qxy.codeerror.convention.project

import com.qxy.codeerror.convention.depend.*
import com.qxy.codeerror.convention.depend.dependAndroidUI
import com.qxy.codeerror.convention.depend.dependLibBase
import com.qxy.codeerror.convention.project.base.BaseLibProject
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * lib_common模块，指当前项目各个模块公用的自定义view、资源文件等等
 * 依赖lib_base模块并统一按需添加依赖
 */
class LibCommonProject(project: Project): BaseLibProject(project) {
    override fun initProjectInImpl() {
        dependLibBase()
        dependAndroidUI()
        dependNetwork()
        dependRoom()
        dependencies {
            "implementation"(Libs.`logging-interceptor`)
            "implementation"(Libs.`converter-gson`)
        }
    }
}