plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}
apply(from = "$rootDir/scripts/library-module.gradle")

android {
    namespace = "kz.btokmyrza.anygram.library.core.presentation"

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)

    implementation(project(":library:core-domain"))
}
