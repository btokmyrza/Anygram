plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/scripts/feature-module.gradle")

android {
    namespace = "kz.btokmyrza.anygram.feature.auth.start"
}

dependencies {

    implementation(libs.androidx.activity.compose)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)

    implementation(project(":feature:auth-common"))
    implementation(project(":feature:phone-number"))

    implementation(project(":library:core-data"))
    implementation(project(":library:core-domain"))
    implementation(project(":library:core-presentation"))
    implementation(project(":library:tdlib"))
}