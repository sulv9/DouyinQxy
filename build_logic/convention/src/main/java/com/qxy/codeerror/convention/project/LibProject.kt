package com.qxy.codeerror.convention.project

import com.qxy.codeerror.convention.depend.dependLibBase
import com.qxy.codeerror.convention.depend.dependLibCommon
import com.qxy.codeerror.convention.project.base.BaseLibProject
import org.gradle.api.Project

/**
 * Lib模块
 * 依赖lib_common和lib_base
 */
class LibProject(project: Project): BaseLibProject(project) {
    override fun initProjectInImpl() {
        dependLibCommon()
        dependLibBase()
    }

    /**
     * 有些模块不需要导入其子模块时重写该方法判断
     */
    override fun isDependChildModule(): Boolean {
        val excludeModuleList = listOf<String>()
        if (name in excludeModuleList) {
            return false
        }
        return super.isDependChildModule()
    }
}