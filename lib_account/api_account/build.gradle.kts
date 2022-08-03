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
    implementation(com.qxy.codeerror.convention.depend.Libs.`arouter-api`)
    kapt(com.qxy.codeerror.convention.depend.Libs.`arouter-compiler`)
}