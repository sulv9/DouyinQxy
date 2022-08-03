package com.qxy.codeerror.convention.project.base

import org.gradle.api.Project

/**
 * 工程基类的封装，使用传入的[project]参数作为类代理，对外暴露[apply]方法
 */
abstract class BaseProject(project: Project) : Project by project {

    fun apply() {
        initProjectInBase()
        initProjectInImpl()
    }

    /**
     * 该方法用在Base类中实现对项目初始化
     * 如果实现该方法则强制使用super
     */
    protected open fun initProjectInBase() {}

    /**
     * 该方法用在实现类中对项目初始化
     * 强制非抽象类重写该方法
     */
    protected abstract fun initProjectInImpl()

}