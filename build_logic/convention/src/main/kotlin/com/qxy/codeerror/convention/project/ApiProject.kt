package com.qxy.codeerror.convention.project

import com.qxy.codeerror.convention.project.base.BaseLibProject
import org.gradle.api.Project

/**
 * Api模块按需依赖，尽量只包含接口和简单逻辑
 */
class ApiProject(project: Project): BaseLibProject(project) {
    override fun initProjectInImpl() {
    }
}