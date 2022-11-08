package com.special.data.social

import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.special.place.resource.R

object SocialLoginHelper {
    fun appInitial(context: Context) {
        KakaoSdk.init(context, context.getString(R.string.kakao_app_key))
    }
}