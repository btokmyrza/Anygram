plugins {
    alias(libs.plugins.android.library)
}
apply(from = "$rootDir/scripts/library-module.gradle")

android {
    namespace = "kz.btokmyrza.anygram.library.core.domain"
}
