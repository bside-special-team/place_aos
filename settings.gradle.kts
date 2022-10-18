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
//        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
//        maven(url = "https://naver.jfrog.io/artifactory/maven/")
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
