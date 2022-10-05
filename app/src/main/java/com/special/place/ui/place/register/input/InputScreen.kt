package com.special.place.ui.place.register.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun InputPlaceName(vm: PlaceInputEventListener) {
    val placeName by vm.placeName.observeAsState("")

    TextField(
        value = placeName,
        onValueChange = { vm.setPlaceName(it) },
        label = { Text("플레이스 이름") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun InputPlaceDescription(vm: PlaceInputEventListener) {
    val placeDescription by vm.placeDescription.observeAsState("")

    TextField(
        value = placeDescription,
        onValueChange = { vm.setPlaceDescription(it) },
        label = { Text("플레이스 설명") },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp)
    )
}