package com.special.place.ui

import com.special.domain.entities.place.Place

sealed class Route {
    object TutorialPage : Route()
    object LoginPage : Route()
    object ModifyNickNamePage : Route()
    object MainPage : Route()
    object PlaceRegisterPage : Route()
    object MyInfoPage : Route()
    object SettingPage : Route()
    object MyActivityPage : Route()
    object BadgeListPage : Route()

    class PlaceDetailPage(val place: Place) : Route()
}
