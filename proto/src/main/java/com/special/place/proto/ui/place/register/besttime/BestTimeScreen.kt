package com.special.place.proto.ui.place.register.besttime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.special.place.proto.showTimePicker


@Composable
fun VisitTimePicker(vm: BestTimeEventListener) {
    val visitTime by vm.placeVisitTime.observeAsState("00:00 - 00:00")

    val timeEndPicker = showTimePicker(LocalContext.current) { hour, minute ->
        vm.setPlaceBestEndTime(String.format("%02d:%02d", hour, minute))
    }

    val timeStartPicker = showTimePicker(LocalContext.current) { hour, minute ->
        vm.setPlaceBestStartTime(String.format("%02d:%02d", hour, minute))

        timeEndPicker.updateTime(hour, minute)
        timeEndPicker.show()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "방문하기 좋은 시간", modifier = Modifier.width(120.dp))
        Text(text = visitTime,
            textAlign = TextAlign.Center,
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier
                .clickable { timeStartPicker.show() }
                .padding(8.dp)
                .fillMaxSize())
    }
}