import com.qxy.codeerror.convention.project.*

/**
 * 根据工程名分配不同的插件
 */

val projectName: String = project.name
when {
    projectName == "app" -> AppProject(project).apply()
    projectName == "lib_common" -> LibCommonProject(project).apply()
    projectName == "lib_base" -> LibBaseProject(project).apply()
    projectName.startsWith("lib_") -> LibProject(project).apply()
    projectName.startsWith("api_") -> ApiProject(project).apply()
    projectName.startsWith("module_") -> ModuleProject(project).apply()
    else -> throw Exception("未知类型模块：name = $projectName dir = $projectDir")
}