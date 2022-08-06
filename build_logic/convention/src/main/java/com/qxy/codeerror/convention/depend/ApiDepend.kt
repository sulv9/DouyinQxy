@file:Suppress("ObjectPropertyName", "SpellCheckingInspection")

package com.qxy.codeerror.convention.depend

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * 统一配置Api路径，以检测api模块的实现模块。
 *
 * 如果api的实现类只在其父文件夹对应的模块中，可以这样写:
 *
 *   const val api_test = ":lib_test:api_test"
 *
 * 如果api的实现类在多个模块中，可以这样写:
 *
 *   val api_test = ":api_test" impl ":lib_a"
 *
 *     或
 *
 *   val api_test = ":api_test" impl listOf(":lib_a", ":lib_b")
 */
object ApiPath {

    const val api_account = ":lib_account:api_account"

    private infix fun String.impl(implPath: List<String>) =
        this.toApiPathSaver().apply { addImplPath(implPath) }

    private infix fun String.impl(implPath: String) =
        this.toApiPathSaver().apply { addImplPath(implPath) }
}

/**
 * 传入api字符串路径依赖api
 */
internal fun Project.dependApi(apiPath: String) = dependApi(apiPath.toApiPathSaver())

/**
 * 传入apiPathSaver对象依赖api
 */
internal fun Project.dependApi(apiPathSaver: ApiPathSaver) {
    if (!apiPathSaver.hasImpl()) {
        throw RuntimeException("${apiPathSaver.apiPath}模块无实现模块")
    }
    dependencies {
        "implementation"(project(apiPathSaver.apiPath))
    }
}

/**
 * 传入api字符串路径依赖api的实现类，单模块调试中需要用到
 */
internal fun Project.dependApiImpl(apiPath: String) = dependApiImpl(apiPath.toApiPathSaver())

/**
 * 传入apiPathSaver对象依赖api的实现类，单模块调试中需要用到
 */
internal fun Project.dependApiImpl(apiPathSaver: ApiPathSaver) {
    dependencies {
        apiPathSaver.apiImplList.forEach {
            "implementation"(project(it))
        }
    }
}

/**
 * 将api路径字符串转换为封装的存储Api路径的类
 */
private fun String.toApiPathSaver() =
    ApiPathSaver(this).apply {
        val parentModulePath = apiPath.substringBeforeLast(":")
        if (parentModulePath.isNotBlank()) {
            addImplPath(parentModulePath)
        }
    }

/**
 * 存储api模块的路径及其实现类所在的模块路径
 */
class ApiPathSaver(
    val apiPath: String
) {
    val apiImplList = mutableListOf<String>()

    fun hasImpl() = apiImplList.isNotEmpty()

    fun addImplPath(implPath: List<String>) = apiImplList.addAll(implPath)

    fun addImplPath(implPath: String) = apiImplList.add(implPath)
}