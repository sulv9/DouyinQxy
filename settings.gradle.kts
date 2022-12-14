@file:Suppress("UnstableApiUsage")

include(":module_main")


pluginManagement {
    includeBuild("build_logic")
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("$rootDir/build/maven") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://artifact.bytedance.com/repository/AwemeOpenSDK") }
        google()
        mavenCentral()
    }
}
rootProject.name = "DouyinQxy"
include(":app")
include(":module_personal")
include(":module_rank")
include(":module_login")
include(":lib_base")
include(":lib_common")
include(":lib_account")
include(":lib_account:api_account")
