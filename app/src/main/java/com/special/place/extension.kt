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
import com.special.domain.entities.Coordinate
import com.special.domain.entities.Place

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun Place.toMarker() = Marker(
    state = MarkerState(coordinate.toLatLng()),
    captionText = name,
    captionRequestedWidth = 200.dp,
    captionAligns = arrayOf(Align.Top)
)

fun Coordinate.toLatLng(): LatLng = LatLng(latitude.toDouble(), longitude.toDouble())

@Composable
fun showTimePicker(context: Context, callback: (Int, Int) -> Unit): TimePickerDialog {
    return TimePickerDialog(
        context, { picker, hour, minute ->
            callback.invoke(hour, minute)
        }, 0, 0, true)
}