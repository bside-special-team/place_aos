package com.special.place.proto.social

sealed class LoginType {
    object KakaoLoginType: LoginType()
    object GoogleLoginType: LoginType()
}
