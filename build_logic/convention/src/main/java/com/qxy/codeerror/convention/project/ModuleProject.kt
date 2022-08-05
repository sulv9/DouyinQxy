package com.qxy.codeerror.convention.project

import com.qxy.codeerror.convention.depend.*
import com.qxy.codeerror.convention.depend.dependAndroidUI
import com.qxy.codeerror.convention.depend.dependLibBase
import com.qxy.codeerror.convention.depend.dependLibCommon
import com.qxy.codeerror.convention.depend.dependLifecycleKtx
import com.qxy.codeerror.convention.project.base.BaseLibProject
import org.gradle.api.Project

/**
 * module模块
 * 依赖lib_common, lib_base, AndroidUI和LifecycleKtx、抖音sdk
 */
class ModuleProject(project: Project): BaseLibProject(project) {
    override fun initProjectInImpl() {
        dependLibCommon()
        dependLibBase()
        dependAndroidUI()
        dependLifecycleKtx()
        dependApiAccount()
    }
}