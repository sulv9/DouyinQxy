package com.qxy.codeerror.convention.project

import com.qxy.codeerror.convention.depend.dependNetwork
import com.qxy.codeerror.convention.project.base.BaseLibProject
import org.gradle.api.Project

/**
 * lib_base模块，指各个项目都会公用的部分，如数据存储、网络请求和base类的封装等
 * 统一按需添加依赖
 */
class LibBaseProject(project: Project): BaseLibProject(project) {
    override fun initProjectInImpl() {
        dependNetwork()
    }
}