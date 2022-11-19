package com.special.place

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.Align
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.overlay.OverlayImage
import com.special.domain.entities.place.Coordinate
import com.special.domain.entities.place.Place
import com.special.place.resource.R

val marker = OverlayImage.fromResource(R.drawable.ic_marker_question)

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun Place.toMarker(callback: () -> Unit) = Marker(
    icon = marker,
    state = MarkerState(coordinate.toLatLng()),
    captionText = name,
    captionRequestedWidth = 200.dp,
    captionAligns = arrayOf(Align.Top),
    onClick = {
        callback.invoke()
        false
    }
)

fun Coordinate.toLatLng(): LatLng = LatLng(latitude.toDouble(), longitude.toDouble())

@Composable
fun showTimePicker(context: Context, hour: Int = 0, minute: Int = 0, callback: (Int, Int) -> Unit): TimePickerDialog {
    return TimePickerDialog(
        context, { picker, hour, minute ->
            callback.invoke(hour, minute)
        }, hour, minute, true)
}