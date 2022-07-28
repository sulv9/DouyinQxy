@file:Suppress("UnstableApiUsage")

// 启用versionCatalogs功能
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
rootProject.name = "build_logic"
include(":convention")