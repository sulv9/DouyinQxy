import com.qxy.codeerror.convention.depend.*

plugins {
    id("module-manager")
}

dependencies {
    implementation(Libs.palette)
    implementation(Libs.`logging-interceptor`)
    implementation(Libs.`converter-gson`)
}