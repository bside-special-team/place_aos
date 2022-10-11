//pluginManagement {
//    repositories {
//        gradlePluginPortal()
//        google()
//        mavenCentral()
//    }
//}
//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
rootProject.buildFileName = "build.gradle.kts"
rootProject.name = "Place"
include(":app")
include(":domain")
include(":remote")
include(":data")
include(":mock")
include(":proto")
include(":resource")
