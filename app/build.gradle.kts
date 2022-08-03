import com.qxy.codeerror.convention.depend.dependApiAccount
plugins {
    id("module-manager")
}

//dependApiAccount()

dependencies {
    implementation(project(":lib_account:api_account"))
    implementation(project(":lib_account"))
}