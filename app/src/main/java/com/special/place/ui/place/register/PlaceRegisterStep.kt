package com.special.place.ui.place.register

enum class PlaceRegisterStep(val step: Int) {
    Location(0),
    SelectPicture(1),
    InputPlaceName(2),
    ChooseHashtag(3),
    Complete(4)
}

fun PlaceRegisterStep.next(): PlaceRegisterStep {
    return PlaceRegisterStep.values()[step + 1]
}