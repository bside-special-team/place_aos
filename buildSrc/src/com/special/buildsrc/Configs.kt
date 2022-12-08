package com.special.buildsrc

object Configs {
    const val compileSdkVersion = 33
    const val minSdkVersion = 23
    const val targetSdkVersion = 33

    const val versionCode = 9
    const val versionName = "0.1.0"

    const val composeVersion = "1.3.1"

    object Signing {
        const val alias = "special"
        const val storePassword = "tmvptufXla!@"
        const val keyPassword = "tmvptufXla!@"

        const val keyStorePath = "../keys/special_team.jks"
    }
}