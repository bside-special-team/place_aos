package com.special.place.ui.place.register

enum class PlaceRegisterStep(val step: Int) {
    Location(0),
    SelectPicture(1),
    InputPlaceName(2),
    ChooseHashtag(3),
    Complete(4)
}

fun PlaceRegisterStep.next(): PlaceRegisterStep {
    val values = PlaceRegisterStep.values()
    if (step >= values.size) {
        return PlaceRegisterStep.Complete
    }
    return values[step + 1]
}

fun PlaceRegisterStep.back(): PlaceRegisterStep {
    if (step == 0) {
        return PlaceRegisterStep.Location
    }
    return PlaceRegisterStep.values()[step - 1]
}