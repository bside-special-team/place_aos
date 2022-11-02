import com.special.buildsrc.Configs
import com.special.buildsrc.Deps

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = Configs.compileSdkVersion

    defaultConfig {
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":remote"))
    implementation(project(":mock"))
    implementation(project(":resource"))

    implementation(Deps.OkHttp.loggingInterceptor)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)

    debugImplementation(Deps.Pluto.debug)
    releaseImplementation(Deps.Pluto.release)
    debugImplementation(Deps.Pluto.debugNetwork)
    releaseImplementation(Deps.Pluto.releaseNetwork)

    implementation(Deps.Google.login)
    implementation(Deps.Kakao.login)

//    implementation("androidx.core:core-ktx:1.9.0")
//    implementation("androidx.appcompat:appcompat:1.5.1")
//    implementation("com.google.android.material:material:1.6.1")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}