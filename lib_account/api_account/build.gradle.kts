plugins {
    id("module-manager")
}

apply(plugin = "kotlin-kapt")

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}

dependencies {
    implementation(project(":lib_account"))
    implementation(project(":lib_base"))
    implementation(com.qxy.codeerror.convention.depend.Libs.`arouter-api`)
    kapt(com.qxy.codeerror.convention.depend.Libs.`arouter-compiler`)
}