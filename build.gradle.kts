buildscript {
    repositories {
        mavenCentral()
        jcenter()
        google()
    }


}// Top-level build file where you can add configuration options common to all sub-projects/modules.

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()

        maven(url = "https://naver.jfrog.io/artifactory/maven/")
    }
}

//plugins {
//    id("com.android.application") version "7.2.0" apply false
//    id("com.android.library") version "7.2.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
//    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false
//}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}