plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "kz.btokmyrza.anygram"
    compileSdk = 35

    defaultConfig {
        applicationId = "kz.btokmyrza.anygram"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
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
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    implementation(libs.decompose)
    implementation(libs.decompose.extensions.compose)

    implementation(libs.kotlinx.serialization.core)

    implementation(project(":feature:auth-common"))
    implementation(project(":feature:auth-navigation"))
    implementation(project(":feature:auth-start"))
    implementation(project(":feature:country-chooser"))
    implementation(project(":feature:phone-number"))
    implementation(project(":feature:otp"))
    implementation(project(":feature:home"))
    implementation(project(":feature:chats"))

    implementation(project(":library:core-data"))
    implementation(project(":library:core-domain"))
    implementation(project(":library:core-presentation"))
    implementation(project(":library:tdlib"))
}
