import com.special.buildsrc.Deps

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Deps.Kotlin.Coroutines.core)

    implementation(Deps.Retrofit.core)
    implementation(Deps.Retrofit.gsonConverter)

    implementation(Deps.Hilt.dagger)
    kapt(Deps.Hilt.daggerCompiler)
}