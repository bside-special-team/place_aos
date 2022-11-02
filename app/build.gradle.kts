import com.special.buildsrc.Configs
import com.special.buildsrc.Deps

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Configs.compileSdkVersion

    defaultConfig {
        applicationId = "com.special.place"
        minSdk = Configs.minSdkVersion
        targetSdk = Configs.targetSdkVersion
        versionCode = Configs.versionCode
        versionName = Configs.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Configs.composeVersion
    }
    packagingOptions {
        resources.excludes.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":resource"))

    implementation(Deps.Android.core)
    implementation(Deps.Android.runtimeKTX)
    implementation(Deps.Android.activityCompose)
    implementation(Deps.Android.constraintLayoutCompose)

    implementation(Deps.Android.viewModelKTX)
    implementation(Deps.Android.liveDataKTX)

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)

    implementation(Deps.Compose.ui)
    implementation(Deps.Compose.material)
    implementation(Deps.Compose.uiPreview)
    implementation(Deps.Compose.runtimeLiveData)

    debugImplementation(Deps.Compose.tooling)
    debugImplementation(Deps.Compose.manifest)

    implementation(Deps.Coil.coilCompose) {
        // 'coil'과 'naverMap'이 의존성 충돌 발생.
        exclude(group = "androidx.appcompat", module = "appcompat-resources")
    }

    implementation(Deps.Hilt.android)
    kapt(Deps.Hilt.compiler)

    implementation(Deps.NaverMap.sdk)
    implementation(Deps.NaverMap.compose)
    implementation(Deps.Google.playLocation)

    implementation(Deps.Pager.pager)
    implementation(Deps.Pager.indicators)

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.1")
}