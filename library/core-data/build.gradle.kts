plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/scripts/library-module.gradle")

android {
    namespace = "kz.btokmyrza.anygram.library.core.data"
}

dependencies {

    implementation(libs.androidx.datastore.preferences)

    implementation(project(":library:core-domain"))
    implementation(project(":library:tdlib"))
}
