package com.special.buildsrc

object Deps {

    object Kotlin {
        const val version = "1.7.10"

        const val bom = "org.jetbrains.kotlin:kotlin-bom"

        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version}"

        object Coroutines {
            private const val version = "1.6.0-RC"

            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }

        object Test {
            object Versions {
                const val jupiterVersion = "5.5.0"
            }

            const val core = "org.jetbrains.kotlin:kotlin-test:$version"
            const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
            const val junit = "org.jetbrains.kotlin:kotlin-test-junit:$version"
            const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2"
            const val jupiter = "org.junit.jupiter:junit-jupiter-api:${Versions.jupiterVersion}"
            const val jupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.jupiterVersion}"
        }
    }

    object Android {
        object Versions {
            const val lifecycle = "2.3.1"
        }

        const val core = "androidx.core:core-ktx:1.9.0"


        const val activityCompose = "androidx.activity:activity-compose:1.5.1"
        const val constraintLayoutCompose = "androidx.constraintlayout:constraintlayout-compose:1.0.1"

        const val viewModelKTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val liveDataKTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val runtimeKTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

        object Test {
            const val junit = "junit:junit:4.13.2"
            const val androidJunit = "androidx.test.ext:junit:1.1.3"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        }
    }

    object Compose {
        object Versions {
            const val core = "1.3.0-beta01"
        }

        object Test {
            const val junit = "androidx.compose.ui:ui-test-junit4:${Versions.core}"
        }

        const val ui = "androidx.compose.ui:ui:${Versions.core}"
        const val material = "androidx.compose.material:material:${Versions.core}"
        const val uiPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.core}"
        const val manifest = "androidx.compose.ui:ui-test-manifest:${Versions.core}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.core}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"
        const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:1.2.1"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:2.0.0-rc01"
    }

    object Retrofit {
        object Versions {
            const val retrofit = "2.9.0"
            const val gsonConverter = "2.9.0"
        }

        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    }

    object OkHttp {
        object Versions {
            const val loggingInterceptor = "4.9.1"
        }

        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    }

    object Google {
        object Versions {
            const val gson = "2.8.7"
            const val playService = "16.0.0"
        }

        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val playLocation = "com.google.android.gms:play-services-location:${Versions.playService}"
    }

    object Hilt {
        object Versions {
            const val core = "2.43.2"
        }

        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.core}"
        const val android = "com.google.dagger:hilt-android:${Versions.core}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.core}"

        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.core}"
        const val dagger = "com.google.dagger:dagger:${Versions.core}"
    }

    object NaverMap {
        object Versions {
            const val sdk = "3.15.0"
            const val compose = "1.2.1"
        }

        const val sdk = "com.naver.maps:map-sdk:${Versions.sdk}"
        const val compose = "io.github.fornewid:naver-map-compose:${Versions.compose}"
    }


    object Pluto {
        private const val version = "2.0.5"

        const val debug = "com.plutolib:pluto:$version"
        const val release = "com.plutolib:pluto-no-op:$version"

        const val debugNetwork = "com.plutolib.plugins:network:$version"
        const val releaseNetwork = "com.plutolib.plugins:network-no-op:$version"



    }

    object Pager{
        private const val version = "0.20.1"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
        const val indicators = "com.google.accompanist:accompanist-pager-indicators:$version"
    }
}