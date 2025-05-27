pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Anygram"

include(":app")

// feature
include(":feature:auth-common")
include(":feature:auth-navigation")
include(":feature:auth-start")
include(":feature:country-chooser")
include(":feature:phone-number")
include(":feature:otp")
include(":feature:home")
include(":feature:chats")

// library
include(":library:core-data")
include(":library:core-domain")
include(":library:core-presentation")
include(":library:tdlib")
