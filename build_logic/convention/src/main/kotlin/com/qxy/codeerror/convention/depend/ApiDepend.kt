package com.qxy.codeerror.convention.depend

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * 统一配置Api路径
 * api路径示例为：
 * val api_test = ":lib_test:api_test".path
 */
object ApiPath {
    /**
     * Api路径
     * 如果api有多个实现模块可以这样写:
     * val api_test = ":api_test".path impl ":lib_a" 或
     * val api_test = ":api_test".path impl listOf(":lib_a", ":lib_b")
     */
    val api_account = ":lib_account:api_account".path

    private val String.path
        get() = ApiPathSaver(this).also { saver ->
            val parentModulePath = this.substringBeforeLast(":")
            if (parentModulePath.isNotBlank()) {
                saver.addImplPath(parentModulePath)
            }
        }

    private infix fun ApiPathSaver.impl(implPath: List<String>) =
        this.apply { addImplPath(implPath) }

    private infix fun ApiPathSaver.impl(implPath: String) =
        this.apply { addImplPath(implPath) }
}

internal fun Project.dependApi(apiPathSaver: ApiPathSaver) {
    if (!apiPathSaver.hasImpl()) {
        throw RuntimeException("${apiPathSaver.apiPath}模块无实现模块")
    }
    dependencies {
        "implementation"(project(apiPathSaver.apiPath))
    }
}

internal fun Project.dependApiImpl(apiPathSaver: ApiPathSaver) {
    dependencies {
        apiPathSaver.apiImplList.forEach {
            "implementation"(project(it))
        }
    }
}

class ApiPathSaver(
    val apiPath: String
) {
    val apiImplList = mutableListOf<String>()

    fun hasImpl() = apiImplList.isNotEmpty()

    fun addImplPath(implPath: List<String>) = apiImplList.addAll(implPath)

    fun addImplPath(implPath: String) = apiImplList.add(implPath)
}