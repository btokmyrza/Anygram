plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/scripts/feature-module.gradle")

android {
    namespace = "kz.btokmyrza.anygram.feature.otp"
}

dependencies {

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)

    implementation(project(":feature:auth-common"))
    
    implementation(project(":library:core-data"))
    implementation(project(":library:core-domain"))
    implementation(project(":library:core-presentation"))
    implementation(project(":library:tdlib"))
}
