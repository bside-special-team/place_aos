package com.special.place.ui.place.map

import com.naver.maps.map.overlay.OverlayImage
import com.special.place.resource.R

sealed class PlaceMarker(overlayImage: OverlayImage) {
    object Normal : PlaceMarker(OverlayImage.fromResource(R.drawable.ic_place_normal))
    object Visited : PlaceMarker(OverlayImage.fromResource(R.drawable.ic_place_visited))
    object Focused : PlaceMarker(OverlayImage.fromResource(R.drawable.ic_place_focused))
    object Highlighted : PlaceMarker(OverlayImage.fromResource(R.drawable.ic_place_highlighted))
}

